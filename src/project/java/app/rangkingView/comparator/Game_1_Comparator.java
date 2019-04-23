package project.java.app.rangkingView.comparator;

import java.util.Comparator;

import project.java.app.model.vo.User;

public class Game_1_Comparator implements Comparator<User>{

	@Override
	public int compare(User o1, User o2) {
		return o1.getRecord()-o2.getRecord();
	}
}
