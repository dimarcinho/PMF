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
public class Boss1_Advancing_State extends BossState {
    
    private int jumps; //define o número de pulos em direção ao player
    private int count; //conta o número de pulos em direção ao player
    
    public Boss1_Advancing_State(Boss b, BossStateManager bsm){
        super(b, bsm);
        this.b.vy = -b.jumpspeed;
        pursuit();
        
        this.jumps = 2;
        this.count = 0;
    }

    @Override
    public void init() {}

    @Override
    public void reset() {}

    @Override
    public void pause() {}

    @Override
    public void resume(){}

    @Override
    public void update() {
        
        b.vy += b.g;
        
        b.x += b.vx;
        b.y += b.vy;
        
        if(b.y >= 640 && b.vy != 0){
            count++;
            if(count < jumps){
                b.y = 640;
                b.vy = -b.jumpspeed;
                this.pursuit();
            } else {
                bsm.setState(new Boss1_Idle_State(this.b, this.bsm));
            }
        }
        
        b.update();
    }
    
    public void pursuit(){
        
        if(b.p.x > b.x){
            b.vx = b.speed;
        } else {
            b.vx = -b.speed;
        }        

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

