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
import com.zyx.service.infoquery.SysTranscfgService;
import com.zyx.util.Tools;
import com.zyx.web.WebController;
import com.zyx.service.system.DictionariesService;
/** 
 * 说明：
 * 创建人：kbky
 * 创建时间：
 */
@Controller("infoquerySysTranscfgController")
@RequestMapping(value="/infoquery")
public class SysTranscfgController extends WebController {
	
	@Autowired
	private SysTranscfgService infoquerySysTranscfgService;
	@Autowired
	private DictionariesService dictionariesService;
	
	/*
	 * 查询
	 */
	@RequestMapping("/systranscfgList")
	public String list(Map<String, Object> map) throws Exception{
		PageData pd = this.getPageData();
		int pageNum = Page.getPageNum(pd);
		int pageSize = Page.getPageSize(pd);
		List<Map<String, Object>> list = infoquerySysTranscfgService.listService(pageNum, pageSize, pd);
		PageInfo<Map<String, Object>> pageInfo = new PageInfo<Map<String, Object>>(list);
		map.put("page", pageInfo);
		map.put("systranscfgList", list);
		map.put("pd", pd);
		return "/infoquery/systranscfg/systranscfg_list";
	}
	
	/*
	 * 保存
	 */
	@RequestMapping("/systranscfgSave")
	@ResponseBody
	public String save(Map<String, Object> map) throws Exception{
		return infoquerySysTranscfgService.saveService(getPageData());
	}
	
	/*
	 * 修改
	 */
	@RequestMapping("/systranscfgUpdate")
	@ResponseBody
	public String update(Map<String, Object> map) throws Exception{
		return infoquerySysTranscfgService.updateService(getPageData());
	}
	
	/*
	 * 删除
	 */
	@RequestMapping("/systranscfgDel")
	@ResponseBody
	public String del(Map<String, Object> map) throws Exception{
		return infoquerySysTranscfgService.delService(getPageData());
	}
	
	/*
	 * 跳转到新增页面
	 */
	@RequestMapping("/systranscfgAdd")
	public String add(Map<String, Object> map) throws Exception{
		PageData pd = getPageData();
		pd.put("TABLE_NAME", "BON_SYS_TRANSCFG_TBL");
		pd.put("TABLE_COLUMN", "STATUS");
		map.put("statusList", dictionariesService.listDicService(pd));
		map.put("action", "Save");
		map.put("pd", pd);
		map.put("url", Tools.getStringValue(pd.get("url")));
		return "/infoquery/systranscfg/systranscfg_edit";
	}
	
	/*
	 * 跳转到修改页面
	 */
	@RequestMapping("/systranscfgEdit")
	public String edit(Map<String, Object> map) throws Exception{
		PageData pd = getPageData();
		Map<String, Object> result = infoquerySysTranscfgService.editService(pd);
		map.put("systranscfg", result);
		pd.put("TABLE_NAME", "BON_SYS_TRANSCFG_TBL");
		pd.put("TABLE_COLUMN", "STATUS");
		map.put("statusList", dictionariesService.listDicService(pd));
		map.put("action", "Update");
		map.put("pd", pd);
		map.put("url", Tools.getStringValue(pd.get("url")));
		return "/infoquery/systranscfg/systranscfg_edit";
	}
	
	/*
	 * 跳转到详情页面
	 */
	@RequestMapping("/systranscfgDetail")
	public String detail(Map<String, Object> map) throws Exception{
		PageData pd = getPageData();
		Map<String, Object> result = infoquerySysTranscfgService.editService(pd);
		map.put("systranscfg", result);
		map.put("pd", pd);
		map.put("url", Tools.getStringValue(pd.get("url")));
		return "/infoquery/systranscfg/systranscfg_detail";
	}
}
