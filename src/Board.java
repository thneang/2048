import java.awt.Color;
import java.awt.Graphics;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Random;

import javax.swing.JPanel;

/**
 * 
 * @author Neang Thomas and Bruit Gabriel
 *
 */
class Board extends JPanel implements Cloneable{

	private static final long serialVersionUID = 1L;
	private Value[][] tab;
	private Random random;
	private Value firstValue;
	private Value secondValue;
	private Value nextValue;
	private final int size = 4;
	private int score;
	private int highValue;

	/**
	 *  Create a new empty board.
	 * @details The two first Value are generated but not add to the board.
	 *  The score and the max Value are initialize to 0.
	 */
	public Board() {
		random = new Random();
		this.tab = new Value[size][size];
		int i, j;
		for (i = 0; i < size; i++) {
			for (j = 0; j < size; j++) {
				tab[i][j] = new Value(0, i, j);
				this.add(tab[i][j]);
			}
		}
		score = 0;
		highValue = 0;
		initFirstSecond();
	}
	
	/**
	 *  Set the seed of the Random object in Board.
	 * @return void.
	 */
	public void setSeed(long seed){
		random = new Random(seed);
	}
	
	/**
	 *  Get the first Value of the board.
	 * @return An object Value corresponding to the first value of the board.
	 */
	public Value getFirstValue() {
		return firstValue;
	}
	
	/**
	 *    Set the first Value of the board.
	 *@details This method had to be called before the method initBoard() or it will do nothing.
	 * @return  void.
	 */
	public void setFirstValue(Value firstValue){
		this.firstValue = Objects.requireNonNull(firstValue);
	}

	/**
	 *  Get the second Value of the board.
	 * @return An object Value corresponding to the second Value of the board.
	 */
	public Value getSecondValue() {
		return secondValue;
	}
	
	/**
	 *  Set the Second Value of the board.
	 * @details This method had to be called before initBoard().
	 * @param secondValue  The Value that replace the default Value of the constructor.
	 * @return 
	 */
	
	public void setSecondValue(Value secondValue){
		this.secondValue = Objects.requireNonNull(secondValue);
	}

	/**
	 *  Get the next Value that will be add to the board.
	 * @details The next Value is add when addNextValue() is called.
	 * @return An object Value corresponding to the next value.
	 */
	public Value getNextValue() {
		return nextValue;
	}
	
	/**
	 *  Set the next value
	 * @param nextValue The Value to add when addNextValue is called.
	 * @return void
	 */
	public void setNextValue(Value nextValue){
		this.nextValue = Objects.requireNonNull(nextValue);
	}
	
	public int getScore(){
		return score;
	}
	
	/**
	 *  Get the high value.
	 * @return An int.
	 */
	public int getHighValue(){
		return highValue;
	}
	
	/**
	 *  Add an object Value to the board.
	 * @details This method is here if we want to add a Value manually and directly in the board.
	 * @param value The value that will be add.
	 * @return void
	 */
	public void addValue(Value value){
		Objects.requireNonNull(value);
		int x, y;
		x = value.getX();
		y = value.getY();
		if((x<0) || (x>=size) || (y<0) || (y>=size))
			throw new IllegalStateException("The Value for addValue can't be add, wrong coordonate");
		for (y = 0; y < tab.length; y++) {
			for (x = 0; x < tab.length; x++) {
				if (tab[x][y].getPoint().equals(value.getPoint())) {
					tab[x][y] = value;
				}

			}
		}
	}

	/**
	 *  Generate the first and the second Value of the Tab with the object Random.
	 * @return void
	 */
	public void initFirstSecond(){
		generateNextValue();
		firstValue = nextValue;
		do{
			generateNextValue();
		}while(nextValue.equals(firstValue));
		secondValue = nextValue;
	}
	
	/**
	 *  Add the two first Value in the board.
	 * @details You can choose your value with setFirstValue() and setSecondValue().
	 * @return void
	 */
	public void initBoard(){
		addValue(firstValue);
		addValue(secondValue);
	}

	/**
	 *  Get the length of the board.
	 * @return An int which is the size of the board.
	 */
	public int getLength() {
		return size;
	}

	@Override
	protected Board clone() throws CloneNotSupportedException {
		int x,y;
		Board board = new Board();
		board.firstValue = (Value)this.firstValue.clone();
		board.secondValue = (Value)this.secondValue.clone();
		board.nextValue = (Value)this.nextValue.clone();
		for(x=0;x<size;x++)
			for(y=0;y<size;y++)
				board.tab[x][y] = (Value)this.tab[x][y].clone();
		board.score = this.score;
		board.highValue = this.highValue;
		return board;
	}

	/**
	 *  Return the number of empty square.
	 * @return An int which is the number calculate.
	 */
	public int getSquareEmpty() {
		return getEmptyAvailable().size();
	}
	
	public LinkedList<Value> getEmptyAvailable(){
		LinkedList<Value> list = new LinkedList<Value>();
		int x, y;
		for (x = 0; x < size; x++) {
			for (y = 0; y < size; y++) {
				if (tab[x][y].getValue() == 0)
					list.add(new Value(0,x,y));
			}
		}
		return list;
	}
	
	/**
	 *  Generate the next Value that will be add.
	 * @details There is a chance of 10% for the value 4 and 90% for 2.
	 * @return void
	 */
	public void generateNextValue(){
		int value;
		int rand = random.nextInt(100);
		int count_empty;
		if (rand <= 10) 
			value = 4;
		else
			value = 2;
		count_empty = getSquareEmpty();
		rand = random.nextInt(Integer.MAX_VALUE) % count_empty;
		rand++;
		int x, y;
		for (y = 0; y < tab.length; y++) {
			for (x = 0; x < tab.length; x++) {
				if (tab[x][y].getValue() == 0) {
					rand--;
					if (rand == 0) {
						nextValue = new Value(value,x,y);
					}
				}

			}
		}
	}
	
	/**
	 *  Add the nextValue in the board
	 * @details You can choose your value with the method setNextValue(Value nextValue) before 
	 * youre call this method.
	 * @return void
	 */

	public void addNextValue() {
		int x, y;
		for (y = 0; y < tab.length; y++) {
			for (x = 0; x < tab.length; x++) {
				if (tab[x][y].getPoint().equals(nextValue.getPoint())) {
					tab[x][y] = nextValue;
				}

			}
		}
		
	}

	/**
	 *  return true if the board contains 2048 else return false.
	 * @return A boolean.
	 */
	public boolean contains2048() {
		return highValue >= 2048;
	}

	/**
	 *  Return true if the board is full else return false.
	 * @return a boolean
	 */
	public boolean isFull() {
		int x, y;
		for (x = 0; x < tab.length; x++) {
			for (y = 0; y < tab.length; y++) {
				if (tab[x][y].getValue() == 0)
					return false;
			}
		}
		return true;
	}

	/**
	 *  return true if a movement is possible else return false.
	 * @return A boolean.
	 */
	public boolean canMove() {
		int x, y;

		for (x = 0; x < size; x++) {
			for (y = 0; y < size; y++) {

				if ((x > 0)
						&& (tab[x][y].getValue() == tab[x - 1][y].getValue())) {
					return true;
				}
				if ((x < 3)
						&& (tab[x][y].getValue() == tab[x + 1][y].getValue())) {
					return true;
				}
				if ((y > 0)
						&& (tab[x][y].getValue() == tab[x][y - 1].getValue())) {
					return true;
				}
				if ((y < 3)
						&& (tab[x][y].getValue() == tab[x][y + 1].getValue())) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 *  Return true if we can't move else false.
	 * @return A boolean
	 */
	public boolean canNotMove() {

		return !canMove();
	}

	/**
	 *  Update the score and the actual high value.
	 * @param score The score made by the player
	 * @param highValue The Value made by the player
	 * @return void
	 */
	private void update(int score, int highValue) {
		this.score += score;
		if (highValue > this.highValue) {
			this.highValue = highValue;
		}
	}

	/**
	 *  Do a fusion between the first value in position x1,y1 with the second in position
	 * x2,y2
	 * @details the method return true if we have a fusion else false.
	 * @param x1 The position in x axis of the first value.
	 * @param y1 The position in y axis of the first value.
	 * @param x1 The position in x axis of the second value.
	 * @param y1 The position in y axis of the second value.
	 * @return A boolean
	 */
	private boolean fusion(int x1, int y1, int x2, int y2) {
		int value = tab[x1][y1].getValue();
		int value2 = tab[x2][y2].getValue();
		int res;

		if ((value == value2) && (value != 0)) {
			res = value + value2;
			tab[x1][y1].setValue(0);
			tab[x2][y2].setValue(res);
			update(res, res);
			return true;
		}
		return false;
	}

	/**
	 *  Move a value which is in position x1,y1 to x2,y2 if this place is empty.
	 * @details the method return true if the value move else false.
	 * @param x1 The position in x axis of the first value.
	 * @param y1 The position in y axis of the first value.
	 * @param x1 The position in x axis of the second value.
	 * @param y1 The position in y axis of the second value.
	 * @return A boolean
	 */
	private boolean moveSquare(int x1, int y1, int x2, int y2) {
		int value = tab[x1][y1].getValue();
		int value2 = tab[x2][y2].getValue();

		if ((0 != value) && (value2 == 0)) {
			tab[x1][y1].setValue(0);
			tab[x2][y2].setValue(value);
			return true;
		}
		return false;
	}

	/**
	 *  Move all the value downward.
	 * @details the method return true if it moves else false.
	 * @return A boolean
	 */
	private boolean moveDownLoop() {
		int x, y;
		int i;
		boolean move = false;

		for (x = 0; x < size; x++) {
			for (i = 0; i < size; i++) {
				for (y = size - 1; y > 0; y--) {
					if (moveSquare(x, y - 1, x, y))
						move = true;
				}

			}
		}
		return move;
	}

	/**
	 *  Move all the value downward and do the fusion when necessary.
	 * @details the method return true if it moves,fusion else false.
	 * @return A boolean
	 */
	public boolean moveDown() {
		int x, y;
		boolean move = false;
		if (moveDownLoop()) {
			move = true;
		}
		for (x = 0; x < size; x++) {

			for (y = size - 1; y > 0; y--) {
				if (fusion(x, y - 1, x, y))
					move = true;
			}
		}
		if (moveDownLoop()) {
			move = true;
		}
		
		return move;
	}

	/**
	 *  Move all the value upward.
	 * @details the method return true if it moves else false.
	 * @return A boolean
	 */
	private boolean moveUpLoop() {
		int x, y;
		int i;
		boolean move = false;
		for (x = 0; x < size; x++) {
			for (i = 0; i < size; i++) {
				for (y = 1; y < size; y++) {
					if (moveSquare(x, y, x, y - 1))
						move = true;
				}

			}
		}
		return move;
	}

	/**
	 *  Move all the value upward and do the fusion when necessary.
	 * @details the method return true if it moves,fusion else false.
	 * @return A boolean
	 */
	public boolean moveUp() {
		int x, y;
		boolean move = false;
		if (moveUpLoop()) {
			move = true;
		}
		for (x = 0; x < size; x++) {

			for (y = 1; y < size; y++) {
				if (fusion(x, y, x, y - 1))
					move = true;
			}
		}
		if (moveUpLoop()) {
			move = true;
		}
		
		return move;
	}

	/**
	 *  Move all the value leftward.
	 * @details the method return true if it moves else false.
	 * @return A boolean
	 */
	private boolean moveLeftLoop() {
		int x, y;
		int i;
		boolean move = false;
		for (y = 0; y < size; y++) {

			for (i = 0; i < size; i++) {
				for (x = 1; x < size; x++) {
					if (moveSquare(x, y, x - 1, y))
						move = true;
				}
			}
		}
		return move;
	}

	/**
	 *  Move all the value leftward and do the fusion when necessary.
	 * @details the method return true if it moves,fusion else false.
	 * @return A boolean
	 */
	public boolean moveLeft() {
		int x, y;
		boolean move = false;
		if (moveLeftLoop()) {
			move = true;
		}
		for (y = 0; y < size; y++) {

			for (x = 1; x < size; x++) {
				if (fusion(x, y, x - 1, y))
					move = true;
			}
		}
		if (moveLeftLoop()) {
			move = true;
		}
		
		return move;
	}

	/**
	 *  Move all the value rightward.
	 * @details the method return true if it moves else false.
	 * @return A boolean
	 */
	private boolean moveRightLoop() {
		int x, y;
		int i;
		boolean move = false;
		for (y = 0; y < size; y++) {

			for (i = 0; i < size; i++) {
				for (x = size - 2; x >= 0; x--) {
					if (moveSquare(x, y, x + 1, y))
						move = true;
				}
			}
		}
		return move;
	}

	/**
	 *  Move all the value rightward and do the fusion when necessary.
	 * @details the method return true if it moves,fusion else false.
	 * @return A boolean
	 */
	public boolean moveRight() {
		int x, y;
		boolean move = false;
		if (moveRightLoop()) {
			move = true;
		}
		for (y = 0; y < size; y++) {

			for (x = size - 2; x >= 0; x--) {
				if (fusion(x, y, x + 1, y))
					move = true;
			}
		}
		if (moveRightLoop()) {
			move = true;
		}
		
		return move;
	}

	/**
	 *  Print the board in terminal.
	 * @return void
	 */
	@Override
	public String toString() {
		String result = new String();
		int i, j;
		result = result.concat(Integer.toString(score) +"\n"+Integer.toString(highValue) +"\n");
		for (i = 0; i < tab.length; i++) {
			for (j = 0; j < tab.length; j++) {
				result = result.concat(tab[j][i].toString() + "  ");
			}
			result = result.concat("\n");
		}
		return result;
	}
	
	/**
	 *  Return a note for the board.
	 * @return double.
	 */
	public double markOrdinate(){
		 /* scores for all four directions*/
		double[] totals = new double[4];
		int x,y,next;
		double currentValue,nextValue;
		/* up/down direction*/
		for (x=0; x < size; x++) {
			y = 0;
			next = y+1;
			while ( next<4 ) {
				while ( next<4 && (tab[x][next].getValue() == 0 )) {
					/* We ignore empty cell for order*/
					next++;
				}
				/* Case we have the last cell empty */
				if (next>=4) { 
					next--; 
				}
				currentValue = tab[x][y].getValue();
				if(currentValue != 0)
					currentValue = Math.log(currentValue)/Math.log(2);
				nextValue = tab[x][next].getValue();
				if(nextValue != 0)
					nextValue = Math.log(nextValue)/Math.log(2);
				/* Check the two direction*/
				if (currentValue > nextValue) {
					totals[0] += nextValue - currentValue;
				} 
				else if (nextValue > currentValue) {
					totals[1] += currentValue - nextValue;
				}
				y = next;
				next++;
			}
		}
		// left/right direction
		for (y=0; y<size; y++) {
			x = 0;
			next = x+1;
			while ( next<4 ) {
				while ( next<4 && (tab[next][y].getValue() == 0 )) {
					next++;
				}
				if (next>=4) { 
					next--; 
				}
				currentValue = tab[x][y].getValue();
				if(currentValue != 0)
					currentValue = Math.log(currentValue)/Math.log(2);
				nextValue = tab[next][y].getValue();
				if(nextValue != 0)
					nextValue = Math.log(nextValue)/Math.log(2);
				/* Check the two direction */
				if (currentValue > nextValue) {
					totals[2] += nextValue - currentValue;
				} 
				else if (nextValue > currentValue) {
					totals[3] += currentValue - nextValue;
				}
				x = next;
				next++;
			}
		}
		return Math.max(totals[0], totals[1]) + Math.max(totals[2], totals[3]);
	}
	
	/**
	 *  Return a negative note for the board.
	 * @details More the board is not regular more the value return is low.
	 * @return double.
	 */
	public double markNotRegular(){
		 double notRegular = 0;
		 int x,y;
		 int xNext,yNext;
		 double value,valueNext;
		 /*We start the verification in the top-left corner*/
		 for (x=0; x<size; x++) {
			 for (y=0; y<size; y++) {
				 /* We don't look empty cell */
				 if ( (value = tab[x][y].getValue()) != 0 ) {
					 value = Math.log(value)/Math.log(2);
					 /* If it's not empty we check the regularity with the two neighbor */
					 /*Here we check the right neighbor and ignore if it is empty*/
					 for (xNext = x+1; xNext<size; xNext++) {
						 
						 if ( (valueNext = tab[xNext][y].getValue()) != 0) {
							 valueNext = Math.log(valueNext) / Math.log(2);
							 notRegular -= Math.abs(value - valueNext);
							 break;
						 }
					 }
					 /* Here we check the down neighbor*/
					 for (yNext = y+1; yNext<size; yNext++) {

						 if ( (valueNext = tab[x][yNext].getValue()) != 0) {
							 valueNext = Math.log(valueNext) / Math.log(2);
							 notRegular -= Math.abs(value - valueNext);
							 break;
						 }
					 }
				 }
			 }
		 }
		
		 return notRegular;
	}
	
	public double heuristique() {

		int emptyCells = getSquareEmpty();
		double highValue = Math.log(getHighValue())/Math.log(2);
		double ordinateWeight =1 ,emptyWeight = 2.7, maxWeight = 1,regularityWeight = 0.1;
		return (markOrdinate() * ordinateWeight) 
				+ (emptyCells * emptyWeight) 
				+ (highValue * maxWeight)
				+ (markNotRegular() * regularityWeight);

	}

	/**
	 *  Draw the board.
	 * @details The java.swing api use this method.
	 * you shouldn't use it. You can call repaint().
	 * @param g Graphics use by the java api.
	 * @return void
	 */
	public void paintComponent(Graphics g) {
		int i, j, value;
		int x, y, xs, ys;
		int squareWidth = tab[0][0].getSquareWidth();
		/* draw Value of tab */
		for (i = 0; i < size; i++) {
			for (j = 0; j < size; j++) {
				Value v = tab[i][j];
				g.setColor(v.getColor());
				x = v.getPoint().getX() * squareWidth;
				y = v.getPoint().getY() * squareWidth;
				g.fillRect(x, y, squareWidth, squareWidth);
				xs = x + squareWidth / 2;
				ys = y + squareWidth / 2;
				g.setColor(Color.BLACK);
				value = v.getValue();
				if (value != 0)
					g.drawString(Integer.toString(value), xs, ys);
			}
		}
		/*draw score and hightValue*/
		g.setColor(new Color(204, 204, 204));
		g.fillRect(size * squareWidth, 0, 150, 800);
		g.setColor(Color.BLACK);
		g.drawLine(size * squareWidth, 0, size * squareWidth, 800);
		g.setColor(Color.BLACK);
		g.drawString("Score :" + Integer.toString(score),
				(size * squareWidth) + 10, squareWidth / 2);
		g.drawString("Max :" + Integer.toString(highValue),
				(size * squareWidth) + 10, squareWidth);

	}
	/*
	 * public void paintComponent(Graphics g){ int i,j; for(i=0;i<size;i++){
	 * for(j=0;j<size;j++){ tab[i][j].repaint(); } } }
	 */
}