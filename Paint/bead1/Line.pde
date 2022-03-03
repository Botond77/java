class Line extends Shape {

    final String name = "Line";
    private PVector p1;
    private PVector p2;

    Line() {
    }

    Line(PVector p1) {
        this.p1 = p1;
    }

    Line(PVector p1, PVector p2) {

        this.p1 = p1;
        this.p2 = p2;
        order();
    }

    void setPoints(PVector p1, PVector p2) {

        this.p1 = p1;
        this.p2 = p2;
        order();
    }

    void AddPoint(PVector p1) {
        if (this.p1 == null) {
            this.p1 = p1;
            return;
        }

        this.p2 = p1;
    }

    void update(PVector update) {
    }

    private void order() {
        float radiusP1 = pow(p1.x, 2) + pow(p1.y, 2);
        float radiusP2 = pow(p2.x, 2) + pow(p2.y, 2);
        if (radiusP1 > radiusP2) {
            PVector tmp = p1;
            p1 = p2;
            p2 = tmp;
        }
    }

    void draw_shape() {

        if (p2 == null) {

            beginShape();

            point(p1.x, p1.y);

            endShape(CLOSE);
        } else {
            float radiusP1 = pow(p1.x, 2) + pow(p1.y, 2);
            float radiusP2 = pow(p2.x, 2) + pow(p2.y, 2);
            if (radiusP1 < 0.001f || radiusP2 < 0.001f)
                return;

            if (selected) {

                fill(selectedFillColor);
                stroke(selectedContourColor);
            } else {
                fill(fillColor);
                stroke(contourColor);
            }
            beginShape();

            line(p1.x, p1.y, p2.x, p2.y);

            endShape(CLOSE);
        }
    }

    void move(float x, float y) {
        p1.x += x;
        p1.y += y;

        p2.x += x;
        p2.y += y;
    }

    void rotate(float x, float y) {

    }

    String toString() {
        char q = '"';
        String ret = new String();
        String tab = new String("\n\t");
        ret += q + "Line" + q + ":" + "\n{"
                + tab + q + "Fill" + q
                + ": " + String.valueOf(fillColor) + ","
                + tab + q + "Contour" + q + ": " + String.valueOf(contourColor) + ","
                + tab + q + "Startpos" + q + ": "
                + p1.toString() + ","
                + tab + q + "Endpos" + q + ": "
                + p2.toString() + "\n},\n";

        return ret;
    }

    Boolean isDrawable() {
        return p1 != null && p2 != null;
    }

    void pushFar() {
        p1.z = 0;
        p2.z = 0;

    }

    void bringOnTop() {

    }

    public Object clone() throws CloneNotSupportedException {
        Line l = (Line) super.clone();

        l.p1 = new PVector(p1.x + 10, p1.y + 10);
        l.p2 = new PVector(p2.x + 10, p2.y + 10);
        return l;
    }

    boolean onSegment(PVector p, PVector q, PVector r) {
        if (q.x <= Math.max(p.x, r.x) && q.x >= Math.min(p.x, r.x) &&
                q.y <= Math.max(p.y, r.y) && q.y >= Math.min(p.y, r.y))
            return true;

        return false;
    }

    int orientation(PVector p, PVector q, PVector r) {
        float val = (q.y - p.y) * (r.x - q.x) -
                (q.x - p.x) * (r.y - q.y);

        if (val == 0)
            return 0;

        return (val > 0) ? 1 : 2;
    }

    Boolean Contains(float x, float y) {

        PVector center = new PVector(x, y);
        PVector bottom = new PVector(x, y - 5);
        PVector top = new PVector(x, y + 5);
        PVector left = new PVector(x - 5, y);
        PVector right = new PVector(x + 5, y);

        return doIntersect(p1, p2, center, bottom) || doIntersect(p1, p2, center, top)
                || doIntersect(p1, p2, center, left) || doIntersect(p1, p2, center, right);
    }

    Boolean doIntersect(PVector p1, PVector q1, PVector p2, PVector q2) {

        int o1 = orientation(p1, q1, p2);
        int o2 = orientation(p1, q1, q2);
        int o3 = orientation(p2, q2, p1);
        int o4 = orientation(p2, q2, q1);

        if (o1 != o2 && o3 != o4)
            return true;

        if (o1 == 0 && onSegment(p1, p2, q1))
            return true;

        if (o2 == 0 && onSegment(p1, q2, q1))
            return true;

        if (o3 == 0 && onSegment(p2, p1, q2))
            return true;

        if (o4 == 0 && onSegment(p2, q1, q2))
            return true;

        return false;
    }

    JSONObject toJsonMap() {
        JSONObject jsonObject = new JSONObject();
        JSONObject json = new JSONObject();

        jsonObject.setString("Startpos", p1.toString());
        jsonObject.setString("EndPos", p2.toString());
        jsonObject.setString("Contour", String.valueOf(contourColor));
        jsonObject.setString("FillColor", String.valueOf(fillColor));
        json.setJSONObject("Line", jsonObject);

        return json;

    }

}
