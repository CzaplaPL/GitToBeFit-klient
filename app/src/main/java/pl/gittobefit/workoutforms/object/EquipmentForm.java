package pl.gittobefit.workoutforms.object;

public class EquipmentForm
{
    final private int id;
    final private String name;
    final private String url;
    private boolean isEqiupment=false;
    private boolean ischecked=false;
    private boolean isOffline;

    public EquipmentForm(int id, String name, String url,boolean isOffline)
    {
        this.id = id;
        this.name = name;
        this.url = url;
        this.isOffline = isOffline;
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
    public boolean isOffline() { return isOffline; }
}
