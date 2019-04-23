package project.java.app.game_1.listener;

import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JButton;

import project.java.app.game_1.gui.Game_1;
import project.java.app.game_1.util.StopWatch;
import project.java.app.main.gui.MainMenu;
import project.java.app.main.util.BgmPlayer;

public class MyActionListener implements ActionListener{
		
	private Game_1 g;
	private Random rnd = new Random();
	private int[] flagArr = new int[16];
	private List<Integer> btnTextList = new ArrayList<>();
	private StopWatch sw;
	private Component[] arr;
	private MainMenu m;
	
	{
		while(true) {
			if(btnTextList.size()==16) break;
			int num = rnd.nextInt(16)+1;
			if(flagArr[num-1] == 1) continue;
			flagArr[num-1] = 1;
			btnTextList.add(num);
		}
		
	}
	
	public MyActionListener() {}
	
	public MyActionListener(Game_1 g, Component[] arr, MainMenu m) {
		this.arr = arr;
		this.g = g;
		this.m = m;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		JButton src = (JButton) e.getSource();

		if (src == g.getStartGame()) {
			sw = new StopWatch(g, arr);
			sw.getStopWatch().start();
			
			for(int i=0; i<arr.length; i++) {
				((JButton)arr[i]).setIcon(null);
				((JButton)arr[i]).setText(btnTextList.get(i).toString());
				((JButton)arr[i]).setFont(new Font("휴먼매직체", Font.BOLD, 25));
				((JButton)arr[i]).setBorderPainted(true);
				((JButton)arr[i]).setEnabled(false);
			}
			
			for(int i=0; i<arr.length; i++) {
				((JButton)arr[i]).addActionListener(new Game_1_Listener(btnTextList, g, sw));
			}
			
			g.getStartGame().setEnabled(false);			
			
		} else if (src == g.getEndGame()) {
			BgmPlayer.stopBgm();
			BgmPlayer.playBgm("sounds/main.wav");
			m.getF().setVisible(true);
			g.getF().dispose();
		}
	}

	public Game_1 getG() {
		return g;
	}

	public void setG(Game_1 g) {
		this.g = g;
	}

	public Random getRnd() {
		return rnd;
	}

	public void setRnd(Random rnd) {
		this.rnd = rnd;
	}

	public List<Integer> getBtnTextList() {
		return btnTextList;
	}

	public void setBtnTextList(List<Integer> btnTextList) {
		this.btnTextList = btnTextList;
	}
}
