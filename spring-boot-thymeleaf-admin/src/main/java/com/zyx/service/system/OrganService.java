package com.zyx.service.system;

import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.github.pagehelper.PageHelper;
import com.zyx.dao.DaoSupport;
import com.zyx.model.PageData;
import com.zyx.util.Constants;
import com.zyx.util.Tools;

@Service
public class OrganService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/*
	 * 查询服务
	 */
	public List<Map<String, Object>> listService(int pageNum, int pageSize, PageData pd) throws Exception{
		PageHelper.startPage(pageNum, pageSize);
		Object object = dao.findForList("com.zyx.mapper.OrganMapper.list", pd);
		return Tools.objToList(object);
	}
	
	/*
	 * 保存服务
	 */
	public String saveService(PageData pd) throws Exception {
		int count = (int) dao.findForObject("com.zyx.mapper.OrganMapper.isExists", pd);
		if(count > 0){
			return "机构编码已存在!";
		}else{
			dao.save("com.zyx.mapper.OrganMapper.save", pd);
			return Constants.SUCCESS;
		}
	}
	
	/*
	 * 修改服务
	 */
	public Map<String, Object> editService(PageData pd) throws Exception {
		Object object = dao.findForObject("com.zyx.mapper.OrganMapper.listById", pd);
		return Tools.objToMap(object);
	}
	
	/*
	 * 获取机构字典
	 */
	public List<Map<String, Object>> organDic(PageData pd) throws Exception {
		Object object = dao.findForList("com.zyx.mapper.OrganMapper.listDic", pd);
		return Tools.objToList(object);
	}
	
	/*
	 * 修改
	 */
	public String updateService(PageData pd) throws Exception {
		dao.update("com.zyx.mapper.OrganMapper.update", pd);
		return Constants.SUCCESS;
	}
	
	/*
	 * 删除
	 */
	public String delService(PageData pd) throws Exception {
		int count = (int) dao.findForObject("com.zyx.mapper.OrganMapper.hasChilden", pd);
		if(count > 0){
			return "该机构存在下级机构,请先删除下级机构!";
		}else{
			dao.delete("com.zyx.mapper.OrganMapper.delete", pd);
			return Constants.SUCCESS;
		}
	}
	
	
}
