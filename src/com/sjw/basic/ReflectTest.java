package com.sjw.basic;

import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ReflectTest {

	public static void main(String[] args) throws Exception {
		//下面代码可以获取ClassTest对应的Class
		Class<ClassTest> clazz = ClassTest.class;
		//获取该Class对象所对应类的全部构造器
		Constructor[] ctors = clazz.getDeclaredConstructors();
		System.out.println("ClassTest的全部构造器如下：");
		for (Constructor c : ctors)
		{
			System.out.println(c);
		}
		//获取该Class对象所对应类的全部public构造器
		Constructor[] publicCtors = clazz.getConstructors();
		System.out.println("ClassTest的全部public构造器如下：");
		for (Constructor c : publicCtors)
		{
			System.out.println(c);
		}
		//获取该Class对象所对应类的全部public方法
		Method[] mtds = clazz.getMethods();
		System.out.println("ClassTest的全部public方法如下：");
		for (Method md : mtds)
		{
			System.out.println(md);
		}
		//获取该Class对象所对应类的指定方法
		System.out.println("ClassTest里带一个字符串参数的info方法为："
			+ clazz.getMethod("info" , String.class));
		//获取该Class对象所对应类的上的全部注释
		Annotation[] anns = clazz.getAnnotations();
		System.out.println("ClassTest的全部Annotattion如下：");
		for (Annotation an : anns)
		{
			System.out.println(an);
		}
		System.out.println("该Class元素上的@SuppressWarnings注释为："
			+ clazz.getAnnotation(SuppressWarnings.class));
		//获取该Class对象所对应类的全部内部类
		Class<?>[] inners = clazz.getDeclaredClasses();
		System.out.println("ClassTest的全部内部类如下：");
		for (Class c : inners)
		{
			System.out.println(c);
		}
		//使用Class.forName方法加载ClassTest的Inner内部类
		Class inClazz = Class.forName("ClassTest$Inner");
		//通过getDeclaringClass()访问该类所在的外部类
		System.out.println("inClazz对应类的外部类为：" + 
			inClazz.getDeclaringClass());
		System.out.println("ClassTest的包为：" + clazz.getPackage());
		System.out.println("ClassTest的父类为：" + clazz.getSuperclass());
		
		//创建对象
		ClassTest test = clazz.newInstance();
		//调用方法
		mtds[0].invoke(test, null);
		Field nameField = clazz.getDeclaredField("name");
		nameField.setAccessible(true);//取消访问权限检查
		nameField.set(test, "sjw");
		System.out.println(test.getName());
		
		//反射操作数组
		//创建一个元素类型为String ，长度为10的数组
		Object arr = Array.newInstance(String.class, 10);
		//依次为arr数组中index为5的元素赋值
		Array.set(arr, 5, "Struts2权威指南");
		//依次取出arr数组中index为5的元素的值
		Object book1 = Array.get(arr , 5);
		//输出arr数组中index为5的元素
		System.out.println(book1);
	}

}

class ClassTest
{
	private String name;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	//为该类定义一个私有的构造器
	private ClassTest()
	{
	}
	//定义一个有参数的构造器
	public ClassTest(String name)
	{
		System.out.println("执行有参数的构造器");
	}
	//定义一个无参数的info方法
	public void info()
	{
		System.out.println("执行无参数的info方法");
	}
	//定义一个有参数的info方法
	public void info(String str)
	{
		System.out.println("执行有参数的info方法"
			+ "，其实str参数值：" + str);
	}
	//定义一个测试用的内部类
	class Inner
	{
	}
	public static void main(String[] args) 
		throws Exception
	{
		
	}
}
