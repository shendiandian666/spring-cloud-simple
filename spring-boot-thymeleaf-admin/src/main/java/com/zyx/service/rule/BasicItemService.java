package com.zyx.service.rule;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.apache.commons.collections.MapUtils;
import org.springframework.stereotype.Service;
import com.github.pagehelper.PageHelper;
import com.zyx.dao.DaoSupport;
import com.zyx.model.PageData;
import com.zyx.util.Constants;
import com.zyx.util.JsonTool;
import com.zyx.util.Tools;

/** 
 * 说明： 
 * 创建人：kbky
 * 创建时间：
 * @version
 */
@Service("ruleBasicItemService")
public class BasicItemService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/*
	 * 查询
	 */
	public List<Map<String, Object>> listService(int pageNum, int pageSize, PageData pd) throws Exception{
		PageHelper.startPage(pageNum, pageSize);
		Object object = dao.findForList("com.zyx.mapper.rule.BasicItemMapper.list", pd);
		return Tools.objToList(object);
	}
	
	/*
	 * 保存
	 */
	public String saveService(PageData pd) throws Exception {
		int count = (int) dao.findForObject("com.zyx.mapper.rule.BasicItemMapper.isExists", pd);
		if(count > 0){
			return "元素编码已存在!";
		}else{
			dao.save("com.zyx.mapper.rule.BasicItemMapper.save", pd);
			return Constants.SUCCESS;
		}
	}
	
	/*
	 * 修改页面数据查询
	 */
	public Map<String, Object> editService(PageData pd) throws Exception {
		Object object = dao.findForObject("com.zyx.mapper.rule.BasicItemMapper.listById", pd);
		return Tools.objToMap(object);
	}
	
	/*
	 * 修改
	 */
	public String updateService(PageData pd) throws Exception {
		int count = (int) dao.findForObject("com.zyx.mapper.rule.BasicItemMapper.isExists", pd);
		if(count > 0){
			dao.update("com.zyx.mapper.rule.BasicItemMapper.update", pd);
			return Constants.SUCCESS;
		}else{
			return "记录已被其他用户删除!";		
		}
	}
	
	/*
	 * 删除
	 */
	public String delService(PageData pd) throws Exception {
		dao.delete("com.zyx.mapper.rule.BasicItemMapper.delete", pd);
		return Constants.SUCCESS;
	}
	
	/*
	 * 将BON_BASIC_ITEM_TBL中的PROG_DESC字段json值转换为map
	 */
	public Map<String, Object> findProgDesc(PageData pd) throws Exception {
		Object object = dao.findForObject("com.zyx.mapper.rule.BasicItemMapper.findByItemCode", pd);
		Map<String, Object> result = Tools.objToMap(object);
		String progDesc = MapUtils.getString(result, "PROG_DESC");
		if(progDesc != null && !"".equals(progDesc)){
			Map<String, Object> progMap = JsonTool.jsonToMap(progDesc);
			result.put("PROG_DESC", progMap);
		}
		return result;
	}
	
}

