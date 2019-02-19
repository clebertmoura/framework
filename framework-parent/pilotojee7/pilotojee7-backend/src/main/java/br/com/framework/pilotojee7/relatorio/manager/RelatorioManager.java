package br.com.framework.pilotojee7.relatorio.manager;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.enterprise.event.TransactionPhase;
import javax.inject.Inject;
import javax.persistence.PersistenceException;
import javax.validation.ConstraintViolationException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import br.com.framework.model.exception.ModelException;
import br.com.framework.model.qualifiers.AppConfig;
import br.com.framework.pilotojee7.core.manager.AppBaseManagerImpl;
import br.com.framework.pilotojee7.core.util.Constants;
import br.com.framework.pilotojee7.relatorio.dao.RelatorioDao;
import br.com.framework.pilotojee7.relatorio.domain.Relatorio;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;

/**
 * Manager da entidade Relatorio.
 *
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 */
@Stateless
public class RelatorioManager extends AppBaseManagerImpl<Long, Relatorio, RelatorioDao> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
    @AppConfig
    private Properties appConfig;
	
	private File baseReportPath;
	
	@Inject
	private Event<Relatorio> event;

	public RelatorioManager() {
		super(Relatorio.class);
	}
	
	@PostConstruct
	private void postConstruct() {
		baseReportPath = new File(appConfig.getProperty(Constants.APP_SYSTEM_BASEPATH), Constants.APP_RELATORIOS_PATH);
		if (!baseReportPath.exists()) {
			baseReportPath.mkdirs();
		}
	}
	
	@Override
	@Inject
	protected void setSearch(RelatorioDao searchable) {
		super.setSearch(searchable);
	}
	
	@Override
	public Relatorio insert(Relatorio entity)
			throws PersistenceException, ConstraintViolationException, ModelException {
		entity = super.insert(entity);
		event.fire(entity);
		return entity;
	}
	
	@Override
	public Relatorio update(Relatorio entity)
			throws PersistenceException, ConstraintViolationException, ModelException {
		// TODO Auto-generated method stub
		entity = super.update(entity);
		event.fire(entity);
		return entity;
	}
	
	@Override
	public void validateEntityFields(Relatorio entity, boolean isInsert)
			throws ConstraintViolationException, ModelException {
		super.validateEntityFields(entity, isInsert);
		if (entity.getJrxmlData() != null) {
			ByteArrayInputStream bais = new ByteArrayInputStream(entity.getJrxmlData());
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			try {
				JasperCompileManager.compileReportToStream(bais, baos);
			} catch (JRException e) {
				logger.error("Erro ao compilar relatorio.", e);
				throw new ModelException(e, "erro.relatorio.compilacao");
			}
		}
	}
	
	/**
	 * Observa o evento de insert e update do relatorio.
	 * 
	 * @param relatorio
	 */
	public void observerRelatorio(@Observes(during = TransactionPhase.AFTER_SUCCESS) Relatorio relatorio) {
		compilarRelatorio(relatorio);
	}

	/**
	 * Compila o relatorio.
	 * 
	 * @param relatorio
	 */
	public void compilarRelatorio(Relatorio relatorio) {
		ByteArrayInputStream inStream = new ByteArrayInputStream(relatorio.getJrxmlData());
		String compiledFileName = String.format("%s.jasper", relatorio.getId());
		File compiledFile = new File(baseReportPath, compiledFileName);
		if (compiledFile.exists()) {
			FileUtils.deleteQuietly(compiledFile);
		}
		FileOutputStream outStream = null;
		try {
			outStream = FileUtils.openOutputStream(compiledFile);
			JasperCompileManager.compileReportToStream(inStream, outStream);
			outStream.flush();
		} catch (IOException e) {
			logger.error("Erro ao criar arquivo temporario para compilacao do relatorio.", e);
		} catch (JRException e) {
			logger.error("Erro ao compilar o relatorio.", e);
		} finally {
			if (outStream != null) 
				IOUtils.closeQuietly(outStream);
			IOUtils.closeQuietly(inStream);
		}
	}
	
}