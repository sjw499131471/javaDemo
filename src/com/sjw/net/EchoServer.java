package com.sjw.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoServer {

	public static void main(String[] args) {
		try {
			new EchoServer().service();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private ServerSocket serverSocket;
	private int port = 21;
	public EchoServer() throws IOException {
		System.out.println("port" + port);
		serverSocket = new ServerSocket(port);//创建服务器套接字实例，赋端口号
	}
	
	public void service() {
		while(true){
			Socket socket = null;
			try {
				System.out.println("服务器启动");
				socket = serverSocket.accept();//开始接收客户端的请求
				System.out.println("新连接ip：" + socket.getInetAddress() + "端口：" + socket.getPort());
				BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));//把socket的字节流输入转换为字符流输入
				PrintWriter printWriter = new PrintWriter(socket.getOutputStream(),true);////把socket的字节流输出转换为字符流输出,autoFlash参数为true：调用the println, printf, or format methods will flush the output buffer

				
				String requestStr = null;
				while((requestStr = bufferedReader.readLine()) != null){
					System.out.println("请求：" + requestStr);
					printWriter.println("响应：" + requestStr);
					if (requestStr.equals("bye")) {
						break;
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}finally {
				if(socket!=null)
					try {
						System.out.println("连接关闭");
						socket.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
			}
		}
	}

}
