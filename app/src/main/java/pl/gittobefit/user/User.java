package pl.gittobefit.user;

import android.content.Context;

import pl.gittobefit.database.AppDataBase;
import pl.gittobefit.database.entity.EntityUser;

/**
 * klasa przechowująca informacje o użytkowniku
 */
public class User
{
    public enum WayOfLogin
    {
        DEFAULT,
        OUR_SERVER,
        GOOGLE,
        FACEBOOK
    }

    private String email ="";
    private String auth ="";
    private String id ="";
    private WayOfLogin loggedBy = WayOfLogin.DEFAULT;

    private static volatile User INSTANCE;

    /**
     * pobieranie instancji usera
     * @return instancje usera
     * @author czapla
     */
    static public User getInstance()
    {
        if (INSTANCE == null)
        {
            synchronized (User.class)
            {
                if (INSTANCE == null)
                {
                    INSTANCE = new User();
                }
            }
        }
        return INSTANCE;
    }
    /**
     * funkcja dodaje użytkownika
     * @param email email
     * @param password hasło
     * @param auth auth naszego serwera
     * @param id idserwera
     * @param context context
     * @author czapla
     */
    public void add(String email, String password, String auth, String id, WayOfLogin loggedBy, Context context)
    {
        this.email=email;
        this.id=id;
        this.auth=auth;
        this.loggedBy = loggedBy;
        AppDataBase.getDatabase(context).user().addUser(new EntityUser(id, email, password));
    }
    /**
     * funkcja dodaje uzytkownika z pustym hasłem
     * @param email email
     * @param auth auth naszego serwera
     * @param id idserwera
     * @param context context
     * @author czapla
     */
    public void add(String email, String auth, String id, WayOfLogin loggedBy, Context context)
    {
        this.email=email;
        this.id=id;
        this.auth=auth;
        this.loggedBy = loggedBy;
        AppDataBase.getDatabase(context).user().addUser(new EntityUser(id, email, ""));
    }
    //getter do emaila - Kuba
    public String getEmail()
    {
        return this.email;
    }
    //getter do Tokena(zeminna Auth) - Kuba
    public String getToken()
    {
        return this.auth;
    }
    //setter do tokena - Kuba
    public void setToken(String auth) {
        this.auth = auth;
    }
    //getter do sposobu zalogowania - Kuba
    public WayOfLogin getLoggedBy() {
        return loggedBy;
    }
    //setter do sposobu zalogowania - Kuba
    public void setLoggedBy(WayOfLogin loggedBy) {
        this.loggedBy = loggedBy;
    }
    //setter email - Kuba
    public void setEmail(String email) {
        this.email = email;
    }
}
