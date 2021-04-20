package pl.gittobefit.database.entity.equipment;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class EquipmentType
{
    @PrimaryKey()
    private int id;
    private String name;
    private String path;
    private boolean offline;

    public EquipmentType(int id, String name, String path)
    {
        this.id = id;
        this.name = name;
        this.path = path;
        this.offline = true;
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

    public String getPath()
    {
        return path;
    }

    public void setPath(String path)
    {
        this.path = path;
    }

    public boolean isOffline()
    {
        return offline;
    }

    public void setOffline(boolean offline)
    {
        this.offline = offline;
    }
}
