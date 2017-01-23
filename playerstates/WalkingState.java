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
    
    public WalkingState(Player p, PlayerStateManager psm){
        super(p, psm);        
        init();        
    }


    @Override
    public void init() {
        p.vy = 0;
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
        p.sc.update();
        
        if(p.vx == 0 && p.vy == 0)
            this.psm.setState(new StandingState(this.p, this.psm)); 

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
        
        if(key == KeyEvent.VK_UP){

            p.vy -= (int)(p.jumpspeed*1.1);
            System.out.println("UP!");
            this.psm.setState(new JumpingState(this.p, this.psm));
            
        } else if(key == KeyEvent.VK_DOWN) {            
                       
        } else if(key == KeyEvent.VK_LEFT) { 
            
            p.accX = -p.acc0;            

        } else if(key == KeyEvent.VK_RIGHT){

            p.accX = +p.acc0;            

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
            p.vx = 0;
            p.accX = 0;
        } 
        if(key == KeyEvent.VK_RIGHT){
            p.vx = 0;
            p.accX = 0;
        }   
    }


    
}

