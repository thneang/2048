import java.awt.event.KeyEvent;
/**
 * 
 * @author Neang Thomas and Bruit Gabriel
 *
 */
public class Event {

	private int direction;
	private boolean move;

	/**
	 *  Create the Object Event that will move the board.
	 */
	public Event() {
		direction = -1;
		move = false;
	}

	/**
	 *  Set the direction.
	 * @param key The movement choose.
	 * @return void
	 */
	public void setDirection(int key) {
		this.direction = key;
	}
	
	/**
	 *  Get the direction.
	 * @return An int correspond to the actual direction.
	 */
	public int getDirection(){
		return direction;
	}

	/**
	 *  Do the move choose.
	 * @param board The board to move.
	 */
	private void move(Board board) {
		switch (direction) {
		case KeyEvent.VK_DOWN:
			move = board.moveDown();
			break;
		case KeyEvent.VK_UP:
			move = board.moveUp();
			break;
		case KeyEvent.VK_LEFT:
			move = board.moveLeft();
			break;
		case KeyEvent.VK_RIGHT:
			move = board.moveRight();
			break;
		default:
			move = false;
			break;
		}
	}

	/**
	 *  Do the event depending of the direction.
	 * @details This method call move(Board board).
	 * @param board The board to modify.
	 */
	public void doEvent(Board board) {
		move(board);
	}
	
	/**
	 *  Return if an event happened.
	 * @return True if something happened else false.
	 */
	public boolean didSomething(){
		return move;
	}

}
