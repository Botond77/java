class Rectangle extends Shape implements Cloneable {
    float w = 80;
    float h = 80;
    PVector p1;
    PVector p2;

    PVector x1;
    PVector x2;
    PVector x3;
    PVector x4;

    float angle = 1;

    Rectangle() {

    }

    Rectangle(PVector p1) {
        this.p1 = p1;

    }

    void update(PVector p) {
        x1 = new PVector(p1.x, p1.y);
        x4 = new PVector(p.x, p.y);
        x2 = new PVector(x4.x, x1.y);
        x3 = new PVector(x1.x, x4.y);

        h = abs(x1.y - x4.y);
        w = abs(x1.x - x4.x);
    }

    void draw_shape() {

        if (x1 != null && x2 != null && x3 != null && x4 != null) {

            if (selected) {

                fill(selectedFillColor);
                stroke(selectedContourColor);
            } else {
                fill(fillColor);
                stroke(contourColor);
            }

            beginShape();

            vertex(x1.x, x1.y);
            vertex(x2.x, x2.y);
            vertex(x4.x, x4.y);
            vertex(x3.x, x3.y);

            endShape(CLOSE);
        }
    }

    Boolean Contains(float x, float y) {

        Boolean belowX = false;
        Boolean belowY = false;
        belowX = (length(x1) >= length(x4)) ? (x1.x >= x && x4.x <= x) : (x1.x <= x && x4.x >= x);
        belowY = (length(x1) >= length(x4)) ? (x1.y >= y && x4.y <= y) : (x1.y <= y && x4.y >= y);
        return belowX && belowY;
    }

    float length(PVector x) {
        return pow(x.x, 2) + pow(x.y, 2);
    }

    void move(float x, float y) {
        x1.x += x;
        x1.y += y;

        x2.x += x;
        x2.y += y;

        x3.x += x;
        x3.y += y;

        x4.x += x;
        x4.y += y;
    }

    void rotate(float x, float y) {
        angle = atan2(x - x1.y, y - x1.y);

    }

    public String toString() {
        if (x1 == null && x2 == null && x3 == null && x4 == null)
            return "Rectangle: ...";
        String ret = new String("Rectangle:\n{\n");

        ret += "\twidth: " + w + ",\n\theight: " + h;
        ret += ",\n\tX1:" + x1.toString();
        ret += ",\n\tX2:" + x2.toString();
        ret += ",\n\tX3:" + x3.toString();
        ret += ",\n\tX4:" + x4.toString();
        ret += "\n},\n";
        toJsonMap();
        return ret;

    }

    JSONObject toJsonMap() {
        JSONObject jsonObject = new JSONObject();
        JSONObject json = new JSONObject();

        jsonObject.setString("X1", x1.toString());
        jsonObject.setString("X2", x2.toString());
        jsonObject.setString("X3", x3.toString());
        jsonObject.setString("X4", x4.toString());

        json.setJSONObject("Rectangle", jsonObject);
        return json;

    }

    Boolean isDrawable() {
        return drawable;
    }

    void pushFar() {

        x1.z = 1;
        x2.z = 1;
        x3.z = 1;
        x4.z = 1;

    }

    void bringOnTop() {
        x1.z = 0;
        x2.z = 0;
        x3.z = 0;
        x4.z = 0;

    }

    public Object clone() throws CloneNotSupportedException {
        Rectangle r = new Rectangle(new PVector(x1.x + 5, x1.y + 5));
        r.setFillColor(fillColor);
        r.setContourColor(contourColor);

        r.update(new PVector(x4.x + 5, x4.y + 5));
        r.drawable = true;

        return r;
    }
}