package com.sjw.basic;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//泛型
/**
 * 
 * @author sjw
 *
 */
public class GenericTest {

	public static void main(String[] args) {
		//测试通配符：大多数情况，泛型方法都可以替代通配符
		testWildcards(Arrays.asList("Buenos Aires", "Córdoba", "La Plata"));
		
		testGenericMethod(Arrays.asList("Buenos Aires", "Córdoba", "La Plata"),Arrays.asList("Buenos Aires", "Córdoba", "La Plata"));
		
		//擦除
		Apple<Integer> a = new Apple<Integer>(6);
		//a的getSize方法返回Integer对象
		Integer as = a.getSize();
		//把a对象赋给Apple变量，会丢失尖括号里的类型信息
		Apple b = a;
		//b只知道size的类型是Number
		Number size1 = b.getSize();
		//下面代码引起编译错误
		//Integer size2 = b.getSize();
		
		//Apple<Number> a2 = new Apple<Integer>();//Integer extends Number,但是编译报错
		Number n1 = new Integer(1);//编译通过
		
		//转换
		List<Integer> li = new ArrayList<Integer>();
		li.add(6);
		li.add(9);
		List list = li;
		//下面代码引起“未经检查的转换”的警告，编译、运行时完全正常
		List<String> ls = list;
		//但只要访问ls里的元素，如下面代码将引起运行时异常。
		System.out.println(ls.get(0));
	}

	private static<T,S extends T> void testGenericMethod(List<T> list1,List<S> list2) {
		
	}

	//private static void testWildcards(List<?> list) {
	private static void testWildcards(List<? extends String> list) {
		for (Object object : list) {
			System.out.println(object);
		}
		//当使用通配符时，无法向集合添加元素
		//list.add(new Object());//编译错误
	}

}

class Apple<T extends Number>
{
	T size;

	public Apple()
	{
	    //泛型+反射
	    Class<T> tClass = (Class<T>)((ParameterizedType)getClass().
	        getGenericSuperclass()).getActualTypeArguments()[0];
	    try {
            size = (T)Class.forName(tClass.getName()).newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
	}

	public Apple(T size)
	{
		this.size = size;
	}

	public void setA(T size)
	{
		this.size = size;
	}
	public T getSize()
	{
		 return this.size;
	}
}
