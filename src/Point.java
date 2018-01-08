/**
 * 
 * @author Neang Thomas and Bruit Gabriel
 *
 */

public class Point{
	private final int x;
	private final int y;

	/**
	 *  Construct an object Point.
	 *  @param x The position in x axis
	 *  @param y The position in y axis
	 */
	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 *  Get the position in x axis.
	 * @return An int
	 */
	public int getX() {
		return x;
	}

	/**
	 *  Get the position in y axis.
	 * @return An int
	 */
	public int getY() {
		return y;
	}

	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Point){
			Point point = (Point)obj;
			if( (x == point.x) && (y == point.y) ){
				return true;
			}	
		}
		return false;
	}

	@Override
	public String toString() {
		return "Point [x=" + x + ", y=" + y + "]";
	}
	
	 

}