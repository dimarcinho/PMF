/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package objects;

import playerstates.JumpingState;
import playerstates.PlayerStateManager;
import playerstates.StandingState;


public class Collisions {
    
    public PlayerStateManager psm;    
    public Player p;
    public MapTile map;
    public EnemyController ec;
    public ItemController ic;
    public ParticleGenerator p_gen;
    
    public boolean collided;
    
    public Collisions(PlayerStateManager psm, MapTile map,
                        EnemyController ec, ItemController ic){
        this.psm = psm;        
        this.map = map;
        this.p = psm.states.peek().p;
        this.ec = ec;
        this.ic = ic;
        
        p_gen = new ParticleGenerator(0, 0, 0);        
        
        collided = false;
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
            psm.setState(new JumpingState(this.p, this.psm)); 
        }
        
        if (p.vx > 0 && map.getTileBounds(row0, col0+1).intersects(p.getGhostBounds()) && map.getLogic(row0, col0+1) == 1){
            
            p.vx = 0;
            p.x = map.getTileBounds(row0, col0+1).x-32;
                //System.out.println("vx > 0");
        }
        
        if (p.vx < 0 && map.getTileBounds(row0, col0-1).intersects(p.getGhostBounds()) && map.getLogic(row0, col0-1) == 1){
            
            p.vx = 0;
            p.x = map.getTileBounds(row0, col0).x;
                //System.out.println("vx > 0");
        }
        
        if (p.vy < 0 && map.getTileBounds(row0, col0).intersects(p.getGhostBounds()) && map.getLogic(row0, col0) == 1){
            p.vy = 0;
            p.y = map.getTileBounds(row0, col0).y+32;
        }
        
        if (p.vy >= 0 && map.getTileBounds(row0+1, col0).intersects(p.getGhostBounds()) && map.getLogic(row0+1, col0) == 1){

            p.vy = 0;
            p.y = map.getTileBounds(row0+1, col0).y-32;
            //System.out.println("vy > 0");
            psm.setState(new StandingState(psm.states.peek().p, this.psm));
        
        } else if(map.getTileBounds(row0+1, col0).intersects(p.getGhostBounds()) && map.getLogic(row0+1, col0) == 0){
                    
                   psm.setState(new JumpingState(psm.states.peek().p, this.psm)); 
                   
        }
        
    }
    
    public void collisionItems(){
        
        for(int i = 0; i < ic.items.size(); i++){
            
            if(this.p.getBounds().intersects(ic.items.get(i).getBounds())){
                //System.out.println("pegou item!");                
                p_gen.addParticles(ic.items.get(i).x,ic.items.get(i).y,50);
                ic.removeItem(ic.items.get(i));
                Score s = new Score();
                s.notify("GET_BBL");
                s = null;
            }
            
        }
        
    }
    
    public void collisionEnemies(){
        
        for(int i = 0; i < ec.enemies.size(); i++){
            
            if(this.p.getBounds().intersects(ec.enemies.get(i).getBounds())){
                System.out.println("Colisão com inimigo detectada!!!!");
                this.p.reset();
            }
            
        }
        
    }
    
    public void shotEnemies(){
        
        for(int i = 0; i < ec.enemies.size(); i++){
            
            for(int j = 0; j < p.sc.shots.size(); j++){
                
                //colisão do tiro com inimigo
                
                if(ec.enemies.get(i).getBounds().intersects(p.sc.shots.get(j).getBounds())){
                    p_gen.addParticles(ec.enemies.get(i).x, ec.enemies.get(i).y, 150);      
                    //System.out.println(ec.enemies.get(i).x+","+ec.enemies.get(i).y);
                    ec.enemies.remove(i);
                    p.sc.shots.remove(j);
                    Score s = new Score();
                    s.notify("KILL_ENEMY");
                    s = null;                    
                    break;
                } else if(p.sc.shots.get(j).x < 0 || p.sc.shots.get(j).x > map.col*map.tsize){
                    //se o objeto sair dos limites do mapa, ele é destruído
                    p.sc.shots.remove(j);
                }
                
            }
        }
    }
    
} 