/**
 * 
 */
package com.sjw.util.nettest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author sjw
 * 2021年4月7日
 */
public class SpeedTest {
	/**
	 * @Description 获取下载速度 MB/s
	 */
	public String getDownloadSpeed() {
		String speed = "";
		try {
			Process pro = Runtime.getRuntime().exec("speedtest-cli --no-upload --simple --bytes");
			BufferedReader buf = new BufferedReader(new InputStreamReader(pro.getInputStream()));
			String line = null;
			while ((line = buf.readLine()) != null) {
				System.out.println(line);
				Pattern pattern = Pattern.compile("(Download: )(\\d+\\.\\d+)", Pattern.CASE_INSENSITIVE);
				Matcher matcher = pattern.matcher(line);
				if (matcher.find()) {
					speed = matcher.group(2);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return speed;
	}
	
	/**
	 * @Description 获取上传速度 MB/s
	 */
	public String getUploadSpeed() {
		String speed = "";
		try {
			Process pro = Runtime.getRuntime().exec("speedtest-cli --no-download --simple --bytes");
			BufferedReader buf = new BufferedReader(new InputStreamReader(pro.getInputStream()));
			String line = null;
			while ((line = buf.readLine()) != null) {
				System.out.println(line);
				Pattern pattern = Pattern.compile("(Upload: )(\\d+\\.\\d+)", Pattern.CASE_INSENSITIVE);
				Matcher matcher = pattern.matcher(line);
				if (matcher.find()) {
					speed = matcher.group(2);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return speed;
	}

//	public static void main(String[] args) {
//		SpeedTest test = new SpeedTest();
//		System.out.println(test.getDownloadSpeed());
//		System.out.println(test.getUploadSpeed());
//	}

}
