package project.java.app.game_1.util;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class WrongClick {
	private Thread wc = new Thread(new WrongClick_());
	private JButton src;
	
	public WrongClick() {}
	
	public WrongClick(JButton src) {
		this.src = src;
	}
	
	class WrongClick_ implements Runnable {

		@Override
		public void run() {
			try {
				src.setIcon(null);
				Thread.sleep(700);
				Thread.interrupted();
				src.setIcon(new ImageIcon("images/game_1/4.png"));
			} catch (InterruptedException e) {
			}
		}
	}
	
	public Thread getWc() {
		return wc;
	}
	
	public void setWc(Thread wc) {
		this.wc = wc;
	}
	public JButton getSrc() {
		return src;
	}
	public void setSrc(JButton src) {
		this.src = src;
	}
}
