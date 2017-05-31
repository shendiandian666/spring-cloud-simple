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
import com.zyx.service.infoquery.DataAccService;
import com.zyx.util.Tools;
import com.zyx.web.WebController;
import com.zyx.service.system.DictionariesService;
/** 
 * 说明：
 * 创建人：kbky
 * 创建时间：
 */
@Controller("infoqueryDataAccController")
@RequestMapping(value="/infoquery")
public class DataAccController extends WebController {
	
	@Autowired
	private DataAccService infoqueryDataAccService;
	@Autowired
	private DictionariesService dictionariesService;
	
	/*
	 * 查询
	 */
	@RequestMapping("/dataaccList")
	public String list(Map<String, Object> map) throws Exception{
		PageData pd = this.getPageData();
		int pageNum = Page.getPageNum(pd);
		int pageSize = Page.getPageSize(pd);
		List<Map<String, Object>> list = infoqueryDataAccService.listService(pageNum, pageSize, pd);
		PageInfo<Map<String, Object>> pageInfo = new PageInfo<Map<String, Object>>(list);
		map.put("page", pageInfo);
		map.put("dataaccList", list);
		map.put("pd", pd);
		return "/infoquery/dataacc/dataacc_list";
	}
	
	/*
	 * 保存
	 */
	@RequestMapping("/dataaccSave")
	@ResponseBody
	public String save(Map<String, Object> map) throws Exception{
		return infoqueryDataAccService.saveService(getPageData());
	}
	
	/*
	 * 修改
	 */
	@RequestMapping("/dataaccUpdate")
	@ResponseBody
	public String update(Map<String, Object> map) throws Exception{
		return infoqueryDataAccService.updateService(getPageData());
	}
	
	/*
	 * 删除
	 */
	@RequestMapping("/dataaccDel")
	@ResponseBody
	public String del(Map<String, Object> map) throws Exception{
		return infoqueryDataAccService.delService(getPageData());
	}
	
	/*
	 * 跳转到新增页面
	 */
	@RequestMapping("/dataaccAdd")
	public String add(Map<String, Object> map) throws Exception{
		PageData pd = getPageData();
		pd.put("TABLE_NAME", "BON_DATA_ACC_TBL");
		pd.put("TABLE_COLUMN", "POINTS_RANGE");
		map.put("pointsrangeList", dictionariesService.listDicService(pd));
		pd.put("TABLE_NAME", "BON_DATA_ACC_TBL");
		pd.put("TABLE_COLUMN", "TYPE");
		map.put("typeList", dictionariesService.listDicService(pd));
		pd.put("TABLE_NAME", "BON_DATA_ACC_TBL");
		pd.put("TABLE_COLUMN", "STATUS");
		map.put("statusList", dictionariesService.listDicService(pd));
		map.put("action", "Save");
		map.put("pd", pd);
		map.put("url", Tools.getStringValue(pd.get("url")));
		return "/infoquery/dataacc/dataacc_edit";
	}
	
	/*
	 * 跳转到修改页面
	 */
	@RequestMapping("/dataaccEdit")
	public String edit(Map<String, Object> map) throws Exception{
		PageData pd = getPageData();
		Map<String, Object> result = infoqueryDataAccService.editService(pd);
		map.put("dataacc", result);
		pd.put("TABLE_NAME", "BON_DATA_ACC_TBL");
		pd.put("TABLE_COLUMN", "POINTS_RANGE");
		map.put("pointsrangeList", dictionariesService.listDicService(pd));
		pd.put("TABLE_NAME", "BON_DATA_ACC_TBL");
		pd.put("TABLE_COLUMN", "TYPE");
		map.put("typeList", dictionariesService.listDicService(pd));
		pd.put("TABLE_NAME", "BON_DATA_ACC_TBL");
		pd.put("TABLE_COLUMN", "STATUS");
		map.put("statusList", dictionariesService.listDicService(pd));
		map.put("action", "Update");
		map.put("pd", pd);
		map.put("url", Tools.getStringValue(pd.get("url")));
		return "/infoquery/dataacc/dataacc_edit";
	}
	
	/*
	 * 跳转到详情页面
	 */
	@RequestMapping("/dataaccDetail")
	public String detail(Map<String, Object> map) throws Exception{
		PageData pd = getPageData();
		Map<String, Object> result = infoqueryDataAccService.editService(pd);
		map.put("dataacc", result);
		map.put("pd", pd);
		map.put("url", Tools.getStringValue(pd.get("url")));
		return "/infoquery/dataacc/dataacc_detail";
	}
}
