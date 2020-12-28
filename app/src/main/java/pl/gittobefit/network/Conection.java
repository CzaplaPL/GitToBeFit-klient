package pl.gittobefit.network;

import java.security.cert.CertificateException;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import pl.gittobefit.user.User;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Conection
{

    private final Retrofit restAdapter ;
    public final UserServices user;
    public Conection(String url)
    {
        OkHttpClient okHttpClient = UnsafeOkHttpClient.getUnsafeOkHttpClient();
        restAdapter = new Retrofit.Builder()
                .baseUrl(url)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        user = new UserServices(restAdapter);
    }



}
