package pl.gittobefit.workoutforms.object;

public class Equipment extends EquipmentForm
{
    @Override
    public String toString()
    {
        return "Equipment{" +
                "description='" + getName() + '\'' +
                '}';
    }

    private final String description;
    public Equipment(int id, String name, String url,String description)
    {
        super(id, name, url);
        this.description=description;
    }
}
