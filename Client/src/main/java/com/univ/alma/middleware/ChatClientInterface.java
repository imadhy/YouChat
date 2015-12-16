package com.univ.alma.middleware;

import java.rmi.*;

/**
 * Created by imadhy on 16/12/15.
 */
public interface ChatClientInterface extends Remote {
    public void tell (String name)throws RemoteException;
    public String getName()throws RemoteException ;
}
