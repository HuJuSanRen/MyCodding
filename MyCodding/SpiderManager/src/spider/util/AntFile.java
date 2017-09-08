package spider.util;

import java.io.File;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.DirectoryScanner;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.Copy;
import org.apache.tools.ant.taskdefs.Delete;
import org.apache.tools.ant.taskdefs.Expand;
import org.apache.tools.ant.taskdefs.Mkdir;

/**
 * 2013.12.19
 * ant 
 * @author zq
 *
 */
public class AntFile {
	//解压缩文件,src是压缩文件，dest是解压的目标目录，成功返回1，失败返-1
	public static int unzip(File src,File dest){
		int res = -1;
		try {
			Project prj=new Project(); 
			Expand expand=new Expand(); 
			expand.setProject(prj); 
			expand.setSrc(src); 
			expand.setOverwrite(true); 
			expand.setDest(dest); 
			expand.execute();
			res = 1;
		} catch (BuildException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();			
		} 
		return res;
	}
	
	//删除目录及其下所有文件含子目录,成功返回1，失败返回-1
	public static int deleteDir(File f){
		int res = -1;
    	try {
			Project prj=new Project(); 
			Delete delete=new Delete(); 
			delete.setProject(prj); 
			delete.setDir(f); //可同时将子目录及所有文件删除 
			delete.execute();
			res = 1;
		} catch (BuildException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
    	return res;
	}
	
	//删除文件,成功返回1，失败返回-11
	public static int deleteFile(File f){
		int res = -1;
    	try {
			Project prj=new Project(); 
			Delete delete=new Delete(); 
			delete.setProject(prj); 
			delete.setFile(f); //��ͬʱ����Ŀ¼�������ļ�ɾ�� 
			delete.execute();
			res = 1;
		} catch (BuildException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
    	return res;
	}
	
		
	public static void main(String[] args){
		File src =new File("./demo/7/Selenium.zip"); //
		File dest=new File("./demo/7/");
		AntFile.unzip(src, dest);
		

	
	}
 
}
