package com.zyx.web.http;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.zyx.service.wp.UploadService;
import com.zyx.web.WebController;

@RestController
public class HttpUploadController extends WebController {

	@Autowired
	private UploadService uploadService;
	
	@RequestMapping("/uploadImg")
	public boolean save() throws Exception {
		return uploadService.upload(getPageData());
	}
	
}
