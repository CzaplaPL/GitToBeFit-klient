package pl.gittobefit;

import android.util.Log;

public class LogUtils
{
    private LogUtils(){}

    public static void logCause(String cause)
     {
         try
         {
             Log.w("cause",cause);
         }catch(NullPointerException ignored){ }
     }
}
