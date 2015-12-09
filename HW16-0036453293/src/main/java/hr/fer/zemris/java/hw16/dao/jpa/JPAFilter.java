package hr.fer.zemris.java.hw16.dao.jpa;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

/**
 * Class used as a data base connection filter. It stops everything requesting or coming from /servleti/*
 * and creates or destroys its JPA entity manager provider.
 * 
 * @author MarkoB
 * @version 1.0
 */
@WebFilter("/servleti/*")
public class JPAFilter implements Filter {

	/**
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	/**
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, 
	 * javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		try {
			chain.doFilter(request, response);
		} finally {
			JPAEMProvider.close();
		}
	}
	
	/**
	 * @see javax.servlet.Filter#destroy()
	 */
	@Override
	public void destroy() {
	}
}