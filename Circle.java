import java.lang.*;
import java.util.*;

public class Circle
{
    public ArrayList listX;
    public ArrayList listY;
    public int nCoords;
    public int radius;
    public int deslX;
    public int deslY;

    public Circle(int radius, int deslX, int deslY)
    {
        listX = new ArrayList();
        listY = new ArrayList();
        nCoords = 0;
        this.radius = radius;
        this.deslX = deslX;
        this.deslY = deslY;
    }

    public void segment(int signalX, int signalY, boolean reversePath, boolean reverseXY)
    {
        int d, k, x, y;

        ArrayList tempListX = new ArrayList();
        ArrayList tempListY = new ArrayList();

        d = 5 / 4 - radius;
        k = 0;

        x = 0;
        y = radius;

        nCoords = 1;

        if (reverseXY)
        {
            tempListX.add(radius * signalY + deslX);
            tempListY.add(deslY);
        }
        else
        {
            tempListX.add(deslX);
            tempListY.add(radius * signalY + deslY);
        }

        while (x <= y)
        {
            x++;

            if (d < 0)
            {
                d += 2*x + 3;
            }
            else
            {
                d += 2*(x - y) + 5;
                y--;
            }

            if (reverseXY)
            {
                tempListX.add(y * signalY + deslX);
                tempListY.add(x * signalX + deslY);
            }
            else
            {
                tempListX.add(x * signalX + deslX);
                tempListY.add(y * signalY + deslY);
            }

            nCoords++;
        }

        listX.clear();
        listY.clear();

        if (reversePath)
        {
            for (int i = tempListX.size() - 1; i >= 0; i--)
            {
                listX.add(tempListX.get(i));
                listY.add(tempListY.get(i));
            }
        }
        else
        {
            for (int i = 0; i < tempListX.size(); i++)
            {
                listX.add(tempListX.get(i));
                listY.add(tempListY.get(i));
            }
        }
    }
}