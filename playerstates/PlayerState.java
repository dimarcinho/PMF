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
public abstract class PlayerState {
    
    public PlayerStateManager psm;
    public Player p;
    
    public PlayerState(Player player, PlayerStateManager psm){
        this.p = player;
        this.psm = psm;
        init();
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
