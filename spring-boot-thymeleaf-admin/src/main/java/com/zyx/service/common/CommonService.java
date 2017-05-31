package com.zyx.service.common;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.zyx.dao.DaoSupport;

@Service
public class CommonService {

	@Resource(name="daoSupport")
	private DaoSupport dao;
	
	public String sequence(String sequence) throws Exception {
		return dao.findForObject("com.zyx.mapper.CommonMapper.sequence", sequence).toString();
	}
	
}
