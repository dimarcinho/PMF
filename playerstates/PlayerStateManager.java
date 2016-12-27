/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package playerstates;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.LinkedList;

public class PlayerStateManager {
 
    public LinkedList<PlayerState> states;
    
    public PlayerStateManager(){
        states = new LinkedList<PlayerState>();   
    }
    
    public void init(){}
    public void update(){
        states.peek().update();        
        //System.out.println(states.peek().toString());
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
    
    public void setState(PlayerState state){
        if(!states.isEmpty())        
            states.pop();        
        states.push(state);
    }
    
    public PlayerState getState(){
        return this.states.getLast();
    }
            
}
