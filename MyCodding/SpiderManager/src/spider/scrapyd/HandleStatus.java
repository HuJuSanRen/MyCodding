package spider.scrapyd;

import java.util.ArrayList;
import java.util.List;

import spider.dao.CrawlTaskDao;
import spider.model.CrawlTask;
import spider.scrapyd.*;
//获取爬虫任务状态，更新数据库中相应状态
public class HandleStatus {

	
	public static void doStatus(){
		List<PendingStatus> pending=null;
		List<RunningStatus> running=null;
		List<FinishedStatus> finished=null;
		
		ScrapydDao sd = new ScrapydDao();
	    String url="http://localhost:6800";
	    CrawlTaskDao ctd = new CrawlTaskDao();
	    ArrayList<String> ls = ctd.getProject();
	    for(int i=0;i<ls.size();i++){		 
			String prj = ls.get(i);			
	    	JsonStatus status=sd.getStatus(prj, url); //获取scrapyd上项目对应的状态	    	
	    	pending = status.getPending();
	    	running = status.getRunning(); //获取running状态的列表
	    	finished = status.getFinished();
	    	//更新挂起任务的状态
	    	for(int j=0;j<pending.size();j++){
	    		String jobid = pending.get(j).getId();
	    		//String spidername = pending.get(i).getSpider();
	    		ctd.updateStatusByJobId(jobid,3); //更新相应爬虫的状态，3 挂起（pending）	    		
	    	}
	    	//更新运行任务的状态
	    	for(int l=0;l<running.size();l++){
	    		String jobid = running.get(l).getId();
	    		//String spidername = pending.get(i).getSpider();
	    		ctd.updateStatusByJobId(jobid,1); //1 运行	    		
	    	}
	    	//更新结束任务的状态
	    	for(int n=0;n<finished.size();n++){
	    		String jobid = pending.get(n).getId();
	    		//String spidername = pending.get(i).getSpider();
	    		ctd.updateStatusByJobId(jobid,2); //2 结束	    		
	    	}
	    
	    }
		
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
