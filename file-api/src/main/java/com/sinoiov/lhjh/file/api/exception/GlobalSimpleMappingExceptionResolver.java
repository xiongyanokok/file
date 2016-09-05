package com.sinoiov.lhjh.file.api.exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import com.sinoiov.lhjh.file.api.common.PrintWriterUtil;
import com.sinoiov.lhjh.file.api.common.ResponseDto;

/**
 * 全局异常处理
 * 
 * @author xiongyan
 * @date 2015年12月18日 下午6:12:56
 */
public class GlobalSimpleMappingExceptionResolver extends SimpleMappingExceptionResolver {
	
	private static final Logger logger = LoggerFactory.getLogger(GlobalSimpleMappingExceptionResolver.class);
	
	/**
	 * 异常处理
	 */
	protected ModelAndView doResolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception e) {
		// 错误日记记录
		logger.error("全局异常信息：", e);
		
		// 输出消息
		PrintWriterUtil.printWriter(response, ResponseDto.bulidError("系统繁忙，请稍后重试！"));
		return null;
	}
	
}