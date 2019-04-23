package project.java.app.game_3.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import project.java.app.game_3.controller.MenuSetting;
import project.java.app.game_3.controller.Timer;
import project.java.app.game_3.vo.ImageBtn;
import project.java.app.main.gui.MainMenu;
import project.java.app.main.util.BgmPlayer;
import project.java.app.model.io.RankingIO;
import project.java.app.model.vo.User;

/**
 * @author EunChan 같은 그림 찾기
 */

public class Game_3 {
	private MainMenu m;
	public JFrame frame = new JFrame(); // 메인 프레임 생성

	public JLabel score_Label = new JLabel("Score"); // Score 텍스트 Label
	public JLabel score_view = new JLabel("0"); // Score Label
	
	// 게임 화면 Panel (event에서 사용하기 위해 전역에서 선언)
	public JPanel gamePanel = new JPanel() {
		public void paintComponent(Graphics g) {
			// Panel에 맞게 이미지 리사이징
			ImageIcon icon = new ImageIcon("images/game_3/loading.png");
			Image img = icon.getImage();
			Image change_img = img.getScaledInstance(700, 680,
					java.awt.Image.SCALE_SMOOTH);
			
			g.drawImage(new ImageIcon(change_img).getImage(), 0, 0, null);
			setOpaque(false);
			super.paintComponent(g);
		}
	};
	
	// public JPanel menu = new JPanel(); // 메뉴 화면 Panel (event에서 사용하기 위해 전역에서 선언)
	public JLabel time_view = new JLabel("0"); // 경과 시간 Label
	public JLabel time_label = new JLabel("TIME"); // 타임 텍스트 Label

	MenuSetting menuSetting;

	// 남은 시간 표현을 위한 쓰레드
	public Thread timer = new Timer(this);
	public int time = 31;

	// 제출할 문제를 List에 담기, 밑에서 2번 넣는 이유는 같은그림이 2개 이기 때문
	// 동일한 button을 2번 그릴수 있는 방법이 있는지 찾아봐야할듯..
	public Object[] arr;

	// 현재 카드 짝을 찾은 수 (종료 조건으로 사용)
	public int find_card = 0;

	// 게임 종료를 알리는 변수
	public int ending = 0;

	// 게임 레벨에 대한 옵션을 저장하기 위한 2차원 배열 {랜덤 수의 갯수, 그리드 크기 , 리사이징 이미지 크기 w, 리사이징 이미지 크기
	// h, level 당 추가 시간}
	public int[][] level_Option = { {}, { 10, 4, 5, 120, 100, 31 }, { 15, 5, 6, 90, 100, 41 },
			{ 21, 6, 7, 70, 80, 51 } };

	// 점수 표현 변수
	public int cnt = 0;

	// 게임 레벨 변수
	public int level = 1;

	// 타이머 숫자를 부드럽게 넘기기 위한 플래그 변수
	public int time_flag = 1;
	
	// 메뉴가기 go 눌렀을 경우 flag
	public int menu_go = 0;
	
	public Game_3() {}
	
	public Game_3(MainMenu m) {
		this.m = m;

		frame.setTitle("같은 그림 찾기");

		// 게임화면 너비 : 1000 , 높이 : 700 고정 , x,y는 중앙 고정
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		int w = 1000;
		int h = 700;
		int x = (dimension.width - w) / 2;
		int y = (dimension.height - h) / 2;
		frame.setBounds(x, y, w, h);

		// 레이아웃 사용 X
		frame.setLayout(null);

		// frame resize
		frame.setResizable(false);

		// cardlayout을 통해 스타트 화면 과 게임 화면 분리
		frame.setLayout(null);

		// 왼쪽 게임 Panel Set
		GameSetting();

		// 오른쪽 메뉴
		menuSetting = new MenuSetting(this);
		menuSetting.menu_Set(m);

		music();

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

	public void GameSetting() {

		// 패널 크기 및 배경 지정
		gamePanel.setBounds(0, 0, 700, 680);
		
		// Panel에 맞게 이미지 리사이징
		ImageIcon icon = new ImageIcon("images/game_3/loding.png");
		Image img = icon.getImage();
		Image change_img = img.getScaledInstance(700,680, java.awt.Image.SCALE_SMOOTH);
		
		set_card(level);

	}

	class MouseListener implements ActionListener {
		private int i;// 몇번째 버튼이 눌렸는지 확인하는 변수
		private ImageBtn[] imageBtn;// ImageBtn메소드를 이용하기 위해 선언된 변수
		private int[] arr;// 눌린 버튼 1,2의 num값을 저장하기 위해 선언된 버튼
		private int check;// 0,1으로 변경이 되며 arr과 iArr값에 영향을 끼침.
		private int[] iArr;// i값을 저장하는 변수로 2개의 눌린 버튼위치를 저장하는 변수

		public MouseListener(ImageBtn[] imageBtn) {
			this.imageBtn = imageBtn;
			arr = new int[2];
			check = 0;
			iArr = new int[2];
		}

		@Override
		public void actionPerformed(ActionEvent e) {

			// TODO Auto-generated method stub
			i = Integer.parseInt(e.getActionCommand());
			iArr[check] = i;

			arr[check] = imageBtn[i].clickButton();
			gamePanel.paintImmediately(0, 0, 700, 680);

			// 이미 누른 카드를 또 누를 경우
			if (arr[check] == -1)
				return;

			// 두개의 카드를 선택했을 시
			if (check == 1) {
				// 카드가 서로 다를시
				if (arr[0] != arr[1]) {
					if (cnt > 0)
						cnt -= 10;
					imageBtn[iArr[0]].returnImage();
					try {
						Thread.sleep(200);
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					imageBtn[iArr[1]].returnImage();

				}
				// 카드가 같을 시
				else {
					cnt += 100;
					++find_card;
					imageBtn[arr[0]].setState(true);
					imageBtn[arr[0]].setState(true);
				}

				score_view.setText(String.valueOf(cnt));

				menuSetting.menu.paintImmediately(700, 0, 300, 700);

				// 종료 조건 , 카드를 다 찾거나 남은 시간이 0초 일
				if (find_card == level_Option[level][0]) {
					// level이 1~2 단계 일 경우는 다음 단계로 업, 3단계 까지 다 했을 경우 종료
					++level;
					timer.interrupt();
					if (level == 4) {
						ending = 1;
					} else {
						time_flag = 0;
						set_card(level);
						find_card = 0;
						time += level_Option[level][5];
						menuSetting.menu.paintImmediately(700, 0, 300, 700);
					}
				}

				check = 0;
				return;
			}
			check++;

		}
	}

	public void set_card(int level) {

		// 그리드 레이아웃 세팅
		gamePanel.setLayout(new GridLayout(level_Option[level][1], level_Option[level][2]));

		// 같은 그림 세팅
		// Set을 통해 중복 제거 및 LinkedHashSet으로 set에 대한 입력 순서 보존
		Set<Integer> set = new LinkedHashSet<>();

		// 랜덤 수 저장 ===> 랜덤으로 문제가 출제되기 위해 사용
		while (set.size() < level_Option[level][0])
			set.add((int) ((Math.random() * level_Option[level][0]) + 1));

		// 배열에 랜덤수 저장
		arr = set.toArray();

		// 카드 저장하는 배열
		ImageBtn[] imageBtn = new ImageBtn[level_Option[level][0] * 2];

		// 이전 레벨의 카드 모두 제거 (새로 카드를 insert 하기 위해)
		gamePanel.removeAll();
		gamePanel.repaint();

		// 리스너 생성
		MouseListener listener = new MouseListener(imageBtn);

		for (int i = 0; i < arr.length; i++) {
			String fileName = "images/game_3/" + String.valueOf(arr[i]) + ".png";

			// 버튼에 맞게 이미지 리사이징
			ImageIcon icon = new ImageIcon(fileName);
			Image img = icon.getImage();
			Image change_img = img.getScaledInstance(level_Option[level][3], level_Option[level][4],
					java.awt.Image.SCALE_SMOOTH);

			// 리사이징 된 이미지 아이콘
			ImageIcon change_icon = new ImageIcon(change_img);

			// 카드 뒷면 이미지 리사이징
			ImageIcon def = new ImageIcon("images/game_3/default.png");
			Image def_img = def.getImage();
			Image change_def = def_img.getScaledInstance(level_Option[level][3], level_Option[level][4],
					java.awt.Image.SCALE_SMOOTH);
			ImageIcon result_def = new ImageIcon(change_def);

			ImageBtn btn = new ImageBtn(Integer.toString(2 * i), (int) arr[i], change_icon, result_def);
			ImageBtn btn2 = new ImageBtn(Integer.toString((2 * i) + 1), (int) arr[i], change_icon, result_def);

			imageBtn[2 * i] = btn;
			imageBtn[(2 * i) + 1] = btn2;

			imageBtn[2 * i].addActionListener(listener);
			imageBtn[(2 * i) + 1].addActionListener(listener);
		}

		// 랜덤 수 저장 ===> 랜덤으로 문제가 출제되기 위해 사용
		while (set.size() < level_Option[level][0] * 2)
			set.add((int) ((Math.random() * level_Option[level][0] * 2)));

		// 랜덤수 저장
		arr = set.toArray();

		// 버튼을 랜덤으로 배치 설정
		for (int i = 0; i < imageBtn.length; i++) {
			gamePanel.add(imageBtn[(int) arr[i]]);
		}

		time_flag = 1;

		// 패널에 추가
		frame.add(gamePanel);
	}

	public void Game_End() {

		Image icon = new ImageIcon("images/game_3/1.png").getImage().getScaledInstance(60, 60, 0);
		ImageIcon iconn = new ImageIcon(icon);

		String user_Name = JOptionPane.showInputDialog(null, "스코어 " + score_view.getText() + "\n남은 시간 " + time,
				"같은 그림 찾기 랭킹 등록", JOptionPane.YES_NO_OPTION);

		// 현재 날짜와 시간 가져오기
		SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd");
		Date date = new Date();
		String now_time = sdf.format(date);
						
		new RankingIO().writeUser(new User(user_Name, time, cnt,now_time), 3);
		
		
		
		int run = JOptionPane.showConfirmDialog(null, "게임을 다시 시작 하시겠습니까?", "Again?", JOptionPane.YES_NO_OPTION,
				JOptionPane.PLAIN_MESSAGE, iconn);

		if (run == JOptionPane.YES_OPTION) {
			game_Reset();
		} else if (run == JOptionPane.NO_OPTION) {
			BgmPlayer.stopBgm();
			BgmPlayer.playBgm("sounds/main.wav");
			frame.dispose();
			m.getF().setVisible(true);
		}
	}

	public void game_Reset() {
		// 해당 정부 모두 초기화 및 label 초기화
		find_card = 0;
		cnt = 0;
		level = 1;
		time = 31;
		ending = 0;
		set_card(level);
		score_view.setText("0");
		time_view.setText("0");
		gamePanel.paintImmediately(0, 0, 700, 680);
		menuSetting.menu.paintImmediately(700, 0, 300, 700);
		timer = new Timer(this);
		timer.start();
	}

	public void music() {

//		Clip clip;
//
//		try {
//			AudioInputStream stream;
//			AudioFormat format;
//			DataLine.Info info;
//
//			stream = AudioSystem.getAudioInputStream(new File("sounds/game_3.wav"));
//			format = stream.getFormat();
//			info = new DataLine.Info(Clip.class, format);
//			clip = (Clip) AudioSystem.getLine(info);
//			clip.open(stream);
//
//			clip.start();
//		}
//
//		catch (Exception e) {
//			// whatevers
//		}
		BgmPlayer.playBgm("sounds/game_3.wav");
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new GameStart();
	}
}
