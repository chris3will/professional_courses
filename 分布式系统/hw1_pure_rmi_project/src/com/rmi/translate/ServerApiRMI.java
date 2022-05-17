package com.rmi.translate;

import com.rmi.translate.controller.DataBase;
import com.rmi.translate.controller.TransApi;
import com.rmi.translate.service.DataBaseService;
import com.rmi.translate.service.TransApiService;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class ServerApiRMI {
    private static final String APP_ID = "20220310001119593";
    private static final String SECURITY_KEY = "_QQmvfbCONw0rfEd2WnP";

    public  static  void main(String[] args) throws RemoteException {

        System.out.println("Create online translator remote service ...");
        // register a translator api，理解为实例化一个翻译服务
        TransApi transApi = new TransApiService(APP_ID,SECURITY_KEY);
        // 将此服务器转换为一个远程服务接口
        TransApi skeleton_api = (TransApi) UnicastRemoteObject.exportObject(transApi,0);
        // 将RMI服务注册到特定端口，这里默认为1200
        Registry registry_api = LocateRegistry.createRegistry(1200);
        // 将skeleton进行名称绑定为"Translator"，方便远端客户进行查询。注册此服务
        registry_api.rebind("Translator",skeleton_api);
        System.out.println("Create online translator remote service successfully！");

        System.out.println("Creating online database remote service");
        DataBase database = new DataBaseService();
        DataBase skeleton_database = (DataBase) UnicastRemoteObject.exportObject(database,0);
        Registry registry_database = LocateRegistry.createRegistry(1201); // 先尝试能否一个端口绑定多个服务
        registry_database.rebind("Database",skeleton_database);
        System.out.println("Create online database remote service successfully！");
    }
}
