package com.sjw.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import org.apache.commons.net.io.CRLFLineReader;

public class EchoClient {
	private String serverIP = "192.168.1.253";//192.168.1.253
	private int serverPort = 21;
	Socket socket = null;

	public static void main(String[] args) {
		try {
			new EchoClient().sendRequest();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public EchoClient() throws UnknownHostException, IOException {
		System.out.println("serverIP" + serverIP + "serverPort" +serverPort);
		socket = new Socket(serverIP, serverPort);
		socket.setSoTimeout(5000);
		System.out.println("连接成功.");
	}
	
	public void sendRequest() {
		try {
			BufferedReader responseBR = new BufferedReader(new InputStreamReader(socket.getInputStream()));//服务器响应
			System.out.println(responseBR.readLine());
			PrintWriter requestPW = new PrintWriter(socket.getOutputStream(), true);
			BufferedReader localBR = new BufferedReader(new InputStreamReader(System.in));//接收用户输入
			
			String msg = null;
			while((msg = localBR.readLine()) != null){
				requestPW.println(msg);//发送请求
				System.out.println(responseBR.readLine());//读取服务器响应
				
				if (msg.equals("bye")) {
					break;
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
