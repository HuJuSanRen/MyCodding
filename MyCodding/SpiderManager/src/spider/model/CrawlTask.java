package spider.model;

public class CrawlTask {
	
	public int id;
	public String project;  //项目名称
	public String name;   //爬虫名称
	public int status; //-2无法访问 -1 删除  0 项目已上传  1 运行 2 结束 3 挂起（pending）
	public String jobid;  // 当前的爬虫任务id
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getProject() {
		return project;
	}
	public void setProject(String project) {
		this.project = project;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getJobid() {
		return jobid;
	}
	public void setJobid(String jobid) {
		this.jobid = jobid;
	}
	
	

}
