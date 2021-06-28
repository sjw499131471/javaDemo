package com.sjw.basic;

import java.io.ObjectStreamException;
import java.io.Serializable;
import java.lang.Thread.UncaughtExceptionHandler;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import javax.swing.plaf.SliderUI;

/**
 * 在java中，每次程序运行至少启动2个线程。一个是main线程，一个是垃圾收集线程。因为每当使用java命令执行一个类的时候，实际上都会启动一个ＪＶＭ，每一个ｊＶＭ实际在就是在操作系统中启动了一个进程。
 * 守护线程：未其他线程提供便利服务。典型代表是垃圾回收站。JVM中最后一个线程销毁，守护线程才销毁。
 * 可重入锁：自己可以再次获取自己的内部锁。是一种递归无阻塞的同步机制。如果锁不可重入，递归时将出现死锁。
 * 注意String的常量池特性（String a="sameStr";String b = "sameStr";锁a和锁b锁的是锁的同一个对象）
 * SimpleDataFormat非线程安全，不能设置为全局变量
 *
 */

/**
 * 查看死锁：cmd下运行jps——》jstack -l 线程id
 * 
 *
 */
public class ThreadTest {
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public static void main(String[] args) {
		//1.java多线程基础知识
		TestThreadBasic();
		
		//2.变量及对象的并发访问(同步)
		TestConcurrentAccess();
		
		//3.线程间通信
		TestThraedCommunication();
		
		//4.Lock的使用
		TestUsageOfLock();
		
		//5.定时器Timer
		TestTimer();
		
		//6.单例模式和多线程
		TestSingleton();
		
		//7.线程组
		TestThreadGroup();
		//线程异常
		TestThreadException();
		
		//Callable有返回值且可以抛出异常
		CallableTest();
		
		//线程池
		ThreadPoolTest();
		
		//线程安全的集合：ConcurrentHashMap和ConcurrentLinkedDeque
//		ConcurrentHashMap<String, String> concurrentHashMap = new ConcurrentHashMap<String, String>();
	}

	private static void ThreadPoolTest() {
		//创建一个具有固定线程数（6）的线程池
		ExecutorService pool = Executors.newFixedThreadPool(6);
		//向线程池中提交2个线程
		pool.submit(new MyRunnable());
		pool.submit(new MyRunnable());
		//关闭线程池
		pool.shutdown();		
	}

	private static void CallableTest() {
		//创建Callable对象
		RtnThread rt = new RtnThread();
		//使用FutureTask来包装Callable对象
		FutureTask<Integer> task = new FutureTask<Integer>(rt);
		for (int i = 0 ; i < 100 ; i++)
		{
			System.out.println(Thread.currentThread().getName()
				+ " 的循环变量i的值：" + i);
			if (i == 20)
			{
				//实质还是以Callable对象来创建、并启动线程
				new Thread(task , "有返回值的线程").start();
			}
		}
		try
		{
			//获取线程返回值
			System.out.println("子线程的返回值：" + task.get());//get方法会阻塞主线程，直到call方法执行结束			
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}		
	}

	private static void TestThreadException() {
		Thread t =new Thread(new Runnable() {
			@Override
			public void run() {
				String a = null;
				System.out.println(a.toString() + "");
			}
		});
		t.setUncaughtExceptionHandler(new UncaughtExceptionHandler() {//设置本线程的异常处理器，当前对象已经设置了异常处理器，则不会执行线程组的异常处理器
			@Override
			public void uncaughtException(Thread t, Throwable e) {
				System.out.println(t.getName() + "出现异常");
			}
		});
		t.start();
		
//		MyThread.setDefaultUncaughtExceptionHandler(new UncaughtExceptionHandler() {//对象的异常处理器会覆盖默认异常处理器
//			@Override
//			public void uncaughtException(Thread t, Throwable e) {
//				System.out.println("MyThread线程实例的默认处理器");
//			}
//		});
		
		//实现当线程内任一线程出现异常，其他线程也终止
		ThreadGroup myGroup = new MyGroup("自定义线程组1");//线程组和线程类的默认处理器可以同时捕获线程异常，前提是线程组的uncaughtException实现中调用super.uncaughtException(t, e);
		
	}

	private static void TestThreadGroup() {
		//线程组用于组织管理零散线程
		ThreadGroup group1 = new ThreadGroup("线程组1");
		System.out.println(group1.getParent().getName());//自动归组特性，新建组时如果没有指定父线程组，则默认加入当前线程的线程组
		Thread t1 = new Thread(group1,"线程1");//新建线程并加入线程组
		
		ThreadGroup group2 = new ThreadGroup(group1, "子线程组1");//多级线程组
//		group2.interrupt();//组内线程批量中断
		
		ThreadGroup[] groupArr = new ThreadGroup[group2.activeGroupCount()];
		group2.enumerate(groupArr);//拷贝组内的线程组到集合
		Thread[] threadArr = new Thread[group2.activeCount()];
		group2.enumerate(threadArr);//拷贝组内的线程集合
	}

	private static void TestSingleton() {
		// 立即加载/饿汉模式
		
	}

	private static void TestTimer() {
		Timer timer = new Timer();
		TimerTask task = new TimerTask() {
			@Override
			public void run() {
				System.out.println("任务执行了：" + sdf.format(new Date()));
			}
		};
		timer.schedule(task, 2000);//延迟2s执行任务
		timer.schedule(task, 2000, 1000);//2s后每秒执行一次
		try {
//			timer.schedule(task, sdf.parse("2016-12-08 21:37:50"));
//			timer.schedule(task, sdf.parse("2016-12-08 21:39:30"), 1000);//如果每次任务执行时间大于设定间隔时间，则实际间隔时间就是每个任务执行时间
//			timer.cancel();//取消timer的所有任务
//			task.cancel();//取消本任务
			/**
			 * 方法schedule 和方法scheduleAtFixedRate都会按顺序执行，所以不要考虑非线程安全的情况。
				方法schedule 和scheduleAtFixedRate 主要的区别只在于不延时的情况。
				使用scbedule 方法: 如果执行任务的时间没有被延时，那么下一次任务的执行时间参考的是上一次任务的"开始"时的时间来计算。
				使用scheduleAtFixedRate 方法:如果执行任务的时间没有被延时，那么下一次任务的执行时间参考的是上一次任务的"结束"时的时间来计算。
				延时的情况则没有区别，也就是使用schedule 或scheduleAtFixedRate 方法都是如果执行任务的时间被延时，那么下一次任务的执行时间参考的是上一次任务"结束"时的时间来计算。
			 */
			timer.scheduleAtFixedRate(task, sdf.parse("2016-12-08 21:39:30"), 1000);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	private static void TestUsageOfLock() {
		//ReentrantLock
		/**
		 * 持的是对象锁
		 */
		TestReentrantLock();
		
		//Condition
		/**
		 * 结合ReentrantLock实现等待/通知模式
		 * 可选择性通知
		 */
		TestCondition();
//		TestCondition2();//多个Condition实现选择性通知
		
		//公平与非公平锁
		/**
		 * 公平锁表示线程获取锁的顺序是按照线程加锁的顺序来分配的，即先来先得的FIFO 先进先出顺序。
		 * 而非公平锁就是一种获取锁的抢占机制，是随机获得锁的，和公平锁不一样的就是先来的不一定先得到锁，这个方式可能造成某些线程一直拿不到锁。
		 */
		TestFairAndUnfairLock();
		
		//ReentrantLock的方法
		TestMethodOfReentrantLock();
		
		//ReentrantReadWriteLock
		/**
		 * 读写锁表示也有两个锁，一个是读操作相关的锁， 也称为共享锁; 另一个是写操作相关的锁，也叫排他锁。
		 * 也就是多个读锁之间不互斥， 读锁与写锁互斥，写锁与写锁五斥。同一时刻只允许一个Thread 进行写入操作。
		 */
		TestReentrantReadWriteLock();
	}

	private static void TestReentrantReadWriteLock() {
		final ObjForTestReentrantReadWriteLock obj = new ObjForTestReentrantReadWriteLock();
		Thread t1= new Thread(new Runnable() {
			@Override
			public void run() {
//				obj.testReadLock();
				obj.testWriteLock();
			}
		});
		Thread t2= new Thread(new Runnable() {
			@Override
			public void run() {
//				obj.testWriteLock();
				obj.testReadLock();
			}
		});
		t1.start();
		t2.start();
	}

	private static void TestMethodOfReentrantLock() {
		//TODO
		
	}

	private static void TestFairAndUnfairLock() {
		final ObjForTestReentrantLock obj = new ObjForTestReentrantLock(true);//构造参数代表是否是公平锁
		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				System.out.println(Thread.currentThread().getName() + "加了锁");
				obj.methodForFairTest();
			}
		};
		Thread[] threads = new Thread[10];
		for (int i = 0; i < 10; i++) {
			threads[i] =new Thread(runnable);
		}
		for (int i = 0; i < 10; i++) {
			threads[i].start();
		}
		//运行结果正确：先加锁的先获得了锁。
	}

	private static void TestCondition2() {
		final ObjForTestReentrantLock obj = new ObjForTestReentrantLock();
		Thread t1= new Thread(new Runnable() {
			@Override
			public void run() {
				obj.awaitTest();
			}
		});
		Thread t2= new Thread(new Runnable() {
			@Override
			public void run() {
				obj.awaitTest2();
			}
		});
		t1.start();
		t2.start();
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		obj.signalTest();
		/**运行结果
		 * begin awaitTest
			begin awaitTest2
			begin signalTest
			end signalTest//只通知的第一个Condition
			end awaitTest
		 */
		
	}

	private static void TestCondition() {
		final ObjForTestReentrantLock obj = new ObjForTestReentrantLock();
		Thread t1= new Thread(new Runnable() {
			@Override
			public void run() {
				obj.awaitTest();
			}
		});
		Thread t2= new Thread(new Runnable() {
			@Override
			public void run() {
				obj.signalTest();
			}
		});
		t1.start();
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		t2.start();
		/**运行结果
		 * begin awaitTest
			begin signalTest
			end signalTest
			end awaitTest
		 */
		
	}

	private static void TestReentrantLock() {
		final ObjForTestReentrantLock obj = new ObjForTestReentrantLock();
		Thread t1= new Thread(new Runnable() {
			@Override
			public void run() {
				obj.method();
			}
		});
		Thread t2= new Thread(new Runnable() {
			@Override
			public void run() {
				obj.method();
			}
		});
		t1.start();
		t2.start();
		/**运行结果
		 * begin method
			end method
			begin method
			end method
		 */
	}

	private static void TestThraedCommunication() {
		/*等待/通知机制*/
		/**
		 * 通过Object类的wait()和notify()方法实现等待/通知机制。
		 * wait()和notify()方法调用前必须获取当前对象的对象锁。
		 * wait()调用后让出当前持有的对象锁；notify()调用后随机通知一个wait()状态的线程，让其重新获得对象锁，调用notify()后不是立即释放锁，而是要等同步代码执行完。
		 * 
		 */
		TestWaitNotify();
		
		/*生产者消费者模式:等待/通知机制的应用*/
		TestProducerCustomer();
		
		//管道流可以实现线程之间的通信
		
		/*join方法使用*/
		/**
		 * join方法使当前线程等待调用join方法的线程结束再继续运行
		 * join 与synchronized的区别是: join 在内部使用wait() 方法进行等待，而sychronized 关键字使用的是"对象监视器"原理做为同步。
		 */
		TestJoin();
		
		/*ThreadLocal*/
		/**
		 * 类ThreadLocal 主要解决的就是每个线程绑定自己的值， 可以将ThreadLocal 类比喻成全局存放数据的盒子， 盒子中可以存储每个线程的私有数据。
		 * 使用类lnheritableThreadLocal 可以在子线程中取得父线程的值。
		 */
		//验证变量的隔离性
		TestThreadLocal();
		TestlnheritableThreadLocal();
	}

	private static void TestlnheritableThreadLocal() {
		Tools.inheritableThreadLocalExt.set("主线程的值");
		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println(Tools.inheritableThreadLocalExt.get());
			}
		});
		t1.start();
	}

	private static void TestThreadLocal() {
		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				Tools.threadLocalExt.get();//第一次get也不会返回null
				for (int i = 0; i < 10; i++) {
					Tools.threadLocal.set("" + i);
					try {
						Thread.sleep(200);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					System.out.println("t1的值：" + Tools.threadLocal.get());
				}
			}
		});
		Thread t2 = new Thread(new Runnable() {
			@Override
			public void run() {
				for (int i = 0; i < 10; i++) {
					Tools.threadLocal.set("" + i);
					try {
						Thread.sleep(200);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					System.out.println("t2的值：" + Tools.threadLocal.get());
				}
			}
		});
		t1.start();
		t2.start();
		
	}

	private static void TestJoin() {
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(2000);
					System.out.println("t线程结束");
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
		t.start();
		try {
			t.join();
//			t.join(1000);//设置等待时间，通过wait(long)实现的
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("主线程线程结束");
	}

	private static void TestProducerCustomer() {
		String lock = new String();
		final Producer producer = new Producer(lock);
		final Customer customer = new Customer(lock);
		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					producer.setValue();
				}
			}
		});
		Thread t2 = new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					customer.getValue();
				}
			}
		});
		t1.start();
		t2.start();
	}

	private static void TestWaitNotify() {
		final ObjForTestThraedCommunication obj = new ObjForTestThraedCommunication();
		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				obj.testWait();
			}
		});
		Thread t2 = new Thread(new Runnable() {
			@Override
			public void run() {
				obj.testNotify();
			}
		});
		t1.start();
		try {
			Thread.sleep(50);//确保t1先启动
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		t2.start();
		/**运行结果
		 * start testWait
			start testWait lock
			start testNotify
			start testNotify lock
			end testNotify lock//执行完testNotify lock的代码块再释放锁继续执行testWait
			end testNotify
			end testWait lock
			end testWait
		 */
	}

	private static void TestConcurrentAccess() {
		/*synchronized关键字的使用*/
		/**
		 * synchronized：同步不可以继承
		 * 出现异常，锁自动释放
		 *  synchronized 同步方法持的是对象锁
		 * synchronized 同步方法和synchronized(this)代码块会阻塞其他synchronized 同步方法和其他synchronized(this)代码块
		 * synchronized代码块传入的参数是互斥锁，传入this则锁本对象，传入一个变量则锁变量，持有同一个锁的代码块将互斥
		 * synchronized用在static方法上锁的是对应的Class类
		 * synchronized可以同步工作内存和公共内存的变量值（在JVM被设置为-server 模式时为了线程运行的效率， 线程一直在私有堆栈中取变量的值。）
		 */
		//测试锁的可重入性
		TestReInLock();
		//synchronized同步代码段
		//测试synchronized(this){}
//		testSynchronizedThis();
		//测试synchronized(anyObj){}
//		testSynchronizedanyObj();
		
		/*volatile关键字*/
		/**
		 * volatile的作用是使变量在多个线程间可见，线程每次改变volatile的值都会反馈给主线程，每次取都会从主线程取。但它并没有实现原子操作，所以不是线程安全的。
		 * 在JVM被设置为-server 模式时为了线程运行的效率， 线程一直在私有堆栈中取变量的值。使用volatile可以强制从公共堆楼中取得变量的值， 而不是从线程私有数据棋中取得变量的值。
		 */
		
	}

	private static void TestThreadBasic() {
		//测试通过继承Thread的方式实现多线程
//		Thread t1 = new MyThread("线程1");
//		Thread t2 = new MyThread("线程2");
//		Thread t3 = new MyThread("线程3");
//		t1.start();//不能调用run；多线程的实现需要本地操作系统的支持；
//		t2.start();
//		t3.start();
		
		//测试通过实现Runnable的方式实现多线程
//		Thread t1 = new Thread(new MyRunnable("线程1"));
//		Thread t2 = new Thread(new MyRunnable("线程2"));
//		Thread t3 = new Thread(new MyRunnable("线程3"));
//		t1.start();//线程的调度具有随机性，start的顺序不代表线程执行的顺序。
//		t2.start();
//		t3.start();
		
		//Thread类的方法介绍
//		t1.setPriority(10);//设置线程优先级，优先级高的有更高的几率（并非100%）先执行。
//		t1.suspend();//暂停。极易造成公共的同步对象的独占，导致其他线程无法访问该对象
//		t1.resume();//继续
//		Thread.yield();//让出cpu，给其他线程
//		t1.stop();//不建议使用，强制让线程停止则可能使一些清理性的工作得不到完成。另外一个情况就是对锁定的对象进行了"解锁"，导致数据得不到同步的处理，出现数据不一致的问题。
//		t1.interrupt();//中断线程
//		System.out.println("当前线程是否已经中断：" + Thread.interrupted());//判断当前线程是否已经中断，此处当前线程是main线程；interrupted会清除中断状态，即第一次返回true，第二次调用返回false。
//		System.out.println("t1是否已经中断：" + t1.isInterrupted());//isInterrupted不会清除中断状态
//		System.out.println("t1 name:"  + t1.getName());
//		System.out.println("t1.isAlive:" + t1.isAlive());//当前线程是否是活动状态：已启动且未终止
//		System.out.println("t1 id:" + t1.getId());
		
	}

	private static void testSynchronizedanyObj() {
		final ObjForThreadTest obj = new ObjForThreadTest();
		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				obj.methodForSyncAnyObjTest();
			}
		});
		Thread t2 = new Thread(new Runnable() {
			@Override
			public void run() {
				obj.methodForSyncAnyObjTest2();
			}
		});
		Thread t3 = new Thread(new Runnable() {
			@Override
			public void run() {
				obj.methodForSyncThisTest();
			}
		});
		t1.start();
		t2.start();
		t3.start();
		/**运行结果：
		 * begin methodForSyncAnyObjTest
			begin methodForSyncThisTest//methodForSyncAnyObjTest未结束就执行methodForSyncThisTest，证明synchronized (this)和synchronized(anyObj)不互斥
			end methodForSyncAnyObjTest
			end methodForSyncThisTest
			begin methodForSyncAnyObjTest2//synchronized(anyObj)和synchronized(anyObj)互斥，两个anyObj为同一个对象。
			end methodForSyncAnyObjTest2
		 */		
	}

	private static void testSynchronizedThis() {
		final ObjForThreadTest obj = new ObjForThreadTest();
		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				obj.methodForSyncThisTest();
			}
		});
		Thread t2 = new Thread(new Runnable() {
			@Override
			public void run() {
				obj.methodForSyncThisTest2();
			}
		});
		t1.start();
		t2.start();
		/**运行结果：
		 * begin methodForSyncThisTest
			end methodForSyncThisTest
			begin methodForSyncThisTest2
			end methodForSyncThisTest2
		 */
	}

	private static void TestReInLock() {
		final ObjForThreadTest obj = new ObjForThreadTest();
		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				obj.methodB();
			}
		});
		Thread t2 = new Thread(new Runnable() {
			@Override
			public void run() {
				obj.methodA();
			}
		});
		t1.start();
		t2.start();
		/**运行结果：
		 * methodB begin
			methodB end//methodB的执行阻塞了methodA，synchronized方法会阻塞其他synchronized方法
			methodA begin
			methodB begin
			methodB end
			methodA end
			methodA begin//依然是同步。
			methodB begin
			methodB end
			methodA end
		 */
	}

}

//多线程实现方式1：继承Thread
class MyThread extends Thread{
	String str;
	public MyThread() {}
	public MyThread(String str) {
		this.str = str;
	}
	@Override
	public void run() {
		System.out.println(str + "run start");
		try {
			sleep(1000);
		} catch (InterruptedException e) {
			System.out.println(e.getMessage());
		}
		System.out.println(str + "run stop");
	}
}

//多线程实现方式2：实现Runnable
class MyRunnable implements Runnable{
	String str;
	public MyRunnable() {}
	public MyRunnable(String str) {
		this.str = str;
	}
	@Override
	public void run() {
		Thread curThread = Thread.currentThread();//获取当前执行本段代码的线程
		System.out.println(str + "run start");
		try {
			Thread.sleep(1000);//让curThread休眠1s
			if (curThread.isInterrupted()) {
				System.out.println("线程中断退出！");
				throw new InterruptedException();//异常法停止线程
				//return;//也可以实现停止线程，但不能将线程停止的讯息向上传播。
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(str + "run stop");
	}
	
}

class ObjForThreadTest{
	private int num;
	private Object anyObj = new Object();
	synchronized public void methodA(){//测试可重入锁
		System.out.println("methodA begin");
		methodB();//methodB也持有本对象的锁，如果锁不可重入，将一直等待methodA的对象锁，出现死锁。
		System.out.println("methodA end");
	}
	synchronized public void methodB(){
		System.out.println("methodB begin");
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("methodB end");
	}
	public void methodForSyncThisTest() {
		synchronized (this) {
			System.out.println("begin methodForSyncThisTest");
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("end methodForSyncThisTest");
		}
	}
	public void methodForSyncThisTest2() {
		synchronized (this) {//synchronized (this)锁的是本对象，要等methodForSyncThisTest释放锁，才能执行methodForSyncThisTest2
			System.out.println("begin methodForSyncThisTest2");
			System.out.println("end methodForSyncThisTest2");
		}
	}
	
	public void methodForSyncAnyObjTest() {//锁任意对象
		synchronized (anyObj) {
			System.out.println("begin methodForSyncAnyObjTest");
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("end methodForSyncAnyObjTest");
		}
	}
	public void methodForSyncAnyObjTest2() {//锁任意对象
		synchronized (anyObj) {
			System.out.println("begin methodForSyncAnyObjTest2");
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("end methodForSyncAnyObjTest2");
		}
	}
	
}

class ObjForTestThraedCommunication{
	private String lock = new String();
	public void testWait(){
		System.out.println("start testWait");
		synchronized(lock){
			System.out.println("start testWait lock");
			try {
				Thread.sleep(2000);//执行两秒后释放锁
				lock.wait();
				//lock.wait(3000);3秒后自动唤醒，无需其他对象notify
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("end testWait lock");
		}
		System.out.println("end testWait");
	}
	
	public void testNotify(){
		System.out.println("start testNotify");
		synchronized(lock){
			System.out.println("start testNotify lock");
			lock.notify();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("end testNotify lock");
		}
		System.out.println("end testNotify");
	}
}

class ValueObj{
	static String value = "";
}
class Producer{
	private String lock = null;
	public Producer(String lock){
		this.lock = lock;
	}

	public void setValue(){
		synchronized (lock) {
			if (!ValueObj.value.equals("")) {//已生产则不重复生产
				try {
					lock.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			System.out.println("生产value");
			ValueObj.value = "test";//生产
			lock.notify();//唤醒消费者
		}
	}
}

class Customer{
	private String lock = null;
	public Customer(String lock){
		this.lock = lock;
	}
	
	public void getValue(){
		synchronized (lock) {
			if (ValueObj.value.equals("")) {//无可消费的值
				try {
					lock.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			System.out.println("消费value");
			ValueObj.value = "";//消费
			lock.notify();//唤醒生产者
		}
	}
}

class Tools{
	public static ThreadLocal<String> threadLocal = new ThreadLocal<String>();
	public static ThreadLocalExt<String> threadLocalExt = new ThreadLocalExt<String>();
	public static InheritableThreadLocalExt inheritableThreadLocalExt = new InheritableThreadLocalExt();
}

class ThreadLocalExt<T> extends ThreadLocal<T>{//解决第一次get时返回null的问题
	@SuppressWarnings("unchecked")
	@Override
	protected T initialValue() {
		return (T)"初始值";
	}
}

class InheritableThreadLocalExt extends InheritableThreadLocal<String>{
	@Override
	protected String initialValue() {
		return "初始值";
	}
	@Override
	protected String childValue(String parentValue) {
		return parentValue + "；额外值";
	}
}

class ObjForTestReentrantLock{
	private ReentrantLock lock = new ReentrantLock();
	private Condition condition = lock.newCondition();
	private Condition condition2 = lock.newCondition();
	
	public ObjForTestReentrantLock(){}
	public ObjForTestReentrantLock(Boolean isFair){
		lock = new ReentrantLock(isFair);
	}
	public void method(){
		try {
			lock.lock();//锁的是当前ObjForTestReentrantLock实例
			System.out.println(Thread.currentThread().getName() +  "begin method");
			Thread.sleep(2000);
			System.out.println(Thread.currentThread().getName() +  "end method");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}finally {
			lock.unlock();
		}
	}
	
	public void awaitTest(){
		try {
			lock.lock();
			System.out.println("begin awaitTest");
			condition.await();
			System.out.println("end awaitTest");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}finally {
			lock.unlock();
		}
	}
	public void signalTest(){
		lock.lock();
		System.out.println("begin signalTest");
//		condition.signal();//相当于notify()
		condition.signalAll();//相当于notifyAll()
		System.out.println("end signalTest");
		lock.unlock();
	}
	public void awaitTest2(){
		try {
			lock.lock();
			System.out.println("begin awaitTest2");
			condition2.await();
			System.out.println("end awaitTest2");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}finally {
			lock.unlock();
		}
	}
	public void signalTest2(){
		lock.lock();
		System.out.println("begin signalTest2");
//		condition2.signal();//相当于notify()
		condition2.signalAll();//相当于notifyAll()
		System.out.println("end signalTest2");
		lock.unlock();
	}
	
	public void methodForFairTest(){
		lock.lock();//锁的是当前ObjForTestReentrantLock实例
		System.out.println(Thread.currentThread().getName() + "获得了锁。");
		lock.unlock();
	}
}

class ObjForTestReentrantReadWriteLock{
	private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
	public void testReadLock(){
		lock.readLock().lock();
		try {
			System.out.println(Thread.currentThread().getName() + " begin testReadLock at:" + System.currentTimeMillis());
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}finally {
			lock.readLock().unlock();
		}
	}
	
	public void testWriteLock(){
		lock.writeLock().lock();
		try {
			System.out.println(Thread.currentThread().getName() + " begin testWriteLock at:" + System.currentTimeMillis());
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}finally {
			lock.writeLock().unlock();
		}
	}
}

class ObjForTestSingleton{//饿汉模式
	private static ObjForTestSingleton obj = new ObjForTestSingleton();
	public ObjForTestSingleton getInstance(){
		return obj;
	}
}

class ObjForTestSingleton2{//懒汉模式
	private static ObjForTestSingleton obj = null;
	public ObjForTestSingleton getInstance(){
		if (obj != null) {
		}else {
			synchronized (ObjForTestSingleton2.class) {
				if(obj == null)//使用双检查机制实现单例模式，即线程安全又高效率
					obj = new ObjForTestSingleton();
			}
		}
		return obj;
	}
}

class ObjForTestSingleton3 implements Serializable{//内部类实现单例模式
	private static final long serialVersionUID = 222L;
	public static class ObjForTestSingleton3InnerClass{
		public static ObjForTestSingleton3 obj = new ObjForTestSingleton3();
	}
	public static ObjForTestSingleton3 getInstance(){
		return ObjForTestSingleton3InnerClass.obj;
	}
	protected Object readResole() throws ObjectStreamException{//解决序列化时内部类实现单例模式的问题
		return ObjForTestSingleton3InnerClass.obj;
	}
}

class ObjForTestSingleton4{//static关键字实现单例模式
	private static ObjForTestSingleton4 obj = null;
	static{
		obj = new ObjForTestSingleton4();
	}
	public static ObjForTestSingleton4 getInstance(){
		return obj;
	}
}

class MyGroup extends ThreadGroup{

	public MyGroup(String name) {
		super(name);
	}
	
	@Override
	public void uncaughtException(Thread t, Throwable e) {
		super.uncaughtException(t, e);
		this.interrupt();
	}
}


class RtnThread implements Callable<Integer>
{
	//实现call方法，作为线程执行体
	public Integer call()
	{
		int i = 0;
		for ( ; i < 100 ; i++ )
		{
			System.out.println(Thread.currentThread().getName()
				+ " 的循环变量i的值：" + i);
		}
		//call()方法可以有返回值
		return i;
	}
} 

