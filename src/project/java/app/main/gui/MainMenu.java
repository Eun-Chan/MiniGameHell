package project.java.app.main.gui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import project.java.app.game_1.gui.Game_1;
import project.java.app.game_2.run.Game_2;
import project.java.app.game_3.gui.GameStart;
import project.java.app.main.util.BgmPlayer;
import project.java.app.main.util.GameScreenshot;
import project.java.app.rangkingView.gui.RankingView;

public class MainMenu {
	private JFrame f = new JFrame();
	private String[] buttonName = { "같은그림찾기", "지뢰찾기", "숫자기억", "랭킹보기", "게임종료" };
	private JScrollPane backGround;
	private ImageIcon icon;
	private JPanel backPanel;
	private ImageIcon gameImg;

	public MainMenu() {
		
		BgmPlayer.playBgm("sounds/main.wav");
		f.setTitle("미니게임 지옥");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setLayout(null);

		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		int width = 1000, height = 700;
		int x = (d.width - width) / 2, y = (d.height - height) / 2;
		f.setBounds(x, y, width, height);

		backGroundSetting();
		addButton();
		f.setVisible(true);
	}

	public void backGroundSetting() {
		icon = new ImageIcon("images/main/main.png");

		backPanel = new JPanel() {
			public void paintComponent(Graphics g) {
				g.drawImage(icon.getImage(), 0, 0, null);
				setOpaque(false);
				super.paintComponent(g);
			}
		};
		backPanel.setLayout(null);
		backGround = new JScrollPane(backPanel);

		GameScreenshot gs = new GameScreenshot(this);
		gs.getGs().start();
		f.setContentPane(backGround);
	}

	public void addButton() {
		int y = 320;

		for (int i = 0; i < 5; i++) {
			ImageIcon img = new ImageIcon("Images/main/ButtonImages/" + (i + 1) + ".png");
			JButton bt = new JButton(img);
			bt.setName(buttonName[i]);
			bt.setBounds(810, y, 150, 50);
			buttonSetting(bt, img);
			backPanel.add(bt);
			y += 65;
		}
	}

	public void buttonSetting(JButton bt, ImageIcon img) {
		bt.addMouseListener(new MyMouseListener());
		bt.setIcon(img);
		bt.setBorderPainted(false);
		bt.setFocusPainted(false);
		bt.setContentAreaFilled(false);
	}

	class MyMouseListener extends MouseAdapter {

		@Override
		public void mouseClicked(MouseEvent e) {
			JButton src = (JButton) e.getSource();

			if (src.getName().equals(buttonName[0])) {
				BgmPlayer.stopBgm();
				new Game_1(MainMenu.this);
				JOptionPane.showMessageDialog(null, "1~16까지 작은 숫자부터 누르면 됩니다.");
				f.setVisible(false);
			} else if (src.getName().equals(buttonName[1])) {
				BgmPlayer.stopBgm();
				new Game_2(MainMenu.this);
				f.setVisible(false);
			}
			else if(src.getName().equals(buttonName[2])) {
				BgmPlayer.stopBgm();
				new GameStart(MainMenu.this);
				f.setVisible(false);
			}
			else if(src.getName().equals(buttonName[3])) {
				new RankingView(MainMenu.this);
				f.setVisible(false);
			}
			else if (src.getName().equals(buttonName[4]))
				System.exit(0);
		}
	}

	public JFrame getF() {
		return f;
	}

	public void setF(JFrame f) {
		this.f = f;
	}

	public String[] getButtonName() {
		return buttonName;
	}

	public void setButtonName(String[] buttonName) {
		this.buttonName = buttonName;
	}

	public JScrollPane getBackGround() {
		return backGround;
	}

	public void setBackGround(JScrollPane backGround) {
		this.backGround = backGround;
	}

	public ImageIcon getIcon() {
		return icon;
	}

	public void setIcon(ImageIcon icon) {
		this.icon = icon;
	}

	public JPanel getBackPanel() {
		return backPanel;
	}

	public void setBackPanel(JPanel backPanel) {
		this.backPanel = backPanel;
	}

	public ImageIcon getGameImg() {
		return gameImg;
	}

	public void setGameImg(ImageIcon gameImg) {
		this.gameImg = gameImg;
	}
}
