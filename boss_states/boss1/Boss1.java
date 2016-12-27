/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package boss_states.boss1;

import boss_states.Boss;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import objects.EnemyShot;
import objects.Player;
import objects.ShotController;
import objects.SpriteSheet;

/**
 *
 * @author Marcio
 */
public class Boss1 extends Boss {

    private int shotCount;
    public ShotController esc;
    
    public Boss1(int x, int y, Player p){
        super(x,y,p);        
        
        this.x = x;
        this.y = y;
        this.p = p;
        
        this.setSS("/res/barrel boss.png");
        
        this.setFrames(0, 4);
        
        this.vx = 0;
        this.vy = 0;
        this.g = 1;
        this.jumpspeed = 20;
        this.speed = 5;
        
        this.lifepoints = 3;
        
        esc = new ShotController();
        
        this.init();
        
        isInvencible = false;
    }
    
    @Override
    public void init(){
        shotCount = 0;
    }
    
    public void enemyShot(){
        
        EnemyShot es = new EnemyShot(this.x + 32, this.y+60, shotDir(), 0);
        esc.addShot(es);
        
    }
    
    public boolean shotDir(){
        boolean dir = false;
        if(x > p.x)
            dir = true;        
        return dir;
    }

    @Override
    public void changeDirection() {        
        
        if(x > p.x){
            this.setFrames(0, 4);
        } else if (x < p.x){
            this.setFrames(5, 4);
        }
    }

    @Override
    public void draw(Graphics g) {         
         g.drawImage(this.getAnimatedImage(), this.x, this.y, null);    
         esc.draw(g);
    }
    
    @Override
    public Image getAnimatedImage(){
        return this.frameSS;        
    }

    @Override
    public Rectangle getBounds() {
        Rectangle r = new Rectangle(this.x, this.y, 64, 96);
        return r;
    }
        
    public Rectangle getWeakPoint() {
        Rectangle r = new Rectangle(this.x+24, this.y+51, 13, 12);
        return r;
    }

    @Override
    public void update() {
        
        esc.update();
        
        this.checkLimits();
        
        if(!isInvencible){
            this.changeDirection();
            shotCount++;
            if(shotCount >= 75){
                this.enemyShot();
                shotCount = 0;
            }
        }
        
        this.Animation();
                
        //System.out.println("x, y: "+x+", "+y);
        //System.out.println("vx, vy: "+vx+", "+vy);
        //System.out.println("Player(x, y): ("+p.x+", "+p.y+")");
    }

    public void checkLimits(){
        
        if(x < 32){
            x = 32;
        }
        if(x > 2464){
            x = 2464;
        }

    }    
    
    public void setSS(String path){
        this.ss = new SpriteSheet(this.i.load(path));
    }
    
    @Override
    public void Animation(){
        
        frameSS = ss.crop2(frameNumber*64, 0, 64, 96);
        
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
