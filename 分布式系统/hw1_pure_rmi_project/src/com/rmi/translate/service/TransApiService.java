package com.rmi.translate.service;

import com.rmi.translate.controller.DataBase;
import com.rmi.translate.controller.TransApi;
import com.rmi.translate.common.MD5;

import java.util.HashMap;
import java.util.Map;


public class TransApiService implements TransApi {
    private static final String TRANS_API_HOST = "http://api.fanyi.baidu.com/api/trans/vip/translate";

    private String appid;
    private String securityKey;
    private DataBase database;

    public DataBase getDatabase() {
        return database;
    }

    public void setDatabase(DataBase database) {
        this.database = database;
    }

//    // 定义一个中文到英语的简单词库
//    private Map<String, String> ChineseToEnglish = new HashMap<String, String>();
//    private Map<String, String> EnglishToChinese = new HashMap<String, String>();

    public TransApiService(String appid, String securityKey) {
        this.appid = appid;
        this.securityKey = securityKey;
        this.database = new DataBaseService();
    }

    @Override
    public String getTransResult(String query, String from, String to) {
        Map<String, String> params = buildParams(query, from, to);
        return com.rmi.translate.common.HttpGet.get(TRANS_API_HOST, params);
    }

    private Map<String, String> buildParams(String query, String from, String to) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("q", query);
        params.put("from", from);
        params.put("to", to);

        params.put("appid", appid);

        // 随机数
        String salt = String.valueOf(System.currentTimeMillis());
        params.put("salt", salt);

        // 签名
        String src = appid + query + salt + securityKey; // 加密前的原文
        params.put("sign", MD5.md5(src));

        return params;
    }
}
