import java.awt.*;
import java.awt.geom.*;
import java.util.Timer;
import java.util.TimerTask;
import java.awt.image.BufferedImage;

/**
* A simple example for transforming one triangulated image into another one.
* For the animation, the doube buffering technique is applied in the same way 
* as in the clock example in the class DoubeBufferingClockExample.
*
* @author Frank Klawonn
* Last change 31.05.2005
*
* @see TriangulatedImage
* @see BufferImageDrawer
* @see DoubeBufferingClockExample
*/
public class MorphingCandS extends TimerTask
{

  //The window in which the transformation is shown.
  private BufferedImageDrawer buffid;

  //The two images to be transformed into each other will be scaled to 
  //this size.
  private int width;
  private int height;

  //The first triangulated image.
  //private TriangulatedImage t1;

  //The second triangulated image.
  //private TriangulatedImage t2;

  private TriangulatedImage ts[];

  //This is used for generating/storing the intermediate images.
  private BufferedImage mix;

  //A variable which is increased stepwise from 0 to 1. It is needed
  //for the computation of the convex combinations.
  private double alpha;

  //The change of alpha in each step: deltAlpha = 1.0/steps
  private double deltaAlpha;

  private int[][] triangles;

  private Infinite inf;

  private int radius;
  private int segments;
  private int segment;
  private int steps;
  private int delay;


  /**
   * Constructor
   *
   * @param bid    The window in which the transformation is shown.
   */
  MorphingCandS(BufferedImageDrawer bid, int radius, int segments, int steps)
  {
    inf = new Infinite(radius);

    this.radius = radius;
    this.segments = segments;
    this.steps = steps;
    this.delay = delay;

    segment = 0;

    ts = new TriangulatedImage[16];

    buffid = bid;

    width = 500;
    height = 250;

    deltaAlpha = 1.0 / steps;

    alpha = 0;

    triangles = new int[16][3];

    triangles[0][0] = 0;
    triangles[0][1] = 1;
    triangles[0][2] = 8;

    triangles[1][0] = 1;
    triangles[1][1] = 2;
    triangles[1][2] = 9;

    triangles[2][0] = 9;
    triangles[2][1] = 2;
    triangles[2][2] = 3;

    triangles[3][0] = 10;
    triangles[3][1] = 3;
    triangles[3][2] = 4;

    triangles[4][0] = 5;
    triangles[4][1] = 10;
    triangles[4][2] = 4;

    triangles[5][0] = 6;
    triangles[5][1] = 11;
    triangles[5][2] = 5;

    triangles[6][0] = 7;
    triangles[6][1] = 11;
    triangles[6][2] = 6;

    triangles[7][0] = 0;
    triangles[7][1] = 8;
    triangles[7][2] = 7;

    triangles[8][0] = 8;
    triangles[8][1] = 1;
    triangles[8][2] = 12;

    triangles[9][0] = 1;
    triangles[9][1] = 9;
    triangles[9][2] = 12;

    triangles[10][0] = 12;
    triangles[10][1] = 9;
    triangles[10][2] = 3;

    triangles[11][0] = 12;
    triangles[11][1] = 3;
    triangles[11][2] = 10;

    triangles[12][0] = 12;
    triangles[12][1] = 10;
    triangles[12][2] = 5;

    triangles[13][0] = 11;
    triangles[13][1] = 12;
    triangles[13][2] = 5;

    triangles[14][0] = 7;
    triangles[14][1] = 12;
    triangles[14][2] = 11;

    triangles[15][0] = 7;
    triangles[15][1] = 8;
    triangles[15][2] = 12;

    Image loadedImage;

    for (int i = 0; i < 16; i++)
    {
      ts[i] = new TriangulatedImage();

      ts[i].bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

      Graphics2D g2dt = ts[i].bi.createGraphics();

      loadedImage = new javax.swing.ImageIcon("images/" + (i + 1) + ".jpg").getImage();
      g2dt.drawImage(loadedImage, 0, 0, null);

      ts[i].tPoints = new Point2D[13];

      ts[i].tPoints[0] = new Point2D.Double(0, 0);
      ts[i].tPoints[1] = new Point2D.Double(250, 0);
      ts[i].tPoints[2] = new Point2D.Double(499, 0);
      ts[i].tPoints[3] = new Point2D.Double(499, 125);
      ts[i].tPoints[4] = new Point2D.Double(499, 249);
      ts[i].tPoints[5] = new Point2D.Double(250, 249);
      ts[i].tPoints[6] = new Point2D.Double(0, 249);
      ts[i].tPoints[7] = new Point2D.Double(0, 125);

      //ts[i].triangles = triangles;
    }

    ts[0].tPoints[8] = new Point2D.Double(57, 76);
    ts[0].tPoints[9] = new Point2D.Double(187, 76);
    //ts[0].tPoints[10] = new Point2D.Double(50, 240);
    //ts[0].tPoints[11] = new Point2D.Double(285, 240);
    ts[0].tPoints[10] = new Point2D.Double(187, 206);
    ts[0].tPoints[11] = new Point2D.Double(57, 206);
    ts[0].tPoints[12] = new Point2D.Double(123, 142);

    ts[1].tPoints[8] = new Point2D.Double(76, 62);
    ts[1].tPoints[9] = new Point2D.Double(201, 95);
    //ts[1].tPoints[10] = new Point2D.Double(125, 160);
    //ts[1].tPoints[11] = new Point2D.Double(355, 160);
    ts[1].tPoints[10] = new Point2D.Double(168, 220);
    ts[1].tPoints[11] = new Point2D.Double(42, 187);
    ts[1].tPoints[12] = new Point2D.Double(123, 142);

    ts[2].tPoints[8] = new Point2D.Double(40, 130);
    ts[2].tPoints[9] = new Point2D.Double(270, 130);
    //ts[2].tPoints[10] = new Point2D.Double(40, 245);
    //ts[2].tPoints[11] = new Point2D.Double(270, 245);
    ts[2].tPoints[10] = new Point2D.Double(270, 245);
    ts[2].tPoints[11] = new Point2D.Double(40, 245);
    ts[2].tPoints[12] = new Point2D.Double(115, 57);

    ts[3].tPoints[8] = new Point2D.Double(210, 50);
    ts[3].tPoints[9] = new Point2D.Double(495, 50);
    //ts[3].tPoints[10] = new Point2D.Double(210, 240);
    //ts[3].tPoints[11] = new Point2D.Double(495, 240);
    ts[3].tPoints[10] = new Point2D.Double(495, 240);
    ts[3].tPoints[11] = new Point2D.Double(210, 240);
    ts[3].tPoints[12] = new Point2D.Double(147, 75);

    ts[4].tPoints[8] = new Point2D.Double(100, 50);
    ts[4].tPoints[9] = new Point2D.Double(395, 50);
    //ts[4].tPoints[10] = new Point2D.Double(100, 200);
    //ts[4].tPoints[11] = new Point2D.Double(395, 200);
    ts[4].tPoints[10] = new Point2D.Double(395, 200);
    ts[4].tPoints[11] = new Point2D.Double(100, 200);
    ts[4].tPoints[12] = new Point2D.Double(147, 75);

    ts[5].tPoints[8] = new Point2D.Double(160, 70);
    ts[5].tPoints[9] = new Point2D.Double(285, 70);
    //ts[5].tPoints[10] = new Point2D.Double(160, 200);
    //ts[5].tPoints[11] = new Point2D.Double(285, 200);
    ts[5].tPoints[10] = new Point2D.Double(285, 200);
    ts[5].tPoints[11] = new Point2D.Double(160, 200);
    ts[5].tPoints[12] = new Point2D.Double(62, 65);

    ts[6].tPoints[8] = new Point2D.Double(227, 8);
    ts[6].tPoints[9] = new Point2D.Double(320, 8);
    //ts[6].tPoints[10] = new Point2D.Double(227, 229);
    //ts[6].tPoints[11] = new Point2D.Double(320, 229);
    ts[6].tPoints[10] = new Point2D.Double(320, 229);
    ts[6].tPoints[11] = new Point2D.Double(227, 229);
    ts[6].tPoints[12] = new Point2D.Double(46, 110);

    ts[7].tPoints[8] = new Point2D.Double(235, 88);
    ts[7].tPoints[9] = new Point2D.Double(450, 88);
    //ts[7].tPoints[10] = new Point2D.Double(235, 230);
    //ts[7].tPoints[11] = new Point2D.Double(450, 230);
    ts[7].tPoints[10] = new Point2D.Double(450, 230);
    ts[7].tPoints[11] = new Point2D.Double(235, 230);
    ts[7].tPoints[12] = new Point2D.Double(107, 71);

    ts[8].tPoints[8] = new Point2D.Double(180, 10);
    ts[8].tPoints[9] = new Point2D.Double(360, 10);
    //ts[8].tPoints[10] = new Point2D.Double(180, 200);
    //ts[8].tPoints[11] = new Point2D.Double(360, 200);
    ts[8].tPoints[10] = new Point2D.Double(360, 200);
    ts[8].tPoints[11] = new Point2D.Double(180, 200);
    ts[8].tPoints[12] = new Point2D.Double(90, 95);

    ts[9].tPoints[8] = new Point2D.Double(70, 60);
    ts[9].tPoints[9] = new Point2D.Double(460, 60);
    //ts[9].tPoints[10] = new Point2D.Double(70, 195);
    //ts[9].tPoints[11] = new Point2D.Double(460, 195);
    ts[9].tPoints[10] = new Point2D.Double(460, 195);
    ts[9].tPoints[11] = new Point2D.Double(70, 195);
    ts[9].tPoints[12] = new Point2D.Double(195, 67);

    ts[10].tPoints[8] = new Point2D.Double(37, 8);
    ts[10].tPoints[9] = new Point2D.Double(435, 8);
    //ts[10].tPoints[10] = new Point2D.Double(37, 205);
    //ts[10].tPoints[11] = new Point2D.Double(435, 205);
    ts[10].tPoints[10] = new Point2D.Double(435, 205);
    ts[10].tPoints[11] = new Point2D.Double(37, 205);
    ts[10].tPoints[12] = new Point2D.Double(199, 123);

    ts[11].tPoints[8] = new Point2D.Double(45, 65);
    ts[11].tPoints[9] = new Point2D.Double(480, 65);
    //ts[11].tPoints[10] = new Point2D.Double(45, 230);
    //ts[11].tPoints[11] = new Point2D.Double(80, 230);
    ts[11].tPoints[10] = new Point2D.Double(160, 230);
    ts[11].tPoints[11] = new Point2D.Double(45, 230);
    ts[11].tPoints[12] = new Point2D.Double(217, 82);

    ts[12].tPoints[8] = new Point2D.Double(230, 10);
    ts[12].tPoints[9] = new Point2D.Double(410, 10);
    //ts[12].tPoints[10] = new Point2D.Double(230, 160);
    //ts[12].tPoints[11] = new Point2D.Double(410, 160);
    ts[12].tPoints[10] = new Point2D.Double(410, 160);
    ts[12].tPoints[11] = new Point2D.Double(230, 160);
    ts[12].tPoints[12] = new Point2D.Double(90, 75);

    ts[13].tPoints[8] = new Point2D.Double(10, 5);
    ts[13].tPoints[9] = new Point2D.Double(350, 5);
    //ts[13].tPoints[10] = new Point2D.Double(10, 145);
    //ts[13].tPoints[11] = new Point2D.Double(350, 145);
    ts[13].tPoints[10] = new Point2D.Double(350, 145);
    ts[13].tPoints[11] = new Point2D.Double(10, 145);
    ts[13].tPoints[12] = new Point2D.Double(170, 70);

    ts[14].tPoints[8] = new Point2D.Double(120, 45);
    ts[14].tPoints[9] = new Point2D.Double(470, 45);
    //ts[14].tPoints[10] = new Point2D.Double(120, 210);
    //ts[14].tPoints[11] = new Point2D.Double(470, 210);
    ts[14].tPoints[10] = new Point2D.Double(470, 210);
    ts[14].tPoints[11] = new Point2D.Double(120, 210);
    ts[14].tPoints[12] = new Point2D.Double(175, 82);

    ts[15].tPoints[8] = new Point2D.Double(170, 110);
    ts[15].tPoints[9] = new Point2D.Double(330, 110);
    //ts[15].tPoints[10] = new Point2D.Double(170, 240);
    //ts[15].tPoints[11] = new Point2D.Double(330, 240);
    ts[15].tPoints[10] = new Point2D.Double(330, 240);
    ts[15].tPoints[11] = new Point2D.Double(170, 240);
    ts[15].tPoints[12] = new Point2D.Double(80, 65);

    for (int i = 0; i < 16; i++) ts[i].triangles = triangles;
  }


  //This method is called in regular intervals. This method computes
  //the updated image/frame and calls the repaint method to draw the
  //updated image on the window.
  public void run()
  {
    int x, y, x1, y1, x2, y2, jumpSeg, a, b;

    //Since this method is called arbitrarily often, interpolation must only
    //be carred out while alpha is between 0 and 1.
    if (alpha >= 0 && alpha <= 1)
    {
      jumpSeg = inf.coords / segments;

      a = jumpSeg * segment;

      if (segment == 14)
        b = 0;
      else
        b = jumpSeg * (segment + 1);

      x1 = (int)inf.pathX.get(a);
      y1 = (int)inf.pathY.get(a);

      x2 = (int)inf.pathX.get(b);
      y2 = (int)inf.pathY.get(b);

      x = (int)((1 - alpha) * x1 + alpha * x2);
      y = (int)((1 - alpha) * y1 + alpha * y2);

      if (segment == 14)
        mix = ts[segment].mixWith(ts[0], alpha);
      else
        mix = ts[segment].mixWith(ts[segment + 1], alpha);

      //Draw the interpolated image on the BufferedImage.
      buffid.g2dbi.drawImage(mix, x, y, null);

      //x = (1 - alpha) * ax + alpha * bx;
      //y = (1 - alpha) * ay + alpha * by;

      //Call the method for updating the window.
      buffid.repaint();
    }
    else if (segment < 14)
    {
      alpha = 0.0;

      segment++;
    }
    else
    {
      alpha = 0.0;

      segment = 0;
    }

    //Increment alpha.
    alpha += deltaAlpha;

  }


  public static void main(String[] argv)
  {

    if (argv.length < 3) return;

    int radius = Integer.valueOf(argv[0]);
    int segments = 16;
    int steps = Integer.valueOf(argv[1]);
    int delay = Integer.valueOf(argv[2]);

    //Width of the window.
    int width = 1366;
    //Height of the window.
    int height = 768;

    //The BufferedImage to be drawn in the window.
    BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

    //The window in which everything is drawn.
    BufferedImageDrawer bid = new BufferedImageDrawer(bi, width, height);
    bid.setTitle("cgwork2");

    //The TimerTask in which the repeated computations for drawing take place.
    MorphingCandS mcs = new MorphingCandS(bid, radius, segments, steps);

    Timer t = new Timer();
    t.scheduleAtFixedRate(mcs, 0, delay);

  }

}

