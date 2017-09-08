package spider.scrapyd;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;


public class HttpPost {

	
	//http://www.cnblogs.com/zhuawang/archive/2012/12/08/2809380.html
	
    public static String sendGet(String url, String param) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url + "?" + param;
            URL realUrl = new URL(urlNameString);
            //  
            URLConnection connection = realUrl.openConnection();
            //  
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            //  
            connection.connect();
            //  
            Map<String, List<String>> map = connection.getHeaderFields();
            // 
         /*   for (String key : map.keySet()) {
                System.out.println(key + "--->" + map.get(key));
            } */
            //  
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("����GET��������쳣��" + e);
            e.printStackTrace();
        }
        //  
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }

	
    public static String sendPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            //  
            URLConnection conn = realUrl.openConnection();
            //  
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            //  
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 
            out = new PrintWriter(conn.getOutputStream());
            // 
            out.print(param);
            // flus 
            out.flush();
            //  
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("  POST  ok"+e);
            e.printStackTrace();
        }
        // 
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return result;
    }    
    
    
	
	public static void main(String[] args) {
		
		//POST test
		String url="http://localhost:6800/schedule.json";
        String param ="project=hrtencent&spider=hrtencent";
		String str= sendPost(url, param);
        System.out.println(str);
        Map<String, Object> robj = Json.getResponse(str);
        System.out.println(robj.get("status"));
        //get test
        url = "http://localhost:6800/listjobs.json" ;
        param = "project=hrtencent";
        String s= sendGet(url, param);
        System.out.println(s);
        // json
       if(str==null || str.length()<1){
        str = "{\"status\": \"ok\", \"jobid\": \"59b3bbf0d24011e6bcb5b870f404fa8e\", \"node_name\": \"55J42TLEZUEXYJU\"}" ;
       }
       robj = Json.getResponse(str);
       System.out.println(robj.get("status"));
       System.out.println(robj.get("running"));
        
	}

}
