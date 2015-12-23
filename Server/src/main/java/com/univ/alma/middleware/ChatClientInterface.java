package com.univ.alma.middleware;

/**
 * Created by imadhy on 16/12/15.
 */

import org.w3c.dom.NodeList;

import java.rmi.*;

public interface ChatClientInterface extends Remote {

    public void tell (String name)throws RemoteException ;
    public String getName()throws RemoteException ;

}