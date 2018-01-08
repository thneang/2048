import java.awt.event.KeyEvent;

/**
 * 
 * @author Neang Thomas and Bruit Gabriel
 *
 */
public class IA extends Player{
	private static final int DEPTH_MAX = 5;
	private Event event;
    private final int[] moves;
    
    /**
   	 *  Create the Object IA which is the alpha beta solver.
   	 */
	public IA(){
		key = -1;
		event = new Event();
		moves = new int[] { KeyEvent.VK_DOWN, KeyEvent.VK_UP,KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT };
	}
	
	/**
	 *  The IA take a decision and set the next direction wich correspond at the choice.
	 * @details The board in param will be copy. Note that the complexity up with the size of the board.
	 * @param board The current board where we have to take a decision to play.
	 */
	public void takeDecision(Board board){
		int depth = DEPTH_MAX;
        Board boardCloned ;
        int bestMove = 0;
        double max = Double.NEGATIVE_INFINITY  , valueOfMove = 0;     
        
        for (int j = 0; j < moves.length; j++) {
                
            try {
                boardCloned = (Board) board.clone();
                event.setDirection(moves[j]);
                event.doEvent(boardCloned);
                if( event.didSomething() ){
                	valueOfMove = searchBestMove(boardCloned,depth);
                	if (max < valueOfMove) {
                		max = valueOfMove;
                		bestMove = moves[j];
                	}
                }

            }catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
        }
        	key = bestMove;

	}
	/**
	 *  Give a mark for a board issue of a direction.
	 * @details The heuristic function is in board
	 * @return double that correspond to the mark.
	 */
	private double searchBestMove(Board node,int depth) throws CloneNotSupportedException{
        Board nodeCloned = node.clone() ;
        double score;
        double max = Double.NEGATIVE_INFINITY;
        /*if we can win return maxScore*/
        if(node.contains2048()){
            return Double.POSITIVE_INFINITY;
        }
        /* If we lose we return the worse score because we don't want to do this movement */
        if( (  node.isFull()  && node.canNotMove() )){
            return Double.NEGATIVE_INFINITY;
        }
        /* End of Tree */
        else if( ( 0 == depth ) ){
            return nodeCloned.heuristique();

        }
       
        for (int i = 0; i < 4 ; i++) {
            nodeCloned = node.clone();
            event.setDirection(moves[i]);
            event.doEvent(nodeCloned);
            if( event.didSomething() ){
                if( max < ( score = searchBestMove(nodeCloned,depth-1)) ){
                    max = score ;
                }
                
            }
        }  
        return max;
        } 
	
	/**
	 *  Return the direction chose by the IA.
	 * @return An int.
	 */
	public int getKey(){
		int tmp = key;
		key = -1;
		return tmp;
	}

	@Override
	public void keyPressed(KeyEvent e) {
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
