package org.weymouth.ants;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

public class TestSensing {
	
	@Test
	public void testUnboundedLocation(){
		List<Food> spots = new ArrayList<Food>();
		spots.add( new Food(100,100) );
		spots.add( new Food(100,200) );
		spots.add( new Food(100,300) );
		
		Location place = new Food(50,200);

		AntSensor sensor = new AntSensor(1000.0);
		
		sensor.setLocation(place);
		List<Food> selected = sensor.look(spots);
		assertEquals(3,selected.size());
	}
		
	@Test
	public void testInsideBoundingBox() {
		
		List<Food> spots = new ArrayList<Food>();
		spots.add( new Food(100,100) );
		spots.add( new Food(100,200) );
		spots.add( new Food(100,300) );
		
		Location location = new Location(150,180);
		
		AntSensor sensor = new AntSensor(80.0);
		sensor.setLocation(location);
	
		List<Food> selected = sensor.look(spots);
		assertEquals(2,selected.size());
			
	}

	@Test
	public void testNoMatch() {
		
		List<Food> spots = new ArrayList<Food>();
		spots.add( new Food(100,100) );
		spots.add( new Food(100,200) );
		spots.add( new Food(100,300) );
		
		Location location = new Location(150,180);
		
		AntSensor sensor = new AntSensor(20.0);
		sensor.setLocation(location);
	
		List<Food> selected = sensor.look(spots);
		assertEquals(0,selected.size());

	}}
