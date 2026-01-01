package ludu;

import java.io.*;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

//3:54 PM 10/11/2004
//renamed from Sounds to Sound 7:55 AM 9/13/2005
//u 8:02 AM 9/13/2005
//Update started by Wasiqul Islam on 30/12/2025

public class Sound implements java.io.Serializable
{
   
   private  int lastSound = -1;
   private final int n = 9;
   private Clip soundClips[] = new Clip[ n ];

   public Sound()
   {
      try
      {
         soundClips[ 0 ] = getClip( "CLICK.WAV");
         soundClips[ 1 ] = getClip( "ERROR.WAV");
         soundClips[ 2 ] = getClip( "SHAKE.WAV");
         soundClips[ 3 ] = getClip( "LAUNCH.WAV");
         soundClips[ 4 ] = getClip( "SELECT.WAV");
         soundClips[ 5 ] = getClip( "MOVE.WAV");
         soundClips[ 6 ] = getClip( "GAMEOVER.WAV");
         soundClips[ 7 ] = getClip( "HELP.WAV");
         soundClips[ 8 ] = getClip( "KILL.WAV");
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
         soundClips[ index ].setFramePosition(0); // Rewind to the beginning
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
         soundClips[ index ].setFramePosition(0); // Rewind to the beginning
         lastSound = index;
         soundClips[ index ].loop(Clip.LOOP_CONTINUOUSLY);
         soundClips[ index ].start();
      }
   }

}
