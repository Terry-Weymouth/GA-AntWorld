package org.weymouth.ants.core;

import static org.junit.Assert.assertEquals;

import java.util.Random;

import org.junit.Test;
import org.weymouth.ants.core.Network;

public class TestNetwork {
	
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
		int l = 0;
		for (int li = 0; li < net_layers.length - 1; li++) {
			l += (net_layers[li] + 1) * net_layers[li+1];
		}
		double[] unwrappedWeights = new double[l];
		double d = (double) l;
		for (int i = 0; i < unwrappedWeights.length; i++){
			unwrappedWeights[i] = (1/d) * (double) (i+1);
		}
		net.wrapWeights(unwrappedWeights);
		double[] internalWeights = net.unwrapWeights();
		assertEquals(unwrappedWeights.length, internalWeights.length);
		for (int i = 0; i < internalWeights.length; i++) {
			assertEquals("(Zero) Unwraped weights failed for index: " + i,unwrappedWeights[i],internalWeights[i],ERROR_BAR);
		}
	}

	@Test
	public void zeroWeights(){
		Network net = new Network(zero,net_layers);
		int l = 0;
		for (int li = 0; li < net_layers.length - 1; li++) {
			l += (net_layers[li] + 1) * net_layers[li+1];
		}
		double[] internalWeights = net.unwrapWeights();
		assertEquals(l, internalWeights.length);
		for (int i = 0; i < internalWeights.length; i++) {
			assertEquals("(Zero) Unwraped weights failed for index: " + i,-Network.WEIGHT_BAND,internalWeights[i],ERROR_BAR);
		}
		double[] inputs = {1.0,1.0,1.0};
		double[] expectedOutputs = {0.0,0.0};
		net.setInputs(inputs);
		net.propogate();
		double[] outputs = net.output();
		double[] probe = net.input();
		assertEquals(inputs.length, probe.length);
		for (int i = 0; i < inputs.length; i++) {
			assertEquals("(Zero) Input weights failed for index: " + i,inputs[i],probe[i],ERROR_BAR);			
		}
		assertEquals(expectedOutputs.length, outputs.length);
		for (int i = 0; i < outputs.length; i++) {
			assertEquals("(Zero) Output values failed for index: " + i,expectedOutputs[i],outputs[i],ERROR_BAR);
		}
		
	}
	
	@Test
	public void oneWeights(){
		Network net = new Network(one,net_layers);
		int l = 0;
		for (int li = 0; li < net_layers.length - 1; li++) {
			l += (net_layers[li] + 1) * net_layers[li+1];
		}
		double[] internalWeights = net.unwrapWeights();
		assertEquals(l, internalWeights.length);
		for (int i = 0; i < internalWeights.length; i++) {
			assertEquals("(One) Unwraped weights failed for index: " + i,Network.WEIGHT_BAND,internalWeights[i],ERROR_BAR);
		}
		double[] inputs = {1.0,1.0,1.0};
		double[] expectedOutputs = {1.0,1.0};
		net.setInputs(inputs);
		net.propogate();
		double[] outputs = net.output();
		double[] probe = net.input();
		assertEquals(inputs.length, probe.length);
		for (int i = 0; i < inputs.length; i++) {
			assertEquals("(One) Input weights failed for index: " + i,inputs[i],probe[i],ERROR_BAR);			
		}
		assertEquals(expectedOutputs.length, outputs.length);
		for (int i = 0; i < outputs.length; i++) {
			assertEquals("(One) Output values failed for index: " + i,expectedOutputs[i],outputs[i],ERROR_BAR);
		}
	}

}
