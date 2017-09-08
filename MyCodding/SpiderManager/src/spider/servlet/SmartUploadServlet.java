package spider.servlet;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import spider.dao.CrawlTaskDao;
import spider.model.CrawlTask;
import spider.scrapyd.ScrapydDao;
import spider.util.AntFile;

import com.jspsmart.upload.SmartUpload;
import com.jspsmart.upload.SmartUploadException;

public class SmartUploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public SmartUploadServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * 实现上传文件功能
	 * @author MoncyJa
	 */
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		SmartUpload su = new SmartUpload();
		String prjname = su.getRequest().getParameter("xiangmuming");
		String spidername = su.getRequest().getParameter("pachongming");
		
		//设置上传文件保存路径
		String filePath = getServletContext().getRealPath("/")+"uploadfile";
		System.out.println("文件上传到："+filePath);
		File file = new File(filePath);
		if(!file.exists()){
			file.mkdir();
		}
		
		//初始化对象
		su.initialize(getServletConfig(), req, resp);
		//设置上传文件大小
		su.setMaxFileSize(1024*1024*10);
		//设置所有文件的大小
		su.setTotalMaxFileSize(1024*1024*100);
		//设置允许上传文件类型
		su.setAllowedFilesList("zip");
		String result ="爬虫部署失败！";
		//设置禁止上传的文件类型
		try {
			su.setDeniedFilesList("rar,jsp,js");
			//上传文件
			su.upload();
			
			int count = su.save(filePath);
			System.out.println("上传"+count+"个文件！");
			if(count==1){
			  String str=filePath+"/"+su.getFiles().getFile(0).getFileName();
			  File src = new File(str);
			  File des = new File(file.getPath());
			  if(AntFile.unzip(src, des)>0){	//解压缩			  				  
				  AntFile.deleteFile(src);//删除上传的压缩文件
				  //向scrapyd部署项目
				  str = su.getFiles().getFile(0).getFileName();
				  String fname = str.substring(0,str.lastIndexOf(".")); //去掉扩展名
				  str  = filePath+"/"+fname ;
				  File workdir = new File(str); //形成工作路径
				  ScrapydDao sd  = new ScrapydDao();
				  boolean b = sd.deploySpider(prjname, "r1.0.0", workdir);
				  if(b){   //部署项目成功
				   CrawlTaskDao ctd = new CrawlTaskDao();
				   CrawlTask ct = new CrawlTask();
				   ct.setProject(prjname);
				   ct.setName(spidername);
				   ctd.addProject(ct)
   				   String jobid = sd.startSpider(prjname, spidername, "http://localhost:6800");

					 //更新数据库状态 
					result = "爬虫部署成功！";  
				  }
			  };
			}
			
		} catch (SQLException | SmartUploadException e) {
			
			e.printStackTrace();
		}
		req.setAttribute("result", result);
		req.getRequestDispatcher("AddFile.jsp").forward(req,resp);
	}

}
