import java.awt.event.KeyEvent;
import java.util.LinkedList;

/**
 * 
 * @author Neang Thomas and Bruit Gabriel
 *
 */
public class IA2 extends Player {

	private static final int DEPTH_MAX = 4;
	private Event event;
    private final int[] moves;

    /**
   	 *  Create the Object IA which is the alpha beta solver.
   	 */
	public IA2() {
		key = -1;
		event = new Event();
		moves = new int[] { KeyEvent.VK_DOWN, KeyEvent.VK_UP,KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT };

	}
  
    
	

	/**
   	 *  Give a Mark to a board issue of one direction.
   	 * 	It is a recursive function so the complexity will increase when the size of the boards augment.
   	 * @param node The current board to evaluate.
   	 * @param depth The depth for recurrence.
   	 * @param a The value of alpha.
   	 * @param b The value of beta.
   	 * @param player A boolean to know what is the type of the current node.
   	 * @throws A CloneNotSupportedExceptionwich normally never happen.
   	 * @return A double.
   	 */
	public double alphabeta(Board node , int depth, double a, double b, boolean player ) throws CloneNotSupportedException{
        Board nodeCloned  = node.clone();
        
        /*if we can win return maxScore*/
 	   if(node.contains2048()){
 		   return Double.POSITIVE_INFINITY;
 	   }
 	   /* If we lose we return the worse score because we don't want to do this movement */
 	  if( (  node.isFull()  && node.canNotMove() )){
 		   return -9999999999999999.0 + nodeCloned.heuristique();
    		}
        /* End of Tree */
 	   else if( ( 0 == depth ) ){
            return nodeCloned.heuristique();

 	   }
        if ( player ){
     	   	   
     	  
            for (int i = 0; i < 4 ; i++) {
                    nodeCloned = node.clone();
                    event.setDirection(moves[i]);
                    event.doEvent(nodeCloned);
                    if( event.didSomething() ){
                 	   a = Math.max( a , alphabeta(nodeCloned , depth - 1, a, b, false));
                    }
            }  
            return a;
        }
        else{
             LinkedList<Value> emptyValue = node.getEmptyAvailable();
             int[] possibleValues = {2, 4};
             int x,y;
             for(Value cell : emptyValue) {
                 x = cell.getX();
                 y = cell.getY();
                 for (int value : possibleValues) {
                         nodeCloned = (Board) node.clone();
                         nodeCloned.addValue(new Value(value,x,y));
                         b = Math.min(b, alphabeta( nodeCloned , depth - 1, a, b,  true ));

                     if ( b <= a ){
                         return a ; /* break (" α cut-off ") ; */
                     }
                 }
            }
            
            return b ;

        }

}

	   /**
		 *  The IA take a decision and set the next direction wich correspond at the choice.
		 * @details The board in param will be copy. Note that the complexity up with the size of the board.
		 * @param board The current board where we have to take a decision to play.
		 */
    @Override
    public void takeDecision(Board board) {
        int depth = DEPTH_MAX;
        Board boardCloned ;
        int bestMove = KeyEvent.VK_LEFT ;
        double max = Double.NEGATIVE_INFINITY  , valueOfMove = 0;     
        
        for (int j = 0; j < moves.length; j++) {
                
            try {
                boardCloned = (Board) board.clone();
                event.setDirection(moves[j]);
                event.doEvent(boardCloned);
                if( event.didSomething() ){
                
                if (max < (valueOfMove = alphabeta(boardCloned, depth,Double.NEGATIVE_INFINITY , Double.POSITIVE_INFINITY , false))) {
                        max = valueOfMove;
                        bestMove = moves[j];
                    }
                }

            } catch (CloneNotSupportedException e) {
                System.err.println(e);
            }
            
            

        }

        key = bestMove;


    } 

    /**
	 *  Return the direction chose by the IA.
	 * @return An int.
	 */
	public int getKey() {
/*
		try {
			Thread.sleep(2500);
		} catch (Exception e) {
			// TODO: handle exception
		}*/
		int tmp = key;
		key=-1;

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