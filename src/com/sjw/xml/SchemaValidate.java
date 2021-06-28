package com.sjw.xml;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;
import org.xml.sax.*;
import org.xml.sax.helpers.*;
import javax.xml.parsers.*;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.*;
import javax.xml.*;
import java.io.*;

public class SchemaValidate
{
	public static void main(String[] args) throws Exception
	{
		//创建SchemaFactory对象
		SchemaFactory schemaFactory = SchemaFactory.newInstance(
			XMLConstants.W3C_XML_SCHEMA_NS_URI);
		//通过SchemaFactory对象创建Schema对象
		Schema schema = schemaFactory.newSchema(new File("book.xsd"));
		System.out.println("--------下面使用DOM解析--------");
		//创建DOM解析器工厂实例
		DocumentBuilderFactory domFactory = DocumentBuilderFactory
			.newInstance();
		//启用命名空间支持
		domFactory.setNamespaceAware(true); 
		//为domFactory设置验证有效性的Schema对象
		domFactory.setSchema(schema);
		//创建DOM解析器
		DocumentBuilder builder = domFactory.newDocumentBuilder();
		//为DOM解析器注册ErrorHandler监听器
		builder.setErrorHandler(new CrazyItErrorHandler());
		//开始解析
		builder.parse("book.xml");
		System.out.println("--------下面使用SAX解析--------");
		//创建SAX解析器工厂实例
		SAXParserFactory saxFactory = SAXParserFactory
			.newInstance();
		//为saxFactory设置验证有效性的Schema对象
		saxFactory.setSchema(schema);
		//启用命名空间支持
		saxFactory.setNamespaceAware(true); 
		//创建SAX解析器
		SAXParser parser = saxFactory.newSAXParser();
		//开始解析
		parser.parse("bookUseXSD.xml" , new CrazyItErrorHandler());
		
		System.out.println("--------下面是仅验证有效性不解析--------");
		OnlyValidate(schema);
		
		System.out.println("--------下面使用dom4j进行验证--------");
		validateUsingdom4j();
	}

	//定义一个内部类作为ErrorHandler
	public static class CrazyItErrorHandler extends DefaultHandler
	{
		//当发生校验错误时触发该方法
		public void error(SAXParseException exception) 
			throws SAXException
		{
			System.out.println("---error方法---");
			System.out.println(exception.getSystemId() + "文档的第"
				+ exception.getLineNumber()
				+ "行，第" + exception.getColumnNumber() 
				+ "发生有效性错误，错误信息是：" + exception.getMessage());
		}
	}

	private static void OnlyValidate(Schema schema) {
		//创建Validator对象
		Validator validator = schema.newValidator();
		//为Validator对象注册ErrorHandler
		validator.setErrorHandler(new CrazyItErrorHandler());
		//调用Validator对象的validate()方法验证XML文档的有效性。
		try {
			validator.validate(new StreamSource(new File("book.xml")));
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static void validateUsingdom4j() {
		try {
			//创建启用DTD验证功能的SAXReader对象
			SAXReader reader = new SAXReader(true);
			//启用XML Schema验证XML文档的有效性
			reader.setFeature("http://apache.org/xml/features/validation/schema"
				, true);
			//为XML文档设置检验有效性的Schema文档
//			reader.setProperty(
//				"http://apache.org/xml/properties/schema/external-noNamespaceSchemaLocation"
//				, "book.xsd"
//			);//xml文件指定了schema则无需设置
			//为SAXReader设置ErrorHandler
			reader.setErrorHandler(new DefaultHandler()
			{
				public void error(SAXParseException exception) 
					throws SAXException
				{
					System.out.println("---error方法---");
					System.out.println(exception.getSystemId() + "文档的第"
						+ exception.getLineNumber()
						+ "行，第" + exception.getColumnNumber() 
						+ "发生有效性错误，错误信息是：" + exception.getMessage());
				}
			}); 
			Document doc = reader.read(new File("bookUseXSD.xml"));
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		
	}
}
