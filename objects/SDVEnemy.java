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
public class SDVEnemy extends Enemy {
    
    private int x0, xf;    
    
    public SDVEnemy(int x, int y){
        this.x = x;
        this.y = y;
        
        vx = -1;
        vy = 0;
        
        x0 = x - 80;
        xf = x + 80;        

        this.ss = new SpriteSheet(i.load("/res/SDV.png"));
        
        //this.setFrames(0, 1);
        this.frameSpeed = 8; //quanto maior, mais lento
        
        this.setFrames(2, 5);//teste da SDV
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
            this.setFrames(2, 5);
        } else {
            this.setFrames(9, 5);
        }
        
    }
    
}
