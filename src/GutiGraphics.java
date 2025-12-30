//11:55 PM 10/8/2004 u 1:42 PM 12/26/2004 u 3:47 PM 12/26/2004
//class 'GutiGraphics' that draws a 'Guti(bangla)' with a specifired color for Ludu game

import java.awt.*;

public class GutiGraphics implements java.io.Serializable
{
   private int x, y; //x and y co-ordinate
   private int type;  //e.g. Constants.RED etc.
   private Color colors[] = new Color[ 5 ];
   private final int colorDecrement = 35;
   public GutiGraphics()     //                  (tag)
   {
      type = Constants.RED;
      build();
   }
   public GutiGraphics( int t )      //                 (tag)
   {
      if( t >= 0 && t <= 3 )
      {
         type = t;
      }
      else
         type = Constants.RED;
      build();
   }
   public void setType( int t )       //                (tag)
   {
      if( t >= 0 && t <= 3 )
      {
         type = t;
      }
   }
   public void build()         //              (tag)
   {
      int r, g, b;
      if( type == Constants.RED )
      {
         r = 225;
         g = 0;
         b = 0;
         for( int i = 0; i < 5; i ++ )
         {
            if( r != 0 )
            {
               r -= colorDecrement;
            }
            if( g != 0 )
            {
               g -= colorDecrement;
            }
            if( b != 0 )
            {
               b -= colorDecrement;
            }
            colors[ i ] = new Color( r, g, b );
         }
      }
      else if( type == Constants.BLUE )
      {
         r = 0;
         g = 0;
         b = 225;
         for( int i = 0; i < 5; i ++ )
         {
            if( r != 0 )
            {
               r -= colorDecrement;
            }
            if( g != 0 )
            {
               g -= colorDecrement;
            }
            if( b != 0 )
            {
               b -= colorDecrement;
            }
            colors[ i ] = new Color( r, g, b );
         }
      }
      else if( type == Constants.GREEN )
      {
         r = 0;
         g = 225;
         b = 0;
         for( int i = 0; i < 5; i ++ )
         {
            if( r != 0 )
            {
               r -= colorDecrement;
            }
            if( g != 0 )
            {
               g -= colorDecrement;
            }
            if( b != 0 )
            {
               b -= colorDecrement;
            }
            colors[ i ] = new Color( r, g, b );
         }
      }
      else if( type == Constants.YELLOW )
      {
         r = 225;
         g = 225;
         b = 0;
         for( int i = 0; i < 5; i ++ )
         {
            if( r != 0 )
            {
               r -= colorDecrement;
            }
            if( g != 0 )
            {
               g -= colorDecrement;
            }
            if( b != 0 )
            {
               b -= colorDecrement;
            }
            colors[ i ] = new Color( r, g, b );
         }
      }
   }
   public void drawGuti( Graphics g, int x2, int y2 )     //                  (tag)
   {
      Graphics2D g2d = ( Graphics2D )g;
      g2d.setRenderingHint( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON );
      int x3, y3, w, h;
      x3 = 10; //x-coor.
      y3 = 12; //y-coor.
      x3+=x2;
      y3+=y2;
      w = 30; //width
      h = 28; //height
      for( int i = 4; i >= 0 ; i-- )
      {
         g2d.setColor( colors[ i ] );
         g2d.fillOval( x3, y3, w, h );
         y3--;
      }
   }
}