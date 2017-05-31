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
import com.zyx.service.infoquery.BasicStartService;
import com.zyx.util.Tools;
import com.zyx.web.WebController;
import com.zyx.service.system.DictionariesService;
/** 
 * 说明：
 * 创建人：kbky
 * 创建时间：
 */
@Controller("infoqueryBasicStartController")
@RequestMapping(value="/infoquery")
public class BasicStartController extends WebController {
	
	@Autowired
	private BasicStartService infoqueryBasicStartService;
	@Autowired
	private DictionariesService dictionariesService;
	
	/*
	 * 启动
	 */
	@RequestMapping("/basicstartStart")
	@ResponseBody
	public String start(Map<String, Object> map) throws Exception{
		return infoqueryBasicStartService.startService(getPageData());
	}
	
	/*
	 * 停止
	 */
	@RequestMapping("/basicstartStop")
	@ResponseBody
	public String stop(Map<String, Object> map) throws Exception{
		return infoqueryBasicStartService.stopService(getPageData());
	}
	
	/*
	 * 查询
	 */
	@RequestMapping("/basicstartList")
	public String list(Map<String, Object> map) throws Exception{
		PageData pd = this.getPageData();
		int pageNum = Page.getPageNum(pd);
		int pageSize = Page.getPageSize(pd);
		List<Map<String, Object>> list = infoqueryBasicStartService.listService(pageNum, pageSize, pd);
		PageInfo<Map<String, Object>> pageInfo = new PageInfo<Map<String, Object>>(list);
		map.put("page", pageInfo);
		map.put("basicstartList", list);
		map.put("pd", pd);
		return "/infoquery/basicstart/basicstart_list";
	}
	
	/*
	 * 保存
	 */
	@RequestMapping("/basicstartSave")
	@ResponseBody
	public String save(Map<String, Object> map) throws Exception{
		return infoqueryBasicStartService.saveService(getPageData());
	}
	
	/*
	 * 修改
	 */
	@RequestMapping("/basicstartUpdate")
	@ResponseBody
	public String update(Map<String, Object> map) throws Exception{
		return infoqueryBasicStartService.updateService(getPageData());
	}
	
	/*
	 * 删除
	 */
	@RequestMapping("/basicstartDel")
	@ResponseBody
	public String del(Map<String, Object> map) throws Exception{
		return infoqueryBasicStartService.delService(getPageData());
	}
	
	/*
	 * 跳转到新增页面
	 */
	@RequestMapping("/basicstartAdd")
	public String add(Map<String, Object> map) throws Exception{
		PageData pd = getPageData();
		pd.put("TABLE_NAME", "BON_BASIC_START_TBL");
		pd.put("TABLE_COLUMN", "STATUS");
		map.put("statusList", dictionariesService.listDicService(pd));
		map.put("action", "Save");
		map.put("pd", pd);
		map.put("url", Tools.getStringValue(pd.get("url")));
		return "/infoquery/basicstart/basicstart_edit";
	}
	
	/*
	 * 跳转到修改页面
	 */
	@RequestMapping("/basicstartEdit")
	public String edit(Map<String, Object> map) throws Exception{
		PageData pd = getPageData();
		Map<String, Object> result = infoqueryBasicStartService.editService(pd);
		map.put("basicstart", result);
		pd.put("TABLE_NAME", "BON_BASIC_START_TBL");
		pd.put("TABLE_COLUMN", "STATUS");
		map.put("statusList", dictionariesService.listDicService(pd));
		map.put("action", "Update");
		map.put("pd", pd);
		map.put("url", Tools.getStringValue(pd.get("url")));
		return "/infoquery/basicstart/basicstart_edit";
	}
	
	/*
	 * 跳转到详情页面
	 */
	@RequestMapping("/basicstartDetail")
	public String detail(Map<String, Object> map) throws Exception{
		PageData pd = getPageData();
		Map<String, Object> result = infoqueryBasicStartService.editService(pd);
		map.put("basicstart", result);
		map.put("pd", pd);
		map.put("url", Tools.getStringValue(pd.get("url")));
		return "/infoquery/basicstart/basicstart_detail";
	}
}
