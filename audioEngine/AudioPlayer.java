
package audioEngine;

import javax.sound.sampled.*;
import observerpattern.Observer;

public class AudioPlayer implements Observer {
    
    private Clip clip;
    
    public AudioPlayer(String s){
        try{
            AudioInputStream ais = AudioSystem.getAudioInputStream(
                    getClass().getResourceAsStream(s)
                    );
            
            AudioFormat baseFormat = ais.getFormat();            
            AudioFormat decodeFormat = new AudioFormat(
                    AudioFormat.Encoding.PCM_SIGNED,
                    baseFormat.getSampleRate(),
                    16,
                    baseFormat.getChannels(),
                    baseFormat.getChannels()*2,
                    baseFormat.getSampleRate(),                    
                    false
                );
            AudioInputStream dais = AudioSystem.getAudioInputStream(decodeFormat, ais);            
            clip = AudioSystem.getClip();
            clip.open(dais);            
            
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    private void setVol(double vol, Clip clip){
        FloatControl gain = (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);
        float dB = (float)(Math.log(vol)/Math.log(10)*20);
        gain.setValue(dB);        
    }
    
    public void setLowVol(float low){
        FloatControl gain = (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);
        //float dB = (float)this.getVol()-1f;
        gain.setValue(low);        
    }
    
    public float getVol(){
        FloatControl gain = (FloatControl)this.clip.getControl(FloatControl.Type.MASTER_GAIN);
        return gain.getValue();
    }
 
    public void play(){
        if (clip == null)return;
        stop();
        clip.setFramePosition(0);
        clip.start();        
            
    }
    
    public void loop(){        
        if (clip == null)return;
        stop();
        clip.loop(100); //ficará em loop por 100 vezes... não é o ideal...
    }
    
    public void stop(){
        if(clip.isRunning()) clip.stop();
    }
    
    public void close(){
        stop();
        clip.close();
    }

    @Override
    public void onNotify(String s) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
