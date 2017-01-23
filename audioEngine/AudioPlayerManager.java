
package audioEngine;

import java.util.ArrayList;
import java.util.Hashtable;
import observerpattern.Observer;

public final class AudioPlayerManager implements Observer{

    public ArrayList<AudioPlayer> audios;
    private ArrayList<Thread> threads;
    private Hashtable<String, AudioPlayer> loaded;
    
    public AudioPlayerManager(){
        audios = new ArrayList<>();
        threads = new ArrayList<>();
        loaded = new Hashtable<>();
        init();
    }
    
    private void init(){
        //criar todos os arquivos de áudio na memória
        loaded.put("JUMP", new AudioPlayer("/res/audio/sfx/Jump7.wav"));
        loaded.put("DEATH", new AudioPlayer("/res/audio/sfx/Death.wav"));
        loaded.put("SHOT", new AudioPlayer("/res/audio/sfx/Laser_Shoot3.wav"));
        
        //na hora de utilizá-los, apenas copiar o respectivo e gerar a thread
        
        new Thread(new Runnable(){
               @Override
               public void run(){                   
                   //AudioPlayer sfx;
                   //sfx = loaded.get("JUMP");                   
                   loaded.get("JUMP").play();
                   //sfx.play();
                   //AudioPlayer audioplayer = new AudioPlayer("/res/audio/sfx/Laser_Shoot3.wav");
                   //audioplayer.play();
               }
        }).start();
        
    }
    
    public void addAudioPlayer(AudioPlayer sound){
        this.audios.add(sound);
    }
    
    public void removeAudioPlayer(AudioPlayer sound){
        this.audios.remove(sound);
    }
    
    public void stopAllSounds(){
        for(AudioPlayer ap : audios){
            ap.stop();
        }
    }
    
    public void play2(final String s){
        /*
        threads.add(
               new Thread(new Runnable(){
                   @Override
                   public void run(){
                       loaded.get(s).play();
                   }
                })
        );
        threads.get(threads.size()-1).start();
         * 
         */
        
        
        new Thread(new Runnable(){
               @Override
               public void run(){                   
                   AudioPlayer sfx;
                   sfx = loaded.get(s);                   
                   //loaded.get(s).play();
                   sfx.play();
                   //AudioPlayer audioplayer = new AudioPlayer("/res/audio/sfx/Laser_Shoot3.wav");
                   //audioplayer.play();
               }
        }).start();
        
        System.out.println("Playing: "+s);
    }
    
    public void play(String s){
        this.addAudioPlayer(new AudioPlayer(s));
        this.audios.get(audios.size()-1).play();
        System.out.println("Playing: "+s);
    }
    
    public void loop(String s){
        this.addAudioPlayer(new AudioPlayer(s));
        this.audios.get(audios.size()-1).loop();        
    }
    
    public void fadeOutAllSounds(){
        
        for(AudioPlayer ap : audios){
            for(int i = 0; i < 100; i++){
                //System.out.println(ap.getVol());
                ap.setLowVol(-5f);
                System.out.println(i+": diminuindo o volume de "+ap.getClass().getName());
                
            }
        }
    }

    @Override
    public void onNotify(String s) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
