package spider.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import spider.db.DB;
import spider.model.User;

public class UserDAO {
	public static void insert(User user){
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn=DB.getConnection();
			String sql = "INSERT INTO User (name,password) VALUES ( ?, ?)";
			ps=conn.prepareStatement(sql);
			ps.setString(1, user.getUser());
			ps.setString(2, user.getPassword());
			ps.executeUpdate();
	 } catch (Exception e){
		 System.out.print(e);
	 } finally{
		 DB.close(null, ps, conn);		 
	 }				
	}
	
	
	public static void select(User user){
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		String dbpass = null;

		try {
			conn = DB.getConnection();
			String sql = "select * from User where name=?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, user.getUser());
			rs = ps.executeQuery();
			while(rs.next()){
				dbpass = rs.getString("password");
			}
			user.setPassword(dbpass);
			rs.close();
			ps.close();
			conn.close();
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
