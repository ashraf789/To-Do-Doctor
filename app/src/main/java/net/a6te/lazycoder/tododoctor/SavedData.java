package net.a6te.lazycoder.tododoctor;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Mobile App Develop on 4/5/2017.
 */

public class SavedData{
    private static final String PREFERENCE_NAME = "authenticate info";
    private static final String USER_EMAIL = "email";
    private static final String USER_PASSWORD = "pass";
    private static final String DEFAULT_MSG = null;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Context context;


    public SavedData(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(PREFERENCE_NAME,context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void saveData(String emial,String password){
        editor.putString(USER_EMAIL,emial);
        editor.putString(USER_PASSWORD,password);
        editor.commit();
    }
    public String getEmail(){
        return sharedPreferences.getString(USER_EMAIL,DEFAULT_MSG);
    }
    public String getPassword(){
        return sharedPreferences.getString(USER_PASSWORD,DEFAULT_MSG);
    }

    public void cleanData(){

    }
}
