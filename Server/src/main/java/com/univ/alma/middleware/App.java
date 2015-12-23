package com.univ.alma.middleware;

/**
 * Hello world!
 *
 */

import java.rmi.*;

public class App 
{
    public static void main( String[] args ) {

        try {
            java.rmi.registry.LocateRegistry.createRegistry(1099);

            ServerInterface b = new ChatServer();
            Naming.rebind("rmi://127.0.0.1/youchat", b);
            System.out.println("[System] Le serveur du Chat a démarrer.");
        } catch (Exception e) {
            System.out.println("Erreur lors du démarrage du serveur : " + e);
        }
    }
}
