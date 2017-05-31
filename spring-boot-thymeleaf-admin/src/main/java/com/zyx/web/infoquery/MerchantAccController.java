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
import com.zyx.service.infoquery.MerchantAccService;
import com.zyx.util.Tools;
import com.zyx.web.WebController;
import com.zyx.service.system.DictionariesService;
/** 
 * 说明：
 * 创建人：kbky
 * 创建时间：
 */
@Controller("infoqueryMerchantAccController")
@RequestMapping(value="/infoquery")
public class MerchantAccController extends WebController {
	
	@Autowired
	private MerchantAccService infoqueryMerchantAccService;
	@Autowired
	private DictionariesService dictionariesService;
	
	/*
	 * 查询
	 */
	@RequestMapping("/merchantaccList")
	public String list(Map<String, Object> map) throws Exception{
		PageData pd = this.getPageData();
		int pageNum = Page.getPageNum(pd);
		int pageSize = Page.getPageSize(pd);
		List<Map<String, Object>> list = infoqueryMerchantAccService.listService(pageNum, pageSize, pd);
		PageInfo<Map<String, Object>> pageInfo = new PageInfo<Map<String, Object>>(list);
		map.put("page", pageInfo);
		map.put("merchantaccList", list);
		map.put("pd", pd);
		return "/infoquery/merchantacc/merchantacc_list";
	}
	
	/*
	 * 保存
	 */
	@RequestMapping("/merchantaccSave")
	@ResponseBody
	public String save(Map<String, Object> map) throws Exception{
		return infoqueryMerchantAccService.saveService(getPageData());
	}
	
	/*
	 * 修改
	 */
	@RequestMapping("/merchantaccUpdate")
	@ResponseBody
	public String update(Map<String, Object> map) throws Exception{
		return infoqueryMerchantAccService.updateService(getPageData());
	}
	
	/*
	 * 删除
	 */
	@RequestMapping("/merchantaccDel")
	@ResponseBody
	public String del(Map<String, Object> map) throws Exception{
		return infoqueryMerchantAccService.delService(getPageData());
	}
	
	/*
	 * 跳转到新增页面
	 */
	@RequestMapping("/merchantaccAdd")
	public String add(Map<String, Object> map) throws Exception{
		PageData pd = getPageData();
		pd.put("TABLE_NAME", "BON_MERCHANT_ACC_TBL");
		pd.put("TABLE_COLUMN", "TYPE");
		map.put("typeList", dictionariesService.listDicService(pd));
		pd.put("TABLE_NAME", "BON_MERCHANT_ACC_TBL");
		pd.put("TABLE_COLUMN", "STATUS");
		map.put("statusList", dictionariesService.listDicService(pd));
		map.put("action", "Save");
		map.put("pd", pd);
		map.put("url", Tools.getStringValue(pd.get("url")));
		return "/infoquery/merchantacc/merchantacc_edit";
	}
	
	/*
	 * 跳转到修改页面
	 */
	@RequestMapping("/merchantaccEdit")
	public String edit(Map<String, Object> map) throws Exception{
		PageData pd = getPageData();
		Map<String, Object> result = infoqueryMerchantAccService.editService(pd);
		map.put("merchantacc", result);
		pd.put("TABLE_NAME", "BON_MERCHANT_ACC_TBL");
		pd.put("TABLE_COLUMN", "TYPE");
		map.put("typeList", dictionariesService.listDicService(pd));
		pd.put("TABLE_NAME", "BON_MERCHANT_ACC_TBL");
		pd.put("TABLE_COLUMN", "STATUS");
		map.put("statusList", dictionariesService.listDicService(pd));
		map.put("action", "Update");
		map.put("pd", pd);
		map.put("url", Tools.getStringValue(pd.get("url")));
		return "/infoquery/merchantacc/merchantacc_edit";
	}
	
	/*
	 * 跳转到详情页面
	 */
	@RequestMapping("/merchantaccDetail")
	public String detail(Map<String, Object> map) throws Exception{
		PageData pd = getPageData();
		Map<String, Object> result = infoqueryMerchantAccService.editService(pd);
		map.put("merchantacc", result);
		map.put("pd", pd);
		map.put("url", Tools.getStringValue(pd.get("url")));
		return "/infoquery/merchantacc/merchantacc_detail";
	}
}
