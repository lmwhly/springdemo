package com.remote.service.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by Luoo2 on 2016/3/29.
 */
public interface RmiSample extends Remote{

    public int   sum(int a,int b) throws RemoteException;
}
