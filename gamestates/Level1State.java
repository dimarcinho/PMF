
package gamestates;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import objects.*;
import observerpattern.*;
import playerstates.*;
import pmf.GamePanel;
import pmf.PMF;


public class Level1State extends GameState implements Subject {
    
    //public Player player;    
    public PlayerStateManager psm;
    public MapTile map;
    public Camera camera;
    public Score score;
    public LifePoints lifepoints;
    public Collisions collisions;
    public EnemyController ec;
    public ItemController ic;
    
    
    private ArrayList<Observer> observers;

    String background = "/res/img/background_sky.png";
    //String background = "/res/img/background_pink.png";
    String bg1 = "/res/img/background_sea.png";
    
    public Item greenFlag;
    
    private int exitCounter = 0, exitMax = 30, enterCounter = 0, enterMax = 30;
    private boolean isExiting = false, isEnter = false;    
    
    public Fire fire;
    
    public Level1State(GameStateManager gsm){
        super(gsm);
        
        observers = new ArrayList<>();
        score = new Score();        
        
        //player = new Player(5950, 10);
        //player = new Player(100, 250);
        psm = new PlayerStateManager();        
        psm.setState(new StandingState(new Player(100,240), this.psm));
        
        map = new MapTile(32, "/res/img/levelteste.png");
        //map = new MapTile(32, "/res/img/levelteste2.png");
        camera = new Camera(psm.getPlayer(), map);
        
        lifepoints = new LifePoints(psm.getPlayer());
        
        ec = new EnemyController();
        ic = new ItemController();
        collisions = new Collisions(psm, map, ec, ic);
        
        //adiciona os inimigos direto do mapa
        for(int i = 0; i < map.row; i++){
            for(int j = 0; j < map.col; j++){                
                if(map.getTile(i, j) == 5){
                    ec.addEnemy(new EnemyA(map.tsize*j, map.tsize*i));
                }
            }
        }
        
        ec.addEnemy(new SDVEnemy(10*map.tsize, 13*map.tsize));
        ec.addEnemy(new PumpEnemy(15*map.tsize, 13*map.tsize));
        
        greenFlag = new Item(197*map.tsize,9*map.tsize,1);
        
        ic.addItem(greenFlag);

        ic.addItem(new Item(19*map.tsize,12*map.tsize,0));
        ic.addItem(new Item(18*map.tsize,12*map.tsize,0));
        ic.addItem(new Item(17*map.tsize,12*map.tsize,0));
        ic.addItem(new Item(16*map.tsize,12*map.tsize,0));        
        ic.addItem(new Item(19*map.tsize,11*map.tsize,0));
        ic.addItem(new Item(18*map.tsize,11*map.tsize,0));
        ic.addItem(new Item(17*map.tsize,11*map.tsize,0));
        ic.addItem(new Item(16*map.tsize,11*map.tsize,0));
        
        init();
    }

    @Override
    public void init() {
        
        this.addObserver(GamePanel.amp);
        this.addObserver(score);
        
        isEnter = true;
                              
        GamePanel.amp.loop("/res/audio/music/level1-1.mp3");        
        
    }

    @Override
    public void pause() {

    }

    @Override
    public void reset() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void update() {
        
        if(isEnter){
            enterCounter++;
            if(enterCounter > enterMax){
                isEnter = false;
            }
        }
        
        if(!isExiting){
            
            psm.update();        
            ec.update();
            ic.update();
            collisions.update();
            lifepoints.update(psm.getPlayer());
            camera.update(psm.getPlayer());
            score.setLifes(psm.getPlayer().lifes);

            if(psm.getPlayer().getBounds().intersects(greenFlag.getBounds())){
                notify("NEXT_LEVEL");
                GamePanel.amp.stopAllSounds();
                gsm.setState(new Boss1LevelState(this.gsm));
            }
            
            //desenvolver metodologia para morte em buracos
            //usar Zones na classe collisions!
            // --> a morte tem q ser informada pela classe Collisions;
            //desta maneira, as instâncias dos levels apenas irão checar.
            if(psm.getPlayer().y > 1220){
                notify("DEATH");
                psm.getPlayer().dead();
                isExiting = true;
            } else if(psm.getPlayer().isDead) {
                notify("DEATH");
                isExiting = true;
            }
            
        } else {
            
            if(exitCounter > 75){
                for(int i = 0; i < 120; i++){
                    //apenas espera;
                }
                score.reset();
                gsm.setState(new Level1State(this.gsm));
            }
        }
        
        if(isExiting){
            if(psm.getPlayer().getLifes() <= 0 && exitCounter == exitMax){
                gsm.setState(new GameOverState(gsm));
            }
        }
    }
    

    @Override
    public void draw(Graphics g){
        
        g.drawImage(this.getBackgroundImage(background), 0, 0, null);
        g.drawImage(this.getBackgroundImage(bg1), 0, 700, null);
        
        camera.draw(g);

        psm.draw(g);
        ec.draw(g);
        ic.draw(g);
        
        collisions.p_gen.draw(g);
                
        g.translate(-camera.offsetX, -camera.offsetY);
        score.draw(g);
        lifepoints.draw(g);
        
        if(isEnter){
            enterLevel(g);
        }
        
        if(isExiting){
            exitLevel(g);
        }
    }
    
    public void enterLevel(Graphics g){
        int RGBcode = 0;
        float alpha;
        alpha = (255f-255f*((float)enterCounter/(float)enterMax));
        g.setColor(new Color(RGBcode,RGBcode,RGBcode,(int)alpha));
        g.fillRect(0, 0, PMF.WIDTH, PMF.HEIGHT);
        
    }
        
    public void exitLevel(Graphics g){
        int stepHeight = 10;
        exitMax = PMF.HEIGHT/stepHeight;
        exitCounter++;
        //loop tá horrível, mas funciona; acho que um simple if() basta
        for(int i = exitCounter - 2; i < exitCounter; i++){                
            g.setColor(Color.black);
            g.fillRect(0, 0, PMF.WIDTH, i*stepHeight);
            if(exitCounter > exitMax){
                exitCounter = exitMax;
            }            
        }      
        
        
    }
    
    public Image getBackgroundImage(String bg){
        ImageIcon i = new ImageIcon(getClass().getResource(bg));
        return i.getImage();        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        psm.keyPressed(e);
        
        if(e.getKeyCode() == KeyEvent.VK_P){
            gsm.states.push(new PausedState(this.gsm));
        }   
        
        if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
            System.exit(0);
        }
        
        //TESTANDO CÓDIGO DE TRANSIÇÃO
        if(e.getKeyCode() == KeyEvent.VK_ENTER){
            this.isExiting = !isExiting;
            exitCounter = 0;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        psm.keyReleased(e);        
    }

    @Override
    public void addObserver(Observer o) {
        this.observers.add(o);
    }

    @Override
    public void removeObserver(Observer o) {
        this.observers.remove(o);
    }

    @Override
    public void notify(String s) {
        for(Observer o: observers){
            o.onNotify(s);
            System.out.println("Evento "+s+" enviado para :"+o);
        }
    }
}
