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
        
        input.update();
                
        p.vy += p.g;
        
        p.x += p.vx;
        p.y += p.vy;
        
        p.checkLimits();
        
        p.changeDirection();
        updateAnimation();
        p.Animation();
        
        p.sc.update();
        
        handleInput();
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
    
    public void handleInput(){
        
        for(int i=0; i < input.isDown.size(); i++){
            
            if(input.isDown.get(i) == input.KEY_UP){

            } 
            if(input.isDown.get(i) == input.KEY_DOWN){

            } 
            if(input.isDown.get(i) == input.KEY_LEFT){

                p.flyLeft();
            } 
            if(input.isDown.get(i) == input.KEY_RIGHT){

                p.flyRight();
            }            
        }
        
        if(input.isPressed(input.KEY_SPACE)){
            p.shoot();            
        }
    }

    @Override
    public void keyPressed(KeyEvent e){
        
        input.keyPressed(e);        
    }
    
    @Override
    public void keyReleased(KeyEvent e){
                
        input.keyReleased(e);
        
        int key = e.getKeyCode();
                
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
