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
import com.zyx.service.infoquery.LimitChgService;
import com.zyx.util.Tools;
import com.zyx.web.WebController;
import com.zyx.service.system.DictionariesService;
/** 
 * 说明：
 * 创建人：kbky
 * 创建时间：
 */
@Controller("infoqueryLimitChgController")
@RequestMapping(value="/infoquery")
public class LimitChgController extends WebController {
	
	@Autowired
	private LimitChgService infoqueryLimitChgService;
	@Autowired
	private DictionariesService dictionariesService;
	
	/*
	 * 查询
	 */
	@RequestMapping("/limitchgList")
	public String list(Map<String, Object> map) throws Exception{
		PageData pd = this.getPageData();
		int pageNum = Page.getPageNum(pd);
		int pageSize = Page.getPageSize(pd);
		List<Map<String, Object>> list = infoqueryLimitChgService.listService(pageNum, pageSize, pd);
		PageInfo<Map<String, Object>> pageInfo = new PageInfo<Map<String, Object>>(list);
		map.put("page", pageInfo);
		map.put("limitchgList", list);
		map.put("pd", pd);
		return "/infoquery/limitchg/limitchg_list";
	}
	
	/*
	 * 保存
	 */
	@RequestMapping("/limitchgSave")
	@ResponseBody
	public String save(Map<String, Object> map) throws Exception{
		return infoqueryLimitChgService.saveService(getPageData());
	}
	
	/*
	 * 修改
	 */
	@RequestMapping("/limitchgUpdate")
	@ResponseBody
	public String update(Map<String, Object> map) throws Exception{
		return infoqueryLimitChgService.updateService(getPageData());
	}
	
	/*
	 * 删除
	 */
	@RequestMapping("/limitchgDel")
	@ResponseBody
	public String del(Map<String, Object> map) throws Exception{
		return infoqueryLimitChgService.delService(getPageData());
	}
	
	/*
	 * 跳转到新增页面
	 */
	@RequestMapping("/limitchgAdd")
	public String add(Map<String, Object> map) throws Exception{
		PageData pd = getPageData();
		pd.put("TABLE_NAME", "BON_LIMIT_CHG_TBL");
		pd.put("TABLE_COLUMN", "TYPE_CODE");
		map.put("typecodeList", dictionariesService.listDicService(pd));
		pd.put("TABLE_NAME", "BON_LIMIT_CHG_TBL");
		pd.put("TABLE_COLUMN", "LIMIT_TYPE");
		map.put("limittypeList", dictionariesService.listDicService(pd));
		pd.put("TABLE_NAME", "BON_LIMIT_CHG_TBL");
		pd.put("TABLE_COLUMN", "OTH_NBR");
		map.put("othnbrList", dictionariesService.listDicService(pd));
		pd.put("TABLE_NAME", "BON_LIMIT_CHG_TBL");
		pd.put("TABLE_COLUMN", "LIMIT_VAL");
		map.put("limitvalList", dictionariesService.listDicService(pd));
		map.put("action", "Save");
		map.put("pd", pd);
		map.put("url", Tools.getStringValue(pd.get("url")));
		return "/infoquery/limitchg/limitchg_edit";
	}
	
	/*
	 * 跳转到修改页面
	 */
	@RequestMapping("/limitchgEdit")
	public String edit(Map<String, Object> map) throws Exception{
		PageData pd = getPageData();
		Map<String, Object> result = infoqueryLimitChgService.editService(pd);
		map.put("limitchg", result);
		pd.put("TABLE_NAME", "BON_LIMIT_CHG_TBL");
		pd.put("TABLE_COLUMN", "TYPE_CODE");
		map.put("typecodeList", dictionariesService.listDicService(pd));
		pd.put("TABLE_NAME", "BON_LIMIT_CHG_TBL");
		pd.put("TABLE_COLUMN", "LIMIT_TYPE");
		map.put("limittypeList", dictionariesService.listDicService(pd));
		pd.put("TABLE_NAME", "BON_LIMIT_CHG_TBL");
		pd.put("TABLE_COLUMN", "OTH_NBR");
		map.put("othnbrList", dictionariesService.listDicService(pd));
		pd.put("TABLE_NAME", "BON_LIMIT_CHG_TBL");
		pd.put("TABLE_COLUMN", "LIMIT_VAL");
		map.put("limitvalList", dictionariesService.listDicService(pd));
		map.put("action", "Update");
		map.put("pd", pd);
		map.put("url", Tools.getStringValue(pd.get("url")));
		return "/infoquery/limitchg/limitchg_edit";
	}
	
	/*
	 * 跳转到详情页面
	 */
	@RequestMapping("/limitchgDetail")
	public String detail(Map<String, Object> map) throws Exception{
		PageData pd = getPageData();
		Map<String, Object> result = infoqueryLimitChgService.editService(pd);
		map.put("limitchg", result);
		map.put("pd", pd);
		map.put("url", Tools.getStringValue(pd.get("url")));
		return "/infoquery/limitchg/limitchg_detail";
	}
}
