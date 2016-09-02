package com.sinoiov.lhjh.file.intf;

/**
 * 文件上传接口
 * 
 * @author xiongyan
 * @date 2016年8月25日 下午2:21:47
 */
public interface FileUploadService {

	/**
	 * 上传文件
	 * 
	 * @param fileName  文件名称
	 * @param fileData  文件字节数组
	 * @return
	 */
	public String uploadFile(String fileName, byte[] fileData);
	
}
