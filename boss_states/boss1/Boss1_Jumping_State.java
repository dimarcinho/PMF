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
public class Boss1_Jumping_State extends BossState {

    public Boss1_Jumping_State(Boss b, BossStateManager bsm){
        super(b, bsm);
        this.b.vy = -b.jumpspeed-10;
    }

    @Override
    public void init() {
        
    }

    @Override
    public void reset() {
        
    }

    @Override
    public void pause() {
        
    }

    @Override
    public void resume() {
        
    }

    @Override
    public void update() {
        b.vy += b.g;
        b.y += b.vy;
        
        if(b.y >= 640 && b.vy != 0){
            b.vy = 0;
            b.y = 640;
            bsm.setState(new Boss1_Advancing_State(this.b, this.bsm));
        }
        b.update();
    }

    @Override
    public void draw(Graphics g) {
        b.draw(g);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        
    }

    @Override
    public void keyReleased(KeyEvent e) {
        
    }

    public void changeDirection() {        
        
        if(b.vx < 0){
            this.b.setFrames(0, 4);
        } else if (b.vx > 0){
            this.b.setFrames(5, 4);
        }
    }
    
}
