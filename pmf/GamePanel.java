
package pmf;

import audioEngine.AudioPlayerManager;
import gamestates.*;
import java.awt.event.KeyEvent;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class GamePanel extends JPanel implements ActionListener, KeyListener {
    
    public Timer t;
    public GameStateManager gsm;
   
    private Image dbImage;
    private Graphics dbg;
    String background = "/res/background_sea.png";
        
    private int FPS;
    
    public static AudioPlayerManager amp;
    
    public GamePanel(){
        
        setFocusable(true);
        
        FPS = 60;
        gsm = new GameStateManager();
        
        gsm.setState(new MenuState(this.gsm)); //define o início do jogo
        
        addKeyListener(this);
        
        amp = new AudioPlayerManager();
        
        init();
                        
        t = new Timer(10, this);
        t.start();
        
    }
    
    public void init(){}    
    
    @Override
    public void paint(Graphics g){        
        dbImage = createImage(getWidth(),getHeight());
        dbg = dbImage.getGraphics();
        paintComponent(dbg);
        g.drawImage(dbImage,0,0,this);
    }
    
    @Override
    public void paintComponent(Graphics g){  
        
        gsm.draw(g);

    }
    
    public Image getBackgroundImage(){
        ImageIcon i = new ImageIcon(getClass().getResource(background));
        return i.getImage();        
    }
    
    public void update(){}

    @Override
    public void actionPerformed(ActionEvent e) {
        
        //GAME LOOP
        try{
           
            long startTime, endTime;
            startTime = System.currentTimeMillis();        
            gsm.update();            
            endTime = System.currentTimeMillis();
            while(endTime - startTime < 1000/FPS ){
                endTime = System.currentTimeMillis();
            }            
            
        } catch(Exception s) {
            throw new IllegalStateException("Game has a Exception at GameLoop: ", s);
        }
        
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        gsm.keyPressed(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        gsm.keyReleased(e);
    }
    
}
