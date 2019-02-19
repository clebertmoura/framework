package br.com.framework.model.error.interceptor;

import java.util.List;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import br.com.framework.model.error.api.Error;
import br.com.framework.model.error.impl.ErrorBuilder;
import br.com.framework.model.error.impl.ErrorBusiness;
import br.com.framework.model.exception.ModelException;
import br.com.framework.model.log.impl.MessengerProducer;
import br.com.framework.model.util.Constants;
import br.com.framework.util.Config;

@Interceptor
@LogInterceptorBinding
public class LogInterceptor {

	@Inject
	private MessengerProducer messengerProducer;

	@AroundInvoke
	public Object intercept(InvocationContext context) throws Exception {
		Config config = new Config(context.getTarget().getClass().getClassLoader(), Constants.CONFIG_FILENAME);
		Boolean isInterceptorActivated = Boolean.parseBoolean(config.getPropriedade("isInterceptorActivated"));

		Object proceed = null;
		try {
			proceed = context.proceed();
		} catch (ModelException e) {
			if (!isInterceptorActivated)
				return proceed;

			ErrorBusiness errorBusiness = ErrorBuilder.buildErrorBusiness(null);
			List<Error> errors = ErrorBuilder.buildFromModelException(e);
			errorBusiness.getErrors().addAll(errors);
			messengerProducer.sendToJMSQueue(errorBusiness);
		}
		return proceed;
	}

}
