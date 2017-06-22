/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gamestates;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import boss_states.*;
import boss_states.boss1.*;
import java.awt.Color;
import java.util.ArrayList;
import objects.*;
import objects.Player;
import observerpattern.Observer;
import observerpattern.Subject;
import playerstates.*;
import pmf.GamePanel;
import pmf.PMF;

/**
 *
 * @author Marcio
 */
public class Boss1LevelState extends GameState implements Subject {
            
    
    public Boss boss;    
    
    public PlayerStateManager psm;
    public BossStateManager bsm;
    
    public MapTile map;
    public Camera camera;
    public Score score;
    public LifePoints lifepoints;
    public Collisions collisions;
    public EnemyController ec;
    public ItemController ic;
    
    private ArrayList<Observer> observers;
            
    private int exitCounter = 0, exitMax = 30;
    private boolean isExiting = false;
    
    String background = "/res/img/background_sky.png";
    String bg1 = "/res/img/background_sea.png";
    
    public Boss1LevelState(GameStateManager gsm){
        super(gsm);
        
        observers = new ArrayList<>();
        score = new Score();
             
        psm = new PlayerStateManager();        
        psm.setState(new StandingState(new Player(100,500), this.psm));
        
        lifepoints = new LifePoints(psm.getPlayer());
        
        map = new MapTile(32, "/res/img/level1boss.png");        
        camera = new Camera(psm.getPlayer(), map);
        
        bsm = new BossStateManager();
        boss = new Boss(600, 400, psm.getPlayer());
        bsm.setState(new Boss_Idle_State(this.boss, this.bsm));
        
        ec = new EnemyController();
        ic = new ItemController();
        collisions = new Collisions(psm, map, ec, ic);
        
        init();
        
    }

    @Override
    public void init() {
        
        this.addObserver(GamePanel.amp);
        this.addObserver(score);
        this.addObserver(psm);
        
        GamePanel.amp.stopAllSounds();        
        //GamePanel.amp.play("/res/audio/music/Orbital Colossus.mp3");
        GamePanel.amp.loop("/res/audio/music/Orbital Colossus.mp3");
        
 
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
        
        if(!isExiting){
            
            psm.update();
            bsm.update();
            ec.update();
            ic.update();       

            collisions.update();
            this.BossCollisions();
            camera.update(psm.states.peek().p);

            lifepoints.update(psm.getPlayer());            
            score.setLifes(psm.getPlayer().lifes);
            
        }
        
        if(isExiting){
            if(psm.getPlayer().getLifes() <= 0 && exitCounter == exitMax){
                gsm.setState(new GameOverState(gsm));
            }
        }
    }

    @Override
    public void draw(Graphics g) {        
        g.drawImage(this.getBackgroundImage(background), 0, 0, null);
        g.drawImage(this.getBackgroundImage(bg1), 0, 700, null);
        
        camera.draw(g);
        
        bsm.draw(g);
        psm.draw(g);
        ec.draw(g);
        ic.draw(g);
        
        collisions.p_gen.draw(g);        
        
        g.translate(-camera.offsetX, -camera.offsetY);
        score.draw(g);        
        lifepoints.draw(g);
        
        if(isExiting){
            exitLevel(g);
        }
        
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
    
    public void BossCollisions(){
        
        boolean shotBoss = false;
        
        //checa se o player é atingido pelo Boss
        if(psm.getPlayer().getBounds().intersects(boss.getBounds()) && !boss.isInvencible
                && psm.getPlayer().isInvincible == false
                && psm.getPlayer().isDead == false){
            
            //psm.getPlayer().reset(100, 100);
            //gsm.states.remove(this);
            //gsm.states.push(new Boss1LevelState(this.gsm));
            //player tocou no chefe!
            
            notify("PLAYER_HURT");
        }
        
        //checa se o player é atingido pelos tiros do Boss
        for(Shot e : boss.esc.shots){
            if(e.getBounds().intersects(psm.getPlayer().getBounds())
                && psm.getPlayer().isInvincible == false
                && psm.getPlayer().isDead == false){
                
                notify("PLAYER_HURT");
                
                //player atingido pelo tiro do boss!!!
                System.out.println("player atingido pelo tiro do boss!!!!");
            }
        }
        //****************** inserir rotina para morte do player e reset da "fase Boss" ************
        
        if(psm.getPlayer().isDead){
            if(psm.getPlayer().getLifes() == 0){
                isExiting = true;                
                //gsm.setState(new GameOverState(gsm));                
            } else {
                gsm.setState(new Boss1LevelState(gsm));
            }
        }
        
        //checa se o Boss é atingido pelos tiros do player
        for(Shot e: psm.getPlayer().sc.shots){
            if(e.getBounds().intersects(boss.getWeakPoint()) && !boss.isInvencible){
                //chefe atingido no ponto fraco!!! 
                psm.getPlayer().sc.shots.remove(e);
                shotBoss = true;
                break;
                //não setando o estado direto aqui pois estava dando erro no linkedlist...
            }
        }
        
        //Se o Boss toma um tiro, passa para o estado respectivo
        if(shotBoss){
            bsm.setState(new Boss_Hit_State(this.boss, this.bsm));
        }
        
        //Checa se o chefe está morto e avança para próxima fase
        if(boss.isDead){
            GamePanel.amp.stopAllSounds();
            //GamePanel.amp.fadeOutAllSounds();            
            gsm.setState(new BeatGameState(gsm));
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
