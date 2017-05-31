package com.zyx.util;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class CreateCode {

	private static final String MODEL_NAME = "infoquery";	//模块名
	private static final String CLASS = "MerchantList";	//类名前缀(生成LoginController,LoginService)
	private static final String TABLE_NAME = "BON_MERCHANT_LIST_TBL";	//表名
	private static final String PRIMARY_KEY = "ID";	//主键字段
	private static final String SEQUENCE = "to_char(sysdate,'yyyyMMdd')||POINTS_DATA_SEQ.NEXTVAL";	//对应表的唯一ID生成方式
	private static final String HAS_SELECT = "true";	//是否有下拉列表
	private static final String USE_ORGAN = "true";
	
	private static final Map<String, String[]> COLUMNS_MAP = new LinkedHashMap<String, String[]>(){
		private static final long serialVersionUID = 1L;
		{
			//数组中的第一个值为字段描述，第二个值表示(true:下拉列表，false:非下拉列表)
			put("SHOP_CODE", new String[]{"商城编码","false"});
			put("TRANS_DATE", new String[]{"日期","false"});
			put("TYPE", new String[]{"类型","true"});
			put("ACCOUNT", new String[]{"内部账号","false"});
			put("REDIT", new String[]{"贷方发生额","false"});
			put("DEBIT", new String[]{"借方发生额","false"});
			put("AMT", new String[]{"账户余额","false"});
			put("STATUS", new String[]{"状态","true"});
			
			
		}
	};
	private static final Map<String, Object> MODEL = new HashMap<String, Object>(){
		private static final long serialVersionUID = 1L;
		{
			put("model", MODEL_NAME);
			put("class", CLASS);
			put("tableName", TABLE_NAME);
			put("primaryKey", PRIMARY_KEY);
			put("sequence", SEQUENCE);
			put("hasSelect", HAS_SELECT);
			put("columnMap", COLUMNS_MAP);
			put("useOrgan", USE_ORGAN);
		}
	};
	
	public static void main(String[] args) throws Exception {
		Freemarker.printFile("controller.ftl", MODEL, PathUtil.getControllerPath()+MODEL_NAME+"/"+CLASS+"Controller.java");
		Freemarker.printFile("service.ftl", MODEL, PathUtil.getServicePath()+MODEL_NAME+"/"+CLASS+"Service.java");
		Freemarker.printFile("html_list.ftl", MODEL, PathUtil.getHtmlPath()+MODEL_NAME+"/"+CLASS.toLowerCase()+"/"+CLASS.toLowerCase()+"_list.html");
		Freemarker.printFile("html_edit.ftl", MODEL, PathUtil.getHtmlPath()+MODEL_NAME+"/"+CLASS.toLowerCase()+"/"+CLASS.toLowerCase()+"_edit.html");
		Freemarker.printFile("html_detail.ftl", MODEL, PathUtil.getHtmlPath()+MODEL_NAME+"/"+CLASS.toLowerCase()+"/"+CLASS.toLowerCase()+"_detail.html");
		Freemarker.printFile("mapper.ftl", MODEL, PathUtil.getMapperPath()+MODEL_NAME+"/"+CLASS+"Mapper.xml");
		System.out.println("菜单访问链接："+"/"+MODEL_NAME+"/"+CLASS.toLowerCase()+"List");
		System.out.println("添加权限："+MODEL_NAME+"/"+CLASS.toLowerCase()+":add");
		System.out.println("修改权限："+MODEL_NAME+"/"+CLASS.toLowerCase()+":edit");
		System.out.println("删除权限："+MODEL_NAME+"/"+CLASS.toLowerCase()+":delete");
		System.out.println("详情权限："+MODEL_NAME+"/"+CLASS.toLowerCase()+":detail");
	}
}
