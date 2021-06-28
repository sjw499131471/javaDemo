package com.sjw.basic;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintStream;
import java.io.RandomAccessFile;
import java.io.Reader;
import java.io.Serializable;
import java.io.Writer;
import java.util.Scanner;

/**
ASCII（数字、英文）:1个字符占一个字节（所有的编码集都兼容ASCII）
ISO8859-1（欧洲）：1个字符占一个字节
GB-2312/GBK：1个字符占两个字节
Unicode: 1个字符占两个字节（网络传输速度慢）
UTF-8：变长字节，对于英文一个字节，对于汉字两个或三个字节。
 * @author sjw
 *
 */
public class IOTest {

	public static void main(String[] args) {
		String fileName = "E:" + File.separator + "test.txt";
		//创建一个文件
		File file = new File(fileName);
		OutputStream out = null;
		InputStream is = null;
		InputStream is2 = null;
		RandomAccessFile raf = null;
		try {
			//file.createNewFile();
			//往文件写内容
//			out =new FileOutputStream(file);
//			String str = "你好";
//			byte[] strByte = str.getBytes();
//			out.write(strByte);//内存往文件写
			
			//已知文件长度：从文件读取内容
//			is = new FileInputStream(file);
//			byte[] bytes =new byte[(int) file.length()];
//			is.read(bytes);//内存从文件读取
//			System.out.println(new String(bytes));
			//未知文件长度：从文件读取内容
//			is2 = new FileInputStream(file);
//			byte[] bytes2 = new byte[1024];
//			int index= 0;
//			int tempByte = 0;
//			while ((tempByte=is2.read())!=-1) {
//				bytes2[index++] = (byte)tempByte;
//			}
//			System.out.println(new String(bytes2));//你好
			
			//管道流：两个线程之间的通信。
//			Send send=new Send();
//	        Recive recive=new Recive();
//	        try{
//	        	//管道连接
//	            send.getOut().connect(recive.getInput());
//	        }catch (Exception e) {
//	            e.printStackTrace();
//	        }
//	        new Thread(send).start();
//	        new Thread(recive).start();
	        
			//控制台输出转存
//	        System.setOut(new PrintStream(file));
//	        System.out.println("本内容输出在文件里！");
//	        System.out.println("本内容输出在文件里：追加！");
	        
	        //字符流:字节流在操作的时候本身是不会用到缓冲区的，是文件本身的直接操作的，但是字符流在操作的时候是通过缓冲区来操作文件的。
//	        Writer writer = new FileWriter(file);
//	        writer.write(str);
//	        writer.close();
//	        Reader reader = new FileReader(file);
//	        char[] chars = new char[100];
//	        int readCount = reader.read(chars);
//	        System.out.println("长度：" + readCount);
//	        System.out.println(new String(chars, 0, readCount));
//			reader.close();
	        //字节流转字符流
//	        Writer charWriter = new OutputStreamWriter(new FileOutputStream(file));
//	        charWriter.write(str);
//	        charWriter.close();
	        
	        //缓冲区输入流:VM的内部建立一个缓冲区,使用带缓冲区的输入输出流的速度会大幅提高，缓冲区越大，效率越高。
//	        BufferedReader bufferReader = new BufferedReader(new InputStreamReader(System.in));
//	        System.out.println("请输入内容：");
//	        String sysInput = bufferReader.readLine();
//	        System.out.println("您输入了：" + sysInput);
//	        bufferReader.close();//切记：最后调用flash或close方法释放资源
	        
	        //对象持久化
//	        ObjectOutputStream objectOS = new ObjectOutputStream(new FileOutputStream(file));
//	        objectOS.writeObject(new Person("李明",22));
//	        objectOS.close();
			
//			ReadFromProcess.run(null);
//			WriteToProcess.run(null);
			
			//RandomAccessFile可以随意操作，读取文件，比如从文件中间开始读取文件而不是从头开始
//			raf = new RandomAccessFile("out.txt" , "rw");
//			//将记录指针移动的out.txt文件的最后
//			raf.seek(raf.length());
//			raf.write("追加的内容！\r\n".getBytes());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (out!= null) {
				try {
					out.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			if (is!= null) {
				try {
					is.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			if (is2!= null) {
				try {
					is2.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			if (raf != null) {
				try {
					raf.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
	}

}

/**
 * 消息发送类
 * */
class Send implements Runnable{
    private PipedOutputStream out=null;
    public Send() {
        out=new PipedOutputStream();
    }
    public PipedOutputStream getOut(){
        return this.out;
    }
    public void run(){
        String message="hello , Rollen";
        try{
            out.write(message.getBytes());
        }catch (Exception e) {
            e.printStackTrace();
        }try{
            out.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
 
/**
 * 接受消息类
 * */
class Recive implements Runnable{
    private PipedInputStream input=null;
    public Recive(){
        this.input=new PipedInputStream();
    }
    public PipedInputStream getInput(){
        return this.input;
    }
    public void run(){
        byte[] b=new byte[1000];
        int len=0;
        try{
            len=this.input.read(b);
        }catch (Exception e) {
            e.printStackTrace();
        }try{
            input.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("接受的内容为 "+(new String(b,0,len)));
    }
}

class Person implements Serializable{//实现序列化接口可以持久化对象
	private static final long serialVersionUID = 1L;
	String name;//transient关键字可以表示该字段不序列化
	int age;
	
	public Person(){
    }
 
    public Person(String name, int age){
        this.name = name;
        this.age = age;
    }
    
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	
	@Override
	public String toString() {
		return "姓名：" + name + "年龄：" + age;
	}
}

class ReadFromProcess
{
	public static void run(String[] args)
	{
		BufferedReader br = null;
		try
		{
			//运行javac命令，返回运行该命令的子进程
			Process p = Runtime.getRuntime().exec("javac");
			//以p进程的错误流创建BufferedReader对象（这个错误流对本程序是输入流，对p进程则是输出流）
			br = new BufferedReader(new InputStreamReader(p.getErrorStream()));
			String buff = null;
			//采取循环方式来读取p进程的错误输出
			while((buff = br.readLine()) != null)
			{
				System.out.println(buff);
			}
		}
		catch (IOException ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			try
			{
				if (br != null)
					br.close();
			}
			catch (IOException ex)
			{
				ex.printStackTrace();
			}
		}
	}
}

class WriteToProcess
{
	public static void run(String[] args) 
	{
		PrintStream ps = null;
		try
		{
			//运行java ReadStandard命令，返回运行该命令的子进程
			Process p = Runtime.getRuntime().exec("java ReadStandard");
			//以p进程的输出流创建PrintStream对象
			//这个输出流对本程序是输出流，对p进程则是输入流）
			ps = new PrintStream(p.getOutputStream());
			//向ReadStandard程序写入内容，这些内容将被ReadStandard读取
			ps.println("普通字符串");
			ps.println(new WriteToProcess());
		}
		catch (IOException ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			if (ps != null)
				ps.close();
		}
	}
}
//定义一个ReadStandard类，该类可以接受标准输入，
//并将标准输入写入out.txt文件。
class ReadStandard
{
	public static void main(String[] args) throws Exception
	{

		//使用System.in创建Scanner对象，用于获取标准输入
		Scanner sc = new Scanner(System.in);
		PrintStream ps = new PrintStream(
			new FileOutputStream("out.txt"));
		//增加下面一行将只把回车作为分隔符
		sc.useDelimiter("\n");
		//判断是否还有下一个输入项
		while(sc.hasNext())
		{
			//输出输入项
			ps.println("键盘输入的内容是：" + sc.next());
		}
		ps.close();
	}
}
