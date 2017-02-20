/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package playerstates;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import objects.Player;

/**
 *
 * @author Marcio
 */
public class WalkingState extends PlayerState {
    
    private int currentXdir, lastXdir;
    
    public WalkingState(Player p, PlayerStateManager psm){
        super(p, psm);        
        init();

        this.id = "WALKING_STATE";
    }


    @Override
    public void init() {
        p.vy = 0;
        
        currentXdir = p.direction;
        if(p.direction == 1){
            p.setFrames(2, 1);
        } else {
            p.setFrames(7, 1);
        }        
        lastXdir = currentXdir;
    }
    
    @Override
    public void reset() {
        
    }

    @Override
    public void pause() {
        
    }

    @Override
    public void resume() {
        
    }

    @Override
    public void update() {
        
        p.vy = 0;
        
        p.vx += p.accX;
        
        if(Math.abs(p.vx) >= p.vxmax){
            if(p.vx > 0){
                p.vx = p.vxmax;
            } else {
                p.vx = -p.vxmax;
            }   
        }
        
        p.x += p.vx;        
        p.y += p.vy;
        
        p.checkLimits();
        
        p.changeDirection();
        updateAnimation();
        p.Animation();
        
        p.sc.update();
        
        if(p.vx == 0 && p.vy == 0){
            System.out.println("Walking --> Standing;");
            this.psm.setState(new StandingState(this.p, this.psm)); 
        }

    }
    
    public void updateAnimation(){
        currentXdir = p.direction;
        if(currentXdir != lastXdir){
            if(p.direction == 1){
                p.setFrames(2, 1);
            } else {
                p.setFrames(7, 1);
            }
            lastXdir = currentXdir;
        }
    }

    @Override
    public void draw(Graphics g) {
        //desenhar sprites do standing...
        p.draw(g);
        p.sc.draw(g);
    }

    @Override
    public void keyPressed(KeyEvent e){
        int key = e.getKeyCode();
        
        if(!isDown(key)){
            isPressed.add(key);
        }
        
        if(isDown(KeyEvent.VK_UP)){
            p.jump(id);
            this.psm.setState(new JumpingState(this.p, this.psm));
        } 
        if(isDown(KeyEvent.VK_DOWN)) {
            
        } 
        if(isDown(KeyEvent.VK_LEFT)) {
            p.moveLeft();
        } 
        if(isDown(KeyEvent.VK_RIGHT)){
            p.moveRight();
        }
        
        if(isDown(KeyEvent.VK_SPACE)){
            p.shoot();
        }
        /*
        if(key == KeyEvent.VK_UP){
            
            p.jump(id);
            this.psm.setState(new JumpingState(this.p, this.psm));
            
        } else if(key == KeyEvent.VK_DOWN) {            
                       
        } else if(key == KeyEvent.VK_LEFT) { 
            
            p.moveLeft();

        } else if(key == KeyEvent.VK_RIGHT){

            p.moveRight();

        } 
        
        if(key == KeyEvent.VK_SPACE){
            p.shoot();
        }
        */
    }
    
    @Override
    public void keyReleased(KeyEvent e){
        int key = e.getKeyCode();
        
        if(isPressed.indexOf(key) != -1){
            isPressed.remove(isPressed.indexOf(key));
        }
        
        if(key == KeyEvent.VK_UP){

        } 
        if(key == KeyEvent.VK_DOWN) {

        } 
        if(key == KeyEvent.VK_LEFT) {
            p.vx = 0;
            p.accX = 0;
        } 
        if(key == KeyEvent.VK_RIGHT){
            p.vx = 0;
            p.accX = 0;
        }   
    }


    
}

