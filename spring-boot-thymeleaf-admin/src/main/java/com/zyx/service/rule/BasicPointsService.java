package com.zyx.service.rule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.apache.commons.collections.MapUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.github.pagehelper.PageHelper;
import com.zyx.dao.DaoSupport;
import com.zyx.model.PageData;
import com.zyx.model.system.User;
import com.zyx.service.system.DictionariesService;
import com.zyx.util.Constants;
import com.zyx.util.JsonTool;
import com.zyx.util.Tools;


/** 
 * 说明： 
 * 创建人：kbky
 * 创建时间：
 * @version
 */
@Service("ruleBasicPointsService")
public class BasicPointsService {

	@Autowired
	private DictionariesService dictionariesService;
	
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	public List<Map<String, Object>> findLimitType() throws Exception{
		Object object = dao.findForList("com.zyx.mapper.rule.BasicPointsMapper.findLimitType", "");
		return Tools.objToList(object);
	}
	
	/*
	 * 查询
	 */
	public List<Map<String, Object>> listService(int pageNum, int pageSize, PageData pd) throws Exception{
		PageHelper.startPage(pageNum, pageSize);
		Object object = dao.findForList("com.zyx.mapper.rule.BasicPointsMapper.list", pd);
		return Tools.objToList(object);
	}
	
	/*
	 * 保存
	 */
	@Transactional
	public String saveService(PageData pd) throws Exception {
		User user = (User) SecurityUtils.getSubject().getPrincipal();
		int count = (int) dao.findForObject("com.zyx.mapper.rule.BasicPointsMapper.isExists", pd);
		if(count > 0){
			return "积分编码已存在!";
		}else{
			//活动
			dao.save("com.zyx.mapper.rule.BasicPointsMapper.save", pd);
			//基本规则
			PageData baseRule = new PageData();
			baseRule.put("RULE_CODE", pd.get("BASE_RULE_CODE"));
			baseRule.put("SHOP_CODE", pd.get("SHOP_CODE"));
			baseRule.put("POINTS_CODE", pd.get("POINTS_CODE"));
			baseRule.put("RULE_NUM", "1");
			baseRule.put("RULE_NAME", pd.get("BASE_RULE_NAME"));
			baseRule.put("RULE_TYPE", "1");
			baseRule.put("RULE_FORMULA", "");
			baseRule.put("FORMULA_TYPE", "");
			baseRule.put("STATUS", pd.get("BASE_STATUS"));
			baseRule.put("user", user);
			dao.save("com.zyx.mapper.rule.BasicPointsMapper.saveBasicRule", baseRule);
			String baseRuleSelect = pd.getString("baseRuleTableValue");
			if(!"".equals(baseRuleSelect) && null != baseRuleSelect){
				String[] ruleArr = baseRuleSelect.split(",ykbk,");
				for(int i = 0; i < ruleArr.length; i++){
					String[] ruleValue = ruleArr[i].split(",kbky,",-1);
					String itemCode = ruleValue[0].split("--")[0];
					String fun = ruleValue[1] == null ? "" : ruleValue[1];
					String val = ruleValue[2] == null ? "" : ruleValue[2];
					PageData baseRuleItem = new PageData();
					baseRuleItem.put("SHOP_CODE", pd.get("SHOP_CODE"));
					baseRuleItem.put("RULE_CODE", pd.get("BASE_RULE_CODE"));
					baseRuleItem.put("RULEITEM_CODE", itemCode);
					
					//查询basic_item表记录配置
					pd.put("RULEITEM_CODE", itemCode);
					Map<String, Object> result = (Map<String, Object>) dao.findForObject("com.zyx.mapper.rule.BasicItemMapper.findByItemCode", pd);
					String ruleItemType = MapUtils.getString(result, "RULEITEM_TYPE");
					ruleItemType = ruleItemType == null ? "" : ruleItemType;
					
					baseRuleItem.put("RULEITEM_NUM", i+1);
					
					baseRuleItem.put("RULEITEM_SELECT", fun);
					baseRuleItem.put("RULEITEM_TEXT", val);
					
					if("2".equals(ruleItemType)){
						String ruleItemFun = Tools.getStringValue(result.get("RULEITEM_FUN"));
						baseRuleItem.put("RULEITEM_FUN", ruleItemFun);
					}else{
						baseRuleItem.put("RULEITEM_FUN", fun);
					}
					
					val = fun + "|" + val;
					
					if(val.length() > 200){
						baseRuleItem.put("RULEITEM_TYPE", "3");
						
						baseRuleItem.put("RULEITEM_VAL", "");
						
						baseRuleItem.put("ID", pd.get("ID"));
						dao.save("com.zyx.mapper.rule.BasicPointsMapper.saveRuleItem", baseRuleItem);
						
					}else{
						if("2".equals(ruleItemType)){
							baseRuleItem.put("RULEITEM_TYPE", "2");
						}else if("3".equals(ruleItemType)){
							baseRuleItem.put("RULEITEM_TYPE", "0");
						}else{
							baseRuleItem.put("RULEITEM_TYPE", "1");
						}
						baseRuleItem.put("RULEITEM_VAL", val);
						
						baseRuleItem.put("ID", pd.get("ID"));
						dao.save("com.zyx.mapper.rule.BasicPointsMapper.saveRuleItem", baseRuleItem);
					}
				}
			}
			
			//计算规则
			String calTableTxt = pd.getString("calTableTxt");
			if(!"".equals(calTableTxt) && null != calTableTxt){
				String[] calTableArr = calTableTxt.split(",xyz,");
				for(int i = 0; i < calTableArr.length; i++){
					String calTable = calTableArr[i];
					String[] calInput = calTable.split(",zyx,",-1);
					String calRuleCode = calInput[0];
					String calRuleName = calInput[1];
					String calStatus = calInput[2];
					String ruleFormula = calInput[3];
					String formulaType = calInput[4];
					PageData calRule = new PageData();
					calRule.put("RULE_CODE", calRuleCode);
					calRule.put("SHOP_CODE", pd.get("SHOP_CODE"));
					calRule.put("POINTS_CODE", pd.get("POINTS_CODE"));
					calRule.put("RULE_NUM", (i+1));
					calRule.put("RULE_NAME", calRuleName);
					calRule.put("RULE_TYPE", "2");
					calRule.put("RULE_FORMULA", ruleFormula);
					calRule.put("FORMULA_TYPE", formulaType);
					calRule.put("STATUS", calStatus);
					calRule.put("OPER_ORG", pd.get("OPER_ORG"));
					calRule.put("OPER_ID", pd.get("OPER_ID"));
					calRule.put("OPER_DATE", pd.get("OPER_DATE"));
					calRule.put("ID", pd.get("ID"));
					calRule.put("user", user);
					dao.save("com.zyx.mapper.rule.BasicPointsMapper.saveBasicRule", calRule);
					String[] calSelect = calInput[5].split(",ykbk,");
					for(int j = 0; j < calSelect.length; j++){
						String[] ruleValue = calSelect[j].split(",kbky,",-1);
						String itemCode = ruleValue[0].split("--")[0];
						String fun = ruleValue[1] == null ? "" : ruleValue[1];
						String val = ruleValue[2] == null ? "" : ruleValue[2];
						PageData calRuleItem = new PageData();
						calRuleItem.put("SHOP_CODE", pd.get("SHOP_CODE"));
						calRuleItem.put("RULE_CODE", calRuleCode);
						calRuleItem.put("RULEITEM_CODE", itemCode);
						
						pd.put("RULEITEM_CODE", itemCode);
						Map<String, Object> result = (Map<String, Object>) dao.findForObject("com.zyx.mapper.rule.BasicItemMapper.findByItemCode", pd);
						String ruleItemType = MapUtils.getString(result, "RULEITEM_TYPE");
						ruleItemType = ruleItemType == null ? "" : ruleItemType;
						
						calRuleItem.put("RULEITEM_NUM", j+1);
						calRuleItem.put("RULEITEM_SELECT", fun);
						calRuleItem.put("RULEITEM_TEXT", val);
						
						if("2".equals(ruleItemType)){
							String ruleItemFun = Tools.getStringValue(result.get("RULEITEM_FUN"));
							calRuleItem.put("RULEITEM_FUN", ruleItemFun);
						}else{
							calRuleItem.put("RULEITEM_FUN", fun);
						}
						val = fun + "|" + val;
						
						
						if(val.length() > 200){
							calRuleItem.put("RULEITEM_TYPE", "3");
							
							calRuleItem.put("RULEITEM_VAL", "");
							
							calRuleItem.put("ID", pd.get("ID"));
							dao.save("com.zyx.mapper.rule.BasicPointsMapper.saveRuleItem", calRuleItem);
							
						}else{
							if("2".equals(ruleItemType)){
								calRuleItem.put("RULEITEM_TYPE", "2");
							}else if("3".equals(ruleItemType)){
								calRuleItem.put("RULEITEM_TYPE", "0");
							}else{
								calRuleItem.put("RULEITEM_TYPE", "1");
							}
							calRuleItem.put("RULEITEM_VAL", val);
							
							calRuleItem.put("ID", pd.get("ID"));
							dao.save("com.zyx.mapper.rule.BasicPointsMapper.saveRuleItem", calRuleItem);
						}
						
					}
				}
			}
			//附加规则
			PageData attachRule = new PageData();
			attachRule.put("RULE_CODE", pd.get("ATTACH_RULE_CODE"));
			attachRule.put("SHOP_CODE", pd.get("SHOP_CODE"));
			attachRule.put("POINTS_CODE", pd.get("POINTS_CODE"));
			attachRule.put("RULE_NUM", "1");
			attachRule.put("RULE_NAME", pd.get("ATTACH_RULE_NAME"));
			attachRule.put("RULE_TYPE", "3");
			attachRule.put("RULE_FORMULA", pd.get("ATTACH_RULE_FORMULA"));
			attachRule.put("FORMULA_TYPE", pd.get("ATTACH_FORMULA_TYPE"));
			attachRule.put("STATUS", pd.get("ATTACH_STATUS"));
			attachRule.put("OPER_ORG", pd.get("OPER_ORG"));
			attachRule.put("OPER_ID", pd.get("OPER_ID"));
			attachRule.put("OPER_DATE", pd.get("OPER_DATE"));
			attachRule.put("ID", pd.get("ID"));
			attachRule.put("user", user);
			dao.save("com.zyx.mapper.rule.BasicPointsMapper.saveBasicRule", attachRule);
			
			return Constants.SUCCESS;
		}
	}
	
	/*
	 * 修改页面数据查询
	 */
	public Map<String, Object> editService(Map<String, Object> map, PageData pd) throws Exception {
		Object object = dao.findForObject("com.zyx.mapper.rule.BasicPointsMapper.listById", pd);
		
		List<Map<String, Object>> list = (List<Map<String, Object>>)dao.findForList("com.zyx.mapper.rule.BasicPointsMapper.findByPointsCode", Tools.objToMap(object));
		Map<String, Object> baseRule = new HashMap<String, Object>();
		Map<String, Object> attachRule = new HashMap<String, Object>();
		List<Map<String, Object>> calList = new ArrayList<Map<String, Object>>();
		for(Map<String, Object> data : list){
			String type = Tools.getStringValue(data.get("RULE_TYPE"));
			if("1".equals(type)){
				baseRule = data;
				List<Map<String, Object>> list1 = (List<Map<String, Object>>) dao.findForList("com.zyx.mapper.rule.BasicPointsMapper.findByRuleCode", data);
				for(Map<String, Object> m : list1){
					String progDesc = MapUtils.getString(m, "PROG_DESC");
					Map<String, Object> descMap = new HashMap<String, Object>();
					if(!"".equals(progDesc) && progDesc != null){
						descMap = JsonTool.jsonToMap(progDesc);
					}
					m.put("progDescList", descMap);
				}
				baseRule.put("baseSelect", list1);
				map.put("baseRuleSelect", getRuleSelect(pd, list1));
				map.put("baseRuleSelectContext", getRuleContent(list1,"base"));
			}
			if("2".equals(type)){
				List<Map<String, Object>> list2 = (List<Map<String, Object>>) dao.findForList("com.zyx.mapper.rule.BasicPointsMapper.findByRuleCode", data);
				for(Map<String, Object> m : list2){
					String progDesc = MapUtils.getString(m, "PROG_DESC");
					Map<String, Object> descMap = new HashMap<String, Object>();
					if(!"".equals(progDesc) && progDesc != null){
						descMap = JsonTool.jsonToMap(progDesc);
					}
					m.put("progDescList", descMap);
				}
				data.put("calSelect", list2);
				calList.add(data);
			}
			if("3".equals(type)){
				attachRule = data;
				
				List<Map<String, Object>> list3 = (List<Map<String, Object>>) dao.findForList("com.zyx.mapper.rule.BasicPointsMapper.findByRuleCode", data);
				for(Map<String, Object> m : list3){
					String progDesc = MapUtils.getString(m, "PROG_DESC");
					Map<String, Object> descMap = new HashMap<String, Object>();
					if(!"".equals(progDesc) && progDesc != null){
						descMap = JsonTool.jsonToMap(progDesc);
					}
					m.put("progDescList", descMap);
				}
				attachRule.put("attachSelect", list3);
				
			}
		}
		map.put("baseRule",baseRule);
		map.put("calRule",getEditCalHtml(calList));//getEditCalHtml
		map.put("attachRule",attachRule);
		
		return Tools.objToMap(object);
	}
	
	/*
	 * 修改
	 */
	@Transactional
	public String updateService(PageData pd) throws Exception {
		int count = (int) dao.findForObject("com.zyx.mapper.rule.BasicPointsMapper.isExists", pd);
		if(count > 0){
			//dao.update("com.zyx.mapper.rule.BasicPointsMapper.update", pd);
			delService(pd);
			saveService(pd);
			return Constants.SUCCESS;
		}else{
			return "记录已被其他用户删除!";		
		}
	}
	
	/*
	 * 删除
	 */
	@Transactional
	public String delService(PageData pd) throws Exception {
		
		Map<String, Object> delPage = (Map<String, Object>)dao.findForObject("com.zyx.mapper.rule.BasicPointsMapper.listById", pd);
		//删除ruleitem表
		dao.delete("com.zyx.mapper.rule.BasicPointsMapper.delRuleItem", delPage);
		//删除rule表
		dao.delete("com.zyx.mapper.rule.BasicPointsMapper.delRule", delPage);
		//删除活动表
		dao.delete("com.zyx.mapper.rule.BasicPointsMapper.delete", pd);
		//删除启动表中的数据
		dao.delete("com.zyx.mapper.rule.BasicPointsMapper.delStart", delPage);
		return Constants.SUCCESS;
	}
	
	public List<Map<String, Object>> listItems(PageData pd)throws Exception{
		Object object = dao.findForList("com.zyx.mapper.rule.BasicPointsMapper.listItems", pd);
		return Tools.objToList(object);
	}
	
	/*
	 * 添加元素下拉列表
	 */
	private String getRuleSelect(PageData pd, List<Map<String, Object>> list) throws Exception{
		//添加元素下拉列表
		List<Map<String, Object>>	itemsList = listItems(pd);
		StringBuilder sb = new StringBuilder();
		sb.append("<td style=\"width: 100px; text-align: right; padding-top: 13px;\">添加元素:</td>");
		sb.append("<td colspan=\"6\">");
		sb.append("<select multiple=\"\" class=\"chosen-select form-control\" id=\"BASE_RULE_SELECT\" name=\"BASE_RULE_SELECT\" data-placeholder=\"添加元素...\">");
		sb.append("<option value=''></option>");
		for(Map<String, Object> m : itemsList){
			String code = Tools.getStringValue(m.get("RULEITEM_CODE"));
			String prog = Tools.getStringValue(m.get("RULEITEM_PROG"));
			String desc = Tools.getStringValue(m.get("RULEITEM_DESC"));
			boolean flag = false;
			for(Map<String, Object> mm : list){
				String mCode = Tools.getStringValue(mm.get("RULEITEM_CODE"));
				if(code.equals(mCode)){
					flag = true;
				}
			}
			if(flag){
				sb.append("<option selected=\"selected\" value='"+code + "--" + prog + "'>" + desc + "</option>");
			}else{
				sb.append("<option value='"+code + "--" + prog + "'>" + desc + "</option>");
			}
		}
		sb.append("</select>");
		sb.append("<input id=\"BASE_RULE_SELECT_TXT\" name=\"BASE_RULE_SELECT_TXT\" type=\"hidden\" value=\"\" /></td>");
		return sb.toString();
	}
	
	/*
	 * 元素下拉列表对应的HTML代码
	 */
	private String getRuleContent(List<Map<String, Object>> list, String type){
		StringBuilder baseSelectContext = new StringBuilder();
		for(Map<String, Object> m : list){
			String prog = Tools.getStringValue(m.get("RULEITEM_PROG"));
			String code = Tools.getStringValue(m.get("RULEITEM_CODE"));
			String desc = Tools.getStringValue(m.get("RULEITEM_DESC"));
			String text = Tools.getStringValue(m.get("RULEITEM_TEXT"));
			//String select = Tools.getStringValue(m.get("RULEITEM_SELECT"));
			String id = code + "--" + prog;
			String value = code + "--" + prog;
			if("1".equals(prog)){
				baseSelectContext.append("<tr id='"+type+id +"' name='"+ type +"Select'>");
				baseSelectContext.append("<td style=\"width:75px;text-align: right;padding-top: 13px;\">");
				baseSelectContext.append(desc);
				baseSelectContext.append("<input type=\"hidden\" name=\"selectVal\" value='" + code + "--" + prog + "'/>");
				baseSelectContext.append("<td>");
				baseSelectContext.append("<select class=\"chosen-select form-control\" name=\"funValue\" id='" + code + "--" + prog + "_FUN' data-placeholder=\"\" style=\"vertical-align:top;\">");
				baseSelectContext.append("<option value=\"0\">等于</option>");
				baseSelectContext.append("<option value=\"1\">不等于</option>");
				baseSelectContext.append("<option value=\"2\">属于</option>");
				baseSelectContext.append("<option value=\"3\">不属于</option>");
				baseSelectContext.append("<option value=\"4\">大于</option>");
				baseSelectContext.append("<option value=\"5\">小于</option>");
				baseSelectContext.append("<option value=\"6\">大于等于</option>");
				baseSelectContext.append("<option value=\"7\">小于等于</option>");
				baseSelectContext.append("</select>");
				baseSelectContext.append("</td>");
				baseSelectContext.append("<td colspan=\"4\"><input type=\"text\" name=\"valValue\" id='" + desc + "--" + prog + "_VAL' value='" + text + "' maxlength=\"12\" placeholder=\"\" title=\"\" style=\"width:98%;\"/></td>");
				baseSelectContext.append("</tr>");
			}else if("2".equals(prog)){
				baseSelectContext.append("<tr id='"+type+id +"' name='"+type+"Select'>");
				baseSelectContext.append("<td style=\"width:75px;text-align: right;padding-top: 13px;\">");
				baseSelectContext.append(desc);
				baseSelectContext.append("<input type=\"hidden\" name=\"selectVal\" value='" + value + "'/>");
				baseSelectContext.append("</td>");
				baseSelectContext.append("<td colspan=\"5\">");
				baseSelectContext.append("<select class=\"chosen-select form-control\" name=\"funValue\" id='" + id + "_FUN' data-placeholder=\"\" style=\"vertical-align:top;\">");
				Map<String, Object> progDescMap = (Map<String, Object>) m.get("progDescList");
				for(Map.Entry entry : progDescMap.entrySet()){
					baseSelectContext.append("<option value='"+entry.getKey()+"' >"+entry.getValue()+"</option>");
				}
				baseSelectContext.append("</select>");
				baseSelectContext.append("<input type=\"hidden\" name=\"valValue\" id='"+desc+"--"+prog+"_VAL' value='"+text+"' maxlength=\"12\" placeholder=\"\" title=\"\" style=\"width:98%;\"/>");
				baseSelectContext.append("</td>");
				baseSelectContext.append("</tr>");
			}else if("3".equals(prog)){
				baseSelectContext.append("<tr id='"+type+id +"' name='"+type+"Select' >");
				baseSelectContext.append("<td style=\"width:75px;text-align: right;padding-top: 13px;\">");
				baseSelectContext.append(desc);
				baseSelectContext.append("<input type=\"hidden\" name=\"selectVal\" value='" + value + "'/>");
				baseSelectContext.append("</td>");
				baseSelectContext.append("<td colspan=\"5\">");
				baseSelectContext.append("<select class=\"chosen-select form-control\" name=\"funValue\" id='" + id + "_FUN' data-placeholder=\"\" style=\"vertical-align:top;\">");
				Map<String, Object> progDescMap = (Map<String, Object>) m.get("progDescList");
				for(Map.Entry entry : progDescMap.entrySet()){
					baseSelectContext.append("<option value='"+entry.getKey()+"' >"+entry.getValue()+"</option>");
				}
				baseSelectContext.append("</select>");
				baseSelectContext.append("<input type=\"hidden\" name=\"valValue\" id='"+desc+"--"+prog+"_VAL' value='"+text+"' maxlength=\"12\" placeholder=\"\" title=\"\" style=\"width:98%;\"/>");
				baseSelectContext.append("</td>");
				baseSelectContext.append("</tr>");
			}else if("4".equals(prog)){
				baseSelectContext.append("<tr id='"+type+id +"' name='"+type+"Select' >");
				baseSelectContext.append("<td style=\"width:75px;text-align: right;padding-top: 13px;\">");
				baseSelectContext.append(desc);
				baseSelectContext.append("<input type=\"hidden\" name=\"selectVal\" value='" + value + "'/>");
				baseSelectContext.append("</td>");
				baseSelectContext.append("<td colspan=\"5\">");
				baseSelectContext.append("<select name=\"funValue\" id='" + id + "_FUN' class=\"multiselect\" multiple=\"\"> ");
				Map<String, Object> progDescMap = (Map<String, Object>) m.get("progDescList");
				String selected = Tools.getStringValue(m.get("RULEITEM_SELECT"));
				for(Map.Entry entry : progDescMap.entrySet()){
					if(selected.contains(entry.getKey().toString())){
						baseSelectContext.append("<option selected=\"selected\" value='"+entry.getKey()+"' >"+entry.getValue()+"</option>");
					}else{
						baseSelectContext.append("<option value='"+entry.getKey()+"' >"+entry.getValue()+"</option>");
					}
				}
				baseSelectContext.append("</select>");
				baseSelectContext.append("<input type=\"hidden\" name=\"valValue\" id='"+desc+"--"+prog+"_VAL' value='"+text+"' maxlength=\"12\" placeholder=\"\" title=\"\" style=\"width:98%;\"/>");
				baseSelectContext.append("</td>");
				baseSelectContext.append("</tr>");
			}else if("21".equals(prog)){
				baseSelectContext.append("<tr id='"+type+id +"' name='"+type+"Select' >");
				baseSelectContext.append("<td style=\"width:75px;text-align: right;padding-top: 13px;\">");
				baseSelectContext.append(desc);
				baseSelectContext.append("<input type=\"hidden\" name=\"selectVal\" value='" + value + "'/>");
				baseSelectContext.append("</td>");
				baseSelectContext.append("<td colspan=\"5\">");
				baseSelectContext.append("<select class=\"chosen-select form-control\" name=\"funValue\" id='" + id + "_FUN' data-placeholder=\"\" style=\"vertical-align:top;\">");
				Map<String, Object> progDescMap = (Map<String, Object>) m.get("progDescList");
				for(Map.Entry entry : progDescMap.entrySet()){
					baseSelectContext.append("<option value='"+entry.getKey()+"' >"+entry.getValue()+"</option>");
				}
				baseSelectContext.append("</select>");
				baseSelectContext.append("<input type=\"hidden\" name=\"valValue\" id='"+desc+"--"+prog+"_VAL' value='"+text+"' maxlength=\"12\" placeholder=\"\" title=\"\" style=\"width:98%;\"/>");
				baseSelectContext.append("</td>");
				baseSelectContext.append("</tr>");
			}else if("31".equals(prog)){
				baseSelectContext.append("<tr id='"+type+id +"' name='"+type+"Select' >");
				baseSelectContext.append("<td style=\"width:75px;text-align: right;padding-top: 13px;\">");
				baseSelectContext.append(desc);
				baseSelectContext.append("<input type=\"hidden\" name=\"selectVal\" value='" + value + "'/>");
				baseSelectContext.append("</td>");
				baseSelectContext.append("<td colspan=\"5\">");
				baseSelectContext.append("<select class=\"chosen-select form-control\" name=\"funValue\" id='" + id + "_FUN' data-placeholder=\"\" style=\"vertical-align:top;\">");
				Map<String, Object> progDescMap = (Map<String, Object>) m.get("progDescList");
				for(Map.Entry entry : progDescMap.entrySet()){
					baseSelectContext.append("<option value='"+entry.getKey()+"' >"+entry.getValue()+"</option>");
				}
				baseSelectContext.append("</select>");
				baseSelectContext.append("<input type=\"hidden\" name=\"valValue\" id='"+desc+"--"+prog+"_VAL' value='"+text+"' maxlength=\"12\" placeholder=\"\" title=\"\" style=\"width:98%;\"/>");
				baseSelectContext.append("</td>");
				baseSelectContext.append("</tr>");
			}else if("41".equals(prog)){
				baseSelectContext.append("<tr id='"+type+id +"' name='"+type+"Select' >");
				baseSelectContext.append("<td style=\"width:75px;text-align: right;padding-top: 13px;\">");
				baseSelectContext.append(desc);
				baseSelectContext.append("<input type=\"hidden\" name=\"selectVal\" value='" + value + "'/>");
				baseSelectContext.append("</td>");
				baseSelectContext.append("<td colspan=\"5\">");
				baseSelectContext.append("<select name=\"funValue\" id='" + id + "_FUN' class=\"multiselect\" multiple=\"\"> ");
				Map<String, Object> progDescMap = (Map<String, Object>) m.get("progDescList");
				String selected = Tools.getStringValue(m.get("RULEITEM_SELECT"));
				for(Map.Entry entry : progDescMap.entrySet()){
					if(selected.contains(entry.getKey().toString())){
						baseSelectContext.append("<option selected=\"selected\" value='"+entry.getKey()+"' >"+entry.getValue()+"</option>");
					}else{
						baseSelectContext.append("<option value='"+entry.getKey()+"' >"+entry.getValue()+"</option>");
					}
				}
				baseSelectContext.append("</select>");
				baseSelectContext.append("<input type=\"hidden\" name=\"valValue\" id='"+desc+"--"+prog+"_VAL' value='"+text+"' maxlength=\"12\" placeholder=\"\" title=\"\" style=\"width:98%;\"/>");
				baseSelectContext.append("</td>");
				baseSelectContext.append("</tr>");
			}
			
		}
		return baseSelectContext.toString();
	}
	
	public String getEditCalHtml(List<Map<String, Object>> calList) throws Exception {
		//规则状态
		PageData pd = new PageData();
		pd.put("TABLE_NAME", "BON_BASIC_RULE_TBL");
		pd.put("TABLE_COLUMN", "STATUS");
		List<Map<String, Object>> list = dictionariesService.listDicService(pd);
		StringBuilder result = new StringBuilder();
		for(Map<String, Object> cal : calList){
			result.append("<div class=\"widget-box\">");
			result.append("<div class=\"widget-header\">");
			result.append("<h5 class=\"widget-title\">规则</h5>");
			result.append("<div class=\"widget-toolbar\">");
			result.append("<a href=\"#\" data-action=\"close\">");
			result.append("<i class=\"ace-icon fa fa-times\"></i>");
			result.append("</a>");
			result.append("</div>");
			result.append("</div>");
			result.append("<div class=\"widget-body\">");
			result.append("<div class=\"widget-main\">");
			result.append("<table id=\"cal_rule_table\" name=\"cal_rule_table\" class=\"table table-striped table-bordered table-hover\">");
			result.append("<tr>");
			result.append("<td style=\"width: 75px; text-align: right; padding-top: 13px;\">规则编码:</td>");
			result.append("<td style=\"width: 200px;\">");
			String ruleCode = Tools.getStringValue(cal.get("RULE_CODE"));
			result.append("<input type=\"text\" name=\"CAL_RULE_CODE\" id=\"CAL_RULE_CODE\" value='"+ruleCode+"' maxlength=\"18\" placeholder=\"\" title=\"规则编码\" style=\"width: 98%;\" readonly=\"readonly\"/></td>");
			result.append("<td style=\"width: 75px; text-align: right; padding-top: 13px;\">规则名称:</td>");
			String ruleName = Tools.getStringValue(cal.get("RULE_NAME"));
			result.append("<td style=\"\"><input type=\"text\" name=\"CAL_RULE_NAME\" id=\"CAL_RULE_NAME\" value='"+ruleName+"' maxlength=\"30\" placeholder=\"\" title=\"规则名称\" style=\"width: 98%;\" /></td>");
			result.append("<td style=\"width: 75px; text-align: right; padding-top: 13px;\">状态:</td>");
			result.append("<td style=\"width: 180px;\">");
			result.append("<select class=\"chosen-select form-control\" name=\"CAL_STATUS\" id=\"CAL_STATUS\" data-placeholder=\"\" style=\"vertical-align: top; width: 98%;\">");
			String status = Tools.getStringValue(cal.get("STATUS"));
			for(Map<String, Object> m : list){
				String bianMa = Tools.getStringValue(m.get("BIANMA"));
				String name = Tools.getStringValue(m.get("NAME"));
				if(bianMa.equals(status)){
					result.append("<option selected=\"selected\" value='"+bianMa+"'>"+name+"</option>");
				}else{
					result.append("<option value='"+bianMa+"'>"+name+"</option>");
				}
			}
			result.append("</select></td>");
			result.append("</tr>");
			result.append("<tr>");
			result.append("<td style=\"width: 100px; text-align: right; padding-top: 13px;\">添加元素:</td>");
			result.append("<td colspan=\"6\">");
			result.append("<select multiple=\"\" class=\"chosen-select form-control\" name=\"CAL_RULE_SELECT\" data-placeholder=\"添加元素...\">");
			
			List<Map<String, Object>> calSelect = (List<Map<String, Object>>) cal.get("calSelect");
			List<Map<String, Object>> itemsList = listItems(pd);
			for(Map<String, Object> m : itemsList){
				String code = Tools.getStringValue(m.get("RULEITEM_CODE"));
				String prog = Tools.getStringValue(m.get("RULEITEM_PROG"));
				String desc = Tools.getStringValue(m.get("RULEITEM_DESC"));
				boolean flag = false;
				for(Map<String, Object> cs : calSelect){
					String calRuleCode = Tools.getStringValue(cs.get("RULEITEM_CODE"));
					if(code.equals(calRuleCode)){
						flag = true;
					}
				}
				if(flag){
					result.append("<option selected=\"selected\" value='"+code + "--" + prog + "'>" + desc + "</option>");
				}else{
					result.append("<option value='"+code + "--" + prog + "'>" + desc + "</option>");
				}
			}
			result.append("</select>");
			result.append("<input name=\"table_value\" type=\"hidden\" value=\"\" /></td>");
			result.append("</tr>");
			result.append("<tr>");
			result.append("<td style=\"width: 100px; text-align: right; padding-top: 13px;\">积分公式类型:</td>");
			result.append("<td>");
			result.append("<select class=\"chosen-select form-control\" name=\"CAL_FORMULA_TYPE\" id=\"CAL_FORMULA_TYPE\" data-placeholder=\"公式类型\" style=\"vertical-align: top;\">");
			result.append("<option value=\"\"></option>");
			//规则积分公式类型
			pd.put("TABLE_NAME", "BON_BASIC_RULE_TBL");
			pd.put("TABLE_COLUMN", "FORMULA_TYPE");
			String formulaType = Tools.getStringValue(cal.get("FORMULA_TYPE"));
			List<Map<String, Object>> formulaTypeList = dictionariesService.listDicService(pd);
			for(Map<String, Object> m : formulaTypeList){
				String bianMa = Tools.getStringValue(m.get("BIANMA"));
				String name = Tools.getStringValue(m.get("NAME"));
				if(bianMa.equals(formulaType)){
					result.append("<option selected=\"selected\" value='"+bianMa+"'>"+name+"</option>");
				}else{
					result.append("<option value='"+bianMa+"'>"+name+"</option>");
				}
			}
			result.append("</select>");
			result.append("</td>");
			result.append("<td style=\"width: 100px; text-align: right; padding-top: 13px;\">积分规则公式:</td>");
			result.append("<td colspan=\"3\">");
			String calRuleFormula = Tools.getStringValue(cal.get("RULE_FORMULA"));
			result.append("<input type=\"text\" name=\"CAL_RULE_FORMULA\" id=\"CAL_RULE_FORMULA\" value='"+calRuleFormula+"' maxlength=\"200\" placeholder=\"\" title=\"积分规则公式\" style=\"width: 98%;\" />");
			result.append("</td>");
			result.append("</tr>");
			
			result.append(getRuleContent(calSelect, "cal"));
			
			result.append("</table>");
			result.append("</div>");
			result.append("</div>");
			result.append("</div>");
		}
		return result.toString();
	}
	
	public String getCalHtml() throws Exception{
		//规则状态
		PageData pd = new PageData();
		pd.put("TABLE_NAME", "BON_BASIC_RULE_TBL");
		pd.put("TABLE_COLUMN", "STATUS");
		List<Map<String, Object>> list = dictionariesService.listDicService(pd);
				
		//生成计算规则编码
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < 18; i++){
			char c = (char)(Math.random()*26+65);
			String s = String.valueOf(c);
			sb.append(s);
		}
		StringBuilder result = new StringBuilder();
		result.append("<div class=\"widget-box\">");
		result.append("<div class=\"widget-header\">");
		result.append("<h5 class=\"widget-title\">规则</h5>");
		result.append("<div class=\"widget-toolbar\">");
		result.append("<a href=\"#\" data-action=\"close\">");
		result.append("<i class=\"ace-icon fa fa-times\"></i>");
		result.append("</a>");
		result.append("</div>");
		result.append("</div>");
		result.append("<div class=\"widget-body\">");
		result.append("<div class=\"widget-main\">");
		result.append("<table id=\"cal_rule_table\" name=\"cal_rule_table\" class=\"table table-striped table-bordered table-hover\">");
		result.append("<tr>");
		result.append("<td style=\"width: 75px; text-align: right; padding-top: 13px;\">规则编码:</td>");
		result.append("<td style=\"width: 200px;\">");
		result.append("<input type=\"text\" name=\"CAL_RULE_CODE\" id=\"CAL_RULE_CODE\" value='"+sb.toString()+"' maxlength=\"18\" placeholder=\"\" title=\"规则编码\" style=\"width: 98%;\" readonly=\"readonly\"/></td>");
		result.append("<td style=\"width: 75px; text-align: right; padding-top: 13px;\">规则名称:</td>");
		result.append("<td style=\"\"><input type=\"text\" name=\"CAL_RULE_NAME\" id=\"CAL_RULE_NAME\" value=\"\" maxlength=\"30\" placeholder=\"\" title=\"规则名称\" style=\"width: 98%;\" /></td>");
		result.append("<td style=\"width: 75px; text-align: right; padding-top: 13px;\">状态:</td>");
		result.append("<td style=\"width: 180px;\">");
		result.append("<select class=\"chosen-select form-control\" name=\"CAL_STATUS\" id=\"CAL_STATUS\" data-placeholder=\"\" style=\"vertical-align: top; width: 98%;\">");
		for(Map<String, Object> m : list){
			String bianMa = Tools.getStringValue(m.get("BIANMA"));
			String name = Tools.getStringValue(m.get("NAME"));
			result.append("<option value='"+bianMa+"'>"+name+"</option>");
		}
		result.append("</select></td>");
		result.append("</tr>");
		result.append("<tr>");
		result.append("<td style=\"width: 100px; text-align: right; padding-top: 13px;\">添加元素:</td>");
		result.append("<td colspan=\"6\">");
		result.append("<select multiple=\"\" class=\"chosen-select form-control\" name=\"CAL_RULE_SELECT\" data-placeholder=\"添加元素...\">");
		
		
		List<Map<String, Object>> itemsList = listItems(pd);
		for(Map<String, Object> m : itemsList){
			String code = Tools.getStringValue(m.get("RULEITEM_CODE"));
			String prog = Tools.getStringValue(m.get("RULEITEM_PROG"));
			String desc = Tools.getStringValue(m.get("RULEITEM_DESC"));
			result.append("<option value='"+code + "--" + prog + "'>" + desc + "</option>");
		}
		
		result.append("</select>");
		result.append("<input name=\"table_value\" type=\"hidden\" value=\"\" /></td>");
		result.append("</tr>");
		result.append("<tr>");
		result.append("<td style=\"width: 100px; text-align: right; padding-top: 13px;\">积分公式类型:</td>");
		result.append("<td>");
		result.append("<select class=\"chosen-select form-control\" name=\"CAL_FORMULA_TYPE\" id=\"CAL_FORMULA_TYPE\" data-placeholder=\"公式类型\" style=\"vertical-align: top;\">");
		result.append("<option value=\"\"></option>");
		//规则积分公式类型
		pd.put("TABLE_NAME", "BON_BASIC_RULE_TBL");
		pd.put("TABLE_COLUMN", "FORMULA_TYPE");
		List<Map<String, Object>> formulaTypeList = dictionariesService.listDicService(pd);
		for(Map<String, Object> m : formulaTypeList){
			String bianMa = Tools.getStringValue(m.get("BIANMA"));
			String name = Tools.getStringValue(m.get("NAME"));
			result.append("<option value='"+bianMa+"'>"+name+"</option>");
		}
		result.append("</select>");
		result.append("</td>");
		result.append("<td style=\"width: 100px; text-align: right; padding-top: 13px;\">积分规则公式:</td>");
		result.append("<td colspan=\"3\">");
		result.append("<input type=\"text\" name=\"CAL_RULE_FORMULA\" id=\"CAL_RULE_FORMULA\" value=\"\" maxlength=\"200\" placeholder=\"\" title=\"积分规则公式\" style=\"width: 98%;\" />");
		result.append("</td>");
		result.append("</tr>");
		result.append("</table>");
		result.append("</div>");
		result.append("</div>");
		result.append("</div>");
		return result.toString();
	}
}


