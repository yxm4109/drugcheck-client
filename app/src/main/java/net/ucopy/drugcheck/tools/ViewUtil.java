package net.ucopy.drugcheck.tools;

import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Toast;

/**
 * Created by Weh on 2015/10/18.
 */
public class ViewUtil {

    public static void toast(Context context,String content){
        Toast.makeText(context,content,Toast.LENGTH_SHORT).show();
    }

    public static void showProcessDialog(Context context, ProgressDialog m_pDialog, String title, String msg) {
        m_pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        m_pDialog.setTitle(title);
        m_pDialog.setMessage(msg);
        m_pDialog.setIndeterminate(false);
        m_pDialog.setCancelable(true);
        m_pDialog.show();

    }

}
