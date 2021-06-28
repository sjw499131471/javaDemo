/**
 * 
 */
package com.sjw.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.SocketException;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

/**
 * @author sjw
 * 2020年9月25日
 */
public class FtpClientTest {

	/**
	 * @Description
	 * @param args
	 */
	public static void main(String[] args) {
		InputStream inputStream = null;
		try {
			FTPClient client = new FTPClient();
			System.out.println("connect");
			client.setConnectTimeout(3000);
			client.connect("192.168.1.114", 21);//192.168.1.253
			System.out.println("login");
			if (FTPReply.isPositiveCompletion(client.getReplyCode())) {
				boolean result = client.login("ftpUser", "123");
				System.out.println("result:" + result);
				if (FTPReply.isPositiveCompletion(client.getReplyCode())) {//是否连接成功,成功true,失败false
					String dir=new String("/天气预报".getBytes("GBK"),"iso-8859-1");
					client.changeWorkingDirectory(dir);
					//FTPFile[] files = client.listFiles();
					String fileName = new String("南通市气象台2020年4月1日6时未来六天天气预报.txt".getBytes("GBK"),"iso-8859-1");
					inputStream = client.retrieveFileStream(fileName);
					BufferedReader bufferReader = new BufferedReader(new InputStreamReader(inputStream,"GBK"));
					while( true ) {
					    String tempStr = bufferReader.readLine();
					    if( tempStr == null ) {
					        break;
					    } else{
					    	//String string = new Str
					        System.out.println(tempStr);
					    }
					}
					if (client.completePendingCommand()) {//必须调用此方法判断上个事务是否结束
						System.out.println("success");
					}else {
						//
					}
					client.logout();
					client.disconnect();
				}else {
					client.disconnect();
				}
			}
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			
		}

	}

}
