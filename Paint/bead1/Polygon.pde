class Polygon extends Shape {

    private ArrayList<PVector> points;
    ArrayList<Line> lines;
    Boolean showPolygon = false;

    Boolean isInited = false;

    Polygon() {
        points = new ArrayList<PVector>();
        lines = new ArrayList<Line>();
    }

    Polygon(PVector point) {
        points = new ArrayList<PVector>();
        lines = new ArrayList<Line>();
        points.add(point);

    }

    void update(PVector update) {
    }

    ArrayList<Line> getLines() {
        return this.lines;
    }

    ArrayList<PVector> getPoints() {
        return this.points;
    }

    public void addPoint(PVector point) {

        points.add(point);
        refresh();
    }

    public void refresh() {
        if (points.size() < 2)
            return;
        int i = points.size() - 2;
        PVector size2 = points.get(i);
        PVector size1 = points.get(i + 1);
        lines.add(
                new Line(new PVector(size2.x, size2.y),
                        new PVector(size1.x, size1.y)));

    }

    void draw_shape() {

        if (showPolygon) {

            for (Line line : lines) {
                line.selected = selected;
                line.draw_shape();

            }

        } else {
            for (PVector point : points) {
                beginShape();
                point(point.x, point.y);
                endShape(CLOSE);

            }

        }

    }

    void move(float x, float y) {

        for (Line line : lines) {
            line.move(x, y);
        }
        for (PVector point : points) {
            point.x += x;
            point.y += y;
        }
    }

    void rotate(float x, float y) {

    }

    Boolean Contains(float x, float y) {

        for (Line line : lines) {
            if (line.Contains(x, y)) {
                return true;
            }
        }
        return false;
    }

    public String toString() {
        char q = '"';
        String ret = new String();
        ret += q + "Polygon" + q + ":\n{\n";
        for (int i = 0; i < lines.size() / 2 + 1; i++) {
            String inner = new String();
            String lineToStr = lines.get(i).toString();

            inner += lineToStr;

            ret += inner + "\n";
        }
        ret += "\n},\n";
        return ret;
    }

    JSONObject toJsonMap() {
        JSONObject json = new JSONObject();
        int i=0;
        while ( i < lines.size() / 2 + 1) {
            JSONObject jsonObject = lines.get(i).toJsonMap();
            json.setJSONObject("Line"+String.valueOf(i), jsonObject);
            i++;
        }
        json.setString("Size",String.valueOf(i));
        return json;

    }

    Boolean isDrawable() {
        return showPolygon;
    }

    public Object clone() throws CloneNotSupportedException {
        Polygon p = new Polygon();

        p.points = new ArrayList<PVector>();
        for (PVector v : points) {
            p.points.add(new PVector(v.x + 10, v.y + 10));
        }

        p.lines = new ArrayList<Line>();

        for (Line line : lines) {
            Line l = (Line) line.clone();
            p.lines.add(l);
        }
        p.setFillColor(fillColor);
        p.setContourColor(contourColor);
        p.showPolygon = true;

        return p;
    }

    void pushFar() {

    }

    void bringOnTop() {

    }
}
