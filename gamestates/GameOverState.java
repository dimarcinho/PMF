
package gamestates;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import objects.Fire;
import objects.Score;
import objects.Player;
import pmf.GamePanel;
import pmf.PMF;


public class GameOverState extends GameState {
        
    private int lastPoints = 0;
    private Image GameOver;
    private ArrayList<Fire> flame = new ArrayList<>();
    
    private int timerWait, timerFade;
    private int waitingTime, fadingTime;
    private boolean isExiting = false;
    
    public GameOverState(GameStateManager gsm){
        super(gsm);        
        init();
        
        waitingTime = 30;
        fadingTime = 180;
        
        for(int i = 0; i < 5; i++){
            //flame.add(new Fire(2*i*100,535,200,1));
        }
        
        flame.add(new Fire(-10,-465,1200,1));
        
        GameOver = getImage("/res/img/game_over.png");      
    }

    
    @Override
    public void init() {
    
        Score score = new Score();
        lastPoints = score.getPoints();
        score.reset();
        
        Player p = new Player(0,0);
        p.setLifes(2);
        
        GamePanel.amp.stopAllSounds();
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
        
        for(Fire f : flame){
            f.update();
        }
        
        if(timerWait < waitingTime){
            timerWait++;            
        } else {
            isExiting = true;
        }
        
        if(isExiting){
            if(timerFade < fadingTime){
                timerFade++;   
            } else {
                Player p = new Player(0,0);
                p.setLifes(2);
                gsm.setState(new MenuState(this.gsm));
            }
        }
    }

    @Override
    public void draw(Graphics g) {
        
        //desenha o fundo
        g.setColor(Color.black);
        g.fillRect(0, 0, PMF.WIDTH, PMF.HEIGHT);        
        for(Fire f : flame){
            f.draw(g);
        }
        
        //desenha o menu
        g.drawImage(GameOver, PMF.WIDTH/2 - 200, 
                    PMF.HEIGHT/2 - 50, 
                    GameOver.getWidth(null), 
                    GameOver.getHeight(null),
                    null);        
        
        //fade de saída
        if(isExiting){
            float a = ((float)timerFade/(float)fadingTime);            
            g.setColor(new Color(.1f,.0f,.0f,a));
            g.fillRect(0, 0, PMF.WIDTH, PMF.HEIGHT);
        }
    }
    
    public Image getImage(String path){
        ImageIcon i = new ImageIcon(getClass().getResource(path));
        return i.getImage();        
    }


    @Override
    public void keyPressed(KeyEvent e) {
        int k = e.getKeyCode();        
        
        if(k == KeyEvent.VK_ENTER){
            GamePanel.amp.stopAllSounds();
            gsm.setState(new MenuState(gsm));
        }        
        
        if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
            System.exit(0);
        }
        
        
    }

    @Override
    public void keyReleased(KeyEvent e) {}
    
}
