package ${modulePackage}.dao;

import javax.ejb.Stateless;

import ${modulePackage}.domain.${entityName};
import ${basePackage}.core.dao.AppBaseDaoImpl;

/**
 *  DAO da entidade ${entityName}.
 *
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 */
@Stateless
public class ${entityName}Dao extends AppBaseDaoImpl<Long, ${entityName}>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1L;

	public ${entityName}Dao() {
		super(${entityName}.class);
	}
	
}
