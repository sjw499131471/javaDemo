package com.sjw.util;
 
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import org.apache.commons.io.FileUtils;
 
/**
 * 修改文件编码
 * @author Administrator
 *
 */
public class GBKtoUTF8Util {
	public static void main(String[] args) throws IOException {
		//GBK编码格式源码路径
		String srcDirPath = "E:\\svn\\NET\\source\\UNMS-CLIENT_V3.1.9\\src\\com\\zte\\lmt\\ump\\cpm\\vermanage\\publicUtil";
		//转为UTF-8编码格式源码路径
		String utf8DirPath = "E:\\temp\\publicUtil";
		//获取所有java文件 
		Collection<File> javaGbkFileCol = FileUtils.listFiles(new File(srcDirPath), new String[] { "java" }, true);
 
		for (File javaGbkFile : javaGbkFileCol) {
			//UTF8格式文件路径 
			String utf8FilePath = utf8DirPath + javaGbkFile.getAbsolutePath().substring(srcDirPath.length());
			//使用GBK读取数据，然后用UTF-8写入数据 
			FileUtils.writeLines(new File(utf8FilePath), "UTF-8", FileUtils.readLines(javaGbkFile, "GBK"));
		}
	}
}