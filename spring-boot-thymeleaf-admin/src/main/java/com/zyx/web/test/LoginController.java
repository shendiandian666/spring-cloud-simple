package com.zyx.web.test;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.github.pagehelper.PageInfo;
import com.zyx.model.PageData;
import com.zyx.service.test.LoginService;
import com.zyx.util.Tools;
import com.zyx.web.WebController;
import com.zyx.service.system.DictionariesService;
/** 
 * 说明：
 * 创建人：kbky
 * 创建时间：
 */
@Controller("testLoginController")
@RequestMapping(value="/test")
public class LoginController extends WebController {
	
	@Autowired
	private LoginService testLoginService;
	@Autowired
	private DictionariesService dictionariesService;
	
	@RequestMapping("/loginList")
	public String list(Map<String, Object> map) throws Exception{
		PageData pd = this.getPageData();
		int pageNum = Tools.getIntValue(pd, "pageNum");
		int pageSize = Tools.getIntValue(pd, "pageSize");
		List<Map<String, Object>> list = testLoginService.listService(pageNum, pageSize, pd);
		PageInfo<Map<String, Object>> pageInfo = new PageInfo<Map<String, Object>>(list);
		map.put("page", pageInfo);
		map.put("loginList", list);
		map.put("pd", pd);
		return "/test/login/login_list";
	}
	
	@RequestMapping("/loginSave")
	@ResponseBody
	public String save(Map<String, Object> map) throws Exception{
		return testLoginService.saveService(getPageData());
	}
	
	@RequestMapping("/loginUpdate")
	@ResponseBody
	public String update(Map<String, Object> map) throws Exception{
		return testLoginService.updateService(getPageData());
	}
	
	@RequestMapping("/loginDel")
	@ResponseBody
	public String del(Map<String, Object> map) throws Exception{
		return testLoginService.delService(getPageData());
	}
	
	@RequestMapping("/loginAdd")
	public String add(Map<String, Object> map) throws Exception{
		PageData pd = getPageData();
		pd.put("TABLE_NAME", "JT_USER");
		pd.put("TABLE_COLUMN", "STATUS");
		map.put("statusList", dictionariesService.listDicService(pd));
		map.put("action", "Save");
		map.put("pd", pd);
		map.put("url", Tools.getStringValue(pd.get("url")));
		return "/test/login/login_edit";
	}
	
	@RequestMapping("/loginEdit")
	public String edit(Map<String, Object> map) throws Exception{
		PageData pd = getPageData();
		Map<String, Object> result = testLoginService.editService(pd);
		map.put("login", result);
		pd.put("TABLE_NAME", "JT_USER");
		pd.put("TABLE_COLUMN", "STATUS");
		map.put("statusList", dictionariesService.listDicService(pd));
		map.put("action", "Update");
		map.put("pd", pd);
		map.put("url", Tools.getStringValue(pd.get("url")));
		return "/test/login/login_edit";
	}
}
