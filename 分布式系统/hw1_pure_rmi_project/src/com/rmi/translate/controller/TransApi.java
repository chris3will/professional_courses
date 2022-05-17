package com.rmi.translate.controller;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.time.LocalDateTime;
import java.util.Map;

public interface TransApi extends Remote {
    // 该方法包含从本地数据库中查询与调用baidu开发者接口
    String getTransResult(String query, String from, String to) throws RemoteException;

}
