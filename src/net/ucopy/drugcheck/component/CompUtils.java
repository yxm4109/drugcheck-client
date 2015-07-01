package net.ucopy.drugcheck.component;

import android.app.ProgressDialog;
import android.content.Context;

public class CompUtils {
	public static void showProcessDialog(Context context, ProgressDialog m_pDialog, String title, String msg) {
		m_pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		m_pDialog.setTitle(title);
		m_pDialog.setMessage(msg);
		m_pDialog.setIndeterminate(false);
		m_pDialog.setCancelable(true);
		m_pDialog.show();

	}
}
