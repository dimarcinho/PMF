
package boss_states.boss1;

import boss_states.Boss;
import boss_states.BossState;
import boss_states.BossStateManager;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

public class Boss1_Hit_State extends BossState {

    private int count;
    
    public Boss1_Hit_State(Boss b, BossStateManager bsm){
        super(b, bsm);
        this.b.vx = 0;
        this.b.vy = 0;
        this.b.isInvencible = true;
        this.b.lifepoints--;
        
        count = 0;
        
        this.b.setFrames(10, 1);
        
        //super.b.setFrames(10, 1);

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
        if(count >= 50){
            b.isInvencible = false;
            bsm.setState(new Boss1_Idle_State(this.b, this.bsm));
        }
        
        b.update();
        
        System.out.println(b.frameNumber+","+this.b.frameNumber);        
        
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
