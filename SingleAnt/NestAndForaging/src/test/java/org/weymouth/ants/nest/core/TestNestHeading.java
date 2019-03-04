package org.weymouth.ants.nest.core;

import static org.junit.Assert.assertEquals;

import java.util.Random;

import org.junit.Test;

public class TestNestHeading {
	
	private static double ERROR_BAR = 0.001;
	
	private static Location[] testAntLocationArray = {
			new Location(400,100), new Location(400,700)
	};
	
	private static double[] testAntHeadingArray = {0};
	
	private static double[][] expectedHeading = {{0.25},{0.75}};

	
	int[] netLayers = {2,2,2,2};
	Random zero = new Random(){
		private static final long serialVersionUID = 1L;
		@Override
		public double nextDouble() {
			return 0.0;
		}
	};

	AntBrain brain = new AntBrain(new Network(zero, netLayers));
	
	// Note: heading values will be in the range 0,1
	//   with 0 meaning 180 degrees to the left,
	//   and 1 meaning 180 degrees to the right,
	
	@Test
	public void testNesttHeadingValue() {
		for (int i = 0; i < testAntLocationArray.length; i++) {
			Location testLocation = testAntLocationArray[i];
			Ant ant = new Ant(brain, testLocation);
			for (int j = 0; j < testAntHeadingArray.length; j++) {
				ant.heading = testAntHeadingArray[j];
				AntSensor sensor = new AntSensor(ant);
				double distance = sensor.nestDistance();
				double heading = sensor.nestHeading(distance);
				assertEquals(expectedHeading[i][j], heading, ERROR_BAR);
				
			}
		}
	}

}
