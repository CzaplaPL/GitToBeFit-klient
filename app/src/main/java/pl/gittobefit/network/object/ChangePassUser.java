package pl.gittobefit.network.object;

/**
 * @author Kuba
 * body do zmiany hasła
 */

public class ChangePassUser
{
    private String email;
    private String actualPassword;
    private String newPassword;
    public ChangePassUser(String email,String actualPassword,  String newPassword)
    {
        this.email = email;
        this.actualPassword = actualPassword;
        this.newPassword = newPassword;
    }
    String getEmail() { return this.email;}
    String getActualPassword() { return this.actualPassword;}

    public String getNewPassword() {
        return newPassword;
    }

    @Override
    public String toString()
    {
        return email + " " + actualPassword;

    }
}
