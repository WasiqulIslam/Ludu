package ludu;

//11:16 PM 10/22/2004
//Update started by Wasiqul Islam on 30/12/2025

public class PermutationPair implements java.io.Serializable
{
   public static boolean isDouble( int index )
   {
      if( index >= 0 && index <= 3 )
      {
         return false;
      }
      else if( index >= 4 && index <= 9 )
      {
         return true;
      }
      else
      {
         return false;
      }
   }
   public static int [] getPieces( int index )
   {
      if( index == 0 )
      {
         int a[] = { 0, -1 };
         return a;
      }
      else if( index == 1 )
      {
         int a[] = { 1, -1 };
         return a;
      }
      else if( index == 2 )
      {
         int a[] = { 2, -1 };
         return a;
      }
      else if( index == 3 )
      {
         int a[] = { 3, -1 };
         return a;
      }
      else if( index == 4 )
      {
         int a[] = { 0, 1 };
         return a;
      }
      else if( index == 5 )
      {
         int a[] = { 0, 2 };
         return a;
      }
      else if( index == 6 )
      {
         int a[] = { 0, 3 };
         return a;
      }
      else if( index == 7 )
      {
         int a[] = { 1, 2 };
         return a;
      }
      else if( index == 8 )
      {
         int a[] = { 1, 3 };
         return a;
      }
      else if( index == 9 )
      {
         int a[] = { 2, 3 };
         return a;
      }
      else
      {
         int a[] = { -1, -1 };
         return a;
      }
   }
}