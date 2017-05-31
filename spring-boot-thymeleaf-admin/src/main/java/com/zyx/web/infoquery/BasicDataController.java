package com.zyx.web.infoquery;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.github.pagehelper.PageInfo;
import com.zyx.model.Page;
import com.zyx.model.PageData;
import com.zyx.service.infoquery.BasicDataService;
import com.zyx.util.Tools;
import com.zyx.web.WebController;
/** 
 * 说明：
 * 创建人：kbky
 * 创建时间：
 */
@Controller("infoqueryBasicDataController")
@RequestMapping(value="/infoquery")
public class BasicDataController extends WebController {
	
	@Autowired
	private BasicDataService infoqueryBasicDataService;
	
	/*
	 * 查询
	 */
	@RequestMapping("/basicdataList")
	public String list(Map<String, Object> map) throws Exception{
		PageData pd = this.getPageData();
		int pageNum = Page.getPageNum(pd);
		int pageSize = Page.getPageSize(pd);
		List<Map<String, Object>> list = infoqueryBasicDataService.listService(pageNum, pageSize, pd);
		PageInfo<Map<String, Object>> pageInfo = new PageInfo<Map<String, Object>>(list);
		map.put("page", pageInfo);
		map.put("basicdataList", list);
		map.put("pd", pd);
		return "/infoquery/basicdata/basicdata_list";
	}
	
	/*
	 * 保存
	 */
	@RequestMapping("/basicdataSave")
	@ResponseBody
	public String save(Map<String, Object> map) throws Exception{
		return infoqueryBasicDataService.saveService(getPageData());
	}
	
	/*
	 * 修改
	 */
	@RequestMapping("/basicdataUpdate")
	@ResponseBody
	public String update(Map<String, Object> map) throws Exception{
		return infoqueryBasicDataService.updateService(getPageData());
	}
	
	/*
	 * 删除
	 */
	@RequestMapping("/basicdataDel")
	@ResponseBody
	public String del(Map<String, Object> map) throws Exception{
		return infoqueryBasicDataService.delService(getPageData());
	}
	
	/*
	 * 跳转到新增页面
	 */
	@RequestMapping("/basicdataAdd")
	public String add(Map<String, Object> map) throws Exception{
		PageData pd = getPageData();
		map.put("action", "Save");
		map.put("pd", pd);
		map.put("url", Tools.getStringValue(pd.get("url")));
		return "/infoquery/basicdata/basicdata_edit";
	}
	
	/*
	 * 跳转到修改页面
	 */
	@RequestMapping("/basicdataEdit")
	public String edit(Map<String, Object> map) throws Exception{
		PageData pd = getPageData();
		Map<String, Object> result = infoqueryBasicDataService.editService(pd);
		map.put("basicdata", result);
		map.put("action", "Update");
		map.put("pd", pd);
		map.put("url", Tools.getStringValue(pd.get("url")));
		return "/infoquery/basicdata/basicdata_edit";
	}
	
	/*
	 * 跳转到详情页面
	 */
	@RequestMapping("/basicdataDetail")
	public String detail(Map<String, Object> map) throws Exception{
		PageData pd = getPageData();
		Map<String, Object> result = infoqueryBasicDataService.editService(pd);
		map.put("basicdata", result);
		map.put("pd", pd);
		map.put("url", Tools.getStringValue(pd.get("url")));
		return "/infoquery/basicdata/basicdata_detail";
	}
}
