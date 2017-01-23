
package audioEngine;

import java.util.ArrayList;

public class SoundManager {
    
    public ArrayList<Sound> sounds = new ArrayList();
    
    public SoundManager(){
        initSounds();
    }
        
    public void initSounds(){
     
        try{
        
        this.addSound(new Sound("jump", Sound.getURL("/res/audio/sfx/Jump7.wav")));
        this.addSound(new Sound("death", Sound.getURL("/res/audio/sfx/Death.wav")));
        
        } catch(Exception e) {
            e.getStackTrace();
        }
    
    }
    
    public void addSound(Sound sound){
        sounds.add(sound);        
    }
    
    public void removeSound(Sound sound){
        sounds.remove(sound);
    }
    
    public void playSound(String name){
        try{
            for(Sound s: sounds){
                if(s.name.equals(name)){
                    s.play();
                }
            }

        } catch(Exception e) {
            e.getStackTrace();
        }
        
    }
    
    public void loopSound(String name){
        try{
            for(Sound s: sounds){
                if(s.name.equals(name)){
                    s.loop();
                }
            }

        } catch(Exception e) {
            e.getStackTrace();
        }
}
    
    public void stopSound(String name){
        try{
            for(Sound s: sounds){
                if(s.name.equals(name)){
                    s.stop();
                }
            }

        } catch(Exception e) {
            e.getStackTrace();
        }
        
    }
    
    public void stopAllSound(String name){
        try{
            for(Sound s: sounds){
                s.stop();
            }

        } catch(Exception e) {
            e.getStackTrace();
        }
        
    }
}
