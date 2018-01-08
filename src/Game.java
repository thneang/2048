
import java.io.IOException;

import javax.swing.JOptionPane;
/**
 * 
 * @author Neang Thomas and Bruit Gabriel
 *
 */
public class Game {
	private Event event;
	private Board board;
	private Window window;
	private Player player;
	private boolean optionSave = false;
	private Save save;
	private String nameSave;
	private boolean optionReplay = false;
	private Replay replay;
	private String nameReplay;
	private boolean sameFile = true;
	private boolean optionAlea = false;
	private boolean optionAlea2 = false;
	private Alea alea;

	public Game() {
		String answer;
		event = new Event();
		board = new Board();
		window = new Window();
		window.setContentPane(board);
		 String[] mode = {"Human", "IA(level3)", "IA(level4)"};
		    answer = (String)JOptionPane.showInputDialog(null, 
		      "Choose your mode !",
		      "Mode",
		      JOptionPane.QUESTION_MESSAGE,
		      null,
		      mode,
		      mode[2]);
		    if(answer == null)
		    	player = new Human();
		    else
		    	switch(answer){
		    	case "Human": player = new Human();break;
		    	case "IA(level3)": player = new IA();break;
		    	case "IA(level4)": player = new IA2();break;
		    	default:player = new Human();break;
		    	}

	}

	public Game(String[] args) throws IOException {
		this();
		parse(args);
		if(optionReplay && optionSave)
			sameFile = (nameSave.equals(nameReplay));
	}

	public void start() throws IOException {
		window.giveHand(player);
		if(optionReplay){
			window.removeHand(player);
			replay.loadBoard(board);
		}

		board.initBoard();
		board.repaint();
		if(optionSave && (!optionReplay || !sameFile))
			save.writeBoard(board);

		while (player.hasNotFinish() && isNotFinish()) {
			if(!optionReplay)
				player.takeDecision(board);
			event.setDirection(player.getKey());
			if(optionReplay){
				if(!replay.loadEvent(board, event)){
					window.giveHand(player);
					optionReplay = false;
				}
			}
			event.doEvent(board);
			if(event.didSomething()){
				if(!optionReplay){
					board.generateNextValue();
					if(optionAlea || optionAlea2)
						alea.setNextValue(board);
					board.addNextValue();
				}
				if(optionReplay)
					board.addNextValue();
				if(optionSave && (!optionReplay || !sameFile))
					save.writeEvent(board.getNextValue(), event);
				board.repaint();
			}
				
		}
	}

	public boolean isFinish() {
		return board.isFull() && board.canNotMove();
	}

	public boolean isNotFinish() {

		return !isFinish();
	}

	public void end() {
		if(optionReplay){
			JOptionPane
			.showMessageDialog(
					null,
					"Error you lost during replay !",
					"Error", JOptionPane.ERROR_MESSAGE);
			System.exit(12);
		}

		if (board.contains2048())
			JOptionPane.showMessageDialog(null, "You won !", "Congratulation !",
					JOptionPane.INFORMATION_MESSAGE);
		else
			JOptionPane.showMessageDialog(null, "You lose !", "Fail !",
					JOptionPane.INFORMATION_MESSAGE);
	}

	private void parse(String[] args) throws IOException {
		int i;
		for (i = 0; i < args.length; i++) {
			switch (args[i]) {
			case "-s":
				if ((i + 1) < args.length) {
					optionSave = true;
					nameSave = args[i+1];
					save = new Save(args[i + 1]);i++;
				} else {
					JOptionPane
							.showMessageDialog(
									null,
									"Error argument of -s is missing, the game will not save",
									"Error", JOptionPane.ERROR_MESSAGE);
				}
				break;
			case "-r":
				if ((i + 1) < args.length) {
					optionReplay = true;
					nameReplay = args[i+1];
					replay = new Replay(args[i + 1]);i++;
				} else {
					JOptionPane.showMessageDialog(null,
							"Error argument of -r is missing, start new game",
							"Error", JOptionPane.ERROR_MESSAGE);
				}
				break;

			case "-a":
				if ( ((i + 1) < args.length ) && !optionAlea2) {
					optionAlea = true;
					alea = new Alea(args[i + 1],true);
					i++;
				} else {
					JOptionPane.showMessageDialog(null,
							"Error argument of -a is missing or you already use -n", "Error",
							JOptionPane.ERROR_MESSAGE);
				}
				break;
			case "-n":
				if ( ((i + 1) < args.length ) && !optionAlea) {
					optionAlea2 = true;
					alea = new Alea(args[i + 1],false);
					i++;
				} else {
					JOptionPane.showMessageDialog(null,
							"Error argument of -n is missing or you already use -a", "Error",
							JOptionPane.ERROR_MESSAGE);
				}
				break;
				
			case "-g":
				if ( (i + 1) < args.length ) {
					board.setSeed(Long.parseLong(args[i+1]));
					board.initFirstSecond();
					i++;
				} else {
					JOptionPane.showMessageDialog(null,
							"Error argument of -g is missing", "Error",
							JOptionPane.ERROR_MESSAGE);
				}break;

			default:
				JOptionPane.showMessageDialog(null, args[i]
						+ " is not an option", "", JOptionPane.OK_OPTION);
				break;
			}

		}
	}

	public static void main(String[] args) {
		Game game;
		try {
			game = new Game(args);
			game.start();
			game.end();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}