package com.sjw.rmi;
import java.rmi.*;
import javax.naming.*;

public class SimpleClient{
  public static void showRemoteObjects(Context namingContext)throws Exception{
    NamingEnumeration<NameClassPair> e=namingContext.list("rmi:");
    while(e.hasMore()) 
     System.out.println(e.next().getName());
  }

  public static void main( String args[] ){
    String url="rmi://localhost/";
    try{
//      System.setProperty("java.security.policy",SimpleClient.class.getResource("client.policy").toString());
//      System.setSecurityManager(new RMISecurityManager());
      Context namingContext=new InitialContext();
      IHello service1=(IHello)namingContext.lookup(url+"hello");
      
      Class stubClass=service1.getClass();
      System.out.println("service1是"+stubClass.getName()+"的实例"); 
      Class[] interfaces=stubClass.getInterfaces();
      for(int i=0;i<interfaces.length;i++)  
        System.out.println("存根类实现了"+interfaces[i].getName()); 
 
      System.out.println(service1.sayHello()); 
      
      showRemoteObjects(namingContext);
    }catch( Exception e){
       e.printStackTrace();
    }
  }
}
