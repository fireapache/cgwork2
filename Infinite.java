import java.awt.*;
import java.awt.geom.*;
import java.util.Date;
import java.lang.*;
import java.util.*;

public class Infinite
{
  public ArrayList pathX = new ArrayList();
  public ArrayList pathY = new ArrayList();
  public int coords;

  //Constructor
  public Infinite(int radius)
  {
    if (radius < 0) radius = - radius;

    Circle c1 = new Circle(radius, radius + 50, radius + 100);
    Circle c2 = new Circle(radius, 3 * radius + 50, radius + 100);

    ArrayList tempPathX;
    ArrayList tempPathY;

    pathX = new ArrayList();
    pathY = new ArrayList();

    c1.segment(1, -1, false, false);
    tempPathX = c1.listX;
    tempPathY = c1.listY;
    coords = c1.nCoords;

    for (int i = 1; i < tempPathX.size(); i++)
    {
      pathX.add((int)tempPathX.get(i));
      pathY.add((int)tempPathY.get(i));
    }

    c1.segment(-1, 1, true, true);
    coords += c1.nCoords;

    for (int i = 1; i < tempPathX.size(); i++)
    {
      pathX.add((int)tempPathX.get(i));
      pathY.add((int)tempPathY.get(i));
    }

    c2.segment(1, -1, false, true);
    tempPathX = c2.listX;
    tempPathY = c2.listY;
    coords += c2.nCoords;

    for (int i = 1; i < tempPathX.size(); i++)
    {
      pathX.add((int)tempPathX.get(i));
      pathY.add((int)tempPathY.get(i));
    }

    c2.segment(-1, 1, true, false);
    coords += c2.nCoords;

    for (int i = 1; i < tempPathX.size(); i++)
    {
      pathX.add((int)tempPathX.get(i));
      pathY.add((int)tempPathY.get(i));
    }

    c2.segment(1, 1, false, false);
    coords += c2.nCoords;

    for (int i = 1; i < tempPathX.size(); i++)
    {
      pathX.add((int)tempPathX.get(i));
      pathY.add((int)tempPathY.get(i));
    }

    c2.segment(1, 1, true, true);
    coords += c2.nCoords;

    for (int i = 1; i < tempPathX.size(); i++)
    {
      pathX.add((int)tempPathX.get(i));
      pathY.add((int)tempPathY.get(i));
    }

    c2.segment(-1, 1, false, true);
    coords += c2.nCoords;

    for (int i = 1; i < tempPathX.size(); i++)
    {
      pathX.add((int)tempPathX.get(i));
      pathY.add((int)tempPathY.get(i));
    }

    c2.segment(1, -1, true, false);
    coords += c2.nCoords;

    for (int i = 1; i < tempPathX.size(); i++)
    {
      pathX.add((int)tempPathX.get(i));
      pathY.add((int)tempPathY.get(i));
    }

    c2.segment(-1, -1, false, false);
    coords += c2.nCoords;

    for (int i = 1; i < tempPathX.size(); i++)
    {
      pathX.add((int)tempPathX.get(i));
      pathY.add((int)tempPathY.get(i));
    }

    c2.segment(-1, -1, true, true);
    coords += c2.nCoords;

    for (int i = 1; i < tempPathX.size(); i++)
    {
      pathX.add((int)tempPathX.get(i));
      pathY.add((int)tempPathY.get(i));
    }

    c1.segment(1, 1, false, true);
    tempPathX = c1.listX;
    tempPathY = c1.listY;
    coords += c1.nCoords;

    for (int i = 1; i < tempPathX.size(); i++)
    {
      pathX.add((int)tempPathX.get(i));
      pathY.add((int)tempPathY.get(i));
    }

    c1.segment(1, 1, true, false);
    coords += c1.nCoords;

    for (int i = 1; i < tempPathX.size(); i++)
    {
      pathX.add((int)tempPathX.get(i));
      pathY.add((int)tempPathY.get(i));
    }

    c1.segment(-1, 1, false, false);
    coords += c1.nCoords;

    for (int i = 1; i < tempPathX.size(); i++)
    {
      pathX.add((int)tempPathX.get(i));
      pathY.add((int)tempPathY.get(i));
    }

    c1.segment(1, -1, true, true);
    coords += c1.nCoords;

    for (int i = 1; i < tempPathX.size(); i++)
    {
      pathX.add((int)tempPathX.get(i));
      pathY.add((int)tempPathY.get(i));
    }

    c1.segment(-1, -1, false, true);
    coords += c1.nCoords;

    for (int i = 1; i < tempPathX.size(); i++)
    {
      pathX.add((int)tempPathX.get(i));
      pathY.add((int)tempPathY.get(i));
    }

    c1.segment(-1, -1, true, false);
    coords += c1.nCoords;

    for (int i = 1; i < tempPathX.size(); i++)
    {
      pathX.add((int)tempPathX.get(i));
      pathY.add((int)tempPathY.get(i));
    }
  }
}

