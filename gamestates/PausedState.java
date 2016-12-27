/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gamestates;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import pmf.PMF;

/**
 *
 * @author Marcio
 */
public class PausedState extends GameState {
    
    public PausedState(GameStateManager gsm){
        super(gsm);
        init();
    }

    
    public void init() {}
    
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

    }

    @Override
    public void draw(Graphics g) {
        //desenha o fundo
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(0, 0, PMF.WIDTH, PMF.HEIGHT);
        
        //desenha o menu
        g.setColor(Color.red);        
        g.setFont(new Font("Arial", Font.ITALIC, 48));
        g.drawString("JOGO PAUSADO", PMF.WIDTH/2 - 200, PMF.HEIGHT/2);

    }


    @Override
    public void keyPressed(KeyEvent e) {
        int k = e.getKeyCode();
        
        if(e.getKeyCode() == KeyEvent.VK_P){
            gsm.states.pop();
        }
        
    }

    @Override
    public void keyReleased(KeyEvent e) {
        
    }
    
}
