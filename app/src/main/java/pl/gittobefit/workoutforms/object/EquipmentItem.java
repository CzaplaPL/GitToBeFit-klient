package pl.gittobefit.workoutforms.object;

public class EquipmentItem extends EquipmentForm
{
    private final String description;
    public EquipmentItem(int id, String name, String url, String description)
    {
        super(id, name, url);
        this.description=description;
    }
}
