package spider.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import spider.dao.UserDAO;
import spider.model.User;

public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * 实现用户登录功能
	 * @author MoncyJa
	 */
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		resp.setContentType("text/html;charset=utf-8");
		HttpSession session = req.getSession();
		
		String yonghuming = req.getParameter("yonghuming");
		String mima = req.getParameter("mima");
		
		User user = new User();
		user.setUser(yonghuming);
		
		UserDAO.select(user);
		
		if(mima.equals(user.getPassword())){
			session.setAttribute("user", yonghuming);
			resp.sendRedirect("AddFile.jsp");
		}else{
			session.setAttribute("msg", "用户名或密码错误！");
			resp.sendRedirect("Login.jsp");
		}
	}
}
