
package boss_states.boss1;

import boss_states.BossState;
import boss_states.BossStateManager;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.Random;
import objects.ParticleGenerator;

public class Boss_Death_State extends BossState {

    private int count;
    private ParticleGenerator pg;
    
    public Boss_Death_State(Boss b, BossStateManager bsm){
        
        super(b, bsm);
        
        b.vx = 0;
        b.vy = 0;
        b.isInvencible = true;
        
        b.setFrames(11, 0);
        
        count = 0;
        
        pg = new ParticleGenerator(0,0,0);
        
    }
    
    private void deathExplosions(int n){
        
        for(int i = 0; i < n; i++){
            
            Color c = new Color(0,0,0);            
            
            Random rnd = new Random();
            int xp, yp, vxp, vyp, size, life;
            xp = b.x + rnd.nextInt(55) + 5;
            yp = b.y + rnd.nextInt(87) + 5;
            
            vxp = -7 + rnd.nextInt(14);
            vyp = -7 + rnd.nextInt(14);
            while(vxp == 0 && vyp == 0){
                vxp = -7 + rnd.nextInt(14);
                vyp = -7 + rnd.nextInt(14);
            }
            
            size = 2+rnd.nextInt(1);
            life = 100+rnd.nextInt(100);
            
            pg.addParticles(xp, yp, vxp, vyp, size, life, c);
        }
        
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
        if(count % 3 == 0 && count < 146){            
            deathExplosions(80);            
        }
        
        b.update();
        pg.update();
        /*
         *é necessário colocar o contador do loop, caso contrário ele irá dar
         *true em isEmpty equanto as partículas não forem geradas (pois o vetor
         *já começa vazio) devido ao count % k;
         *pra funcionar é necessário que count > k+1
         * 
         */
        if(pg.particles.isEmpty() &&  count > 4){            
            //define novo estado
            b.isDead = true;
            System.out.println("pular para próxima fase");
        }
        
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
