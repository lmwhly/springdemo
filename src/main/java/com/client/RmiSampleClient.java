package com.client;

import com.remote.service.interfaces.RmiSample;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

/**
 * Created by Luoo2 on 2016/3/29.
 */
public class RmiSampleClient {
    public static void main(String[] args) {



        try {

            String   url = "//localhost:8808/SAMPLE-SERVER";
            RmiSample RmiObject = (RmiSample) Naming.lookup(url);

            System.out.println("   1 + 2 = " + RmiObject.sum(1,2) );

        } catch (NotBoundException e) {
            System.out.println("NotBound:   " + e.toString());
            e.printStackTrace();
        } catch (MalformedURLException e) {
            System.out.println("Malformed   URL: " + e.toString());
            e.printStackTrace();
        } catch (RemoteException e) {
            System.out.println("Error   in lookup: " + e.toString());
            e.printStackTrace();
        }




    }
}
