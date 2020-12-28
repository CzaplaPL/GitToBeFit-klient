package pl.gittobefit.network;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * klasa odpowiedzialana za połączenie z serwerem
 */
public class Connection
{
    public final UserServices user;
    private static volatile Connection INSTANCE;
    static public Connection getConect()
    {
        if (INSTANCE == null)
        {
            synchronized (Connection.class)
            {
                if (INSTANCE == null)
                {
                    INSTANCE = new Connection("https://77.55.236.227:8443");
                }
            }
        }
        return INSTANCE;
    }

    /**
     * tworzy połączenie z serwerem
     * @param url url serwera
     */
    public Connection(String url)
    {
        OkHttpClient okHttpClient = UnsafeOkHttpClient.getUnsafeOkHttpClient();
        Retrofit restAdapter = new Retrofit.Builder()
                .baseUrl(url)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        user = new UserServices(restAdapter);
    }



}
