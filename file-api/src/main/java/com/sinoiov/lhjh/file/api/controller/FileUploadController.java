package com.sinoiov.lhjh.file.api.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.sinoiov.lhjh.file.api.common.ResponseDto;
import com.sinoiov.lhjh.file.intf.FileUploadService;

/**
 * 文件上传
 * 
 * @author xiongyan
 * @date 2016年8月25日 下午2:19:12
 */
@Controller
@RequestMapping(value = "/file")
public class FileUploadController {
	
	private static final Logger logger = LoggerFactory.getLogger(FileUploadController.class);
	
	@Autowired
	private FileUploadService fileUploadService;

	/**
	 * 上传
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/upload", method = {RequestMethod.POST})
    @ResponseBody
	public ResponseDto upload(MultipartHttpServletRequest request) {
		try {
			Map<String, MultipartFile> files = request.getFileMap();
			if (MapUtils.isNotEmpty(files)) {
				List<String> fileUrls = new ArrayList<String>(files.size());
				for (Map.Entry<String, MultipartFile> entry : files.entrySet()) {
					// 文件名
					String fileName = entry.getValue().getOriginalFilename();
					
					// 文件内容
					byte[] fileData = entry.getValue().getBytes();
					
					// 上传文件
					String fileUrl = fileUploadService.uploadFile(fileName, fileData);
					
					// 文件地址结果集
					fileUrls.add(fileUrl);
				}
				return ResponseDto.bulidSuccess(fileUrls);
			}
			return ResponseDto.bulidError("请选择要上传的文件！");
		} catch (Exception e) {
			logger.error("文件上传失败：", e);
			return ResponseDto.bulidError();
		}
	}
	
	/**
	 * 上传
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/big/upload", method = {RequestMethod.POST})
	@ResponseBody
	public ResponseDto upload(HttpServletRequest request) {
		try {
			ServletFileUpload fileUpload = new ServletFileUpload(new DiskFileItemFactory());
			// 这里就是中文文件名处理的代码，其实只有一行，setHeaderEncoding就可以了
			fileUpload.setHeaderEncoding("utf-8");
			// 解析上传文件数据包
			List<FileItem> fileItems = fileUpload.parseRequest(request);
			if (CollectionUtils.isNotEmpty(fileItems)) {
				List<String> fileUrls = new ArrayList<String>(fileItems.size());
				for (FileItem fileItem : fileItems) {
					// 文件名
					String fileName = fileItem.getName();
					
					// 文件内容
					byte[] fileData = fileItem.get();
					
					// 上传文件
					String fileUrl = fileUploadService.uploadFile(fileName, fileData);
					
					// 文件地址结果集
					fileUrls.add(fileUrl);
				}
				return ResponseDto.bulidSuccess(fileUrls);
			}
			return ResponseDto.bulidError("请选择要上传的文件！");
		} catch (Exception e) {
			logger.error("文件上传失败：", e);
			return ResponseDto.bulidError();
		}
	}
	
}