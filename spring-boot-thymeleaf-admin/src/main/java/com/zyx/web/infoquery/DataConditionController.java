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
import com.zyx.service.infoquery.DataConditionService;
import com.zyx.service.system.DictionariesService;
import com.zyx.util.Tools;
import com.zyx.web.WebController;
/** 
 * 说明：
 * 创建人：kbky
 * 创建时间：
 */
@Controller("infoqueryDataConditionController")
@RequestMapping(value="/infoquery")
public class DataConditionController extends WebController {
	
	@Autowired
	private DataConditionService infoqueryDataConditionService;
	@Autowired
	private DictionariesService dictionariesService;
	
	/*
	 * 查询
	 */
	@RequestMapping("/dataconditionList")
	public String list(Map<String, Object> map) throws Exception{
		PageData pd = this.getPageData();
		int pageNum = Page.getPageNum(pd);
		int pageSize = Page.getPageSize(pd);
		List<Map<String, Object>> list = infoqueryDataConditionService.listService(pageNum, pageSize, pd);
		PageInfo<Map<String, Object>> pageInfo = new PageInfo<Map<String, Object>>(list);
		map.put("page", pageInfo);
		map.put("dataconditionList", list);
		map.put("pd", pd);
		return "/infoquery/datacondition/datacondition_list";
	}
	
	/*
	 * 保存
	 */
	@RequestMapping("/dataconditionSave")
	@ResponseBody
	public String save(Map<String, Object> map) throws Exception{
		return infoqueryDataConditionService.saveService(getPageData());
	}
	
	/*
	 * 修改
	 */
	@RequestMapping("/dataconditionUpdate")
	@ResponseBody
	public String update(Map<String, Object> map) throws Exception{
		return infoqueryDataConditionService.updateService(getPageData());
	}
	
	/*
	 * 删除
	 */
	@RequestMapping("/dataconditionDel")
	@ResponseBody
	public String del(Map<String, Object> map) throws Exception{
		return infoqueryDataConditionService.delService(getPageData());
	}
	
	/*
	 * 跳转到新增页面
	 */
	@RequestMapping("/dataconditionAdd")
	public String add(Map<String, Object> map) throws Exception{
		PageData pd = getPageData();
		pd.put("TABLE_NAME", "BON_DATA_CONDITION_TBL");
		pd.put("TABLE_COLUMN", "CONDITION_CODE");
		map.put("conditioncodeList", dictionariesService.listDicService(pd));
		map.put("action", "Save");
		map.put("pd", pd);
		map.put("url", Tools.getStringValue(pd.get("url")));
		return "/infoquery/datacondition/datacondition_edit";
	}
	
	/*
	 * 跳转到修改页面
	 */
	@RequestMapping("/dataconditionEdit")
	public String edit(Map<String, Object> map) throws Exception{
		PageData pd = getPageData();
		Map<String, Object> result = infoqueryDataConditionService.editService(pd);
		map.put("datacondition", result);
		pd.put("TABLE_NAME", "BON_DATA_CONDITION_TBL");
		pd.put("TABLE_COLUMN", "CONDITION_CODE");
		map.put("conditioncodeList", dictionariesService.listDicService(pd));
		map.put("action", "Update");
		map.put("pd", pd);
		map.put("url", Tools.getStringValue(pd.get("url")));
		return "/infoquery/datacondition/datacondition_edit";
	}
	
	/*
	 * 跳转到详情页面
	 */
	@RequestMapping("/dataconditionDetail")
	public String detail(Map<String, Object> map) throws Exception{
		PageData pd = getPageData();
		Map<String, Object> result = infoqueryDataConditionService.editService(pd);
		map.put("datacondition", result);
		map.put("pd", pd);
		map.put("url", Tools.getStringValue(pd.get("url")));
		return "/infoquery/datacondition/datacondition_detail";
	}
}
