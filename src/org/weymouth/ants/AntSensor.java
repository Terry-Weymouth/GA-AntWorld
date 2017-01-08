package org.weymouth.ants;

import java.util.List;
import java.util.stream.Collectors;

public class AntSensor {
	
	Location location = new Location(0,0);
	final double radius;
	double[] inputs = {1.0,2.0,3.0,4.0,5.0};

	public AntSensor(double r) {
		radius = r;
	}

	public void setLocation(Location place) {
		location = place;
	}

	public List<Food> look(List<Food> meals) {
		List<Food> selected = meals.stream().filter(p -> Util.insideBox(location,radius,p)).collect(Collectors.toList());
		return selected;
	}


}
