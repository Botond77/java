
abstract class Shape implements Cloneable, Comparable<Shape> {

    PVector position = new PVector();
    int detailness = 100;

    Boolean selected = false;

    Boolean drawable = false;
    Boolean doneModifying = false;

    int fillColor = -12698050;
    int contourColor = -16711419;

    int selectedContourColor = -174023;
    int selectedFillColor = -14318564;

    Shape() {
    };

    abstract void draw_shape();

    abstract Boolean Contains(float x, float y);

    abstract void move(float x, float y);

    abstract void rotate(float x, float y);

    abstract void pushFar();

    abstract void bringOnTop();

    abstract void update(PVector update);

    abstract JSONObject toJsonMap();

    Object clone() throws CloneNotSupportedException {
        return (Shape) super.clone();
    }

    Boolean isDrawable() {
        return drawable;
    }

    @Override
    public boolean equals(Object obj) {
        return this == obj;
    }

    void setFillColor(int fillcolor) {
        this.fillColor = fillcolor;
    }

    void setContourColor(int contourColor) {
        this.contourColor = contourColor;
    }

    public int compareTo(Shape m) {
        return this.getClass().getSimpleName().compareTo(m.getClass().getSimpleName());
    }

}