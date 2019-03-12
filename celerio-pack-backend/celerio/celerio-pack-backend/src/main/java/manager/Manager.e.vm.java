$output.java($entity.manager)##

$output.require($entity.model)##
$output.require($entity.root.primaryKey)##
$output.require("${Root.packageName}.core.manager.AppBaseManagerImpl")##
$output.require($entity.dao)##

$output.require("javax.ejb.Stateless")##
$output.require("javax.ejb.EJB")##

import java.util.List;
import java.util.Map;

import javax.validation.ConstraintViolationException;

import org.apache.commons.lang3.StringUtils;

import br.com.framework.model.exception.ModelException;
import br.com.framework.model.error.impl.ErrorBusiness;
import br.com.framework.search.impl.Operator;


/**
 * Implementação Manager da entidade: {@link $entity.model.type} 
 *
 * @see AppBaseManagerImpl
 */
@Stateless
public class $output.currentClass extends AppBaseManagerImpl<${entity.root.primaryKey.type}, ${entity.model.type}, ${entity.model.type}Dao> {
	
	public ${entity.model.type}Manager() {
		super(${entity.model.type}.class);
	}
	
	@Override
	@EJB
	protected void setSearch(${entity.dao.type} search) {
		super.setSearch(search);
	}
	
	@Override
    public void validateEntityFields(${entity.model.type} entity, boolean isInsert)
    		throws ConstraintViolationException, ModelException {
    	super.validateEntityFields(entity, isInsert);
    	
    	List<ErrorBusiness> errors = newErrorList();
    	
## --- Raw attributes
#foreach ($attribute in $entity.nonCpkAttributes.list)
	#if(!$attribute.isInPk() && !$attribute.isVersion() && !$attribute.isAuditEntityAttribute() && !$attribute.isInFk() 
		&& !$attribute.getColumnConfig().getFieldName().equals("status") && $attribute.getColumnConfig().getUnique())
		#if($attribute.isString())
		if (StringUtils.isNotBlank(entity.${attribute.getter}())) {	
		#else
		if (entity.${attribute.getter}() != null) {	
		#end
			${entity.model.type} entityAttr = getSearch().findUniqueByField("${attribute.var}", Operator.EQ, entity.${attribute.getter}()).getUniqueResult();
			if ((isInsert && entityAttr != null) || (!isInsert && !entityAttr.getId().equals(entity.getId()))) {
				addError(errors, "${entity.model.var}.validation.${attribute.var}.uniqueViolation", entity.${attribute.getter}());
			}
		}
	#end
#end

		throwIfErros(errors);
    }
}