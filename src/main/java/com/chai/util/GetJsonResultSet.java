package com.chai.util;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import com.chai.model.LabelValueBean;

public class GetJsonResultSet {
public static JSONArray getjson(List list){
	JSONArray array=new JSONArray();
	for(Object object : list)
    {
		JSONObject obj=new JSONObject();
       HashMap<String, String> row = (HashMap)object;
       for ( Object key : row.keySet()) {
    	   obj.put((String)key, row.get(key));
    	}
       array.put(obj);
    }
	return array;	
}

public static JSONArray getjsonCombolist(List list,boolean allOption){
	JSONArray array=new JSONArray();
	if(allOption){
		JSONObject allOptionObject=new JSONObject(); 
		allOptionObject.put("value", "null");
		allOptionObject.put("label", "All");
		array.put(allOptionObject);
	}
	for(Object object : list)
    {
		JSONObject obj=new JSONObject();
       Map row = (Map)object;
       for ( Object key : row.keySet()) {
    	   if(key.toString().contains("id") || key.toString().contains("ID") || key.toString().contains("Id")){
    		   obj.put("value", row.get(key));
    	   }else{
    		   obj.put("label", row.get(key));
    	   } 
    	}
       array.put(obj);
    }
	return array;	
}
public static List<LabelValueBean> getCombolistInBean(List list,boolean allOption){
	List<LabelValueBean> array=new LinkedList<LabelValueBean>();
	if(allOption){
		JSONObject allOptionObject=new JSONObject(); 
		array.add(new LabelValueBean(null,"All"));
	}
	for(Object object : list)
    {
       Map row = (Map)object;
       LabelValueBean bean=new LabelValueBean();
       int i=0;
       for ( Object key : row.keySet()) {
    	   if(i==0){
    		   bean.setValue(String.valueOf(row.get(key)));
    	   }else if(i==1){
    		   bean.setLabel(String.valueOf(row.get(key)));
    	   }
    	  i++; 
    	}
       i=0;
       array.add(bean);
    }
	return array;	
}
public static List<LabelValueBean> getdropList(List list){
	List<LabelValueBean> droplist=new LinkedList<LabelValueBean>();
	LabelValueBean bean=null;
//	Map colSizeinRow=(Map)list.get(0);
//	if(colSizeinRow.size()==2){
//		
//	}else if(colSizeinRow.size()==3){
//		
//	}else if(colSizeinRow.size()==4){
//		
//	}
	int i=0; 
	for(Object object : list)
    {
		 bean=new LabelValueBean();
       Map row = (Map)object;
       i=0;
       for ( Object key : row.keySet()) {
    	   if(i==0){
   			bean.setValue(String.valueOf(row.get(key)));
   		}if(i==1){
   			bean.setLabel(String.valueOf(row.get(key)));
   		}if(i==2){
   			bean.setExtra1(String.valueOf(row.get(key)));
   		}if(i==3){
   			bean.setExtra2(String.valueOf(row.get(key)));
   		}
   		i++;
    	}
       droplist.add(bean);
    }
	return droplist;
}
}
