package me.root4.whereami;

import android.text.format.DateFormat;
import java.util.Date;

/**
 * Created by harish on 4/3/16.
 */
public class Utils {

    public static String getTime(long date) {
        return (String) DateFormat.format("hh:mm:ss a", new Date(date));
    }

    public static String getDate(long date) {
        return (String) DateFormat.format("MMM dd yy", new Date(date));
    }

    public static String getDateTime(long date) {
        return (String) DateFormat.format("MM/dd/yyyy hh:mm:ss a", new Date(date));
    }
}
