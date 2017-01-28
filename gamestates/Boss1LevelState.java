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
import objects.*;
import playerstates.*;
import pmf.GamePanel;

/**
 *
 * @author Marcio
 */
public class Boss1LevelState extends GameState {
            
    public Player player;
    public Boss boss;    
    
    public PlayerStateManager psm;
    public BossStateManager bsm;
    
    public MapTile map;
    public Camera camera;
    public Score score;
    public Collisions collisions;
    public EnemyController ec;
    public ItemController ic;
            
    String background = "/res/img/background_sky.png";
    String bg1 = "/res/img/background_sea.png";
    
    public Boss1LevelState(GameStateManager gsm){
        super(gsm);
        
        score = new Score();        
        player = new Player(100, 500);
        boss = new Boss(600, 400, player);
        
        map = new MapTile(32, "/res/img/level1boss.png");        
        camera = new Camera(player, map);        
        psm = new PlayerStateManager();        
        psm.setState(new StandingState(this.player, this.psm));
        
        bsm = new BossStateManager();
        bsm.setState(new Boss_Idle_State(this.boss, this.bsm));
        
        ec = new EnemyController();
        ic = new ItemController();
        collisions = new Collisions(psm, map, ec, ic);
        
        init();
        
    }

    @Override
    public void init() {
        
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
        
        //player.update();
        //boss.update();
        psm.update();
        bsm.update();
        ec.update();
        ic.update();       
        
        collisions.update();
        this.BossCollisions();
        camera.update(psm.states.peek().p);
    }

    @Override
    public void draw(Graphics g) {        
        g.drawImage(this.getBackgroundImage(background), 0, 0, null);
        g.drawImage(this.getBackgroundImage(bg1), 0, 700, null);
        
        camera.draw(g);
        
        //player.draw(g);
        //boss.draw(g);        
        bsm.draw(g);
        psm.draw(g);
        ec.draw(g);
        ic.draw(g);
        
        collisions.p_gen.draw(g);        
        
        g.translate(-camera.offsetX, -camera.offsetY);
        score.draw(g);
        
    }
    
    public Image getBackgroundImage(String bg){
        ImageIcon i = new ImageIcon(getClass().getResource(bg));
        return i.getImage();        
    }
    
    public void BossCollisions(){
        
        boolean shotBoss = false;
        
        //checa se o player é atingido pelo Boss
        if(player.getBounds().intersects(boss.getBounds()) && !boss.isInvencible){
            player.reset();
            gsm.states.remove(this);
            gsm.states.push(new Boss1LevelState(this.gsm));
            //player tocou no chefe!
        }
        
        //checa se o player é atingido pelos tiros do Boss
        for(Shot e : boss.esc.shots){
            if(e.getBounds().intersects(player.getBounds())){
                player.reset();
                gsm.states.remove(this);
                gsm.states.push(new Boss1LevelState(this.gsm));
                //player atingido pelo tiro do boss!!!
                System.out.println("player atingido pelo tiro do boss!!!!");
            }
        }
        //****************** inserir rotina para morte do player e reset da "fase Boss" ************
        
        //checa se o Boss é atingido pelos tiros do player
        for(Shot e: player.sc.shots){
            if(e.getBounds().intersects(boss.getWeakPoint()) && !boss.isInvencible){
                //chefe atingido no ponto fraco!!! 
                player.sc.shots.remove(e);
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
            //gsm.states.push(new BeatGameState(gsm));
            gsm.setState(new BeatGameState(gsm));
        }
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        psm.keyPressed(e);
        //player.keyPressed(e);
        
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
    
}
