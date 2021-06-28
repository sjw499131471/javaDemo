package com.sjw.net;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;

/**
 * 测试Socket的使用
 * @author Administrator
 *
 */
public class TestSocket {

	public static void main(String[] args) throws Exception{
//		SocketBasic();
		ServerSocketBasic();
		
		
	}

	private static void ServerSocketBasic() throws Exception{
		//1.构造函数
//		ServerSocket serverSocket = new ServerSocket();//无参构造函数的作用是在绑定端口前先设置一些参数
		ServerSocket serverSocket = new ServerSocket(8989);//端口号8989
//		ServerSocket serverSocket = new ServerSocket(8989,50,InetAddress.getByName("localhost"));//第二个参数表示只允许50个连接连接服务器，第三个参数绑定服务器IP地址
		
		//2.获取ServerSocket信息
		serverSocket.getLocalPort();
		serverSocket.getInetAddress();
		
		//3.接收客户端信息
		serverSocket.accept();
		
		//4.ServerSocket选项:1--SO_TIMEOUT,2--SO_REUSEADDR,3--SO_RCVBUF
		
		
		serverSocket.close();
	}

	private static void SocketBasic() throws Exception{
		/**
		 * 1.构造Socket
		 * #除了无参构造函数，其他都会尝试与服务器建立连接
		 * #默认情况下Socket客户端的IP为本机IP，端口号由操作系统自动分配
		 */
		//1.1 设置请求超时时间
		Socket socket = new Socket();
		SocketAddress socketAddress = new InetSocketAddress("localhost", 8989);
		socket.connect(socketAddress, 5000);
		
		//1.2 设置服务器地址
		//Socket socket = new Socket("localhost",8989);
		InetAddress inetAddress = InetAddress.getLocalHost();//获取本机IP地址
		InetAddress inetAddress2 = InetAddress.getByName("127.0.0.1");
		InetAddress inetAddress3 = InetAddress.getByName("www.baidu.com");
		//Socket socket = new Socket(inetAddress,8989);
		
		//1.3 当主机同属两个网络，有两个IP是，与服务器建立连接时需要设置本机IP
		InetAddress serverIP = InetAddress.getByName("192.168.1.107");
		InetAddress localIP = InetAddress.getByName("192.168.1.103");
		//Socket socket = new Socket(serverIP,8989,localIP,8988);
		
		//2.获取Socket信息
		InetAddress inetAddress4 = socket.getInetAddress();
		int port = socket.getPort();
		InetAddress inetAddress5 = socket.getLocalAddress();
		int port2 = socket.getLocalPort();
		InputStream is = socket.getInputStream();
		OutputStream os = socket.getOutputStream();
		boolean b1 = socket.isBound();
		boolean b2 = socket.isClosed();
		boolean b3 = socket.isConnected();
		boolean b4 = socket.isInputShutdown();
		boolean b5 = socket.isOutputShutdown();
		
		//3.半关闭socket
//		socket.shutdownInput();
//		socket.shutdownOutput();
		
		/**
		 * 4.设置Socket选项
		 */
		//1.TCP_NODELAY:默认情况下使用	Negale算法，即发送方的数据不会立即发送，而是等缓冲区满之后再发送，发送完一批数据后会等接收端的回应，再发送下一批数据。此算法适用于需要传送大批量数据并且需要接受端回应的情况，减少通信次数，提高通信效率。
		//当需要持续发送少量数据并不需要客户端立即响应时，可以将TCP_NODELAY设置为true
//		socket.setTcpNoDelay(true);
		//2.SO_RESUSEADDR:固定端口的服务器关闭之后，绑定的端口不一定会立即释放，此时立即重启服务器会导致绑定端口失败（已经被占用），为了确保一个进程关闭Socket之后，即使它还没有释放端口，同一台主机的其他进程还可以立即重用该端口。
		//设置SO_RESUSEADDR需要在绑定端口号之前,即必须使用无参构造函数，再设置SO_RESUSEADDR，再绑定本地端口。
//		socket.setReuseAddress(true);
		//3.SO_TIMEOUT:超时时间
//		socket.setSoTimeout(2000);//2s
		//4.SO_LINGER：逗留时间设置。默认情况下socket调用close之后不是立即关闭，而是要等待一段时间等所有数据传送送接受端再关闭。当设置了已秒为单位的逗留时间后，过了逗留时间就会立即关闭，剩余数据（已发送但还未传输到接受端的数据）将被抛弃。
//		socket.setSoLinger(true, 2);//2秒
		//5.SO_RCVBUF/SO_SNDBUF:设置发送和接受的缓冲区大小
//		socket.setSendBufferSize(1000);
//		socket.setReceiveBufferSize(1000);
		//6.SO_KEEPALIVE:底层TCP实现会监听连接是否有效。如果连接失效，则会自动关闭Socket，断开连接。
//		socket.setKeepAlive(true);//默认是false
		//7.设置服务类型：0x02--低成本，0x04--高可靠性，0x08：最高吞吐量，0x10：最小延迟
//		socket.setTrafficClass(0x02|0x04);//请求低成本及高可靠性
		//8.设置连接时间、延迟和带宽的相对重要新
//		socket.setPerformancePreferences(1, 3, 2);//表示连接时间最重要，延迟最次要。
		
		
		socket.close();		
	}

}
