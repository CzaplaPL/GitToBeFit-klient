package pl.gittobefit.user;

import androidx.lifecycle.MutableLiveData;

/**
 * klasa przechowująca informacje o użytkowniku
 */
public class User
{

    public enum WayOfLogin
    {
        NO_LOGIN,
        OUR_SERVER,
        GOOGLE,
        FACEBOOK
    }

    public enum SynchroniseTraining
    {
        NO_SYNCHRONISE,
        START_SYNCHRONISE,
        SYNCHRONISE_SUCCESS,
        SYNCHRONISE_ERROR
    }

    private String email = "";
    private String auth = "";
    private String idServer = "";
    private WayOfLogin loggedBy = WayOfLogin.NO_LOGIN;
    private MutableLiveData<SynchroniseTraining> synchroniseTraining = new MutableLiveData<>(SynchroniseTraining.NO_SYNCHRONISE);
    private static volatile User INSTANCE;

    /**
     * pobieranie instancji usera
     *
     * @return instancje usera
     * @author czapla
     */
    static public User getInstance()
    {
        if(INSTANCE == null)
        {
            synchronized(User.class)
            {
                if(INSTANCE == null)
                {
                    INSTANCE = new User();
                }
            }
        }
        return INSTANCE;
    }

    /**
     * funkcja dodaje uzytkownika z pustym hasłem
     *
     * @param email email
     * @param auth  auth naszego serwera
     * @param id    idserwera
     * @author czapla
     */
    public void add(String email, String auth, String id, WayOfLogin loggedBy)
    {
        this.email = email;
        this.idServer = id;
        this.auth = auth;
        this.loggedBy = loggedBy;
        this.synchroniseTraining.setValue(SynchroniseTraining.NO_SYNCHRONISE);
    }

    public String getIdServer()
    {
        return idServer;
    }

    public void setIdServer(String idServer)
    {
        this.idServer = idServer;
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
    public void setToken(String auth)
    {
        this.auth = auth;
    }

    //getter do sposobu zalogowania - Kuba
    public WayOfLogin getLoggedBy()
    {
        return loggedBy;
    }

    //setter do sposobu zalogowania - Kuba
    public void setLoggedBy(WayOfLogin loggedBy)
    {
        this.loggedBy = loggedBy;
    }

    //setter email - Kuba
    public void setEmail(String email)
    {
        this.email = email;
    }

    public SynchroniseTraining getSynchroniseTraining()
    {
        return synchroniseTraining.getValue();
    }

    public MutableLiveData<SynchroniseTraining> getSynchroniseTraining$()
    {
        return synchroniseTraining;
    }

    public void setSynchroniseTraining(SynchroniseTraining synchroniseTraining)
    {
        this.synchroniseTraining.postValue(synchroniseTraining);
    }
}
