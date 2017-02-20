
package objects;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import observerpattern.Observer;

public class Score implements Observer { 
 
    private static int points;
    private String[] events;
    private int[] values;
    
    public Score(){
        
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
    
    //a ideia é criar um método para preencher os vetores;
    private void setEventPoint(String s, int i){}
    
    public void reset(){
        Score.points = 0;
    }

    public int getPoints() {
        return points;
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
        g.fillRect(50, 55, 120, 40);
        g.setColor(Color.white);
        g.setFont(new Font("Arial", Font.BOLD, 16));
        g.drawString("Pontos: "+points, 60, 80);
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
