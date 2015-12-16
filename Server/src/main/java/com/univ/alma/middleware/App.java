package com.univ.alma.middleware;

/**
 * Hello world!
 *
 */

import java.rmi.*;
import java.rmi.server.*;

public class App 
{
    public static void main( String[] args ) {

        try {
            //System.setSecurityManager(new RMISecurityManager());
            java.rmi.registry.LocateRegistry.createRegistry(1099);

            ServerInterface b = new ChatServer();
            Naming.rebind("rmi://127.0.0.1/youchat", b);
            System.out.println("[System] Chat Server is ready.");
        } catch (Exception e) {
            System.out.println("Chat Server failed : " + e);
        }
    }
}
