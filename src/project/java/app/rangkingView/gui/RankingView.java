package project.java.app.rangkingView.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumnModel;

import project.java.app.main.gui.MainMenu;
import project.java.app.model.vo.User;
import project.java.app.rangkingView.sort.RankingSort;



public class RankingView {
	private JFrame f;
	private JPanel leftpanel;
	private JPanel centerpanel;
	private JPanel rightpanel;
	private MainMenu m;
	String[] db = {"",
			"database/game_1_user.ser",
			"database/game_2_user.ser",
			"database/game_3_user.ser"};
	
	public RankingView( ) {}
	
	public RankingView(MainMenu m) {
		this.m = m;
		// ------------  panel + label(게임 이름) ==> feat(세준이형)---------------
		f = new JFrame("랭킹 조회 게시판");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		int width = 1000, height = 700;
		int x = (d.width - width) / 2, y = (d.height - height) / 2;
		
		f.setBounds(x, y, width, height);
		f.setLayout(null);
		
		leftpanel = new JPanel();
	    leftpanel.setLayout(null);
	    leftpanel.setBounds(35, 30, 300, 600);
	    leftpanel.setBackground(new Color(17, 33, 90));
	    JLabel leftlabel = new JLabel("1부터16");
	    leftlabel.setForeground(Color.ORANGE);
	    leftlabel.setFont(new Font("DialogInput", Font.BOLD|Font.ITALIC, 20));
//	    leftlabel.setLayout(null); JLabel Layout은 아직 필요하지않을것같아서 주석처리, 밑에도 동일
	    leftlabel.setBounds(110,10,100,100);
	    leftpanel.add(leftlabel);
	      
		centerpanel = new JPanel();
		centerpanel.setLayout(null);
		centerpanel.setBounds(345, 30, 300, 600);
		centerpanel.setBackground(Color.WHITE);
		JLabel centerlabel = new JLabel("지뢰찾기");
		centerlabel.setForeground(Color.ORANGE);
		centerlabel.setFont(new Font("DialogInput", Font.BOLD|Font.ITALIC, 20));
//		centerlabel.setLayout(null);
		centerlabel.setBounds(110, 10, 100, 100);
		centerpanel.add(centerlabel);

		rightpanel = new JPanel();
		rightpanel.setLayout(null);
		rightpanel.setBounds(665, 30, 300, 600);
		rightpanel.setBackground(new Color(231,33,44));
		JLabel rightlabel = new JLabel("같은그림찾기");
		rightlabel.setForeground(Color.ORANGE);
		rightlabel.setFont(new Font("DialogInput", Font.BOLD|Font.ITALIC, 20));
//		rightlabel.setLayout(null);
		rightlabel.setBounds(90, 10, 190, 100);
		rightpanel.add(rightlabel);

		// -----------  Jtable -------------------
		game_1_JTable();
		game_2_JTable();
		game_3_JTable();
		goMainMenuButton();
		
		f.add(leftpanel);
		f.add(centerpanel);
		f.add(rightpanel);
		
		f.setVisible(true);
	}
	
	public void game_1_JTable() {
		// 1번  게임  jtable (왼쪽  게시판)
		// data에 정렬된 랭킹정보를 받아온다
		
		List<User> data = new ArrayList<>();
		data = new RankingSort().sortRanking(1);
		
		// rowData의 크기를 명시적으로 10을  준것은, 랭킹  10위까지만  나타내기 위해서
		String[] columnNames = {"랭킹","닉네임","걸린 시간", "플레이 날"};
		String[][] rowData = new String[data.size()][4];
		
		// rowData에 DB정보 insert
		for(int i = 0 ; i < data.size(); i++) {
			// 상위 랭킹 10명만 나오게 조건
			if(i >= 10) break;
			rowData[i][0] = String.valueOf(i+1);
			rowData[i][1] = data.get(i).getNickname();
			rowData[i][2] = String.valueOf(data.get(i).getRecord());
			rowData[i][3] = data.get(i).getDate();
		}
		
		JTable table = new JTable(rowData,columnNames);
		// 한정된 세로길이를 위해 스크롤을 추가해주는 것인데, 최대 10명만 출력되기 때문에 필수X
		JScrollPane js = new JScrollPane(table);
		js.setBounds(0, 100, 300, 200);
		
		sortCellAtCenter(table);

		leftpanel.add(js);
	}
	
	public void game_2_JTable() {
		
		// 2번 게임 jtable (가운데 게시판)
		List<User> data = new ArrayList<>();
		data = new RankingSort().sortRanking(2);
		
		// rowData의 크기를 명시적으로 10을 준것은, 랭킹 10위까지만 나타내기 위해서
		String[] columnNames = { "랭캉", "닉네임", "점수", "플레이 날"};
		String[][] rowData = new String[data.size()][4];
		
		// rowData에 DB정보 insert
		for(int i = 0 ; i < data.size(); i++) {
			// 상위 랭킹 10명만 나오게 조건
			if(i >= 10) break;
			rowData[i][0] = String.valueOf(i+1);
			rowData[i][1] = data.get(i).getNickname();
			rowData[i][2] = String.valueOf(data.get(i).getRecord());
			rowData[i][3] = data.get(i).getDate();
		}
		
		JTable table = new JTable(rowData, columnNames);
		
		// 한정된 세로길이를 위해 스크롤을 추가해주는 것인데, 최대 10명만 출력되기 때문에 필수X
		JScrollPane js = new JScrollPane(table);
		js.setBounds(0, 100, 300, 200);
		
		sortCellAtCenter(table);
		
		centerpanel.add(js);
	}
	
	public void game_3_JTable() {
		
		// 3번 게임 jtable (오른쪽 게시판)
		List<User> data = new ArrayList<>();
		data = new RankingSort().sortRanking(3);
		
		// rowData의 크기를 명시적으로 10을 준것은, 랭킹 10위까지만 나타내기 위해서
		String[] columnNames = { "랭킹", "이름", "점수","남은 시간", "플레이 날"};
		String[][] rowData = new String[data.size()][5];
		
		// rowData에 DB정보 insert
				for(int i = 0 ; i < data.size(); i++) {
					// 상위 랭킹 10명만 나오게 조건
					if(i >= 10) break;
					rowData[i][0] = String.valueOf(i+1);
					rowData[i][1] = data.get(i).getNickname();
					rowData[i][2] = String.valueOf(data.get(i).getScore());
					rowData[i][3] = String.valueOf(data.get(i).getRecord());
					rowData[i][4] = data.get(i).getDate();
				}
		
		JTable table = new JTable(rowData, columnNames);
		
		// 한정된 세로길이를 위해 스크롤을 추가해주는 것인데, 최대 10명만 출력되기 때문에 필수X
		JScrollPane js = new JScrollPane(table);
		js.setBounds(0, 100, 300, 200);
		
		sortCellAtCenter(table);
		
		rightpanel.add(js);
	}
	
	public void sortCellAtCenter(JTable table) {
		//각 JTable 셀들 가운데정렬을 위한 메소드
		
		DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
		dtcr.setHorizontalAlignment(SwingConstants.CENTER);
		TableColumnModel tcm = table.getColumnModel();
		
		// 각 열 정렬하는 코드
		for (int i = 0; i < table.getColumnCount(); i++)
			tcm.getColumn(i).setCellRenderer(dtcr);	
	}
	
	public void goMainMenuButton() {
		JButton btn = new JButton("돌아가기");
		btn.setFont(new Font("휴먼매직체", Font.BOLD, 20));
		btn.setBounds(50, 520, 200, 60);
		btn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				f.dispose();
				m.getF().setVisible(true);
			}
		});
		centerpanel.add(btn);
	}
}
