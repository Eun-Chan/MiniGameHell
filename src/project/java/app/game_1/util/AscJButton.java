package project.java.app.game_1.util;

import java.awt.Component;
import java.util.Comparator;

import javax.swing.JButton;

public class AscJButton implements Comparator<Integer>{

	@Override
	public int compare(Integer o1, Integer o2) {
		return o1-o2;
	}
}
