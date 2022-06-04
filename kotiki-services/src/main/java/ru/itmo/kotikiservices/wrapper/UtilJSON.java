package ru.itmo.kotikiservices.wrapper;

import com.google.gson.Gson;

public class UtilJSON {
    public static String serializeToJson(Object object) {
        Gson gson = new Gson();
        String json = gson.toJson(object);
        return json;
    }

    public static <T> T deserializeFromJson(String json, Class<T> type) {
        Gson gson = new Gson();
        T object = gson.fromJson(json, type);
        return object;
    }
}
