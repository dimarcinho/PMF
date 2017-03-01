
package boss_states.boss1;

import boss_states.BossState;
import boss_states.BossStateManager;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import objects.ParticleGenerator;

public class Boss_Hit_State extends BossState {

    private int count;
    private ParticleGenerator pg;
    
    public Boss_Hit_State(Boss b, BossStateManager bsm){
        
        super(b, bsm);
        b.vx = 0;
        b.vy = 0;
        b.isInvencible = true;
        b.lifepoints--;
        
        count = 0;
        
        b.setFrames(10, 1);

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
        
                                
        if(b.lifepoints <= 0){
            bsm.states.peek().b.clearShots();
            bsm.setState(new Boss_Death_State(this.b,this.bsm));
        }
        
        count++;
        if(count >= 120){
            b.isInvencible = false;
            bsm.setState(new Boss_Jumping_State(this.b, this.bsm));
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
