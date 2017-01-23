/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package audioEngine;

import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;

public class AudioClip {

    private File file;
    
    public AudioClip(String path){
        file = new File(path);
        if(!file.exists()){
            System.out.println("Arquivo do áudio não encontrado!");
        }
    }
    
    public AudioInputStream getAudioStream(){
        try{
            return AudioSystem.getAudioInputStream(file);            
        } catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
}
