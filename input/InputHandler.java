
package input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class InputHandler implements KeyListener {
    
    private ArrayList<Integer> isPressed;
    //private ArrayList<Integer> isDown;
    
    private Command btnUP;
    private Command btnDown;
    private Command btnLeft;
    private Command btnRight;
    
    public InputHandler(){
        
        isPressed = new ArrayList<>();
        
        
        //Command Pattern em teste de implementação
        btnUP = new JumpCommand();
        
    }
    
    public boolean isDown(int key){
        return isPressed.contains(key);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        
        int key = e.getKeyCode();
        
        if(!isDown(key)){
            isPressed.add(key);
        }        
                
        if(isDown(KeyEvent.VK_UP)){
            btnUP.execute();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        
        int key = e.getKeyCode();
        
        if(isPressed.indexOf(key) != -1){
            isPressed.remove(isPressed.indexOf(key));
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    
    
}
