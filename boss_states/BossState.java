
package boss_states;

import boss_states.boss1.Boss;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

public abstract class BossState {
    
    public BossStateManager bsm;
    public Boss b;
    
    public BossState(Boss b, BossStateManager bsm){
        this.b = b;
        this.bsm = bsm;
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
