package project.java.app.game_3.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class EffectPanel extends JPanel implements Runnable{
    Vector<Point> v =new Vector<Point>();//눈덩이 50개의 위치를 저장
   
    public EffectPanel(){
        this.setLayout(null);
        for(int i=0; i<50; i++){
            int x=(int)(Math.random()*300);
            int y=(int)(Math.random()*340);
            v.add(new Point(x,y));
        }
    }
    
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        ImageIcon icon=new ImageIcon("images/game_3/back.png");
        Image img=icon.getImage();
        g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), this);//배경
        g.setColor(Color.WHITE);
        for(int i=0; i<v.size(); i++){//10x10 눈덩이를 그린다.
            Point p=v.get(i);
            g.fillOval((int)p.getX(), (int)p.getY(), 5, 5);
        }
    }
    
    public void changeSnowPoaition(){
        for(int i=0; i<v.size(); i++){
            Point p=v.get(i);
            p.x+=(int)(Math.random()*20);
            p.y+=(int)(Math.random()*5);
            //눈덩이가 프레임 밖으로 나가게 되면 나간 좌표를 0으로 초기화
            if(p.x>300)
                p.x=0;
            if(p.y>340)
                p.y=0;
            v.set(i, p);
        }
    }
    
    @Override
    public void run(){
        while(true){
            try{
                Thread.sleep(50);
            }
            catch(Exception e){
                return;
            }
            changeSnowPoaition();
            repaint();//계속 업데이트
        }
    }
}