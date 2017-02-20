
package gamestates;

import audioEngine.AudioPlayerManager;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import objects.*;
import observerpattern.*;
import playerstates.*;
import pmf.GamePanel;


public class Level1State extends GameState implements Subject {
    
    public Player player;    
    public PlayerStateManager psm;
    public MapTile map;
    public Camera camera;
    public Score score;
    public LifePoints lifepoints;
    public Collisions collisions;
    public EnemyController ec;
    public ItemController ic;
    
    
    private ArrayList<Observer> observers;
    private AudioPlayerManager APM = new AudioPlayerManager();

    String background = "/res/img/background_sky.png";
    //String background = "/res/img/background_pink.png";
    String bg1 = "/res/img/background_sea.png";
    
    public Item greenFlag;
    
    public Level1State(GameStateManager gsm){
        super(gsm);
        
        observers = new ArrayList<>();
        
        score = new Score();        
        
        //player = new Player(6000, 10);
        player = new Player(100, 250);
        
        lifepoints = new LifePoints(player);
        
        map = new MapTile(32, "/res/img/levelteste.png");
        
        camera = new Camera(player, map);
        
        psm = new PlayerStateManager();
        
        psm.setState(new StandingState(this.player, this.psm));
        
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
        
        ic.addItem(new Item(19*map.tsize,13*map.tsize,0));
        ic.addItem(new Item(18*map.tsize,13*map.tsize,0));
        ic.addItem(new Item(17*map.tsize,13*map.tsize,0));
        ic.addItem(new Item(16*map.tsize,13*map.tsize,0));
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
        
        this.addObserver(APM);
                        
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
        
        psm.update();        
        ec.update();
        ic.update();
        collisions.update();
        lifepoints.update(psm.states.peek().p);
        camera.update(psm.states.peek().p);
        
        //desenvolver metodologia para morte em buracos
        //usar Zones na classe collisions!
        if(player.y > 1220 || player.lifePoints == 0){
            notify("DEATH");
            score.reset();
            GamePanel.amp.stopAllSounds();
            gsm.states.push(new Level1State(this.gsm));
        }
        
        if(player.getBounds().intersects(greenFlag.getBounds())){
            GamePanel.amp.stopAllSounds();
            gsm.states.push(new Boss1LevelState(this.gsm));
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
        
        player.draw(g);
        
        g.translate(-camera.offsetX, -camera.offsetY);
        score.draw(g);
        lifepoints.draw(g);
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
        }
    }
}
