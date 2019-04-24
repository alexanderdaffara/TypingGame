import java.awt.Color;
import objectdraw.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.*;

public class TypingMain extends WindowController implements KeyListener{
	
	
	//SerialID
	private static final long serialVersionUID = -2541679205690828049L;
	
	private static final int FRAME_WIDTH = 1200;
	private static final int FRAME_HEIGHT = 700;
	
	private int level = 1;
	private int charIndex = 0;
	
	private static Text pressToPlay;
	private static Text gameOverText;
	private Text instructions1;
	private Text instructions2;
	private FilledRect endLine;
	private static FramedRect playBox;
	private static boolean playMode;
	private WordMaker wordMaker;
	private Timer timer;
	
	public static void main(String[] args) {
		new Acme.MainFrame(new TypingMain(),args,FRAME_WIDTH,FRAME_HEIGHT);
	}
	
	public void begin() {
		((Component)canvas).setBackground(Color.BLACK);
		//create play text
		pressToPlay = new Text("Press To Play", 0, 0, canvas);
		pressToPlay.moveTo((canvas.getWidth()/2) - (pressToPlay.getWidth()/2),
				            (canvas.getHeight()/2) - (pressToPlay.getHeight()/2));
		//create and hide game over text
		gameOverText = new Text("GAME OVER", 0, 0, canvas);
		gameOverText.moveTo((canvas.getWidth()/2) - (gameOverText.getWidth()/2),
				            (canvas.getHeight()/2) - 150);
		gameOverText.hide();
		//create top instructions text
		instructions1 = new Text("Type the words as they appear", 0, 0, canvas);
		instructions1.moveTo((canvas.getWidth()/2) - (instructions1.getWidth()/2),
				            (canvas.getHeight()/2) - 150);
		//create bottom instructions text
		instructions2 = new Text("Don't let them hit the edge!!", 0, 0, canvas);
		instructions2.moveTo((canvas.getWidth()/2) - (instructions2.getWidth()/2),
				            instructions1.getY() + instructions1.getHeight() + 5);
		//create pressable play box
		playBox = new FramedRect(pressToPlay.getX() - 50,
				                 pressToPlay.getY() - 50,
				                 pressToPlay.getWidth() + 100,
				                 pressToPlay.getHeight() + 100,
				                 canvas);
		timer = new Timer(canvas);
		//set all colors to green
		pressToPlay.setColor(Color.GREEN);
		gameOverText.setColor(Color.GREEN);
		instructions1.setColor(Color.GREEN);
		instructions2.setColor(Color.GREEN);
		playBox.setColor(Color.GREEN);
		//this detects keyEvents
		canvas.addKeyListener(this);
	}
	
	public void onMouseClick(Location point) {
		if (playBox.contains(point) && playMode == false) {
			pressToPlay.hide();
			playBox.hide();
			gameOverText.hide();
			instructions1.hide();
			instructions2.hide();
			endLine = new FilledRect(0, 0, 5, canvas.getHeight(), canvas);
			endLine.setColor(Color.RED);
			playMode = true;
			play();
		}
	}
	
	public void play() {
		if (wordMaker != null) {
			// pause wordMaker thread;
			wordMaker.untilReset = false;
			// remove all elements of all words from canvas
			for(int i = 0; i < wordMaker.words.size(); i++) {
				wordMaker.words.get(i).wordNotTyped = false;
				for(int j = 0; j < wordMaker.words.get(i).getWordText().length(); j++) {
					wordMaker.words.get(i).word[j].removeFromCanvas();
				}
			}
		}
		timer.resetTime();
		Timer.playing = true;
		wordMaker = new WordMaker(level, canvas);
	}
	
	public static void endGame() {
		TypingMain.playBox.show();
		TypingMain.pressToPlay.show();
		TypingMain.gameOverText.show();
		Timer.playing = false;
		playMode = false;
	}

	@Override
	public void keyTyped(KeyEvent evt) {
		System.out.println(evt.getKeyChar());
		//reference to leftmost word as a char array
		Text[] charr = wordMaker.words.get(wordMaker.words.size() - 1).word;
		//if keyTyped is the next correct letter
		if (charIndex >= charr.length) { charIndex = 0; }
		if (evt.getKeyChar() == charr[charIndex].getText().charAt(0)) {
			(charr[charIndex]).setColor(Color.RED);
			charIndex++;
			if (charIndex == wordMaker.words.get(wordMaker.words.size() - 1).getWordText().length()) {
				System.out.println("word removed");
				wordMaker.words.get(wordMaker.words.size() - 1).wordNotTyped = false;
				wordMaker.words.remove(wordMaker.words.size() - 1);
				for (int i = 0; i < charr.length; i++) {
					charr[i].removeFromCanvas();
				}
				for (int i = 0; i < wordMaker.words.get(wordMaker.words.size()-1).word.length; i++) {
					wordMaker.words.get(wordMaker.words.size()-1).word[i].setColor(Color.GREEN);
				}
				charIndex = 0;
				//wordMaker.SlowDown();
			}
		} else {
			new Error(canvas);
			wordMaker.ErrorHandler();
		}
	}
	
	//unused KeyListener methods
	public void keyPressed(KeyEvent arg0) {}
	public void keyReleased(KeyEvent arg0) {}

	
	
}//end of class
