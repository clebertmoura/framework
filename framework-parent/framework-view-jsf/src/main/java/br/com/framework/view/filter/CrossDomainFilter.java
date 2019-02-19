package br.com.framework.view.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

public class CrossDomainFilter implements Filter {
	
	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		((HttpServletResponse)response).addHeader("Access-Control-Allow-Origin", "*");
		((HttpServletResponse)response).addHeader("Access-Control-Allow-Headers", "Authorization, Origin, X-Requested-With, Content-Type");
        ((HttpServletResponse)response).addHeader("Access-Control-Expose-Headers", "Location, Content-Disposition");
        ((HttpServletResponse)response).addHeader("Access-Control-Allow-Methods", "POST, PUT, GET, DELETE, HEAD, OPTIONS");
        chain.doFilter(request, response);
        
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {

	}
}