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

        this.id = "WALKING_STATE";
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
        p.Animation();
        p.changeDirection();
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

