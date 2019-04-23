package project.java.app.game_2.vo;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import project.java.app.main.gui.MainMenu;
import project.java.app.main.util.BgmPlayer;
import project.java.app.model.io.RankingIO;
import project.java.app.model.vo.User;
import project.java.app.rangkingView.controller.UserManager;

public class Board {
	// coordinating behavior of cells.
	private Cell[][] cells;
	private int cellID = 0;
	private int side = 8;
	private int limit = side - 2;
	private Timer clock;
	private int counter;
	private JButton try_;
	private JLabel time;
	private JButton start;
	ArrayList<Integer> loc1;
	ArrayList<Integer> loc2;
	private JFrame f;
	private int sum = 0;
	private JButton goback;
	private JLabel emoji;
	private JPanel name;
	Image img, icon;
	Image change_img;
	ImageIcon iconn;
	private boolean check;
	private MainMenu m;
	private UserManager u = new UserManager();
	private JButton closed;

	public Board() {
	}

	public Board(MainMenu m) {
		this.m = m;
		BgmPlayer.playBgm("sounds/game_2.wav", 1);
		setBoard();
	}

	public int getSum() {
		return sum;
	}

	public void setSum(int sum) {
		this.sum = sum;
	}

	public boolean isCheck() {
		return check;
	}

	public void setCheck(boolean check) {
		this.check = check;
	}

	public Timer getClock() {
		return clock;
	}

	public void setClock(Timer clock) {
		this.clock = clock;
	}

	public void setBoard() {
		loc1 = generateMinesLocation(1); // 지뢰 생성
		loc2 = new ArrayList<Integer>(); //

		Cell cc = new Cell(this);

		f = new JFrame();
		f.setTitle("MineSweeper");
		f.setSize(1000, 700);

		icon = new ImageIcon("images/game_2/bell.png").getImage().getScaledInstance(40, 40, 0);
		iconn = new ImageIcon(icon);

		JOptionPane.showMessageDialog(null, "시작 버튼을 누르고 게임을 진행하세요", "NOTICE", JOptionPane.PLAIN_MESSAGE, iconn);

		emoji = new JLabel();

		try {
			img = ImageIO.read(new File("images/game_2/firstsmile.png"));
			change_img = img.getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH);
			emoji.setIcon(new ImageIcon(change_img));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		name = new JPanel();

		name.setLayout(new GridLayout(1, 5));

		start = new JButton("시작");
		start.setFont(new Font("Dialog", Font.PLAIN, 12));
		check = false;

		time = new JLabel("timer");
		time.setFont(new Font("Dialog", Font.PLAIN, 12));

		try_ = new JButton("다시시도");
		try_.setFont(new Font("Dialog", Font.PLAIN, 12));

		goback = new JButton("메인으로 돌아가기");
		goback.setFont(new Font("Dialog", Font.PLAIN, 12));
		
		closed = new JButton("종료하기"); //추가
		closed.setFont(new Font("Dialog", Font.PLAIN, 12)); //추가

		name.add(start);
		name.add(time);

		name.add(emoji, BorderLayout.CENTER);

		name.add(try_);
		name.add(goback);
		name.add(closed); //추가

		clock = new Timer();

		class MyEvent implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent e) { // 시작버튼을 눌러야 카운터가 시작

				if (e.getSource() == start) {

					counter = 0;
					clock.scheduleAtFixedRate(new TimerTask() {
						public void run() {

							counter++;
							time.setText(counter + "sec");
							time.setForeground(Color.RED);

							try {
								img = ImageIO.read(new File("images/game_2/secondsmile.png"));
								change_img = img.getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH);
								emoji.setIcon(new ImageIcon(change_img));

							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}

					}, new Date(), 1000);

					check = true;

//					System.out.println(check);
				}
			}
		}

		MyEvent a = new MyEvent();
		start.addActionListener(a);

		try_.addActionListener(new ActionListener() { // 다시시도

			@Override
			public void actionPerformed(ActionEvent e) {

				if (e.getSource() == try_) {

					playagain(1);
				}

			}

		});

		// 메인으로 돌아가시겠습니까 ?
		goback.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == goback) {
					f.dispose();
					BgmPlayer.stopBgm();
					BgmPlayer.playBgm("sounds/main.wav");
					m.getF().setVisible(true);
				}
			}
		});
		
		 closed.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					if(e.getSource()==closed) {
						icon = new ImageIcon("images/game_2/bye.png").getImage().getScaledInstance(40, 40, 0);
				    	iconn = new ImageIcon(icon);
						JOptionPane.showMessageDialog(null,"게임을 종료합니다! ","NOTICE", JOptionPane.PLAIN_MESSAGE,iconn);
						f.dispose();
			          	
						
					}	
					
				}
		    	
		    });
		

		f.add(name, BorderLayout.NORTH);
		f.add(addCells());

		plantMines();
		setCellValues();

		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		f.setVisible(true);

	}

	public JPanel addCells() { // 셀 보여줌
		JPanel panel = new JPanel(new GridLayout(side, side));

		cells = new Cell[side][side];
//		System.out.println(check);		

		for (int i = 0; i < side; i++) {
			for (int j = 0; j < side; j++) {
				cells[i][j] = new Cell(this);
				cells[i][j].setId(getID());

				panel.add(cells[i][j].getButton());

			}
		}

		return panel;

	}

	public void plantMines() { // 지뢰심기
		loc1 = generateMinesLocation(10);

		for (int i : loc1) {
			getCell(i).setValue(-1);
		}
	}

	public ArrayList<Integer> generateMinesLocation(int q) {
		loc2 = new ArrayList<Integer>();
		int random;
		for (int i = 0; i < q;) { // 지뢰 위치 설정
			random = (int) (Math.random() * (side * side));
			if (!loc2.contains(random)) {
				loc2.add(random);
				i++;
			}

		}
		return loc2;
	}

	public void setCellValues() { // 지뢰 내 옆에 몇개 있니~

		for (int i = 0; i < side; i++) {
			for (int j = 0; j < side; j++) {
				if (cells[i][j].getValue() != -1) {

					if (j >= 1 && cells[i][j - 1].getValue() == -1)
						cells[i][j].incrementValue();
					if (j <= limit && cells[i][j + 1].getValue() == -1)
						cells[i][j].incrementValue();
					if (i >= 1 && cells[i - 1][j].getValue() == -1)
						cells[i][j].incrementValue();
					if (i <= limit && cells[i + 1][j].getValue() == -1)
						cells[i][j].incrementValue();
					if (i >= 1 && j >= 1 && cells[i - 1][j - 1].getValue() == -1)
						cells[i][j].incrementValue();
					if (i <= limit && j <= limit && cells[i + 1][j + 1].getValue() == -1)
						cells[i][j].incrementValue();
					if (i >= 1 && j <= limit && cells[i - 1][j + 1].getValue() == -1)
						cells[i][j].incrementValue();
					if (i <= limit && j >= 1 && cells[i + 1][j - 1].getValue() == -1)
						cells[i][j].incrementValue();

				}
			}
		}
	}

	public void scanForEmptyCells() throws IOException { // 빈칸 연쇄 반응 일으키는 코드
		/*
		 * 유저가 셀을 클릭했을때, 만약에그 셀의 value 값이 0 이면 옆에 있는 다른 셀들까지 클릭됨(비어있는 셀들만) value 값이 다른거면
		 * checkCell이 실행
		 */
		for (int i = 0; i < side; i++) {
			for (int j = 0; j < side; j++) {
				if (!cells[i][j].isNotChecked()) {

					if (j >= 1 && cells[i][j - 1].isEmpty())
						cells[i][j - 1].checkCell();
					if (j <= limit && cells[i][j + 1].isEmpty())
						cells[i][j + 1].checkCell();
					if (i >= 1 && cells[i - 1][j].isEmpty())
						cells[i - 1][j].checkCell();
					if (i <= limit && cells[i + 1][j].isEmpty())
						cells[i + 1][j].checkCell();
					if (i >= 1 && j >= 1 && cells[i - 1][j - 1].isEmpty())
						cells[i - 1][j - 1].checkCell();
					if (i <= limit && j <= limit && cells[i + 1][j + 1].isEmpty())
						cells[i + 1][j + 1].checkCell();
					if (i >= 1 && j <= limit && cells[i - 1][j + 1].isEmpty())
						cells[i - 1][j + 1].checkCell();
					if (i <= limit && j >= 1 && cells[i + 1][j - 1].isEmpty())
						cells[i + 1][j - 1].checkCell();

				}
			}
		}
	}

	public int getID() { // cell 숫자
		int id = cellID;
		cellID++;
		return cellID;
	}

	public Cell getCell(int id) { // 숫자 표시
		for (Cell[] a : cells) {
			for (Cell b : a) {
				if (b.getId() == id) {
					return b;
				}
			}
		}

		return null;

	}

	public void fail() throws IOException { // 지뢰 클릭시 게임 종료

		try {
			img = ImageIO.read(new File("images/game_2/crying.png"));
			change_img = img.getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH);
			emoji.setIcon(new ImageIcon(change_img));

		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		for (Cell[] a : cells) {
			for (Cell b : a) {
				sum = 0;
				b.reveal();
			}
		}

		clock.cancel();

		icon = new ImageIcon(img).getImage().getScaledInstance(40, 40, 0);
		iconn = new ImageIcon(icon);
		JOptionPane.showMessageDialog(null, "실패하셨네요ㅠㅠ", "ㅠ_ㅠ", JOptionPane.PLAIN_MESSAGE, iconn);

		playagain(1);

	}

	public void reset() {
		Cell cc = new Cell(this);

		clock.cancel();
		counter = 0;
		cc.resetb();
		loc1.clear();
		cellID = 0;
		sum = 0;
		for (int i = 0; i < side; i++) {
			for (int j = 0; j < side; j++) {
				cells[i][j] = new Cell(this);

			}
		}
		time.setText(counter + "sec");

		f.getContentPane().removeAll();

	}

	public void stopcnt() throws IOException {

		System.out.println(sum);
		int record = counter;

		if (sum == 54) { // 54 //63
			for (Cell[] a : cells) {
				for (Cell b : a) {
					b.reveal();
				}
			}
			clock.cancel();

			try {
				img = ImageIO.read(new File("images/game_2/eyeheart.png"));
				change_img = img.getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH);
				emoji.setIcon(new ImageIcon(change_img));

			} catch (IOException e1) {
				e1.printStackTrace();
			}

			icon = new ImageIcon("images/game_2/trophy.png").getImage().getScaledInstance(40, 40, 0);
			iconn = new ImageIcon(icon);

			String yourName = (String) JOptionPane.showInputDialog(null, "기록을 저장하기 위해 이름을 적으시고 확인을 눌러주세요.", "RECORD",
					JOptionPane.PLAIN_MESSAGE, iconn, null, "");

			if (yourName != null) {
				JOptionPane.showMessageDialog(null, "YOUR NAME : " + yourName + "\nYOUR SCORE : " + record, "RECORD",
						JOptionPane.PLAIN_MESSAGE, iconn);

				SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd HH:mm");
				Date date = new Date();
				String now_time = sdf.format(date);
				
				new RankingIO().writeUser(new User(yourName, record, now_time), 2);
				
				// 다시 시작
				playagain(record);

			} else if (yourName == null) {
				icon = new ImageIcon("images/game_2/bell.png").getImage().getScaledInstance(40, 40, 0);
				iconn = new ImageIcon(icon);

				JOptionPane.showMessageDialog(null, "기록을 저장하지 않으셨습니다.", "NOTICE", JOptionPane.PLAIN_MESSAGE, iconn);
				playagain(record);
			}

		}

	}

	public void playagain(int record) {

		icon = new ImageIcon("images/game_2/cap.png").getImage().getScaledInstance(40, 40, 0);
		iconn = new ImageIcon(icon);

		int run = JOptionPane.showConfirmDialog(null, "게임을 다시 시작 하시겠습니까?", "Again?", JOptionPane.YES_NO_OPTION,
				JOptionPane.PLAIN_MESSAGE, iconn);

		if (run == JOptionPane.YES_OPTION) {
			f.dispose();
			reset();
			setBoard();
			
		} else if (run == JOptionPane.NO_OPTION) {
			icon = new ImageIcon("images/game_2/bye.png").getImage().getScaledInstance(40, 40, 0);
			iconn = new ImageIcon(icon);
			
			
			JOptionPane.showMessageDialog(null, "잘가요~", "GOOD BYE", JOptionPane.PLAIN_MESSAGE, iconn);
			check = false;
			BgmPlayer.stopBgm();
			BgmPlayer.playBgm("sounds/main.wav");
			f.dispose();
			m.getF().setVisible(true);

		}
	}
	

	

	
}
