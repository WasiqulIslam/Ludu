package ludu;

//2:24 PM 10/10/2004
//Update started by Wasiqul Islam on 30/12/2025

import java.awt.*;
import javax.swing.*;

public class MPanel extends JPanel implements java.io.Serializable
{
   private int w, h; //width, height
   public MPanel( int w1, int h1 )     //                  (tag)
   {
      w = w1;
      h = h1;
      setBackground( new Color( 135, 103, 191 ) ); //purple
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