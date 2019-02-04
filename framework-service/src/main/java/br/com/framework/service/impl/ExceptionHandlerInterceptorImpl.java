package br.com.framework.service.impl;

import java.io.Serializable;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import org.slf4j.Logger;

import br.com.framework.service.api.ExceptionHandlerInterceptor;

@ExceptionHandlerInterceptor
@Interceptor
public class ExceptionHandlerInterceptorImpl implements Serializable {

	private static final long serialVersionUID = -1701533870027197422L;
	
	@Inject
	private Logger logger;
	
	@AroundInvoke
	public Object beginTransactionIfNotActive(InvocationContext ic) throws Throwable {
		Object retorno = null;
		try {
			retorno = ic.proceed();
			logger.warn("teste");
		} catch (Throwable t) {
			Throwable e = getExceptionRootCause(t);
			
			/*try {
				throw e;
			} catch (SQLException e1) {
			} catch (GenericException e1) {
			}catch (OptimisticLockException | StaleObjectStateException e2) {
			}catch (Exception e1) {
			}*/
		}
		return retorno;
	}
	
	public static Throwable getExceptionRootCause(Throwable e) {
		if (e.getCause() != null) {
			return getExceptionRootCause(e.getCause());
		} else {
			return e;
		}
	}

}
