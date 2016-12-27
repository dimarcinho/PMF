/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package objects;

import java.awt.Graphics;
import java.awt.Rectangle;

/**
 *
 * @author Marcio
 */
public class Shot extends Animated  {
    
    private boolean dir; //true - left ; false - right
    
    public Shot(int x, int y, boolean dir){
        this.x = x;
        this.y = y;
        this.dir = dir;
      
        
        speed = 10;
        
        if(dir)
            vx = -speed;
        else
            vx = +speed;
        
        init();
        
    }    
    
    @Override
    public void init(){        
                
        this.ss = new SpriteSheet(i.load("/res/Shot Calculator.png"));
        
        this.startFrame = 0;
        this.endFrame = startFrame + 3;
        this.frameNumber = startFrame;
        this.frameSpeed = 10; //quanto maior, mais lento  
        
    }
    
    @Override
    public void update(){
        this.x += vx;
        this.Animation();
    }
    
    @Override
    public Rectangle getBounds(){
        Rectangle r = new Rectangle(this.x+6, this.y+9, 20, 15);
        return r;
    }
    
    @Override
    public void draw(Graphics g){
        //g.setColor(Color.black);
        //g.fillRect(x,y,3,3);
        g.drawImage(this.getAnimatedImage(), x, y, null);
    }
    
}
