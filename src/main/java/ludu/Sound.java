package ludu;

import java.io.*;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

//3:54 PM 10/11/2004
//renamed from Sounds to Sound 7:55 AM 9/13/2005
//u 8:02 AM 9/13/2005

public class Sound implements java.io.Serializable
{
   
   private  int lastSound = -1;
   private final int n = 9;
   private Clip soundClips[] = new Clip[ n ];

   public Sound()
   {
      try
      {
         soundClips[ 0 ] = getClip( "click.wav");
         soundClips[ 1 ] = getClip( "error.wav");
         soundClips[ 2 ] = getClip( "shake.wav");
         soundClips[ 3 ] = getClip( "launch.wav");
         soundClips[ 4 ] = getClip( "select.wav");
         soundClips[ 5 ] = getClip( "move.wav");
         soundClips[ 6 ] = getClip( "gameover.wav");
         soundClips[ 7 ] = getClip( "help.wav");
         soundClips[ 8 ] = getClip( "kill.wav");
      }
      catch( Throwable t )
      {
         t.printStackTrace();
      }
   }

   private Clip getClip(String soundPath) throws Exception
   {
      BufferedInputStream inputStream =
         new BufferedInputStream(
            getClass().getResourceAsStream("/sounds/"+ soundPath
         ));
      AudioInputStream audioStream =
         AudioSystem.getAudioInputStream(inputStream);

      Clip clip = AudioSystem.getClip();
      clip.open(audioStream);

      return clip;
   }

   public void playSound( int index )
   {
      if( index >= 0 && index < n && soundClips[ index ] != null )
      {
         if( lastSound >= 0 && lastSound < n )
            soundClips[ lastSound ].stop();
         lastSound = index;
         soundClips[ index ].start();
      }
   }

   public void stopSound( int index )
   {
      if( index >= 0 && index < n && soundClips[ index ] != null )
      {
         soundClips[ index ].stop();
      }
   }

   public void loopSound( int index )
   {
      if( index >= 0 && index < n && soundClips[ index ] != null )
      {
         if( index == lastSound )      //used only for loop sound
            return;
         if( lastSound >= 0 && lastSound < n )
            soundClips[ lastSound ].stop();
         lastSound = index;
         soundClips[ index ].loop(Clip.LOOP_CONTINUOUSLY);
         soundClips[ index ].start();
      }
   }

}
