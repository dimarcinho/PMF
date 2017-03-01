/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package boss_states.boss1;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import objects.Animated;
import objects.EnemyShot;
import objects.Player;
import objects.ShotController;
import objects.SpriteSheet;

/**
 *
 * @author Marcio
 */
public class Boss extends Animated {

    public Player p;
    public int g;
    public int jumpspeed;
    public int lifepoints;
    public boolean isInvencible, isDead;
    public boolean dir, lastXdir;//true - left ; false - right
    
    private int shotCount;
    public ShotController esc;
    
    public Boss(int x, int y, Player p){
        
        this.x = x;
        this.y = y;
        this.p = p;
        
        this.setSS("/res/img/barrel boss.png");
        
        this.setFrames(0, 4);
        
        this.vx = 0;
        this.vy = 0;
        this.g = 1;
        this.jumpspeed = 20;
        this.speed = 5;
        this.frameSpeed = 1;
        
        this.dir = true;
        this.lastXdir = dir;
        
        this.lifepoints = 3;
        
        esc = new ShotController();
        
        this.init();
        
        isInvencible = false;
        isDead = false;
        
    }
    
    @Override
    public void init(){
        shotCount = 0;
    }
    
    public void enemyShot(){
        
        EnemyShot es = new EnemyShot(this.x + 32, this.y+60, shotDir(), 0);
        esc.addShot(es);
        
    }
    
    public void clearShots(){
        esc.shots.clear();
    }
    
    public boolean shotDir(){
        boolean dir = false;
        if(x > p.x)
            dir = true;        
        return dir;
    }
    
    @Override
    public void update() {
        
        esc.update();
        
        this.checkLimits();
        
        if(!isInvencible){
            this.changeDirection();
            shotCount++;
            if(shotCount >= 175){
                this.enemyShot();
                shotCount = 0;
            }
        }
        
        this.Animation();

    }

    @Override
    public void changeDirection() {        
        this.lastXdir = dir;
        
        if(x > p.x){            
            dir = true;
        } else if (x < p.x){            
            dir = false;
        }
        
        if(lastXdir != dir){
            if(x > p.x){
                this.setFrames(0, 4);
            } else {
                this.setFrames(5, 4);
            }
        }
    }
    
    public void resetAnimation(){
        if(x > p.x){            
            dir = false;
            this.setFrames(0, 4);
        } else if (x < p.x){            
            dir = true;
            this.setFrames(5, 4);
        }
        lastXdir = dir;
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
    }
}
