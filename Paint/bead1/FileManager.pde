public class FileManager 
{
    private JSONObject json;
    Map<String, String> multiplicityMap;
    public FileManager() {}
    
    void saveToFile() throws Exception {
        if (userShapes.size() == 0)
            return;
        
        json = new JSONObject();
        
        multiplicityMap = new HashMap<String, String>();
        
        for (Shape shape : shapes)
        {
            String name = shape.getClass().getSimpleName();
            
            multiplicityMap.put(name,"-");
        }
        
        Collections.sort(userShapes);
        
        Shape last = userShapes.get(0);
        int begin = 0;
        int end = 0;
        
        for (int i = 0; i < userShapes.size(); i++) 
        {
            
            
            String name = userShapes.get(i).getClass().getSimpleName();
            String lastName = last.getClass().getSimpleName();
            
            
            if (name.equals(lastName)) {
                end = i;
                
            } else {
                multiplicityMap.put(lastName, new String(String.valueOf(begin) + "-" + String.valueOf(end)));
                last = userShapes.get(i);
                begin = i;
                end = begin;
                
            }
            json.setJSONObject(name + String.valueOf(i), userShapes.get(i).toJsonMap());
        }
        
        
        
        String lastName = last.getClass().getSimpleName();
        multiplicityMap.put(lastName, new String(String.valueOf(begin) + "-" + String.valueOf(end)));
        
        for (Map.Entry < String, String > entry : multiplicityMap.entrySet()) {
            JSONObject obj = new JSONObject();
            obj.setString(entry.getKey(), entry.getValue());
            json.setJSONObject(entry.getKey(), obj);
        }
        
        saveJSONObject(json, "save.json");
        
    }
    ArrayList<Shape> loadFromFile() throws Exception 
    {
        multiplicityMap = new HashMap<String, String>();
        json = loadJSONObject("save.json");
        ArrayList<Shape> result = new ArrayList<Shape>();


        for (Shape shape : shapes)
        {
            String name = shape.getClass().getSimpleName();
            String intervall = json.getJSONObject(name).getString(name);
            
            if (!intervall.equals("-") && !multiplicityMap.containsKey(name))
            {
                multiplicityMap.put(name,intervall);
                ArrayList<Shape> loopResult = loadShape(name, intervall);
                for(Shape loopRes : loopResult)
                {
                    result.add(loopRes);
                }
            }
            
        }   
        
        return result;
        
    }
    ArrayList<Shape> loadShape(String name , String intervall) throws Exception
    {
        ArrayList<Shape> result = new ArrayList<Shape>();
        String[] arrOfStr = intervall.split("-", 2);
        int intervallBegin = Integer.parseInt(arrOfStr[0]);
        int intervallEnd = Integer.parseInt(arrOfStr[1]);
        
        
        for (int i = intervallBegin; i < intervallEnd + 1;i++)
        {
            String loadName = name + String.valueOf(i);
            result.add(loadByType(loadName,json));
            
        }
        
        return result;
        
        
    }
    Shape loadByType(String name, JSONObject json) throws Exception
    {
        
        if (name.contains("Line"))
        {
            
            
            return loadLine(name,json);
            
            
        }
        
        if (name.contains("Circle"))
        {   
            
            return loadCircle(name,json);
        }
        if (name.contains("Rectangle"))
        {
            return loadRectangle(name,json);
            
        }
        if (name.contains("Polygon"))
        {
            return loadPolygon(name,json);
        }
        
        if (name.contains("CatmullRomShape"))
        {
            
            return loadCatmullRomShape(name,json);
            
        }
        
        return null;
    }
    
    Shape loadRectangle(String name, JSONObject json)
    {
        JSONObject lineObj = json.getJSONObject(name);
        String X1 = lineObj.getJSONObject("Rectangle").getString("X1");
        String X2 = lineObj.getJSONObject("Rectangle").getString("X2");
        String X3 = lineObj.getJSONObject("Rectangle").getString("X3");
        String X4 = lineObj.getJSONObject("Rectangle").getString("X4");
        
        X1 = X1.substring(1,X1.length() - 1);
        X2 = X2.substring(1,X2.length() - 1);
        X3 = X3.substring(1,X3.length() - 1);
        X4 = X4.substring(1,X4.length() - 1);
        
        String[] X1OfStr = X1.split(",", 3);
        String[] X2OfStr = X2.split(",", 3);
        String[] X3OfStr = X3.split(",", 3);
        String[] X4OfStr = X4.split(",", 3);
        
        PVector x1 = new PVector(Float.parseFloat(X1OfStr[0]),Float.parseFloat(X1OfStr[1]),Float.parseFloat(X1OfStr[2]));
        PVector x2 = new PVector(Float.parseFloat(X2OfStr[0]),Float.parseFloat(X2OfStr[1]),Float.parseFloat(X2OfStr[2]));
        PVector x3 = new PVector(Float.parseFloat(X3OfStr[0]),Float.parseFloat(X3OfStr[1]),Float.parseFloat(X3OfStr[2]));
        PVector x4 = new PVector(Float.parseFloat(X4OfStr[0]),Float.parseFloat(X4OfStr[1]),Float.parseFloat(X4OfStr[2]));
        
        
        Rectangle r = new Rectangle(new PVector(x1.x,x1.y));
        r.update(new PVector(x4.x,x4.y));
        r.drawable = true;
        userShapes.add(r);
        
        return r;
        
    }
    Shape loadCircle(String name, JSONObject json)
    {
        JSONObject lineObj = json.getJSONObject(name);
        
        String RadiusX = lineObj.getJSONObject("Circle").getString("RadiusX");
        String RadiusY = lineObj.getJSONObject("Circle").getString("RadiusY");
        String Center = lineObj.getJSONObject("Circle").getString("Center");
        Center = Center.substring(1,Center.length() - 1);
        
        String[] CenterOfStr = Center.split(",", 3);
        
        PVector x1 = new PVector(Float.parseFloat(CenterOfStr[0]),Float.parseFloat(CenterOfStr[1]),Float.parseFloat(CenterOfStr[2]));
        
        
        Circle c = new Circle();
        c.xRadius = Float.parseFloat(RadiusX);
        c.yRadius = Float.parseFloat(RadiusY);
        c.setPosition(x1.x, x1.y);
        c.drawable = true;
        
        return c;
    }
    
    Shape loadLine(String name, JSONObject json)
    {   
        if (name.equals("Line"))
        {
            JSONObject lineObj = json;
            
        }    
        JSONObject lineObj = json.getJSONObject(name);
        
        
        
        String EndPos = lineObj.getJSONObject("Line").getString("EndPos");
        String Startpos = lineObj.getJSONObject("Line").getString("Startpos");
        String Contour = lineObj.getJSONObject("Line").getString("Contour");
        String FillColor = lineObj.getJSONObject("Line").getString("FillColor");
        
        EndPos = EndPos.substring(1,EndPos.length() - 1);
        Startpos = Startpos.substring(1,Startpos.length() - 1);
        
        String[] EndPosOfStr = EndPos.split(",", 3);
        String[] StartposOfStr = Startpos.split(",", 3);
        
        PVector x1 = new PVector(Float.parseFloat(EndPosOfStr[0]),Float.parseFloat(EndPosOfStr[1]),Float.parseFloat(EndPosOfStr[2]));
        PVector x4 = new PVector(Float.parseFloat(StartposOfStr[0]),Float.parseFloat(StartposOfStr[1]),Float.parseFloat(StartposOfStr[2]));
        
        float contour = Float.parseFloat(Contour);
        float fill = Float.parseFloat(FillColor);
        Line l = new Line();
        
        l.p1 = new PVector(x1.x,x1.y);
        l.p2 = new PVector(x4.x,x4.y);
        
        return l;
        
    }
    Shape loadPolygon(String name, JSONObject json)
    {
        JSONObject polyObj = json.getJSONObject(name);
        
        String sizeStr = polyObj.getString("Size");
        int size = Integer.parseInt(sizeStr);
        Polygon polygon = new Polygon();
        ArrayList<PVector> points = new ArrayList<PVector>();
        PVector p  = new PVector();
        for (int i = 0; i < size;i++)
        {
            Line l = (Line)loadLine("Line" + String.valueOf(i),polyObj);
            p = l.p2;
            polygon.addPoint(p);
            
        }
        polygon.showPolygon = true;
        return polygon;
    }
    
    Shape loadCatmullRomShape(String name, JSONObject json)
    {
        JSONObject catmullRomShapeObj = json.getJSONObject(name);

        Polygon p = (Polygon)loadPolygon("Polygon",catmullRomShapeObj);
        CatmullRomShape crs = new CatmullRomShape(p);
        crs.initCatmullRom();
        crs.showCatmullRom =true;
         crs.drawable = true;

        return crs;
    }

    
}