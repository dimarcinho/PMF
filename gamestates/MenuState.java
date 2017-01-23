
package gamestates;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import pmf.PMF;

public class MenuState extends GameState {
    
    private String[] option = {"Iniciar", "Sair"};
    private int select = 0;
    
    private Image fpsoImg, background_1, background_2;
    
    String background = "/res/img/background_sky.png";
    String bg2 = "/res/img/background_sea.png";
    
    String fpso = "/res/img/FPSO.gif";
    
    public MenuState(GameStateManager gsm){
        super(gsm);
        
        background_1 = this.getBackgroundImage(background);
        background_2 = this.getBackgroundImage(bg2);
        
        
        fpsoImg = this.getBackgroundImage(fpso);
        
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
        //g.setColor(Color.ORANGE);
        g.fillRect(0, 0, PMF.WIDTH, PMF.HEIGHT);
        g.drawImage(background_1, 0, 0, null);
        g.drawImage(fpsoImg, 150, 500, null);
        g.drawImage(background_2, 0, 600, null);        
        
        //desenha o menu
        g.setColor(Color.DARK_GRAY);        
        g.setFont(new Font("Arial", Font.ITALIC, 48));
        g.drawString("PMF game", PMF.WIDTH/2 - 120, 100);
        g.setFont(new Font("Arial", Font.ITALIC, 14));
        g.drawString("Entregue as SERTECs!", PMF.WIDTH/2 - 70, 140);
        
        g.setColor(Color.white);
        g.setFont(new Font("Arial", Font.BOLD, 28));
        
        for(int i = 0; i < option.length; i++){
            if(select == i)
                g.setColor(Color.yellow);
            else
                g.setColor(Color.white);
            
            g.drawString(option[i], PMF.WIDTH/2 - 50, PMF.HEIGHT/2 + 60*i);
        }
    }
    
    public Image getBackgroundImage(String bg){
        ImageIcon i = new ImageIcon(getClass().getResource(bg));
        return i.getImage();        
    }


    @Override
    public void keyPressed(KeyEvent e) {
        int k = e.getKeyCode();
        
        if(k == KeyEvent.VK_ENTER){
            if(select == 0){
                gsm.states.push(new Level1State(gsm));
                //gsm.states.push(new Boss1LevelState(gsm));
                //System.out.println("Em desenvolvimento...");
            } else {
                System.exit(0);
            }
        }
        
        if(k == KeyEvent.VK_UP || k == KeyEvent.VK_DOWN){
            if(select == 0)
                select = 1;
            else
                select = 0;
        }
        
        if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
            System.exit(0);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        
    }
    
}
