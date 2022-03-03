class CatmullRom extends Curve {
  void Draw()
  {
    beginShape(LINES);

    for (int i = 0; i< bases.size()-3; i++)
    {
      float step = 1.0 / detailness;

      for (float t = 1.0; t<2.0; t+=step)
      {
        PVector p1= Eval(i, t);
        PVector p2= Eval(i, t+step);
        vertex(p1.x, p1.y);
        vertex(p2.x, p2.y);
      }
    }

    endShape();
  }

  PVector Eval(int index, float t)
  {
    PVector P0 = bases.get(index);
    PVector P1 = bases.get(index + 1);
    PVector P2 = bases.get(index + 2);
    PVector P3 = bases.get(index + 3);

    PVector A1 = P0.copy().mult(1-t).add(P1.copy().mult(t));
    PVector A2 = P1.copy().mult(2-t).add(P2.copy().mult(t-1));
    PVector A3 = P2.copy().mult(3-t).add(P3.copy().mult(t-2));

    PVector B1 = A1.copy().mult( (2-t) / 2.0f).add(A2.copy().mult(t / 2.0f));
    PVector B2 = A2.copy().mult( (3-t) / 2.0f).add(A3.copy().mult( (t-1) / 2.0f));

    PVector C = B1.copy().mult(2-t).add(B2.copy().mult(t-1));
    return C.copy();
  }
  void setPoligonBases ( ArrayList<PVector> poligon)
  {
    this.bases = new ArrayList<PVector>();


    if ( poligon.size() >= 6)
    {
      for ( PVector base : poligon)
      {
        if (!bases.contains(base))
        {
          bases.add(base);
        }
      }
    }

    ArrayList<PVector> toConcat = new ArrayList<PVector>(this.bases);
    concatBases(toConcat);
  }
  void concatBases( ArrayList<PVector> baselist)
  {
    for ( PVector  base : baselist)
      bases.add(new PVector(base.x, base.y));
  }
}
