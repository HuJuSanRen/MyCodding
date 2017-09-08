package ssomonitor.dangshu;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class InitMonitor
 */
public class InitMonitor extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private Manager tm ; 
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public InitMonitor() {
        super();
       
    }

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		super.destroy();
		if(tm!=null){
			tm.destroy();
		}
	}

	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		 super.init();		
		 String path = getServletContext().getRealPath("/");
		 System.out.println("start monitor " +path);
         try {
			  tm = new Manager(path);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("start monitor failed ");
		}
        System.out.println(" start monitor success ");
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
