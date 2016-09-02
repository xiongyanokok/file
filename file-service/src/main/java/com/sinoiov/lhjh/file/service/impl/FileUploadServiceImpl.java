package com.sinoiov.lhjh.file.service.impl;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.sinoiov.lhjh.file.intf.FileUploadService;
import com.sinoiov.lhjh.file.service.common.FileTypeCheck;

/**
 * 文件上传服务
 * 
 * @author xiongyan
 * @date 2016年8月25日 下午2:21:47
 */
@Service
public class FileUploadServiceImpl implements FileUploadService {

	private static final Logger logger = LoggerFactory.getLogger(FileUploadServiceImpl.class);
	
	/**
	 * 上传文件
	 * 
	 * @param fileName  文件名称
	 * @param fileData  文件字节数组
	 * @return
	 */
	public String uploadFile(String fileName, byte[] fileData) {
		if (StringUtils.isEmpty(fileName) || null == fileData) {
			return null;
		}
		try {
			// 验证文件类型是否安全
			String fileType = FileTypeCheck.getFileType(fileData);
			if (null == fileType || !fileName.toLowerCase().endsWith(fileType)) {
				logger.error("验证文件类型失败，文件名【{}】 ,文件类型【{}】", fileName, fileType);
				return null;
			}
			
			File file = new File("e:/"+fileName);
			FileUtils.writeByteArrayToFile(file, fileData);
			return file.getPath();
		} catch (Exception e) {
			logger.error("文件上传失败：", e);
			return null;
		}
	}
}
