package edu.uwm.cs351;

import java.util.Comparator;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;

import junit.framework.TestCase;

/**
 * This Solar System is implemented as a doubly linked list.
 * Planets may be added to or removed from a Solar System.
 * They may also be sorted by their distance from their sun in AU, 
 * the length of their day in hours, or their number of moons.
 * 
 * @author scannell
 *
 */
public class SolarSystem {
	
	private static class Node{
		Planet planet;
		Node before, after;
		
		/**
		 *@param p, a planet, must not be null
		 */
		Node(Planet p, Node b, Node n){
			planet = p;
			before = b;
			after = n;
		}
	}
	
	private Node head, tail;
	
	public SolarSystem() {
		head = tail = null;
	}
	
	/**
	 * Add a planet to the end of the list
	 * 
	 * @param p, planet to be added
	 */
	public void add(Planet p) {
		if(head == null) tail = head = new Node(p, null, null);
		else tail = tail.after = new Node(p, tail, tail.after);
	}
	
	/**
	 * Remove a planet from the Solar System
	 * @param p, planet to be removed
	 * @return true if the removal occurred, or false otherwise
	 */
	public boolean remove(Planet p) {
		
		Node node = getNode(p);

		// TODO: did we find it?  If not, return false
		
		// TODO: disconnect from what comes before (head or previous node)
		
		//TODO: disconnect from what comes after (tail or following node)
		
		return true;
	}
	
	private Node getNode(Planet p) {
		for(Node n = head; n != null; n = n.after)
			if(n.planet.equals(p))
				return n;
		
		return null;
	}
	
	/**
	 * Sort using the given comparator
	 * 
	 * @param comparator
	 */
	public void sort(Comparator<Planet> comparator) {
		if(head == null || head.after == null) return;
		bubbleSort(comparator);
	}
	
	/**
	 * Using bubbleSort, sort the elements by the given comparator
	 * 
	 * @precondition assume that there is at least one element 
	 * and therefore head is not null
	 * 
	 * @param comparator describes how elements should be ordered
	 * Note: Comparator usage
	 *     comparator.compare(t1,t2) returns an integer
	 *       if t1 comes before t2,  returns a negative number
	 *       if t1 comes after t2,  returns a positive number
	 *       if t1 and t2 are interchangeable, returns 0
	 */
	private void bubbleSort(Comparator <Planet> comparator) {
		boolean swapped = false;
		Node current = head;
		while(current != null && current.after != null) {
			//TODO
			//compare current and the after element
			//if they are out of order then swap
		}
		if (swapped) bubbleSort(comparator);
	}
	
	/**
	 * Swaps the given current node with the following node
	 * 
	 * @param current, current node to swap with current.after
	 */
	private void swap(Node current) {
		Node after = current.after;
		
		if (after.after != null) after.after.before = current;
		if (current.before != null) current.before.after = after;
		current.after = after.after;
		after.before = current.before;
		after.after = current;
		current.before = after;
		if (current == head) head = after;
		if (after == tail) tail = current;
	}
	
	/**
	 * Sorts the planets by their distance from their sun in AU 
	 * Note: 1 AU (astronomical unit) is the distance from the Earth to the Sun
	 */
	public void sortByDistance() {
		sort(SolarSystem.distanceComparator());
	}
	
	public static Comparator<Planet> distanceComparator() {
		return null; // TODO
	}
	
	/**
	 * Sorts the planets by the length of their day in Earth hours
	 */
	public void sortByDayLength() {
		// TODO
		// Sort using a comparator set to an anonymous class implementing
		// the interface Comparator<Planet>(). You may use the 
		// sortByDistance() as an example, but compare by the 
		// length of the planet's day.
		// You should use the dayLengthComparator() method to
		// create the anonymous comparator.
		
	}
	
	public static Comparator<Planet> dayLengthComparator(){
		return null; // TODO
	}
	
	/**
	 * Sorts the planets by their number of moons
	 */
	public void sortByNumMoons() {
		sort(SolarSystem.numMoonsComparator());
	}
	
	public static Comparator<Planet> numMoonsComparator() {
		return null; // TODO
	}
	
	/**
	 * Creates a canvas to reflect the state of the Solar System
	 * @return
	 */
	public JPanel makeCanvas() {
		return new SolarSystemCanvas();
	}
	
	private class SolarSystemCanvas extends JPanel {
		private static final long serialVersionUID = -2226594746308463675L;
		
		private static final int BOX_WIDTH = 200, BOX_HEIGHT = 130, SECTION_HEIGHT = 20;
		private static final int X_SPACE = 30;
		private static final int Y = 10;
		private static final int ARROWSIZE = 5;
		
		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			Node cursor = head;
			int x = 10;
			while (cursor != null) {
				paintBox(g,x,cursor);
				x+= BOX_WIDTH + X_SPACE;
				cursor = cursor.after;
			}
		}
		
		private void paintBox(Graphics g, int x, Node cursor) {
			paintRectangle(g,x,null);
			paintText(g, x, Y+ SECTION_HEIGHT - 5, cursor.planet.getName() );
			paintText(g, x, Y + SECTION_HEIGHT * 2 - 5, "Distance From Sun (AU): " + cursor.planet.getDistance());
			paintText(g, x, Y + SECTION_HEIGHT * 3 - 5, "Day Length (hr): " + cursor.planet.getDayInHours());
			paintText(g, x, Y + SECTION_HEIGHT * 4 - 5, "Number of Moons: " + cursor.planet.getMoons());
			if (cursor.before != null) paintPrevPointer(g, x);
			if (cursor.after != null) paintNextPointer(g, x);
		}
		
		private void paintRectangle(Graphics g, int x, Color color) {
			if (color != null) {
				Color previous = g.getColor();
				g.setColor(color);
				g.fillRect(x, Y, BOX_WIDTH, BOX_HEIGHT);
				g.setColor(previous);
			}
			g.drawRect(x, Y, BOX_WIDTH, BOX_HEIGHT);
			for (int y2 = SECTION_HEIGHT; y2 < BOX_HEIGHT; y2 += SECTION_HEIGHT) {
				g.drawLine(x, Y + y2, x + BOX_WIDTH, Y + y2);
			}
		}
		
		private void paintText(Graphics g, int x, int y, String text ) {
			FontMetrics fm = getFontMetrics( getFont() );
			int textWidth = fm.stringWidth(text);
			if (textWidth > BOX_WIDTH) {
				int textX = x + (BOX_WIDTH - 10) / 2;
				g.drawString("...", textX, y );
			} else {
				int textX = x + (BOX_WIDTH - textWidth) / 2;
				g.drawString(text, textX, y );
			}
		}
		
		private void paintNextPointer(Graphics g, int x) {
			int startX = x+BOX_WIDTH/2;
			int startY = Y+BOX_HEIGHT-SECTION_HEIGHT/2-SECTION_HEIGHT/3;
			int endX = x+BOX_WIDTH+X_SPACE;
			int [] pointx = { endX, endX - ARROWSIZE, endX -ARROWSIZE};
			int [] pointy = { startY, startY + ARROWSIZE, startY - ARROWSIZE};
			g.drawLine(startX, startY, endX, startY);
			g.fillPolygon(pointx, pointy, pointx.length);
		}
		
		private void paintPrevPointer(Graphics g, int x) {
			int startX = x-X_SPACE;
			int startY = Y+BOX_HEIGHT-SECTION_HEIGHT-SECTION_HEIGHT/2-SECTION_HEIGHT/3;
			int endX = x+BOX_WIDTH/2;
			int [] pointx = { startX, startX + ARROWSIZE, startX + ARROWSIZE};
			int [] pointy = { startY, startY + ARROWSIZE, startY - ARROWSIZE};
			g.drawLine(startX, startY, endX, startY);
			g.fillPolygon(pointx, pointy, pointx.length);
		}
	}

	
	public static class TestSolarSystem extends TestCase{
		protected static Planet mercury = new Planet("Mercury", 0.39, 4222.6, 0);
		protected static Planet venus = new Planet("Venus", 0.723, 2802.0, 0);
		protected static Planet earth = new Planet("Earth", 1, 24, 1);
		protected static Planet mars = new Planet("Mars", 1.524, 24.7, 2);
		protected static Planet jupiter = new Planet("Jupiter", 5.203, 9.9, 79);
		protected static Planet saturn = new Planet("Saturn", 9.539, 10.7, 62);
		protected static Planet uranus = new Planet("Uranus", 19.18, 17.2, 27);
		protected static Planet neptune = new Planet("Neptune", 30.06, 16.1, 14);
		protected static Planet pluto = new Planet("Pluto", 39.53, 153.3, 5);
		
		protected SolarSystem system;
		
		public void setUp() {
			system = new SolarSystem();
			system.add(mercury);
			system.add(venus);
			system.add(earth);
			system.add(mars);
			system.add(jupiter);
			system.add(saturn);
			system.add(uranus);
			system.add(neptune);
		}
	}
	
	public static class TestComparator extends TestSolarSystem {
		
		public void testDistanceComparator() {
			assertTrue(SolarSystem.distanceComparator().compare(earth, jupiter) < 0);
			assertTrue(SolarSystem.distanceComparator().compare(earth, saturn) < 0);
			assertTrue(SolarSystem.distanceComparator().compare(earth, uranus) < 0);
			assertTrue(SolarSystem.distanceComparator().compare(jupiter, jupiter) == 0);
			assertTrue(SolarSystem.distanceComparator().compare(earth, earth) == 0);
			assertTrue(SolarSystem.distanceComparator().compare(mars, mars) == 0);
			assertTrue(SolarSystem.distanceComparator().compare(jupiter, earth) > 0);
			assertTrue(SolarSystem.distanceComparator().compare(saturn, earth) > 0);
			assertTrue(SolarSystem.distanceComparator().compare(uranus, earth) > 0);
		}
		
		public void testDayLengthComparator() {
			assertTrue(SolarSystem.dayLengthComparator().compare(jupiter, jupiter) == 0);
			assertTrue(SolarSystem.dayLengthComparator().compare(saturn, saturn) == 0);
			assertTrue(SolarSystem.dayLengthComparator().compare(mercury, mercury) == 0);
			assertTrue(SolarSystem.dayLengthComparator().compare(jupiter, saturn) < 0);
			assertTrue(SolarSystem.dayLengthComparator().compare(jupiter, mercury) < 0);
			assertTrue(SolarSystem.dayLengthComparator().compare(saturn, mercury) < 0);
			assertTrue(SolarSystem.dayLengthComparator().compare(saturn, jupiter) > 0);
			assertTrue(SolarSystem.dayLengthComparator().compare(mercury, jupiter) > 0);
			assertTrue(SolarSystem.dayLengthComparator().compare(mercury, saturn) > 0);
		}
		
		public void testNumMoonsComparator() {
			assertTrue(SolarSystem.numMoonsComparator().compare(mercury, venus) == 0);
			assertTrue(SolarSystem.numMoonsComparator().compare(venus, venus) == 0);
			assertTrue(SolarSystem.numMoonsComparator().compare(jupiter, jupiter) == 0);
			assertTrue(SolarSystem.numMoonsComparator().compare(mercury, earth) < 0);
			assertTrue(SolarSystem.numMoonsComparator().compare(earth, jupiter) < 0);
			assertTrue(SolarSystem.numMoonsComparator().compare(mars, saturn) < 0);
			assertTrue(SolarSystem.numMoonsComparator().compare(earth, mercury) > 0);
			assertTrue(SolarSystem.numMoonsComparator().compare(jupiter, earth) > 0);
			assertTrue(SolarSystem.numMoonsComparator().compare(saturn, mars) > 0);
		}
	}
	
	public static class TestRemove extends TestSolarSystem {
		
		public void testRemoveSingle() {
			system = new SolarSystem();
			Planet alderaan = new Planet("Alderaan", 1.2, 22.9, 0);
			system.add(alderaan);
			system.remove(alderaan);
			assertTrue(system.head == null);
			assertTrue(system.tail == null);
		}
		
		public void testRemoveBeginning() {
			system = new SolarSystem();
			system.add(pluto);
			system.add(neptune);
			system.remove(pluto);
			assertTrue(system.head == system.getNode(neptune));
			assertTrue(system.tail == system.getNode(neptune));
			assertTrue(system.head.before == null);
			assertTrue(system.tail.after == null);
		}
		
		public void testRemoveMiddle() {
			Node n1 = system.getNode(venus);
			Node n2 = system.getNode(mars);
			system.remove(earth);
			assertTrue(n1.after == n2);
			assertTrue(n2.before == n1);
		}
		
		public void testRemoveEnd() {
			system.add(pluto);
			system.remove(pluto);
			assertTrue(system.tail == system.getNode(neptune));
			assertTrue(system.getNode(neptune).after == null);
		}

		public void testRemoveNonexistent() {
			system = new SolarSystem();
			assertFalse(system.remove(pluto));
			Planet alderaan = new Planet("Alderaan", 1.2, 22.9, 0);
			system.add(alderaan);
			assertFalse(system.remove(pluto));
			assertTrue(system.head != null);
			assertTrue(system.tail != null);
		}
	}
	
	public static class TestSort extends TestSolarSystem {
		
		public void testBubbleSort() {
			system.sortByNumMoons();
			for(Node n = system.head; n.after != null; n = n.after) 
					assertTrue(n.planet.getMoons() <= n.after.planet.getMoons());
			
			system.sortByDistance();
			for(Node n = system.head; n.after != null; n = n.after)
					assertTrue(n.planet.getDistance() < n.after.planet.getDistance());
		}
		
		public void testSortByDayLength() {
			system.sortByDayLength();
			for(Node n = system.head; n.after != null; n = n.after)
					assertTrue(n.planet.getDayInHours() <= n.after.planet.getDayInHours());
		}
		
		public void testBubbleSortEfficiency()
		{
			system = new SolarSystem();
			for(int i=0;i<10000;i++)
			{
				if(i%5==0) system.add(jupiter);
				else if(i%5==1) system.add(venus);
				else if(i%5==2) system.add(earth);
				else if(i%5==3) system.add(mars);
				else if(i%5==4) system.add(pluto);
			}
			system.sortByDistance();
			for(Node n = system.head; n.after != null; n = n.after) {
				// System.out.println("Looking at " + n.planet.getName());
					assertTrue(n.planet.getDistance() <= n.after.planet.getDistance());
			}
		}
		
		private static int MAX = 1 << 20;
		
		public void testBSEfficiency2()
		{
			system = new SolarSystem();
			system.add(new Planet("very far", MAX+1, 24, 1));
			for (int i=0; i < MAX; ++i) {
				system.add(new Planet("Planet " + i, i+1, 24, 1));
			}
			
			int size = 0;
			for (Node n = system.head; n != null; n = n.after) {
				++size;
			}
			assertEquals(MAX+1, size);
			
			system.sortByDistance();
			
			size = 0;
			for (Node n = system.head; n != null; n = n.after) {
				++size;
			}
			assertEquals(MAX+1, size);
			
			int i = 1;
			for (Node p = system.head; p != null; p = p.after) {
				assertEquals(i+0.0, p.planet.getDistance());
				++i;
			}
		}
		
	}


}
