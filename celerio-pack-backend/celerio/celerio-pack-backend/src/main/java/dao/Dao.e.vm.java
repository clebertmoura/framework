$output.java($entity.dao)##

$output.require($entity.model)##
$output.require($entity.root.primaryKey)##
$output.require("${Root.packageName}.core.dao.AppBaseDaoImpl")##

$output.require("javax.ejb.Stateless") ##

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import br.jus.framework.search.impl.Operator;
import br.jus.framework.search.impl.PageRequest;

/**
 * Implementação de DAO da entidade: {@link $entity.model.type} 
 *
 * @see AppBaseDaoImpl
 */
@Stateless
public class $output.currentClass extends AppBaseDaoImpl<${entity.root.primaryKey.type}, ${entity.model.type}> {
	
	private static final long serialVersionUID = 1L;
	
	public ${entity.model.type}Dao() {
		super(${entity.model.type}.class);
	}
	
	@Override
    protected void addGlobalFilterPredicates(PageRequest pageRequest, List<Predicate> predicates,
    		CriteriaBuilder cBuilder, Root<${entity.model.type}> from, Map<String, Path<?>> mapFieldPaths) {
    	super.addGlobalFilterPredicates(pageRequest, predicates, cBuilder, from, mapFieldPaths);
    	String globalFilter = pageRequest.getGlobalFilter();
    	List<Predicate> globalFilterPredicates = new ArrayList<>();
## --- Search attributes
#foreach($attribute in $entity.searchAttributes.flatUp.list)
#if(!$attribute.isInPk() && !$attribute.isFile())
#if($attribute.isString())
		globalFilterPredicates.add(this.createFieldPredicate("${attribute.var}", Operator.LI, globalFilter, mapFieldPaths, from, cBuilder));
#else
		globalFilterPredicates.add(this.createFieldPredicate("${attribute.var}", Operator.EQ, globalFilter, mapFieldPaths, from, cBuilder));
#end		
#end
#end
		if (!globalFilterPredicates.isEmpty()) {
			Predicate predicateOr = cBuilder.or(globalFilterPredicates.toArray(new Predicate[0]));
			predicates.add(predicateOr);
		}
    }
	
}