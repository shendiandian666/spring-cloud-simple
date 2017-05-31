package com.zyx.service.wp;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.zyx.dao.DaoSupport;
import com.zyx.model.PageData;

@Service("wpUserService")
public class UserService {

	@Resource(name="daoSupport")
	private DaoSupport dao;
	
	public boolean save(PageData pd) throws Exception {
		int exists = (Integer) dao.findForObject("com.zyx.mapper.UserMapper.queryUserById", pd);
		if(exists == 0){
			int count = dao.save("com.zyx.mapper.UserMapper.register", pd);
			if(count == 1){
				return true;
			}else{
				return false;
			}
		}
		return true;
	}
	
}
