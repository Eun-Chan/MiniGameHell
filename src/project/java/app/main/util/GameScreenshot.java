package project.java.app.main.util;

import java.awt.Graphics;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import project.java.app.main.gui.MainMenu;

public class GameScreenshot {
	private MainMenu m;
	private Thread gs = new Thread(new StopWatch());
	private JPanel gameScreenshot;
	private JPanel yeePanel;
	private ImageIcon gameImg;
	private ImageIcon yeeImg;
	private Random rnd = new Random();

	public GameScreenshot() {}
	
	public GameScreenshot(MainMenu m) {
		this.m = m;
	}
	
	class StopWatch implements Runnable {

		@Override
		public void run() {
			try {
				int a = 1;
				int flag = 1;
				
				while(true) {
					if(a>9) a = 1;
					gameImg = new ImageIcon("images/main/Screenshot/"+a+".png");
					gameScreenshot = new JPanel() {
						public void paintComponent(Graphics g) {
							g.drawImage(gameImg.getImage(), 0, 0, null);
							setOpaque(false);
							super.paintComponent(g);
						}
					};
					
					gameScreenshot.setBounds(400, 240, 400, 400);
					m.getBackPanel().add(gameScreenshot);
					m.getF().setContentPane(m.getBackGround());
					a++;
					flag++;
					Thread.sleep(1000);
					if(!(flag==7)) continue;
					printYee();
					flag=-2;
				}
				
			} catch(Exception e) {
				
			}
		}
		
		public void printYee() {
			int x = rnd.nextInt(568);
			int y = rnd.nextInt(345);
			
			yeeImg = new ImageIcon("images/main/yee.png");
			yeePanel = new JPanel() {
				public void paintComponent(Graphics g) {
					g.drawImage(yeeImg.getImage(), 0, 0, null);
					setOpaque(false);
					super.paintComponent(g);
				}
			};
			
			yeePanel.setBounds(x, y, 232, 655);
			m.getBackPanel().add(yeePanel, 0);
		} 
	}
	
	public MainMenu getM() {
		return m;
	}
	
	public void setM(MainMenu m) {
		this.m = m;
	}
	
	public Thread getGs() {
		return gs;
	}
	
	public void setGs(Thread gs) {
		this.gs = gs;
	}
}
