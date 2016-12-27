/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package boss_states.boss1;

import boss_states.Boss;
import boss_states.BossState;
import boss_states.BossStateManager;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

/**
 *
 * @author Marcio
 */
public class Boss1_Idle_State extends BossState {

    private int count;
    
    public Boss1_Idle_State(Boss b, BossStateManager bsm){
        super(b, bsm);
        this.b.vx = 0;
        this.b.vy = 0;
        
        this.count = 0;
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
        
        count++;
        if(count >= 120){
            bsm.setState(new Boss1_Jumping_State(this.b, this.bsm));
        }        
        
        b.update();
        
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
