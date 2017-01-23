
package input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class InputHandler implements KeyListener {
    
    private Command btnUP;
    private Command btnDown;
    private Command btnLeft;
    private Command btnRight;
    
    public InputHandler(){
        
        btnUP = new JumpCommand();
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        
        int key = e.getKeyCode();
        
        if(key == KeyEvent.VK_UP){
            btnUP.execute();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void keyTyped(KeyEvent e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    
    
}
