import java.awt.event.KeyListener;
/**
 * @author Neang Thomas and Bruit Gabriel 
 */


public abstract class Player implements KeyListener {
	
	protected int key;

	abstract void takeDecision(Board board);
	
	abstract int getKey();

	public boolean hasNotFinish(){
		return true;
	}

}
