
package audioEngine;

import java.util.ArrayList;
import java.util.Hashtable;
import observerpattern.Observer;

public final class AudioPlayerManager implements Observer{

    public ArrayList<AudioPlayer> audios;
    private ArrayList<Thread> threads;
    private Hashtable<String, AudioPlayer> loaded;
    
    private AudioPlayer ap;
    
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
        
        audios.add(new AudioPlayer("/res/audio/sfx/Laser_Shoot3.wav"));

    }
    
    public void play3(String s){
        throw new UnsupportedOperationException("Utilizado apenas para testes");
        
        
        //ap = new AudioPlayer("/res/audio/sfx/Laser_Shoot3.wav");               
        
        //AudioPlayer audio2 = ap;
        //audio2.play();
        //ap = loaded.get(s);
        
        //ap = audios.get(0);        
        //ap.play();
        
        //audios.get(0).play();
        
        /*
         * testes mostraram que criar o áudio diretametne é mais rápido que
         * armazená-los na memória, o que é estranho ao meu ver.
         */
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
            ap = null;
        }
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
        
        AudioPlayer x;
        
        switch(s){
            case "SHOT":
                x = new AudioPlayer("/res/audio/sfx/Laser_Shoot3.wav");
                break;
            case "JUMP":
                x = new AudioPlayer("/res/audio/sfx/Jump7.wav");
                break;
            case "DEATH":
                x = new AudioPlayer("/res/audio/sfx/Death.wav");
                break;
            case "GET_BBL":
                x = new AudioPlayer("/res/audio/sfx/Pickup_Coin.wav");
                break;
            case "KILL_ENEMY":
                x = new AudioPlayer("/res/audio/sfx/Hit_Hurt6.wav");
                break;
            default:
                throw new IllegalArgumentException("Áudio criado não suportado.");
        }
        
        if(x != null){
            x.play();
        }
        
        
    }
}
