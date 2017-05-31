package com.zyx.service.infoquery;

import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.github.pagehelper.PageHelper;
import com.zyx.dao.DaoSupport;
import com.zyx.model.PageData;
import com.zyx.util.Constants;
import com.zyx.util.Tools;

/** 
 * 说明： 
 * 创建人：kbky
 * 创建时间：
 * @version
 */
@Service("infoquerySysTranscfgService")
public class SysTranscfgService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/*
	 * 查询
	 */
	public List<Map<String, Object>> listService(int pageNum, int pageSize, PageData pd) throws Exception{
		PageHelper.startPage(pageNum, pageSize);
		Object object = dao.findForList("com.zyx.mapper.infoquery.SysTranscfgMapper.list", pd);
		return Tools.objToList(object);
	}
	
	/*
	 * 保存
	 */
	public String saveService(PageData pd) throws Exception {
		int count = (int) dao.findForObject("com.zyx.mapper.infoquery.SysTranscfgMapper.isExists", pd);
		if(count > 0){
			return "主键ID已存在!";
		}else{
			dao.save("com.zyx.mapper.infoquery.SysTranscfgMapper.save", pd);
			return Constants.SUCCESS;
		}
	}
	
	/*
	 * 修改页面数据查询
	 */
	public Map<String, Object> editService(PageData pd) throws Exception {
		Object object = dao.findForObject("com.zyx.mapper.infoquery.SysTranscfgMapper.listById", pd);
		return Tools.objToMap(object);
	}
	
	/*
	 * 修改
	 */
	public String updateService(PageData pd) throws Exception {
		int count = (int) dao.findForObject("com.zyx.mapper.infoquery.SysTranscfgMapper.isExists", pd);
		if(count > 0){
			dao.update("com.zyx.mapper.infoquery.SysTranscfgMapper.update", pd);
			return Constants.SUCCESS;
		}else{
			return "记录已被其他用户删除!";		
		}
	}
	
	/*
	 * 删除
	 */
	public String delService(PageData pd) throws Exception {
		dao.delete("com.zyx.mapper.infoquery.SysTranscfgMapper.delete", pd);
		return Constants.SUCCESS;
	}
	
}

