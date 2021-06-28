package com.sjw.xml;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentFactory;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.ProcessingInstruction;
import org.dom4j.QName;
import org.dom4j.VisitorSupport;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.dom4j.io.XPP3Reader;

public class TestDOM4J {

	public static void main(String[] args)  throws Exception{
		//使用XPP3Reader来解析XML文档
		XPP3Reader reader = new XPP3Reader();
		//使用SAXReader来解析XML文档
//		SAXReader reader = new SAXReader();
		Document doc = reader.read(new File("alarmCodeInfo_zh.xml"));
		Element root = doc.getRootElement();
		parse(root);
		
		//节点查询
		//selectNodes(doc);
		
		//使用访问者模式来访问XML文档
		//doc.accept(new MyVistor());
		
		//writeNewDoc();

	}
	
	/**
	 * @Description
	 * @param doc
	 */
	private static void selectNodes(Document document) {
		System.out.println("节点查询：");
		
		List<Node> list = document.selectNodes("//computer_book/book_name");//选取所有 computer_book子元素下的 book_name元素，而不管它们在文档中的位置。

	    Node node = document.selectSingleNode("//computer_book");//选取根元素bookList
	    String name = node.valueOf("@intro-moive");//获取节点的name属性
	    
	    Node node2 = document.selectSingleNode("//computer_book[@intro-moive='movie']");//根据节点属性选择节点
	    System.out.println("node2.valueOf intro-moive:" + node2.valueOf("@intro-moive"));
	    Element element = node2.getParent();
	    QName qName = new QName("computer_book");
	    List<Element> elements = element.elements(qName);
	    System.out.println(elements.get(0).getName());
	}

	private static void writeNewDoc() throws Exception {
		//创建一个DocumentFactory对象
		DocumentFactory factory = new DocumentFactory();
		//创建一个Document对象
		Document doc = factory.createDocument();
		//向Document对象中添加一个处理指令节点
		doc.addProcessingInstruction("crazyit" 
			, "website=\"http://www.crazyit.org\"");
		//向doc中添加根节点元素
		Element root = doc.addElement("书籍列表");
		//采用循环方式添加4个子元素
		for (int i = 0; i < 4 ; i++ )
		{
			//创建一个“计算机书籍”子元素
			Element pcBook = root.addElement("计算机书籍");
			//添加一个随机数作为isbn属性值
			pcBook.addAttribute("isbn" , Math.round((Math.random() * 1000)) + ""); 
			//为“计算机书籍”元素添加“书名”子元素，并设置属性值
			Element name = pcBook.addElement("书名");
			name.setText("书籍" + i);
			//为“计算机书籍”元素添加“价格”子元素，并设置属性值
			Element price = pcBook.addElement("价格");
			price.setText(i * 10 + "");
		}
		//定义一个输出格式对象
		OutputFormat format = new OutputFormat("	", true, "GBK"); 
		FileWriter fw = new FileWriter("dom4jnewbook.xml");
		//定义一份XMLWriter对象
		XMLWriter writer = new XMLWriter(fw , format); 
		writer.write(doc);  
		fw.close();
		
	}

	public static void parse(Element ele){
		//处理当前元素包含的所有属性
		parseAttribute(ele);
		//获取当前元素包含的所有子元素
		List el = ele.elements(); 
		//遍历每个子元素
		for (Object e : el){
			Element element = (Element)e;
			//如果该元素的内容不是只包含字符串
			if (!element.isTextOnly()){
				parse(element);
			}else{
				//处理当前元素的全部属性
				parseAttribute(element);
				//获取当前元素的内容
				System.out.println(element.getQName().getName() 
					+ "--->" + element.getText());
			}
		}
	}
	
	//定义一个方法处理指定元素的所有属性
	public static void parseAttribute(Element ele){
		//获取Element元素的所有属性
		List attList = ele.attributes();
		//遍历Element元素每个属性 
		for (Object e : attList){
			Attribute attr = (Attribute)e;
			//访问当前元素的每个属性的属性值
			System.out.println(ele.getQName().getName() + "元素的"
				+ attr.getQName().getName() + "属性值为：" 
				+ attr.getValue());
		}
	}

}

//定义一个Visitor实现类
class MyVistor extends VisitorSupport{
	//保存当前正在处理的节点
	private String currentElement;
	//当Visitor访问元素时回调该方法
	public void visit(Element node){
		//如果节点内容全部是文本
		if (node.isTextOnly()){
			System.out.println(node.getName() + "元素的值是："
				+ node.getText());
		}
		currentElement = node.getName();
	}
	//当Visitor访问属性时回调该方法
	public void visit(Attribute node){
		System.out.println(currentElement + "元素的"
			+ node.getName() + "属性的值是："
			+ node.getText());
	}
	//当Visitor访问处理指令时回调该方法
	public void visit(ProcessingInstruction node){
		System.out.println("处理指令"
			+ node.getTarget() + "的内容是："
			+ node.getText());
	}
}
