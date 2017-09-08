package spider.scrapyd;

import java.io.File;
import java.util.List;
import java.util.Map;

public class ScrapydDao {
	//deploy spider,success return true
  public boolean deploySpider(String prj,String version,File path){
	  boolean bres = false;
	  try{	
		String[] cmd = new String[] { "cmd", "/c", "scrapyd-deploy -p "+ prj+" -v "+version };
        String str =  Command.exec(cmd, path);	
        if(str.lastIndexOf('}')>0){
            str = str.substring(0,str.lastIndexOf('}')+1);
            Map<String, Object> robj = Json.getResponse(str);
            if(robj!=null && "ok".equals(robj.get("status").toString())){
            	bres = true;
            }
        }
	  }catch(Exception e){
		  e.printStackTrace();
	  }
      return bres 	;
	}
	
	
	
   //start spider, return result:jobid ,false null
	public String startSpider(String prj,String spider,String url){
	   String res = null;
	   try{	
		String url2=url+"/schedule.json";
        String param ="project="+prj+"&spider="+spider;
		String str= HttpPost.sendPost(url2, param);
		Map<String, Object> robj = Json.getResponse(str);
        if(robj!=null &&"ok".equals(robj.get("status").toString()) && robj.get("jobid")!=null){
        	res =  robj.get("jobid").toString() ;
        }
	   }catch(Exception e){
		  e.printStackTrace();
	   }
	   return res;
	}
	
	public boolean stopSpider(String prj,String jobid,String url){
	 boolean bres = false;
	 try {
	    String url2=url+"/cancel.json";
        String param ="project="+prj+"&job="+jobid;
		String str= HttpPost.sendPost(url2, param);
		Map<String, Object> robj = Json.getResponse(str);
        if(robj!=null && "ok".equals(robj.get("status").toString())){
        	bres =  true ;
        }
	 }catch(Exception e){
		e.printStackTrace(); 
	 }
	 return bres;		
	}
	
	public JsonStatus getStatus(String prj,String url){
	  JsonStatus res = null;
	  try{	
		String url2=url+"/listjobs.json";
        String param ="project="+prj;
		String str= HttpPost.sendGet(url2, param);		  
		res = Json.getJSONObject(str); 
	  }catch(Exception e){
		  e.printStackTrace();
	  }
	  return res;		
	}
	//delete project by project name post method
	public boolean delProject(String prj,String url){
		boolean bres = false;
		try{
			String url2=url+"/delproject.json";
	        String param ="project="+prj;
			String str= HttpPost.sendPost(url2, param);		  
			Map<String, Object> robj = Json.getResponse(str);
	        if(robj!=null && "ok".equals(robj.get("status").toString())){
	        	bres =  true ;
	        }
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return bres ;
	}
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			String jobid = "";
			File f = new File("F:\\pywork\\PycharmProjects\\TencentHR\\hrtencent");
			ScrapydDao sd  = new ScrapydDao();
			boolean b = sd.deploySpider("hrtencent", "r1.0.0", f);
			if(b){
			   jobid = sd.startSpider("hrtencent", "hrtencent", "http://localhost:6800");
			   System.out.println(jobid);			
			}else{
				System.out.println("deloy fail");
			}			
		   Thread.sleep(3000);  //wait for 
		   JsonStatus sta = sd.getStatus("hrtencent", "http://localhost:6800");
		   if(sta!=null){
		     System.out.println(sta.getStatus());
		     List<FinishedStatus> ls = sta.getFinished();
		     for(int i=0;i<ls.size();i++){
     		    System.out.println(ls.get(i).getId()+"  "+ls.get(i).getSpider());
		     }
		   }else{
			    System.out.println("spider get status fail");
		   }
           //
		  b = sd.stopSpider("hrtencent", jobid, "http://localhost:6800");
		  if(b){
			  System.out.println("spider stop success"); 
		  }else{
			  System.out.println("spider stop fail");
		  }
		  Thread.sleep(3000);
		   //
	      b = sd.delProject("hrtencent", "http://localhost:6800");
		  if(b){ 
			  System.out.println("project deleted success");
		  }else{
			  System.out.println("project deleted fail"); 
		  }
		   
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
