package project.java.app.game_3.controller;

import project.java.app.game_3.gui.Game_3;

public class Timer extends Thread{
	private Game_3 gui;
	
	public Timer(Game_3 gui) {
		this.gui = gui;
	}
	
	@Override
	public void run() {
		
		while (true) {
			try {
				sleep(1000);
				if(gui.time_flag == 1)
					gui.time--;
				gui.time_view.setText(String.valueOf(gui.time));
				
				if(gui.time == 0 || gui.ending == 1 || gui.menu_go == 1) {
					sleep(1000);
					interrupt();
					if(gui.menu_go != 1)
						gui.Game_End();
					break;
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
