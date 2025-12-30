//3:54 PM 10/11/2004
//renamed from Sounds to Sound 7:55 AM 9/13/2005
//u 8:02 AM 9/13/2005

import java.applet.*;
import java.net.*;

public class Sound implements java.io.Serializable
{
   private  int lastSound = -1;
   private final int n = 9;
   private AudioClip sounds[] = new AudioClip[ n ];
   private boolean firstTime = true;

   public Sound()
   {
      try
      {
         firstTime = false;
         sounds[ 0 ] = Applet.newAudioClip( new URL( "file", "", -1, "click.wav" ) );
         sounds[ 1 ] = Applet.newAudioClip( new URL( "file", "", -1, "error.wav" ) );
         sounds[ 2 ] = Applet.newAudioClip( new URL( "file", "", -1, "shake.wav" ) );
         sounds[ 3 ] = Applet.newAudioClip( new URL( "file", "", -1, "launch.wav" ) );
         sounds[ 4 ] = Applet.newAudioClip( new URL( "file", "", -1, "select.wav" ) );
         sounds[ 5 ] = Applet.newAudioClip( new URL( "file", "", -1, "move.wav" ) );
         sounds[ 6 ] = Applet.newAudioClip( new URL( "file", "", -1, "gameover.wav" ) );
         sounds[ 7 ] = Applet.newAudioClip( new URL( "file", "", -1, "help.wav" ) );
         sounds[ 8 ] = Applet.newAudioClip( new URL( "file", "", -1, "kill.wav" ) );
      }
      catch( Throwable t )
      {
         t.printStackTrace();
      }
   }

   public void playSound( int index )
   {
      if( index >= 0 && index < n && sounds[ index ] != null )
      {
         if( lastSound >= 0 && lastSound < n )
            sounds[ lastSound ].stop();
         lastSound = index;
         sounds[ index ].play();
      }
   }
   public void stopSound( int index )
   {
      if( index >= 0 && index < n && sounds[ index ] != null )
      {
         sounds[ index ].stop();
      }
   }
   public void loopSound( int index )
   {
      if( index >= 0 && index < n && sounds[ index ] != null )
      {
         if( index == lastSound )      //used only for loop sound
            return;
         if( lastSound >= 0 && lastSound < n )
            sounds[ lastSound ].stop();
         lastSound = index;
         sounds[ index ].loop();
      }
   }
}
