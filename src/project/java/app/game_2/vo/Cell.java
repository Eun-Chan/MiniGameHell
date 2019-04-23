package project.java.app.game_2.vo;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

public class Cell implements ActionListener{
	//controling behavior of single cell
	
	private JButton button; // 버튼
    private Board board; // 보드
    private int value; 
    private int id; //board 에서 사용됨 
    boolean notChecked;
    private  Dimension size;
    private JLabel mylabel;

    public Cell(Board board) {
    	 button = new JButton();
    	 mylabel = new JLabel();

         button.addActionListener(this);
         this.board = board;
    	 
         setNotChecked(true); //check x
    }
    
    public JButton getButton() {
        return button;
    }

    public int getValue() {
        return value;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void displayValue() throws IOException{ //클릭후 보여지는 숫자 또는 지뢰
        if(value==-1){
//          이미지 추가
            Image img = ImageIO.read(new File("images/game_2/flag.png"));
            Image change_img = img.getScaledInstance(60, 60,
					java.awt.Image.SCALE_SMOOTH);

            mylabel.setIcon(new ImageIcon(change_img));
            button.setLayout(new BorderLayout());          
            button.add(mylabel, BorderLayout.CENTER);
            
            
            button.setBackground(Color.RED);
            
            button.setBorderPainted(false);
            button.setFocusPainted(false);
            button.setContentAreaFilled(false);
            
            mylabel.setVisible(true);
        }else if(value!=0){
        	
            button.setText(String.valueOf(value));
            button.setFont(new Font("Dialog", Font.PLAIN, 18));
            

            button.setBorderPainted(false);
            button.setFocusPainted(false);
            button.setContentAreaFilled(false);
            
            board.setSum(board.getSum()+1);   
        }
    }

    public void reveal() throws IOException{
    	displayValue();
    	button.setEnabled(false); //폭탄 선택시 클릭 x
    	
    }
    
    public void checkCell() throws IOException{ // 클릭후 0이면 공백 표시, -1이면 지뢰
    	
        button.setEnabled(false); 
        displayValue();
        setNotChecked(false);
        
        if(value == 0) {
        	board.scanForEmptyCells();
        	board.setSum(board.getSum()+1);
        }
        if(value == -1) {
        	board.fail();
       
        }
      
    }

    public void incrementValue(){ //value 값증가    	
        value++;
    }

    public boolean isNotChecked(){ //check value
        return notChecked;
    }

    public boolean isEmpty(){ //어떤게 공백인가    	
        return isNotChecked() && value==0;
    }


	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(board.isCheck()==true) { //시작 버튼 눌렀을때만 게임 실행!
			try {
				checkCell();
				cntstop();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		      		
	}

	public void setNotChecked(boolean notChecked) {
		this.notChecked = notChecked;
	}
	

	
	public void resetb() {

		setValue(0);
//	    button.setText("");
//	  	button.getRootPane().removeAll();
	  	button = new JButton();
	  	button.setEnabled(true); 
//	    isNotChecked();
	    setNotChecked(true);
	    
	}



	public void cntstop() throws IOException {
		board.stopcnt();
		  
	}
	

}
