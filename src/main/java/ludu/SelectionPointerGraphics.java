package ludu;

//1:08 PM 10/10/2004 u 3:36 PM 31-Dec-2004 FRI
//Class that contains the graphics of a selection pointer and also draws it, single and double selection are available

import java.awt.*;
import java.awt.geom.*;

public class SelectionPointerGraphics implements java.io.Serializable
{
   private GeneralPath gps, gpd;  //GeneralPathSingle -- GeneralPathDouble
   public SelectionPointerGraphics()      //                 (tag)
   {
      gps = new GeneralPath();
      gps.moveTo( 35, 5);
      gps.lineTo( 23, 28);
      gps.lineTo( 36, 29);
      gps.lineTo( 20, 40);
      gps.lineTo( 4, 29);
      gps.lineTo( 16, 29);
      gps.lineTo( 4, 5);
      gps.closePath();
      gpd = new GeneralPath();
      gpd.moveTo( 35, 5);
      gpd.lineTo( 23, 28);
      gpd.lineTo( 36, 29);
      gpd.lineTo( 20, 40);
      gpd.lineTo( 4, 29);
      gpd.lineTo( 16, 29);
      gpd.lineTo( 4, 5);
      gpd.lineTo( 20, 14);
      gpd.closePath();
   }
   public void drawSelection( Graphics g, int x1, int y1, int type, int disp )      //                 (tag)
   {
      Graphics2D g2d = ( Graphics2D )g;
      g2d.setRenderingHint( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON );
      int x, y;
      x = x1;
      y = y1;
      if( type == Constants.SINGLE )
      {
         Color c = g2d.getColor();
         g2d.setColor( new Color( 255, 255, 255 ) );  //white
         g2d.translate( x , y + disp );
         g2d.fill( gps );
         g2d.translate( - x, - ( y + disp ) );
         g2d.setColor( c );
      }
      else if( type == Constants.DOUBLE )
      {
         Color c = g2d.getColor();
         g2d.setColor( new Color( 0, 0, 0 ) );  //black
         g2d.translate( x, y + disp );
         g2d.fill( gpd );
         g2d.translate( -x, -( y + disp ) );
         g2d.setColor( c );
      }
   }
}
