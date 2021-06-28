package com.sjw.basic;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.PriorityQueue;
import java.util.Properties;
import java.util.TreeSet;
import java.util.WeakHashMap;

public class CollectionTest {

	public static void main(String[] args) throws FileNotFoundException, IOException {
		//List:有序可重复；ArrayList查询快、增删慢；LinkedList增删快、查询慢：多用于模拟队列和堆栈。
		//Set：无序，不重复；
		//Map:key值不可重复
		//Queue:除了基本的 Collection 操作外，队列还提供其他的插入、提取和检查操作。每个方法都存在两种形式：一种抛出异常（操作失败时），另一种返回一个特殊值（null 或 false，具体取决于操作）。插入操作的后一种形式是用于专门为有容量限制的 Queue 实现设计的；在大多数实现中，插入操作不会失败。
		//Deque:继承Queue接口；一个线性 collection，支持在两端插入和移除元素。名称 deque 是“double ended queue（双端队列）”的缩写，通常读为“deck”。
		
		testList();
		testSet();
		testMap();
		
//		testColllectionsUtil();
	}

	private static void testColllectionsUtil() {
		List<String> list = new ArrayList<String>();
		list.add("1");
		list.add("2");
		list.add("3");
		
		//1.排序操作
		System.out.println(list);//[1, 2, 3]
		Collections.reverse(list);
		System.out.println(list);//[3, 2, 1]
		Collections.shuffle(list);
		System.out.println(list);//[3, 1, 2]
		Collections.sort(list);
		System.out.println(list);//[1, 2, 3]
		Collections.sort(list,new Comparator<String>() {//定制排序
			@Override
			public int compare(String o1, String o2) {
				if (o1.length() > o2.length()) {
					return 1;
				}else if (o1.length() == o2.length()) {
					return 0;
				}else{
					return -1;
				}
			}
		});
		System.out.println(list);//[1, 2, 3]
		Collections.swap(list, 1, 2);
		System.out.println(list);//[1, 3, 2]
		Collections.rotate(list, 1);//将倒数n个元素旋转到列表前排
		System.out.println(list);//[2, 1, 3]
		
		//2.查找，删除操作
		Collections.sort(list);
		int index = Collections.binarySearch(list, "1");//二分法查找，list必须有序，list内元素必须实现Comparable接口
		System.out.println(index);//1
		int frequency = Collections.frequency(list, 1);
		System.out.println(frequency);
		
		//3.同步控制
		List syscList = Collections.synchronizedList(list);
		
		//4.不可变集合
		List unmodifiableList = Collections.unmodifiableList(list);
		List emptyUnmodifiableList = Collections.emptyList();
		List singletonUnmodifiableList = Collections.singletonList("1");//返回只包含指定对象（只有一个）的、不可变的集合对象
	}

	private static void testMap() throws FileNotFoundException, IOException {
		/*HashMap和Hashtable*/
		//Hashtable是一个线程安全的Map实现，不允许null作key，尽量少用，可通过Collections工具把HashMap变为线程安全的
		//HashMap非线程安全，允许null作key
		//HashMap和Hashtable判断key相等的两个标准:equals方法返回true，hashCode也相等
		//LinkedHashMap保持插入顺序
		LinkedHashMap scores = new LinkedHashMap();
		scores.put("语文" , 80);
		scores.put("数学" , 76);
		scores.put("英文" , 76);
		scores.put("英文" , 76);//该键值对不会被插入，与上面的相等
		//遍历scores里的所有的key-value对
		for (Object key : scores.keySet())
		{
			System.out.print(key + "------>");
			System.out.print(scores.get(key) + "\n");
		}
		//Properties是Hashtable的子类
		Properties props = new Properties();
		//向Properties中增加属性
		props.setProperty("username" , "yeeku");
		props.setProperty("password" , "123456");
		//将Properties中的属性保存到a.ini文件中
		props.store(new FileOutputStream("a.ini") , "comment line");
		//新建一个Properties对象
		Properties props2 = new Properties();
		//向Properties中增加属性
		props2.setProperty("gender" , "male");
		//将a.ini文件中的属性名-属性值追加到props2中
		props2.load(new FileInputStream("a.ini") );
		System.out.println(props2.get("password"));
		System.out.println(props2);
		
		/*SortedMap和TreeMap：Map内值根据大小自然排序或根据自定义实现比较排序*/
		//类似于SortedSet和TreeSet
		//TreeMap的key以TreeSet的形式存储
		
		/*WaekHashMap*/
		//WaekHashMap的key只保留对对象的弱引用，即没有其他对象引用这个key对应的对象时，key所引用的对象将被回收，HashMap也将自动删除这么key所对应的key-value
		WeakHashMap whm = new WeakHashMap();
		//将WeakHashMap中添加三个key-value对，
		//三个key都是匿名字符串对象（没有其他引用）
		whm.put(new String("语文") , new String("良好"));
		whm.put(new String("数学") , new String("及格"));
		whm.put(new String("英文") , new String("中等"));
		//将WeakHashMap中添加一个key-value对，
		//该key是一个系统缓存的字符串对象。
		whm.put("java" , new String("中等"));
		//输出whm对象，将看到4个key-value对。
		System.out.println(whm);
		//通知系统立即进行垃圾回收
		System.gc();
		System.runFinalization();
		//通常情况下，将只看到一个key-value对。
		System.out.println(whm);
		
		//IdentityHashMap:比较key是否相等时，通过key1=key2实现
		
		//EnumMap
		//创建一个EnumMap对象，该EnumMap的所有key必须是Season枚举类的枚举值
		EnumMap enumMap = new EnumMap(Season.class);
		enumMap.put(Season.SUMMER , "夏日炎炎");
		enumMap.put(Season.SPRING , "春暖花开");
		System.out.println(enumMap);
	}

	private static void testList() {
		List<String> list = new ArrayList<String>();
		list.add("1");
		list.add("2");
		ListIterator<String> iterator = list.listIterator();
		while (iterator.hasNext()) {
			System.out.println(iterator.next());
		}
		while (iterator.hasPrevious()) {//反向迭代
			System.out.println(iterator.previous());
		}
		
		//LinkedList可实现栈与队列:插入删除效率高，随机访问效率低。底层通过链表实现
		LinkedList< String> linkedList = new LinkedList<String>();
		linkedList.push("1");//push方法等同addFirst方法
		linkedList.push("2");//从列表头插入
		System.out.println(linkedList);//[2, 1]
		linkedList.pop();//等同 removeFirst()：从列表头删除
		System.out.println(linkedList);//[1]
		linkedList.offer("2");//等同 offerLast(E)：从列表尾加入
		System.out.println(linkedList);//[1, 2]
		linkedList.pollLast();//从列表尾删除
		System.out.println(linkedList);//[1]
		
		//PriorityQueue类似于TreeSet
		PriorityQueue pq = new PriorityQueue();
		//下面代码依次向pq中加入四个元素
		pq.offer(6);
		pq.offer(-3);
		pq.offer(9);
		pq.offer(0);
		//输出pq队列，并不是按元素的加入顺序排列，而是按元素的大小顺序排列
		System.out.println(pq);
		//访问队列第一个元素，其实就是队列中最小的元素：-3
		System.out.println(pq.peek());
	}

	private static void testSet() {
		//HashSet（通过hashCode比较是否相等）查询、删除和增加元素的效率都非常高
		HashSet<String> set = new HashSet<String>();
		set.add("1");
		set.add("1");
		set.add("2");
		set.add("3");
		set.add("5");
		set.add("4");
		System.out.println(set.toString());//[3, 2, 1, 5, 4]
		
		//顺序插入，性能略有降低，但全体遍历性能高
		LinkedHashSet<String> linkedHashSet = new LinkedHashSet<String>();
		linkedHashSet.add("1");
		linkedHashSet.add("2");
		linkedHashSet.remove("1");
		System.out.println(linkedHashSet);
		
		//TreeSet添加的元素必须实现Comparable接口，TreeSet支持两种排序方式：自然排序和定制排序，默认是自然排序
		//当需要保持Set内元素一直有序时使用
		TreeSet<ComparableClz> treeSet = new TreeSet<ComparableClz>();//自然排序方式
		treeSet.add(new ComparableClz("11"));
		treeSet.add(new ComparableClz("111"));
		treeSet.add(new ComparableClz("1"));
		System.out.println(treeSet);//[1, 11, 111]
		
		TreeSet<String> treeSet2 = new TreeSet<String>(new Comparator<String>() {//定制排序
			@Override
			public int compare(String o1, String o2) {
				if (o1.length() > o2.length()) {
					return 1;
				}else if (o1.length() == o2.length()) {
					return 0;
				}else{
					return -1;
				}
			}
		});
		treeSet2.add("11");
		treeSet2.add("111");
		treeSet2.add("1");
		System.out.println(treeSet2);//[1, 11, 111]
	}

}

class ComparableClz implements Comparable<ComparableClz>{
	private String name;
	public ComparableClz(String name){
		this.name = name;
	}
	public String getName() {
		return name;
	}
	@Override
	public int compareTo(ComparableClz o) {
		if (o.getName().length() > this.name.length()) {
			return -1;
		}else if(o.getName().length() == this.name.length()){
			return 0;
		}else{
			return 1;
		}
	}
	
	public String toString() {
		return this.name;
	}
	
}

enum Season
{
	SPRING,SUMMER,FALL,WINTER
}

