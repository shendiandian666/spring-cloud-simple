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
import com.zyx.service.infoquery.MerchantListService;
import com.zyx.util.Tools;
import com.zyx.web.WebController;
import com.zyx.service.system.DictionariesService;
/** 
 * 说明：
 * 创建人：kbky
 * 创建时间：
 */
@Controller("infoqueryMerchantListController")
@RequestMapping(value="/infoquery")
public class MerchantListController extends WebController {
	
	@Autowired
	private MerchantListService infoqueryMerchantListService;
	@Autowired
	private DictionariesService dictionariesService;
	
	/*
	 * 查询
	 */
	@RequestMapping("/merchantlistList")
	public String list(Map<String, Object> map) throws Exception{
		PageData pd = this.getPageData();
		int pageNum = Page.getPageNum(pd);
		int pageSize = Page.getPageSize(pd);
		List<Map<String, Object>> list = infoqueryMerchantListService.listService(pageNum, pageSize, pd);
		PageInfo<Map<String, Object>> pageInfo = new PageInfo<Map<String, Object>>(list);
		map.put("page", pageInfo);
		map.put("merchantlistList", list);
		map.put("pd", pd);
		return "/infoquery/merchantlist/merchantlist_list";
	}
	
	/*
	 * 保存
	 */
	@RequestMapping("/merchantlistSave")
	@ResponseBody
	public String save(Map<String, Object> map) throws Exception{
		return infoqueryMerchantListService.saveService(getPageData());
	}
	
	/*
	 * 修改
	 */
	@RequestMapping("/merchantlistUpdate")
	@ResponseBody
	public String update(Map<String, Object> map) throws Exception{
		return infoqueryMerchantListService.updateService(getPageData());
	}
	
	/*
	 * 删除
	 */
	@RequestMapping("/merchantlistDel")
	@ResponseBody
	public String del(Map<String, Object> map) throws Exception{
		return infoqueryMerchantListService.delService(getPageData());
	}
	
	/*
	 * 跳转到新增页面
	 */
	@RequestMapping("/merchantlistAdd")
	public String add(Map<String, Object> map) throws Exception{
		PageData pd = getPageData();
		pd.put("TABLE_NAME", "BON_MERCHANT_LIST_TBL");
		pd.put("TABLE_COLUMN", "TYPE");
		map.put("typeList", dictionariesService.listDicService(pd));
		pd.put("TABLE_NAME", "BON_MERCHANT_LIST_TBL");
		pd.put("TABLE_COLUMN", "STATUS");
		map.put("statusList", dictionariesService.listDicService(pd));
		map.put("action", "Save");
		map.put("pd", pd);
		map.put("url", Tools.getStringValue(pd.get("url")));
		return "/infoquery/merchantlist/merchantlist_edit";
	}
	
	/*
	 * 跳转到修改页面
	 */
	@RequestMapping("/merchantlistEdit")
	public String edit(Map<String, Object> map) throws Exception{
		PageData pd = getPageData();
		Map<String, Object> result = infoqueryMerchantListService.editService(pd);
		map.put("merchantlist", result);
		pd.put("TABLE_NAME", "BON_MERCHANT_LIST_TBL");
		pd.put("TABLE_COLUMN", "TYPE");
		map.put("typeList", dictionariesService.listDicService(pd));
		pd.put("TABLE_NAME", "BON_MERCHANT_LIST_TBL");
		pd.put("TABLE_COLUMN", "STATUS");
		map.put("statusList", dictionariesService.listDicService(pd));
		map.put("action", "Update");
		map.put("pd", pd);
		map.put("url", Tools.getStringValue(pd.get("url")));
		return "/infoquery/merchantlist/merchantlist_edit";
	}
	
	/*
	 * 跳转到详情页面
	 */
	@RequestMapping("/merchantlistDetail")
	public String detail(Map<String, Object> map) throws Exception{
		PageData pd = getPageData();
		Map<String, Object> result = infoqueryMerchantListService.editService(pd);
		map.put("merchantlist", result);
		map.put("pd", pd);
		map.put("url", Tools.getStringValue(pd.get("url")));
		return "/infoquery/merchantlist/merchantlist_detail";
	}
}
