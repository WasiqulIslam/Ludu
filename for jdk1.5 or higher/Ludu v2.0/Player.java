//3:40 PM 10/11/2004 u 4:40 PM 10/12/2004 bugsolved 11:38 PM 10/12/2004 u 9:30 PM 10/23/2004 u 6:43 PM 11/13/2004 u 5:09 PM 12/26/2004
//u 9:10 PM 9/12/2005

import javax.swing.*;

public class Player implements java.io.Serializable
{
   private boolean debugStatus = false;
   public static int numberOfPlayers;
   private int id;   //e.g. Constants.RED for simplicity. Note: player id and color may be different
   private int teamID;
   private String playerName;
   private String teamName;
   private int color;  //e.g. Constants.RED
   private Guti pieces[];
   private int gutiLocation[] = new int[ 4 ];
   private int gutiCount = -1;  //always guti count must be 4 for a player
   private boolean isDoubling[] = { false, false, false, false };
   private boolean canMoveFurther[] = { true, true, true, true }; //Used only in case of passing through double blocked pieces
   /*if piece[ 2 ] is doubling then: isDoubling[ 2 ] = true*/
   private int doublingWith[] = new int[ 4 ];
   /*if pieces[ 0 ] is doubling with pieces[ 3 ] then: doublingWith[ 0 ] = 3*/
   private boolean isRetired = false;  //if this player has all pieces at its destination i,e, player finishes his playing
   public Player( Player p )
   {
      id = p.getID();
      teamID = p.getTeamID();
      playerName = p.getPlayerName();
      teamName =  p.getTeamName();
      color = p.getColor();
      gutiCount = p.getGutiCount();
      gutiLocation[ 0 ] = p.getLocation( 0 );
      gutiLocation[ 1 ] = p.getLocation( 1 );
      gutiLocation[ 2 ] = p.getLocation( 2 );
      gutiLocation[ 3 ] = p.getLocation( 3 );
      pieces = new Guti[ 4 ];
      pieces[ 0 ] = new Guti( p.getGuti( 0 ) );
      pieces[ 1 ] = new Guti( p.getGuti( 1 ) );
      pieces[ 2 ] = new Guti( p.getGuti( 2 ) );
      pieces[ 3 ] = new Guti( p.getGuti( 3 ) );
      isDoubling[ 0 ] = p.isDoubling( 0 );
      isDoubling[ 1 ] = p.isDoubling( 1 );
      isDoubling[ 2 ] = p.isDoubling( 2 );
      isDoubling[ 3 ] = p.isDoubling( 3 );
      doublingWith[ 0 ] = p.doublingWith( 0 );
      doublingWith[ 1 ] = p.doublingWith( 1 );
      doublingWith[ 2 ] = p.doublingWith( 2 );
      doublingWith[ 3 ] = p.doublingWith( 3 );
      canMoveFurther[ 0 ] = p.isMovable( 0 );
      canMoveFurther[ 1 ] = p.isMovable( 1 );
      canMoveFurther[ 2 ] = p.isMovable( 2 );
      canMoveFurther[ 3 ] = p.isMovable( 3 );
      isRetired = p.isRetired();
   }
   public Player( int i, int ti, String pName, String tName )
   {
      id = i;
      teamID = ti;
      playerName = pName;
      teamName = tName;
      pieces = new Guti[ 4 ];
      pieces[ 0 ] = new Guti( i, 0, Constants.RED );
      pieces[ 1 ] = new Guti( i, 1, Constants.RED );
      pieces[ 2 ] = new Guti( i, 2, Constants.RED );
      pieces[ 3 ] = new Guti( i, 3, Constants.RED );
      isRetired = false;
   }
   public String getPlayerName()
   {
      return playerName;
   }
   public int getPlayerID()
   {
      return id;
   }
   public String getTeamName()
   {
      return teamName;
   }
   public int getID()
   {
      return id;
   }
   public int getTeamID()
   {
      return teamID;
   }
   public void setPlayerName( String pn )
   {
      playerName = pn;
   }
   public void setTeamName( String tn )
   {
      teamName = tn;
   }
   public void setTeamID( int i )
   {
      teamID = i;
   }
   public void setColor( int c )   //color eg. Constants.RED
   {
      if( c >= 0 && c<= 3 )
      {
         color = c;
         pieces[ 0 ].setType( c );
         pieces[ 1 ].setType( c );
         pieces[ 2 ].setType( c );
         pieces[ 3 ].setType( c );
      }
   }
   public int getColor()
   {
      return color;
   }
   public void setGutiCount( int i )
   {
      gutiCount = i;
   }
   public int getGutiCount()
   {
      return  gutiCount;
   }
   public void setLocation( int gutiNo, int loc )
   {
      gutiLocation[ gutiNo ] = loc;
   }
   public int getLocation( int gutiNo )
   {
      return gutiLocation[ gutiNo ];
   }
   public void setRetired( boolean b )
   {
      isRetired = b;
   }
   public boolean isRetired()
   {
      return isRetired;
   }
   public void setDoubling( int pieceIndex, boolean b )
   {
      isDoubling[ pieceIndex ] = b;
   }
   public void setDoubling( int pieceIndex, int doubWth )
   {
      if( doubWth >= 0 && doubWth <= 3 && pieceIndex >= 0 && pieceIndex <= 3 && pieceIndex != doubWth )
      {
         isDoubling[ pieceIndex ] = true;
         isDoubling[ doubWth ] = true;
         doublingWith[ pieceIndex ] = doubWth;
         doublingWith[ doubWth ] = pieceIndex;
      }
   }
   public boolean isDoubling( int pieceIndex )
   {
      return isDoubling[ pieceIndex  ];
   }
   public int doublingWith( int pieceIndex )
   {
      return doublingWith[ pieceIndex ];
   }
   public Guti getGuti( int index )
   {
      return pieces[ index ];
   }
   public void printPlayerInfo()   //used for debugging
   {
      if( debugStatus )
      {
         System.out.println( "Player: " + id );
         System.out.println( "Guti 0:" + gutiLocation[ 0 ] );
         System.out.println( "Guti 1:" + gutiLocation[ 1 ] );
         System.out.println( "Guti 2:" + gutiLocation[ 2 ] );
         System.out.println( "Guti 3:" + gutiLocation[ 3 ] );
      }
   }
   public void error( String msg )
   {
      if( debugStatus )
      {
         JOptionPane.showMessageDialog( null, "Error occured:\n" + msg );
      }
   }
   public void debug( String msg )
   {
      if( debugStatus )
      {
         System.err.println( "" + msg );
      }
   }
   public void unsetDoubling( int pieceIndex, int doubWth )
   {
      if( doubWth >= 0 && doubWth <= 3 && pieceIndex >= 0 && pieceIndex <= 3 && pieceIndex != doubWth )
      {
         isDoubling[ pieceIndex ] = false;
         isDoubling[ doubWth ] = false;
         doublingWith[ pieceIndex ] = -1;
         doublingWith[ doubWth ] = -1;
      }
   }
   public void releaseDoubling( int gid )
   {
      if( isDoubling[ gid ] == true )
      {
         int g2 = doublingWith[ gid ];
         isDoubling[ g2 ] = false;
         doublingWith[ g2 ] = -1;
         isDoubling[ gid ] = false;
         doublingWith[ gid ] = -1;
      }
   }
   public void setMovable( int pieceIndex, boolean canMove )
   {
      if( pieceIndex >= 0 && pieceIndex <= 3 )
      {
         canMoveFurther[ pieceIndex ] = canMove;
      }
   }
   public void resetMove()
   {
      for( int i = 0; i <= 3; i++ )
      {
         canMoveFurther[ i ] = true;
      }
   }
   public boolean isMovable( int pieceIndex )
   {
      if( pieceIndex >= 0 && pieceIndex <= 3 )
      {
         return canMoveFurther[ pieceIndex ];
      }
      return true;
   }
}
