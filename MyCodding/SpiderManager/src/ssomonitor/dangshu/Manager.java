package ssomonitor.dangshu;

import java.util.Timer;

public class Manager {
   Param param ;
   Timer timer ;
   
   public Manager(String path){
	   init(path);
   }
   
   void init(String path){
	   param = new Param(path).getParam();
		  //启动定时器
	   timer = new Timer();
	   timer.schedule(new MonitorTask(this),10*1000, param.getPeriodTime()*1000);//�ӳ�10s��ִ�У�  
   }
   
   void destroy(){
	   if(timer!=null){
	     timer.cancel();
	     timer = null;
	   }
	   param = null;
   }
   

public Param getParam() {
	return param;
}

public void setParam(Param param) {
	this.param = param;
}
   
}
