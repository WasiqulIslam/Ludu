//3:08 PM 10/10/2004 u 4:27 PM 11/9/2004
//Used to have buttons of same widths,( width of button can be set here )
//u 9:08 PM 9/12/2005

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MButton extends JButton implements java.io.Serializable
{
   private int w;
   public MButton( String caption )       //                (tag)
   {
      super( caption );
      setBackground( Color.blue );
      setForeground( Color.green );
      setRequestFocusEnabled( false );
   }
   public void setButtonWidth( int w1 )      //                 (tag)
   {
      w = w1;
   }
   public Dimension getPreferredSize()        //               (tag)
   {
      return new Dimension( w , ( int )( super.getPreferredSize().getHeight() ) );
   }
   public Dimension getMinimumSize()     //                  (tag)
   {
      return getPreferredSize();
   }
   public Dimension getMaximumSize()      //                 (tag)
   {
      return getPreferredSize();
   }
}