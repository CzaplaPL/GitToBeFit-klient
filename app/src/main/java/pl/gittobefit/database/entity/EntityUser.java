package pl.gittobefit.database.entity;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class EntityUser {
    @PrimaryKey(autoGenerate = true)
    private   int id;

    private String idSerwer;
    private String email;
    private String password;
    public EntityUser(String idSerwer,String email,String password)
    {
        this.idSerwer = idSerwer;
        this.email = email;
        this.password = password;

    }
    public int getId()
    {
        return id;
    }

    public String getIdSerwer()
    {
        return idSerwer;
    }

    public String getEmail()
    {
        return email;
    }

    public String getPassword()
    {
        return password;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    @Override
    public String toString()
    {
        return "EntityUser " + "email='" + email + '\'' +password;
    }
}
