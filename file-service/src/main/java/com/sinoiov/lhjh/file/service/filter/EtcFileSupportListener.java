package com.sinoiov.lhjh.file.service.filter;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

/**
 * 为SOA配置文件提供外部加载支持
 * 
 * @author Lei
 */
public class EtcFileSupportListener implements ServletContextListener {

	private Logger logger = Logger.getLogger(EtcFileSupportListener.class);

	public void contextInitialized(ServletContextEvent evt) {

		File etcFileSupportFile = new File(System.getProperty("catalina.home") + "/etc/fileService/");
		
		logger.debug("etcFileSupportFile:" + etcFileSupportFile.getPath());

		try {
			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			Method addUrlMethod = URLClassLoader.class.getDeclaredMethod("addURL", URL.class);

			if (addUrlMethod != null) {

				addUrlMethod.setAccessible(true);
				addUrlMethod.invoke(classLoader, etcFileSupportFile.toURI().toURL());
			}
		} catch (Throwable e) {
			logger.error("在添加/etc/fileService目录至Classpath时出现异常", e);
		}
	}

	public void contextDestroyed(ServletContextEvent evt) {
	}
}