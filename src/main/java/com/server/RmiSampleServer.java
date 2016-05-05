package com.server;

import com.remote.service.impl.RmiSampleImpl;

import java.net.MalformedURLException;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

/**
 * Created by Luoo2 on 2016/3/29.
 */
public class RmiSampleServer {

    public static void main(String[] args) {


        try {
            LocateRegistry.createRegistry(8808);
            RmiSampleImpl server = new RmiSampleImpl();
            Naming.rebind("//localhost:8808/SAMPLE-SERVER", server);
            System.out.println   (" Զ�̶���ע��ɹ��� RMI �����Ѿ��������ȴ��ͻ��˵��� ....");


        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

    }
}
