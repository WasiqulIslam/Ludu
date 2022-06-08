//2:24 PM 10/10/2004

import java.awt.*;
import javax.swing.*;

public class MPanel extends JPanel implements java.io.Serializable
{
   private int w, h; //width, height
   public MPanel( int w1, int h1 )     //                  (tag)
   {
      w = w1;
      h = h1;
      setBackground( new Color( 179, 117, 191 ) );
   }
   public Dimension getPreferredSize()    //                   (tag)
   {
      return new Dimension( w, h );
   }
   public Dimension getMinimumSize()    //                   (tag)
   {
      return getPreferredSize();
   }
   public Dimension getMaximumSize()      //                 (tag)
   {
      return getPreferredSize();
   }
}