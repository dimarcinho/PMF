
package objects;

import java.awt.Graphics;


public class LifePoints extends Animated {
    
    protected Player player;
    private int lifepoints;
    
    public LifePoints(Player player){
        this.player = player;
        
        this.ss = new SpriteSheet(i.load("/res/img/pontos_de_vida.png"));
        
        this.frameSpeed = 10;
        
    }
    
    public void setLifePoints(int lifepoints){
        this.lifepoints = lifepoints;
    }
        
    public void update(Player player){
        int lifePointsBefore = lifepoints;
        setLifePoints(player.lifePoints);
        
        if(lifePointsBefore != lifepoints){
            this.frameSpeed = 10;
            switch(lifepoints){
                default:
                    System.out.println("Erro em Lifepoints draw()");
                    break;
                case 4:
                    this.setFrames(0, 1);
                    break;
                case 3:
                    this.setFrames(2, 1);
                    break;
                case 2:
                    this.setFrames(4, 1);
                    break;
                case 1:
                    this.setFrames(6, 3);
                    this.frameSpeed = 5;
                    break;
              
            }
        }
        
        this.Animation();
    
    }
    
    @Override
    public void draw(Graphics g){
        
        g.drawImage(this.getAnimatedImage(), 50, 100, null);
        
    }
    
    @Override
    public void Animation(){
        
        frameSS = ss.crop4(frameNumber, 5, 64, 96);        
        
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

    }
    
    
}
