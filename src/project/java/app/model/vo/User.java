package project.java.app.model.vo;

import java.io.Serializable;

public class User implements Serializable {
	private static final long serialVersionUID = 1L;
	private String nickname;
	private int score;
	private int record; //은찬이형 게임 남은시간
	private String date;
	
	public User() {}
	
	public User(String nickname, int record, String date) {
		this.nickname = nickname;
		this.record = record;
		this.date = date;
		this.score = 0;
	}
	
	public User(String nickname, int record, int score, String date) {
		this(nickname, record, date);
		this.score = score;
	}
	@Override
	public String toString() {
		if(score!=0) return nickname+" "+record+" "+score;
		return nickname+" "+record;
	}

	public String getNickname() {
		return nickname;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getRecord() {
		return record;
	}

	public void setRecord(int record) {
		this.record = record;
	}
}
