package project.java.app.game_3.vo;

import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class ImageBtn extends JButton{
	// 같은 이미지인지 확인 하는 변수
	private int num;
	// 1 - 버튼 눌렀을 때, 2 - 기본 default
	private ImageIcon img1, img2;
	// 똑같은 버튼이 눌렸는지
	private boolean check;
	// 카드 상태 변수 , true일 시 짝을 찾은 카드
	private boolean state = false;
	
	public ImageBtn(String str, int num, ImageIcon img1, ImageIcon img2) {
		// e.getMassage() 메소드를 이용하기 위해
		super(str,img2);
		this.img1 = img1;
		this.img2 = img2;
		this.num = num;
		check = true;
		setForeground(Color.WHITE);
	}

	// 버튼 클릭시 이미지 변경후 중복클릭 막기 같은 버튼이 또 눌릴시 -1 반환
	public int clickButton() {
		if (check) {
			setIcon(img1);
			check = false;
			return num;
		}
		return -1;
	}

	// 기본 이미지로 변경하는 메소드
	public int returnImage() {
		setIcon(img2);
		check = true;
		return 0;
	}

	// img1을 변경하고 num값을 변경
	public void ChangeImage(ImageIcon image, int n) {
		img1 = image;
		num = n;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public boolean getState() {
		return state;
	}

	public void setState(boolean state) {
		this.state = state;
	}
}
