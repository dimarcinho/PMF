/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package playerstates;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import objects.Player;

public class StandingState extends PlayerState {
    
    public StandingState(Player p, PlayerStateManager psm){
        super(p, psm);        
        init();    
    }
    
    @Override
    public void init() {
        p.vy = 0;
        p.vx = 0;
        p.accX = 0;
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
        
        p.x += p.vx;
        p.y += p.vy;
        
        p.checkLimits();

    }

    @Override
    public void draw(Graphics g) {
        //desenhar sprites do standing...
        p.draw(g);
    }

    @Override
    public void keyPressed(KeyEvent e){
        int key = e.getKeyCode();
        
        if(key == KeyEvent.VK_UP){

            p.vy -= p.jumpspeed;
            System.out.println("UP!");
            this.psm.setState(new JumpingState(this.p, this.psm));                
            
        } 
        if(key == KeyEvent.VK_DOWN) {           
           //ducking...
            
        } 
        if(key == KeyEvent.VK_LEFT) { 
            p.accX = -p.acc0;
            p.vx += p.accX;
            this.psm.setState(new WalkingState(this.p, this.psm));        

        } 
        if(key == KeyEvent.VK_RIGHT){
            p.accX = +p.acc0;
            p.vx += p.accX;
            this.psm.setState(new WalkingState(this.p, this.psm)); 

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
            //p.vx = 0;
        } 
        if(key == KeyEvent.VK_RIGHT){
            //p.vx = 0;
        }   
    }

    
}
