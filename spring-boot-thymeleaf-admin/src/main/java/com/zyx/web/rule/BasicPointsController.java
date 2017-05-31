package com.zyx.web.rule;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.github.pagehelper.PageInfo;
import com.zyx.model.Page;
import com.zyx.model.PageData;
import com.zyx.model.system.User;
import com.zyx.service.rule.BasicItemService;
import com.zyx.service.rule.BasicPointsService;
import com.zyx.util.Tools;
import com.zyx.web.WebController;
import com.zyx.service.system.DictionariesService;
/** 
 * 说明：
 * 创建人：kbky
 * 创建时间：
 */
@Controller("ruleBasicPointsController")
@RequestMapping(value="/rule")
public class BasicPointsController extends WebController {
	
	@Autowired
	private BasicPointsService ruleBasicPointsService;
	@Autowired
	private BasicItemService ruleBasicItemService;
	@Autowired
	private DictionariesService dictionariesService;
	
	/*
	 * 查询
	 */
	@RequestMapping("/basicpointsList")
	public String list(Map<String, Object> map) throws Exception{
		PageData pd = this.getPageData();
		int pageNum = Page.getPageNum(pd);
		int pageSize = Page.getPageSize(pd);
		List<Map<String, Object>> list = ruleBasicPointsService.listService(pageNum, pageSize, pd);
		PageInfo<Map<String, Object>> pageInfo = new PageInfo<Map<String, Object>>(list);
		map.put("page", pageInfo);
		map.put("basicpointsList", list);
		map.put("pd", pd);
		return "/rule/basicpoints/basicpoints_list";
	}
	
	/*
	 * 保存
	 */
	@RequestMapping("/basicpointsSave")
	@ResponseBody
	public String save(Map<String, Object> map) throws Exception{
		return ruleBasicPointsService.saveService(getPageData());
	}
	
	/*
	 * 修改
	 */
	@RequestMapping("/basicpointsUpdate")
	@ResponseBody
	public String update(Map<String, Object> map) throws Exception{
		return ruleBasicPointsService.updateService(getPageData());
	}
	
	/*
	 * 删除
	 */
	@RequestMapping("/basicpointsDel")
	@ResponseBody
	public String del(Map<String, Object> map) throws Exception{
		return ruleBasicPointsService.delService(getPageData());
	}
	
	/*
	 * 跳转到新增页面
	 */
	@RequestMapping("/basicpointsAdd")
	public String add(Map<String, Object> map) throws Exception{
		PageData pd = getPageData();
		pd.put("TABLE_NAME", "BON_BASIC_POINTS_TBL");
		pd.put("TABLE_COLUMN", "POINTS_RANGE");
		map.put("pointsrangeList", dictionariesService.listDicService(pd));
		pd.put("TABLE_NAME", "BON_BASIC_POINTS_TBL");
		pd.put("TABLE_COLUMN", "TYPE");
		map.put("typeList", dictionariesService.listDicService(pd));
		pd.put("TABLE_NAME", "BON_BASIC_POINTS_TBL");
		pd.put("TABLE_COLUMN", "DATA_FLAG");
		map.put("dataflagList", dictionariesService.listDicService(pd));
		pd.put("TABLE_NAME", "BON_BASIC_POINTS_TBL");
		pd.put("TABLE_COLUMN", "OWNER");
		map.put("ownerList", dictionariesService.listDicService(pd));
		pd.put("TABLE_NAME", "BON_BASIC_POINTS_TBL");
		pd.put("TABLE_COLUMN", "POINTS_TYPE");
		map.put("pointstypeList", dictionariesService.listDicService(pd));
		pd.put("TABLE_NAME", "BON_BASIC_POINTS_TBL");
		pd.put("TABLE_COLUMN", "RUN_FLAG");
		map.put("runflagList", dictionariesService.listDicService(pd));
		pd.put("TABLE_NAME", "BON_BASIC_POINTS_TBL");
		pd.put("TABLE_COLUMN", "LONGTIME_FLAG");
		map.put("longtimeflagList", dictionariesService.listDicService(pd));
		/*pd.put("TABLE_NAME", "BON_BASIC_POINTS_TBL");
		pd.put("TABLE_COLUMN", "LIMIT_TYPE");
		map.put("limittypeList", dictionariesService.listDicService(pd));*/
		pd.put("TABLE_NAME", "BON_BASIC_POINTS_TBL");
		pd.put("TABLE_COLUMN", "STATUS");
		map.put("statusList", dictionariesService.listDicService(pd));
		//限额类型
		map.put("limittypeList", ruleBasicPointsService.findLimitType());
		
		//生成基本规则编码
		StringBuilder sb1 = new StringBuilder();
		for(int i = 0; i < 18; i++){
			char c = (char)(Math.random()*26+65);
			String s = String.valueOf(c);
			sb1.append(s);
		}
		map.put("BASE_RULE_CODE", sb1.toString());
		//生成附加规则编码
		StringBuilder sb2 = new StringBuilder();
		for(int i = 0; i < 18; i++){
			char c = (char)(Math.random()*26+65);
			String s = String.valueOf(c);
			sb2.append(s);
		}
		map.put("ATTACH_RULE_CODE", sb2.toString());
		
		//规则状态
		pd.put("TABLE_NAME", "BON_BASIC_RULE_TBL");
		pd.put("TABLE_COLUMN", "STATUS");
		map.put("ruleStatusList", dictionariesService.listDicService(pd));
		//规则积分公式类型
		pd.put("TABLE_NAME", "BON_BASIC_RULE_TBL");
		pd.put("TABLE_COLUMN", "FORMULA_TYPE");
		map.put("formulaTypeList", dictionariesService.listDicService(pd));
		
		//添加元素下拉列表
		List<Map<String, Object>>	itemsList = ruleBasicPointsService.listItems(pd);
		map.put("itemsList", itemsList);
		
		StringBuilder sb = new StringBuilder();
		sb.append("<td style=\"width: 100px; text-align: right; padding-top: 13px;\">添加元素:</td>");
		sb.append("<td colspan=\"6\">");
		sb.append("<select multiple=\"\" class=\"chosen-select form-control\" id=\"BASE_RULE_SELECT\" name=\"BASE_RULE_SELECT\" data-placeholder=\"添加元素...\">");
		for(Map<String, Object> m : itemsList){
			String code = Tools.getStringValue(m.get("RULEITEM_CODE"));
			String prog = Tools.getStringValue(m.get("RULEITEM_PROG"));
			String desc = Tools.getStringValue(m.get("RULEITEM_DESC"));
			sb.append("<option value='"+code + "--" + prog + "'>" + desc + "</option>");
		}
		sb.append("</select>");
		sb.append("<input id=\"BASE_RULE_SELECT_TXT\" name=\"BASE_RULE_SELECT_TXT\" type=\"hidden\" value=\"\" /></td>");
		
		map.put("baseRuleSelect", sb.toString());
		
		User user = (User) SecurityUtils.getSubject().getPrincipal();
		map.put("SHOP_CODE", user.getOrganId());
		
		map.put("action", "Save");
		map.put("pd", pd);
		map.put("url", Tools.getStringValue(pd.get("url")));
		return "/rule/basicpoints/basicpoints_edit";
	}
	
	/*
	 * 跳转到新增页面
	 */
	@RequestMapping("/basicpointsAddPoints")
	public String goAddPoints(Map<String, Object> map) throws Exception{
		PageData pd = getPageData();
		pd.put("TABLE_NAME", "BON_BASIC_POINTS_TBL");
		pd.put("TABLE_COLUMN", "POINTS_RANGE");
		map.put("pointsrangeList", dictionariesService.listDicService(pd));
		pd.put("TABLE_NAME", "BON_BASIC_POINTS_TBL");
		pd.put("TABLE_COLUMN", "TYPE");
		map.put("typeList", dictionariesService.listDicService(pd));
		pd.put("TABLE_NAME", "BON_BASIC_POINTS_TBL");
		pd.put("TABLE_COLUMN", "DATA_FLAG");
		map.put("dataflagList", dictionariesService.listDicService(pd));
		pd.put("TABLE_NAME", "BON_BASIC_POINTS_TBL");
		pd.put("TABLE_COLUMN", "OWNER");
		map.put("ownerList", dictionariesService.listDicService(pd));
		pd.put("TABLE_NAME", "BON_BASIC_POINTS_TBL");
		pd.put("TABLE_COLUMN", "POINTS_TYPE");
		map.put("pointstypeList", dictionariesService.listDicService(pd));
		pd.put("TABLE_NAME", "BON_BASIC_POINTS_TBL");
		pd.put("TABLE_COLUMN", "RUN_FLAG");
		map.put("runflagList", dictionariesService.listDicService(pd));
		pd.put("TABLE_NAME", "BON_BASIC_POINTS_TBL");
		pd.put("TABLE_COLUMN", "LONGTIME_FLAG");
		map.put("longtimeflagList", dictionariesService.listDicService(pd));
		/*pd.put("TABLE_NAME", "BON_BASIC_POINTS_TBL");
		pd.put("TABLE_COLUMN", "LIMIT_TYPE");
		map.put("limittypeList", dictionariesService.listDicService(pd));*/
		pd.put("TABLE_NAME", "BON_BASIC_POINTS_TBL");
		pd.put("TABLE_COLUMN", "STATUS");
		map.put("statusList", dictionariesService.listDicService(pd));
		//限额类型
		map.put("limittypeList", ruleBasicPointsService.findLimitType());
		
		//生成基本规则编码
		StringBuilder sb1 = new StringBuilder();
		for(int i = 0; i < 18; i++){
			char c = (char)(Math.random()*26+65);
			String s = String.valueOf(c);
			sb1.append(s);
		}
		map.put("BASE_RULE_CODE", sb1.toString());
		//生成附加规则编码
		StringBuilder sb2 = new StringBuilder();
		for(int i = 0; i < 18; i++){
			char c = (char)(Math.random()*26+65);
			String s = String.valueOf(c);
			sb2.append(s);
		}
		map.put("ATTACH_RULE_CODE", sb2.toString());
		
		//规则状态
		pd.put("TABLE_NAME", "BON_BASIC_RULE_TBL");
		pd.put("TABLE_COLUMN", "STATUS");
		map.put("ruleStatusList", dictionariesService.listDicService(pd));
		//规则积分公式类型
		pd.put("TABLE_NAME", "BON_BASIC_RULE_TBL");
		pd.put("TABLE_COLUMN", "FORMULA_TYPE");
		map.put("formulaTypeList", dictionariesService.listDicService(pd));
		
		//添加元素下拉列表
		List<Map<String, Object>>	itemsList = ruleBasicPointsService.listItems(pd);
		map.put("itemsList", itemsList);
		
		StringBuilder sb = new StringBuilder();
		sb.append("<td style=\"width: 100px; text-align: right; padding-top: 13px;\">添加元素:</td>");
		sb.append("<td colspan=\"6\">");
		sb.append("<select multiple=\"\" class=\"chosen-select form-control\" id=\"BASE_RULE_SELECT\" name=\"BASE_RULE_SELECT\" data-placeholder=\"添加元素...\">");
		for(Map<String, Object> m : itemsList){
			String code = Tools.getStringValue(m.get("RULEITEM_CODE"));
			String prog = Tools.getStringValue(m.get("RULEITEM_PROG"));
			String desc = Tools.getStringValue(m.get("RULEITEM_DESC"));
			sb.append("<option value='"+code + "--" + prog + "'>" + desc + "</option>");
		}
		sb.append("</select>");
		sb.append("<input id=\"BASE_RULE_SELECT_TXT\" name=\"BASE_RULE_SELECT_TXT\" type=\"hidden\" value=\"\" /></td>");
		
		map.put("baseRuleSelect", sb.toString());
		
		User user = (User) SecurityUtils.getSubject().getPrincipal();
		map.put("SHOP_CODE", user.getOrganId());
		
		map.put("action", "Save");
		map.put("pd", pd);
		map.put("url", Tools.getStringValue(pd.get("url")));
		return "/rule/basicpoints/basicpoints_edit_points";
	}
	
	/*
	 * 跳转到修改页面
	 */
	@RequestMapping("/basicpointsEdit")
	public String edit(Map<String, Object> map) throws Exception{
		PageData pd = getPageData();
		Map<String, Object> result = ruleBasicPointsService.editService(map, pd);
		map.put("basicpoints", result);
		pd.put("TABLE_NAME", "BON_BASIC_POINTS_TBL");
		pd.put("TABLE_COLUMN", "POINTS_RANGE");
		map.put("pointsrangeList", dictionariesService.listDicService(pd));
		pd.put("TABLE_NAME", "BON_BASIC_POINTS_TBL");
		pd.put("TABLE_COLUMN", "TYPE");
		map.put("typeList", dictionariesService.listDicService(pd));
		pd.put("TABLE_NAME", "BON_BASIC_POINTS_TBL");
		pd.put("TABLE_COLUMN", "DATA_FLAG");
		map.put("dataflagList", dictionariesService.listDicService(pd));
		pd.put("TABLE_NAME", "BON_BASIC_POINTS_TBL");
		pd.put("TABLE_COLUMN", "OWNER");
		map.put("ownerList", dictionariesService.listDicService(pd));
		pd.put("TABLE_NAME", "BON_BASIC_POINTS_TBL");
		pd.put("TABLE_COLUMN", "POINTS_TYPE");
		map.put("pointstypeList", dictionariesService.listDicService(pd));
		pd.put("TABLE_NAME", "BON_BASIC_POINTS_TBL");
		pd.put("TABLE_COLUMN", "RUN_FLAG");
		map.put("runflagList", dictionariesService.listDicService(pd));
		pd.put("TABLE_NAME", "BON_BASIC_POINTS_TBL");
		pd.put("TABLE_COLUMN", "LONGTIME_FLAG");
		map.put("longtimeflagList", dictionariesService.listDicService(pd));
		pd.put("TABLE_NAME", "BON_BASIC_POINTS_TBL");
		pd.put("TABLE_COLUMN", "LIMIT_TYPE");
		map.put("limittypeList", dictionariesService.listDicService(pd));
		pd.put("TABLE_NAME", "BON_BASIC_POINTS_TBL");
		pd.put("TABLE_COLUMN", "STATUS");
		map.put("statusList", dictionariesService.listDicService(pd));
		//限额类型
		map.put("limittypeList", ruleBasicPointsService.findLimitType());
		//规则状态
		pd.put("TABLE_NAME", "BON_BASIC_RULE_TBL");
		pd.put("TABLE_COLUMN", "STATUS");
		map.put("ruleStatusList", dictionariesService.listDicService(pd));
		//规则积分公式类型
		pd.put("TABLE_NAME", "BON_BASIC_RULE_TBL");
		pd.put("TABLE_COLUMN", "FORMULA_TYPE");
		map.put("formulaTypeList", dictionariesService.listDicService(pd));
		
		map.put("action", "Update");
		map.put("pd", pd);
		map.put("url", Tools.getStringValue(pd.get("url")));
		return "/rule/basicpoints/basicpoints_edit";
	}
	
	/*
	 * 跳转到详情页面
	 */
	@RequestMapping("/basicpointsDetail")
	public String detail(Map<String, Object> map) throws Exception{
		PageData pd = getPageData();
		Map<String, Object> result = ruleBasicPointsService.editService(map, pd);
		map.put("basicpoints", result);
		map.put("pd", pd);
		map.put("url", Tools.getStringValue(pd.get("url")));
		return "/rule/basicpoints/basicpoints_detail";
	}
	
	@RequestMapping("/findProgDesc")
	@ResponseBody
	public Object findProgDesc() throws Exception {
		return ruleBasicItemService.findProgDesc(getPageData());
	}
	
	@RequestMapping("/getCalHtml")
	@ResponseBody
	public Object getCalHtml() throws Exception {
		return ruleBasicPointsService.getCalHtml();
	}
}
