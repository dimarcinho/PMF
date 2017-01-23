/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package boss_states.boss1;

import boss_states.BossState;
import boss_states.BossStateManager;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

public class Boss_Jumping_State extends BossState {

    public Boss_Jumping_State(Boss b, BossStateManager bsm){
        super(b, bsm);
        b.vy = -b.jumpspeed-10;
        
        b.resetAnimation();
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
            bsm.setState(new Boss_Advancing_State(this.b, this.bsm));
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
    
}
