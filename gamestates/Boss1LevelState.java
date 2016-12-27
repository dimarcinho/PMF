/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gamestates;

import boss_states.BossStateManager;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import boss_states.boss1.Boss1;
import boss_states.boss1.Boss1_Hit_State;
import boss_states.boss1.Boss1_Idle_State;
import objects.Camera;
import objects.Collisions;
import objects.EnemyController;
import objects.ItemController;
import objects.MapTile;
import objects.Player;
import objects.Score;
import objects.Shot;
import playerstates.JumpingState;
import playerstates.PlayerStateManager;

/**
 *
 * @author Marcio
 */
public class Boss1LevelState extends GameState {
            
    public Player player;
    public Boss1 boss;    
    
    public PlayerStateManager psm;
    public BossStateManager bsm;
    
    public MapTile map;
    public Camera camera;
    public Score score;
    public Collisions collisions;
    public EnemyController ec;
    public ItemController ic;
            
    String background = "/res/background_sky.png";
    String bg1 = "/res/background_sea.png";
    
    public Boss1LevelState(GameStateManager gsm){
        super(gsm);
        
        score = new Score();        
        player = new Player(100, 500);
        boss = new Boss1(600, 400, player);
        
        map = new MapTile(32, "/res/level1boss.png");        
        camera = new Camera(player, map);        
        psm = new PlayerStateManager();        
        psm.setState(new JumpingState(this.player, this.psm));
        
        bsm = new BossStateManager();
        bsm.setState(new Boss1_Idle_State(this.boss, this.bsm));
        
        ec = new EnemyController();
        ic = new ItemController();
        collisions = new Collisions(psm, map, ec, ic);
        
    }

    @Override
    public void init() {
 
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
        
        psm.update();
        bsm.update();
        ec.update();
        ic.update();
        player.update();
        boss.update();
        collisions.update();
        this.BossCollisions();
        camera.update(psm.states.peek().p);
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(this.getBackgroundImage(background), 0, 0, null);
        g.drawImage(this.getBackgroundImage(bg1), 0, 700, null);
        
        camera.draw(g);
        
        player.draw(g);
        boss.draw(g);        
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
        
        //checa se o player é atingido pelo Boss
        if(player.getBounds().intersects(boss.getBounds()) && !boss.isInvencible){
            player.reset();
            //player tocou no chefe!
        }
        
        //checa se o player é atingido pelos tiros do Boss
        for(Shot e : boss.esc.shots){
            if(e.getBounds().intersects(player.getBounds()))
                player.reset();
                //player atingido pelo tiro do boss!!!
        }
        
        for(Shot e: player.sc.shots){
            if(e.getBounds().intersects(boss.getWeakPoint()) && !boss.isInvencible){
                //chefe atingido no ponto fraco!!!                
                bsm.setState(new Boss1_Hit_State(this.boss, this.bsm));
            }
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
