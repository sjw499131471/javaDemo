package com.sjw.basic;

/**
 * 内部类有什么好处？
 * 1.每个内部类都能独立的继承一个接口的实现，所以无论外部类是否已经继承了某个(接口的)实现，对于内部类都没有影响。内部类使得多继承的解决方案变得完整，
　　2.方便将存在一定逻辑关系的类组织在一起，又可以对外界隐藏。
　　3.方便编写事件驱动程序
　　4.方便编写线程代码
 * @author Administrator
 *
 */
public class OuterClass {
	private int a = 1;
	static String name = "test";
	
	public int getVal() {
		InnerClass inner = new InnerClass();//外部类中如果要访问成员内部类的成员，必须先创建一个成员内部类的对象
		return inner.getVal();
	}
	
	/**
	 * 四种内部类：成员内部类，局部内部类，静态内部类，匿名内部类
	 */
	
	//局部内部类
	public void test() {
		final int i=0;
		String str = "test";
		class PartInnerClass{//局部内部类就像是方法里面的一个局部变量一样，是不能有public、protected、private以及static修饰符的。
			private int a=0;
			
			public void print() {
				//System.out.println(str);//error  只能访问final修饰的局部变量
				System.out.println("i:" + i);
				System.out.println("a:" + a);
				System.out.println(name);
			}
		}
	}
	
	//匿名内部类
	public void printMsg() {
		String string = "test";
		new TestInterface() {//匿名内部类，实现了TestInterface，在ANDROID开发中很常用，常用于新建实现View.OnClickListener等接口的匿名内部类；匿名内部类一般用于继承其他类或是实现接口；匿名内部类是唯一一种没有构造器的类。
			@Override
			public void printMsg() {
				System.out.println("Message");
				System.out.println(name);
				//System.out.println(string);//error
			}
		}.printMsg();;
	}
	
	//成员内部类：就像是外部类的一个方法，可以有private，protected和private修饰符，可以访问外部类的所有成员属性和成员方法
	class InnerClass{
		private int a = 10;//当成员内部类拥有和外部类同名的成员变量或者方法时，会发生隐藏现象，即默认情况下访问的是成员内部类的成员。
		
		public int getVal() {
			System.out.println(a + OuterClass.this.a);//访问外部类的同名成员
			return a + OuterClass.this.a;
		}
	}
	
	//静态内部类
	static class StaticInnerClass{
		
	}
	
}


interface TestInterface{
	void printMsg();
}
