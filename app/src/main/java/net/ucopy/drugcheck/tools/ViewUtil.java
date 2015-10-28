package net.ucopy.drugcheck.tools;

import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Toast;

/**
 * Created by Weh on 2015/10/18.
 */
public class ViewUtil {

    public static void toast(Context context, String content) {
        Toast.makeText(context, content, Toast.LENGTH_SHORT).show();
    }

    public static void showProcessDialog(ProgressDialog dialog, String title, String msg) {
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setTitle(title);
        dialog.setMessage(msg);
        dialog.setIndeterminate(false);
        dialog.setCancelable(true);
        dialog.show();

    }

}
