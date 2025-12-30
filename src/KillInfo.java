//5:48 PM 12/26/2004

public class KillInfo
{
   private int killerGutiID, killingLocation;
   private boolean isDoublyKilled;
   public KillInfo( int kgi, int kl, boolean idk )
   {
      killerGutiID = kgi;
      killingLocation = kl;
      isDoublyKilled = idk;
   }
   public int getKillingLocation()
   {
      return killingLocation;
   }
   public int getKillerGutiID()
   {
      return killerGutiID;
   }
   public boolean isDoublyKilled()
   {
      return isDoublyKilled;
   }
}