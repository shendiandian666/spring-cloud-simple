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
import com.zyx.service.infoquery.LimitListService;
import com.zyx.util.Tools;
import com.zyx.web.WebController;
import com.zyx.service.system.DictionariesService;
/** 
 * 说明：
 * 创建人：kbky
 * 创建时间：
 */
@Controller("infoqueryLimitListController")
@RequestMapping(value="/infoquery")
public class LimitListController extends WebController {
	
	@Autowired
	private LimitListService infoqueryLimitListService;
	@Autowired
	private DictionariesService dictionariesService;
	
	/*
	 * 查询
	 */
	@RequestMapping("/limitlistList")
	public String list(Map<String, Object> map) throws Exception{
		PageData pd = this.getPageData();
		int pageNum = Page.getPageNum(pd);
		int pageSize = Page.getPageSize(pd);
		List<Map<String, Object>> list = infoqueryLimitListService.listService(pageNum, pageSize, pd);
		PageInfo<Map<String, Object>> pageInfo = new PageInfo<Map<String, Object>>(list);
		map.put("page", pageInfo);
		map.put("limitlistList", list);
		map.put("pd", pd);
		return "/infoquery/limitlist/limitlist_list";
	}
	
	/*
	 * 保存
	 */
	@RequestMapping("/limitlistSave")
	@ResponseBody
	public String save(Map<String, Object> map) throws Exception{
		return infoqueryLimitListService.saveService(getPageData());
	}
	
	/*
	 * 修改
	 */
	@RequestMapping("/limitlistUpdate")
	@ResponseBody
	public String update(Map<String, Object> map) throws Exception{
		return infoqueryLimitListService.updateService(getPageData());
	}
	
	/*
	 * 删除
	 */
	@RequestMapping("/limitlistDel")
	@ResponseBody
	public String del(Map<String, Object> map) throws Exception{
		return infoqueryLimitListService.delService(getPageData());
	}
	
	/*
	 * 跳转到新增页面
	 */
	@RequestMapping("/limitlistAdd")
	public String add(Map<String, Object> map) throws Exception{
		PageData pd = getPageData();
		pd.put("TABLE_NAME", "BON_LIMIT_LIST_TBL");
		pd.put("TABLE_COLUMN", "LIMIT_TYPE");
		map.put("limittypeList", dictionariesService.listDicService(pd));
		pd.put("TABLE_NAME", "BON_LIMIT_LIST_TBL");
		pd.put("TABLE_COLUMN", "LIMIT_LEVEL_VAL");
		map.put("limitlevelvalList", dictionariesService.listDicService(pd));
		pd.put("TABLE_NAME", "BON_LIMIT_LIST_TBL");
		pd.put("TABLE_COLUMN", "STATUS");
		map.put("statusList", dictionariesService.listDicService(pd));
		map.put("action", "Save");
		map.put("pd", pd);
		map.put("url", Tools.getStringValue(pd.get("url")));
		return "/infoquery/limitlist/limitlist_edit";
	}
	
	/*
	 * 跳转到修改页面
	 */
	@RequestMapping("/limitlistEdit")
	public String edit(Map<String, Object> map) throws Exception{
		PageData pd = getPageData();
		Map<String, Object> result = infoqueryLimitListService.editService(pd);
		map.put("limitlist", result);
		pd.put("TABLE_NAME", "BON_LIMIT_LIST_TBL");
		pd.put("TABLE_COLUMN", "LIMIT_TYPE");
		map.put("limittypeList", dictionariesService.listDicService(pd));
		pd.put("TABLE_NAME", "BON_LIMIT_LIST_TBL");
		pd.put("TABLE_COLUMN", "LIMIT_LEVEL_VAL");
		map.put("limitlevelvalList", dictionariesService.listDicService(pd));
		pd.put("TABLE_NAME", "BON_LIMIT_LIST_TBL");
		pd.put("TABLE_COLUMN", "STATUS");
		map.put("statusList", dictionariesService.listDicService(pd));
		map.put("action", "Update");
		map.put("pd", pd);
		map.put("url", Tools.getStringValue(pd.get("url")));
		return "/infoquery/limitlist/limitlist_edit";
	}
	
	/*
	 * 跳转到详情页面
	 */
	@RequestMapping("/limitlistDetail")
	public String detail(Map<String, Object> map) throws Exception{
		PageData pd = getPageData();
		Map<String, Object> result = infoqueryLimitListService.editService(pd);
		map.put("limitlist", result);
		map.put("pd", pd);
		map.put("url", Tools.getStringValue(pd.get("url")));
		return "/infoquery/limitlist/limitlist_detail";
	}
}
