package org.weymouth.ants;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

public class TestSensing {
	
	@Test
	public void testClosestLocation(){
		List<Food> spots = new ArrayList<Food>();
		spots.add( new Food(100,100) );
		spots.add( new Food(100,200) );
		spots.add( new Food(100,300) );
		
		Location place = new Food(50,200);

		AntSensor sensor = new AntSensor(1000.0);
		
		sensor.setLocation(place);
		int loc = sensor.look(spots);
		assertEquals(1,loc);
	}
	
	@Test
	public void testInsideBoundingBox() {
		List<Food> spots = new ArrayList<Food>();
		spots.add( new Food(100,100) );
		spots.add( new Food(100,200) );
		spots.add( new Food(100,300) );
		
		Location location = new Location(150,180);
		final double r1 = 20;
		
		List<Location> selected = spots.stream().filter(p -> Util.insideBox(location,r1,p)).collect(Collectors.toList());
		assertEquals(0,selected.size());

		final double r2 = 100;
		
		selected = spots.stream().filter(p -> Util.insideBox(location,r2,p)).collect(Collectors.toList());
		assertEquals(2,selected.size());

		AntSensor sensor = new AntSensor(1000.0);
		sensor.setLocation(location);
		
		int loc = sensor.look(spots);
		assertEquals(1,loc);

	}
	
	@Test
	public void testDistanceWithBoundingBox() {
		
		List<Food> spots = new ArrayList<Food>();
		spots.add( new Food(100,100) );
		spots.add( new Food(100,200) );
		spots.add( new Food(100,300) );
		
		Location location = new Location(150,180);
		
		AntSensor sensor = new AntSensor(80.0);
		sensor.setLocation(location);
	
		int loc = sensor.look(spots);
		assertEquals(1,loc);
		
		sensor = new AntSensor(20.0);
		sensor.setLocation(location);
	
		loc = sensor.look(spots);
		assertEquals(-1,loc);

	}

}
