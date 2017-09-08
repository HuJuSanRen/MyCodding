package ssomonitor.dangshu;

import java.io.IOException;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class HTTPConnector {

	private String urlToConnect = ""; 
	private String exceptionCode = "";
	private long linkTime = 0 ;
	private boolean btimeOut=false;
	private int timeOut ;  // 
	
	public HTTPConnector(String urlToConnect) {
		this.urlToConnect = urlToConnect.trim();
	}
	public HTTPConnector(String urlToConnect,int timeOut) {
		this.urlToConnect = urlToConnect.trim();
		this.timeOut = timeOut;
		
	}
	 
	@SuppressWarnings("unused")
	public HttpURLConnection getConnection() 
	{
		HttpURLConnection connectionToResource = null;
		try {
			// need jdk1.6 above
			//CookieHandler.setDefault(new CookieManager(null, CookiePolicy.ACCEPT_ALL));
			
			URL resourceURL = new URL(urlToConnect);     
			
		
				connectionToResource = (HttpURLConnection) resourceURL.openConnection();
				connectionToResource.setRequestMethod("GET");
				connectionToResource.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/30.0.1599.101 Safari/537.36");
			    connectionToResource.setRequestProperty("Host", resourceURL.getHost());
			    connectionToResource.setRequestProperty("Path", resourceURL.getPath());
			    connectionToResource.setRequestProperty("Connection", "keep-alive");
			    connectionToResource.setRequestProperty("Accept-Language", "zh-cn,zh;q=0.8,en-us;q=0.5,en;q=0.3");
			    connectionToResource.setRequestProperty("Accept-Encoding", "gzip,deflate,sdch");
			    connectionToResource.setRequestProperty("Accept", "*/*");
			    
			    connectionToResource.setReadTimeout(this.timeOut/2);//��ȡ���ݳ�ʱ
			    connectionToResource.setConnectTimeout(this.timeOut/2) ;// socket���ӳ�ʱ
			 		      
			    long st = System.currentTimeMillis();
			    connectionToResource.connect();	
			    connectionToResource.getResponseCode();
			    linkTime = System.currentTimeMillis()-st;
			    
			    return connectionToResource;
			
		} catch (MalformedURLException e) {
			exceptionCode = "MalformedURLException";
			return null;
		} catch (ProtocolException e) {
			exceptionCode = "ProtocolException";
			return null;
		}catch (java.net.UnknownHostException e) {
			exceptionCode = "UnknownHostException";
			return null;
		}catch (java.net.SocketException e) {
			exceptionCode = "SocketException";
			return null;
		}catch(java.net.SocketTimeoutException e) {
			exceptionCode = "SocketTimeoutException";
			btimeOut = true;
			return null;
		}catch (IOException e) {
			exceptionCode = "IOException";
			return null;
		}catch (Exception e) {
			exceptionCode = "Exception";
			e.printStackTrace();
			return null;
		}finally{
			if(connectionToResource!=null){
				connectionToResource.disconnect();
			}
		}
	}

	

	public String getExceptionCode() {
		return exceptionCode;
	}

	public long getLinkTime() {
		return linkTime;
	}
	
	public boolean isTimeOut() {
		return btimeOut;
	}

	}
