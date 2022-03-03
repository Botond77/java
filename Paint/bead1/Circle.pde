class Circle extends Shape {
    float r = 40;
    float xRadius = 40;
    float yRadius = 40;

    Circle() {
    }

    Circle(float xRadius, float yRadius) {
        this.xRadius = xRadius / 3.0f;
        this.yRadius = yRadius / 3.0f;
    }

    void update(PVector update) {
        this.xRadius = update.x / 3.0f;
        this.yRadius = update.y / 3.0f;
    }

    void draw_shape() {

        if (selected) {
            fill(selectedFillColor);
            stroke(selectedContourColor);
        } else {
            fill(fillColor);
            stroke(contourColor);
        }

        float step = 2 * PI / detailness;

        pushMatrix();
        beginShape();

        for (int i = 0; i < detailness; i++) {
            float step_tmp = i * step;
            float x = position.x + xRadius * cos(step_tmp);
            float y = position.y + yRadius * sin(step_tmp);

            vertex(x, y);
        }
        endShape(CLOSE);
        popMatrix();
    }

    void setPosition(float x, float y) {
        position.x = x;
        position.y = y;
    }

    void rotate(float x, float y) {
    }

    void move(float x, float y) {
        position.x += x;
        position.y += y;
    }

    Boolean Contains(float x, float y) {

        if (xRadius > 0.01f && yRadius > 0.01f) {
            float d_x = pow(x - position.x, 2) / pow(xRadius, 2);
            float d_y = pow(y - position.y, 2) / pow(yRadius, 2);

            return d_y + d_x <= 1.0f;

        }
        return false;
    }

    String toString() {
        char q = '"';
        String tab = new String("\n\t");

        String ret = new String(q + "Circle" + q + ":" + "{\n");

        ret += tab + q + "Radius" + q + ": " + r + ",";
        ret += tab + q + "Center" + q + ": " + position.toString() + ",";
        ret += "\n},\n";
        return ret;
    }

    JSONObject toJsonMap() {

        JSONObject jsonObject = new JSONObject();
        JSONObject json = new JSONObject();

        jsonObject.setString("RadiusX", String.valueOf(xRadius));
        jsonObject.setString("RadiusY", String.valueOf(yRadius));
        jsonObject.setString("Center", position.toString());
        json.setJSONObject("Circle", jsonObject);

        return json;
    }

    void pushFar() {
    }

    void bringOnTop() {
    }

    public Object clone() throws CloneNotSupportedException {
        Circle c = new Circle();
        c.xRadius = xRadius;
        c.yRadius = yRadius;
        c.position.x = position.x + 10;
        c.position.y = position.y + 10;
        c.drawable = true;
        return c;
    }

}
