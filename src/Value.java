import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;

class Value extends Component implements Cloneable {

	private static final long serialVersionUID = 1L;
	private int value;
	private final Point point;
	private final int squareWidth = 200;

	/**
	 *  Create a new Value
	 * @details constructor
	 * @param value The value.
	 * @param x The position in x axis.
	 * @param y The position in y axis.
	 */
	public Value(int value, int x, int y) {
		this.value = value;
		point = new Point(x, y);
		this.setLocation(x, y);
		this.setVisible(true);
	}

	/**
	 *  Get the value.
	 * @return int represents the value.
	 */
	public int getValue() {
		return value;
	}

	/**
	 *  Set the value.
	 * @param value An int that replace the value.
	 */
	public void setValue(int value) {
		this.value = value;
	}

	/**
	 *  Return the Point of this Value.
	 * @return An object Point which contains the position in x axis and y axis.
	 */
	public Point getPoint() {
		return point;
	}

	/**
	 *  Get the with of a square in the board.
	 * @return An int.
	 */
	public int getSquareWidth() {
		return squareWidth;
	}

	@Override
	public String toString() {
		return Integer.toString(value);
	}

	/**
	 *  Get the color of a value.
	 * @return A object Color.
	 */
	public Color getColor() {

		if (0 == value)
			return new Color(204, 204, 204);

		return new Color(255,
				(int) (255 - (Math.log(value) / Math.log(2)) * 23), 0);

	}
	


	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Value){
			Value v = (Value)obj;
			if( (this.value == v.value) && this.point.equals(v.point))
				return true;
		}
		return false;
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		return (Value)super.clone();
	}

	/**
	 *  Draw a Value with the apropriate color.
	 */
	public void paintComponent(Graphics g) {
		int x, y, xs, ys;
		g.setColor(getColor());
		x = this.getX();
		y = this.getY();
		//System.out.println(x);
		g.fillRect(x, y, squareWidth, squareWidth);
		xs = x + squareWidth / 2;
		ys = y + squareWidth / 2;
		g.setColor(Color.WHITE);
		if (value != 0)
			g.drawString(Integer.toString(value), xs, ys);

	}

}