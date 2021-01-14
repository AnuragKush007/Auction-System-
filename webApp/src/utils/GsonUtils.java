package utils;

import com.google.gson.Gson;

public class GsonUtils {

    public static String toJson(Object obj){
        Gson gson = new Gson();
        return gson.toJson(obj);
    }
}
