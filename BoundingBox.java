package cs2110.collision;
import java.util.Iterator;

/** An instance is a 2D bounding box. */
public class BoundingBox {
	/** The corner of the bounding box with the smaller x,y coordinates. */
	public Vector2D lower; // (minX,minY)

	/** The corner of the bounding box with the larger x,y coordinates.	 */
	public Vector2D upper; // (maxX,maxY)

	/** Constructor: an instance is a copy of bounding box b.*/
	public BoundingBox(BoundingBox b) {
		lower = new Vector2D(b.lower);
		upper = new Vector2D(b.upper);
	}

	/** Constructor: An instance with lower as smaller coordinates and
	 * upper as larger coordinates.
	 * @param lower  Corner with smaller coordinates.
	 * @param upper  Corner with larger coordinates.
	 */
	public BoundingBox(Vector2D lower, Vector2D upper) {
		if (upper.x < lower.x)
			throw new IllegalArgumentException("invalid bbox");
		if (upper.y < lower.y)
			throw new IllegalArgumentException("invalid bbox");

		this.lower = lower;
		this.upper = upper;
	}

	/** Return the width of this bounding box (along x-dimension).
	 * @return Width of this bounding box.
	 */
	public double getWidth() {
		return upper.x - lower.x;
	}

	/** Return the height of this bounding box (along y-dimension).
	 * @return Height of this bounding box.
	 */
	public double getHeight() {
		return upper.y - lower.y;
	}

	/** Return the larger of the width and height of this bounding box.
	 * @return Returns the dimension (width or height) of maximum length.
	 */
	public double getLength() {
		// TODO: Implement me. Done
		if (getWidth() >= getHeight()){
			return getWidth(); }
		else {
			return getHeight();
		}
	}

	/** Return the center of this bounding box.
	 * @return The center of this bounding box.
	 */
	public Vector2D getCenter() {
		// TODO: Implement me. Done
		Vector2D center = new Vector2D(upper.x - (upper.x-lower.x)/2 ,
				upper.y - (upper.y - lower.y)/2);
		return center;
	}

	/** Return the result of displacing this bounding box by d.
	 * @param d
	 *            A displacement vector.
	 * @return The result of displacing this bounding box by vector d.
	 */
	public BoundingBox displaced(Vector2D d) {
		// TODO: Implement me. Done
		Vector2D newupper = new Vector2D(upper.y + d.y, upper.x + d.x);
		Vector2D newlower = new Vector2D(lower.y + d.y, lower.x + d.x);
		BoundingBox newbox = new BoundingBox(newlower, newupper);
		return newbox;
	}

	/** Return true iff this bounding box contains p.
	 * @param p A point.
	 * @return True iff this bounding box contains point p.
	 */
	public boolean contains(Vector2D p) {
		boolean inX = lower.x <= p.x && p.x <= upper.x;
		boolean inY = lower.y <= p.y && p.y <= upper.y;
		return inX && inY;
	}

	/** Return the area of this bounding box.
	 * @return The area of this bounding box.
	 */
	public double getArea() {
		// TODO: Implement me. Done
		return getWidth() * getHeight();
	}

	/** Return true iff this bounding box overlaps with box.
	 * @param box  A bounding box.
	 * @return True iff this bounding box overlaps with box.
	 */
	public boolean overlaps(BoundingBox box) {
		// TODO: Implement me. Done
		Vector2D boxupper = box.upper;
		Vector2D boxlower = box.lower;
		
		return !((lower.x >= boxupper.x) || (upper.x <= boxlower.x)
			    || (lower.y >= boxupper.y) || (upper.y <= boxlower.y));
	}

	/** Return the bounding box of blocks given by iter.
	 * @param iter   An iterator of blocks.
	 * @return The bounding box of the blocks given by the iterator.
	 */
	public static BoundingBox findBBox(Iterator<Block> iter) {
		// Do not modify the following "if" statement.
		if (!iter.hasNext())
			throw new IllegalArgumentException("empty iterator");

		// TODO: Implement me.
		double leftmost = Double.POSITIVE_INFINITY;
		double rightmost = Double.NEGATIVE_INFINITY;
		double highest = Double.NEGATIVE_INFINITY;
		double lowest = Double.POSITIVE_INFINITY;
		
		while (iter.hasNext()) {
			Block b = iter.next();
			if (b.position.x < leftmost) {
				leftmost = b.position.x - b.halfwidth;
			}
			if (b.position.x > rightmost) {
				rightmost = b.position.x + b.halfwidth;
			}
			if (b.position.y < lowest) {
				lowest = b.position.y - b.halfwidth;
			}
			if (b.position.y > highest) {
				highest = b.position.y + b.halfwidth;
			}
		}
			Vector2D low = new Vector2D(leftmost, lowest);
			
			Vector2D up = new Vector2D(rightmost, highest);
			
		return new BoundingBox(low, up);
	}

	/** Return a representation of this bounding box. */
	public String toString() {
		return lower + " -- " + upper;
	}
}
