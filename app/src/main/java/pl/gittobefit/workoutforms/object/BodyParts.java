package pl.gittobefit.workoutforms.object;

public class BodyParts
{
    private String bodyPart;
    private boolean selected;

    public BodyParts(String bodyPart) {
        this.bodyPart = bodyPart;
        this.selected = true;
    }

    public String getBodyPart() {
        return bodyPart;
    }

    public void setBodyPart(String title) {
        this.bodyPart = title;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
