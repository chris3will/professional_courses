package com.rmi.translate.controller;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface DataBase extends Remote {
    String getTransFromDatabase(String query, String from, String to) throws RemoteException;
}
