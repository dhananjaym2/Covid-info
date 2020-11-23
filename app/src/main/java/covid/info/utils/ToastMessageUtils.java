package covid.info.utils;

import android.content.Context;
import android.widget.Toast;

public class ToastMessageUtils {
  public static void showToastMessage(Context context, String msg) {
    Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
  }
}
