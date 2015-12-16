package com.univ.alma.middleware;

/**
 * Created by imadhy on 16/12/15.
 */
import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;

public class ChatClient  extends UnicastRemoteObject implements ChatClientInterface {

    private String name;
    private ChatGUI ui;
    public ChatClient (String n) throws RemoteException {
        name=n;
    }

    public void tell(String st) throws RemoteException{
        System.out.println(st);
        ui.writeMsg(st);
    }
    public String getName() throws RemoteException{
        return name;
    }

    public void setGUI(ChatGUI t){
        ui=t ;
    }
}