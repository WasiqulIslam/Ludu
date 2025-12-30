//2:30 PM 10/10/2004 u 9:31 PM 10/23/2004
//This panel draws a die on its body(container) and has a size 0f 60 X 60 pixels

import java.awt.*;
import java.awt.color.*;

public class DiePanel extends MPanel implements java.io.Serializable
{
   private boolean isSelected = true;
   private int value;
   public DiePanel()   //                    (tag)
   {
      super( 60, 60 );
      isSelected = false;
   }
   public void paintComponent( Graphics g )            //           (tag)
   {
      super.paintComponent( g );
      if( value <= 0 || value >= 7 )
         return;
      Graphics2D g2d = ( Graphics2D )g;
      g2d.setRenderingHint( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON );
      g2d.setColor( new Color( 255, 255, 0 ) );
      g2d.fillRect( 0, 0, 60, 60 );
      g2d.setColor( new Color( 0, 0, 0 ) );
      g2d.setStroke( new BasicStroke( 3.0f ) );
      if( isSelected && ( value >= 1 && value <= 6 ) )
      {
         g2d.drawRect( 0, 0, 60, 60 );
      }
      g2d.setColor( Color.red );
      int x = 0, y = 0;
      if( value == 6 )
      {
         g2d.fillOval( 10 - x, 10 - y, 10, 10 );
         g2d.fillOval( 25 - x, 10 - y, 10, 10 );
         g2d.fillOval( 40 - x, 10- y, 10, 10 );
         g2d.fillOval( 10 - x, 40- y, 10, 10 );
         g2d.fillOval( 25 - x, 40- y, 10, 10 );
         g2d.fillOval( 40 - x, 40- y, 10, 10 );
      }
      else if( value == 5 )
      {
         g2d.fillOval( 10 - x, 10- y, 10, 10 );
         g2d.fillOval( 40 - x, 10- y, 10, 10 );
         g2d.fillOval( 25 - x, 25- y, 10, 10 );
         g2d.fillOval( 10 - x, 40- y, 10, 10 );
         g2d.fillOval( 40 - x, 40- y, 10, 10 );
      }
      else if( value == 4 )
      {
         g2d.fillOval( 10 - x, 10- y, 10, 10 );
         g2d.fillOval( 40 - x, 10- y, 10, 10 );
         g2d.fillOval( 10 - x, 40- y, 10, 10 );
         g2d.fillOval( 40 - x, 40- y, 10, 10 );
      }
      else if( value == 3 )
      {
         g2d.fillOval( 40 - x, 10- y, 10, 10 );
         g2d.fillOval( 25 - x, 25- y, 10, 10 );
         g2d.fillOval( 10 - x, 40- y, 10, 10 );
      }
      else if( value == 2 )
      {
         g2d.fillOval( 40 - x, 10- y, 10, 10 );
         g2d.fillOval( 10 - x, 40- y, 10, 10 );  
      }
      else if( value == 1 )
      {
         g2d.fillOval( 25 - x, 25- y, 10, 10 );
      }
   }
   public void setValue( int v )        // if the value is 6 then this die prints 6 dots in its body               (tag)
   {
      value = v;
      repaint();
   }
   public void clear()         //      for clearing all dots       (tag)
   {
      isSelected = false;
      value = -1;
      repaint();
   }
   public void select()
   {
      isSelected = true;
      repaint();
   }
   public void deselect()
   {
      isSelected = false;
      repaint();
   }
}