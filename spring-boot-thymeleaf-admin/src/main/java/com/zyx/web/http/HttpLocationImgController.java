package com.zyx.web.http;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zyx.service.wp.LocationImgService;
import com.zyx.web.WebController;

@RestController
public class HttpLocationImgController extends WebController {

	@Autowired
	private LocationImgService locationImgService;
	
	@RequestMapping("/locationImgSave")
	public boolean save() throws Exception {
		return locationImgService.save(getPageData());
	}
	
	@RequestMapping("/getImgPath")
	public List<Map<String, Object>> getImgPath() throws Exception{
		return locationImgService.getImgPath(getPageData());
	}
	
}
