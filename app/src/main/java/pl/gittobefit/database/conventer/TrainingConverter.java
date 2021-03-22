package pl.gittobefit.database.conventer;

import androidx.room.TypeConverter;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class TrainingConverter
{
    @TypeConverter
   public ArrayList<String> fromStringToArrayString(String value)
    {
        return new Gson().fromJson(value,new TypeToken<ArrayList<String>>(){}.getType());
    }

    @TypeConverter
    public String fromArrayString(ArrayList<String> value)
    {
    return new Gson().toJson(value);
    }

    @TypeConverter
    public ArrayList<Integer> fromStringToArrayInt(String value)
    {
        return new Gson().fromJson(value,new TypeToken<ArrayList<Integer>>(){}.getType());
    }

    @TypeConverter
    public String fromArrayInt(ArrayList<Integer> value)
    {
        return new Gson().toJson(value);
    }
}
