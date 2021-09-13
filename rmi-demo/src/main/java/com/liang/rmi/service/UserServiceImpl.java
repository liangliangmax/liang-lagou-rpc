package com.liang.rmi.service;

import com.liang.rmi.entity.User;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;

public class UserServiceImpl extends UnicastRemoteObject implements IUserService {

    Map<String,User> userMap = new HashMap<>();

    public UserServiceImpl() throws RemoteException {
        super();
        userMap.put("1",new User("1","zhangsan"));
        userMap.put("2",new User("2","lisi"));
    }

    @Override
    public User getById(String id) throws RemoteException{

        return userMap.get(id);
    }
}
