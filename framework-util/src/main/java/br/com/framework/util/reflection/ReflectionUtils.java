/**
 * 
 */
package br.com.framework.util.reflection;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import com.google.common.base.Predicates;

/**
 * Classe com métodos utilitários de reflexão.
 * 
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 *
 */
public abstract class ReflectionUtils {

	/**
	 * Retorna o {@link Field} correspondente ao fieldName informado
	 * 
	 * @param fieldName
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Field getField(String fieldName, Class<?> clazz) {
		Set<Field> fields = org.reflections.ReflectionUtils.getAllFields(clazz, 
				org.reflections.ReflectionUtils.withName(fieldName));
		return !fields.isEmpty() ? fields.iterator().next() : null;
	}
	
	/**
	 * Retorna um array com todos os {@link Field} da classe informada.
	 * 
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Field[] getAllFields(Class<?> clazz) {
		Set<Field> fields = org.reflections.ReflectionUtils.getAllFields(clazz);
		Field[] fieldResult = new Field[fields.size()];
		return fields.toArray(fieldResult);
	}
	
	/**
	 * Retorna o {@link Method} acessor do fieldName informado.
	 * 
	 * @param clazz
	 * @param fieldClazz
	 * @param fieldName
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Method getFieldGetter(Class<?> clazz, Class<?> fieldClazz, String fieldName) {
		Set<Method> getters = org.reflections.ReflectionUtils.getAllMethods(clazz, 
              Predicates.and(
            		  org.reflections.ReflectionUtils.withModifier(Modifier.PUBLIC), 
            		  org.reflections.ReflectionUtils.withName("get" + StringUtils.capitalize(fieldName)),
            		  org.reflections.ReflectionUtils.withReturnType(fieldClazz),
            		  org.reflections.ReflectionUtils.withParametersCount(0)));
		return !getters.isEmpty() ? getters.iterator().next() : null;
	}
	
	/**
	 * Retorna o {@link Method} modificador do fieldName informado.
	 * 
	 * @param clazz
	 * @param fieldName
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Method getFieldSetter(Class<?> clazz, String fieldName) {
		Set<Method> getters = org.reflections.ReflectionUtils.getAllMethods(clazz, 
              Predicates.and(
            		  org.reflections.ReflectionUtils.withModifier(Modifier.PUBLIC), 
            		  org.reflections.ReflectionUtils.withName("set" + StringUtils.capitalize(fieldName)),
            		  org.reflections.ReflectionUtils.withParametersCount(1)));
		return !getters.isEmpty() ? getters.iterator().next() : null;
	}
	
	/**
	 * Retorna um array com todos os {@link Method} da classe informada.
	 * 
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Method[] getAllMethods(Class<?> clazz) {
		Set<Method> methods = org.reflections.ReflectionUtils.getAllMethods(clazz);
		Method[] fieldResult = new Method[methods.size()];
		return methods.toArray(fieldResult);
	}
	
	/**
	 * Retorna o {@link Method} informado.
	 * 
	 * @param clazz
	 * @param methodName
	 * @param paramTypes
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Method getMethod(Class<?> clazz, String methodName, Class<?>... paramTypes) {
		Set<Method> methods = org.reflections.ReflectionUtils.getAllMethods(clazz, 
	          Predicates.and(
	        		  org.reflections.ReflectionUtils.withModifier(Modifier.PUBLIC), 
	        		  org.reflections.ReflectionUtils.withName(methodName),
	        		  org.reflections.ReflectionUtils.withParameters(paramTypes)));
		return !methods.isEmpty() ? methods.iterator().next() : null;
	}
	

}
