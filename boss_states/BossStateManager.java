/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package boss_states;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.LinkedList;

public class BossStateManager {
 
    public LinkedList<BossState> states;
    
    public BossStateManager(){
        states = new LinkedList<BossState>();   
    }
    
    public void init(){}
    public void update(){
        states.peek().update();        
    }
    public void draw(Graphics g){
        states.peek().draw(g);
    }
    public void keyPressed(KeyEvent e){
        states.peek().keyPressed(e);
    }
    public void keyReleased(KeyEvent e){    
        states.peek().keyReleased(e);
    }
    
    public void setState(BossState state){
        if(!states.isEmpty())        
            states.pop();        
        states.push(state);
    }
    
    public BossState getState(){
        return this.states.getLast();
    }
            
}
