
package objects;

import audioEngine.AudioPlayer;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import pmf.GamePanel;

public class Player extends Animated {
    
    public int vxmax, vymax;    
    public int xg, yx; // variáveis para checar a colisão
    public int acc0, accX, jumpspeed, g;
    
    public int lifes, lifePoints;
    
    public Point left, right, top, bottom;
    public ShotController sc;
    
    public boolean check; //para checar colisões
    public boolean lastXdir = false;
    
    public Rectangle ghost;
    
    String petroleiro = "/res/img/petroleiro_piskel.png";
    
    private AudioPlayer audioplayer;
    
    public Player(int x, int y){
        
        //this.ss = new SpriteSheet(i.load("/res/img/petroleiro_teste_spritesheet.png"));
        this.ss = new SpriteSheet(i.load("/res/img/petroleiro_cartoon_color2.png"));
        
        this.x = x;
        this.y = y;
        
        this.lifes = 3;
        this.lifePoints = 5;
        
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

        this.setFrames(0, 3);
        this.frameSpeed = 10; //quanto maior, mais lento
    }
    
    public void reset(){
        
        this.x = 100;
        this.y = 100;
        vx = 0;
        vy = 0;
        accX = 0;
        sc.removeAll();
        
        audioplayer = new AudioPlayer("/res/audio/sfx/Death.wav");
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
            
            this.setFrames(3, 9);
            this.direction = 1;
            
        } else if(vx < 0){
            
            this.setFrames(5, 2);
            this.direction = -1;
        }
        
        if(vx == 0){
            
            if(direction > 0){
                this.setFrames(0, 3);
            } else {
                this.setFrames(34, 36);
            }
        }
        

    }
    
    public void checkLimits(){

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
        if(vx > 0)
            lastXdir = false;
        if(vx < 0)
            lastXdir = true;
    }
    
    public void shoot(){
        Shot shot = new Shot(x,y,lastXdir);
        sc.addShot(shot);
        
        GamePanel.amp.onNotify("SHOT");
         
    }
    
    public void hurt(){
        lifePoints--;
        this.y -= 5;
        if(direction == 1){
            vy = -10;
            vx = -5;
        } else if(direction == -1){
            vy = -10;
            vx = +5;
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
        //Rectangle r = new Rectangle(this.x, this.y, 64, 64);
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
        //g.setColor(Color.red);        
        //g.fillRect(this.getBounds().x, this.getBounds().y, this.getBounds().width, this.getBounds().height);
        //g.setColor(Color.black);
        //g.drawString("x: "+this.x+", y: "+this.y, 300, 400);
        
        //g.drawRect(this.getGhostBounds().x, this.getGhostBounds().y, 32, 32);
        
        //g.drawImage(this.getAnimatedImage(), this.x, this.y, null);        
        
        g.drawImage(this.getImage(petroleiro), this.x, this.y-32, null);        
        
        //desenha os tiros ---> desacoplar!
        sc.draw(g);
    }
    
    @Override
    public void Animation(){
        
        //frameSS = ss.crop2(tsize*frameNumber, 0, tsize, tsize);
        //System.out.println(tsize+"*"+frameNumber+" "+tsize+" "+tsize);
        //frameSS = ss.crop3(frameNumber, 19);
        frameSS = ss.crop4(frameNumber, 19, 64, 64);
        
        if(counterSS % frameSpeed == 0){
            if(frameNumber < endFrame){
                frameNumber++;
            } else {
                frameNumber = startFrame;
            }   
        }
        
        if(counterSS > 20*frameSpeed){
            counterSS = 0;
        } else {
            counterSS++;
        }
        
        if(vx == 0 && vy == 0)
            counterSS--;
    }
    
    public void keyPressed(KeyEvent e){

    }
    
    public void keyReleased(KeyEvent e){

    }
}
