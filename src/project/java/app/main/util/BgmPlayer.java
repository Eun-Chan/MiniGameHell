package project.java.app.main.util;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.UnsupportedAudioFileException;

public class BgmPlayer {

	private static Clip clip;
	
	public static void playBgm(String name) {
		try {
			AudioInputStream ais = 	AudioSystem.getAudioInputStream(new BufferedInputStream(new FileInputStream(name)));
			clip = AudioSystem.getClip();
			clip.open(ais);
			clip.start();
			clip.loop(100);
		} catch(Exception e) {
			
		}
	}
	
	public static void playBgm(String name, int a) {
		try {
			AudioInputStream ais = 	AudioSystem.getAudioInputStream(new BufferedInputStream(new FileInputStream(name)));
			clip = AudioSystem.getClip();
			clip.open(ais);
			clip.start();
		} catch(Exception e) {
			
		}
	}
	
	/*
	 * game_1 노래가 안멈춰서 만든 메소드
	 */
	public static Clip playBgm(String name, boolean bool) {
		try {
			AudioInputStream ais = 	AudioSystem.getAudioInputStream(new BufferedInputStream(new FileInputStream(name)));
			clip = AudioSystem.getClip();
			clip.open(ais);
		} catch (Exception e) {
			
		}
		return clip;
	}
	
	public static void stopBgm() {
		clip.stop();
		clip.close();
	}
}
