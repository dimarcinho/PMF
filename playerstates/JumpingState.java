/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package playerstates;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import objects.Player;

public class JumpingState extends PlayerState {
    
    private int currentXdir, lastXdir;
    
    public JumpingState(Player p, PlayerStateManager psm){
        super(p, psm);
        this.id = "JUMPING_STATE";
        init();
    }
    
    @Override
    public void init() {
        
        currentXdir = p.direction;
        if(p.direction == 1){
            p.setFrames(4, 0);
        } else {
            p.setFrames(9, 0);
        }        
        lastXdir = currentXdir;
    
    }
    
    @Override
    public void reset() {}

    @Override
    public void pause() {  
    }

    @Override
    public void resume() {
        
    }

    @Override
    public void update() {
        
        p.vy += p.g;
        
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
        if(currentXdir != lastXdir){
            if(p.direction == 1){
                p.setFrames(4, 0);
            } else {
                p.setFrames(9, 0);
            }
            lastXdir = currentXdir;
        }
    }

    @Override
    public void draw(Graphics g) {
        //desenhar sprites do jumping...        
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
            
        } 
        if(isDown(KeyEvent.VK_DOWN)) {
            
        } 
        if(isDown(KeyEvent.VK_LEFT)) {
            
            //p.vx = -p.speed;            
            p.flyLeft();
        } 
        if(isDown(KeyEvent.VK_RIGHT)){

            //p.vx = +p.speed;            
            p.flyRight();
        }
        
        if(isDown(KeyEvent.VK_SPACE)){
            p.shoot();
        }
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
            p.vx = -1;
        } 
        if(key == KeyEvent.VK_RIGHT){
            p.vx = 1;
        }   
    }


    
}
