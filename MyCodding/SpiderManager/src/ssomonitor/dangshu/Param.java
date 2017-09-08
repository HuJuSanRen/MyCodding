package ssomonitor.dangshu;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Param {
  private  String base="" ; 	
  private  String OUTFILE = "WEB-INF/ini/status.ini" ;
  private   String PARAFILE = "WEB-INF/ini/sso.ini" ;
  
  private String url ;  //目标url
  private int timeout=5000 ; //超时设置，ms
  private int periodTime=10 ;  //  轮询周期 10s
  
  
  public Param(String path){
	 // String base = this.getClass().getClassLoader().getResource(".").getPath();
	  base  = path ;
	  this.OUTFILE = base+this.OUTFILE;
	  System.out.println(this.OUTFILE);
	  this.PARAFILE = base+this.PARAFILE;
  }
  
  public Param getParam(){
	  Param param = this;
	  Properties props = new Properties();
	  
	  try {
		  FileInputStream fis = new FileInputStream(PARAFILE);	
		  InputStream in = new BufferedInputStream (fis);
		  props.load(in);		  
		  String value = props.getProperty ("url");
		  param.setUrl(value);
		  value = props.getProperty ("timeout");
		  param.setTimeout(Integer.valueOf(value));
		  value = props.getProperty ("periodtime");
		  param.setPeriodTime(Integer.valueOf(value));		  
		  
	 } catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	 }
	  return param  ;
  }
  // 
public void writeOk(){
	writeOutFile("0");
}
  public void writeFail(){
	writeOutFile("1");
}
  // 
 public void writeOutFile(String str){
	 File f = new File(OUTFILE);
	 FileWriter  fw = null;
	 try {
		 fw = new FileWriter(f);
		 fw.write(str);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}finally{
		if(fw!=null){
			try {
				fw.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	} 
	 
 }

public String getUrl() {
	return url;
}

public void setUrl(String url) {
	this.url = url;
}

public int getTimeout() {
	return timeout;
}

public void setTimeout(int timeout) {
	this.timeout = timeout;
}

public int getPeriodTime() {
	return periodTime;
}

public void setPeriodTime(int periodTime) {
	this.periodTime = periodTime;
}

  public String getBase() {
	return base;
}

public void setBase(String base) {
	this.base = base;
}

public static void main(String[] args){
	  Param param = new Param("");
	  
	  
  }
  
}
