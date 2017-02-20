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
import java.util.Random;
import javax.swing.ImageIcon;
import objects.ParticleGenerator;
import pmf.GamePanel;
import pmf.PMF;

/**
 *
 * @author Marcio
 */
public class BeatGameState extends GameState {
    
    private int count = 0;
    
    private Image background_1, background_2;
    
    String background = "/res/img/background_sky.png";
    String bg2 = "/res/img/background_sea.png";
    
    private ParticleGenerator pg;
    
    private Random rnd;
    
    public BeatGameState(GameStateManager gsm){
        super(gsm);
        background_1 = this.getBackgroundImage(background);
        background_2 = this.getBackgroundImage(bg2);
        
        pg = new ParticleGenerator(0,0,0);
        
        rnd = new Random();
        
        init();
    }

    @Override
    public void init() {

        GamePanel.amp.stopAllSounds();
        GamePanel.amp.loop("/res/audio/music/09_green_forest.mp3");
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
        
        pg.update();
        if(count % 60 == 0){
            this.fireworks();
            count = 0;
        }
        count++;
        
    }
    
    public void fireworks(){
                        
        //for(int i = 0; i < 10; i++){
            int x, y;
            x = rnd.nextInt(PMF.WIDTH);
            y = rnd.nextInt(PMF.HEIGHT);
            
            for(int j = 0; j < 120; j++){
                
                int vx = rnd.nextInt(16)-8;
                int vy = rnd.nextInt(16)-8;

                while(vx == 0 && vy == 0){
                    vx = rnd.nextInt(16)-8;
                    vy = rnd.nextInt(16)-8; 
                }

                int size = rnd.nextInt(3);
                int life = rnd.nextInt(10)+120;            

                pg.addParticles(x, y, vx, vy, size, life, Color.black);
            }
        //}
        
    }

    @Override
    public void draw(Graphics g) {
        //desenha o fundo
        g.fillRect(0, 0, PMF.WIDTH, PMF.HEIGHT);
        g.drawImage(background_1, 0, 0, null);
        g.drawImage(background_2, 0, 600, null);
        
        //fireworks
        pg.draw(g);
        
        //desenha o menu        
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 28));
        
        g.drawString("PARABÉNS", PMF.WIDTH/3+100, PMF.HEIGHT/4 + 60*2);
        g.drawString("você concluiu o jogo!", PMF.WIDTH/3 + 50, PMF.HEIGHT/4 + 60*3);
        
        g.setColor(Color.white);
        g.setFont(new Font("Arial", Font.BOLD, 14));
        g.drawString("Pressione ESC para sair ou ENTER para reiniciar o jogo", PMF.WIDTH/3, PMF.HEIGHT/4 + 60*4);       
        
        
    }
    
    public Image getBackgroundImage(String bg){
        ImageIcon i = new ImageIcon(getClass().getResource(bg));
        return i.getImage();        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        
        int k = e.getKeyCode();
        
        if(k == KeyEvent.VK_ENTER){
            GamePanel.amp.stopAllSounds();
            gsm.states.push(new MenuState(gsm));
        }        
        
        if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
            System.exit(0);
        }
        
    }

    @Override
    public void keyReleased(KeyEvent e) {
        
    }
    
}
