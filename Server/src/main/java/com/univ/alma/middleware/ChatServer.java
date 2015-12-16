package com.univ.alma.middleware;

/**
 * Created by imadhy on 16/12/15.
 */
import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

public class ChatServer  extends UnicastRemoteObject implements ServerInterface{

    private Vector v=new Vector();
    public ChatServer() throws RemoteException{}

    public boolean login(ChatClientInterface a) throws RemoteException{
        System.out.println(a.getName() + "  got connected....");
        a.tell("You have Connected successfully.");
        publish(a.getName()+ " has just connected.");
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
                //problem with the client not connected.
                //Better to remove it
            }
        }
    }

    public Vector getConnected() throws RemoteException{
        return v;
    }
}

