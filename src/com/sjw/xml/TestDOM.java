package com.sjw.xml;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.w3c.dom.bootstrap.DOMImplementationRegistry;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSOutput;
import org.w3c.dom.ls.LSSerializer;

public class TestDOM {

	public static void main(String[] args) {
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.newDocument();
			//构建XML中的节点
			Element root = doc.createElement("font");
			Element nameElement = doc.createElement("name");
			Text nameValue = doc.createTextNode("san");
			Element sizeElement = doc.createElement("size");
			sizeElement.setAttribute("unit", "px");
			Text sizeValue = doc.createTextNode("14");
			//按顺序添加各个节点
			doc.appendChild(root);
			root.appendChild(nameElement);
			nameElement.appendChild(nameValue);
			root.appendChild(sizeElement);
			sizeElement.appendChild(sizeValue);
			
			//保存方式1
			Transformer t=TransformerFactory.newInstance().newTransformer();
			//设置换行和缩进
			t.setOutputProperty(OutputKeys.INDENT,"yes");
			t.setOutputProperty(OutputKeys.METHOD, "xml");
			t.transform(new DOMSource(doc), new StreamResult(new FileOutputStream(new File("e:/text.xml"))));//将DOM树序列化为xml文件
			
			//保存方式2
			DOMImplementationRegistry domImplementationRegistry = DOMImplementationRegistry.newInstance();//似乎LSSerializer比Transformer性能要高一点
		    DOMImplementationLS domImplementationLS = (DOMImplementationLS) domImplementationRegistry.getDOMImplementation("LS");
		    LSSerializer serializer = domImplementationLS.createLSSerializer();
		    serializer.getDomConfig().setParameter("well-formed", true);
		    LSOutput out = domImplementationLS.createLSOutput();
		    out.setEncoding("UTF-8");
		    FileWriter writer = new FileWriter("e:/text2.xml");
		    out.setCharacterStream(writer);
		    serializer.write(doc, out);
		    
//		    queryXml();//遍历xml文档
		} catch (DOMException e) {
			e.printStackTrace();
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerFactoryConfigurationError e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassCastException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	/**
     * 遍历xml文档
     * */
    public static void queryXml(){
        try{
            //得到DOM解析器的工厂实例
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            //从DOM工厂中获得DOM解析器
            DocumentBuilder dbBuilder = dbFactory.newDocumentBuilder();
            //把要解析的xml文档读入DOM解析器
            Document doc = dbBuilder.parse("src/xidian/sl/dom/school.xml");
            System.out.println("处理该文档的DomImplementation对象  = "+ doc.getImplementation());
            //得到文档名称为Student的元素的节点列表
            NodeList nList = doc.getElementsByTagName("Student");
            //遍历该集合，显示结合中的元素及其子元素的名字
            for(int i = 0; i< nList.getLength() ; i ++){
                Element node = (Element)nList.item(i);
                System.out.println("Name: "+ node.getElementsByTagName("Name").item(0).getFirstChild().getNodeValue());
                System.out.println("Num: "+ node.getElementsByTagName("Num").item(0).getFirstChild().getNodeValue());
                System.out.println("Classes: "+ node.getElementsByTagName("Classes").item(0).getFirstChild().getNodeValue());
                System.out.println("Address: "+ node.getElementsByTagName("Address").item(0).getFirstChild().getNodeValue());
                System.out.println("Tel: "+ node.getElementsByTagName("Tel").item(0).getFirstChild().getNodeValue());
            }
            
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

}
