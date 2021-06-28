/**
 * 
 */
package com.sjw;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Collection;
import java.util.Date;

import org.apache.commons.io.FileUtils;

import com.sun.org.apache.xalan.internal.xsltc.compiler.Template;

/**
 * @author Administrator
 * 2019年6月25日
 */
public class Test {
	public static DecimalFormat fnum = new DecimalFormat("##0.00");
	public static void main(String[] args) {
		Temp();

	}
	
	/**
	 * @Description
	 */
	private static void Temp() {
		float percent = 0 * 100f / 10;
		System.out.println(fnum.format(percent));
	}

	public static void GBKtoUTF8() {
		//GBK编码格式源码路径
		String srcDirPath = "E:\\svn\\NET\\source\\UNMS-CLIENT_V3.1.9\\src\\com\\zte\\lmt\\ump\\fpm\\almagent";
		//转为UTF-8编码格式源码路径
		String utf8DirPath = "E:\\temp\\alarmagent";
		//获取所有java文件 
		Collection<File> javaGbkFileCol = FileUtils.listFiles(new File(srcDirPath), new String[] { "java" }, true);
 
		for (File javaGbkFile : javaGbkFileCol) {
			//UTF8格式文件路径 
			String utf8FilePath = utf8DirPath + javaGbkFile.getAbsolutePath().substring(srcDirPath.length());
			//使用GBK读取数据，然后用UTF-8写入数据 
			try {
				FileUtils.writeLines(new File(utf8FilePath), "UTF-8", FileUtils.readLines(javaGbkFile, "GBK"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
