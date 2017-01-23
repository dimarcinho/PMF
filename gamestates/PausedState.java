/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gamestates;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import pmf.PMF;

/**
 *
 * @author Marcio
 */
public class PausedState extends GameState {
    
    private Image background_1, background_2;
    
    String background = "/res/img/background_sky.png";
    String bg2 = "/res/img/background_sea.png";
    
    public PausedState(GameStateManager gsm){
        super(gsm);
        
        background_1 = this.getBackgroundImage(background);
        background_2 = this.getBackgroundImage(bg2);
        
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
        g.fillRect(0, 0, PMF.WIDTH, PMF.HEIGHT);
        g.drawImage(background_1, 0, 0, null);
        g.drawImage(background_2, 0, 600, null);
        
        //desenha o menu
        g.setColor(Color.red);        
        g.setFont(new Font("Arial", Font.ITALIC, 48));
        g.drawString("JOGO PAUSADO", PMF.WIDTH/2 - 200, PMF.HEIGHT/2);
        
    }
    
    public Image getBackgroundImage(String bg){
        ImageIcon i = new ImageIcon(getClass().getResource(bg));
        return i.getImage();        
    }


    @Override
    public void keyPressed(KeyEvent e) {
        int k = e.getKeyCode();
        
        if(e.getKeyCode() == KeyEvent.VK_P){
            gsm.states.pop();
        }
        
        if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
            System.exit(0);
        }
        
    }

    @Override
    public void keyReleased(KeyEvent e) {
        
    }
    
}
