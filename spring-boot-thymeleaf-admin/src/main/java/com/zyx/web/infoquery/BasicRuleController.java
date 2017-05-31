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
import com.zyx.service.infoquery.BasicRuleService;
import com.zyx.util.Tools;
import com.zyx.web.WebController;
import com.zyx.service.system.DictionariesService;
/** 
 * 说明：
 * 创建人：kbky
 * 创建时间：
 */
@Controller("infoqueryBasicRuleController")
@RequestMapping(value="/infoquery")
public class BasicRuleController extends WebController {
	
	@Autowired
	private BasicRuleService infoqueryBasicRuleService;
	@Autowired
	private DictionariesService dictionariesService;
	
	/*
	 * 查询
	 */
	@RequestMapping("/basicruleList")
	public String list(Map<String, Object> map) throws Exception{
		PageData pd = this.getPageData();
		int pageNum = Page.getPageNum(pd);
		int pageSize = Page.getPageSize(pd);
		List<Map<String, Object>> list = infoqueryBasicRuleService.listService(pageNum, pageSize, pd);
		PageInfo<Map<String, Object>> pageInfo = new PageInfo<Map<String, Object>>(list);
		map.put("page", pageInfo);
		map.put("basicruleList", list);
		map.put("pd", pd);
		return "/infoquery/basicrule/basicrule_list";
	}
	
	/*
	 * 保存
	 */
	@RequestMapping("/basicruleSave")
	@ResponseBody
	public String save(Map<String, Object> map) throws Exception{
		return infoqueryBasicRuleService.saveService(getPageData());
	}
	
	/*
	 * 修改
	 */
	@RequestMapping("/basicruleUpdate")
	@ResponseBody
	public String update(Map<String, Object> map) throws Exception{
		return infoqueryBasicRuleService.updateService(getPageData());
	}
	
	/*
	 * 删除
	 */
	@RequestMapping("/basicruleDel")
	@ResponseBody
	public String del(Map<String, Object> map) throws Exception{
		return infoqueryBasicRuleService.delService(getPageData());
	}
	
	/*
	 * 跳转到新增页面
	 */
	@RequestMapping("/basicruleAdd")
	public String add(Map<String, Object> map) throws Exception{
		PageData pd = getPageData();
		pd.put("TABLE_NAME", "BON_BASIC_RULE_TBL");
		pd.put("TABLE_COLUMN", "RULE_TYPE");
		map.put("ruletypeList", dictionariesService.listDicService(pd));
		pd.put("TABLE_NAME", "BON_BASIC_RULE_TBL");
		pd.put("TABLE_COLUMN", "FORMULA_TYPE");
		map.put("formulatypeList", dictionariesService.listDicService(pd));
		pd.put("TABLE_NAME", "BON_BASIC_RULE_TBL");
		pd.put("TABLE_COLUMN", "STATUS");
		map.put("statusList", dictionariesService.listDicService(pd));
		map.put("action", "Save");
		map.put("pd", pd);
		map.put("url", Tools.getStringValue(pd.get("url")));
		return "/infoquery/basicrule/basicrule_edit";
	}
	
	/*
	 * 跳转到修改页面
	 */
	@RequestMapping("/basicruleEdit")
	public String edit(Map<String, Object> map) throws Exception{
		PageData pd = getPageData();
		Map<String, Object> result = infoqueryBasicRuleService.editService(pd);
		map.put("basicrule", result);
		pd.put("TABLE_NAME", "BON_BASIC_RULE_TBL");
		pd.put("TABLE_COLUMN", "RULE_TYPE");
		map.put("ruletypeList", dictionariesService.listDicService(pd));
		pd.put("TABLE_NAME", "BON_BASIC_RULE_TBL");
		pd.put("TABLE_COLUMN", "FORMULA_TYPE");
		map.put("formulatypeList", dictionariesService.listDicService(pd));
		pd.put("TABLE_NAME", "BON_BASIC_RULE_TBL");
		pd.put("TABLE_COLUMN", "STATUS");
		map.put("statusList", dictionariesService.listDicService(pd));
		map.put("action", "Update");
		map.put("pd", pd);
		map.put("url", Tools.getStringValue(pd.get("url")));
		return "/infoquery/basicrule/basicrule_edit";
	}
	
	/*
	 * 跳转到详情页面
	 */
	@RequestMapping("/basicruleDetail")
	public String detail(Map<String, Object> map) throws Exception{
		PageData pd = getPageData();
		Map<String, Object> result = infoqueryBasicRuleService.editService(pd);
		map.put("basicrule", result);
		map.put("pd", pd);
		map.put("url", Tools.getStringValue(pd.get("url")));
		return "/infoquery/basicrule/basicrule_detail";
	}
}
