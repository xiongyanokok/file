package com.sinoiov.lhjh.file.api.common;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import com.sinoiov.cwza.common.utils.JsonUtilAlibaba;

/**
 * 输出结果工具类
 * 
 * @author xiongyan
 * @date 2016年8月26日 下午1:34:37
 */
public class PrintWriterUtil {

	/**
	 * 输出消息
	 * @param response
	 * @param text
	 */
	public static void printWriter(HttpServletResponse response, Object obj) {
		if (null != response) {
			try {
				// 设置响应头
				response.setContentType("application/json"); // 指定内容类型为 JSON 格式
				response.setCharacterEncoding("UTF-8"); // 防止中文乱码
				// 响应内容
				PrintWriter writer = response.getWriter();
				writer.write(JsonUtilAlibaba.getJsonString(obj));
				writer.flush();
				writer.close();
			} catch (Exception ex) {
			}
		}
	}

}
