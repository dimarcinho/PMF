/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package objects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.Random;

/**
 *
 * @author Marcio
 */
public class EnemyA extends Enemy {
    
    public int x0,xf,y0,yf;
    public Rectangle r;
    
        
    public EnemyA(int x, int y){
        this.x = x;
        this.y = y;
        
        r = new Rectangle(x, y, 32, 32);
        
        Random rnd = new Random();
        
        vx = rnd.nextInt(3)+1;
        vy = 0;
        
        this.setXLimits(100,80);
        
        this.ss = new SpriteSheet(i.load("/res/Válvula Borboleta.png"));
        
        this.setFrames(0, 10);
        this.frameSpeed = 3;
    }
    
    public void init(){}
    
    public void reset(){}
    
    public void setXLimits(int x0, int xf){
        this.x0 = this.x - x0;        
        this.xf = this.x + xf;        
    }
    
    @Override
    public void update(){
            
        x += vx;
        y += vy;
        
        if(x < x0 || x > xf){
            vx = -vx;
        }
        
        this.Animation();
        
    }
    
    @Override
    public void draw(Graphics g){
        
        g.drawImage(this.getAnimatedImage(), x, y, null);
        
    }
    
    @Override
    public Rectangle getBounds(){
        Rectangle r = new Rectangle(this.x+5, this.y, 21, 32);
        return r;    
    }
    
    public void keyPressed(KeyEvent e){};
    public void keyReleased(KeyEvent e){};
    
}
