package com.sinoiov.lhjh.file.api.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * HttpRequest 增强类
 * 
 * @author xiongyan
 * @date 2016年8月26日 上午11:13:26
 */
public class MultiPartRequest extends HttpServletRequestWrapper {

	public MultiPartRequest(HttpServletRequest request) {
		super(request);
	}
	
}