/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package boss_states.boss1;

import boss_states.BossState;
import boss_states.BossStateManager;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

/**
 *
 * @author Marcio
 */
public class Boss_Idle_State extends BossState {

    private int count;
    
    public Boss_Idle_State(Boss b, BossStateManager bsm){
        super(b, bsm);
        b.vx = 0;
        b.vy = 0;
        
        this.count = 0;
        
        b.resetAnimation();
    }

    @Override
    public void init() {}

    @Override
    public void reset() {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void update() {
        
        b.update();
        
        count++;
        if(count >= 40){
            bsm.setState(new Boss_Jumping_State(this.b, this.bsm));
        }
        
    }

    @Override
    public void draw(Graphics g) {
        b.draw(g);
    }

    @Override
    public void keyPressed(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}
    
}
