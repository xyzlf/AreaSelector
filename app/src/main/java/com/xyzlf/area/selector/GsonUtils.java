package com.xyzlf.area.selector;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.Reader;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

public class GsonUtils {

    private static final double VERSION = 1.0f;
    private static final Gson sGson = createGson(true, false);

    private static final Gson sGsonExpose = createGson(true, true);

    private GsonUtils() {
        throw new AssertionError();
    }

    /**
     * Create the standard {@link Gson} configuration
     *
     * @return created gson, never null
     */
    public static final Gson createGson() {
        return createGson(true, false);
    }

    /**
     * Create the standard {@link Gson} configuration
     *
     * @param serializeNulls whether nulls should be serialized
     * @return created gson, never null
     */
    private static final Gson createGson(final boolean serializeNulls,
                                         final boolean exposeEnable) {
        final GsonBuilder builder = new GsonBuilder();
        if (serializeNulls)
            builder.serializeNulls();
        builder.setVersion(VERSION);
//		builder.setPrettyPrinting();// json format
        if (exposeEnable) {
            builder.excludeFieldsWithoutExposeAnnotation();
        }
        return builder.create();
    }

    /**
     * Get reusable pre-configured {@link Gson} instance
     *
     * @return Gson instance
     */
    public static final Gson getGson() {
        return sGson;
    }

    /**
     * Get reusable pre-configured {@link Gson} instance
     *
     * @return Gson instance
     */
    public static final Gson getGson(final boolean exposeEnable) {
        return exposeEnable ? sGsonExpose : sGson;
    }

    /**
     * Convert object to json
     *
     * @param object
     * @return json string
     */
    public static final String toJson(final Object object) {
        return toJson(object, true);
    }

    /**
     * Convert object to json
     *
     * @param object
     * @return json string
     */
    public static final String toJson(final Object object,
                                      final boolean exposeEnable) {
        return exposeEnable ? sGsonExpose.toJson(object) : sGson.toJson(object);
    }

    /**
     * Convert string to given type
     *
     * @param json
     * @param type
     * @return instance of type
     */
    public static final <V> V fromJson(String json, Class<V> type) {
        return sGson.fromJson(json, type);
    }

    /**
     * Convert string to given type
     *
     * @param json
     * @param type
     * @return instance of type
     */
    public static final <V> V fromJson(String json, Type type) {
        return sGson.fromJson(json, type);
    }

    /**
     * Convert content of reader to given type
     *
     * @param reader
     * @param type
     * @return instance of type
     */
    public static final <V> V fromJson(Reader reader, Class<V> type) {
        return sGson.fromJson(reader, type);
    }

    /**
     * Convert content of reader to given type
     *
     * @param reader
     * @param type
     * @return instance of type
     */
    public static final <V> V fromJson(Reader reader, Type type) {
        return sGson.fromJson(reader, type);
    }

    /**
     * 将object对象转换成 map 仅支持 基本类型
     *
     * @param src
     * @param exposeEnable
     * @return
     */
    public static HashMap<String, String> toMap(Object src, boolean exposeEnable) {
        Gson gson = exposeEnable ? sGsonExpose : sGson;
        HashMap<String, String> params = new HashMap<String, String>();
        try {
            if (src == null)
                return null;

            JsonElement jsonTree = gson.toJsonTree(src);
            JsonObject jsonObj = jsonTree.getAsJsonObject();
            Iterator<Entry<String, JsonElement>> iterator = jsonObj.entrySet()
                    .iterator();
            String curKey;
            JsonElement curVal;
            while (iterator.hasNext()) {
                Entry<String, JsonElement> entry = iterator.next();
                curKey = entry.getKey();
                curVal = entry.getValue();
                if (!curVal.isJsonNull()) {
                    params.put(curKey, curVal.getAsString());
                }
            }
            return params;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return params;
    }
}
