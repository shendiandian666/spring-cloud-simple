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
import com.zyx.service.infoquery.LimitCntlService;
import com.zyx.util.Tools;
import com.zyx.web.WebController;
import com.zyx.service.system.DictionariesService;
/** 
 * 说明：
 * 创建人：kbky
 * 创建时间：
 */
@Controller("infoqueryLimitCntlController")
@RequestMapping(value="/infoquery")
public class LimitCntlController extends WebController {
	
	@Autowired
	private LimitCntlService infoqueryLimitCntlService;
	@Autowired
	private DictionariesService dictionariesService;
	
	/*
	 * 查询
	 */
	@RequestMapping("/limitcntlList")
	public String list(Map<String, Object> map) throws Exception{
		PageData pd = this.getPageData();
		int pageNum = Page.getPageNum(pd);
		int pageSize = Page.getPageSize(pd);
		List<Map<String, Object>> list = infoqueryLimitCntlService.listService(pageNum, pageSize, pd);
		PageInfo<Map<String, Object>> pageInfo = new PageInfo<Map<String, Object>>(list);
		map.put("page", pageInfo);
		map.put("limitcntlList", list);
		map.put("pd", pd);
		return "/infoquery/limitcntl/limitcntl_list";
	}
	
	/*
	 * 保存
	 */
	@RequestMapping("/limitcntlSave")
	@ResponseBody
	public String save(Map<String, Object> map) throws Exception{
		return infoqueryLimitCntlService.saveService(getPageData());
	}
	
	/*
	 * 修改
	 */
	@RequestMapping("/limitcntlUpdate")
	@ResponseBody
	public String update(Map<String, Object> map) throws Exception{
		return infoqueryLimitCntlService.updateService(getPageData());
	}
	
	/*
	 * 删除
	 */
	@RequestMapping("/limitcntlDel")
	@ResponseBody
	public String del(Map<String, Object> map) throws Exception{
		return infoqueryLimitCntlService.delService(getPageData());
	}
	
	/*
	 * 跳转到新增页面
	 */
	@RequestMapping("/limitcntlAdd")
	public String add(Map<String, Object> map) throws Exception{
		PageData pd = getPageData();
		pd.put("TABLE_NAME", "BON_LIMIT_CNTL_TBL");
		pd.put("TABLE_COLUMN", "LONGTIME_FLAG");
		map.put("longtimeflagList", dictionariesService.listDicService(pd));
		pd.put("TABLE_NAME", "BON_LIMIT_CNTL_TBL");
		pd.put("TABLE_COLUMN", "LIMIT_YMD_MODE");
		map.put("limitymdmodeList", dictionariesService.listDicService(pd));
		pd.put("TABLE_NAME", "BON_LIMIT_CNTL_TBL");
		pd.put("TABLE_COLUMN", "STATUS");
		map.put("statusList", dictionariesService.listDicService(pd));
		map.put("action", "Save");
		map.put("pd", pd);
		map.put("url", Tools.getStringValue(pd.get("url")));
		return "/infoquery/limitcntl/limitcntl_edit";
	}
	
	/*
	 * 跳转到修改页面
	 */
	@RequestMapping("/limitcntlEdit")
	public String edit(Map<String, Object> map) throws Exception{
		PageData pd = getPageData();
		Map<String, Object> result = infoqueryLimitCntlService.editService(pd);
		map.put("limitcntl", result);
		pd.put("TABLE_NAME", "BON_LIMIT_CNTL_TBL");
		pd.put("TABLE_COLUMN", "LONGTIME_FLAG");
		map.put("longtimeflagList", dictionariesService.listDicService(pd));
		pd.put("TABLE_NAME", "BON_LIMIT_CNTL_TBL");
		pd.put("TABLE_COLUMN", "LIMIT_YMD_MODE");
		map.put("limitymdmodeList", dictionariesService.listDicService(pd));
		pd.put("TABLE_NAME", "BON_LIMIT_CNTL_TBL");
		pd.put("TABLE_COLUMN", "STATUS");
		map.put("statusList", dictionariesService.listDicService(pd));
		map.put("action", "Update");
		map.put("pd", pd);
		map.put("url", Tools.getStringValue(pd.get("url")));
		return "/infoquery/limitcntl/limitcntl_edit";
	}
	
	/*
	 * 跳转到详情页面
	 */
	@RequestMapping("/limitcntlDetail")
	public String detail(Map<String, Object> map) throws Exception{
		PageData pd = getPageData();
		Map<String, Object> result = infoqueryLimitCntlService.editService(pd);
		map.put("limitcntl", result);
		map.put("pd", pd);
		map.put("url", Tools.getStringValue(pd.get("url")));
		return "/infoquery/limitcntl/limitcntl_detail";
	}
}
