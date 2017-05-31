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
import com.zyx.service.infoquery.BasicRuleItemService;
import com.zyx.util.Tools;
import com.zyx.web.WebController;
import com.zyx.service.system.DictionariesService;
/** 
 * 说明：
 * 创建人：kbky
 * 创建时间：
 */
@Controller("infoqueryBasicRuleItemController")
@RequestMapping(value="/infoquery")
public class BasicRuleItemController extends WebController {
	
	@Autowired
	private BasicRuleItemService infoqueryBasicRuleItemService;
	@Autowired
	private DictionariesService dictionariesService;
	
	/*
	 * 查询
	 */
	@RequestMapping("/basicruleitemList")
	public String list(Map<String, Object> map) throws Exception{
		PageData pd = this.getPageData();
		int pageNum = Page.getPageNum(pd);
		int pageSize = Page.getPageSize(pd);
		List<Map<String, Object>> list = infoqueryBasicRuleItemService.listService(pageNum, pageSize, pd);
		PageInfo<Map<String, Object>> pageInfo = new PageInfo<Map<String, Object>>(list);
		map.put("page", pageInfo);
		map.put("basicruleitemList", list);
		map.put("pd", pd);
		return "/infoquery/basicruleitem/basicruleitem_list";
	}
	
	/*
	 * 保存
	 */
	@RequestMapping("/basicruleitemSave")
	@ResponseBody
	public String save(Map<String, Object> map) throws Exception{
		return infoqueryBasicRuleItemService.saveService(getPageData());
	}
	
	/*
	 * 修改
	 */
	@RequestMapping("/basicruleitemUpdate")
	@ResponseBody
	public String update(Map<String, Object> map) throws Exception{
		return infoqueryBasicRuleItemService.updateService(getPageData());
	}
	
	/*
	 * 删除
	 */
	@RequestMapping("/basicruleitemDel")
	@ResponseBody
	public String del(Map<String, Object> map) throws Exception{
		return infoqueryBasicRuleItemService.delService(getPageData());
	}
	
	/*
	 * 跳转到新增页面
	 */
	@RequestMapping("/basicruleitemAdd")
	public String add(Map<String, Object> map) throws Exception{
		PageData pd = getPageData();
		pd.put("TABLE_NAME", "bon_basic_ruleitem_tbl");
		pd.put("TABLE_COLUMN", "RULEITEM_TYPE");
		map.put("ruleitemtypeList", dictionariesService.listDicService(pd));
		pd.put("TABLE_NAME", "bon_basic_ruleitem_tbl");
		pd.put("TABLE_COLUMN", "RULEITEM_FUN");
		map.put("ruleitemfunList", dictionariesService.listDicService(pd));
		map.put("action", "Save");
		map.put("pd", pd);
		map.put("url", Tools.getStringValue(pd.get("url")));
		return "/infoquery/basicruleitem/basicruleitem_edit";
	}
	
	/*
	 * 跳转到修改页面
	 */
	@RequestMapping("/basicruleitemEdit")
	public String edit(Map<String, Object> map) throws Exception{
		PageData pd = getPageData();
		Map<String, Object> result = infoqueryBasicRuleItemService.editService(pd);
		map.put("basicruleitem", result);
		pd.put("TABLE_NAME", "bon_basic_ruleitem_tbl");
		pd.put("TABLE_COLUMN", "RULEITEM_TYPE");
		map.put("ruleitemtypeList", dictionariesService.listDicService(pd));
		pd.put("TABLE_NAME", "bon_basic_ruleitem_tbl");
		pd.put("TABLE_COLUMN", "RULEITEM_FUN");
		map.put("ruleitemfunList", dictionariesService.listDicService(pd));
		map.put("action", "Update");
		map.put("pd", pd);
		map.put("url", Tools.getStringValue(pd.get("url")));
		return "/infoquery/basicruleitem/basicruleitem_edit";
	}
	
	/*
	 * 跳转到详情页面
	 */
	@RequestMapping("/basicruleitemDetail")
	public String detail(Map<String, Object> map) throws Exception{
		PageData pd = getPageData();
		Map<String, Object> result = infoqueryBasicRuleItemService.editService(pd);
		map.put("basicruleitem", result);
		map.put("pd", pd);
		map.put("url", Tools.getStringValue(pd.get("url")));
		return "/infoquery/basicruleitem/basicruleitem_detail";
	}
}
