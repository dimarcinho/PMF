/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package playerstates;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import objects.Player;

public class StandingState extends PlayerState {
    
    private int currentXdir, lastXdir;
    
    public StandingState(Player p, PlayerStateManager psm){
        super(p, psm);        
        this.init();
        
        this.id = "STANDING_STATE";
    }
    
    @Override
    public void init() {
        p.vy = 0;
        p.vx = 0;
        p.accX = 0;
        
        currentXdir = p.direction;
        
        if(p.direction == 1){
            p.setFrames(0, 1);
        } else {
            p.setFrames(5, 1);
        }        
        
        System.out.println("Entrando em "+this.id+" pelo init()");
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
        
        p.vy = p.g;
        p.vy = 0;
        
        p.x += p.vx;
        p.y += p.vy;
        
        p.checkLimits();
        
        p.changeDirection();
        updateAnimation();
        p.Animation();
        
        p.sc.update();


    }
    
        
    public void updateAnimation(){
        currentXdir = p.direction;
        if(!p.isInvincible){            
            if(currentXdir != lastXdir){
                if(p.direction == 1){
                    p.setFrames(0, 1);
                } else {
                    p.setFrames(5, 1);
                }                
            }
        }
        lastXdir = currentXdir;
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
            this.psm.setState(new WalkingState(this.p, this.psm)); 
        } 
        if(isDown(KeyEvent.VK_RIGHT)){
            p.moveRight();
            this.psm.setState(new WalkingState(this.p, this.psm)); 
        }
        
        if(isDown(KeyEvent.VK_SPACE)){
            p.shoot();
        }        
        
        /*
        if(key == KeyEvent.VK_UP){

            p.jump(id);
            //System.out.println("UP!");
            this.psm.setState(new JumpingState(this.p, this.psm));                
            
        } 
        if(key == KeyEvent.VK_DOWN) {           
           //ducking...
            
        } 
        if(key == KeyEvent.VK_LEFT) { 
            p.moveLeft();
            this.psm.setState(new WalkingState(this.p, this.psm));        

        } 
        if(key == KeyEvent.VK_RIGHT){
            p.moveRight();
            this.psm.setState(new WalkingState(this.p, this.psm)); 

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
            //p.vx = 0;
        } 
        if(key == KeyEvent.VK_RIGHT){
            //p.vx = 0;
        }   
    }

    
}
