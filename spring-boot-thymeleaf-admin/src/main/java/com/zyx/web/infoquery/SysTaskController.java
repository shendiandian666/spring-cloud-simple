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
import com.zyx.service.infoquery.SysTaskService;
import com.zyx.util.Tools;
import com.zyx.web.WebController;
import com.zyx.service.system.DictionariesService;
/** 
 * 说明：
 * 创建人：kbky
 * 创建时间：
 */
@Controller("infoquerySysTaskController")
@RequestMapping(value="/infoquery")
public class SysTaskController extends WebController {
	
	@Autowired
	private SysTaskService infoquerySysTaskService;
	@Autowired
	private DictionariesService dictionariesService;
	
	/*
	 * 查询
	 */
	@RequestMapping("/systaskList")
	public String list(Map<String, Object> map) throws Exception{
		PageData pd = this.getPageData();
		int pageNum = Page.getPageNum(pd);
		int pageSize = Page.getPageSize(pd);
		List<Map<String, Object>> list = infoquerySysTaskService.listService(pageNum, pageSize, pd);
		PageInfo<Map<String, Object>> pageInfo = new PageInfo<Map<String, Object>>(list);
		map.put("page", pageInfo);
		map.put("systaskList", list);
		map.put("pd", pd);
		return "/infoquery/systask/systask_list";
	}
	
	/*
	 * 保存
	 */
	@RequestMapping("/systaskSave")
	@ResponseBody
	public String save(Map<String, Object> map) throws Exception{
		return infoquerySysTaskService.saveService(getPageData());
	}
	
	/*
	 * 修改
	 */
	@RequestMapping("/systaskUpdate")
	@ResponseBody
	public String update(Map<String, Object> map) throws Exception{
		return infoquerySysTaskService.updateService(getPageData());
	}
	
	/*
	 * 删除
	 */
	@RequestMapping("/systaskDel")
	@ResponseBody
	public String del(Map<String, Object> map) throws Exception{
		return infoquerySysTaskService.delService(getPageData());
	}
	
	/*
	 * 跳转到新增页面
	 */
	@RequestMapping("/systaskAdd")
	public String add(Map<String, Object> map) throws Exception{
		PageData pd = getPageData();
		pd.put("TABLE_NAME", "BON_SYS_TASK_TBL");
		pd.put("TABLE_COLUMN", "STATUS");
		map.put("statusList", dictionariesService.listDicService(pd));
		map.put("action", "Save");
		map.put("pd", pd);
		map.put("url", Tools.getStringValue(pd.get("url")));
		return "/infoquery/systask/systask_edit";
	}
	
	/*
	 * 跳转到修改页面
	 */
	@RequestMapping("/systaskEdit")
	public String edit(Map<String, Object> map) throws Exception{
		PageData pd = getPageData();
		Map<String, Object> result = infoquerySysTaskService.editService(pd);
		map.put("systask", result);
		pd.put("TABLE_NAME", "BON_SYS_TASK_TBL");
		pd.put("TABLE_COLUMN", "STATUS");
		map.put("statusList", dictionariesService.listDicService(pd));
		map.put("action", "Update");
		map.put("pd", pd);
		map.put("url", Tools.getStringValue(pd.get("url")));
		return "/infoquery/systask/systask_edit";
	}
	
	/*
	 * 跳转到详情页面
	 */
	@RequestMapping("/systaskDetail")
	public String detail(Map<String, Object> map) throws Exception{
		PageData pd = getPageData();
		Map<String, Object> result = infoquerySysTaskService.editService(pd);
		map.put("systask", result);
		map.put("pd", pd);
		map.put("url", Tools.getStringValue(pd.get("url")));
		return "/infoquery/systask/systask_detail";
	}
}
