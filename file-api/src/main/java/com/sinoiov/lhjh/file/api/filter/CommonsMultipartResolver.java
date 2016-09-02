package com.sinoiov.lhjh.file.api.filter;

import javax.servlet.http.HttpServletRequest;

/**
 * 重写CommonsMultipartResolver类的isMultipart方法
 * 
 * @author xiongyan
 * @date 2016年8月26日 上午11:10:39
 */
public class CommonsMultipartResolver extends org.springframework.web.multipart.commons.CommonsMultipartResolver {

	public boolean isMultipart(HttpServletRequest request) {  
        if (request instanceof MultiPartRequest) {  
            return false;  
        } else {  
            return super.isMultipart(request);  
        }  
    } 
}
