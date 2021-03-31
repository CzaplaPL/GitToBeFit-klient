package pl.gittobefit.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * klasa odpowiedzialana za połączenie z serwerem
 */
public class ConnectionToServer
{
    public final UserServices userServices;
    public final WorkoutFormsServices WorkoutFormsServices;
    public final TrainingServices trainingServices;
    private static volatile ConnectionToServer INSTANCE;
    public final String PREFIX_VIDEO_URL = "https://static.fabrykasily.pl/atlas/";

    static public ConnectionToServer getInstance()
    {
        if (INSTANCE == null)
        {
            synchronized (ConnectionToServer.class)
            {
                if (INSTANCE == null)
                {
                    INSTANCE = new ConnectionToServer("https://77.55.236.227:8443");
                }
            }
        }
        return INSTANCE;
    }

    /**
     * tworzy połączenie z serwerem
     * @param url url serwera
     */
    public ConnectionToServer(String url)
    {
        OkHttpClient okHttpClient = UnsafeOkHttpClient.getUnsafeOkHttpClient();
        Retrofit restAdapter = new Retrofit.Builder()
                .baseUrl(url)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        userServices = new UserServices(restAdapter);
        WorkoutFormsServices = new WorkoutFormsServices(restAdapter);
        trainingServices = new TrainingServices(restAdapter);
    }

    /**
     * sprawdza czy użytkownik ma internet
     * @return true jezeli ma
     */
    public static boolean isNetwork(Context context)
    {
        ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return  activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }
}
