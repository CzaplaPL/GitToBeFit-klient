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
    private boolean google;

    public UserEntity(int id, String email, String token, boolean google)
    {
        this.id = id;
        this.email = email;
        this.token = token;
        this.google = google;
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

    @Override
    public String toString()
    {
        return "EntityUser{" + "id=" + id + ", email='" + email + '\'' + ", token='" + token + '\'' + '}';
    }

    public boolean isGoogle()
    {
        return google;
    }

    public void setGoogle(boolean google)
    {
        this.google = google;
    }
}
