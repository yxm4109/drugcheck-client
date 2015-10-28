package net.ucopy.drugcheck.tools;

import android.util.Log;

import net.ucopy.drugcheck.BuildConfig;

/**
 * Created by meituan on 15/10/24.
 * net.ucopy.drugcheck.manager
 */
public class DebugManager {

    public static void e(String tag, String content) {
        if (BuildConfig.DEBUG) {
            Log.e(tag, content);
        }
    }

    public static void i(String tag, String content) {
        if (BuildConfig.DEBUG) {
            Log.e(tag, content);
        }
    }

    public static void w(String tag, String content) {
        if (BuildConfig.DEBUG) {
            Log.e(tag, content);
        }
    }

}
