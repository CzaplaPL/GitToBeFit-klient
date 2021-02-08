package pl.gittobefit.workoutforms.object;

public class EquipmentForm
{
    final private int id;
    final private String name;
    final private String url;
    public EquipmentForm(int id, String name, String url, int id1, String name1, String url1)
    {
        this.id = id1;

        this.name = name1;
        this.url = url1;
    }
    public int getId()
    {
        return id;
    }
    public String getName()
    {
        return name;
    }
    public String getUrl()
    {
        return url;
    }

}
