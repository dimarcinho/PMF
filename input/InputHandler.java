
package input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import observerpattern.Observer;
import observerpattern.Subject;

public class InputHandler extends InputKeys implements KeyListener, Subject {
    
    public ArrayList<Integer> isPressed;
    public ArrayList<Integer> isDown;
    
    private ArrayList<Observer> observers;
    private ArrayList<Command> commands;
    
    private Command btnUp;
    private Command btnDown;
    private Command btnLeft;
    private Command btnRight;
    private Command btnSpace;
    
    public InputHandler(){
        
        isPressed = new ArrayList<>();
        isDown = new ArrayList<>();
        commands = new ArrayList<>();
        observers = new ArrayList<>();
        
        //Command Pattern em teste de implementação
        btnUp = new JumpCommand();
        btnDown = new NullCommand();
        btnLeft = new MoveLeftCommand();
        btnRight = new MoveRightCommand();
        btnSpace = new ShootCommand();
        
    }
    
    public void update(){
        
    }
    
    public boolean isDown(int key){        
        return(isDown.indexOf(key) != -1);        
    }
    
    public boolean isPressed(int key){
        if(isPressed.indexOf(key) != -1){
            isPressed.remove(isPressed.indexOf(key));
            return true;
        }
        return false;
    }

    @Override
    public void keyPressed(KeyEvent e){
        
        int key = e.getKeyCode();
        
        if(isDown.indexOf(key) == -1){            
            isPressed.add(key);
            isDown.add(key);            
        }
    }

    @Override
    public void keyReleased(KeyEvent e){
        
        int key = e.getKeyCode();
        
        if(isPressed.indexOf(key) != -1){
            isPressed.remove(isPressed.indexOf(key));            
        }
        
        if(isDown.indexOf(key) != -1){
            isDown.remove(isDown.indexOf(key));            
        }
    }

    @Override
    public void keyTyped(KeyEvent e){
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void addObserver(Observer o) {
        observers.add(o);
    }

    @Override
    public void removeObserver(Observer o) {
        observers.remove(o);
    }

    @Override
    public void notify(String s) {
        for(Observer o: observers){
            o.onNotify(s);
            System.out.println("Evento "+s+" enviado para :"+o);
            
        }
    }
    
    
}
