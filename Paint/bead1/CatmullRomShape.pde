class CatmullRomShape extends Shape {
    CatmullRom catmullrom;
    Polygon poly;
    Boolean showCatmullRom = false;

    CatmullRomShape(Polygon poly) {
        catmullrom = new CatmullRom();
        this.poly = poly;

    }

    void initCatmullRom() {

        ArrayList<PVector> points = poly.getPoints();
        catmullrom.setPoligonBases(points);

    }

    void update(PVector update) {
    }

    void draw_shape() {
        if (showCatmullRom) {
            if (selected) {

                fill(selectedFillColor);
                stroke(selectedContourColor);
            } else {
                fill(fillColor);
                stroke(contourColor);
            }
            beginShape(LINES);

            for (int i = 0; i < catmullrom.bases.size() - 3; i++) {
                float step = 1.0 / detailness;

                for (float t = 1.0; t < 2.0; t += step) {
                    PVector p1 = catmullrom.Eval(i, t);
                    PVector p2 = catmullrom.Eval(i, t + step);
                    vertex(p1.x, p1.y);
                    vertex(p2.x, p2.y);
                }

            }
        }

        poly.draw_shape();
        endShape();
    }

    void move(float x, float y) {
        poly.move(x, y);
        initCatmullRom();
    }

    Boolean Contains(float x, float y) {
        ArrayList<Line> lines = poly.getLines();
        for (Line line : lines) {
            if (line.Contains(x, y)) {
                return true;
            }
        }

        return false;
    }

    Boolean isDrawable() {
        return drawable;
    }

    void rotate(float x, float y) {

    }

    void pushFar() {

    }

    void bringOnTop() {
    }

    public Object clone() throws CloneNotSupportedException {
        Polygon p = (Polygon) poly.clone();

        CatmullRomShape cRShape = new CatmullRomShape(p);

        cRShape.showCatmullRom = true;
        cRShape.initCatmullRom();
        cRShape.drawable = true;

        return cRShape;
    }

    public String toString() {
        char q = '"';
        String ret = new String(q + "CatMullRomShape" + q + ":" + "\n{");

        ret += poly.toString() + "\n},\n";
        return ret;
    }

    JSONObject toJsonMap() {

        JSONObject json = new JSONObject();

        json.setJSONObject("Polygon", poly.toJsonMap());

        return json;

    }
}
