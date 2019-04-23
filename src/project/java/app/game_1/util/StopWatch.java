package project.java.app.game_1.util;

import java.awt.Component;
import java.awt.GridLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import project.java.app.game_1.gui.Game_1;
import project.java.app.game_1.listener.Game_1_Listener;

public class StopWatch {

	private Game_1 g;
	private Thread stopWatch = new Thread(new StopWatch_());
	private int num = 0;
	private Component[] compArr;
	public static int flag = 0;
	
	public StopWatch() {}
	
	public StopWatch(Game_1 g, Component[] compArr) {
		this.g = g;
		this.compArr = compArr;
	}
	
	class StopWatch_ implements Runnable {
		
		@Override
		public void run() {
			ImageIcon img = new ImageIcon("images/game_1/4.png");
			
			while (true) {
				try {
					g.getRecord().setText("경과 시간 : "+String.valueOf(num++)+"초");
					Thread.sleep(1000);
					if(num==6) {
						for(int i=0; i<16; i++) {
							((JButton)compArr[i]).setIcon(img);
							((JButton)compArr[i]).setBorderPainted(false);
							((JButton)compArr[i]).setFocusPainted(false); 
							((JButton)compArr[i]).setContentAreaFilled(false);
							((JButton)compArr[i]).setEnabled(true);
						}
					}
				} catch (Exception e) {
					g.getRecord().setText("경과 시간 : "+String.valueOf(num)+"초");
					break;
				}
			}
		}
	}
	
	public Thread getStopWatch() {
		return stopWatch;
	}

	public void setStopWatch(Thread stopWatch) {
		this.stopWatch = stopWatch;
	}
	
	public int getNum() {
		return num;
	}
	
	public void setNum(int num) {
		this.num = num;
	}
}
