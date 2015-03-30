package cs2110.collision;
import java.util.ArrayList;
import java.util.Iterator;

/** An instance is a non-empty collection of points organized in a hierarchical
 * binary tree structure. */
public class BlockTree {
	private BoundingBox box; // bounding box of the blocks contained in this tree.

	private int numBlocks; // Number of blocks contained in this tree.

	private BlockTree left; // left subtree --null if this is a leaf

	private BlockTree right; // right subtree --null iff this is a leaf

	private Block block; //The block of a leaf node (null if not a leaf)

	// REMARK:
	// Leaf node: left, right == null && block != null
	// Intermediate node: left, right != null && block == null

	/** Constructor: a binary tree containing blocks.
	 * Precondition: The tree has no be non-empty,
	 * i.e. it must contain at least one block.	 */
	public BlockTree(ArrayList<Block> blocks) { // Leave the following two "if"
												// statements as they are.
		if (blocks == null)
			throw new IllegalArgumentException("blocks null");
		if (blocks.size() == 0)
			throw new IllegalArgumentException("no blocks");

		// TODO: implement me.
		numBlocks = blocks.size();
		
		Iterator<Block> iter = blocks.iterator();
		box = BoundingBox.findBBox(iter);
		
		Vector2D center = box.getCenter();
		
		if (numBlocks == 1) {
			left = null;
			right = null;
			block = blocks.get(0);
			return;
		}
		ArrayList<Block> leftside = new ArrayList<Block>();
		ArrayList<Block> rightside = new ArrayList<Block>();
		
		for (Block b:blocks) {
			if (box.getWidth() > box.getHeight()) {
				if (b.position.x < center.x ) {
					leftside.add(b);
				}
				else {
					rightside.add(b);
				}
					
			}
			else {
				if (b.position.y < center.y) {
					leftside.add(b);
				}
				else {
					rightside.add(b);
				}
			}
			
			
			
		}
	
		left = new BlockTree(leftside);
		right = new BlockTree(rightside);
		
		
		}
	

	/** Return the bounding box of the collection of blocks.
	 * @return The bounding box of this collection of blocks.
	 */
	public BoundingBox getBox() {
		return box;
	}

	/** Return true iff this is a leaf node.
	 * @return true iff this is a leaf node.
	 */
	public boolean isLeaf() {
		return (block != null);
	}

	/** Return true iff this is an intermediate node.
	 * @return true iff this is an intermediate node.
	 */
	public boolean isIntermediate() {
		return !isLeaf();
	}

	/** Return the number of blocks contained in this tree.
	 * @return Number of blocks contained in this tree.
	 */
	public int getNumBlocks() {
		return numBlocks;
	}

	/** Return true iff this collection of blocks contains  point p.
	 * @return True iff this collection of blocks contains  point p.
	 */
	public boolean contains(Vector2D p) {
		// TODO: Implement me. Done
		
		if (!box.contains(p)) {
			return false;
		}
		
			if (numBlocks == 1){
				return block.contains(p);
			}
				
			if (left.box.contains(p)) {
				return left.contains(p);
			}
			else {
				return right.contains(p);
			}
		
	
	}

	/** Return true iff (this tree displaced by thisD) and (tree t 
	 * displaced by d) overlap.
	 * @param thisD
	 *            Displacement of this tree.
	 * @param t
	 *            A tree of blocks.
	 * @param d
	 *            Displacement of tree t.
	 * @return True iff this tree and tree t overlap (account for
	 *         displacements).
	 */
	public boolean overlaps(Vector2D thisD, BlockTree t, Vector2D d) {
		// TODO: Implement me
		
		BoundingBox thismoved = box.displaced(thisD);
		BoundingBox thatmoved = t.box.displaced(d);
		
		if (!thismoved.overlaps(thatmoved)) {
			return false;
		}
		
			if (numBlocks == 1 && t.numBlocks == 1) {
				return Block.overlaps(block, thisD, t.block, d);
			}
			
				
			if (numBlocks == 1) {
				ArrayList<Block> find = new ArrayList<Block>();
				find.add(block);
				BlockTree found = new BlockTree(find);
					
				return found.overlaps(thisD, t.left, d) || 
					   found.overlaps(thisD, t.right, d);
				}
			if (t.numBlocks == 1) {
				return left.overlaps(thisD, t, d) ||
			    right.overlaps(thisD, t, d);
				}
			else {
				
				return  left.overlaps(thisD, t.left, d) || 
						left.overlaps(thisD, t.right, d) ||
						right.overlaps(thisD, t.left, d) ||
						right.overlaps(thisD, t.right, d);
							
				}
			}
				
		
	
	

	/** Return a representation of this instance. */
	public String toString() {
		return toString(new Vector2D(0, 0));
	}

	/** Return a represenation of d
	 * 
	 * @param d  Displacement vector.
	 * @return String representation of this tree (displaced by d).
	 */
	public String toString(Vector2D d) {
		return toStringAux(d, "");
	}

	/** Useful for creating appropriate indentation for function toString.  */
	private static final String indentation = "   ";

	/** Return a representation of this instance displaced by d, with
	 * indentation indent.
	 * @param d Displacement vector.
	 * @param indent  Indentation.
	 * @return String representation of this tree (displaced by d).
	 */
	private String toStringAux(Vector2D d, String indent) {
		String str = indent + "Box: ";
		str += "(" + (box.lower.x + d.x) + "," + (box.lower.y + d.y) + ")";
		str += " -- ";
		str += "(" + (box.upper.x + d.x) + "," + (box.upper.y + d.y) + ")";
		str += "\n";

		if (isLeaf()) {
			String vStr = "(" + (block.position.x + d.x) + "," + (block.position.y + d.y)
					+ ")" + block.halfwidth;
			str += indent + "Leaf: " + vStr + "\n";
		} else {
			String newIndent = indent + indentation;
			str += left.toStringAux(d, newIndent);
			str += right.toStringAux(d, newIndent);
		}

		return str;
	}

}
