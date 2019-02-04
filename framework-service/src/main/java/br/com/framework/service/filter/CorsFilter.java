package br.com.framework.service.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.framework.model.util.Constantes;
import br.com.framework.util.Config;

/**
 * Filtro que intercepta as requisições e valida o CORS Domain. 
 * 
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 *
 */
public class CorsFilter implements Filter {

	private final Config config = new Config(this.getClass().getClassLoader(), Constantes.CONFIG_FILENAME);

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
			throws IOException, ServletException {

		Boolean isEnable = Boolean.valueOf(config.getPropriedade("cors.enable"));
		Boolean isAllowed = false;
		
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse resp = (HttpServletResponse) servletResponse;

		if (isEnable) {

			String[] allowedHost = config.getPropriedade("cors.allowed.origins").split(",");
			String requestURL = request.getRequestURL().toString();

			for (int i = 0; i < allowedHost.length; i++) {

				if (isAllowed) {
					break;
				}

				isAllowed = requestURL.contains(allowedHost[i]);
			}

			if (!isAllowed) {
				resp.setStatus(HttpServletResponse.SC_ACCEPTED);
				return;
			}
		}
		chain.doFilter(request, servletResponse);

	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

}
