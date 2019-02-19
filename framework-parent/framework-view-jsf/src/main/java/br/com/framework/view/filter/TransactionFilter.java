package br.com.framework.view.filter;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.transaction.Status;
import javax.transaction.UserTransaction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Filtro de transação.
 * 
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 *
 */
public class TransactionFilter implements Filter {
	
	private static Logger logger = LoggerFactory.getLogger(TransactionFilter.class);
	
	@Override
	public void destroy() {
	}

	@Resource
	private UserTransaction utx;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		try {
			utx.begin();
			chain.doFilter(request, response);
			int status = utx.getStatus();
			if (status == Status.STATUS_ACTIVE) {
				utx.commit();
			} else if (status == Status.STATUS_MARKED_ROLLBACK) {
				utx.rollback();
			}
		} catch (Exception e) {
			logger.error("Error ao comitar transacao.", e);
		}

	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {

	}
}