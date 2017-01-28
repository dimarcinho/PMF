/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package playerstates;

import audioEngine.AudioPlayer;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import objects.Player;

public class JumpingState extends PlayerState {
    
    public JumpingState(Player p, PlayerStateManager psm){
        super(p, psm);        
        init();
        
        this.id = "JUMPING_STATE";
    }
    
    @Override
    public void init() {
    
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
        p.Animation();
        p.changeDirection();
        p.sc.update();
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
        
        if(key == KeyEvent.VK_UP){
            
        } 
        if(key == KeyEvent.VK_DOWN) {
            
        } 
        if(key == KeyEvent.VK_LEFT) {
            
            //p.vx = -p.speed;            
            p.flyLeft();
        } 
        if(key == KeyEvent.VK_RIGHT){

            //p.vx = +p.speed;            
            p.flyRight();
        }
        
        if(key == KeyEvent.VK_SPACE){
            p.shoot();
        }
    }
    
    @Override
    public void keyReleased(KeyEvent e){
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
