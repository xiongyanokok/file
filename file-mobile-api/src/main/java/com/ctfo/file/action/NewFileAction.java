package com.ctfo.file.action;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import com.ctfo.file.sys.DES;
import com.ctfo.file.sys.EnvironmentUtil;

/**
 * 文件下载
 *
 */
@Controller
@Scope("prototype")
@RequestMapping("/filesupport")
public class NewFileAction {
	private static final Logger log = Logger.getLogger(NewFileAction.class);

	/**
	 * 上传文件
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "uploadFile", method = RequestMethod.POST)
	@ResponseBody
	public String uploadFile(HttpServletRequest request,
			HttpServletResponse response) {
		MultipartHttpServletRequest mhr = (MultipartHttpServletRequest) request;
		// 初始化文件写入目录
		String dateDir = getCurrentDir();
		String fileWirteDir = EnvironmentUtil.getInstance().getPropertyValue("newfileupload.dir");
		fileWirteDir += File.separator + dateDir;
		log.info("fileWirteDir is  " + fileWirteDir);
		// 不存在创建目录
		File file = new File(fileWirteDir);
		if (file.exists() == false) {
			file.mkdirs();
			log.info("has create dir " + fileWirteDir);
		}
		// 获取保存文件的扩展名
		String suffix = mhr.getParameter("suffix");
		if (suffix == null || suffix.trim().length() == 0) {
			suffix = "txt";
		}
		log.info("suffix is  " + suffix);

		// 文件名称以时间戳命名
		String fileName = System.currentTimeMillis() + "";
		log.info("fileName is  " + fileName);

		// 取出图片文件流
		MultipartFile mpf = mhr.getFile("file");

		String fullWritePath = fileWirteDir + File.separator + fileName;
		String writePath = dateDir + File.separator + fileName;
		log.info("fullWritePath  is " + fullWritePath);
		try {
			InputStream fis = mpf.getInputStream();

			FileOutputStream fos = new FileOutputStream(File.separator
					+ fullWritePath + "." + suffix);
			byte[] bs = new byte[1024];
			int amount = fis.read(bs);
			while (amount > 0) {
				fos.write(bs, 0, amount);
				amount = fis.read(bs);
			}
			fis.close();
			fos.close();
			String url = this.getDownloadUrl(request, writePath + "." + suffix);
			return url;

		} catch (Exception e) {
			e.printStackTrace();
			log.error("deal with upload failed!" + fullWritePath, e);
			return "error";
		}

	}

	/**
	 * 根据路径获取下载路径
	 * 
	 * @param request
	 * @param filePath
	 * @return
	 */
	private String getDownloadUrl(HttpServletRequest request, String filePath) {
		String downLoadPath = DES.encrypt(filePath);
		log.info("download path: " + (filePath) + "-->" + downLoadPath);
		String baseUrl = EnvironmentUtil.getInstance().getPropertyValue("downloadFile.url");
		String url = "";
		if (baseUrl != null) {
			url = baseUrl + "?p=" + downLoadPath;
		} else {
			url = request.getRequestURL().toString();
			url = url.replaceAll("uploadFile.do", "downloadFile.do?p="
					+ downLoadPath);
		}
		log.info("url is " + url);
		return url;
	}

	public String getCurrentDir() {
		SimpleDateFormat dirFormat = new SimpleDateFormat("yyyy"
				+ File.separator + "MM" + File.separator + "dd");
		return dirFormat.format(new Date());
	}

	/**
	 * 保存文件
	 * 
	 * @param mpf
	 * @param fullWritePath
	 * @return
	 */
	private boolean processMutilFile(MultipartFile mpf, String fullWritePath) {
		try {
			InputStream fis = mpf.getInputStream();
			FileOutputStream fos = new FileOutputStream(fullWritePath);
			byte[] bs = new byte[1024];
			int amount = fis.read(bs);
			while (amount > 0) {
				fos.write(bs, 0, amount);
				amount = fis.read(bs);
			}
			fis.close();
			fos.close();

		} catch (IOException e) {
			e.printStackTrace();
			log.error("deal with upload failed!" + fullWritePath, e);
			return false;
		}
		return true;
	}

	/**
	 * 下载相关文件
	 * 
	 * @return
	 */

	@RequestMapping("downloadFile")
	public void download(HttpServletRequest req, HttpServletResponse res)
			throws IOException {
		OutputStream os = res.getOutputStream();
		String path = req.getParameter("p");
		if (path == null || path.trim().length() == 0) {
			log.info("parameter is null " + path);
			return;
		}
		String realPath = DES.decrypt(path);
		log.info("path is " + path + "-->" + realPath);
		String fileWirteDir = EnvironmentUtil.getInstance().getPropertyValue("newfileupload.dir");

		String fullPath = fileWirteDir + File.separator + realPath;

		String type[] = realPath.split("\\.");
		String downloadPath = fullPath;
		File df;
		df = new File(downloadPath);
		if (df.exists()) {
			log.info("will be download " + downloadPath);
		} else {
			log.info("file is not exits " + downloadPath);
			return;
		}
		try {
			res.reset();
			if (type[1].equals("txt")) {
				res.setContentType("text/plain");
			} else if (type[1].equals("mp3")) {
				res.setContentType("audio/mp3");
			} else if (type[1].equals("wav")) {
				res.setContentType("audio/wav");
			} else if (type[1].equals("wma")) {
				res.setContentType("audio/x-ms-wma");
			}
			os.write(FileUtils.readFileToByteArray(df));
			os.flush();
		} finally {
			if (os != null) {
				os.close();
			}
		}
	}

}
