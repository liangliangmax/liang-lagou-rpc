package com.liang.rmi.service;

import com.liang.rmi.entity.User;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IUserService extends Remote {

    User getById(String id) throws RemoteException;
}
