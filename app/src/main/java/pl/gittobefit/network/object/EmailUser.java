package pl.gittobefit.network.object;

public class EmailUser
{
    private String email;
    public EmailUser(String email)
    {
        this.email=email;
    }
    public String getEmail() { return this.email;}

    @Override
    public String toString()
    {
        return email;

    }
}

