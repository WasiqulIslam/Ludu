//11:33 AM 10/9/2004 u 4:25 PM 10/24/2004 u 9:50 PM 11/5/2004 u 6:43 PM 11/13/2004
//u 9:11 PM 9/12/2005

import java.awt.*;

public class Position implements java.io.Serializable
{
   private boolean debugStatus = false;
   private static final int START[] = { 1, 40, 27, 14 }; //starting position of RED, BLUE, GREEN and YELOW pieces respectively
   private static final int FINAL[] = { 94, 95, 96, 97 }; //Destination of a specific colored Guti
   private static final int REST[] = { 80, 84, 88, 92 };  //Location or id of resting Guti
   private static final int NO_PATH[] = { 74, 75, 76, 77 };  //When no possible path of piece movement is found selection pointer selects this null(empty) position
   private int id = -1;
   private int x, y, w, h;
   private Color color;
   private int nextRed = -1, nextBlue = -1, nextGreen = -1, nextYellow = -1;
   int currentBlockingPlayers =  0;
   private int blockingPlayerID[] = new int[ 8 ];
   private int blockingTeamID[] = new int[ 8 ];
   private int blockingPlayerFirstGuti[] = new int[ 8 ];
   private int blockingPlayerSecondGuti[] = new int[ 8 ];
   private boolean willItBeDrawn = false;   //Some positions are part of the board and not drawn as board graphics
   public Position( int i )    //                   (tag)
   {
      id = i;
   }
   public boolean isSafe()      //                 (tag)
   {
      if( id == 1 || id == 40|| id == 27|| id == 14|| id == 49|| id == 36|| id == 23|| id == 10 || ( id >= 53 && id <= 72 ) || ( id >= 78 && id <= 93 ) || ( id >= 94 && id <= 97 ) || ( id >= 73 && id <= 77 )  )
      {
         return true;
      }
      else
      {
         return false;
      }
   }
   public boolean isStarIndicated()      //                 (tag)
   {
      if( id == 1 || id == 40|| id == 27|| id == 14|| id == 49|| id == 36|| id == 23|| id == 10 )
      {
         return true;
      }
      else
      {
         return false;
      }
   }

   public void setNext( int n )     //                  (tag)
   {
      nextRed = n;
      nextBlue = n;
      nextGreen = n;
      nextYellow = n;
   }
   public void setNext( int value, int type )       //                (tag)
   {
      if( type == Constants.RED )
         nextRed = value;
      else if( type == Constants.BLUE )
         nextBlue = value;
      else if( type == Constants.GREEN )
         nextGreen = value;
      else if( type == Constants.YELLOW )
         nextYellow = value;
   }
   public void block( int pid, int tid, int gt1, int gt2 )     //                  (tag)
   {
      if( !isSafe() )
      {
         blockingPlayerID[ currentBlockingPlayers ] = pid;
         blockingTeamID[ currentBlockingPlayers ] = tid;
         blockingPlayerFirstGuti[ currentBlockingPlayers ] = gt1;
         blockingPlayerSecondGuti[ currentBlockingPlayers ] = gt2;
         currentBlockingPlayers++;
      }
   }
   public void unblock( int pid, int tid, int gt1, int gt2 )     //                  (tag)
   {
      int a = -1;
      for( int i = 0; i < currentBlockingPlayers; i++ )
      {
         if( blockingPlayerID[ i ] == pid && blockingTeamID[ i ] == tid)
         {
            if( ( gt1 == blockingPlayerFirstGuti[ i ] && gt2 == blockingPlayerSecondGuti[ i ] ) || ( gt2 == blockingPlayerFirstGuti[ i ] && gt1 == blockingPlayerSecondGuti[ i ] ) )
            {
               a = i;
               break;
            }
         }
      }
      if( a != -1 )
      {
         for( int i = a; i < ( currentBlockingPlayers - 1); i++ )
         {
            blockingPlayerID[ i ] = blockingPlayerID[ i + 1 ];
            blockingTeamID[ i ] = blockingTeamID[ i + 1 ];
            blockingPlayerFirstGuti[ i ] = blockingPlayerFirstGuti[ i + 1 ];
            blockingPlayerSecondGuti[ i ] = blockingPlayerSecondGuti[ i + 1 ];
         }
         currentBlockingPlayers--;
      }
   }
   public void unblock()
   {
      currentBlockingPlayers = 0;
   }
   public void releaseBlocking( int pid, int tid, int gt )     //                  (tag)
   {
      label1:
      for( int i = 0; i < currentBlockingPlayers; i++ )
      {
         if( blockingPlayerID[ i ] == pid && blockingTeamID[ i ] == tid && ( blockingPlayerFirstGuti[ i ] == gt || blockingPlayerSecondGuti[ i ] == gt ) )
         {
            unblock( blockingPlayerID[ i ], blockingTeamID[ i ], blockingPlayerFirstGuti[ i ], blockingPlayerSecondGuti[ i ] );
            i = -1;                        //Check the match again -
            continue label1;      //- from the beginning
         }
      }
   }
   public boolean isBlocked()      //                 (tag)
   {
      if( currentBlockingPlayers == 0 )
      {
         return false;
      }
      else
      {
         return true;
      }
   }
   public boolean isBlockedFor( int tid )      //                 (tag)
   {
      if( !isBlocked() )
      {
         return false;
      }
      boolean flag =false;
      for( int i = 0; i < currentBlockingPlayers; i++ )
      {
         if( blockingTeamID[ i ] != tid )
         {
            flag = true;
            break;
         }
      }
      return flag;
   }
   public int[] getBlockingPlayer()      //                 (tag)
   {
      return blockingPlayerID;
   }
   public int[] getBlockingTeam()      //                 (tag)
   {
      return blockingPlayerID;
   }
   public int[] getBlockingPlayerFirstGuti()      //                 (tag)
   {
      return blockingPlayerFirstGuti;
   }
   public int[] getBlockingPlayerSecondGuti()      //                 (tag)
   {
      return blockingPlayerSecondGuti;
   }
   public int getNext( int type )       //                (tag)
   {
      debug( "Position: 135 getNext(): id: " + id );
      if( type == Constants.RED )
      {
         return nextRed;
      }
      else if( type == Constants.BLUE )
      {
         return nextBlue;
      }
      else if( type == Constants.GREEN )
      {
         return nextGreen;
      }
      else if( type == Constants.YELLOW )
      {
         return nextYellow;
      }
      return -1;
   }
   public void setWhetherItWillBeDrawn( boolean b )       //                (tag)
   {
      willItBeDrawn = b;
   }
   public boolean willItBeDrawn()        //               (tag)
   {
      return willItBeDrawn;
   }
   public void setColor( Color c )      //                 (tag)
   {
      color = c;
   }
   public Color getColor()       //                (tag)
   {
     return  color;
   }
   public void setX( int val )     //                  (tag)
   {
      x = val;
   }
   public void setY( int val )      //                 (tag)
   {
      y = val;
   }
   public void setW( int val )        //               (tag)
   {
      w = val;
   }
   public void setH( int val )       //                (tag)
   {
      h = val;
   }
   public int getX()        //               (tag)
   {
      return x;
   }
   public int getY()         //              (tag)
   {
      return y;
   }
   public int getW()       //                (tag)
   {
      return w;
   }
   public int getH()        //               (tag)
   {
      return h;
   }
   public static int getStartPosition( int type )
   {
      if( type >= 0 && type <= 3 )
         return START[ type ];
      else
         return -1;
   }
   public static int getRestingPosition( int type )
   {
      if( type >= 0 && type <= 3 )
         return REST[ type ];
      else
         return -1;
   }
   public static int getFinalPosition( int type )
   {
      if( type >= 0 && type <= 3 )
         return FINAL[ type ];
      else
         return -1;
   }
   public static int getEmptyPosition( int type )
   {
      if( type >= 0 && type <= 3 )
         return NO_PATH[ type ];
      else
         return -1;
   }
   public int getID()
   {
      return id;
   }
   private void debug( String msg )   //in debug function  command output is used for huge info
   {
      if( debugStatus )
         System.out.println( msg );
   }
   public Color getBlockColor()
   {
      Color c = null;
      if( isBlocked() )
      {
         int a = blockingPlayerID[ 0 ];
         if( a == Constants.RED )
         {
            c = Color.red;
         }
         else if( a == Constants.BLUE )
         {
            if( Player.numberOfPlayers != 2 )
            {
               c = Color.blue;
            }
            else
            {
               c = Color.green;
            }
         }
         else if( a == Constants.GREEN )
         {
            c = Color.green;
         }
         else if( a == Constants.YELLOW )
         {
            c = Color.yellow;
         }
      }
      return c;
   }
}
