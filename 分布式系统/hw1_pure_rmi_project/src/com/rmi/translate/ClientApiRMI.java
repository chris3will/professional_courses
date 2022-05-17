package com.rmi.translate;

import com.rmi.translate.controller.DataBase;
import com.rmi.translate.controller.TransApi;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ClientApiRMI {
    public static void main(String[] args) throws RemoteException, NotBoundException {
        // 注册到localhost服务器，并与对应端口链接，启动服务
        Registry registry = LocateRegistry.getRegistry("localhost",1200);
        // 查找名称为"Translator"的服务器并强制转换为TransApi接口
        TransApi transApi = (TransApi) registry.lookup("Translator");

        Registry registry2 = LocateRegistry.getRegistry("localhost",1201);
        // 查找名称为“Database”的服务器并强制转换为Database接口
        DataBase dataBase = (DataBase) registry2.lookup("Database");

        String query = "今天天气很好，请问咱们什么时候去露营？";
        Scanner scanner = new Scanner(System.in);
        while(true) {
            try {
                System.out.println("Please input a Chinese line for translation");
                String line = scanner.nextLine();
                // 正常调用接口方法
                // 首先调用数据库当中的词典来进行查询，否则调用开源的翻译接口服务。
//                transApi.
                if (!dataBase.getTransFromDatabase(line, "auto", "en").equals("当前数据库并未存储对应翻译内容.."))
                    System.out.println(dataBase.getTransFromDatabase(line, "auto", "en"));
                else {
                    System.out.println("Can't get the answer from the database, now call the translate API...");
                    String res = transApi.getTransResult(line, "auto", "en");
                    // 按指定模式在字符串查找，并返回结果
                    System.out.println(res);
                }
            } catch (IllegalStateException | NoSuchElementException e) {
                System.out.println("System.in was closed; exiting");
            }
            System.out.println("Do you want to continue translating Chinese words or sentences?(Y/N)");
            String line = scanner.nextLine();
            if(!(line.equals("Y") || line.equals("y") || line.equals("yes"))){
                System.out.println("Thanks for supporting our system, see you next time...");
                break;
            }
        }

    }
}
