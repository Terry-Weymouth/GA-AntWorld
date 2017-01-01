package org.weymouth.ants;

import java.util.List;
import java.util.stream.Collectors;

public class AntSensor {
	
	Location location = new Location(0,0);
	final double radius;

	public AntSensor(double r) {
		radius = r;
	}

	public void setLocation(Location place) {
		location = place;
	}

	public int look(List<Food> meals) {
		return unboundedLook(filterByBox(meals));
	}

	private int unboundedLook(List<Location> spots) {
		if (spots.isEmpty()) {
			return -1;
		}
		int index = 0;		
		double dist = Util.distance(location, spots.get(index));
		for (int i = 1; i < spots.size(); i ++) {
			double d = Util.distance(location, spots.get(i));
			if (d < dist) {
				dist = d;
				index = i;
			}
		}
		return index;
	}

	private List<Location> filterByBox(List<Food> meals) {
		List<Location> selected = meals.stream().filter(p -> Util.insideBox(location,radius,p)).collect(Collectors.toList());
		return selected;
	}


}
