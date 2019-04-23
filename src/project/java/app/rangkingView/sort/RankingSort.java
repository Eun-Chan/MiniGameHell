package project.java.app.rangkingView.sort;

import java.util.List;

import project.java.app.model.io.RankingIO;
import project.java.app.model.vo.User;
import project.java.app.rangkingView.comparator.*;

public class RankingSort {
	private RankingIO r = new RankingIO();
	
	// 매개인자가 없어서 리턴하기 위해 추가 + return형  변경  원래는 void였음
	public List<User> sortRanking(int n) {
		if(n == 1) {
			List<User> game_1_User = r.readUser("database/game_1_user.ser", 1);
			game_1_User.sort(new Game_1_Comparator());
			return game_1_User;
		}
		else if(n == 2) {
			List<User> game_2_User = r.readUser("database/game_2_user.ser", 2);
			game_2_User.sort(new Game_2_Comparator());
			return game_2_User;
		}
		else {
			List<User> game_3_User = r.readUser("database/game_3_user.ser", 3);
			game_3_User.sort(new Game_3_Comparator());
			return game_3_User;
		}
	}
	
}
