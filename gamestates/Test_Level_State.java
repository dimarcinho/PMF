
package gamestates;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import objects.*;
import observerpattern.Observer;
import observerpattern.Subject;
import playerstates.PlayerStateManager;
import playerstates.StandingState;
import pmf.GamePanel;
import pmf.PMF;

public class Test_Level_State extends GameState implements Subject {
    
    //public Player player;    
    public PlayerStateManager psm;
    public MapTile map;
    public Camera camera;
    public Score score;
    public LifePoints lifepoints;
    public Collisions collisions;
    public EnemyController ec;
    public ItemController ic;
    public DoorController dc;    
    
    private Layer backgroundlayer, layer1;    
    private ArrayList<Observer> observers;
    
    private int exitCounter = 0, exitMax = 30, enterCounter = 0, enterMax = 30;
    private boolean isExiting = false, isEnter = false;    
    
    public Test_Level_State(GameStateManager gsm){
        super(gsm);
        
        observers = new ArrayList<>();
        score = new Score();        
        
        psm = new PlayerStateManager();        
        psm.setState(new StandingState(new Player(100,240), this.psm));
        
        map = new MapTile(32, "/res/img/test_level.png");        
        camera = new Camera(psm.getPlayer(), map);
        
        lifepoints = new LifePoints(psm.getPlayer());
        
        dc = new DoorController();
        dc.addDoor(new Door(200,608,1,2));
        dc.addDoor(new Door(1200,608,2,3));
        dc.addDoor(new Door(1500,288,3,4));
        dc.addDoor(new Door(2500,0,4,1));
        
        ec = new EnemyController();
        ic = new ItemController();        
        collisions = new Collisions(psm, map, ec, ic);
        
        backgroundlayer = new Layer(0,-400, "/res/img/background_industrial4.png");
        layer1 = new Layer(200,-300, "/res/img/background_industrial5.png");
        
        //adiciona os inimigos direto do mapa
        for(int i = 0; i < map.row; i++){
            for(int j = 0; j < map.col; j++){                
                if(map.getTile(i, j) == 5){
                    ec.addEnemy(new EnemyA(map.tsize*j, map.tsize*i));
                }
            }
        }
                
        init();
    }

    @Override
    public void init() {
        this.addObserver(GamePanel.amp);
        this.addObserver(score);
        
        isEnter = true;
    }

    @Override
    public void reset() {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void update() {

        if(isEnter){
            enterCounter++;
            if(enterCounter > enterMax){
                isEnter = false;
            }
        }
        
        if(!isExiting){
            backgroundlayer.update(-psm.getPlayer().vx/8, 0);
            layer1.update(-psm.getPlayer().vx/4, 0);
            psm.update();        
            ec.update();
            ic.update();
            collisions.update();
            lifepoints.update(psm.getPlayer());
            camera.update(psm.getPlayer());
            score.setLifes(psm.getPlayer().lifes);
            
            if(psm.getPlayer().isDead) {
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
    public void draw(Graphics g) {
        
        backgroundlayer.draw(g);
        layer1.draw(g);        
        
        camera.draw(g);
        
        dc.draw(g);

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
    
    public void checkDoors(){
        for(Door d : dc.doors){
            if(psm.getPlayer().getBounds().intersects(d.getBounds())){
                psm.getPlayer().x = dc.getDoor(d.getNextID()).x;
                psm.getPlayer().y = dc.getDoor(d.getNextID()).y-32;
                isEnter = true;
                enterCounter = 0;

                notify("OPEN_DOOR");
            }
        } 
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
        
        if(e.getKeyCode() == KeyEvent.VK_E){
            checkDoors();           
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
