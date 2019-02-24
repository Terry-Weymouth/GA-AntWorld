package org.weymouth.ants.core;

import java.util.Random;

import org.junit.Test;

public class TestNetworkStore {
	
	int[] net_layers = {3,4,4,2};
	Random zero = new Random(){
		private static final long serialVersionUID = 1L;
		@Override
		public double nextDouble() {
			return 0.0;
		}
	};
	
	Random one = new Random(){
		private static final long serialVersionUID = 1L;
		@Override
		public double nextDouble() {
			return 1.0;
		}
	};
	
	Random mid = new Random(){
		private static final long serialVersionUID = 1L;
		@Override
		public double nextDouble() {
			return 0.5;
		}
	};
	
	private static double ERROR_BAR = 0.01;

	@Test
	public void setWeights(){
		Network net = new Network(zero, net_layers);
	}
}
