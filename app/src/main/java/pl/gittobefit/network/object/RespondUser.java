package pl.gittobefit.network.object;

/**
 * obiekt usera konwertowany na jsona
 * @author czapla
 */
public class RespondUser
{
    private String email;
    private String password;
    public RespondUser(String email,String password)
    {
        this.email=email;
        this.password=password;
    }
    public String getEmail() { return this.email;}
    public String getPassword() { return this.password;}

    @Override
    public String toString()
    {
        return email + " " + password;

    }
}
