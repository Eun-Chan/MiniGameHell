package project.java.app.rangkingView.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import project.java.app.model.io.RankingIO;
import project.java.app.model.vo.User;

public class UserManager {
	private List<User> game_1List = new ArrayList<>();
	private List<User> game_2List = new ArrayList<>();
	private List<User> game_3List = new ArrayList<>();
	
	public void addUser(User u, int game_Num) {
		RankingIO r = new RankingIO();
		
		if(game_Num==1) {
			r.writeUser(u, game_Num);
		} else if(game_Num==2) {
			r.writeUser(u, game_Num);
		} else if(game_Num==3) {
			r.writeUser(u, game_Num);
		}
	}

	public List<User> getGame_1List() {
		return game_1List;
	}

	public void setGame_1List(List<User> game_1List) {
		this.game_1List = game_1List;
	}

	public List<User> getGame_2List() {
		return game_2List;
	}

	public void setGame_2List(List<User> game_2List) {
		this.game_2List = game_2List;
	}

	public List<User> getGame_3List() {
		return game_3List;
	}

	public void setGame_3List(List<User> game_3List) {
		this.game_3List = game_3List;
	}
}
