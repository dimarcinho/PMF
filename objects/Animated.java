
package objects;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;


public abstract class Animated {
    
    public ImageLoader i = new ImageLoader();
    public SpriteSheet ss;
    public int frame;
    public int frameSpeed = 10;
    public int frameNumber, startFrame, endFrame;
    public int counterSS = 0;
    public Image frameSS;
    public int direction = 1;
    public int tsize = 32;
        
    public int centerX, centerY;
    
    public int x,y;
    public int vx, vy;    
    
    public int speed = 10;
    
    public void init(){}
    public void update(){}
    public void draw(Graphics g){}    
    
    public Image getAnimatedImage(){
        return frameSS;        
    }
    
    public void Animation(){
        
        frameSS = ss.crop2(tsize*frameNumber, 0, tsize, tsize);
        
        if(counterSS % frameSpeed == 0){
            if(frameNumber < endFrame){
                frameNumber++;
            } else {
                frameNumber = startFrame;
            }   
        }
        
        if(counterSS > 20*frameSpeed){
            counterSS = 0;
        } else {
            counterSS++;
        }
    }
    
    public Rectangle getBounds(){
        Rectangle r = new Rectangle(this.x, this.y, tsize, tsize);
        return r;
    }
    
    public void setFrames(int startFrame, int numOfFrames){
        this.startFrame = startFrame;
        this.endFrame = startFrame + numOfFrames;
        this.frameNumber = startFrame;
    }
    
    public void changeDirection(){}

}
