package ludu;

//5:49 PM 10/10/2004
//u 7:34 PM 9/12/2005
//Update started by Wasiqul Islam on 30/12/2025

public class Guti implements java.io.Serializable
{
   private int type;
   private int playerID;
   private int gutiID;
   public Guti( Guti gt )
   {
      this( gt.getPlayerID(), gt.getGutiID(), gt.getType() );
   }
   public Guti( int pid, int gid, int t )     //                  (tag)
   {
      if( pid >= 0 && pid <= 3 && gid >= 0 && gid <= 3 && t >= 0 && t <= 3 )
      {
         playerID = pid;
         gutiID = gid;
         type = t;
      }
      //else do nothing
   }
   public void setType( int t )         //              (tag)
   {
      if( t >=0 && t <= 3 )
      {
         type = t;
      }
   }
   public int getType()          //             (tag)
   {
      return type;
   }
   public int getPlayerID()
   {
      return playerID;
   }
   public int getGutiID()
   {
      return gutiID;
   }
   public boolean equals( Guti gt )
   {
      if( playerID == gt.getPlayerID() && gutiID == gt.getGutiID() && type == gt.getType() )
      {
         return true;
      }
      else
      {
         return false;
      }
   }
}
