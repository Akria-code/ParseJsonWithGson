package com.example.parsejsonwithgson;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GsonUtils {
    //不用创建对象,直接使用Gson.就可以调用方法
    private static Gson gson = null;

    //判断gson对象是否存在了,不存在则创建对象
    static {
        if (gson == null) {
            //gson = new Gson();
            //使用GsonBuilder方式设置Date对象序列化和反序列化 java.util.Date，java.sql.Timestamp，java.sql.Timestamp的格式
            gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        }
    }

    //无参的私有构造方法
    private GsonUtils() {
    }

    /**
     * 将对象转成json格式
     *
     * @param object
     * @return String
     */
    public static String GsonToString(Object object) {
        String gsonString = null;
        if (gson != null) {
            gsonString = gson.toJson(object);
        }
        return gsonString;
    }

    /**
     * 将json转成特定的cls的对象
     *
     * @param gsonString
     * @param cls
     * @return
     */
    public static <T> T GsonToBean(String gsonString, Class<T> cls) {
        T t = null;
        if (gson != null) {
            //传入json对象和对象类型,将json转成对象
            if (isJson(gsonString)) {
                try {
                    t = gson.fromJson(gsonString, cls);
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("",e.getLocalizedMessage());
                }
            }
        }
        return t;
    }

    /**
     * json字符串转成list
     *
     * @param gsonString
     * @param cls
     * @return
     */
    public static <T> List<T> GsonToList(String gsonString, Class<T> cls) {
//        使用Gson解析Json数据过程中，运行充抛出这样的问题异常的！！！
//        java.lang.ClassCastException: com.google.gson.internal.LinkedTreeMap cannot be cast to
//        在使用泛型过程中，必须要具体到某一个类，
//        这样写法解析完后，对象转换时抛出如上异常
//        List<T> list = null;
//        if (gson != null) {
//            //根据泛型返回解析指定的类型,TypeToken<List<T>>{}.getType()获取返回类型
//            list = gson.fromJson(gsonString, new TypeToken<List<T>>() {
//            }.getType());
//        }
//        return list;

        List<T> list = new ArrayList<T>();
        try {
            JsonArray arry = new JsonParser().parse(gsonString).getAsJsonArray();
            for (JsonElement jsonElement : arry) {
                list.add(gson.fromJson(jsonElement, cls));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;

    }

    /**
     * json字符串转成list中有map的
     *
     * @param gsonString
     * @return
     */
    public static <T> List<Map<String, T>> GsonToListMaps(String gsonString) {
        List<Map<String, T>> list = null;
        if (gson != null) {
            list = gson.fromJson(gsonString,
                    new TypeToken<List<Map<String, T>>>() {
                    }.getType());
        }
        return list;
    }

    /**
     * json字符串转成map的
     *
     * @param gsonString
     * @return
     */
    public static <T> Map<String, T> GsonToMaps(String gsonString) {
        Map<String, T> map = null;
        if (gson != null) {
            map = gson.fromJson(gsonString, new TypeToken<Map<String, T>>() {
            }.getType());
        }
        return map;
    }

    /**
     * 是否为Json数据
     *
     * @param message
     * @return
     */
    public static boolean isJson(String message) {
        try {
            new JSONObject(message);
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("GsonUtils", "┉┉无法Json格式化┉┉  " + message);
            return false;
        }
        return true;
    }
}
