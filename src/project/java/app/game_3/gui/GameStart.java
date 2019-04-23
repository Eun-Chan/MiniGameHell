package project.java.app.game_3.gui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import project.java.app.main.gui.MainMenu;

public class GameStart extends JFrame {

	JButton start_Btn;
	private MainMenu m;
	
	public GameStart() {}
	
	public GameStart(MainMenu m) {

		// 버튼에 맞게 이미지 리사이징
		ImageIcon start_icon = new ImageIcon("images/game_3/start_Btn.png");
		Image start_img = start_icon.getImage();
		Image cha_start_img = start_img.getScaledInstance(300, 190,
				java.awt.Image.SCALE_SMOOTH);

		// 리사이징 된 이미지 아이콘
		ImageIcon change_icon = new ImageIcon(cha_start_img);
		
		start_Btn = new JButton(change_icon);
				
		setLayout(null);

		// 게임화면 너비 : 1000 , 높이 : 700 고정 , x,y는 중앙 고정
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		int w = 1000;
		int h = 700;
		int x = (dimension.width - w) / 2;
		int y = (dimension.height - h) / 2;
		setBounds(x, y, w, h);

		start_Btn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (e.getSource() == start_Btn) {
					new Game_3(m);
					dispose();
				}
			}
		});

		JPanel main_View = new JPanel();
		main_View.setLayout(null);
		main_View.setBounds(0, 0, 1000, 700);

		// 버튼에 맞게 이미지 리사이징
		ImageIcon icon = new ImageIcon("images/game_3/game_Image.png");
		Image img = icon.getImage();
		Image change_img = img.getScaledInstance(700, 690, java.awt.Image.SCALE_SMOOTH);

		JLabel game_View = new JLabel();
		game_View.setIcon(new ImageIcon(change_img));
		game_View.setBounds(0, 0, 700, 690);
		main_View.add(game_View);

		// 버튼에 맞게 이미지 리사이징
		ImageIcon icon2 = new ImageIcon("images/game_3/game_help.png");
		Image img2 = icon2.getImage();
		Image change_img2 = img2.getScaledInstance(300, 500, java.awt.Image.SCALE_SMOOTH);

		JLabel game_Help = new JLabel();
		game_Help.setIcon(new ImageIcon(change_img2));
		game_Help.setBounds(700, 0, 300, 500);
		main_View.add(game_Help);

		start_Btn.setBounds(700, 500, 300, 190);
		main_View.add(start_Btn);

		add(main_View);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

}
