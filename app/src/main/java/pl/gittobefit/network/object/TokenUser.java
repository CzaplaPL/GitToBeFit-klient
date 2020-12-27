package pl.gittobefit.network.object;

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
