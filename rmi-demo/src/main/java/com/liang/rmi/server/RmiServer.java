package com.liang.rmi.server;

import com.liang.rmi.service.IUserService;
import com.liang.rmi.service.UserServiceImpl;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RmiServer {

    public static void main(String[] args) throws RemoteException {
        //1.注册registry实例。绑定端口
        Registry registry = LocateRegistry.createRegistry(9999);

        IUserService userService = new UserServiceImpl();

        registry.rebind("userService",userService);

        System.out.println("服务端启动成功");



        //
    }
}
