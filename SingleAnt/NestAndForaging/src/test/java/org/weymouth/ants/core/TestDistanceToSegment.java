package org.weymouth.ants.core;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.weymouth.ants.core.Location;
import org.weymouth.ants.core.Util;

public class TestDistanceToSegment {
	
	private static double ERROR_BAR = 0.001;

	@Test
	public void distance_to_horizontal_line1(){
		Location a = new Location(0,0);
		Location b = new Location(10,0);
		Location p = new Location(5,1);
		double d = Util.shortestDistance(a.x, a.y, b.x, b.y, p.x, p.y);
		assertEquals(1.0, d, ERROR_BAR);
	}

	@Test
	public void distance_to_horizontal_line2(){
		Location a = new Location(0,0);
		Location b = new Location(10,0);
		Location p = new Location(12,0);
		double d = Util.shortestDistance(a.x, a.y, b.x, b.y, p.x, p.y);
		assertEquals(2.0, d, ERROR_BAR);
	}

	@Test
	public void distance_to_horizontal_line3(){
		Location a = new Location(0,0);
		Location b = new Location(10,0);
		Location p = new Location(0,0);
		double d = Util.shortestDistance(a.x, a.y, b.x, b.y, p.x, p.y);
		assertEquals(0.0, d, ERROR_BAR);
	}

	@Test
	public void distance_to_vertical_line1(){
		Location a = new Location(0,0);
		Location b = new Location(0,10);
		Location p = new Location(0,5);
		double d = Util.shortestDistance(a.x, a.y, b.x, b.y, p.x, p.y);
		assertEquals(0.0, d, ERROR_BAR);
	}

	@Test
	public void distance_to_vertical_line2(){
		Location a = new Location(0,1);
		Location b = new Location(0,10);
		Location p = new Location(1,5);
		double d = Util.shortestDistance(a.x, a.y, b.x, b.y, p.x, p.y);
		assertEquals(1.0, d, ERROR_BAR);
	}

	@Test
	public void distance_to_vertical_line3(){
		Location a = new Location(0,1);
		Location b = new Location(0,10);
		Location p = new Location(1.414,11.414);
		double d = Util.shortestDistance(a.x, a.y, b.x, b.y, p.x, p.y);
		assertEquals(2.0, d, ERROR_BAR);
	}

	@Test
	public void distance_to_diaginal_line1(){
		Location a = new Location(1,1);
		Location b = new Location(5,5);
		Location p = new Location(4,4);
		double d = Util.shortestDistance(a.x, a.y, b.x, b.y, p.x, p.y);
		assertEquals(0.0, d, ERROR_BAR);
	}

	@Test
	public void distance_to_diaginal_line2(){
		Location a = new Location(1,1);
		Location b = new Location(5,5);
		Location p = new Location(10,10);
		double d = Util.shortestDistance(a.x, a.y, b.x, b.y, p.x, p.y);
		double expected = 5.0 * Math.sqrt(2.0);
		assertEquals(expected, d, ERROR_BAR);
	}

}
