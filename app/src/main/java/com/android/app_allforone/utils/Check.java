package com.android.app_allforone.utils;

import android.util.Patterns;
import android.widget.EditText;

public class Check {
    public static boolean checkEmpty(String key, EditText error){
        if (key.isEmpty()) {
            error.setError("Re-password is required!");
            error.requestFocus();

            return false;
        }

        return true;
    }

    public static boolean checkFormat(String key, EditText error){
        if (!Patterns.EMAIL_ADDRESS.matcher(key).matches())
        {
            error.setError("Email type is invalid!");
            error.requestFocus();

            return false;
        }

        return true;
    }

    public static boolean checkConfPass(String pass, String confPass, EditText error){
        if (!pass.equals(confPass))
        {
            error.setError("Email type is invalid!");
            error.requestFocus();

            return false;
        }

        return true;
    }
}
