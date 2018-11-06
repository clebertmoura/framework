package br.jus.framework.model.dao.util;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Path;

/**
 * Classe uitlitária para construção de expressions com funcões específicas do banco.
 * 
 * @author Unidade de Arquitetura de Software <setic.disis.uas@tjpe.jus.br>
 *
 */
public abstract class CriteriaBuilderHelper {

	public static final String TRANSLATE_FUNCTION_NAME = "translate";
	public static final String TRANSLATE_FROM = "'áàâãäéèêëíìïóòôõöúùûüÁÀÂÃÄÉÈÊËÍÌÏÓÒÔÕÖÚÙÛÜçÇ'";
	public static final String TRANSLATE_TO = "'aaaaaeeeeiiiooooouuuuAAAAAEEEEIIIOOOOOUUUUcC'";

	/**
	 * Retorna uma expression que converte todos os caracteres acentuados
	 * para o valor correspondente sen acento.
	 * 
	 * @param cb
	 * @param path
	 * @return
	 */
	public static Expression<String> translate(
			CriteriaBuilder cb, Path<String> path) {
		return cb.function(TRANSLATE_FUNCTION_NAME, String.class,
				path, cb.literal(TRANSLATE_FROM), cb.literal(TRANSLATE_TO));
	}
	
	/**
	 * Retorna uma expression que converte todos os caracteres acentuados
	 * para o valor correspondente sen acento.
	 * 
	 * @param cb
	 * @param value
	 * @return
	 */
	public static Expression<String> translate(
			CriteriaBuilder cb, String value) {
		return cb.function(TRANSLATE_FUNCTION_NAME, String.class,
				cb.literal(value), cb.literal(TRANSLATE_FROM), cb.literal(TRANSLATE_TO));
	}
	
}