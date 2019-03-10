package org.weymouth.ants.nest.core;

public class AntActionStats {
	
	double n = 0.0;
	StatsHolder heading = new StatsHolder();
	StatsHolder speed = new StatsHolder();
	
	public void updateHeading(double hx) {
		heading.update(hx);
	}
	
	public void updateSpeed(double sx) {
		speed.update(sx);
	}

	public double headingMean() {
		return heading.mean();
	}
	
	public double headingSd() {
		return heading.sd();
	}
	
	public double speedMean() {
		return speed.mean();
	}
	
	public double speedSd() {
		return speed.sd();
	}
	
	public double[] values() {
		double[] v = new double[4];
		v[0] = this.headingMean();
		v[1] = this.headingSd();
		v[2] = this.speedMean();
		v[3] = this.speedSd();
		return v;
	}
	
	
	private class StatsHolder {
		// see: derivation on incremental mean and variance at
		// http://datagenetics.com/blog/november22017/index.html
		double n = 0.0;
		double mean = 0.0;
		double var = 0.0;
		
		void update(double x) {
			double oldN = n;
			double oldMean = mean;
			double oldVar = var;
			n += 1.0;
			mean = oldMean + (x - oldMean)/n;
			double varN = (oldVar*oldN) + (x - oldMean)*(x - mean);
			var = varN/n;
		}
		
		double mean() {
			return mean;
		}
		
		double sd() {
			return Math.sqrt(var);
		}
		
	}
}