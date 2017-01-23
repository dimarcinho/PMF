/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package objects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

/**
 *
 * @author Marcio
 */
public class EnemyShot extends Shot {
    
    private int tipo; //define o tipo de tiro inimigo
    
    public EnemyShot(int x, int y, boolean dir, int tipo){
        super(x, y, dir);
        this.tipo = tipo;
        
        if(dir)
            this.vx = -speed;
        else
            this.vx = +speed;

    }
    
    @Override
    public void init(){
        this.ss = new SpriteSheet(i.load("/res/img/oil shot.png"));
        this.setFrames(0, 7);
        this.frameSpeed = 8; //quanto maior, mais lento                
        this.speed = 10;
    }
            
    @Override
    public void update(){
        this.x += this.vx;
        this.Animation();
    }
    
    @Override
    public Rectangle getBounds(){
        Rectangle r = new Rectangle(this.x, this.y, 25, 15);
        return r;
    }
    
    @Override
    public void draw(Graphics g){
        g.drawImage(this.getAnimatedImage(), this.x, this.y, null);
    }
    
    @Override
    public void Animation(){
        
        frameSS = ss.crop2(frameNumber*40, 0, 40, 15);
        
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
        
        if(vx == 0 && vy == 0)
            counterSS--;
    }
    
}
