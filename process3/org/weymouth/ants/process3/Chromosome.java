package org.weymouth.ants.process3;

import java.util.Random;

public class Chromosome {
	
	private static Random r = new Random();
	
	private double[] values;
	
	public Chromosome(Chromosome c) {
		setValues(c.getValues());
	}
	
	public Chromosome(double[] v){
		setValues(v);
	}
	
	void setValues(double[] v){
		double[] clone = new double[v.length];
		for (int i=0; i < v.length; i++) {
			clone[i] = v[i];
		}
		values = clone;
	}
	
	double[] getValues() {
		return values;
	}
	
	void mutate(int numberofMutations) {
		for (int n = 0; n < numberofMutations; n++) {
			int index = r.nextInt(values.length);
			values[index] = Math.random();
		}
	}
	
	static Chromosome cross (Chromosome c1, Chromosome c2) {
		double[] v1 = c1.getValues();
		double[] v2 = c2.getValues();
		double[] cross = new double[v1.length];
		int crossPoint = r.nextInt(v1.length - 1);
		for (int index = 0; index < crossPoint; index++) {
			cross[index] = v1[index];
		}
		for (int index = crossPoint; index < v1.length; index++) {
			cross[index] = v2[index];
		}
		return new Chromosome(cross);
	}

}
