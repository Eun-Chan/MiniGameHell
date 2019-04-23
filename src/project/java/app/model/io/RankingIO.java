package project.java.app.model.io;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

import project.java.app.model.vo.User;
import project.java.app.rangkingView.controller.UserManager;

public class RankingIO {
	private String fileName;
	private UserManager m = new UserManager();
	private User tmp;
	public void writeUser(User u, int game_Num){
		tmp = u;
		switch(game_Num) {
		case 1 : fileName="database/game_1_user.ser"; break;
		case 2 : fileName="database/game_2_user.ser"; break;
		case 3 : fileName="database/game_3_user.ser"; break;
		}
		
		// 헤더를 추가해 주기 위해서,   header_Insert 메소드 자체가 이상할 순 있지만, 맨 처음 DB가 만들어지고 나서, 맨 앞에 헤더가 있어야 하기 때문 
		if(new File("database/game_1_user.ser").length()==0 && game_Num == 1) {
			header_Insert();
			System.out.println("header1");
		}
		if(new File("database/game_2_user.ser").length()==0 && game_Num == 2){
			header_Insert();
			System.out.println("header2");
		}
		if(new File("database/game_3_user.ser").length()==0 && game_Num == 3){
			header_Insert();
			System.out.println("header3");
		}
		
		ObjectOutputStream oos = null;
		try(ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(fileName)))) {
			oos = new ObjectOutputStream(new BufferedOutputStream((new FileOutputStream(fileName))));
			
			int cnt = 0 ;
			
			while(true) {
				User user = (User)ois.readObject();
				System.out.print(user);
				System.out.println(" "+(++cnt));
				oos.writeObject(user);
			}
			
		} catch (EOFException e) { 
			try {
				System.out.println("EOF");
				oos.writeObject(u);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			System.out.println("oos out");
			if(oos!=null) try { System.out.println("oos in");oos.close();} catch (IOException e) {e.printStackTrace();} 
		}
		
	}
	
	public List<User> readUser(String fileName, int game_Num) {
		try(ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(fileName)))) {
			while(true) {
				User user = (User)ois.readObject();
				if(game_Num==1) m.getGame_1List().add(user);
				if(game_Num==2) m.getGame_2List().add(user);
				if(game_Num==3) m.getGame_3List().add(user);
			}
		} catch (EOFException e) { 
			if(game_Num==1) return m.getGame_1List();
			if(game_Num==2) return m.getGame_2List();
			if(game_Num==3) return m.getGame_3List();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public void header_Insert() {
		try(ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(fileName)));
				ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(fileName))))
			 {
			
			// DB에  아무것도  없을시 파일 입력을 위해 헤더를 추가해야 함
			oos.writeObject(tmp);

		} catch(Exception e) {
			
		}
	}
}