package covid.info.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AppDateUtils {
  private static String APPEND_EMPTY_TIME = "T00:00:00Z";

  public static String getCurrentDate() {
    return getFormattedDateForServer(Calendar.getInstance().getTime()) + APPEND_EMPTY_TIME;
  }

  private static String getFormattedDateForServer(Date date) {
    final String DATE_FORMAT_FOR_SERVER = "yyyy-MM-dd";
    SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_FOR_SERVER, Locale.US);
    return sdf.format(date);
    //DateFormat targetFormat = new SimpleDateFormat(format, Locale.ENGLISH);
    //targetFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
    //return targetFormat.format(date);
  }

  public static String getYesterdayDate() {
    Calendar yesterday = Calendar.getInstance();
    yesterday.add(Calendar.DATE, -1);
    return getFormattedDateForServer(yesterday.getTime()) + APPEND_EMPTY_TIME;
  }
}