package spider.scrapyd;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

public class Json {

    //将简单键值对json格式串转为map类型的java对象
   public static    Map<String, Object> getResponse(String str){
	  Map<String, Object> map = null;
	  if(str!=null && str.indexOf('}')>0){
        JSONObject jsonObject = JSONObject.fromObject(str);
        map = (Map<String, Object>) JSONObject.toBean(jsonObject, Map.class);
	  }
      return map ;
    }

 //将json串转成JsonStatus对象
 	public static JsonStatus getJSONObject(String jsonstr){
 		JsonStatus jstatus = null;
 		if(jsonstr!=null && jsonstr.indexOf("}")>0){
 		 JSONObject jsonObject = JSONObject.fromObject(jsonstr); 
		
 		 Map<String, Class> classMap = new HashMap<String, Class>();
		 classMap.put("finished", FinishedStatus.class); 
	     classMap.put("pending", PendingStatus.class); 
	     classMap.put("running", RunningStatus.class); 
    	 jstatus =  (JsonStatus)JSONObject.toBean(jsonObject,JsonStatus.class,classMap); 
 		}
 		return jstatus ;
 	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
      String  str = "{\"status\": \"ok\", \"jobid\": \"59b3bbf0d24011e6bcb5b870f404fa8e\", \"node_name\": \"55J42TLEZUEXYJU\"}" ;
      Map<String, Object> robj = Json.getResponse(str);
      System.out.println(robj.get("status").toString());
      //
      str="{\"status\": \"ok\", \"running\": [], \"finished\": [{\"start_time\": \"2017-01-04 13:40:50.633000\","+
          "\"end_time\": \"2017-01-04 13:41:05.084000\", \"id\": \"59b3bbf0d24011e6bcb5b870f404fa8e\","+
    		  "\"spider\": \"hrtencent\"}], \"pending\": [], \"node_name\": \"55J42TLEZUEXYJU\"}";
      JsonStatus js = getJSONObject(str);
      System.out.println(js.getStatus());
      System.out.println(js.toString());
      List<FinishedStatus> ls = js.getFinished();
      System.out.println(ls);
      for(int i=0;i<ls.size();i++){
    	 System.out.println(ls.get(i).getId()+" "+ls.get(i).getSpider()+" "+ls.get(i).getStart_time());
      }
     
      List<RunningStatus> rls = js.getRunning();
      System.out.println(rls+" "+rls.size());
    
      
      
	}

}
