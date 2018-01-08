import java.awt.event.KeyEvent;

/**
 * 
 * @author Neang Thomas and Bruit Gabriel
 *
 */
public class Human extends Player{
	
	/**
	 *  Create the Object Human which is a normal player.
	 */
	public Human(){
		super();
	}
	
	/**
	 *  This method do nothing for human Player.
	 */
	public void takeDecision(Board board){
		
	}
	
	/**
	 *  return the key choose by the player.
	 * @return An int corresponding to the key pressed.
	 */
	public int getKey(){
		int tmp = key;
		key = -1;
		return tmp;
	}
	
	/**
	 *  Set the key pressed by default we have -1.
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		key = e.getKeyCode();
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

}
