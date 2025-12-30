package ludu;

//5:30 PM 10/10/2004 u 11:49 PM 12/27/2004
//u 9:07 PM 9/12/2005
//Update started by Wasiqul Islam on 30/12/2025

public class GutiContainer implements java.io.Serializable
{
   private boolean debugStatus = false;
   private int id = -1;
   private Guti pieces[];
   private int length;
   public GutiContainer( int i )//                       (tag)
   {
      id = i;
      length = 0;
      pieces = new Guti[ 16 ];
   }
   public int[] contains( int type ) //eg. type: Constants.RED : shows a list of int array containing Guti's index from bottom                       (tag)
   {
      if( length == 0 )
         return null;
      int a = 0;
      for( int i = 0; i < length; i++ )
      {
         if( pieces[ i ].getType() == type )
         {
            a++;
         }
      }
      int b[] = new int[ a ];
      a = 0;
      for( int i = 0; i < length; i++ )
      {
         if( pieces[ i ].getType() == type )
         {
            b[ a ] = i;
            a++;
         }
      }
      return b;
   }
   public Guti deleteOne( Guti piece ) //delete one Guti of type 'type' from bottom                       (tag)
   {
      if( length == 0 )
         return null;
      int a = 0;
      for( int i = 0; i < length; i++ )
      {
         if( pieces[ i ].equals( piece ) )
         {
            a = i;
            break;
         }
      }
      Guti gt = pieces[ a ];
      for( int i = a; i < length-1; i++ )
      {
         pieces[ i ] = pieces[ i + 1 ];
      }
      pieces[ length - 1 ] = null;
      length--;
      return gt;
   }
   public void addOne( Guti piece )  //adds one Guti at the top                       (tag)
   {
      if( length >= 16 )
         return;
      pieces[ length ] = piece;
      length++;
   }
   public int length()      //                   ( tag )
   {
      return length;
   }
   public Guti getPiece( int index )      //                   ( tag )
   {
      if( index < length )
         return pieces[ index ];
      else
         return null;
   }
   public void reset()      //                   ( tag )
   {
      length = 0;
      pieces = null;
      pieces = new Guti[ 16 ];
   }
   public int getID()
   {
      return id;
   }
   public void debug( String msg )
   {
      if( debugStatus )
         System.out.println( msg );
   }
}