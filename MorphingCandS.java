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

  //The number of steps (frames) for the transformation.
  private int steps;

  //The first triangulated image.
  private TriangulatedImage t1;

  //The second triangulated image.
  private TriangulatedImage t2;

  //This is used for generating/storing the intermediate images.
  private BufferedImage mix;

  //A variable which is increased stepwise from 0 to 1. It is needed
  //for the computation of the convex combinations.
  private double alpha;

  //The change of alpha in each step: deltAlpha = 1.0/steps
  private double deltaAlpha;

  private int[][] triangles;


  /**
   * Constructor
   *
   * @param bid    The window in which the transformation is shown.
   */
  MorphingCandS(BufferedImageDrawer bid)
  {
    buffid = bid;

    width = 500;
    height = 250;

    steps = 100;

    deltaAlpha = 1.0/steps;

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

    //This object is used for loading the two images.
    Image loadedImage;

    //Generating the first triangulated image:
    t1 = new TriangulatedImage();

    //Define the size.
    t1.bi = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);

    //Generate the Graphics2D object.
    Graphics2D g2dt1 = t1.bi.createGraphics();

    //Load the image and draw it on the corresponding BufferedImage.
    loadedImage = new javax.swing.ImageIcon("images/1.jpg").getImage();
    g2dt1.drawImage(loadedImage,0,0,null);

    //Definition of the points for the triangulation.
    t1.tPoints = new Point2D[13];

    t1.tPoints[0] = new Point2D.Double(0, 0);
    t1.tPoints[1] = new Point2D.Double(250, 0);
    t1.tPoints[2] = new Point2D.Double(499, 0);
    t1.tPoints[3] = new Point2D.Double(499, 125);
    t1.tPoints[4] = new Point2D.Double(499, 249);
    t1.tPoints[5] = new Point2D.Double(250, 249);
    t1.tPoints[6] = new Point2D.Double(0, 249);
    t1.tPoints[7] = new Point2D.Double(0, 125);
    t1.tPoints[8] = new Point2D.Double(50, 10);
    t1.tPoints[9] = new Point2D.Double(285, 10);
    t1.tPoints[10] = new Point2D.Double(50, 240);
    t1.tPoints[11] = new Point2D.Double(285, 240);
    t1.tPoints[12] = new Point2D.Double(165, 150);

    //Definition of the triangles.
    t1.triangles = triangles;

    //The same for the second image.
    t2 = new TriangulatedImage();

    t2.bi = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
    Graphics2D g2dt2 = t2.bi.createGraphics();

    loadedImage = new javax.swing.ImageIcon("images/2.jpg").getImage();

    g2dt2.drawImage(loadedImage,0,0,null);

    t2.tPoints = new Point2D[13];

    t2.tPoints[0] = new Point2D.Double(0, 0);
    t2.tPoints[1] = new Point2D.Double(250, 0);
    t2.tPoints[2] = new Point2D.Double(499, 0);
    t2.tPoints[3] = new Point2D.Double(499, 125);
    t2.tPoints[4] = new Point2D.Double(499, 249);
    t2.tPoints[5] = new Point2D.Double(250, 249);
    t2.tPoints[6] = new Point2D.Double(0, 249);
    t2.tPoints[7] = new Point2D.Double(0, 125);
    t2.tPoints[8] = new Point2D.Double(125, 25);
    t2.tPoints[9] = new Point2D.Double(355, 25);
    t2.tPoints[10] = new Point2D.Double(125, 160);
    t2.tPoints[11] = new Point2D.Double(355, 160);
    t2.tPoints[12] = new Point2D.Double(240, 100);


    //The indexing for the triangles must be the same as in the
    //the first image.
    t2.triangles = triangles;

  }


  //This method is called in regular intervals. This method computes
  //the updated image/frame and calls the repaint method to draw the
  //updated image on the window.
  public void run()
  {

    //Since this method is called arbitrarily often, interpolation must only
    //be carred out while alpha is between 0 and 1.
    if (alpha>=0 && alpha<=1)
    {
      //Generate the interpolated image.
      mix = t1.mixWith(t2,alpha);

      //Draw the interpolated image on the BufferedImage.
      buffid.g2dbi.drawImage(mix,50,50,null);

      //Call the method for updating the window.
      buffid.repaint();
    }

    //Increment alpha.
    alpha = alpha+deltaAlpha;

  }


  public static void main(String[] argv)
  {

    //Width of the window.
    int width = 600;
    //Height of the window.
    int height = 400;

    //Specifies (in milliseconds) when the frame should be updated.
    int delay = 50;

    //The BufferedImage to be drawn in the window.
    BufferedImage bi = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);


    //The window in which everything is drawn.
    BufferedImageDrawer bid = new BufferedImageDrawer(bi,width,height);
    bid.setTitle("Transforming shape and colour");

    //The TimerTask in which the repeated computations for drawing take place.
    MorphingCandS mcs = new MorphingCandS(bid);


    Timer t = new Timer();
    t.scheduleAtFixedRate(mcs,0,delay);

  }

}

