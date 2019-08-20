package br.com.framework.model.locale;

import java.util.Locale;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.servlet.http.HttpServletRequest;


@Interceptor
public class LocaleInterceptor {
	
	public static final String LOCALE = "locale";
	
	@Resource
	private SessionContext sessionContext;
	
	@Inject
	private Instance<HttpServletRequest> request;

	@AroundInvoke
	public Object intercept(InvocationContext context) throws Exception {
		if (!request.isAmbiguous() && !request.isUnsatisfied()) {
			HttpServletRequest httpServletRequest = request.get();
			try {
				sessionContext.getContextData().put(LOCALE, httpServletRequest.getLocale());
			} catch (Exception e) {
				sessionContext.getContextData().put(LOCALE, 
						new Locale(System.getProperty("user.language"), System.getProperty("user.country")));
			}
		} else {
			sessionContext.getContextData().put(LOCALE, 
				new Locale(System.getProperty("user.language"), System.getProperty("user.country")));
		}
		return context.proceed();
	}

}
