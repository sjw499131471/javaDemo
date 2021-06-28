package com.sjw.basic;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;

public class NIOTest {

	public static void main(String[] args) throws Exception{
		fileChanelTest();
		
		randomFileChanelTest();
		
		readFile();
		
		charSetTest();
		
		fileLockTest();
	}

	private static void fileLockTest() {
		FileChannel channel = null;
		try
		{
			//使锟斤拷FileOutputStream锟斤拷取FileChannel
			channel = new FileOutputStream("a.txt")
				.getChannel();
			//使锟矫凤拷锟斤拷锟斤拷式锟斤拷式锟斤拷指锟斤拷锟侥硷拷锟斤拷锟斤拷
			FileLock lock = channel.tryLock();
			//锟斤拷锟斤拷锟斤拷停5s
			Thread.sleep(5000);
			//锟酵凤拷锟斤拷
			lock.release();
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			try
			{
				if (channel != null)
					channel.close();
			}
			catch (IOException ex)
			{
				ex.printStackTrace();
			}
		}
	}

	private static void charSetTest() throws Exception{
		//锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟侥讹拷应锟斤拷Charset
				Charset cn = Charset.forName("GBK");
				//锟斤拷取cn锟斤拷锟斤拷锟接︼拷谋锟斤拷锟斤拷锟斤拷徒锟斤拷锟斤拷锟�
				CharsetEncoder cnEncoder = cn.newEncoder();
				CharsetDecoder cnDecoder = cn.newDecoder();
				//锟斤拷锟斤拷一锟斤拷CharBuffer锟斤拷锟斤拷
				CharBuffer cbuff = CharBuffer.allocate(8);
				cbuff.put('1');
				cbuff.put('2');
				cbuff.put('3');
				cbuff.flip();
				//锟斤拷CharBuffer锟叫碉拷锟街凤拷锟斤拷锟斤拷转锟斤拷锟斤拷锟街斤拷锟斤拷锟斤拷
				ByteBuffer bbuff = cnEncoder.encode(cbuff);
				//循锟斤拷锟斤拷锟斤拷ByteBuffer锟叫碉拷每锟斤拷锟街斤拷
				for (int i = 0; i < bbuff.capacity() ; i++)
				{
					System.out.print(bbuff.get(i) + " ");
				}
				//锟斤拷ByteBuffer锟斤拷锟斤拷萁锟斤拷锟斤拷锟街凤拷锟斤拷锟斤拷
				System.out.println("\n"
					+ cnDecoder.decode(bbuff));
	}

	private static void readFile() {
		FileChannel fcin = null;
		try
		{
			//锟斤拷锟斤拷锟侥硷拷锟斤拷锟斤拷锟斤拷
			FileInputStream fis = new FileInputStream("ReadFile.java");
			//锟斤拷锟斤拷一锟斤拷FileChannel
			fcin = fis.getChannel();
			fis.close();
			//锟斤拷锟斤拷一锟斤拷ByteBuffer锟斤拷锟斤拷锟斤拷锟斤拷锟截革拷取水
			ByteBuffer bbuff = ByteBuffer.allocate(1024);
			//锟斤拷FileChannel锟斤拷锟斤拷莘锟斤拷锟紹yteBuffer锟斤拷
			while( fcin.read(bbuff) != -1 )
			{
				//锟斤拷Buffer锟侥空帮拷锟斤拷
				bbuff.flip();         
				//锟斤拷锟斤拷Charset锟斤拷锟斤拷
				Charset charset = Charset.forName("gb2312");
				//锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷(CharsetDecoder)锟斤拷锟斤拷
				CharsetDecoder decoder = charset.newDecoder();
				//锟斤拷ByteBuffer锟斤拷锟斤拷锟斤拷转锟斤拷
				CharBuffer cbuff = decoder.decode(bbuff);
				System.out.println(cbuff);
				//锟斤拷Buffer锟斤拷始锟斤拷锟斤拷为锟斤拷一锟斤拷取锟斤拷锟斤拷锟阶硷拷锟�
				bbuff.clear();
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
				if (fcin != null)
					fcin.close();
			}
			catch (IOException ex)
			{
				ex.printStackTrace();
			}
		}
	}

	private static void randomFileChanelTest() {
		FileChannel randomChannel = null;
		try
		{
			File f = new File("a.txt");
			//锟斤拷锟斤拷一锟斤拷RandomAccessFile锟斤拷锟斤拷
			RandomAccessFile raf = new RandomAccessFile(f, "rw");
			//锟斤拷取RandomAccessFile锟斤拷应锟斤拷Channel
			randomChannel = raf.getChannel();
			//锟斤拷Channel锟斤拷锟斤拷锟斤拷锟斤拷锟接筹拷锟斤拷ByteBuffer
			ByteBuffer buffer = randomChannel.map(FileChannel.MapMode.READ_ONLY,
				0 , f.length());
			//锟斤拷Channel锟侥硷拷录指锟斤拷锟狡讹拷锟斤拷锟斤拷锟�
			randomChannel.position(f.length());
			//锟斤拷buffer锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷
			randomChannel.write(buffer);
		}
		catch (IOException ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			try
			{
				if (randomChannel != null)
					randomChannel.close();
			}
			catch (IOException ex)
			{
				ex.printStackTrace();
			}
		}		
	}

	private static void fileChanelTest() {
		FileChannel inChannel = null;
		FileChannel outChannel = null;
		try
		{
			File f = new File("FileChannelTest.java");
			//锟斤拷锟斤拷FileInputStream锟斤拷锟皆革拷锟侥硷拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷FileChannel
			inChannel = new FileInputStream(f).getChannel();
			//锟斤拷FileChannel锟斤拷锟饺拷锟斤拷锟斤拷映锟斤拷锟紹yteBuffer
			MappedByteBuffer buffer = inChannel.map(FileChannel.MapMode.READ_ONLY,0 , f.length());
			//使锟斤拷GBK锟斤拷锟街凤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷
			Charset charset = Charset.forName("GBK");
			//锟斤拷锟侥硷拷锟斤拷锟斤拷锟斤拷锟斤拷锟紽ileBuffer锟斤拷锟斤拷锟皆匡拷锟斤拷锟斤拷锟�
			outChannel = new FileOutputStream("a.txt").getChannel();
			//直锟接斤拷buffer锟斤拷锟斤拷锟斤拷全锟斤拷锟斤拷锟�
			outChannel.write(buffer);
			//锟劫次碉拷锟斤拷buffer锟斤拷clear()锟斤拷锟斤拷锟斤拷锟斤拷原limit锟斤拷position锟斤拷位锟斤拷
			buffer.clear();
			//锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷(CharsetDecoder)锟斤拷锟斤拷
			CharsetDecoder decoder = charset.newDecoder();
			//使锟矫斤拷锟斤拷锟斤拷锟斤拷ByteBuffer转锟斤拷锟斤拷CharBuffer
			CharBuffer charBuffer =  decoder.decode(buffer);
			//CharBuffer锟斤拷toString锟斤拷锟斤拷锟斤拷锟皆伙拷取锟斤拷应锟斤拷锟街凤拷
			System.out.println(charBuffer);
		}
		catch (IOException ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			try
			{
				if (inChannel != null)
					inChannel.close();
				if (outChannel != null)
					outChannel.close();	
			}
			catch (IOException ex)
			{
				ex.printStackTrace();
			}
		}
	}

}
