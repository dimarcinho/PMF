
package gamestates;

import objects.Player;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import objects.Camera;
import objects.Collisions;
import objects.EnemyA;
import objects.EnemyController;
import objects.Item;
import objects.ItemController;
import objects.MapTile;
import objects.PumpEnemy;
import objects.SDVEnemy;
import objects.Score;
import playerstates.JumpingState;
import playerstates.PlayerStateManager;

public class Level1State extends GameState {
    
    public Player player;    
    public PlayerStateManager psm;
    public MapTile map;
    public Camera camera;
    public Score score;
    public Collisions collisions;
    public EnemyController ec;
    public ItemController ic;
        
    String background = "/res/background_sky.png";
    String bg1 = "/res/background_sea.png";
    
    public Item teste;
    
    public Level1State(GameStateManager gsm){
        super(gsm);
        
        score = new Score();
        
        player = new Player(100, 100);
        
        map = new MapTile(32, "/res/levelteste.png");
        
        camera = new Camera(player, map);
        
        psm = new PlayerStateManager();
        
        psm.setState(new JumpingState(this.player, this.psm));
        
        ec = new EnemyController();
        ic = new ItemController();
        collisions = new Collisions(psm, map, ec, ic);
        
        //adiciona os inimigos direto do mapa
        for(int i = 0; i < map.row; i++){
            for(int j = 0; j < map.col; j++){                
                if(map.getTile(i, j) == 5){
                    ec.addEnemy(new EnemyA(map.tsize*j, map.tsize*i));
                    //ec.addEnemy(new SDVEnemy(j*map.tsize, i*map.tsize));
                }
            }
        }
        
        ec.addEnemy(new SDVEnemy(10*map.tsize, 13*map.tsize));
        ec.addEnemy(new PumpEnemy(15*map.tsize, 13*map.tsize));
        
        ic.addItem(new Item(199*map.tsize,11*map.tsize,0));
        ic.addItem(new Item(20*map.tsize,13*map.tsize,0));        
        ic.addItem(new Item(19*map.tsize,13*map.tsize,0));
        ic.addItem(new Item(18*map.tsize,13*map.tsize,0));
        ic.addItem(new Item(17*map.tsize,13*map.tsize,0));
        ic.addItem(new Item(16*map.tsize,13*map.tsize,0));
        ic.addItem(new Item(20*map.tsize,12*map.tsize,0));        
        ic.addItem(new Item(19*map.tsize,12*map.tsize,0));
        ic.addItem(new Item(18*map.tsize,12*map.tsize,0));
        ic.addItem(new Item(17*map.tsize,12*map.tsize,0));
        ic.addItem(new Item(16*map.tsize,12*map.tsize,0));
        ic.addItem(new Item(20*map.tsize,11*map.tsize,0));        
        ic.addItem(new Item(19*map.tsize,11*map.tsize,0));
        ic.addItem(new Item(18*map.tsize,11*map.tsize,0));
        ic.addItem(new Item(17*map.tsize,11*map.tsize,0));
        ic.addItem(new Item(16*map.tsize,11*map.tsize,0));
        
        //ic.addItem(new Item(5*map.tsize,5*map.tsize,0));
        teste = new Item(5*map.tsize, 5*map.tsize, 0);
        
        init();
    }

    @Override
    public void init() {                
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
        player.update();
        collisions.update();
        if(player.y > 1220)
            player.reset();
        camera.update(psm.states.peek().p);
        
        if(player.x > 250){
            gsm.states.push(new Boss1LevelState(this.gsm));
        }
        
        //ic.addItem(new Item(199*map.tsize,11*map.tsize,0));
    }
    

    @Override
    public void draw(Graphics g){        
        g.drawImage(this.getBackgroundImage(background), 0, 0, null);
        g.drawImage(this.getBackgroundImage(bg1), 0, 700, null);
        
        camera.draw(g);
        
        player.draw(g);
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
        //player.keyReleased(e);
    }
}
