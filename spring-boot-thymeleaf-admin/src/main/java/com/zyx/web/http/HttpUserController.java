package com.zyx.web.http;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.zyx.service.wp.UserService;
import com.zyx.web.WebController;

@RestController
public class HttpUserController extends WebController {

	@Autowired
	private UserService userService;
	
	@RequestMapping("/httpUserSave")
	public boolean save() throws Exception {
		return userService.save(getPageData());
	}
	
}
