package covid.info.utils;

import android.app.ProgressDialog;
import android.content.Context;

public class ProgressDialogUtils {

  public static ProgressDialog showProgressDialog(Context context, String progressDialogMessage) {
    ProgressDialog progressDialog = new ProgressDialog(context);
    progressDialog.setMessage(progressDialogMessage);
    progressDialog.setCancelable(false);
    progressDialog.show();
    return progressDialog;
  }

  public static void dismissProgressDialog(ProgressDialog progressDialog) {
    if (progressDialog != null && progressDialog.isShowing()) {
      progressDialog.dismiss();
    }
  }
}