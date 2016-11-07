package com.xyzlf.area.selector;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by j-zhanglifeng-ncf on 2016/11/7.
 */

public class Utils {

    public static <V> V readAreaJson(Context context, Class<V> type) {
        InputStreamReader inputReader;
        try {
            inputReader = new InputStreamReader(context
                    .getResources().getAssets().open("area.json"));
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line;
            StringBuilder sb = new StringBuilder();
            while ((line = bufReader.readLine()) != null) {
                sb.append(line);
            }
            return GsonUtils.fromJson(sb.toString(), type);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
