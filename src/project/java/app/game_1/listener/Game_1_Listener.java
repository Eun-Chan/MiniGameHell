package project.java.app.game_1.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JButton;

import project.java.app.game_1.gui.Game_1;
import project.java.app.game_1.util.StopWatch;
import project.java.app.game_1.util.WrongClick;
import project.java.app.main.util.BgmPlayer;


public class Game_1_Listener implements ActionListener{
	private List<Integer> btnTextList = new ArrayList<>();
	private Game_1 g;
	private StopWatch sw;
	
	public Game_1_Listener() {}
	
	public Game_1_Listener(List<Integer> btnTextList, Game_1 g, StopWatch sw) {
		this.btnTextList = btnTextList;
		this.g = g;
		this.sw = sw;
		Collections.sort(this.btnTextList);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton src = (JButton)e.getSource();
		BgmPlayer.playBgm("sounds/고양이소리.wav", 1);
		if(Integer.parseInt(src.getText())==btnTextList.get(0)) {
			sw.flag++;
			btnTextList.remove(0);
			src.setIcon(null);
			src.setEnabled(false);
			if(sw.flag==16) {
				sw.getStopWatch().interrupt();
				g.game_Restart();
				g.Game_End(sw.getNum());
				g.getStartGame().setEnabled(true);
				sw.flag = 0;
				sw.setNum(0);
			}
		} else {
			new WrongClick(src).getWc().start();
		}
	}
	public List<Integer> getBtnTextList() {
		return btnTextList;
	}

	public void setBtnTextList(List<Integer> btnTextList) {
		this.btnTextList = btnTextList;
	}	
}
