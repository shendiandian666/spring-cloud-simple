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
import com.zyx.service.infoquery.DataExchangeService;
import com.zyx.util.Tools;
import com.zyx.web.WebController;
import com.zyx.service.system.DictionariesService;
/** 
 * 说明：
 * 创建人：kbky
 * 创建时间：
 */
@Controller("infoqueryDataExchangeController")
@RequestMapping(value="/infoquery")
public class DataExchangeController extends WebController {
	
	@Autowired
	private DataExchangeService infoqueryDataExchangeService;
	@Autowired
	private DictionariesService dictionariesService;
	
	/*
	 * 查询
	 */
	@RequestMapping("/dataexchangeList")
	public String list(Map<String, Object> map) throws Exception{
		PageData pd = this.getPageData();
		int pageNum = Page.getPageNum(pd);
		int pageSize = Page.getPageSize(pd);
		List<Map<String, Object>> list = infoqueryDataExchangeService.listService(pageNum, pageSize, pd);
		PageInfo<Map<String, Object>> pageInfo = new PageInfo<Map<String, Object>>(list);
		map.put("page", pageInfo);
		map.put("dataexchangeList", list);
		map.put("pd", pd);
		return "/infoquery/dataexchange/dataexchange_list";
	}
	
	/*
	 * 保存
	 */
	@RequestMapping("/dataexchangeSave")
	@ResponseBody
	public String save(Map<String, Object> map) throws Exception{
		return infoqueryDataExchangeService.saveService(getPageData());
	}
	
	/*
	 * 修改
	 */
	@RequestMapping("/dataexchangeUpdate")
	@ResponseBody
	public String update(Map<String, Object> map) throws Exception{
		return infoqueryDataExchangeService.updateService(getPageData());
	}
	
	/*
	 * 删除
	 */
	@RequestMapping("/dataexchangeDel")
	@ResponseBody
	public String del(Map<String, Object> map) throws Exception{
		return infoqueryDataExchangeService.delService(getPageData());
	}
	
	/*
	 * 跳转到新增页面
	 */
	@RequestMapping("/dataexchangeAdd")
	public String add(Map<String, Object> map) throws Exception{
		PageData pd = getPageData();
		pd.put("TABLE_NAME", "BON_DATA_EXCHANGE_TBL");
		pd.put("TABLE_COLUMN", "YWLX");
		map.put("ywlxList", dictionariesService.listDicService(pd));
		pd.put("TABLE_NAME", "BON_DATA_EXCHANGE_TBL");
		pd.put("TABLE_COLUMN", "POINTS_NO");
		map.put("pointsnoList", dictionariesService.listDicService(pd));
		pd.put("TABLE_NAME", "BON_DATA_EXCHANGE_TBL");
		pd.put("TABLE_COLUMN", "POINTS_TYPE");
		map.put("pointstypeList", dictionariesService.listDicService(pd));
		pd.put("TABLE_NAME", "BON_DATA_EXCHANGE_TBL");
		pd.put("TABLE_COLUMN", "ORGCODE");
		map.put("orgcodeList", dictionariesService.listDicService(pd));
		pd.put("TABLE_NAME", "BON_DATA_EXCHANGE_TBL");
		pd.put("TABLE_COLUMN", "POINTS_SIGN");
		map.put("pointssignList", dictionariesService.listDicService(pd));
		pd.put("TABLE_NAME", "BON_DATA_EXCHANGE_TBL");
		pd.put("TABLE_COLUMN", "STATUS");
		map.put("statusList", dictionariesService.listDicService(pd));
		pd.put("TABLE_NAME", "BON_DATA_EXCHANGE_TBL");
		pd.put("TABLE_COLUMN", "INPUT_MTH");
		map.put("inputmthList", dictionariesService.listDicService(pd));
		map.put("action", "Save");
		map.put("pd", pd);
		map.put("url", Tools.getStringValue(pd.get("url")));
		return "/infoquery/dataexchange/dataexchange_edit";
	}
	
	/*
	 * 跳转到修改页面
	 */
	@RequestMapping("/dataexchangeEdit")
	public String edit(Map<String, Object> map) throws Exception{
		PageData pd = getPageData();
		Map<String, Object> result = infoqueryDataExchangeService.editService(pd);
		map.put("dataexchange", result);
		pd.put("TABLE_NAME", "BON_DATA_EXCHANGE_TBL");
		pd.put("TABLE_COLUMN", "YWLX");
		map.put("ywlxList", dictionariesService.listDicService(pd));
		pd.put("TABLE_NAME", "BON_DATA_EXCHANGE_TBL");
		pd.put("TABLE_COLUMN", "POINTS_NO");
		map.put("pointsnoList", dictionariesService.listDicService(pd));
		pd.put("TABLE_NAME", "BON_DATA_EXCHANGE_TBL");
		pd.put("TABLE_COLUMN", "POINTS_TYPE");
		map.put("pointstypeList", dictionariesService.listDicService(pd));
		pd.put("TABLE_NAME", "BON_DATA_EXCHANGE_TBL");
		pd.put("TABLE_COLUMN", "ORGCODE");
		map.put("orgcodeList", dictionariesService.listDicService(pd));
		pd.put("TABLE_NAME", "BON_DATA_EXCHANGE_TBL");
		pd.put("TABLE_COLUMN", "POINTS_SIGN");
		map.put("pointssignList", dictionariesService.listDicService(pd));
		pd.put("TABLE_NAME", "BON_DATA_EXCHANGE_TBL");
		pd.put("TABLE_COLUMN", "STATUS");
		map.put("statusList", dictionariesService.listDicService(pd));
		pd.put("TABLE_NAME", "BON_DATA_EXCHANGE_TBL");
		pd.put("TABLE_COLUMN", "INPUT_MTH");
		map.put("inputmthList", dictionariesService.listDicService(pd));
		map.put("action", "Update");
		map.put("pd", pd);
		map.put("url", Tools.getStringValue(pd.get("url")));
		return "/infoquery/dataexchange/dataexchange_edit";
	}
	
	/*
	 * 跳转到详情页面
	 */
	@RequestMapping("/dataexchangeDetail")
	public String detail(Map<String, Object> map) throws Exception{
		PageData pd = getPageData();
		Map<String, Object> result = infoqueryDataExchangeService.editService(pd);
		map.put("dataexchange", result);
		map.put("pd", pd);
		map.put("url", Tools.getStringValue(pd.get("url")));
		return "/infoquery/dataexchange/dataexchange_detail";
	}
}
