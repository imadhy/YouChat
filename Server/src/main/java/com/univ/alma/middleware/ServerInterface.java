package com.univ.alma.middleware;


import java.rmi.*;
import java.util.*;
/**
 * Created by imadhy on 16/12/15.
 */
public interface ServerInterface extends Remote {

    public boolean login (ChatClientInterface a)throws RemoteException;
    public void publish (String s)throws RemoteException ;
    public Vector getConnected() throws RemoteException ;
}
