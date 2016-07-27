package com.txyd.database.create.javabean;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.txyd.database.bean.ColumnBean;
import com.txyd.database.bean.DatabaseBean;
import com.txyd.database.bean.JavaConfigBean;
import com.txyd.database.bean.TableBean;
import com.txyd.database.create.FreemarkerCreateBase;
import com.txyd.database.utils.StringUtil;

import freemarker.template.Configuration;
import freemarker.template.Template;

public class FreemarkerCreateJavabean extends FreemarkerCreateBase {


	
	public static boolean  create(List<DatabaseBean> listDb,JavaConfigBean jcb,Configuration configuration) throws Exception {
		long startTime=System.currentTimeMillis();
		if(listDb==null||listDb.size()==0||jcb==null||configuration==null){
			return false;
		}
		Map<String, String> fileMap=new HashMap<String, String>();
		for(TableBean tb:listDb.get(0).getListTable()){
	        
	        Map<String, Object> context = new HashMap<String, Object>();
	        context.put("StringUtil", new StringUtil());
	        context.put("jcb", jcb);
	        context.put("tableBean", tb);
	        Set<String> importSet=new HashSet<>();
	        for(ColumnBean cb: tb.getListColumn()){
	        	if(!cb.getJavabeanFieldDataType().contains("java.lang.")
	        			&& !(cb.getIsPrimaryKey()&&tb.getPrimaryKeyNum()>1)){
	        		importSet.add(cb.getJavabeanFieldDataType());
	        	}
	        	
	        }
	        context.put("importSet", importSet);
	        context.put("columnBeanList", tb.getListColumn());
	        
	        Template template = configuration.getTemplate("ftl/mapperType/javaBean.ftl");	        
	        StringWriter writer = new StringWriter();
	        template.process(context, writer);
	        
//	        System.out.println(writer.toString());
	        
	        String fileName=tb.getJavabeanModelClassName();
	        String fileContent=writer.toString();

	        System.out.println("创建："+fileName+".java");
	        
	        fileMap.put(fileName, fileContent);
		}

		long endTime=System.currentTimeMillis();
		//创建javabean的model文件
		boolean createFile=FreemarkerCreateBase.createFile(jcb, fileMap, FreemarkerCreateBase.FileType.model);
		System.out.println("创建javabean耗时："+(endTime-startTime)+"ms");
		return createFile;
		
	}
	

	

}
