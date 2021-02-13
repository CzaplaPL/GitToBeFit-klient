package pl.gittobefit.workoutforms.object;

public class EquipmentForm
{
    final private int id;
    final private String name;
    final private String url;
    private boolean isEqiupment=false;
    private boolean ischecked=false;

    public EquipmentForm(int id, String name, String url)
    {
        this.id = id;
        this.name = name;
        this.url = url;
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

    @Override
    public String toString()
    {
        return "name='" + name + '\'' +
                '}';
    }

    public boolean isEqiupment()
    {
        return isEqiupment;
    }

    public void setEqiupment(boolean eqiupment)
    {
        isEqiupment = eqiupment;
    }

    public boolean isIschecked()
    {
        return ischecked;
    }

    public void setIschecked(boolean ischecked)
    {
        this.ischecked = ischecked;
    }
}
