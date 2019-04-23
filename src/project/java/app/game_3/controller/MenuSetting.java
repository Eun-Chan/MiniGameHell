package project.java.app.game_3.controller;

import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import project.java.app.game_3.gui.Game_3;
import project.java.app.main.gui.MainMenu;
import project.java.app.main.util.BgmPlayer;
import project.java.app.game_3.gui.EffectPanel;

public class MenuSetting {
	private Game_3 gui;
	Thread effect;
	EffectPanel ep; 
	
	// 메뉴 화면 Panel (event에서 사용하기 위해 전역에서 선언)
	public JPanel menu = new JPanel() {
		public void paintComponent(Graphics g) {
			
			// 버튼에 맞게 이미지 리사이징
			ImageIcon icon = new ImageIcon("images/game_3/background.jpg");
			Image img = icon.getImage();
			Image change_img = img.getScaledInstance(300, 350,
					java.awt.Image.SCALE_SMOOTH);
			
			g.drawImage(new ImageIcon(change_img).getImage(), 0, 0, null);
			setOpaque(false);
			super.paintComponent(g);
		}
	};
	
	public MenuSetting(Game_3 gui) {
		this.gui = gui;
	}
	
	public void menu_Set(MainMenu m){
		
		ep = new EffectPanel();
		ep.setBounds(700, 350, 300, 340);
		gui.frame.add(ep);
		
		effect = new Thread(ep);
		effect.start();
		
		menu.setBounds(700, 0, 300, 540);
		menu.setLayout(null);
		
		gui.score_Label.setBounds(40, 50, 200, 100);
		gui.score_Label.setFont(new Font("Serif", Font.PLAIN, 30));

		// 점수판 라벨
		gui.score_view.setBounds(150, 50, 100, 100);
		gui.score_view.setFont(new Font("Serif", Font.PLAIN, 50));

		// 타이머 텍스트 라벨
		gui.time_label.setBounds(40, 100, 200, 100);
		gui.time_label.setFont(new Font("Serif", Font.PLAIN, 30));

		// 타이머 라벨
		gui.time_view.setBounds(150, 100, 100, 100);
		gui.time_view.setFont(new Font("Serif", Font.PLAIN, 50));

		// 메인메뉴 가기 버튼
		JButton exit_btn = new JButton("메인 메뉴 GO");
		exit_btn.setBounds(10, 10, 280, 70);
		exit_btn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(e.getSource() == exit_btn) {
					BgmPlayer.stopBgm();
					BgmPlayer.playBgm("sounds/main.wav");
					gui.frame.dispose();
					m.getF().setVisible(true);
					gui.menu_go = 1;
				}
			}
		});
		
		// 패널에 추가
		menu.add(gui.score_Label);
		menu.add(gui.score_view);
		menu.add(gui.time_label);
		menu.add(gui.time_view);
		menu.add(exit_btn);
		gui.frame.add(menu);

		gui.timer.start();
		
	}
}
