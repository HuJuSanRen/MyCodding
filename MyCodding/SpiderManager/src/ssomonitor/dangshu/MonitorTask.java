package ssomonitor.dangshu;

import java.net.HttpURLConnection;
import java.util.TimerTask;

public class MonitorTask  extends TimerTask{
   
    Manager manager  ;
    
    public MonitorTask(Manager manager){
    	this.manager = manager ;
    	
    }
    
    
    
    
	@Override
	public void run() {
		HTTPConnector conn = new HTTPConnector(manager.getParam().getUrl(), manager.getParam().getTimeout());
		HttpURLConnection urlconn = conn.getConnection();
		if(urlconn != null){
			manager.getParam().writeOk();
			System.out.println("monitor "+" ok "+ manager.getParam().getUrl()+" "+conn.getExceptionCode());
		}else{
			manager.getParam().writeFail();
			System.out.println("monitor "+" fail "+manager.getParam().getUrl()+" "+conn.getExceptionCode());
		}		
		// 
		
		
		urlconn = null;
		conn = null ;
	}

}
