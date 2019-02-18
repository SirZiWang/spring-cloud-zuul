package com.wangzi.zuul.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;


public class AccessFilter extends ZuulFilter{
	
	Logger logger =  LoggerFactory.getLogger(AccessFilter.class);

	/**
	 * 具体逻辑
	 */
	@Override
	public Object run() throws ZuulException {
		RequestContext cxt= RequestContext.getCurrentContext();
		HttpServletRequest request = cxt.getRequest();
		Object accessToken = request.getParameter("accessToken");
		if(accessToken == null){
			cxt.setSendZuulResponse(false);
			cxt.setResponseStatusCode(401);
			return null;
		}
		
		/**
		 * error 类型的过滤器
		 */
		Throwable throwable = cxt.getThrowable();
		logger.error("this is a ErrorFilter:{}", throwable.getCause().getMessage());
		cxt.set("error.status_code",HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		cxt.set("error.exception", throwable.getCause());
		return null;
	}

	@Override
	public boolean shouldFilter() {
		return true;
	}

	@Override
	/**
	 * 数字越小优先级越高
	 */
	public int filterOrder() {
		return 0;
	}

	/**
	 * pre：可以在请求被路由之前调用
	 * routing：在路由请求时被调用
	 * post：在routing和error过滤器之后被调用
	 * error： 处理请求时发生错误时被调用
	 */
	@Override
	public String filterType() {
		return "pre";
	}

}
