package com.hxcel.globalhealth.web;

/**
 * User: bjorn
 * Date: Jan 1, 2008
 * Time: 6:10:59 PM
 */
import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

public class FlexWebServicesExceptionFilter implements Filter {

  public void destroy() {
  }

  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
  throws IOException, ServletException {

  HttpServletResponse httpResponse = (HttpServletResponse) response;
  FlexExceptionHttpServletResponseHandler wrapper = new FlexExceptionHttpServletResponseHandler(httpResponse);

  chain.doFilter(request, wrapper);
}

public void init(FilterConfig arg0) throws ServletException {
}

}
