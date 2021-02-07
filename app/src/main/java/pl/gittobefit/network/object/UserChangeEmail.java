package pl.gittobefit.network.object;

public class UserChangeEmail
{
    private String email;
    private String password;
    public UserChangeEmail(String email, String password)
    {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "ChangeEmailUser{" + "email='" + email + '\'' + ", password='" + password + '\'' + '}';
    }
}
