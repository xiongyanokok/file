package com.ctfo.file.filter;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * 为SOA配置文件提供外部加载支持
 * 
 * @author Lei
 */
public class EtcFileSupportListener implements ServletContextListener {

	public void contextInitialized(ServletContextEvent evt) {
		String tomcatHome = System.getProperty("catalina.home");
		String sp = File.separator;
		System.out.println("tomcat home is " + tomcatHome);
		File etcFileSupportFile = new File(tomcatHome + sp + "etc" + sp + "file-mobile-api" + sp);

		System.out.println("etcFileSupportFile:" + etcFileSupportFile.getPath());

		try {
			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			Method addUrlMethod = URLClassLoader.class.getDeclaredMethod("addURL", URL.class);

			if (addUrlMethod != null) {

				addUrlMethod.setAccessible(true);
				addUrlMethod.invoke(classLoader, etcFileSupportFile.toURI().toURL());
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	public void contextDestroyed(ServletContextEvent evt) {
	}
}
