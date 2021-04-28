package pl.gittobefit.database.entity;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * encja uzytkownika przechowywana w bazie
 * bedzie zmiana przetrzymywania automatycznego logowania
 * @author Czapla
 */
@Entity
public class UserEntity
{
    @PrimaryKey()
    private int id;

    private String email;
    private String token;
    private boolean isLoggedByGoogle;

    public UserEntity(int id, String email, String token, boolean isLoggedByGoogle)
    {
        this.id = id;
        this.email = email;
        this.token = token;
        this.isLoggedByGoogle = isLoggedByGoogle;
    }

    public int getId()
    {
        return id;
    }

    public String getEmail()
    {
        return email;
    }

    public String getToken()
    {
        return token;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public void setToken(String token)
    {
        this.token = token;
    }

    public boolean isLoggedByGoogle()
    {
        return isLoggedByGoogle;
    }

    public void setLoggedByGoogle(boolean loggedByGoogle)
    {
        this.isLoggedByGoogle = loggedByGoogle;
    }
}
