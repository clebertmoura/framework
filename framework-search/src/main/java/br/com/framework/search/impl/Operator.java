package br.com.framework.search.impl;

/**
 * Operadores de comparação.
 * 
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 *
 */
public enum Operator {
	
	/**
	 * Less Than <
	 */
	LT,
	/**
	 * Greater Than >
	 */
	GT,
	/**
	 * Less Than or Equal To <=
	 */
	LE,
	/**
	 * Greater Than or Equal To =>
	 */
	GE,
	/**
	 * Equals
	 */
	EQ,
	/**
	 * Equals Ignore Case
	 */
	EI,
	/**
	 * Not Equals
	 */
	NE,
	/**
	 * In
	 */
	IN,
	/**
	 * Not In
	 */
	NI,
	/**
	 * Like
	 */
	LK,
	/**
	 * Like ignore case
	 */
	LI
}