package com.zyx.service.wp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zyx.model.PageData;
import com.zyx.service.common.CommonService;

@Service
public class UploadService {

	@Autowired
	private UserService userService;
	@Autowired
	private LocationService locationService;
	@Autowired
	private LocationImgService locationImgService;
	@Autowired
	private CommonService commonService;
	
	public boolean upload(PageData pd) throws Exception{
		
		String sequence = commonService.sequence("SEQ_LOCATION_ID");
		userService.save(pd);
		pd.put("LOCATION_ID", sequence);
		locationService.save(pd);
		locationImgService.save(pd);
		
		return true;
	}
	
}
