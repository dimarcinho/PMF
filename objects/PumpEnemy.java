/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package objects;

import java.awt.Graphics;

/**
 *
 * @author Marcio
 */
public class PumpEnemy extends Enemy {
    
    private int x0, xf;    
    
    public PumpEnemy(int x, int y){
        this.x = x;
        this.y = y;
        
        vx = -1;
        vy = 0;
        
        x0 = x - 90;
        xf = x + 90;        

        this.ss = new SpriteSheet(i.load("/res/img/pump enemy3.png"));
        
        
        this.changeDirection();
        this.frameSpeed = 4; //quanto maior, mais lento
        
    }
    
    @Override
    public void update(){
            
        x += vx;
        
        if(x < x0 || x > xf){
            vx = -vx;
            this.changeDirection();
        }
                
        this.Animation();        
        
    }
    
    @Override
    public void draw(Graphics g){
        
        g.drawImage(this.getAnimatedImage(), x, y, null);
        
    }

    @Override
    public void changeDirection(){
    
        if(vx < 0){
            this.setFrames(0, 7);
        } else {
            this.setFrames(8, 7);
        }
        
    }
    
    @Override
    public void Animation(){
        
        frameSS = ss.crop3(frameNumber, 8);
        
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
    
}
