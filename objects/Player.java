
package objects;

import audioEngine.AudioPlayer;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import javax.swing.ImageIcon;
import pmf.GamePanel;

public class Player extends Animated {
    
    public int vxmax, vymax;    
    public int xg, yx; // variáveis para checar a colisão
    public int acc0, accX, jumpspeed, g;
    
    public int lifes, lifePoints;
    public boolean isInvincible;
    public int countInvincible = 0, InvicibleTime = 120;
    
    public Point left, right, top, bottom;
    public ShotController sc;

    public boolean check; //para checar colisões
    public boolean lastXdir = false; //false -> direita
    
    public Rectangle ghost;
    
    String petroleiro = "/res/img/petroleiro_piskel.png";
    
    public double tick = 0;
    
    public Player(int x, int y){
        
        //this.ss = new SpriteSheet(i.load("/res/img/petroleiro_cartoon_color2.png"));
        //this.ss = new SpriteSheet(i.load("/res/img/saul_cerejinha_sprite.png"));
        this.ss = new SpriteSheet(i.load("/res/img/petroleiro_simples.png"));
        
        this.x = x;
        this.y = y;
        
        this.lifes = 3;
        
        this.lifePoints = 4;
        
        this.top = new Point(this.x+16, this.y);
        this.bottom = new Point(this.x+16, this.y+32);
        this.left = new Point(this.x+16, this.y+16);        
        this.right = new Point(this.x, this.y+16);
        
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

    }
    
    public void reset(){
        lifePoints = 4;
        this.isInvincible = false;
        this.x = 100;
        this.y = 100;
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
        

        
        top.setXY(this.x+16, y);
        bottom.setXY(this.x+16, this.y+32);
        left.setXY(this.x+16, this.y+16);
        right.setXY(this.x, this.y+16);
        
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
        
        if (x < 0)
            x = 0;
        
        if (x > 8000)
            x = 8000;
        
        if (this.y < 1)
            y = 0;
        if (this.y > 1500){
            y = 1500;
            this.reset();
        }
        if(vx > 0){
            lastXdir = false;
        } else if(vx < 0){
            lastXdir = true;
        }
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
        
        this.y -= 5;
        if(direction == 1){
            vy = -10;
            vx = -5;
        } else if(direction == -1){
            vy = -10;
            vx = +5;
        }        
        //setFrames(13,0);
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
        Rectangle r = new Rectangle(this.x+vx, this.y+vy-32, 32, 64);
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
            //setFrames(13,0);
            if(countInvincible % 4 == 0){
                //g.drawImage(this.getImage(petroleiro), this.x, this.y-32, null);
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
