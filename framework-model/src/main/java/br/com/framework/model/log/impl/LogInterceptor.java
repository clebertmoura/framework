package br.com.framework.model.log.impl;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import br.com.framework.model.exception.ModelException;
import br.com.framework.model.log.qualifiers.LogInterceptorBinding;
import br.com.framework.model.util.Constantes;
import br.com.framework.util.Config;

@Interceptor
@LogInterceptorBinding
public class LogInterceptor {

	private final Config config = new Config(this.getClass().getClassLoader(), Constantes.CONFIG_FILENAME);

	@Inject
	private MessengerProducer messengerProducer;

	@AroundInvoke
	public Object intercept(InvocationContext context) throws Exception {

		Boolean isInterceptorActivated = Boolean.parseBoolean(config.getPropriedade("isInterceptorActivated"));

		Object proceed = null;

		try {
			proceed = context.proceed();

		} catch (ModelException e) {

			if (!isInterceptorActivated)
				return proceed;

			ErrorDefault erroDefault = e.getErroDefault();

			messengerProducer.sendToJMSQueue(erroDefault);
		}

		return proceed;

	}

}
