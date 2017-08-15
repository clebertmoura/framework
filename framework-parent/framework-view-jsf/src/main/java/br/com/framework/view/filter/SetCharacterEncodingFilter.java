/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package br.com.framework.view.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 * 
 */
public class SetCharacterEncodingFilter implements Filter {

	public static final String INIT_PARAM_ENCODING = "encoding";
	public static final String DEFAULT_ENCODING = "UTF-8";

	private String encoding;

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		if (null == request.getCharacterEncoding()) {
			request.setCharacterEncoding(encoding);
		}
		/** * Set the default response content type and encoding */
		String contentType = getContentTypeEncoding(response.getContentType());
		response.setContentType(contentType);
		response.setCharacterEncoding(encoding);
		chain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig config) throws ServletException {
		String paramEnconding = config.getInitParameter(INIT_PARAM_ENCODING);
		this.encoding = (paramEnconding != null) ? paramEnconding
				: DEFAULT_ENCODING;
	}

	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}
	
	/**
	 * @param contentType
	 * @return
	 */
	public String getContentTypeEncoding(String contentType) {
		if (contentType != null) {
			if (contentType.indexOf("charset=") < 0) {
				if (contentType.indexOf(";") < 0) {
					contentType += ";";
				}
				contentType += " charset=" + encoding;
			} else {
				contentType = contentType.substring(0, contentType.indexOf("charset=") + 8) + encoding;
			}
		} else {
			contentType = "text/html; charset=" + encoding;
		}
		return contentType;
	}

}