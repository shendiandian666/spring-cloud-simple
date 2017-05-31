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
import com.zyx.service.infoquery.FileBatchService;
import com.zyx.util.Tools;
import com.zyx.web.WebController;
import com.zyx.service.system.DictionariesService;
/** 
 * 说明：
 * 创建人：kbky
 * 创建时间：
 */
@Controller("infoqueryFileBatchController")
@RequestMapping(value="/infoquery")
public class FileBatchController extends WebController {
	
	@Autowired
	private FileBatchService infoqueryFileBatchService;
	@Autowired
	private DictionariesService dictionariesService;
	
	/*
	 * 查询
	 */
	@RequestMapping("/filebatchList")
	public String list(Map<String, Object> map) throws Exception{
		PageData pd = this.getPageData();
		int pageNum = Page.getPageNum(pd);
		int pageSize = Page.getPageSize(pd);
		List<Map<String, Object>> list = infoqueryFileBatchService.listService(pageNum, pageSize, pd);
		PageInfo<Map<String, Object>> pageInfo = new PageInfo<Map<String, Object>>(list);
		map.put("page", pageInfo);
		map.put("filebatchList", list);
		map.put("pd", pd);
		return "/infoquery/filebatch/filebatch_list";
	}
	
	/*
	 * 保存
	 */
	@RequestMapping("/filebatchSave")
	@ResponseBody
	public String save(Map<String, Object> map) throws Exception{
		return infoqueryFileBatchService.saveService(getPageData());
	}
	
	/*
	 * 修改
	 */
	@RequestMapping("/filebatchUpdate")
	@ResponseBody
	public String update(Map<String, Object> map) throws Exception{
		return infoqueryFileBatchService.updateService(getPageData());
	}
	
	/*
	 * 删除
	 */
	@RequestMapping("/filebatchDel")
	@ResponseBody
	public String del(Map<String, Object> map) throws Exception{
		return infoqueryFileBatchService.delService(getPageData());
	}
	
	/*
	 * 跳转到新增页面
	 */
	@RequestMapping("/filebatchAdd")
	public String add(Map<String, Object> map) throws Exception{
		PageData pd = getPageData();
		pd.put("TABLE_NAME", "BON_FILE_BATCH_TBL");
		pd.put("TABLE_COLUMN", "FILE_DIRECTION_CD");
		map.put("filedirectioncdList", dictionariesService.listDicService(pd));
		pd.put("TABLE_NAME", "BON_FILE_BATCH_TBL");
		pd.put("TABLE_COLUMN", "FILE_TYPE_CD");
		map.put("filetypecdList", dictionariesService.listDicService(pd));
		pd.put("TABLE_NAME", "BON_FILE_BATCH_TBL");
		pd.put("TABLE_COLUMN", "TRANS_RSLT_CD");
		map.put("transrsltcdList", dictionariesService.listDicService(pd));
		pd.put("TABLE_NAME", "BON_FILE_BATCH_TBL");
		pd.put("TABLE_COLUMN", "PROCESS_RSLT_CD");
		map.put("processrsltcdList", dictionariesService.listDicService(pd));
		map.put("action", "Save");
		map.put("pd", pd);
		map.put("url", Tools.getStringValue(pd.get("url")));
		return "/infoquery/filebatch/filebatch_edit";
	}
	
	/*
	 * 跳转到修改页面
	 */
	@RequestMapping("/filebatchEdit")
	public String edit(Map<String, Object> map) throws Exception{
		PageData pd = getPageData();
		Map<String, Object> result = infoqueryFileBatchService.editService(pd);
		map.put("filebatch", result);
		pd.put("TABLE_NAME", "BON_FILE_BATCH_TBL");
		pd.put("TABLE_COLUMN", "FILE_DIRECTION_CD");
		map.put("filedirectioncdList", dictionariesService.listDicService(pd));
		pd.put("TABLE_NAME", "BON_FILE_BATCH_TBL");
		pd.put("TABLE_COLUMN", "FILE_TYPE_CD");
		map.put("filetypecdList", dictionariesService.listDicService(pd));
		pd.put("TABLE_NAME", "BON_FILE_BATCH_TBL");
		pd.put("TABLE_COLUMN", "TRANS_RSLT_CD");
		map.put("transrsltcdList", dictionariesService.listDicService(pd));
		pd.put("TABLE_NAME", "BON_FILE_BATCH_TBL");
		pd.put("TABLE_COLUMN", "PROCESS_RSLT_CD");
		map.put("processrsltcdList", dictionariesService.listDicService(pd));
		map.put("action", "Update");
		map.put("pd", pd);
		map.put("url", Tools.getStringValue(pd.get("url")));
		return "/infoquery/filebatch/filebatch_edit";
	}
	
	/*
	 * 跳转到详情页面
	 */
	@RequestMapping("/filebatchDetail")
	public String detail(Map<String, Object> map) throws Exception{
		PageData pd = getPageData();
		Map<String, Object> result = infoqueryFileBatchService.editService(pd);
		map.put("filebatch", result);
		map.put("pd", pd);
		map.put("url", Tools.getStringValue(pd.get("url")));
		return "/infoquery/filebatch/filebatch_detail";
	}
}
