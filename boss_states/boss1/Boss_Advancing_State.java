/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package boss_states.boss1;

import boss_states.BossState;
import boss_states.BossStateManager;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.Random;

/**
 *
 * @author Marcio
 */
public class Boss_Advancing_State extends BossState {
    
    private int jumps; //define o número de pulos em direção ao player
    private int count; //conta o número de pulos em direção ao player
    private Random rnd;
    private int rndInt = 0;
    
    public Boss_Advancing_State(Boss b, BossStateManager bsm){
        super(b, bsm);
        b.vy = -b.jumpspeed;
                
        this.jumps = 5;
        this.count = 0;
                        
        rnd = new Random();        
        pursuit();
        
        b.resetAnimation();
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
                bsm.setState(new Boss_Idle_State(this.b, this.bsm));
            }
        }
        
        b.update();
    }
    
    private void pursuit(){
        
        //cria aleatoriedade no avanço, dificultando a previsão de comportamento
        rndInt = -5 + rnd.nextInt(10);
        
        if(b.p.x > b.x){
            b.vx = b.speed + rndInt;
        } else {
            b.vx = -b.speed - rndInt;
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

