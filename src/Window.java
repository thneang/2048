import java.awt.event.KeyListener;

import javax.swing.JFrame;
/**
 * 
 * @author Neang Thomas and Bruit Gabriel
 *
 */
public class Window extends JFrame {

	private static final long serialVersionUID = 1L;

	/**
	 *  Create the window for the game.
	 */
	public Window() {
		this.setTitle("2048");
		this.setSize(950, 800);
		this.setVisible(true);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	/**
	 *  Give hand to the player for the game.
	 * @param l The keylistener used to interact with the game.
	 */
	public void giveHand(KeyListener l) {
		this.addKeyListener(l);
	}

	/**
	 *  Remove hand.
	 * @param l The keylistener used to interact with the game.
	 */
	public void removeHand(KeyListener l) {
		this.removeKeyListener(l);
	}

}
