package pl.gittobefit.database.entity.equipment;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Equipment
{
    @PrimaryKey()
    private int id;
    private String name;
    private String url;
    private int type;
    private boolean isOffline;

    public Equipment(int id, String name, String url, int type, boolean isOffline)
    {
        this.id = id;
        this.name = name;
        this.url = url;
        this.type = type;
        this.isOffline = isOffline;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getUrl()
    {
        return url;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }

    public int getType()
    {
        return type;
    }

    public void setType(int type)
    {
        this.type = type;
    }

    public boolean isOffline()
    {
        return isOffline;
    }

    public void setOffline(boolean offline)
    {
        isOffline = offline;
    }
}
