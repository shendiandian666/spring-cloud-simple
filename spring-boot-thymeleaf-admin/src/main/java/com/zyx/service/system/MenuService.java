package com.zyx.service.system;

import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.zyx.dao.DaoSupport;
import com.zyx.util.Tools;

@Service
public class MenuService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;

	public List<Map<String, Object>> getAllMenus() throws Exception {
		Object object = dao
				.findForList("com.zyx.mapper.MenuMapper.getAllMenus", "");
		return Tools.objToList(object);
	}
	
	public List<Map<String, Object>> getMenus() throws Exception {
		//PageHelper.startPage(1, 10);
		Object object = dao
				.findForList("com.zyx.mapper.MenuMapper.getMenus", "");
		return Tools.objToList(object);
	}
	
	public List<Map<String, Object>> getFaMenus() throws Exception {
		Object object = dao
				.findForList("com.zyx.mapper.MenuMapper.getFaMenus", "");
		return Tools.objToList(object);
	}
}
