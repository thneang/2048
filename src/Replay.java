import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;

/**
 * 
 * @author Neang Thomas and Bruit Gabriel
 *
 */
public class Replay {
	private File file;
	private FileReader input;
	
	/**
	 *  Construct the object that will load the game.
	 *  @throws IOException If the file doesn't exist.
	 * @param name The name of the file to load.
	 */
	public Replay(String name) throws IOException{
		file = new File(name);
		if(!file.exists())
			throw new IOException("The file " + name + " doesn't exist for option -r !");
		input = new FileReader(file);
	}
	
	/**
	 *  Load a value in the file and ignore commentary, space and \n.
	 * 	Be careful with the format of file, all the error are not supported.
	 * @throws IOException if the reading fail.
	 * @return an object Value.
	 */
	private Value loadValue()throws IOException{
		int c;
		int value,V,H;
		boolean ignore = true;
		do{
			c = input.read();
			switch(c){
			case '#':
				while( (c = input.read()) != '\n'){
					/* Ignore everything after # */
				}
				/* Go to the nextLine */
				break;
			case ' ':
				while( (c = input.read()) == ' '){
					/* Ignore all space */
				}
				break;
			case '\n':break;
			case -1:return null;/* End of File*/
			default:ignore = false;break;
			}
		}while(ignore);
		value = Character.getNumericValue(c);
		c = input.read();
		V = Character.getNumericValue(c);
		c = input.read();
		H = Character.getNumericValue(c);
		return new Value(value,H-1,V-1);
	}
	
	/**
	 *  Set the two first value of the board with the two next Value in the file.
	 * 	This method must be called first just after the creation of the board 
	 * 	and before starting to read in the file.
	 * @param board The board to modify.
	 * @throws IOException If the reading fail.
	 */
	public void loadBoard(Board board) throws IOException{
		Value first = Objects.requireNonNull(loadValue());
		board.setFirstValue(first);
		Value second = Objects.requireNonNull(loadValue());
		board.setSecondValue(second);
	}
	
	/**
	 *  Set the next value in the board and give the direction to do in event.
	 * Be careful with the format of file, the loading stop directly at the first error.
	 * @param board The board to modify.
	 * @param event The event used to move the board.
	 * @throws IOException If the reading fail.
	 * @return A boolean return true if an event is load false when it finish.
	 */
	public boolean loadEvent(Board board,Event event) throws IOException{
		Value nextValue = loadValue();
		if(nextValue == null)
			return false;
		int c = input.read();
		if(c == -1)
			throw new IOException("Format of file not respected");
	
		switch(c){
			case 'S':event.setDirection(KeyEvent.VK_DOWN);break;
			case 'W':event.setDirection(KeyEvent.VK_LEFT);break;
			case 'N':event.setDirection(KeyEvent.VK_UP);break;
			case 'E':event.setDirection(KeyEvent.VK_RIGHT);break;
			default:throw new IOException("Format of file not respected");
		}
		board.setNextValue(nextValue);
		return true;
	}
}
