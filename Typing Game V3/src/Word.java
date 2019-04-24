import objectdraw.*;
import java.awt.*;

public class Word extends ActiveObject {

	public static final int CHAR_SPACING = 2;
	
	private String wordText;
	private double speed;
	public Text word[];
	private double charWidthSum = 0;
	public boolean wordNotTyped = true;
	private boolean endedGame;
	
	public Word(String wordText, int x, int y, double speed, DrawingCanvas canvas) {
		this.wordText = wordText;
		this.speed = speed;
		
		word = new Text[wordText.length()];
		for (int i = 0; i < word.length; i++) {
			word[i] = new Text(wordText.charAt(i), x + charWidthSum + i*CHAR_SPACING, y, canvas);
			charWidthSum += word[i].getWidth();
			word[i].setColor(Color.MAGENTA);
		}
		
		start();
	}
	
	public void run() {
		while(wordNotTyped) {
			for(int i = 0; i < word.length; i++) {
				word[i].move(-speed, 0);
			}
			if(word[0].getX() <= 0 && !endedGame) {
				TypingMain.endGame();
			}
			//60 frames per second
			pause(1000/60);
		}
	}
	
	public String getWordText() {
		return this.wordText;
	}
	
	public double getSpeed() {
		return this.speed;
	}
	
	public void setSpeed(double speed) {
		this.speed = speed;
	}
	
	
	
}//end of class
