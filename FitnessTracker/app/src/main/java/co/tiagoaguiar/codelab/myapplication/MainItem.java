package co.tiagoaguiar.codelab.myapplication;

public class MainItem {
    private int id;
    private int drawableId;
    private int textId;
    private int color;
    private int backgroundId;

    public MainItem(int id, int drawableId, int textId, int color, int backgroundId) {
        this.id = id;
        this.drawableId = drawableId;
        this.textId = textId;
        this.color = color;
        this.backgroundId = backgroundId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDrawableId() {
        return drawableId;
    }

    public void setDrawableId(int drawableId) {
        this.drawableId = drawableId;
    }

    public int getTextId() {
        return textId;
    }

    public void setTextId(int textId) {
        this.textId = textId;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getBackgroundId() {
        return backgroundId;
    }

    public void setBackgroundId(int backgroundId) {
        this.backgroundId = backgroundId;
    }
}
