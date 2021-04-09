package pl.gittobefit.workoutforms.object;

public class BodyParts
{
    private String bodyName;
    private String bodyTitle;
    private boolean selected;

    public BodyParts(String bodyPart ,String title) {
        this.bodyName = bodyPart;
        this.bodyTitle = title;
        this.selected = false;
    }

    public String getBodyName() {
        return bodyName;
    }

    public void setBodyName(String name,String title) {

        this.bodyTitle = title;
        this.bodyName = name;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String getBodyTitle()
    {
        return bodyTitle;
    }
}
