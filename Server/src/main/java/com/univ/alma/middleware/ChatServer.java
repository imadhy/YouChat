package com.univ.alma.middleware;

/**
 * Created by imadhy on 16/12/15.
 */

import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

public class ChatServer  extends UnicastRemoteObject implements ServerInterface{

    private Vector v=new Vector();

    public ChatServer() throws RemoteException {


    }

    public boolean login(ChatClientInterface a, String topic) throws RemoteException{

        System.out.println(a.getName() + "  s'est connecté....");
        a.tell("Tu t'es connecté avec succès.\n");
        publish(a.getName()+ " vient de ce connecté.");
        v.add(a);
        return true;
    }

    public void publish(String s) throws RemoteException{
        System.out.println(s);



        for(int i=0;i<v.size();i++){
            try{
                ChatClientInterface tmp=(ChatClientInterface)v.get(i);
                tmp.tell(s);
            }catch(Exception e){

            }
        }
    }

    public Vector getConnected() throws RemoteException{
        return v;
    }
}

