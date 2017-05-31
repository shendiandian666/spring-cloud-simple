package com.zyx.web.rule;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.github.pagehelper.PageInfo;
import com.zyx.model.Page;
import com.zyx.model.PageData;
import com.zyx.service.rule.BasicItemService;
import com.zyx.util.Tools;
import com.zyx.web.WebController;
import com.zyx.service.system.DictionariesService;
/** 
 * 说明：
 * 创建人：kbky
 * 创建时间：
 */
@Controller("ruleBasicItemController")
@RequestMapping(value="/rule")
public class BasicItemController extends WebController {
	
	@Autowired
	private BasicItemService ruleBasicItemService;
	@Autowired
	private DictionariesService dictionariesService;
	
	/*
	 * 查询
	 */
	@RequestMapping("/basicitemList")
	public String list(Map<String, Object> map) throws Exception{
		PageData pd = this.getPageData();
		int pageNum = Page.getPageNum(pd);
		int pageSize = Page.getPageSize(pd);
		List<Map<String, Object>> list = ruleBasicItemService.listService(pageNum, pageSize, pd);
		PageInfo<Map<String, Object>> pageInfo = new PageInfo<Map<String, Object>>(list);
		map.put("page", pageInfo);
		map.put("basicitemList", list);
		map.put("pd", pd);
		return "/rule/basicitem/basicitem_list";
	}
	
	/*
	 * 保存
	 */
	@RequestMapping("/basicitemSave")
	@ResponseBody
	public String save(Map<String, Object> map) throws Exception{
		return ruleBasicItemService.saveService(getPageData());
	}
	
	/*
	 * 修改
	 */
	@RequestMapping("/basicitemUpdate")
	@ResponseBody
	public String update(Map<String, Object> map) throws Exception{
		return ruleBasicItemService.updateService(getPageData());
	}
	
	/*
	 * 删除
	 */
	@RequestMapping("/basicitemDel")
	@ResponseBody
	public String del(Map<String, Object> map) throws Exception{
		return ruleBasicItemService.delService(getPageData());
	}
	
	/*
	 * 跳转到新增页面
	 */
	@RequestMapping("/basicitemAdd")
	public String add(Map<String, Object> map) throws Exception{
		PageData pd = getPageData();
		pd.put("TABLE_NAME", "BON_BASIC_ITEM_TBL");
		pd.put("TABLE_COLUMN", "RULEITEM_PROG");
		map.put("ruleitemprogList", dictionariesService.listDicService(pd));
		pd.put("TABLE_NAME", "BON_BASIC_ITEM_TBL");
		pd.put("TABLE_COLUMN", "RULEITEM_TBL");
		map.put("ruleitemtblList", dictionariesService.listDicService(pd));
		pd.put("TABLE_NAME", "BON_BASIC_ITEM_TBL");
		pd.put("TABLE_COLUMN", "RULEITEM_COL");
		map.put("ruleitemcolList", dictionariesService.listDicService(pd));
		pd.put("TABLE_NAME", "BON_BASIC_ITEM_TBL");
		pd.put("TABLE_COLUMN", "RULEITEM_TYPE");
		map.put("ruleitemtypeList", dictionariesService.listDicService(pd));
		pd.put("TABLE_NAME", "BON_BASIC_ITEM_TBL");
		pd.put("TABLE_COLUMN", "RULEITEM_FUN");
		map.put("ruleitemfunList", dictionariesService.listDicService(pd));
		pd.put("TABLE_NAME", "BON_BASIC_ITEM_TBL");
		pd.put("TABLE_COLUMN", "STATUS");
		map.put("statusList", dictionariesService.listDicService(pd));
		map.put("action", "Save");
		map.put("pd", pd);
		map.put("url", Tools.getStringValue(pd.get("url")));
		return "/rule/basicitem/basicitem_edit";
	}
	
	/*
	 * 跳转到修改页面
	 */
	@RequestMapping("/basicitemEdit")
	public String edit(Map<String, Object> map) throws Exception{
		PageData pd = getPageData();
		Map<String, Object> result = ruleBasicItemService.editService(pd);
		map.put("basicitem", result);
		pd.put("TABLE_NAME", "BON_BASIC_ITEM_TBL");
		pd.put("TABLE_COLUMN", "RULEITEM_PROG");
		map.put("ruleitemprogList", dictionariesService.listDicService(pd));
		pd.put("TABLE_NAME", "BON_BASIC_ITEM_TBL");
		pd.put("TABLE_COLUMN", "RULEITEM_TBL");
		map.put("ruleitemtblList", dictionariesService.listDicService(pd));
		//查询数据表级联的字段
		String bianMa = Tools.getStringValue(result.get("RULEITEM_TBL"));
		List<Map<String, Object>> list = dictionariesService.listDicService(pd);
		for(Map<String, Object> m : list){
			String id = Tools.getStringValue(m.get("DICTIONARIES_ID"));
			String bm = Tools.getStringValue(m.get("BIANMA"));
			if(bianMa.equals(bm)){
				pd.put("parent_id", id);
				map.put("ruleitemcolList", dictionariesService.listDicByParent(pd));
			}
		}
		
		pd.put("TABLE_NAME", "BON_BASIC_ITEM_TBL");
		pd.put("TABLE_COLUMN", "RULEITEM_TYPE");
		map.put("ruleitemtypeList", dictionariesService.listDicService(pd));
		pd.put("TABLE_NAME", "BON_BASIC_ITEM_TBL");
		pd.put("TABLE_COLUMN", "RULEITEM_FUN");
		map.put("ruleitemfunList", dictionariesService.listDicService(pd));
		pd.put("TABLE_NAME", "BON_BASIC_ITEM_TBL");
		pd.put("TABLE_COLUMN", "STATUS");
		map.put("statusList", dictionariesService.listDicService(pd));
		
		map.put("action", "Update");
		map.put("pd", pd);
		map.put("url", Tools.getStringValue(pd.get("url")));
		return "/rule/basicitem/basicitem_edit";
	}
	
	/*
	 * 跳转到详情页面
	 */
	@RequestMapping("/basicitemDetail")
	public String detail(Map<String, Object> map) throws Exception{
		PageData pd = getPageData();
		Map<String, Object> result = ruleBasicItemService.editService(pd);
		
		String itemCol = Tools.getStringValue(result.get("RULEITEM_COL"));
		
		pd.put("TABLE_NAME", "BON_BASIC_ITEM_TBL");
		pd.put("TABLE_COLUMN", "RULEITEM_TBL");
		//查询数据表级联的字段
		String bianMa = Tools.getStringValue(result.get("RULEITEM_TBL"));
		List<Map<String, Object>> list = dictionariesService.listDicService(pd);
		for(Map<String, Object> m : list){
			String id = Tools.getStringValue(m.get("DICTIONARIES_ID"));
			String bm = Tools.getStringValue(m.get("BIANMA"));
			if(bianMa.equals(bm)){
				pd.put("parent_id", id);
				List<Map<String, Object>> cols = dictionariesService.listDicByParent(pd);
				for(Map<String, Object> c : cols){
					String col = Tools.getStringValue(c.get("BIANMA"));
					if(col.equals(itemCol)){
						result.put("RULEITEM_COL_DESC", Tools.getStringValue(c.get("NAME")));
					}
				}
				map.put("ruleitemcolList", dictionariesService.listDicByParent(pd));
			}
		}
		
		map.put("basicitem", result);
		map.put("pd", pd);
		map.put("url", Tools.getStringValue(pd.get("url")));
		return "/rule/basicitem/basicitem_detail";
	}
	
	/*
	 * 对应数据表级联下拉列表
	 */
	@RequestMapping("/basicitemGetCol")
	@ResponseBody
	public List<Map<String, Object>> getCols() throws Exception {
		PageData pd = getPageData();
		pd.put("TABLE_NAME", "BON_BASIC_ITEM_TBL");
		pd.put("TABLE_COLUMN", "RULEITEM_TBL");
		String bianMa = Tools.getStringValue(pd.get("BIANMA"));
		List<Map<String, Object>> list = dictionariesService.listDicService(pd);
		List<Map<String, Object>> result = Collections.emptyList();
		for(Map<String, Object> m : list){
			String id = Tools.getStringValue(m.get("DICTIONARIES_ID"));
			String bm = Tools.getStringValue(m.get("BIANMA"));
			if(bianMa.equals(bm)){
				pd.put("parent_id", id);
				result = dictionariesService.listDicByParent(pd);
			}
		}
		return result;
	}
	
}
