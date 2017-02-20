
package playerstates;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.LinkedList;
import observerpattern.Observer;

public class PlayerStateManager implements Observer {
 
    public LinkedList<PlayerState> states;
    
    public PlayerStateManager(){
        states = new LinkedList<>();   
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
    
    public void addState(PlayerState state){
        states.push(state);
        System.out.println(states.peek().toString());
    }
    
    public void setState(PlayerState state){
        if(!states.isEmpty())        
            states.pop();        
        states.push(state);
        System.out.println(states.peek().toString());
    }
    
    public PlayerState getState(){
        return this.states.getLast();        
    }

    @Override
    public void onNotify(String s) {
        if(s.equals("PLAYER_HURT")){
            //criar estado para HURT
            states.peek().p.hurt();
        }
        
    }
            
}
