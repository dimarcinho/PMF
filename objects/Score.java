
package objects;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import observerpattern.Observer;

public class Score implements Observer { 
 
    private static int points = 0;
    private static int lifes;
    private String[] events;
    private int[] values;
    
    private Image bgImage, lifeImage;
    
    public Score(){
        
        bgImage = getImage("/res/img/label_bg_02.png");
        lifeImage = getImage("/res/img/vida_capacete.png");
        
        int size = 50;
        
        events = new String[size];
        values = new int[size];
        
        init();
    }
    
    private void init(){
        
        //Definir aqui os eventos e pontuações        
        events[0] = "KILL_ENEMY";
        values[0] = 15;
        
        events[1] = "GET_BBL";
        values[1] = 10;
    }
    
    //a ideia é criar um método para preencher os vetores a partir de um arquivo;
    private void setEventPoint(String s, int i){}

    public int getLifes() {
        return lifes;
    }

    public void setLifes(int lifes) {
        Score.lifes = lifes;
    }
    
    public void reset(){
        Score.points = 0;
    }

    public int getPoints() {
        return Score.points;
    }

    public void setPoints(int points){
        Score.points = points;        
    }
    
    public void addPoints(int points){
        Score.points += points;
    }
    
    public void removePoints(int points){
        Score.points -= points;
        if(Score.points < 0)
            Score.points = 0;
    }
    
    public void draw(Graphics g){
        g.setColor(Color.black);        
        g.drawImage(bgImage, 50, 10, null);//vidas
        g.drawImage(bgImage, 50, 55, null);//pontos        
        
        g.setColor(Color.white);
        g.setFont(new Font("Arial", Font.BOLD, 16));
        
        //vidas
        g.drawImage(lifeImage,65,13,null);
        g.drawString("x "+lifes, 110, 35);
        
        //pontos
        g.drawString("Pontos: "+points, 60, 80);
    }
    
    public Image getImage(String path){
        ImageIcon i = new ImageIcon(getClass().getResource(path));
        return i.getImage();        
    }

    @Override
    public void onNotify(String s) {
        
        for(int i = 0; i < events.length; i++){
            if(events[i] != null){
                if(events[i].equals(s)){
                    this.addPoints(values[i]);
                }
            }
        }
    }
    
}
