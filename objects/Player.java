
package objects;

import audioEngine.AudioPlayer;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import javax.swing.ImageIcon;
import pmf.GamePanel;

public class Player extends Animated {
    
    public int vxmax, vymax;    
    public int acc0, accX, jumpspeed, g;
    
    public static int lifes = 2;
    
    public int lifePoints;
    public boolean isInvincible, isDead;
    public int countInvincible = 0, InvicibleTime = 120;
    
    public ShotController sc;
    
    public boolean lastXdir = false; //false -> direita
    
    public Rectangle ghost;
    
    String petroleiro = "/res/img/petroleiro_piskel.png";
    
    public Player(int x, int y){
        
        //this.ss = new SpriteSheet(i.load("/res/img/petroleiro_cartoon_color2.png"));
        //this.ss = new SpriteSheet(i.load("/res/img/saul_cerejinha_sprite.png"));
        this.ss = new SpriteSheet(i.load("/res/img/petroleiro_simples.png"));
        
        this.x = x;
        this.y = y;
        
        this.lifePoints = 4; //4
  
        this.ghost = new Rectangle(this.x,this.y,32,32);
        
        speed = 8;
        acc0 = 4;
        accX = acc0; //aceleração do eixo X;
        
        jumpspeed = 15;
        g = 1;
        
        vymax = 8; //velocidade terminal
        vxmax = 8;
        
        sc = new ShotController();
        
        this.tsize = 64;        
        
        init();
    }
    
    @Override
    public void init(){
        isDead = false;
    }
    
    public void reset(int x, int y){
        this.x = x;
        this.y = y;
        
        lifePoints = 4;
        this.isInvincible = false;
        this.isDead = false;
        
        vx = 0;
        vy = 0;
        accX = 0;
        sc.removeAll();
        
        //DESACOPLAR!
        AudioPlayer audioplayer = new AudioPlayer("/res/audio/sfx/Death.wav");
        audioplayer.play();
        int i = 0;
        while(i < 100000){
            //just wait
            i++;
            //System.out.println(i);
        }
    }
    
    @Override
    public void update(){
        
        sc.update();
        
        this.checkLimits();
        this.changeDirection();
        this.Animation();

    }
    
    @Override
    public void changeDirection(){
        
        if(vx > 0){            
            this.direction = 1; //direita            
        } else if(vx < 0){            
            this.direction = -1; //esquerda
        }

    }
    
    public void checkLimits(){
        
        this.invincibleCheck(0);

        if (vy > vymax){
            vy = vymax; //velocidade terminal
        }
        
        if(vx > 0){
            lastXdir = false;
        } else if(vx < 0){
            lastXdir = true;
        }

        
    }
    
    public int getLifes(){
        return lifes;
    }
    
    public void setLifes(int lifes){
        this.lifes = lifes;
    }
    
    public void shoot(){
        Shot shot = new Shot(x,y,lastXdir);
        sc.addShot(shot);
        
        //DESACLOPAR !!!!!!
        GamePanel.amp.onNotify("SHOT");
         
    }
    
    public void hurt(){
        
        lifePoints--;
        this.isInvincible = true;        
        this.y -= 5; //para
        
        if(direction == 1){
            vy = -10;
            vx = -5;
        } else if(direction == -1){
            vy = -10;
            vx = +5;
        }        
        
        if(lifePoints <= 0){
            dead();
        }
    }
    
    public void dead(){
        if(!isDead){
            lifes--;
            isDead = true;
        }
        System.out.println("Player morto! Lifes: "+lifes);
    }
    
    public void invincibleCheck(int maxtime){
        if(isInvincible){            
            countInvincible++;
            if(countInvincible > (this.InvicibleTime + maxtime)){
                this.isInvincible = false;
                countInvincible = 0;
            }
        }        
    }
    
    public void jump(String stateID){
        if(stateID.equals("STANDING_STATE")){
            vy -= jumpspeed;            
        } else if(stateID.equals("WALKING_STATE")){
            vy -= (int)(jumpspeed*1.1);
        }
    }
    
    public void moveLeft(){
        accX = -acc0;
    }
    
    public void moveRight(){
        accX = +acc0;
    }
    
    public void flyLeft(){
        vx = -speed;
    }
    public void flyRight(){
        vx = +speed;
    }

    @Override
    public Rectangle getBounds(){
        Rectangle r = new Rectangle(this.x, this.y-32, 32, 64);
        return r;    
    }
    
    public Rectangle getGhostBounds(){
        Rectangle rp = getBounds();
        Rectangle r = new Rectangle(rp.x+vx, rp.y+vy, rp.width, rp.height);        
        return r;    
    }
    
    public Image getImage(String bg){
        ImageIcon i = new ImageIcon(getClass().getResource(bg));
        return i.getImage();        
    }
    
    @Override
    public Image getAnimatedImage(){
        return frameSS;        
    }
    
    @Override
    public void draw(Graphics g){      
        
        if(isInvincible){            
            if(countInvincible % 4 == 0){
                g.drawImage(this.getAnimatedImage(), this.x, this.y-32, null); 
            }
        } else {
            g.drawImage(this.getAnimatedImage(), this.x, this.y-32, null);        
        }
        
        //desenha os tiros ---> desacoplar!
        sc.draw(g);
    }
    
    @Override
    public void Animation(){

        frameSS = ss.crop4(frameNumber, 7, 32, 64);
        
        if(counterSS % frameSpeed == 0){
            if(frameNumber < endFrame){
                frameNumber++;
            } else {
                frameNumber = startFrame;
            }   
        }
        
        if(counterSS >= 20*frameSpeed){
            counterSS = 0;
        } else if(counterSS < 20*frameSpeed) {
            this.counterSS++;
        }

    }
}
