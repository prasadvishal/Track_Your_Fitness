package com.mindfiresolutions.trackyourfittness.Utilities;

import android.content.Context;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Vishal on 5/23/2017.
 * * modified on 5/23/2017
 */
public class LoggerUtility {

    public static void makeShortToast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    public static void makeLongToast(Context context, String msg) {

        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }

    public static void makeLog(String tag, String msg) {
        Log.e(tag, msg);
    }

}



