package com.zyx.service.wp;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zyx.dao.DaoSupport;
import com.zyx.model.PageData;

@Service
public class LocationImgService {

	@Resource(name="daoSupport")
	private DaoSupport dao;
	
	public boolean save(PageData pd) throws Exception {
		int count = dao.save("com.zyx.mapper.LocationImgMapper.save", pd);
		if(count == 1){
			return true;
		}else{
			return false;
		}
	}
	
	public List<Map<String, Object>> getImgPath(PageData pd) throws Exception {
		List<Map<String, Object>> list = (List<Map<String, Object>>) dao.findForList("com.zyx.mapper.LocationImgMapper.getImgPath", pd);
		return list;
	}
	
}
