
package audioEngine;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;

public class AudioClipPlayer {
    
    public static synchronized void playSound(final AudioClip sfx){
        Thread thread = new Thread(){
            public void run(){
                try{
                    
                    AudioInputStream stream = sfx.getAudioStream();
                    Clip clip = AudioSystem.getClip();                    
                    clip.open(stream);                    
                    clip.start();                    
                    
                } catch(Exception e){
                    e.printStackTrace();
                }
            
            }
        };
        thread.start();
                
    }
    
    public static synchronized void loopSound(final AudioClip sfx){
        Thread thread = new Thread(){
            public void run(){
                try{
                    
                    AudioInputStream stream = sfx.getAudioStream();
                    Clip clip = AudioSystem.getClip();                    
                    clip.open(stream);                    
                    clip.loop(100);
                    
                } catch(Exception e){
                    e.printStackTrace();
                }
            
            }
        };
        thread.start();
                
    }   
}
