abstract class Curve {

  int detailness = 100;

  ArrayList<PVector> bases = new ArrayList<PVector>();

  abstract void Draw();
  abstract PVector Eval(int index, float t);
}
