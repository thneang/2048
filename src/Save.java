import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * 
 * @author Neang Thomas and Bruit Gabriel
 *
 */
public class Save {
	private File file;
	private FileWriter output;
	
	/**
	 *  Construct the Object Save that will save the game.
	 * @param name The name of the file.
	 */
	public Save(String name) throws IOException{
		file = new File(name);
	}
	
	/**
	 *  Write the two first value in the file.
	 * @param board The board to save.
	 */
	public void writeBoard(Board board) throws IOException{
		
		output = new FileWriter(file);
		int value = board.getFirstValue().getValue();
		int H = board.getFirstValue().getPoint().getX() + 1;
		int V = board.getFirstValue().getPoint().getY() + 1;
		output.write("#Beginning\n");
		output.write(Integer.toString(value));
		output.write(Integer.toString(V));
		output.write(Integer.toString(H));
		output.write("\n");
		
		value = board.getSecondValue().getValue();
		H = board.getSecondValue().getPoint().getX() + 1;
		V = board.getSecondValue().getPoint().getY() + 1;
		output.write(Integer.toString(value));
		output.write(Integer.toString(V));
		output.write(Integer.toString(H));
		output.write("\n");
		output.write("#Action\n");
		output.close();
		
	}
	
	/**
	 *  Save the event.
	 * @param nextValue The next Value that will appear in the board.
	 * @param event The event to save.
	 */
	public void writeEvent(Value nextValue, Event event) throws IOException{
		int direction = event.getDirection();
		String s;
		switch(direction){
			case KeyEvent.VK_UP: s = "N";break;
			case KeyEvent.VK_RIGHT: s = "E";break;
			case KeyEvent.VK_DOWN: s = "S";break;
			case KeyEvent.VK_LEFT: s = "W";break;
			default:return;
		}
		output = new FileWriter(file,true);
		int value = nextValue.getValue();
		int H = nextValue.getPoint().getX() + 1;
		int V = nextValue.getPoint().getY() + 1;
		output.write(Integer.toString(value));
		output.write(Integer.toString(V));
		output.write(Integer.toString(H));
		output.write(s + "\n");
		output.close();
	}
	

}
