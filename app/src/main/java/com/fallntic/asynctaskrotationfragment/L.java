package com.fallntic.asynctaskrotationfragment;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

public class L {

    public static void m(String message){
        Log.d("AsyncTaskRotationFragment", message);
    }

    public static void s(Context context, String message){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
