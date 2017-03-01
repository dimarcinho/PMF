
package playerstates;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import objects.Player;

public class DeadState extends PlayerState {
    
    public DeadState(Player p, PlayerStateManager psm){
        super(p, psm);
        this.id = "DEAD_STATE";
        init();
    }

    @Override
    public void init() {
        p.setFrames(10, 2);//10,2
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
        
        p.update();
        this.updateAnimation();
    }
    
    public void updateAnimation(){
        if(p.frameNumber == 12){
            p.setFrames(12,0);
        }
    }

    @Override
    public void draw(Graphics g) {
        p.draw(g);
        //p.sc.draw(g);
    }

    @Override
    public void keyPressed(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}
}
