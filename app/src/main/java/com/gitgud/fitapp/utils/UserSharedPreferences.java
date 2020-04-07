package com.gitgud.fitapp.utils;

import android.content.Context;
import android.content.SharedPreferences;
import androidx.preference.PreferenceManager;

import com.gitgud.fitapp.entities.user.AddUserMutation;
import com.gitgud.fitapp.entities.user.LoginUserQuery;

public class UserSharedPreferences {

    SharedPreferences pref;

    static SharedPreferences getPreferences(Context context) {
        return context.getApplicationContext().getSharedPreferences("user_detail", 0);
    }

    public static void setLoggedUser(Context context, LoginUserQuery.LoginUser user) {
        SharedPreferences.Editor editor = getPreferences(context).edit();
        editor.putString("id", user.id());
        editor.putString("name", user.name());
        editor.putString("lastname", user.lastName());
        editor.putString("email", user.email());
        editor.commit();
    }

    public  static String getUserProperty(Context context, String property) {
        return getPreferences(context).getString(property, " ");
    }

}
