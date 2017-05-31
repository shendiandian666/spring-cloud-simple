package com.zyx.service.test;

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
@Service("testLoginService")
public class LoginService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	public List<Map<String, Object>> listService(int pageNum, int pageSize, PageData pd) throws Exception{
		PageHelper.startPage(pageNum, pageSize);
		Object object = dao.findForList("com.zyx.mapper.test.LoginMapper.list", pd);
		return Tools.objToList(object);
	}
	
	public String saveService(PageData pd) throws Exception {
		int count = (int) dao.findForObject("com.zyx.mapper.test.LoginMapper.isExists", pd);
		if(count > 0){
			return "主键ID已存在!";
		}else{
			dao.save("com.zyx.mapper.test.LoginMapper.save", pd);
			return Constants.SUCCESS;
		}
	}
	
	public Map<String, Object> editService(PageData pd) throws Exception {
		Object object = dao.findForObject("com.zyx.mapper.test.LoginMapper.listById", pd);
		return Tools.objToMap(object);
	}
	
	public String updateService(PageData pd) throws Exception {
		dao.update("com.zyx.mapper.test.LoginMapper.update", pd);
		return Constants.SUCCESS;
	}
	
	public String delService(PageData pd) throws Exception {
		dao.delete("com.zyx.mapper.test.LoginMapper.delete", pd);
		return Constants.SUCCESS;
	}
	
}

