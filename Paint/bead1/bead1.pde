import java.io.FileWriter;
import java.util.Iterator;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Collections;


    ArrayList<Shape> shapes;
    Rectangle r;
    Rectangle colorPickerRectangle;
    Rectangle contourPickerRectangle;
    Rectangle colorPickerButton;
    Rectangle contourPickerButton;
    Circle c;
    Circle illustratorCircle;
    Line l;
    Polygon poly;
    Shape selectedShape = poly;
    Shape userShape = null;
    Shape editShape = null;
    CatmullRomShape catmullshape;
    FileManager fileManager;
    ArrayList<Shape> userShapes;

    ColorPicker colorPicker;
    ColorPicker contourPicker;

    static int strokeWeight = 2;

    int fillColor = -12698050;
    int contourColor = -16711419;

    void setup() {
        frameRate(100);

        fileManager = new FileManager();

        strokeWeight(strokeWeight);

        colorPickerButton = new Rectangle(new PVector(100, 500));
        colorPickerButton.update(new PVector(200, 550));

        contourPickerButton = new Rectangle(new PVector(100, 500));
        contourPickerButton.update(new PVector(200, 550));

        userShapes = new ArrayList<Shape>();
        shapes = new ArrayList();

        size(800, 600);

        c = new Circle();
        c.position.x += 50;
        c.position.y += 180;

        colorPickerRectangle = new Rectangle(new PVector(10, 10));
        colorPickerRectangle.update(new PVector(30, 30));

        contourPickerRectangle = new Rectangle(new PVector(50, 10));
        contourPickerRectangle.update(new PVector(70, 30));

        illustratorCircle = new Circle(40, 40);
        illustratorCircle.position.x += 100;
        illustratorCircle.position.y += 20;
        illustratorCircle.r = 5;

        shapes.add(illustratorCircle);
        shapes.add(colorPickerRectangle);
        shapes.add(contourPickerRectangle);

        r = new Rectangle(new PVector(10, 50));
        r.update(new PVector(90, 100));

        colorPicker = new ColorPicker(10, 10, 400, 400, 255);
        contourPicker = new ColorPicker(10, 10, 400, 400, 255);

        l = new Line(new PVector(30, 250), new PVector(90, 250));

        poly = new Polygon();
        // l1
        poly.addPoint(new PVector(30, 300)); // 0
        poly.addPoint(new PVector(90, 300)); // 1
        // l2
        poly.addPoint(new PVector(90, 400)); // 2
        poly.addPoint(new PVector(90, 300)); // 3
        // l3
        poly.addPoint(new PVector(90, 400)); // 4
        poly.addPoint(new PVector(30, 400)); // 5
        poly.showPolygon = true;

        ArrayList<PVector> points = new ArrayList<PVector>(poly.getPoints());
        Polygon catmullpoly = new Polygon();

        for (PVector point : points) {
            point.y += 150;
            catmullpoly.addPoint(point);
        }

        catmullpoly.showPolygon = true;
        catmullshape = new CatmullRomShape(catmullpoly);
        catmullshape.initCatmullRom();

        shapes.add(poly);
        shapes.add(c);
        shapes.add(r);
        shapes.add(l);
        shapes.add(catmullshape);
        catmullshape.showCatmullRom = true;

    }

    void draw() {

        background(120);

        if (colorPicker.isDrawable) {
            background(80);

            colorPickerButton.draw_shape();
            colorPicker.render();
            fill(-67593);

            text("Select Color", 110, 525, 1);
        } else if (contourPicker.isDrawable) {
            background(80);
            contourPickerButton.draw_shape();
            contourPicker.render();
            fill(-67593);
            text("Select Contour", 105, 525, 1);
        } else {

            for (Iterator<Shape> shapeIterator = userShapes.iterator(); shapeIterator.hasNext();) {
                Shape shape = shapeIterator.next();
                shape.draw_shape();

                String selectedName = (selectedShape != null)
                        ? selectedShape.getClass().getSimpleName()
                        : "emptyselectedName";

                String shapeName = (userShape != null) ? shape.getClass().getSimpleName() : "emptyShapeName";

                if (!shape.isDrawable() && !selectedName.equals(shapeName)) {
                    shapeIterator.remove();
                }
            }

            for (Shape shape : shapes) {
                shape.draw_shape();
            }
        }
    }

    void mousePressed() {
        String selectedName = (selectedShape != null)
                ? selectedShape.getClass().getSimpleName()
                : "";

        if (mouseButton == LEFT && !selectedName.isEmpty()) {
            int i = 0;
            Boolean found = false;

            while (i < userShapes.size() && !found) {
                found = (selectedName.equals(userShapes.get(i).getClass().getSimpleName())
                        &&
                        !userShapes.get(i).isDrawable()) ? true : false;
                i++;
            }
            if (selectedName.equals("Line")) {
                if (!found) {
                    Line l = new Line(new PVector(mouseX, mouseY));
                    l.setFillColor(fillColor);
                    l.setContourColor(contourColor);
                    userShapes.add(l);
                    userShape = l;
                } else {
                    Line l = (Line) userShapes.get(i - 1);
                    l.AddPoint(new PVector(mouseX, mouseY));
                    clearSelection();
                }
            }
            if (selectedName.equals("Rectangle")) {
                if (!found) {
                    Rectangle r = new Rectangle(new PVector(mouseX, mouseY));
                    r.setFillColor(fillColor);
                    r.setContourColor(contourColor);
                    userShapes.add(r);
                    userShape = r;
                } else {
                    Rectangle r = (Rectangle) userShapes.get(i - 1);
                    r.update(new PVector(mouseX, mouseY));
                    r.drawable = true;
                    clearSelection();
                }
            }
            if (selectedName.equals("Polygon")) {

                if (!found) {
                    Polygon p = new Polygon(new PVector(mouseX, mouseY));
                    p.setFillColor(fillColor);
                    p.setContourColor(contourColor);
                    userShapes.add(p);
                    userShape = p;
                } else {

                    Polygon p = (Polygon) userShapes.get(i - 1);
                    p.addPoint(new PVector(mouseX, mouseY));
                    ArrayList<PVector> points = p.getPoints();
                    PVector joinPoint = new PVector(points.get(points.size() - 1).x, points.get(points.size() - 1).y);
                    p.addPoint(joinPoint);
                }
            }

            if (selectedName.equals("Circle")) {
                if (!found) {

                    Circle c = new Circle(mouseX, mouseY);
                    c.setPosition(mouseX, mouseY);
                    c.setFillColor(fillColor);
                    c.setContourColor(contourColor);
                    userShapes.add(c);
                    userShape = c;
                } else {

                    Circle c = (Circle) userShapes.get(i - 1);
                    c.drawable = true;
                    clearSelection();
                }
            }
            if (selectedName.equals("CatmullRomShape")) {

                if (!found) {
                    Polygon p = new Polygon(new PVector(mouseX, mouseY));
                    CatmullRomShape CRShape = new CatmullRomShape(p);
                    userShape = CRShape;
                    userShapes.add(CRShape);
                } else {
                    CatmullRomShape CRShape = (CatmullRomShape) userShapes.get(i - 1);
                    Polygon p = CRShape.poly;

                    p.addPoint(new PVector(mouseX, mouseY));
                    ArrayList<PVector> points = p.getPoints();
                    PVector joinPoint = new PVector(points.get(points.size() - 1).x, points.get(points.size() - 1).y);
                    p.addPoint(joinPoint);
                }
            }

        } else if (mouseButton == LEFT && selectedName.isEmpty()) {
            int i = 0;
            Boolean contains = false;
            while (i < userShapes.size() && !contains) {
                contains = userShapes.get(i).Contains(mouseX, mouseY);
                i++;
            }
            clearEditSelection();
            editShape = (contains) ? userShapes.get(i - 1) : null;
            markEditShape();
        }

        if (colorPickerButton.Contains(mouseX, mouseY)) {
            colorPickerRectangle.setFillColor(colorPicker.c);
            illustratorCircle.setFillColor(colorPicker.c);
            fillColor = colorPicker.c;
            colorPicker.isDrawable = false;
        }
        if (contourPickerButton.Contains(mouseX, mouseY)) {
            contourPickerRectangle.setContourColor(contourPicker.c);
            illustratorCircle.setContourColor(contourPicker.c);
            contourColor = contourPicker.c;
            contourPicker.isDrawable = false;
        }
        if (contourPickerRectangle.Contains(mouseX, mouseY)) {
            clearSelection();
            clearEditSelection();

            contourPicker.isDrawable = true;
        }
        if (colorPickerRectangle.Contains(mouseX, mouseY)) {
            clearSelection();
            clearEditSelection();

            colorPicker.isDrawable = true;
        }
    }

    void markEditShape() {
        if (editShape != null) {
            editShape.selected = true;
        }
    }

    void clearSelection() {
        userShape = null;
        if (selectedShape != null) {
            selectedShape.selected = false;
            selectedShape = null;
        }
    }

    void clearEditSelection() {
        if (editShape != null) {
            editShape.selected = false;
            editShape = null;
        }
    }

    void mouseMoved() {
        String selectedName = (selectedShape != null)
                ? selectedShape.getClass().getSimpleName()
                : "emptySelectedName";

        String userShapename = (userShape != null) ? userShape.getClass().getSimpleName() : "emptyUserShapename";

        if (!userShapename.equals(selectedName)) {
            return;
        }
        if (userShape != null) {
            Shape shape = (Shape) userShape;
            shape.update(new PVector(mouseX, mouseY));
        }
    }

    void mouseDragged() {
        if (mouseButton == LEFT) {
            if (editShape != null)
                editShape.move(mouseX - pmouseX, mouseY - pmouseY);
        } else {
            if (editShape != null)
                editShape.rotate(mouseX, mouseY);
        }
    }

    void keyPressed() {

        String selectedName = (selectedShape != null)
                ? selectedShape.getClass().getSimpleName()
                : "";
        System.out.println("keyCode :"+ keyCode);
        if (keyCode == 17) {
            if (selectedName.equals("Circle")) {
                if (userShape != null) {
                    Circle c = (Circle) userShape;
                    c.update(new PVector(mouseX, mouseX));
                    c.drawable = true;
                    clearSelection();
                }
            }
        }
        if (keyCode == 10) {
            if (selectedName.equals("Polygon")) {
                if (userShape != null) {
                    Polygon poly = (Polygon) userShape;
                    if (poly.getPoints().size() < 4)
                        return;
                    poly.showPolygon = true;
                    clearSelection();
                }
            }

            if (selectedName.equals("CatmullRomShape")) {
                if (userShape != null) {
                    CatmullRomShape CRShape = (CatmullRomShape) userShape;

                    Polygon poly = CRShape.poly;
                    if (poly != null) {

                        CRShape.showCatmullRom = (poly.showPolygon) ? true : false;
                        poly.showPolygon = true;

                        if (CRShape.showCatmullRom) {
                            CRShape.initCatmullRom();
                            CRShape.drawable = true;
                            clearSelection();
                        }
                    }
                }
            }
        }
        // 0
        if (keyCode == 192) {
            if (userShape != null) {
                userShapes.remove(userShapes.size() - 1);
            }
        }

        if (selectedShape != null)
            selectedShape.selected = false;
        // 1
        if (keyCode == 49) {
            selectedShape = r;
        }
        // 2
        if (keyCode == 50) {
            selectedShape = c;
        }
        // 3
        if (keyCode == 51) {
            selectedShape = l;
        }
        // 4
        if (keyCode == 52) {
            selectedShape = poly;
        }
        // 5
        if (keyCode == 53) {
            selectedShape = catmullshape;
        }
        // U
        if (keyCode == 85) {
            // missing
        }
        // D
        if (keyCode == 68) {
            if (editShape != null) {
                try {
                    Shape shape = (Shape) editShape.clone();

                    userShapes.add(shape);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        // Delete
        if (keyCode == 127) {
            if (editShape != null)
                userShapes.remove(editShape);
            editShape = null;
        }
        // F
        if (keyCode == 70) {

            
            try 
            {

                fileManager.saveToFile();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

        }

        if (selectedShape != null) {
            clearEditSelection();

            selectedShape.selected = true;
        }
        if(keyCode == 76)
        {
            try 
            {

                ArrayList<Shape> result = fileManager.loadFromFile();
                if(result.size()!=0)
                {
                    userShapes= result;
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

