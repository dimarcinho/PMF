
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
        
        pg = new ParticleGenerator(0,0,0);
        pg.addBasicParticles(b.x+32,b.y+64,30);

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
            bsm.setState(new Boss_Death_State(this.b,this.bsm));
        }
        
        count++;
        if(count >= 120){
            b.isInvencible = false;
            bsm.setState(new Boss_Jumping_State(this.b, this.bsm));
        }
        
        b.update();
        pg.update();
        
        System.out.println(b.frameNumber+","+this.b.frameNumber);
        System.out.println(b.lifepoints);
        
    }

    @Override
    public void draw(Graphics g) {
        b.draw(g);
        pg.draw(g);
    }

    @Override
    public void keyPressed(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}
    
}
