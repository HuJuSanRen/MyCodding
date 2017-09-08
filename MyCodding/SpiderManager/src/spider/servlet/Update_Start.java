package spider.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.jmi.Model.Scrapy_2;
import edu.jmi.ScrapyDao.ScrapyDao;

/**
 * Servlet implementation class Update_Start
 */
public class Update_Start extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Update_Start() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("Utf-8");
		
		String Scrapyid = request.getParameter("ScrapyId");
		System.out.println(Scrapyid);
		if(Scrapyid == null)
		{
			return;
		}
		
		int ScrapyNo = Integer.valueOf(Scrapyid);
		
		CrawlTask scrapy_2 = new CrawlTask();
		CrawlTaskDao scrapydao = new CrawlTaskDao();
		
		scrapy_2.setId(ScrapyNo);
		if(scrapydao.Update_start(scrapy_2))
		{
			response.sendRedirect("information.jsp");
		}
		System.out.println(ScrapyNo);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request,response);
	}

}
