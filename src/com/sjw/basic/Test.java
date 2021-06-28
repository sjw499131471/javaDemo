package com.sjw.basic;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Test {

	public static void main(String[] args) throws Exception{

		/**
		 * 内部类测试
		 */
//		OuterClass outer = new OuterClass();
//		OuterClass.InnerClass inner = outer.new InnerClass();//要创建成员内部类的对象，前提是必须存在一个外部类的对象。
//		
//		OuterClass.StaticInnerClass staticInnerClass = new OuterClass.StaticInnerClass();
		
		/**
		 * 比较运算符的测试
		 "=="操作符的作用:
		1、用于基本数据类型的比较
		2、判断引用是否指向堆内存的同一块地址。
		 */
//		compare_int_with_Integer();
//		compareString();
		
	    //switch语法
//		switchTest();
		
//		intToString();
		
		convert_date_string();
	}

	private static void convert_date_string () throws ParseException {
		String dateStr = "2012-11-12 12:10:14";
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = format.parse(dateStr);//字符串转日期
		String dateStr2 = format.format(date);//日期转字符串
		System.out.println(dateStr2);
	}

	private static void intToString () {
		String str = Integer.toString(4);
		str = Integer.toString(10, 8);//第二个参数为进制基数
		
		str = String.valueOf(4);
		
		str = String.format("%04d", 4);//0004
		System.out.println(str);
	}

	private static void switchTest() {
		switch ("abc") {//支持String/Enum/int
		case "abc":
			System.out.println("abc");
			break;
		case "cba":
			System.out.println("cba");
			break;
		default:
			break;
		}
	}

	private static void compareString() {
		/**
		 * String作为一个基本类型来使用:
		 * 如果String缓冲池内不存在与其指定值相同的String对象，那么此时虚拟机将为此创建新的String对象，并存放在String缓冲池内。
		 * 如果String缓冲池内存在与其指定值相同的String对象，那么此时虚拟机将不为此创建新的String对象，而直接返回已存在的String对象的引用。
		 */
		String s1 = "";
		String s2 = "";
		System.out.println("" == "");//true
		System.out.println(s1 == s2);//true
		System.out.println(s1 == "");//true
		String s3 = "test";
		String s4 = new String("test");//每次new都会在堆内存重新开辟一块内存存储字符串
		System.out.println(s3 == s4);//false
		System.out.println(s4 == "test");//false 
	}

	/**
	 * ①无论如何，Integer与new Integer不会相等。不会经历拆箱过程，i3的引用指向堆，而i4指向专门存放他的内存（常量池），他们的内存地址不一样，所以为false
  ②两个都是非new出来的Integer，如果数在-128到127之间，则是true,否则为false
  java在编译Integer i2 = 128的时候,被翻译成-> Integer i2 = Integer.valueOf(128);而valueOf()函数会对-128到127之间的数进行缓存
  ③两个都是new出来的,都为false
  ④int和integer(无论new否)比，都为true，因为会把Integer自动拆箱为int再去比
	 */
	private static final int i4 = 128;
	private static void compare_int_with_Integer() {
		int i = 128;
        Integer i2 = 128;
        Integer i3 = new Integer(128);
        
        //Integer会自动拆箱为int，所以为true
        System.out.println(i == i2);//true
        System.out.println(i == i3);//true
        System.out.println(i == i3);//true
        System.out.println(i3 == i4);//true
        System.out.println("**************");
        Integer i5 = 127;//java在编译的时候,被翻译成-> Integer i5 = Integer.valueOf(127);
        Integer i6 = 127;
        System.out.println(i5 == i6);//true
        /*Integer i5 = 128;
        Integer i6 = 128;
        System.out.println(i5 == i6);//false
*/        Integer ii5 = new Integer(127);
        System.out.println(i5 == ii5); //false
        Integer i7 = new Integer(128);
        Integer i8 = new Integer(123);
        System.out.println(i7 == i8);  //false
		
	}

}