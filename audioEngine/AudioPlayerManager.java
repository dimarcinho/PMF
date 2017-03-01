
package audioEngine;

import java.util.ArrayList;
//import java.util.Hashtable;
import observerpattern.Observer;

public final class AudioPlayerManager implements Observer{
    /*
    A ideia desta classe era para criar todos os aúdios na memória,
    alocando-os dentro de um Array para depois utilizá-los durante o jogo
    
    Mas este processo está mais demorado do que simplesmente criar o áudio.
    Desta maneira, esta classe em si acaba tornando-se "inútil", pois as mesmas
    referências daqui poderiam estar diretamente dentro da classe AudioPlayer
    
    Por hora, deixo este texto aqui para lembrar o porquê desta classe.
    */

    public ArrayList<AudioPlayer> audios;
    private ArrayList<Thread> threads;
    //private Hashtable<String, AudioPlayer> loaded;
    
    public AudioPlayerManager(){
        audios = new ArrayList<>();
        threads = new ArrayList<>();
        init();
    }

    private void init(){
        //criar todos os arquivos de áudio na memória
        //loaded.put("JUMP", new AudioPlayer("/res/audio/sfx/Jump7.wav"));
        //loaded.put("DEATH", new AudioPlayer("/res/audio/sfx/Death.wav"));
        //loaded.put("SHOT", new AudioPlayer("/res/audio/sfx/Laser_Shoot3.wav"));
        
        audios.add(new AudioPlayer("/res/audio/sfx/Laser_Shoot3.wav"));

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
        
        final AudioPlayer x;
        
        switch(s){
            case "SHOT":
                x = new AudioPlayer("/res/audio/sfx/Laser_Shoot3.wav");
                break;
            case "JUMP":
                x = new AudioPlayer("/res/audio/sfx/Jump7.wav");
                break;
            case "DEATH":
                x = new AudioPlayer("/res/audio/sfx/Death.wav");
                this.stopAllSounds();
                break;
            case "GET_BBL":
                x = new AudioPlayer("/res/audio/sfx/Pickup_Coin.wav");
                break;
            case "KILL_ENEMY":
                x = new AudioPlayer("/res/audio/sfx/Hit_Hurt6.wav");
                break;
            case "PLAYER_HURT":
                x = new AudioPlayer("/res/audio/sfx/Player_Hurt.wav");
                break;
            default:
                x = null;
                System.out.println("Evento de áudio criado não suportado: "+s);                
                //throw new IllegalArgumentException("Evento de áudio criado não suportado: "+s);
        }
        
        if(x != null){
            
            //threads separadas************
            /*
            new Thread(new Runnable(){
                @Override
                public void run(){
                    x.play();
                }
            }).start();
            */
            //*********************
            
            //threads juntas*******
            x.play();
            //*********************
        }
        
        
    }
}
