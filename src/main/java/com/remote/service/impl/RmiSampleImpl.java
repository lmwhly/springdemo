package com.remote.service.impl;

import com.remote.service.interfaces.RmiSample;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Created by Luoo2 on 2016/3/29.
 */
public class RmiSampleImpl extends UnicastRemoteObject implements RmiSample {
    public RmiSampleImpl() throws RemoteException {
        super();
    }

    @Override
    public int sum(int a, int b) throws RemoteException {
        return a+b;
    }
}
