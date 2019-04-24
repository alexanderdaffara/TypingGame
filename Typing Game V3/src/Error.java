import java.awt.Color;
import objectdraw.*;

public class Error extends ActiveObject {

	FilledRect rect;
	
	public Error (DrawingCanvas canvas) {
		rect = new FilledRect(0,0,canvas.getWidth(),canvas.getHeight(), canvas);
		rect.setColor(Color.GRAY);
		start();
	}
	
	public void run() {
		pause(1);
		rect.removeFromCanvas();
	}
	
}
