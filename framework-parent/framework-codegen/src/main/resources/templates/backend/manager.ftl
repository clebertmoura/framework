package ${modulePackage}.manager;

import javax.ejb.Stateless;
import javax.inject.Inject;

import ${modulePackage}.dao.${entityName}Dao;
import ${modulePackage}.domain.${entityName};
import ${basePackage}.core.manager.AppBaseManagerImpl;

/**
 * Manager da entidade ${entityName}.
 *
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 */
@Stateless
public class ${entityName}Manager extends AppBaseManagerImpl<Long, ${entityName}, ${entityName}Dao> {

	public ${entityName}Manager() {
		super(${entityName}.class);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	@Inject
	protected void setSearch(${entityName}Dao searchable) {
		super.setSearch(searchable);
	}

}