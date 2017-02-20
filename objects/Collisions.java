/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package objects;

import java.util.ArrayList;
import observerpattern.Observer;
import observerpattern.Subject;
import playerstates.JumpingState;
import playerstates.PlayerStateManager;
import playerstates.StandingState;
import playerstates.WalkingState;
import pmf.GamePanel;


public class Collisions implements Subject {
    
    public PlayerStateManager psm;    
    public Player p;
    public MapTile map;
    public EnemyController ec;
    public ItemController ic;
    public ParticleGenerator p_gen;
    
    public boolean collided;    
    
    private ArrayList<Observer> observers = new ArrayList<>();
    
    public Collisions(PlayerStateManager psm, MapTile map,
                        EnemyController ec, ItemController ic) {
        this.psm = psm;        
        this.map = map;
        this.p = psm.states.peek().p;
        this.ec = ec;
        this.ic = ic;
        
        p_gen = new ParticleGenerator(0, 0, 0);        
        
        collided = false;        
        
        //adicionando Observers        
        this.addObserver(new Score());
        this.addObserver(GamePanel.amp);
        this.addObserver(psm);
        
    }
    
    public void update(){
        
        this.collisionEnemies();
        this.shotEnemies();
        this.collisionItems();
        this.p_gen.update();
        
        //define limites de colisão apenas no entorno do player
        int limite = 0; //em tiles
        int col0 = (int)(p.x+16)/map.tsize-limite;
        if (col0 < 0) col0=0;
        int row0 = (int)p.y/map.tsize-limite;
        if (row0 < 0) row0=0;
        int colf = (int)p.x/map.tsize+limite;
        int rowf = (int)p.y/map.tsize+limite;
        
        
        if(map.getLogic(row0, col0) == 0 && map.getLogic(row0+1, col0) == 0){
            //se o estado for jumping, não fazer nada!!!! (i.e., mantem o estado jumping)
            if(!psm.states.peek().id.equals("JUMPING_STATE")){
                System.out.println("Caindo!");
                psm.setState(new JumpingState(psm.states.peek().p, this.psm)); 
            } 
        }
        
        if (p.vx > 0 && map.getTileBounds(row0, col0+1).intersects(p.getGhostBounds()) && map.getLogic(row0, col0+1) == 1){
            
            p.vx = 0;
            p.x = map.getTileBounds(row0, col0+1).x-32;

        }
        
        if (p.vx < 0 && map.getTileBounds(row0, col0-1).intersects(p.getGhostBounds()) && map.getLogic(row0, col0-1) == 1){            
            p.vx = 0;
            p.x = map.getTileBounds(row0, col0).x;
        }
        
        if (p.vy < 0 && map.getTileBounds(row0, col0).intersects(p.getGhostBounds()) && map.getLogic(row0, col0) == 1){
            p.vy = 0;
            p.y = map.getTileBounds(row0, col0).y+32;
        }
        
        if (p.vy >= 0 && map.getTileBounds(row0+1, col0).intersects(p.getGhostBounds()) && map.getLogic(row0+1, col0) == 1){

            p.vy = 0;
            p.y = map.getTileBounds(row0+1, col0).y-32;
            //System.out.println("vy > 0");
            
            if(!psm.states.peek().id.equals("STANDING_STATE") || !psm.states.peek().id.equals("WALKING_STATE")){
                System.out.println("Encostando no chão!");
                
                //psm.setState(new WalkingState(psm.states.peek().p, this.psm));
                
                psm.setState(new StandingState(psm.states.peek().p, this.psm));
                
            }   
        
        } else if(map.getTileBounds(row0+1, col0).intersects(p.getGhostBounds()) && map.getLogic(row0+1, col0) == 0){
            
                   if(!psm.states.peek().id.equals("JUMPING_STATE")){
                       System.out.println("Ainda caindo!");
                       psm.setState(new JumpingState(psm.states.peek().p, this.psm)); 
                   }
                   
        }
        
    }
    
    public void collisionItems(){
        
        for(int i = 0; i < ic.items.size(); i++){
            
            if(this.p.getBounds().intersects(ic.items.get(i).getBounds())){
                //System.out.println("pegou item!");                
                p_gen.addBasicParticles(ic.items.get(i).x,ic.items.get(i).y,50);
                ic.removeItem(ic.items.get(i));
                
                notify("GET_BBL");
                
            }
            
        }
        
    }
    
    public void collisionEnemies(){
        
        for(int i = 0; i < ec.enemies.size(); i++){
            
            if(this.p.getBounds().intersects(ec.enemies.get(i).getBounds()) 
                    && this.p.isInvincible == false){
                System.out.println("Colisão com inimigo detectada!!!!");
                notify("PLAYER_HURT");                
            }
            
        }
        
    }
    
    public void shotEnemies(){
        
        for(int i = 0; i < ec.enemies.size(); i++){
            
            for(int j = 0; j < p.sc.shots.size(); j++){
                
                //colisão do tiro com inimigo
                
                if(ec.enemies.get(i).getBounds().intersects(p.sc.shots.get(j).getBounds())){
                    
                    p_gen.addBasicParticles(ec.enemies.get(i).x, ec.enemies.get(i).y, 128);      
                    ec.enemies.remove(i);
                    p.sc.shots.remove(j);
                    
                    notify("KILL_ENEMY");
                    break;
                    
                } else if(p.sc.shots.get(j).x < 0 || p.sc.shots.get(j).x > map.col*map.tsize){
                    //se o objeto sair dos limites do mapa, ele é destruído
                    p.sc.shots.remove(j);
                }
                
            }
        }
    }

    @Override
    public void addObserver(Observer o) {
        this.observers.add(o);
    }

    @Override
    public void removeObserver(Observer o) {
        if(o != null){
            this.observers.remove(o);
        }
    }

    @Override
    public void notify(String s) {
        
        for(Observer o: observers){
            o.onNotify(s);
        }
    }
    
} 