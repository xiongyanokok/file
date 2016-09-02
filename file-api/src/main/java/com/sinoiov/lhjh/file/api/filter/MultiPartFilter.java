package com.sinoiov.lhjh.file.api.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.GenericFilterBean;

import com.sinoiov.lhjh.file.api.common.PrintWriterUtil;
import com.sinoiov.lhjh.file.api.common.ResponseDto;

/**
 * 自定义文件上传过滤器
 * 
 * @author xiongyan
 * @date 2016年8月26日 上午11:07:05
 */
public class MultiPartFilter extends GenericFilterBean {
	
	private static final Logger logger = LoggerFactory.getLogger(MultiPartFilter.class);
	
	// 允许上传的最大文件是10M
	private static final Integer maxUploadSize = 1024 * 1024 * 10; 

	/**
	 * 如果是大文件上传，把request重新进行包装
	 */
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
			FilterChain filterChain) throws IOException, ServletException {
		
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;

		// 获取ContentType
		String contentType = request.getContentType();
		if (null != contentType && contentType.indexOf("multipart/form-data") != -1) {
			// 获取请求内容大小
			int contentLength = request.getContentLength();
			if (contentLength > maxUploadSize) {
				logger.error("上传文件太大！");
				PrintWriterUtil.printWriter(response, ResponseDto.bulidError("上传文件太大！"));
				return;
			}
			
			MultiPartRequest multiPartRequest = new MultiPartRequest(request);
			request = multiPartRequest;
		}
		
		filterChain.doFilter(request, response); 
	}

}
