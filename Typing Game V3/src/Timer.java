import objectdraw.*;
import java.awt.Color;

public class Timer extends ActiveObject{

	private int timeAliveTenths = 0;
	private int timeAliveOnes = 0;
	private int timeAliveTens = 0;
	private int timeAliveHundreds = 0;
	private Text timeAliveText;
	private FramedRect timerBox;
	public static boolean playing = false;
	private boolean tensMode;
	private boolean hundredsMode;
	private boolean firstTensTrigger = true;
	private boolean firstHundredsTrigger = true;
	
	public Timer(DrawingCanvas canvas) {
		timeAliveText = new Text(timeAliveOnes + "." + timeAliveTenths, canvas.getWidth() - 70, 25, canvas);
		timerBox = new FramedRect(timeAliveText.getX() - 10, timeAliveText.getY() - 10,
				                  timeAliveText.getWidth() + 20, timeAliveText.getHeight() + 20, canvas);
		timeAliveText.setColor(Color.GREEN);
		timerBox.setColor(Color.GREEN);
		start();
	}
	
	public void resetTime() {
		this.timeAliveTenths = 0;
		this.timeAliveOnes = 0;
		this.timeAliveTens = 0;
		this.timeAliveHundreds = 0;
		this.tensMode = false;
		this.hundredsMode = false;
		this.timeAliveText.setText(timeAliveOnes + "." + timeAliveTenths);
		this.timerBox.setWidth(timeAliveText.getWidth() + 20);
		this.firstTensTrigger = true;
		this.firstHundredsTrigger = true;
	}
	
	public void run() {
		while(true) {
			if(playing) {
				if(timeAliveTenths == 10) {
					timeAliveTenths = 0;
					timeAliveOnes++;
				}
				if(timeAliveOnes == 10) {
					timeAliveOnes = 0;
					timeAliveTens++;
					tensMode = true;
					if(firstTensTrigger) {
						timerBox.setWidth(timerBox.getWidth() + 5);
						firstTensTrigger = false;
					}
				}
				if(timeAliveTens == 10) {
					timeAliveTens = 0;
					timeAliveHundreds++;
					hundredsMode = true;
					if(firstHundredsTrigger) {
						timerBox.setWidth(timerBox.getWidth() + 9);
						firstHundredsTrigger = false;
					}
				}
				if(timeAliveHundreds == 10) {
					TypingMain.endGame();
				}
				if(!tensMode && !hundredsMode) {
					timeAliveText.setText(timeAliveOnes + "." + timeAliveTenths);
				} else if(tensMode && !hundredsMode) {
					timeAliveText.setText("" + timeAliveTens + timeAliveOnes + "." + timeAliveTenths);
				} else if(hundredsMode) {
					timeAliveText.setText("" + timeAliveHundreds + timeAliveTens + timeAliveOnes + "." + timeAliveTenths);
				}
				timeAliveTenths++;
			}
		pause(100);
		}
	}
	
}
