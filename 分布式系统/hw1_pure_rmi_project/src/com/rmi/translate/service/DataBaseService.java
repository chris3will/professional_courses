package com.rmi.translate.service;

import com.rmi.translate.controller.DataBase;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;

public class DataBaseService implements DataBase {
    private Map<String, String> ChineseToEnglish = new HashMap<String, String>();
    private Map<String, String> EnglishToChinese = new HashMap<String, String>();

    public DataBaseService(){
        // 先写一个初始化的构造函数
        // 函数构造完毕时，需要同时对字典进行初始实例化。
        this.ChineseToEnglish.put("狗","Dog");
        this.ChineseToEnglish.put("猫","Cat");
        this.EnglishToChinese.put("Dog","狗");
        this.EnglishToChinese.put("Cat", "猫");
    }

    @Override
    public String getTransFromDatabase(String query, String from, String to) throws RemoteException {
        /**
         *
         * @param query 需要被翻译的单词
         * @param from 输入字符串的语种
         * @param to 输出字符串的语种
         * @return 英汉互译后的内容，如果词典中不包含此单词返回null
         */
        if(from.equals("auto") || to.equals("en") || from.equals("ch")){
            return this.ChToEn(query);
        }else{
            return this.EnToCh(query);
        }
    }

    private String ChToEn(String from){
        if(!this.ChineseToEnglish.containsKey(from)) {
            this.ChineseToEnglish.put(from, "当前数据库并未存储对应翻译内容..");
        }
        return this.ChineseToEnglish.get(from);
    }
    private String EnToCh(String from){
        if(!this.EnglishToChinese.containsKey(from)) {
            this.EnglishToChinese.put(from, "当前数据库并未存储对应翻译内容..");
        }
        return this.EnglishToChinese.get(from);
    }
}
