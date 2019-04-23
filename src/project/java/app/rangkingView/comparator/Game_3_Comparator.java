package project.java.app.rangkingView.comparator;

import java.util.Comparator;

import project.java.app.model.vo.User;

public class Game_3_Comparator implements Comparator<User>{

	@Override
	public int compare(User o1, User o2) {
		int a = o1.getScore();
		int b = o2.getScore();
		if(a==b) {
			return o2.getRecord()-o1.getRecord();
		}
		return b-a;
	}
}
