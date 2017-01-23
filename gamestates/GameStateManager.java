
package gamestates;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.LinkedList;

public class GameStateManager {
 
    public LinkedList<GameState> states;
    
    public GameStateManager(){
        states = new LinkedList<>();       
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
    
    public void addState(GameState state){
        if(state != null)        
            states.push(state);
    }
    
    public void removeState(GameState state){
        if(state != null)        
            states.pop();
    }
    
    
    public void setState(GameState state){
        if(!states.isEmpty())        
            states.pop();
        states.push(state);
    }
    
    public GameState getState(){
        return this.states.getLast();
    }
            
}
