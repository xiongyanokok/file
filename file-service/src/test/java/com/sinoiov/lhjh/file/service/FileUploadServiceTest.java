package com.sinoiov.lhjh.file.service;

import org.junit.Test;

import com.caucho.hessian.client.HessianProxyFactory;
import com.sinoiov.lhjh.file.intf.FileUploadService;

//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = { "classpath:spring-*.xml" })
public class FileUploadServiceTest {

	//@Autowired
	//private FileUploadService fileUploadService;
	
	@Test
	public void upload() throws Exception {
		String url = "http://localhost:20881/hessian/FileUploadService";
		HessianProxyFactory factory = new HessianProxyFactory();    
		FileUploadService service = (FileUploadService) factory.create(url);    
		
		String fileUrl = service.uploadFile(null, null);
		System.out.println(fileUrl);
	}
}