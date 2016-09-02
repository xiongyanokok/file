package com.ctfo.file.exception;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;


/**
 * WEB层全局Exception错误处理器
 *
 */
public class WebGloabException implements HandlerExceptionResolver{  
    private static Logger logger = Logger.getLogger(WebGloabException.class);  
    //@Override  
    public ModelAndView resolveException(HttpServletRequest request,  
            HttpServletResponse response, Object handler, Exception ex) {  
        logger.error("spring mvc 拦截的错误 : ",ex);
        //Map<String,Object> map = new HashMap<String,Object>();  
        //StringPrintWriter strintPrintWriter = new StringPrintWriter();  
        //ex.printStackTrace(strintPrintWriter);
        String path = request.getServletPath();
        if(path!=null && path.indexOf("uploadImg.do")!=-1){
        	return new ModelAndView("redirect:newImgError.do");
        }else{
        	return new ModelAndView("redirect:imgError.do");  
        }
    }  
    
    class StringPrintWriter extends PrintWriter{  
    	  
        public StringPrintWriter(){  
            super(new StringWriter());  
        }  
         
        public StringPrintWriter(int initialSize) {  
              super(new StringWriter(initialSize));  
        }  
         
        public String getString() {  
              flush();  
              return ((StringWriter) this.out).toString();  
        }  
         
        //@Override  
        public String toString() {  
            return getString();  
        } 
    }
}
  
