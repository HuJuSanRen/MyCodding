package spider.scrapyd;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

public class Command {
		
	public static String exec(String[] cmd, File path) {

		final StringBuffer stringBuffer = new StringBuffer();
		try {
			final Process process = Runtime.getRuntime().exec(cmd, null, path);
			new Thread(new Runnable() {
				public void run() {
					try {
						BufferedReader reader = new BufferedReader(
								new InputStreamReader(process.getInputStream()));
						String line = null;
						while ((line = reader.readLine()) != null) {
							stringBuffer.append(line);
						}
						reader.close();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}).start();
			new Thread(new Runnable() {
				public void run() {
					try {
						BufferedReader reader = new BufferedReader(
								new InputStreamReader(process.getErrorStream()));
						String line = null;
						while ((line = reader.readLine()) != null) {
							stringBuffer.append(line);
						}
						reader.close();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}).start();
			process.getOutputStream().close();
			process.waitFor();
			if (process.exitValue() == 0) {
				System.out.println("successfully execute command");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		String str = stringBuffer.toString();
		System.out.println(str);
		return str ;
	}

	public static void main(String[] args) {
	//	String[] cmd = new String[] { "cmd", "/c", "wmic cpu get name" };
		String[] cmd = new String[] { "cmd", "/c", "scrapyd-deploy -p hrtencent -v r1.0.0 " };
        File f = new File("F:\\pywork\\PycharmProjects\\TencentHR\\hrtencent");
		String str = exec(cmd,f);
		System.out.println(str);
	}
}