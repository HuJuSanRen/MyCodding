package spider.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import spider.dao.UserDAO;
import spider.model.User;

public class RegistServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public RegistServlet() {
        super();
    }
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @实现用户注册功能
	 * @author MoncyJa
	 * @see HttpServlet#doPost(HttpServletRequest req, HttpServletResponse resp)
	 */
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		resp.setContentType("text/html;charset=utf-8");
		
		String yonghuming = req.getParameter("yonghuming");
		String mima = req.getParameter("mima");
		
		User user = new User();
		user.setUser(yonghuming);
		user.setPassword(mima);
		
		UserDAO.insert(user);

		resp.sendRedirect("Login.jsp");
	}

}
