package pl.gittobefit.user;

import android.content.Context;

import pl.gittobefit.database.AppDataBase;
import pl.gittobefit.database.entity.EntityUser;

public class User
{
    private String email ="";
    private String password ="";
    private String auth ="";
    private String id ="";

    public void init(String email, String password, String auth, String id, Context context)
    {
        this.email=email;
        this.password=password;
        this.id=id;
        this.auth=auth;
        AppDataBase.getDatabase(context).user().addUser(new EntityUser(id, email, password));
    }
    public void initGoogle(String email,  String auth, String id, Context context)
    {
        this.email=email;
        this.password=" ";
        this.id=id;
        this.auth=auth;
        AppDataBase.getDatabase(context).user().addUser(new EntityUser(id, email, password));
    }
}
