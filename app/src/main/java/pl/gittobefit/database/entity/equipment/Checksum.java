package pl.gittobefit.database.entity.equipment;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;

@Entity
public class Checksum
{

    private String Table;
    @PrimaryKey
    private Long Checksum;
    private ArrayList<Integer> loadedType = new ArrayList<>();

    public String getTable()
    {
        return Table;
    }

    public void setTable(String table)
    {
        Table = table;
    }

    public Long getChecksum()
    {
        return Checksum;
    }

    public void setChecksum(Long checksum)
    {
        Checksum = checksum;
    }

    public ArrayList<Integer> getLoadedType()
    {
        return loadedType;
    }

    public void setLoadedType(ArrayList<Integer> loadedType)
    {
        this.loadedType = loadedType;
    }
}
