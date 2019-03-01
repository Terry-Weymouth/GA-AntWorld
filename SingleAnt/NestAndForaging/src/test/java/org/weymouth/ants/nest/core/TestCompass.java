package org.weymouth.ants.nest.core;

import org.junit.Test;
import org.weymouth.ants.nest.core.Compass;

import static org.junit.Assert.assertEquals;

public class TestCompass {
	
	private static double ERROR_BAR = 0.001;
	
	@Test
	public void headingForSouth() {
		double expectedHeading = 90.0;
		double dx = 0.0;
		double dy = 100.0;
		double heading = Compass.headingForDelta(dx,dy);
		assertEquals(expectedHeading, heading, ERROR_BAR);
	}
	
	@Test
	public void headingForNorth() {
		double expectedHeading = -90.0;
		double dx = 0.0;
		double dy = -100.0;
		double heading = Compass.headingForDelta(dx,dy);
		assertEquals(expectedHeading, heading, ERROR_BAR);
	}

	@Test
	public void headingForEast() {
		double expectedHeading = 0.0;
		double dx = 100.0;
		double dy = 0.0;
		double heading = Compass.headingForDelta(dx,dy);
		assertEquals(expectedHeading, heading, ERROR_BAR);
	}

	@Test
	public void headingForWest() {
		double expectedHeading = 180.0;
		double dx = -100.0;
		double dy = 0.0;
		double heading = Compass.headingForDelta(dx,dy);
		assertEquals(expectedHeading, heading, ERROR_BAR);
	}
	
	@Test
	public void vectorXForSouth() {
		double expectedX = 0.0;
		double expectedY = 100.0;
		double angle = Compass.headingForDelta(expectedX,expectedY);
		double r = 100.0;
		double x = Compass.dxForThetaR(angle,r);
		assertEquals(expectedX, x, ERROR_BAR);
	}

	@Test
	public void vectorYForSouth() {
		double expectedX = 0.0;
		double expectedY = 100.0;
		double angle = Compass.headingForDelta(expectedX,expectedY);
		double r = 100.0;
		double y = Compass.dyForThetaR(angle,r);
		assertEquals(expectedY, y, ERROR_BAR);
	}

	@Test
	public void vectorXForNorth() {
		double expectedX = 0.0;
		double expectedY = -100.0;
		double angle = Compass.headingForDelta(expectedX,expectedY);
		double r = 100.0;
		double x = Compass.dxForThetaR(angle,r);
		assertEquals(expectedX, x, ERROR_BAR);
	}

	@Test
	public void vectorYForNorth() {
		double expectedX = 0.0;
		double expectedY =-100.0;
		double angle = Compass.headingForDelta(expectedX,expectedY);
		double r = 100.0;
		double y = Compass.dyForThetaR(angle,r);
		assertEquals(expectedY, y, ERROR_BAR);
	}

	@Test
	public void vectorXForEast() {
		double expectedX = 100.0;
		double expectedY = 0.0;
		double angle = Compass.headingForDelta(expectedX,expectedY);
		double r = 100.0;
		double x = Compass.dxForThetaR(angle,r);
		assertEquals(expectedX, x, ERROR_BAR);
	}

	@Test
	public void vectorYForEast() {
		double expectedX = 100.0;
		double expectedY = 0.0;
		double angle = Compass.headingForDelta(expectedX,expectedY);
		double r = 100.0;
		double y = Compass.dyForThetaR(angle,r);
		assertEquals(expectedY, y, ERROR_BAR);
	}

	@Test
	public void vectorXForWest() {
		double expectedX = -100.0;
		double expectedY = 0.0;
		double angle = Compass.headingForDelta(expectedX,expectedY);
		double r = 100.0;
		double x = Compass.dxForThetaR(angle,r);
		assertEquals(expectedX, x, ERROR_BAR);
	}

	@Test
	public void vectorYForWest() {
		double expectedX = -100.0;
		double expectedY = 0.0;
		double angle = Compass.headingForDelta(expectedX,expectedY);
		double r = 100.0;
		double y = Compass.dyForThetaR(angle,r);
		assertEquals(expectedY, y, ERROR_BAR);
	}
	
	@Test
	public void wrappingAngle1() {
		double expectedAngle = 45.0;
		double angle = expectedAngle + 360.0;
		angle = Compass.rewrap(angle);
		assertEquals(expectedAngle,angle, ERROR_BAR);
	}

	@Test
	public void wrappingAngle2() {
		double expectedAngle = 45.0;
		double angle = expectedAngle - 360.0;
		angle = Compass.rewrap(angle);
		assertEquals(expectedAngle,angle, ERROR_BAR);
	}

	@Test
	public void wrappingAngle3() {
		double expectedAngle = -45.0;
		double angle = expectedAngle + 360.0;
		angle = Compass.rewrap(angle);
		assertEquals(expectedAngle,angle, ERROR_BAR);
	}

	@Test
	public void wrappingAngle4() {
		double expectedAngle = -45.0;
		double angle = expectedAngle - 360.0;
		angle = Compass.rewrap(angle);
		assertEquals(expectedAngle,angle, ERROR_BAR);
	}
}
