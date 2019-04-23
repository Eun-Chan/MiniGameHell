package project.java.app.game_2.run;

import project.java.app.game_2.vo.Board;
import project.java.app.main.gui.MainMenu;

public class Game_2 {
	
	public Game_2() {}
	
	public Game_2(MainMenu m) {
		Board board = new Board(m);		
	}
}
