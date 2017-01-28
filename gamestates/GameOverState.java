
package gamestates;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import objects.Score;
import pmf.PMF;


public class GameOverState extends GameState {
    
    private int count = 0;
    private int lastPoints = 0;
    
    public GameOverState(GameStateManager gsm){
        super(gsm);
        init();
    }

    
    @Override
    public void init() {
    
        Score score = new Score();
        lastPoints = score.getPoints();
        score.reset();
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
                
        count++;
        if(count > 600){
            gsm.states.add(new MenuState(this.gsm));
        }

    }

    @Override
    public void draw(Graphics g) {
        
        //desenha o fundo
        g.setColor(Color.cyan);
        g.fillRect(0, 0, PMF.WIDTH, PMF.HEIGHT);
        
        //desenha o menu
        g.setColor(Color.red);        
        g.setFont(new Font("Arial", Font.ITALIC, 48));
        g.drawString("GAME OVER", PMF.WIDTH/2 - 200, PMF.HEIGHT/2);
        
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
