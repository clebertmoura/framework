/**
 * 
 */
package br.com.framework.util.reflection;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
	public static Field getField(String fieldName, Class<?> clazz) {
		Field field = null;
		while (clazz != null) {
			field = null;
			try {
				field = clazz.getDeclaredField(fieldName);
			} catch (Exception e) {}
			if (field != null) {
				break;
			}
			clazz = clazz.getSuperclass();
		}
		return field;
	}
	
	/**
	 * Retorna um array com todos os {@link Field} da classe informada.
	 * 
	 * @param clazz
	 * @return
	 */
	public static Field[] getAllFields(Class<?> clazz) {
		List<Field> fieldList = new ArrayList<Field>();
		while (clazz != null) {
			Field[] fields = clazz.getDeclaredFields();
			if (fields != null && fields.length > 0) {
				fieldList.addAll(Arrays.asList(fields));
			}
			clazz = clazz.getSuperclass();
		}
		Field[] fieldResult = new Field[fieldList.size()];
		return fieldList.toArray(fieldResult);
	}
	
	/**
	 * Retorna um array com todos os {@link Method} da classe informada.
	 * 
	 * @param clazz
	 * @return
	 */
	public static Method[] getAllMethods(Class<?> clazz) {
		List<Method> methodList = new ArrayList<Method>();
		while (clazz != null) {
			Method[] methods = clazz.getDeclaredMethods();
			if (methods != null && methods.length > 0) {
				methodList.addAll(Arrays.asList(methods));
			}
			clazz = clazz.getSuperclass();
		}
		Method[] fieldResult = new Method[methodList.size()];
		return methodList.toArray(fieldResult);
	}
	
	/**
	 * @param clazz
	 * @param methodName
	 * @param paramTypes
	 * @return
	 */
	public static Method getMethod(Class<?> clazz, String methodName, Class<?>... paramTypes) {
		Method method = null;
		while (clazz != null) {
			try {
				method = clazz.getDeclaredMethod(methodName, paramTypes);
			} catch (NoSuchMethodException e) {
			} catch (SecurityException e) {
			}
			if (method != null) {
				break;
			}
			clazz = clazz.getSuperclass();
		}
		return method;
	}
	

}
