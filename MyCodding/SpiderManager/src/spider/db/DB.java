package spider.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DB {
	private static final String DRIVER = "com.mysql.jdbc.Driver";
	private static final String URL = "jdbc:mysql://localhost:3306/spidermanager?useUnicode=true&characterEncoding=utf-8";
	private static final String user = "root";
	private static final String password = "123456";
	
	public static Connection getConnection(){
		Connection conn=null;
		try {
			Class.forName(DRIVER);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null; 
		}
		try {
			conn = DriverManager.getConnection(URL,user,password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}
	public static void close(ResultSet rs,PreparedStatement pst,Connection conn){
		try
			 {
			 if(rs != null){
			 rs.close();
			 }
			 if(pst!=null){
			 pst.close();
			 }
			 if(conn!=null){
			 conn.close();
			 }
			 } catch (SQLException e)
			 {
			 // TODO Auto-generated catch block
			 e.printStackTrace();
			 }
		}
	
	
	
	public static void main(String[] args) {
		DB.getConnection();
	}

}
