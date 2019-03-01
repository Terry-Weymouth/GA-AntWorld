package org.weymouth.ants.nest.core;

import java.util.Random;

public class Util {

	private static Random randomGenerator = new Random();

	/* 
	 * Compute the shortest distance between line segment P1, P2, to point P3 
	 * using generalized parameterization for the point on the segment
	 * p = P1(1-u) + P2(u) , u on [0.1]
	 * 
	 *  Method copied the java approach from answer 4 of this questions:
	 *  http://stackoverflow.com/questions/849211/shortest-distance-between-a-point-and-a-line-segment
	 */
	public static double shortestDistance(double x1,double y1,double x2,double y2,double x3,double y3)
    {
		double px=x2-x1;
		double py=y2-y1;
		double temp=(px*px)+(py*py);
		double u=((x3 - x1) * px + (y3 - y1) * py) / (temp);
        if(u>1){
            u=1;
        }
        else if(u<0){
            u=0;
        }
        double x = x1 + u * px;
        double y = y1 + u * py;

        double dx = x - x3;
        double dy = y - y3;
        double dist = Math.sqrt(dx*dx + dy*dy);
        return dist;

    }

	static Location [] targets = {new Location(550, 550), new Location(650,650), new Location(750,750), new Location(850,850), new Location(950,950)};
	static int targetIndex = 0;
	
	public static Location cycleTarget() {
		Location ret = targets[targetIndex];
		targetIndex++;
		if (targetIndex >= targets.length) {
			targetIndex = 0;
		}
		return ret;
	}

	public static double distance(Location l1, Location l2) {
		double dx = l2.x - l1.x;
		double dy = l2.y - l1.y;
		return Math.sqrt(dx * dx + dy * dy);
	}

	public static boolean insideBox(Location location, double radius, Location p) {
		double xmin = location.x - radius;
		double xmax = location.x + radius;
		double ymin = location.y - radius;
		double ymax = location.y + radius;
		return (p.x >= xmin) && (p.x <= xmax) && (p.y >= ymin) && (p.y <= ymax);
	}

	public static int randomSelectionOfInt(int max) {
		return randomGenerator.nextInt(max);
	}
	
	public static Location randomLocation() {
		int y = randomGenerator.nextInt(AntWorld.HEIGHT);
		int x = randomGenerator.nextInt(AntWorld.WIDTH);
		return new Location(x,y);
	}
	
	public static Location randomInteriorPoint() {
		return  new Location(
				10 + randomGenerator.nextInt(AntWorld.HEIGHT-20),
				10 + randomGenerator.nextInt(AntWorld.WIDTH-20));
	}
	
	public static double headingTowards(double x, double y) {
		double ret = (Math.atan2(y, x) * 180.0) / Math.PI;
		ret = ret + 180.0;
		if (ret > 360.0) ret = ret - 360.0;
		if (ret < 0) ret = ret + 360.0;
		return ret;
	}

}
