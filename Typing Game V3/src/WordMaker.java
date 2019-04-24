import objectdraw.*;

import java.awt.Color;
import java.util.*;

public class WordMaker extends ActiveObject {
	
	private int delay;
	private double speed;
	private Random rand;
	private DrawingCanvas canvas;
	
	public boolean untilReset = true;
	private boolean firstWord = true;
	
	public Word newWord;
	public ArrayList<Word> words = new ArrayList<Word>(100);
	
	
	public WordMaker(int level, DrawingCanvas canvas) {
		switch (level) {
			case 1: delay = 1000; speed = 1.0; break;
		}
		this.canvas = canvas;
		rand = new Random();
		start();
	}
	
	public void ErrorHandler() {
		for(int i = 0; i < words.size(); i++) {
			words.get(i).setSpeed(((int)words.get(i).getSpeed()) + 1);
		}
		this.speed += .1;
	}
	
	public void SlowSpeed() {
		for(int i = 0; i < words.size(); i++) {
			words.get(i).setSpeed(((int)words.get(i).getSpeed()) + 1);
		}
		this.speed += .1;
	}
	
	public void run() {
		while(untilReset) {
			int randomIndex = rand.nextInt(WordLibrary.level1Words.length);
			newWord = new Word(WordLibrary.level1Words[randomIndex], 
					           canvas.getWidth(), 
					           rand.nextInt(canvas.getHeight() - 110) + 50,
					           speed,
			                   canvas);
			words.add(0, newWord);
			if(firstWord) {
				for (int i = 0; i < words.get(words.size()-1).word.length; i++) {
					words.get(words.size()-1).word[i].setColor(Color.GREEN);
				}
				firstWord = false;
			}
			pause(delay);
		}
	}
}
