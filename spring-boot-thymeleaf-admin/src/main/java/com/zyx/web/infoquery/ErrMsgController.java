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
import com.zyx.service.infoquery.ErrMsgService;
import com.zyx.util.Tools;
import com.zyx.web.WebController;
import com.zyx.service.system.DictionariesService;
/** 
 * 说明：
 * 创建人：kbky
 * 创建时间：
 */
@Controller("infoqueryErrMsgController")
@RequestMapping(value="/infoquery")
public class ErrMsgController extends WebController {
	
	@Autowired
	private ErrMsgService infoqueryErrMsgService;
	@Autowired
	private DictionariesService dictionariesService;
	
	/*
	 * 查询
	 */
	@RequestMapping("/errmsgList")
	public String list(Map<String, Object> map) throws Exception{
		PageData pd = this.getPageData();
		int pageNum = Page.getPageNum(pd);
		int pageSize = Page.getPageSize(pd);
		List<Map<String, Object>> list = infoqueryErrMsgService.listService(pageNum, pageSize, pd);
		PageInfo<Map<String, Object>> pageInfo = new PageInfo<Map<String, Object>>(list);
		map.put("page", pageInfo);
		map.put("errmsgList", list);
		map.put("pd", pd);
		return "/infoquery/errmsg/errmsg_list";
	}
	
	/*
	 * 保存
	 */
	@RequestMapping("/errmsgSave")
	@ResponseBody
	public String save(Map<String, Object> map) throws Exception{
		return infoqueryErrMsgService.saveService(getPageData());
	}
	
	/*
	 * 修改
	 */
	@RequestMapping("/errmsgUpdate")
	@ResponseBody
	public String update(Map<String, Object> map) throws Exception{
		return infoqueryErrMsgService.updateService(getPageData());
	}
	
	/*
	 * 删除
	 */
	@RequestMapping("/errmsgDel")
	@ResponseBody
	public String del(Map<String, Object> map) throws Exception{
		return infoqueryErrMsgService.delService(getPageData());
	}
	
	/*
	 * 跳转到新增页面
	 */
	@RequestMapping("/errmsgAdd")
	public String add(Map<String, Object> map) throws Exception{
		PageData pd = getPageData();
		pd.put("TABLE_NAME", "BON_ERR_MSG_TBL");
		pd.put("TABLE_COLUMN", "TRANS_TIME");
		map.put("transtimeList", dictionariesService.listDicService(pd));
		pd.put("TABLE_NAME", "BON_ERR_MSG_TBL");
		pd.put("TABLE_COLUMN", "CUSTR_TYPE");
		map.put("custrtypeList", dictionariesService.listDicService(pd));
		map.put("action", "Save");
		map.put("pd", pd);
		map.put("url", Tools.getStringValue(pd.get("url")));
		return "/infoquery/errmsg/errmsg_edit";
	}
	
	/*
	 * 跳转到修改页面
	 */
	@RequestMapping("/errmsgEdit")
	public String edit(Map<String, Object> map) throws Exception{
		PageData pd = getPageData();
		Map<String, Object> result = infoqueryErrMsgService.editService(pd);
		map.put("errmsg", result);
		pd.put("TABLE_NAME", "BON_ERR_MSG_TBL");
		pd.put("TABLE_COLUMN", "TRANS_TIME");
		map.put("transtimeList", dictionariesService.listDicService(pd));
		pd.put("TABLE_NAME", "BON_ERR_MSG_TBL");
		pd.put("TABLE_COLUMN", "CUSTR_TYPE");
		map.put("custrtypeList", dictionariesService.listDicService(pd));
		map.put("action", "Update");
		map.put("pd", pd);
		map.put("url", Tools.getStringValue(pd.get("url")));
		return "/infoquery/errmsg/errmsg_edit";
	}
	
	/*
	 * 跳转到详情页面
	 */
	@RequestMapping("/errmsgDetail")
	public String detail(Map<String, Object> map) throws Exception{
		PageData pd = getPageData();
		Map<String, Object> result = infoqueryErrMsgService.editService(pd);
		map.put("errmsg", result);
		map.put("pd", pd);
		map.put("url", Tools.getStringValue(pd.get("url")));
		return "/infoquery/errmsg/errmsg_detail";
	}
}
