package cs2110.collision;

import static org.junit.Assert.*;
import javax.vecmath.Point2d;
import java.util.ArrayList;
import java.util.Iterator;

import org.junit.Test;

public class test {

	@Test
	public void test() {
		Vector2D a = new Vector2D(1,2);
		Vector2D d = new Vector2D(12, 12);
		BoundingBox D = new BoundingBox(a, d);
		Vector2D b = new Vector2D(0,0);
		Vector2D center = new Vector2D(3.0,3.0);
		BoundingBox box = new BoundingBox(b, new Vector2D(9,9));
		Vector2D x  = box.getCenter();
		
		//assertEquals(center.x, x.x, .00001);
		
		//assertEquals(D, box.displaced(a));
		
		//assertEquals(6.0, D.getLength(), .0001);
		//assertEquals(36., D.getArea(), .0001);
		//assertEquals(true, box.overlaps(D));
		
		
		Block one = new Block(1, 1, null, new Point2d(1,1), .5);
		Block two = new Block(2, 2, null, new Point2d(2,2), .5);
		ArrayList<Block> list = new ArrayList<Block>();
		list.add(one);
		list.add(two);
		Iterator<Block> iterlist = list.iterator();
		BoundingBox findtest = BoundingBox.findBBox(iterlist);
		
		assertEquals(findtest.lower.x , .5, .00001); 
		assertEquals(findtest.lower.y , .5, .00001);
		assertEquals(findtest.upper.x , 2.5, .00001);
		assertEquals(findtest.upper.y, 2.5, .00001);
		
		//BlockTree blocktree = new BlockTree(list);
		
		//assertEquals(blocktree.toString(), "one, two");
		
		Block three = new Block(3, 3, null, new Point2d(4,3), .5);
		Block four = new Block(1, 2, null, new Point2d(4,3), .5);
		list.add(three);
		
		
		BlockTree blocktree2 = new BlockTree(list);
		list.remove(one);
		list.remove(two);
		list.add(four);
		
		
		
		//assertEquals(blocktree2.toString(), "one, two");
		
		//assertEquals(blocktree2.contains(a), false);
		
		//assertEquals(blocktree2.overlaps(a, blocktree3, a), true);
		ArrayList<Block> list2 = new ArrayList<Block>();
		Block five = new Block(1, 2, null, new Point2d(1,3), .5);
		Block six = new Block(1, 2, null, new Point2d(2,7), .5);
		Block seven = new Block(1, 2, null, new Point2d(0,2), .5);
		Block eight = new Block(1, 2, null, new Point2d(3,9), .5);
		list2.add(five);
		list2.add(six);
		list2.add(seven);
		list2.add(eight);
		
		BlockTree blocktree3 = new BlockTree(list2);
		
		assertEquals(blocktree2.overlaps(b, blocktree3, b), false);
		
		assertEquals(blocktree3.contains(center), false);
		
		
		assertEquals(blocktree3.contains(new Vector2D(80,3)), false);
	}

}
