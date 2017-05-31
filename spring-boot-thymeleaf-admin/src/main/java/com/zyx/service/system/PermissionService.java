package com.zyx.service.system;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.collections.MapUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.github.pagehelper.PageHelper;
import com.zyx.dao.DaoSupport;
import com.zyx.model.PageData;
import com.zyx.model.system.User;
import com.zyx.util.Constants;
import com.zyx.util.Tools;

@Service
public class PermissionService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/*
	 * 获取树形菜单根目录
	 */
	public List<Map<String, Object>> getRootList(HttpServletRequest request) throws Exception {
		// 目标树
		List<Map<String, Object>> treeList = new ArrayList<Map<String, Object>>();
		String projectPath = request.getContextPath();
		List<Map<String, Object>> rootList = Tools
				.objToList(dao.findForList("com.zyx.mapper.PermissionMapper.getRoot", ""));
		//查询是否有子节点
		List<Map<String, Object>> childList = Tools
				.objToList(dao.findForList("com.zyx.mapper.PermissionMapper.getPermission", ""));
		//Subject subject = SecurityUtils.getSubject();
		for(Map<String, Object> root : rootList){
			Integer menuId =  MapUtils.getInteger(root, "B_ID");
			root.put("B_URL", projectPath + "/permissionRight?PARENT_ID="+menuId);
			root.put("target", "treeFrame");
			//if(subject.isPermitted(menuId.toString()+menuUrl)){
				for(Map<String, Object> child : childList){
					//从非顶级节点中，为当前节点寻找子结点
					boolean equals = MapUtils.getInteger(child, "PARENT_ID").equals(menuId);
					if(equals){
						root.put("isParent", true);
					}
				}
				treeList.add(root);
			//}
		}
		return treeList;
	}
	
	/*
	 * 获取树形菜单子节点
	 */
	public List<Map<String, Object>> getNodes(HttpServletRequest request,PageData pd) throws Exception {
		String parentId = Tools.getStringValue(pd.get("parent_id"));
		if("".equals(parentId)){
			pd.put("parent_id", "0");
		}
		String projectPath = request.getContextPath();
		List<Map<String, Object>> rootList = Tools
				.objToList(dao.findForList("com.zyx.mapper.PermissionMapper.getNodes", pd));
		//查询是否有子节点
		List<Map<String, Object>> childList = Tools
				.objToList(dao.findForList("com.zyx.mapper.PermissionMapper.getPermission", ""));
		//Subject subject = SecurityUtils.getSubject();
		for(Map<String, Object> root : rootList){
			Integer menuId =  MapUtils.getInteger(root, "B_ID");
			root.put("B_URL", projectPath + "/permissionRight?PARENT_ID="+menuId);
			root.put("target", "treeFrame");
			//if(subject.isPermitted(menuId.toString()+menuUrl)){
				for(Map<String, Object> child : childList){
					//从非顶级节点中，为当前节点寻找子结点
					boolean equals = MapUtils.getInteger(child, "PARENT_ID").equals(menuId);
					if(equals){
						root.put("isParent", true);
					}
				}
			//}
		}
		return rootList;
	}
	
	/*
	 * 获取右侧查询列表
	 */
	public List<Map<String, Object>> permissionRight(int pageNum, int pageSize, PageData pd) throws Exception{
		String parentId = Tools.getStringValue(pd.get("PARENT_ID"));
		if("".equals(parentId)){
			pd.put("PARENT_ID", "0");
		}
		PageHelper.startPage(pageNum, pageSize);
		return Tools.objToList(dao.findForList("com.zyx.mapper.PermissionMapper.getPermissionById", pd));
	}
	
	/*
	 * 保存
	 */
	@Transactional
	public String saveService(PageData pd) throws Exception {
		String parentId = Tools.getStringValue(pd.get("PARENT_ID"));
		if("".equals(parentId)){
			pd.put("PARENT_ID", "0");
		}
		String _parameter = "SEQ_DICTIONARIES_ID";
		String sequence = (String) dao.findForObject("com.zyx.mapper.CommonMapper.sequence", _parameter);
		pd.put("MENU_ID", sequence);
		int count = dao.save("com.zyx.mapper.PermissionMapper.save", pd);
		if(count > 0){
			Object object = dao.findForObject("com.zyx.mapper.UserMapper.loginUser", pd);
			Map<String, Object> userMap = Tools.objToMap(object);
			String userRights = Tools.getStringValue(userMap.get("RIGHTS"));
			BigInteger resRights = new BigInteger(userRights).setBit(Integer.valueOf(sequence));
			pd.put("RIGHTS", resRights.toString());
			User user = (User) SecurityUtils.getSubject().getPrincipal();
			pd.put("USER_ID", user.getUserId());
			dao.update("com.zyx.mapper.UserMapper.updateRights", pd);
			return Constants.SUCCESS;
		}else{
			return "保存失败!";
		}
	}
	
	/*
	 * 修改
	 */
	public String updateService(PageData pd) throws Exception {
		dao.update("com.zyx.mapper.PermissionMapper.update", pd);
		return Constants.SUCCESS;
	}
	
	/*
	 * 删除
	 */
	@Transactional
	public String permissionDel(PageData pd) throws Exception {
		int count = dao.save("com.zyx.mapper.PermissionMapper.delete", pd);
		if(count > 0){
			Object object = dao.findForObject("com.zyx.mapper.UserMapper.loginUser", pd);
			Map<String, Object> userMap = Tools.objToMap(object);
			String userRights = Tools.getStringValue(userMap.get("RIGHTS"));
			Object objList = dao.findForList("com.zyx.mapper.PermissionMapper.findDel", pd);
			List<Map<String, Object>> list = Tools.objToList(objList);
			for(Map<String, Object> m : list){
				String menuId = Tools.getStringValue(m.get("MENU_ID"));
				BigInteger resRights = new BigInteger(userRights).clearBit(Integer.valueOf(menuId));
				User user = (User) SecurityUtils.getSubject().getPrincipal();
				pd.put("RIGHTS", resRights.toString());
				pd.put("USER_ID", user.getUserId());
				dao.update("com.zyx.mapper.UserMapper.updateRights", pd);
			}
			return Constants.SUCCESS;
		}else{
			return "删除失败";
		}
	}
	
	/*
	 * 修改菜单图标
	 */
	public String editicon(PageData pd) throws Exception {
		dao.update("com.zyx.mapper.PermissionMapper.editicon", pd);
		return Constants.SUCCESS;
	}
	
	public Map<String, Object> findOneService(String menuId) throws Exception {
		Object object = dao.findForObject("com.zyx.mapper.PermissionMapper.findById", menuId);
		return Tools.objToMap(object);
	}

}
