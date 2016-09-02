package com.sinoiov.lhjh.file.service.common;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 文件类型验证
 * 
 * @author xiongyan
 * @date 2016年8月31日 下午12:37:50
 */
public final class FileTypeCheck {
	
	/**
	 * logger
	 */
	private static final Logger logger = LoggerFactory.getLogger(FileTypeCheck.class);

	/**
	 * 文件类型
	 */
	private static final Map<String, String> FILE_TYPE_MAP = new HashMap<String, String>();   
	
	/**
	 * 文件类型字节数
	 */
	private static final int TYPE_BYTE_COUNT = 50;
	 
	/**
	 * 16进制
	 */
    private static final int TYPE_VALUE = 0xFF;
    
    private FileTypeCheck() {}
    
    static {    
    	//初始化文件类型信息
        getAllFileType();      
    }    
        
    /**
     * 初始化文件类型
     */
    private static void getAllFileType() {    
    	FILE_TYPE_MAP.put("jpg", "ffd8ff"); //jpeg (jpg)    
        FILE_TYPE_MAP.put("png", "89504e47");  //png (png)    
        FILE_TYPE_MAP.put("gif", "47494638");  //gif (gif)    
    }    
    
	/**
	 * 获取文件类型
	 * 
	 * @param src
	 * @return
	 */
	public static String getFileType(byte[] src) {
		if (null == src || src.length <= 0) {
			logger.error("获取文件类型，参数不能为空");
			return null;
		}
		// 获取文件头
		String fileHex = bytesToHexString(src);
		if (StringUtils.isEmpty(fileHex)) {
			logger.error("获取文件头为空");
			return null;
		}
		
		Iterator<Entry<String, String>> entryiterator = FILE_TYPE_MAP.entrySet().iterator();
		while (entryiterator.hasNext()) {
			Entry<String, String> entry = entryiterator.next();
			if (fileHex.startsWith(entry.getValue())) {
				return entry.getKey();
			}
		}
		return null;
	} 
    
    
    /**
     * byte数组转换成16进制字符串
     * 
     * @param src
     * @return
     */
	public static String bytesToHexString(byte[] src) {
		if (src == null || src.length <= 0) {
			return null;
		}
		StringBuilder stringBuilder = new StringBuilder();
		int length = src.length > TYPE_BYTE_COUNT ? TYPE_BYTE_COUNT : src.length;
		for (int i = 0; i < length; i++) {
			int v = src[i] & TYPE_VALUE;
			String hv = Integer.toHexString(v);
			if (hv.length() < 2) {
				stringBuilder.append(0);
			}
			stringBuilder.append(hv);
		}
		return stringBuilder.toString();
	}
	
	public static void main(String[] args) throws Exception {
		File f = new File("e:/aaa.jpg");
		byte[] b = FileUtils.readFileToByteArray(f);
		System.out.println(getFileType(b));
	}
}
