package pl.gittobefit.network.object;

/**
 * obiekt tokena konwertowany na json
 * @author czapla
 */
public class TokenUser
{
    private String idToken;

    public String getToken()
    {
        return idToken;
    }

    public void setToken(String token)
    {
        this.idToken = token;
    }
    public TokenUser(String token)
    {
        this.idToken =token;
    }

}
