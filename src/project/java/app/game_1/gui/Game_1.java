package project.java.app.game_1.gui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import project.java.app.game_1.listener.MyActionListener;
import project.java.app.main.gui.MainMenu;
import project.java.app.main.util.BgmPlayer;
import project.java.app.model.io.RankingIO;
import project.java.app.model.vo.User;
import project.java.app.rangkingView.controller.UserManager;

public class Game_1 {
	
	private JFrame f = new JFrame();
	private JButton startGame = new JButton("게임시작");
	private JButton endGame = new JButton("게임종료");
	private JLabel record = new JLabel("경과 시간 : 0초");
	private JPanel gamePanel;
	private JPanel infoPanel;
	private MyActionListener l; 
	private Component[] arr;
	private ImageIcon back = new ImageIcon("images/game_1/3.png"); //뒷면 이미지
	private UserManager u = new UserManager();
	private MainMenu m;
	private Clip clip;
	
	public Game_1() {}
	
	public Game_1(MainMenu m) {
		this.m = m;
		
		clip = BgmPlayer.playBgm("sounds/game_1.wav", true);
		clip.start();
		
		panelSetting();
		addButton();
		frameSetting();
		arr = gamePanel.getComponents();
		
		record.setFont(new Font("휴먼둥근헤드라인", Font.BOLD, 20));
		record.setBounds(45, 160, 200, 30);
		
		infoPanel.add(record);
		
		startGame.setBounds(45, 500, 200, 40);
		endGame.setBounds(45, 570, 200, 40);
		infoPanel.add(startGame);
		infoPanel.add(endGame);
		
		l = new MyActionListener(this, arr, m);
		startGame.addActionListener(l);
		endGame.addActionListener(l);
		
		f.getContentPane().add(gamePanel);
		f.getContentPane().add(infoPanel);
		f.setVisible(true);
	}
	
	
	public void frameSetting() {
		f.setTitle("Game_1");
		f.getContentPane().setLayout(null);
		f.setResizable(false);
		
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		int width = 1000, height = 700;
		int x = (d.width-width)/2, y = (d.height-height)/2;
		f.setBounds(x, y, width, height);
	}
	
	public void panelSetting() {
		gamePanel = new JPanel();
		gamePanel.setBounds(0, 0, 700, 663);
		gamePanel.setLayout(new GridLayout(4,4,2,2));
		
		infoPanel = new JPanel() {
			public void paintComponent(Graphics g) {
				g.drawImage(new ImageIcon("images/game_1/fire.jpg").getImage(), 0, 0, null);
				setOpaque(false);
				super.paintComponent(g);
			}
		};
		infoPanel.setLayout(null);
		infoPanel.setBounds(700, 0, 286, 663);
	}
	
	public void addButton() {
		for(int i=0; i<16; i++) {
			JButton btn = new JButton(back);
			btn.setBorderPainted(false);
			btn.setFocusPainted(false); 
			btn.setContentAreaFilled(false);
			gamePanel.add(btn);
		}
	}
	
	public void Game_End(int sec) {
		String user_Name = "";
		int num = JOptionPane.showConfirmDialog(null, "저장하시겠습니까?", "게임종료", JOptionPane.YES_NO_OPTION);
		
		if(num==JOptionPane.YES_OPTION) {
			user_Name = JOptionPane.showInputDialog(null, "시간기록 : "+sec+"초"+"\n최고기록 : '업데이트 예정'\n닉네임 입력", JOptionPane.YES_NO_OPTION);			
			
			// 현재 날짜와 시간 가져오기
			SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd HH:mm");
			Date date = new Date();
			String now_time = sdf.format(date); 
			
			new RankingIO().writeUser(new User(user_Name, sec, now_time), 1);
		} 
		
		int num_ = JOptionPane.showConfirmDialog(null, "메인메뉴로 돌아가시겠습니까?", "메인메뉴로", JOptionPane.YES_NO_OPTION);
		if(num_==JOptionPane.YES_OPTION) {
			clip.stop();
			clip.close();
			BgmPlayer.playBgm("sounds/main.wav", 1);
			m.getF().setVisible(true);
			f.dispose();
		}
	}
	
	public void game_Restart() {
		gamePanel.removeAll();
		
		f.getContentPane().add(gamePanel);
		for(int i=0; i<16; i++) {
			JButton btn = new JButton(back);
			btn.setBorderPainted(false);
			btn.setFocusPainted(false); 
			btn.setContentAreaFilled(false);
			gamePanel.add(btn);
		}
		
		arr = null;
		arr = gamePanel.getComponents();
		
		startGame.removeActionListener(l);
		startGame.addActionListener(new MyActionListener(this, arr, m));
		
	}
	public JButton getStartGame() {
		return startGame;
	}
	
	public void setStartGame(JButton startGame) {
		this.startGame = startGame;
	}
	
	public JButton getEndGame() {
		return endGame;
	}
	
	public JFrame getF() {
		return f;
	}

	public void setF(JFrame f) {
		this.f = f;
	}

	public void setEndGame(JButton endGame) {
		this.endGame = endGame;
	}
	
	public JLabel getRecord() {
		return record;
	}
	
	public void setRecord(JLabel record) {
		this.record = record;
	}
	
	public JPanel getGamePanel() {
		return gamePanel;
	}
	
	public void setGamePanel(JPanel gamePanel) {
		this.gamePanel = gamePanel;
	}
	
	public ImageIcon getBack() {
		return back;
	}
	
	public void setBack(ImageIcon back) {
		this.back = back;
	}
}
