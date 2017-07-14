package com_ucast_manager.tools;

import android.content.Context;
import android.content.SharedPreferences;

import com_ucast_manager.app.ExceptionApplication;


/**
 * Created by Administrator on 2016/6/12.
 */
public class SavePasswd {

    private static SavePasswd savePasswd;
    private final String fileName = "myfile";


    private SavePasswd() {

    }

    ;

    public static SavePasswd getInstace() {
        if (savePasswd == null) {
            synchronized (SavePasswd.class) {
                if (savePasswd == null) {
                    savePasswd = new SavePasswd();
                }
            }
        }
        return savePasswd;
    }

    public void save(String name, String passwd) {
        SharedPreferences sp = ExceptionApplication
                .context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(name, passwd);
        editor.commit();
    }

    public String get(String name) {
        SharedPreferences sp = ExceptionApplication
                .context.getSharedPreferences(fileName, Context.MODE_PRIVATE);

        return sp.getString(name, "");
    }

}
