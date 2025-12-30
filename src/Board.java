//4:09 PM 10/9/2004 u 6:41 PM 11/13/2004 u 4:17 PM 12/26/2004
//Class Board, responsible for drawing board grapchics for ludu games
//u 9:04 PM 9/12/2005

import java.awt.*;
import java.awt.image.*;
import java.awt.geom.*;
import javax.swing.*;
import java.awt.Color.*;

public class Board implements java.io.Serializable
{
   private boolean debugStatus = false;
   private Position positions[] = new Position[ 98 ];
   private GutiContainer containers[] = new GutiContainer[ 98 ];
   public Board()   // (tag)
   {
      int i;
      for( i = 0; i <= 97; i++ )
      {
         positions[ i ] = new Position( i );
         containers[ i ] = new GutiContainer( i );
      }

      //----------------------Co-ordinate data
      int a;
      a = 1;
      positions[ a ].setX( 240 );
      positions[ a ].setY( 520 );
      a = 2;
      positions[ a ].setX( 240 );
      positions[ a ].setY( 480 );
      a = 3;
      positions[ a ].setX( 240 );
      positions[ a ].setY( 440 );
      a = 4;
      positions[ a ].setX( 240 );
      positions[ a ].setY( 400 );
      a = 5;
      positions[ a ].setX( 240 );
      positions[ a ].setY( 360 );
      a = 6;
      positions[ a ].setX( 200 );
      positions[ a ].setY( 320 );
      a = 7;
      positions[ a ].setX( 160 );
      positions[ a ].setY( 320 );
      a = 8;
      positions[ a ].setX( 120 );
      positions[ a ].setY( 320 );
      a = 9;
      positions[ a ].setX( 80 );
      positions[ a ].setY( 320 );
      a = 10;
      positions[ a ].setX( 40 );
      positions[ a ].setY( 320 );
      a = 11;
      positions[ a ].setX( 0 );
      positions[ a ].setY( 320 );
      a = 12;
      positions[ a ].setX( 0 );
      positions[ a ].setY( 280 );
      a = 13;
      positions[ a ].setX( 0 );
      positions[ a ].setY( 240 );
      a = 14;
      positions[ a ].setX( 40 );
      positions[ a ].setY( 240 );
      a = 15;
      positions[ a ].setX( 80 );
      positions[ a ].setY( 240 );
      a = 16;
      positions[ a ].setX( 120 );
      positions[ a ].setY( 240 );
      a = 17;
      positions[ a ].setX( 160 );
      positions[ a ].setY( 240 );
      a = 18;
      positions[ a ].setX( 200 );
      positions[ a ].setY( 240 );
      a = 19;
      positions[ a ].setX( 240 );
      positions[ a ].setY( 200 );
      a = 20;
      positions[ a ].setX( 240 );
      positions[ a ].setY( 160 );
      a = 21;
      positions[ a ].setX( 240 );
      positions[ a ].setY( 120 );
      a = 22;
      positions[ a ].setX( 240 );
      positions[ a ].setY( 80 );
      a = 23;
      positions[ a ].setX( 240 );
      positions[ a ].setY( 40 );
      a = 24;
      positions[ a ].setX( 240 );
      positions[ a ].setY( 0 );
      a = 25;
      positions[ a ].setX( 280 );
      positions[ a ].setY( 0 );
      a = 26;
      positions[ a ].setX( 320 );
      positions[ a ].setY( 0 );
      a = 27;
      positions[ a ].setX( 320 );
      positions[ a ].setY( 40 );
      a = 28;
      positions[ a ].setX( 320 );
      positions[ a ].setY( 80 );
      a = 29;
      positions[ a ].setX( 320 );
      positions[ a ].setY( 120 );
      a = 30;
      positions[ a ].setX( 320 );
      positions[ a ].setY( 160 );
      a = 31;
      positions[ a ].setX( 320 );
      positions[ a ].setY( 200 );
      a = 32;
      positions[ a ].setX( 360 );
      positions[ a ].setY( 240 );
      a = 33;
      positions[ a ].setX( 400 );
      positions[ a ].setY( 240 );
      a = 34;
      positions[ a ].setX( 440 );
      positions[ a ].setY( 240 );
      a = 35;
      positions[ a ].setX( 480 );
      positions[ a ].setY( 240 );
      a = 36;
      positions[ a ].setX( 520 );
      positions[ a ].setY( 240 );
      a = 37;
      positions[ a ].setX( 560 );
      positions[ a ].setY( 240 );
      a = 38;
      positions[ a ].setX( 560 );
      positions[ a ].setY( 280 );
      a = 39;
      positions[ a ].setX( 560 );
      positions[ a ].setY( 320 );
      a = 40;
      positions[ a ].setX( 520 );
      positions[ a ].setY( 320 );
      a = 41;
      positions[ a ].setX( 480 );
      positions[ a ].setY( 320 );
      a = 42;
      positions[ a ].setX( 440 );
      positions[ a ].setY( 320 );
      a = 43;
      positions[ a ].setX( 400 );
      positions[ a ].setY( 320 );
      a = 44;
      positions[ a ].setX( 360 );
      positions[ a ].setY( 320 );
      a = 45;
      positions[ a ].setX( 320 );
      positions[ a ].setY( 360 );
      a = 46;
      positions[ a ].setX( 320 );
      positions[ a ].setY( 400 );
      a = 47;
      positions[ a ].setX( 320 );
      positions[ a ].setY( 440 );
      a = 48;
      positions[ a ].setX( 320 );
      positions[ a ].setY( 480 );
      a = 49;
      positions[ a ].setX( 320 );
      positions[ a ].setY( 520 );
      a = 50;
      positions[ a ].setX( 320 );
      positions[ a ].setY( 560 );
      a = 51;
      positions[ a ].setX( 280 );
      positions[ a ].setY( 560 );
      a = 52;
      positions[ a ].setX( 240 );
      positions[ a ].setY( 560 );
      a = 53;
      positions[ a ].setX( 280 );
      positions[ a ].setY( 520 );
      a = 54;
      positions[ a ].setX( 280 );
      positions[ a ].setY( 480 );
      a = 55;
      positions[ a ].setX( 280 );
      positions[ a ].setY( 440 );
      a = 56;
      positions[ a ].setX( 280 );
      positions[ a ].setY( 400 );
      a = 57;
      positions[ a ].setX( 280 );
      positions[ a ].setY( 360 );
      a = 58;
      positions[ a ].setX( 520 );
      positions[ a ].setY( 280 );
      a = 59;
      positions[ a ].setX( 480 );
      positions[ a ].setY( 280 );
      a = 60;
      positions[ a ].setX( 440 );
      positions[ a ].setY( 280 );
      a = 61;
      positions[ a ].setX( 400 );
      positions[ a ].setY( 280 );
      a = 62;
      positions[ a ].setX( 360 );
      positions[ a ].setY( 280 );
      a = 63;
      positions[ a ].setX( 280 );
      positions[ a ].setY( 40 );
      a = 64;
      positions[ a ].setX( 280 );
      positions[ a ].setY( 80 );
      a = 65;
      positions[ a ].setX( 280 );
      positions[ a ].setY( 120 );
      a = 66;
      positions[ a ].setX( 280 );
      positions[ a ].setY( 160 );
      a = 67;
      positions[ a ].setX( 280 );
      positions[ a ].setY( 200 );
      a = 68;
      positions[ a ].setX( 40 );
      positions[ a ].setY( 280 );
      a = 69;
      positions[ a ].setX( 80 );
      positions[ a ].setY( 280 );
      a = 70;
      positions[ a ].setX( 120 );
      positions[ a ].setY( 280 );
      a = 71;
      positions[ a ].setX( 160 );
      positions[ a ].setY( 280 );
      a = 72;
      positions[ a ].setX( 200 );
      positions[ a ].setY( 280 );
      a = 73;
      positions[ a ].setX( 240 );
      positions[ a ].setY( 240 );
      a = 74;
      positions[ a ].setX( 0 );
      positions[ a ].setY( 360 );
      a = 75;
      positions[ a ].setX( 360 );
      positions[ a ].setY( 360 );
      a = 76;
      positions[ a ].setX( 360 );
      positions[ a ].setY( 0 );
      a = 77;
      positions[ a ].setX( 0 );
      positions[ a ].setY( 0 );
      a = 78;
      positions[ a ].setX( 80 );
      positions[ a ].setY( 440 );
      a = 79;
      positions[ a ].setX( 120 );
      positions[ a ].setY( 440 );
      a = 80;
      positions[ a ].setX( 80 );
      positions[ a ].setY( 480 );
      a = 81;
      positions[ a ].setX( 120 );
      positions[ a ].setY( 480 );
      a = 82;
      positions[ a ].setX( 440 );
      positions[ a ].setY( 440 );
      a = 83;
      positions[ a ].setX( 480 );
      positions[ a ].setY( 440 );
      a = 84;
      positions[ a ].setX( 440 );
      positions[ a ].setY( 480 );
      a = 85;
      positions[ a ].setX( 480 );
      positions[ a ].setY( 480 );
      a = 86;
      positions[ a ].setX( 440 );
      positions[ a ].setY( 80 );
      a = 87;
      positions[ a ].setX( 480 );
      positions[ a ].setY( 80 );
      a = 88;
      positions[ a ].setX( 440 );
      positions[ a ].setY( 120 );
      a = 89;
      positions[ a ].setX( 480 );
      positions[ a ].setY( 120 );
      a = 90;
      positions[ a ].setX( 80 );
      positions[ a ].setY( 80 );
      a = 91;
      positions[ a ].setX( 120 );
      positions[ a ].setY( 80 );
      a = 92;
      positions[ a ].setX( 80 );
      positions[ a ].setY( 120 );
      a = 93;
      positions[ a ].setX( 120 );
      positions[ a ].setY( 120 );
      a = 94;
      positions[ a ].setX( 280 );
      positions[ a ].setY( 320 );
      a = 95;
      positions[ a ].setX( 320 );
      positions[ a ].setY( 280 );
      a = 96;
      positions[ a ].setX( 280 );
      positions[ a ].setY( 240 );
      a = 97;
      positions[ a ].setX( 240 );
      positions[ a ].setY( 280 );

      //----------------------Width-Height data

      for( i = 1; i <= 72; i++ )
      {
         positions[ i ].setW( 40 );
         positions[ i ].setH( 40 );
      }
      positions[ 73 ].setW( 120 );
      positions[ 73 ].setH( 120 );
      for( i = 74; i <= 77; i++ )
      {
         positions[ i ].setW( 240 );
         positions[ i ].setH( 240 );
      }
      for( i = 78; i <= 97; i++ )
      {
         positions[ i ].setW( 40 );
         positions[ i ].setH( 40 );
      }

      //----------------------Color data

      Color c = new Color( 128, 128, 255 ); //Light blue
      for( i = 1; i <= 52; i++ )
      {
         positions[ i ].setColor( c );
      }
      c = new Color( 255, 0, 0 );  //red
      positions[ 1 ].setColor( c );
      positions[ 74 ].setColor( c );
      for( i = 53; i <= 57; i++ )
      {
         positions[ i ].setColor( c );
      }
      c = new Color( 0, 0, 255 );  //blue
      positions[ 40 ].setColor( c );
      positions[ 75 ].setColor( c );
      for( i = 58; i <= 62; i++ )
      {
         positions[ i ].setColor( c );
      }
      c = new Color( 0, 255, 0 );  //green
      positions[ 27 ].setColor( c );
      positions[ 76 ].setColor( c );
      for( i = 63; i <= 67; i++ )
      {
         positions[ i ].setColor( c );
      }
      c = new Color( 255, 255, 0 );  //yellow
      positions[ 14 ].setColor( c );
      positions[ 77 ].setColor( c );
      for( i = 68; i <= 72; i++ )
      {
         positions[ i ].setColor( c );
      }
      c = new Color( 255, 128, 64 );  //yellow
      positions[ 73 ].setColor( c );

      //----------------------Draw Confirmation data

      for( i = 1; i <= 77; i++ )
      {
         positions[ i ].setWhetherItWillBeDrawn( true );
      }

      //----------------------Next Pointer data

      for( i = 1; i <= 97; i++ ) //resetting all
      {
         positions[ i ].setNext( -1 );
      }

      for( i = 1; i <= 51; i++ ) 
      {
         positions[ i ].setNext( i + 1 );
      }
         positions[ 52 ].setNext( 1 );

         positions[ 51 ].setNext( 53, Constants.RED );
         positions[ 38 ].setNext( 58, Constants.BLUE );
         positions[ 25 ].setNext( 63, Constants.GREEN );
         positions[ 12 ].setNext( 68, Constants.YELLOW );

      for( i = 53; i <= 56; i++ )
      {
         positions[ i ].setNext( i + 1, Constants.RED );
      }
      positions[ 57 ].setNext( 94, Constants.RED );
      for( i = 58; i <= 61; i++ )
      {
         positions[ i ].setNext( i + 1, Constants.BLUE );
      }
      positions[ 62 ].setNext( 95, Constants.BLUE );
      for( i = 63; i <= 66; i++ )
      {
         positions[ i ].setNext( i + 1, Constants.GREEN );
      }
      positions[ 67 ].setNext( 96, Constants.GREEN );
      for( i = 68; i <= 71; i++ )
      {
         positions[ i ].setNext( i + 1, Constants.YELLOW );
      }
      positions[ 72 ].setNext( 97, Constants.YELLOW );
   }
   public BufferedImage getBoardImage()  // (tag)
   {
      BufferedImage bi = new BufferedImage( 600, 600, BufferedImage.TYPE_INT_RGB );
      Graphics2D g2d = bi.createGraphics();
      g2d.setRenderingHint( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON );
      int i, a, b, c, d;
      Color col;
      //--------------------Filling with colored rectangles
      for( i = 0; i <= 97; i++ )
      {
         if( positions[ i ].willItBeDrawn() )
         {
            col = positions[ i ].getColor();
            if( col == null )
               col = Color.black;
            if( i >= 73 && i <= 77 )
            {
               g2d.setPaint( new GradientPaint( (float)0.0, (float)0.0, col, (float)200.0, (float)200.0, Color.pink, true ) );
            }
            else
            {
               g2d.setColor( col );
            }
            a = positions[ i ].getX();
            b = positions[ i ].getY();
            c = positions[ i ].getW();
            d = positions[ i ].getH();
            g2d.fillRect( a, b, c, d );
         }
      }
      //--------------------drawing lines
      col = new Color( 0, 0, 0 );  //black
      g2d.setColor( col );
      g2d.drawLine( 0, 240, 600, 240);
      g2d.drawLine( 0, 360, 600, 360);
      g2d.drawLine( 240, 0, 240, 600);
      g2d.drawLine( 360, 0, 360, 600);

      g2d.drawLine( 0, 280, 240, 280);
      g2d.drawLine( 0, 320, 240, 320);
      g2d.drawLine( 360, 280, 600, 280);
      g2d.drawLine( 360, 320, 600, 320);
      g2d.drawLine( 280, 0, 280, 240);
      g2d.drawLine( 320, 0, 320, 240);
      g2d.drawLine( 280, 360, 280, 600);
      g2d.drawLine( 320, 360, 320, 600);

      g2d.drawLine( 40, 240, 40, 360);
      g2d.drawLine( 80, 240, 80, 360);
      g2d.drawLine( 120, 240, 120, 360);
      g2d.drawLine( 160, 240, 160, 360);
      g2d.drawLine( 200, 240, 200, 360);
      g2d.drawLine( 560, 240, 560, 360);
      g2d.drawLine( 400, 240, 400, 360);
      g2d.drawLine( 440, 240, 440, 360);
      g2d.drawLine( 480, 240, 480, 360);
      g2d.drawLine( 520, 240, 520, 360);
      g2d.drawLine( 560, 240, 560, 360);
      g2d.drawLine( 240, 40,360 ,40 );
      g2d.drawLine( 240, 80,360 , 80);
      g2d.drawLine( 240, 120,360 , 120);
      g2d.drawLine( 240, 160,360 , 160);
      g2d.drawLine( 240, 200,360 , 200);
      g2d.drawLine( 240, 400,360 , 400);
      g2d.drawLine( 240, 440,360 , 440);
      g2d.drawLine( 240, 480,360 , 480);
      g2d.drawLine( 240, 520,360 , 520);
      g2d.drawLine( 240, 560,360 , 560);

      //--------------------Filling with stars and drawing arrow

      col = new Color( 0, 64, 64 );  //cyan(may be)
      g2d.setColor( col );

      GeneralPath star = new GeneralPath();
      star.moveTo( 12, 5 );
      star.lineTo( 34, 28 );
      star.lineTo( 6, 26);
      star.lineTo( 27, 5);
      star.lineTo( 19, 38);
      star.closePath();

      GeneralPath arrow = new GeneralPath();
      arrow.moveTo( 20, 4 );
      arrow.lineTo( 26, 11 );
      arrow.lineTo( 22, 11);
      arrow.lineTo( 22, 30);
      arrow.lineTo( 27, 33);
      arrow.lineTo( 27, 37 );
      arrow.lineTo( 21, 34 );
      arrow.lineTo( 15, 37);
      arrow.lineTo( 15, 33);
      arrow.lineTo( 19, 31);
      arrow.lineTo( 19, 11);
      arrow.lineTo( 15, 11 );
      arrow.closePath();

      g2d.translate( 40, 240 );
      g2d.fill( star );
      g2d.translate( -40, -240 );
      g2d.translate( 40, 320 );
      g2d.fill( star );
      g2d.translate( -40, -320 );
      g2d.translate( 240, 40);
      g2d.fill( star );
      g2d.translate( -240, -40);
      g2d.translate( 320, 40);
      g2d.fill( star );
      g2d.translate( -320, -40);
      g2d.translate( 520, 240);
      g2d.fill( star );
      g2d.translate( -520, -240);
      g2d.translate( 520, 320);
      g2d.fill( star );
      g2d.translate( -520, -320);
      g2d.translate( 240, 520);
      g2d.fill( star );
      g2d.translate( -240, -520);
      g2d.translate( 320, 520);
      g2d.fill( star );
      g2d.translate( -320, -520);
      g2d.translate( 240, 480);
      g2d.fill( arrow );
      g2d.translate( -240, -480);

      return bi;
   }
   public GutiContainer getContainer( int index )     //                 (tag)
   {
      if( index >= 0 && index <= 97 )
      {
         return containers[ index ];
      }
      else
      {
         error( "Board- index:" + index  );
      }
      return null;
   }
   public Position getPosition( int index )     //                 (tag)
   {
      if( index >= 0 && index <= 97 )
      {
         return positions[ index ];
      }
      return null;
   }
   private void error( String msg )   //for debugging
   {
      if( debugStatus )
      {
         JOptionPane.showMessageDialog( null, "Error message: \n" + msg );
      }
   }
   private void debug( String msg )   //in debug function  command output is used for huge info
   {
      if( debugStatus )
         System.out.println( msg );
   }
}