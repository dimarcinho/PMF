
package gamestates;

import java.awt.Graphics;
import java.awt.event.KeyEvent;

public abstract class GameState {
    
    public GameStateManager gsm;
    
    public GameState(GameStateManager gsm){
        this.gsm = gsm;
    }
    
    public abstract void init();
    public abstract void reset();
    public abstract void pause();
    public abstract void resume();
    public abstract void update();
    public abstract void draw(Graphics g);
    public abstract void keyPressed(KeyEvent e);
    public abstract void keyReleased(KeyEvent e);
}
