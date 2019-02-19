package br.com.framework.search.indexer.listener;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;

import org.apache.commons.lang3.StringUtils;
import org.apache.solr.common.SolrInputDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.framework.domain.api.BaseEntity;
import br.com.framework.search.indexer.annotations.DocumentId;
import br.com.framework.search.indexer.annotations.Indexed;
import br.com.framework.search.indexer.api.IndexedBaseEntity;
import br.com.framework.search.indexer.api.service.IndexerService;
import br.com.framework.util.reflection.ReflectionUtils;

/**
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 *
 */
public abstract class IndexedBaseEntityListener implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final String FIELD_ENTITY_CLASS = "_entity_class";

	private static Logger LOGGER = LoggerFactory.getLogger(IndexedBaseEntityListener.class);
	
	public IndexedBaseEntityListener() {
		super();
	}

	@SuppressWarnings("rawtypes")
	private void indexFieldTree(Field field, Object object, SolrInputDocument doc) {
		Object fieldValue = null;
		try {
			field.setAccessible(true);
			fieldValue = field.get(object);
		} catch (Exception e) {
			LOGGER.error("indexFieldTree");
		}
		if (fieldValue != null) {
			Class<?> runtimeFieldType = fieldValue.getClass();
			if (BaseEntity.class.isAssignableFrom(runtimeFieldType)) {
				Serializable id = ((BaseEntity)fieldValue).getId();
				doc.addField(field.getName(), String.format("%s|%s", runtimeFieldType.getName(), id));
			} else if (Collection.class.isAssignableFrom(runtimeFieldType)){
				Collection<?> collectionValue = (Collection<?>) fieldValue;
				Object firstItem = collectionValue.iterator().next();
				Class<?> itemClass = firstItem.getClass();
				List<Object> items = new ArrayList<>();
				if (BaseEntity.class.isAssignableFrom(itemClass)) {
					for (Object item : collectionValue) {
						items.add(String.format("%s|%s", itemClass.getName(), ((BaseEntity)item).getId()));
					}
				} else {
					for (Object item : collectionValue) {
						items.add(item);
					}
				}
				doc.addField(field.getName(), items);
			} else {
				doc.addField(field.getName(), fieldValue);
			}
		}
	}
	
	/**
	 * @param entityClass
	 * @param fieldsToIndex
	 * @param fieldsDocumentId
	 * @return
	 */
	private void initIndexFieldsList(Class<?> entityClass, List<Field> fieldsToIndex, List<Field> fieldsDocumentId) {
		Field[] allFields = ReflectionUtils.getAllFields(entityClass);
		for (Field field : allFields) {
			if (!Modifier.isStatic(field.getModifiers())) {
				if (field.isAnnotationPresent(br.com.framework.search.indexer.annotations.Field.class)) {
					fieldsToIndex.add(field);
				} else if (field.isAnnotationPresent(DocumentId.class)) {
					fieldsDocumentId.add(field);
				} else {
					Method method = ReflectionUtils.getMethod(entityClass, String.format("get%s", StringUtils.capitalize(field.getName())));
					if (method != null) {
						if (method.isAnnotationPresent(br.com.framework.search.indexer.annotations.Field.class)) {
							fieldsToIndex.add(field);
						} else if (method.isAnnotationPresent(DocumentId.class)) {
							fieldsDocumentId.add(field);
						}
					}
				}
				
			}
		}
		// remove os field que tenham sido anotados com @Field e @DocumentId erroneamente.
		fieldsToIndex.removeAll(fieldsDocumentId);
	}
	
	/**
	 * @param entity
	 * @return
	 */
	private SolrInputDocument buildDocumentFromEntity(Object entity) {
		SolrInputDocument document = null;
		Class<? extends Object> entityClass = entity.getClass();
		if (entityClass.isAnnotationPresent(Indexed.class)) {
			Indexed indexed = entityClass.getAnnotation(Indexed.class);
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug(String.format("Montando documento de indice da entidade: %s", entity.toString()));
			}
			List<Field> fieldsToIndex = new ArrayList<>();
			List<Field> fieldsDocumentId = new ArrayList<>();
			initIndexFieldsList(entityClass, fieldsToIndex, fieldsDocumentId);
			
			document = new SolrInputDocument();
			document.addField(FIELD_ENTITY_CLASS, entityClass.getName());
			
			if (!fieldsDocumentId.isEmpty()) {
				if (fieldsDocumentId.size() > 1) {
					SolrInputDocument docId = new SolrInputDocument();
					for (Field field : fieldsDocumentId) {
						indexFieldTree(field, entity, docId);
					}
					document.addField(indexed.compositeIdField(), docId);
				} else {
					Field field = fieldsDocumentId.get(0);
					try {
						field.setAccessible(true);
						Object value = field.get(entity);
						if (value != null) {
							document.addField(field.getName(), value);
						}
					} catch (Exception e) {
						LOGGER.error(String.format("Erro ao acessar campo '%s' da classe '%s'", field.getName(), entityClass.getName()));
					}
				}
				for (Field field : fieldsToIndex) {
					indexFieldTree(field, entity, document);
				}
			} else {
				LOGGER.error(String.format("A classe '%s' não possui atributo anotado com @DocumentId", entityClass.getName()));
				document = null;
			}
		} else {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug(String.format("A classe '%s' não possui anotação com @Indexed", entityClass.getName()));
			}
		}
		return document;
	}
	

	@PostPersist
	public void onPostPersist(Object object) {
		Class<? extends Object> entityClass = object.getClass();
		if (entityClass.isAnnotationPresent(Indexed.class)) {
			SolrInputDocument document = buildDocumentFromEntity(object);
			if (document != null) {
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug(String.format("Adicionado novo objeto ao indice. %s", object.toString()));
				}
				if (isIndexerServiceActive()) {
					try {
						getIndexerService().getSolrServer().add(document);
						getIndexerService().getSolrServer().commit();
					} catch (Exception e) {
						LOGGER.error(String.format("Ocorreu um erro ao adicionar indexar Entidade %s", 
								object.getClass().getName()), e);
					}
				} else {
					LOGGER.error(String.format("Índice da entidade '%s' não foi atualizado! Servidor Solr encontra-se indisponível.", entityClass.getName()));
				}
			}
		}
	}

	@PostUpdate
	public void onPostUpdate(Object object) {
		Class<? extends Object> entityClass = object.getClass();
		if (entityClass.isAnnotationPresent(Indexed.class)) {
			SolrInputDocument document = buildDocumentFromEntity(object);
			if (document != null) {
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug(String.format("Atualizando objeto ao indice. %s", object.toString()));
				}
				if (isIndexerServiceActive()) {
					try {
						getIndexerService().getSolrServer().add(document);
						getIndexerService().getSolrServer().commit();
					} catch (Exception e) {
						LOGGER.error(String.format("Ocorreu um erro ao atualizar indice da Entidade %s", 
								object.getClass().getName()), e);
					}
				} else {
					LOGGER.error(String.format("Índice da entidade '%s' não foi atualizado! Servidor Solr encontra-se indisponível.", entityClass.getName()));
				}
			}
		}
	}
	
	@PostRemove
	public void onPostRemove(Object object) {
		Class<? extends Object> entityClass = object.getClass();
		if (entityClass.isAnnotationPresent(Indexed.class)) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug(String.format("Adicionado novo objeto ao indice. %s", object.toString()));
			}
			if (IndexedBaseEntity.class.isAssignableFrom(entityClass)) {
				String uuid = ((IndexedBaseEntity<?>)object).getUuid();
				if (isIndexerServiceActive()) {
					try {
						getIndexerService().getSolrServer().deleteById(uuid);
						getIndexerService().getSolrServer().commit();
					} catch (Exception e) {
						LOGGER.error(String.format("Ocorreu um erro ao remover índice da Entidade %s. DocumentId = %s", 
								object.getClass().getName(), uuid), e);
					}
				} else {
					LOGGER.error(String.format("Índice da entidade '%s', Id '%s' não foi removido! Servidor Solr encontra-se indisponível.", entityClass.getName(), uuid));
				}
			} else if (BaseEntity.class.isAssignableFrom(entityClass)) {
				Serializable id = ((BaseEntity<?>)object).getId();
				if (isIndexerServiceActive()) {
					try {
						getIndexerService().getSolrServer().deleteByQuery(String.format("%s:%s AND id:%s", FIELD_ENTITY_CLASS, entityClass.getName(), id));
						getIndexerService().getSolrServer().commit();
					} catch (Exception e) {
						LOGGER.error(String.format("Ocorreu um erro ao remover índice da Entidade %s. DocumentId = %s", 
								object.getClass().getName(), id), e);
					}
				} else {
					LOGGER.error(String.format("Índice da entidade '%s', Id '%s' não foi removido! Servidor Solr encontra-se indisponível.", entityClass.getName(), id));
				}
			} else {
				LOGGER.error(String.format("A classe '%s' não implementa a interface %s.", entityClass.getName(), IndexedBaseEntity.class.getName()));
			}
		}
	}

	private boolean isIndexerServiceActive() {
		return getIndexerService() != null && getIndexerService().isServiceActive();
	}

	/**
	 * Este método deve ser implementado na classe concreta.
	 * 
	 * @return
	 */
	public abstract IndexerService getIndexerService();
	
}