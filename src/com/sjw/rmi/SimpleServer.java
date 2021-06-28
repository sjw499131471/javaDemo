package com.sjw.rmi;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class SimpleServer {

	public static void main(String[] args) throws Exception{
		IHello hello = new Hello();
		LocateRegistry.createRegistry(1099);
		Naming.bind("rmi://localhost:1099/hello", hello);
		
//		Context namingContext=new InitialContext();
//	    namingContext.rebind( "rmi:HelloService1", hello );
		
//		namingContext.rebind( "rmi://localhost:8000/HelloService1", service1 );

	}

}

@SuppressWarnings("serial")
class Hello extends UnicastRemoteObject implements IHello {

	public Hello()throws RemoteException{}
	@Override
	public String sayHello() throws RemoteException {
		return "hello";
	}

}

interface IHello extends Remote {
	public String sayHello() throws RemoteException;
}
