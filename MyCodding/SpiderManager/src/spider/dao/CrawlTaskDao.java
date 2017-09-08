package spider.dao;

import spider.db.*;
import spider.model.*;
import java.sql.*;
import java.util.ArrayList;


public class CrawlTaskDao 
{
	//添加项目
	public boolean addProject(CrawlTask ct){
		boolean b = false;
		
		if(isProjectExist(ct.getProject(), ct.getName())){
			return b ;
		}
		
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "insert into crawltask(project,name) value(?,?) "; 
		
		try 
		{
			connection = DB.getConnection();
			ps = connection.prepareStatement(sql);
			ps.setString(1, ct.getProject());
			ps.setString(2, ct.getName());
			
			int i = ps.executeUpdate();
			if(i>0){
				b = true ;
			}
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			DB.close(rs, ps, connection);
		}
		
		
		return b ;
	}
	
	//获取全部符合条件的记录
	public ArrayList<CrawlTask> getCrawlTask()
	{
		ArrayList<CrawlTask> res = new ArrayList<CrawlTask>() ;
		
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "select * from crawltask where status<>-1 "; //被删的不读取
		
		try 
		{
			connection = DB.getConnection();
			ps = connection.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next())
			{
				int id = rs.getInt("id");
				String project = rs.getString("project");				
				String name = rs.getString("name");
				int status = rs.getInt("status");
				String jobid = rs.getString("jobid");
			    
				CrawlTask ct = new CrawlTask();
				ct.setId(id);
				ct.setProject(project);
				ct.setName(name);
				ct.setStatus(status);
				ct.setJobid(jobid);
				res.add(ct);   //将记录加入列表			
			}			
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			DB.close(rs, ps, connection);
		}
		
		return res;
	}
	//
	public boolean isProjectExist(String prj,String name)
	{
		boolean b = false ;
		
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "select * from crawltask where project=? and name=? "; //被删的不读取
		
		try 
		{
			connection = DB.getConnection();
			ps = connection.prepareStatement(sql);
			ps.setString(1,prj);
			ps.setString(2,name);
			rs = ps.executeQuery();
			if(rs.next())
			{
				b = true;			
			}			
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			DB.close(rs, ps, connection);
		}
		
		return b;
	}
	
	
	// 通过id，更新状态，成功返回true
	public boolean updateStatusById(int id,int status)
	{
		boolean flag = false;		
		Connection connection = null;
		PreparedStatement ps = null;
		String sql = "update crawltask set status=? where id=? " ;
		try
		{
			connection = DB.getConnection();			
			ps = connection.prepareStatement(sql);	
			ps.setInt(1, status);
			ps.setInt(2, id);
			int i = ps.executeUpdate();
			if(i>0){
			   flag = true;
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			DB.close(null, ps, connection);
		}
		
		return flag;
	}
	
	public boolean updateStatusByJobId(String jobid,int status)
	{
		boolean flag = false;		
		Connection connection = null;
		PreparedStatement ps = null;
		String sql = "update crawltask set status=? where jobid=?" ;		
		try
		{
			connection = DB.getConnection();			
			ps = connection.prepareStatement(sql);
			ps.setInt(1, status);
			ps.setString(2, jobid);
			int i = ps.executeUpdate();
			if(i>0){			
			   flag = true;
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			DB.close(null, ps, connection);
		}
		
		return flag;
	}
	
	//获取项目名称，获取爬虫状态时使用
	public ArrayList<String> getProject()
	{
		ArrayList<String> res = new ArrayList<String>() ;
		
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "select project from crawltask where status>0 group by project"; //被删的不读取
		
		try 
		{
			connection = DB.getConnection();
			ps = connection.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next())
			{
				
				String project = rs.getString("project");			
			
				res.add(project);   //将记录加入列表			
			}			
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			DB.close(rs, ps, connection);
		}
		
		return res;
	}
	
	
	
	public static void main(String args[])
	{
		
		CrawlTaskDao ctd = new CrawlTaskDao();
		ArrayList<CrawlTask> ls = ctd.getCrawlTask();
		for(int i=0;i<ls.size();i++){
			System.out.println(ls.get(i).getProject()+" "+ls.get(i).getName());
		}
		ctd.updateStatusById(1, -1);
		ctd.updateStatusByJobId("1234567", 1);
		
		ArrayList<String> ls2 = ctd.getProject();
		for(int i=0;i<ls2.size();i++){
			System.out.println(ls2.get(i));
		}
		//
		CrawlTask ct = new CrawlTask();
		ct.setProject("qiancheng");
		ct.setName("itspider");
		ctd.addProject(ct);
		
	}

}
