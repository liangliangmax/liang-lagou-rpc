package com.liang.rmi.client;

import com.liang.rmi.entity.User;
import com.liang.rmi.service.IUserService;

import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class IRmiClient {

    public static void main(String[] args) throws RemoteException, NotBoundException {

        //获取register实例
        Registry registry = LocateRegistry.getRegistry("127.0.0.1", 9999);

        IUserService userService = (IUserService) registry.lookup("userService");

        User user = userService.getById("1");

        System.out.println(user);

    }


}
