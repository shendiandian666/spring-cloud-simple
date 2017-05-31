package com.zyx.web.http;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.zyx.service.wp.LocationService;
import com.zyx.web.WebController;

@RestController
public class HttpLocationController extends WebController {

	@Autowired
	private LocationService locationService;
	
	@RequestMapping("/locationSave")
	public boolean save() throws Exception {
		return locationService.save(getPageData());
	}
	
	@RequestMapping("/getMarkers")
	public List<Map<String, Object>> getMarkers() throws Exception{
		return locationService.getMarkers();
	}
	
}
