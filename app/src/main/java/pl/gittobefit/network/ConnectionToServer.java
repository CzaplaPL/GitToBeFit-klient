package pl.gittobefit.network;

import java.net.InetAddress;

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
    private static volatile ConnectionToServer INSTANCE;
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
    }

    /**
     * sprawdza czy użytkownik ma internet
     * @return true jezeli ma 
     */
    public static boolean isNetwork()
    {
        try
        {
            InetAddress ipAddr = InetAddress.getByName("google.com");
            return !ipAddr.equals("");
        } catch (Exception e) {
            return false;
        }
    }
}
