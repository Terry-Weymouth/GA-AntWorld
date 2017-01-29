package org.weymouth.ants;

import java.util.List;
import java.util.Random;

public class Network {

	private Random rng;
	private int[] layerWidths;
	private Layer[] layer;
	private Weight[] weight;
	
	public Network(Random rng,int[] layerWidths){
		this.rng = rng;
		this.layerWidths = layerWidths;
		layer = new Layer[layerWidths.length];
		weight = new Weight[layerWidths.length - 1];
		
		for (int i = 0; i < layerWidths.length; i++) {
			layer[i] = new Layer(layerWidths[i]);
		}
		
		for (int layer = 0; layer < (layerWidths.length - 1); layer++) {
			weight[layer] = new Weight(layerWidths[layer]+1,layerWidths[layer+1]);
		}
		
		setNetToRandom();
		
	}
	
	public List<Double> getParameters() {
		// TODO Auto-generated method stub
		return null;
	}

	public void mutate(Random rng) {
		// TODO Auto-generated method stub
		
	}
	
	public void setInputs(double[] netInput){
		layer[0].setInputs(netInput);
	}
	
	private void setNetToRandom() {
		for (int layer = 0; layer < (layerWidths.length - 1); layer++) {
			for (int input = 0; input < (layerWidths[layer] + 1); input++) {
				for (int output = 0; output < layerWidths[layer+1]; output++) {
					setWeight(layer,input,output, (2*rng.nextDouble() - 1.0));
				}
			}
		}
	}
	
	public double[] output() {
		return layer[layer.length - 1].getValues();
	}
	
	public double[] input() {
		return layer[0].getValues();
	}

	public void propogate() {
		double [] input;
		double [] output;
		for (int l = 0; l < (layer.length - 1); l++) {
			input = layer[l].getValues();
			output = new double[layer[l+1].getValues().length];
			Weight w = weight[l];
			for (int o = 0; o < output.length; o++) {
				double sum = w.getWeight(0,o);
				for (int i = 0; i < input.length; i++) {
					sum += input[i]*w.getWeight(i+1, o);
				}
				sum = sum / (double)(input.length);
				output[o] = sigmoid(sum);
			}
			layer[l+1].setInputs(output);
		}
	}
	
	private double sigmoid(double x){
		return 1.0 / (1.0 + Math.exp(-x));
	}

	private void setWeight(int layer, int input, int output, double value) {
		Weight w = weight[layer];
		w.setWeight(input,output,value);
	}

	private class Layer {
		
		double [] values;
		
		Layer(int width) {
			values = new double[width];
		}
		
		void setInputs(double[] inputs) {
			if (inputs.length != values.length) {
				throw new IllegalArgumentException("input length, " + inputs.length + ", not equal to layer width, " + values.length);
			}
			values = inputs;
		}
		
		double[] getValues() {
			return values;
		}
	}
	
	private class Weight {
		
		double [][] values;
		
		Weight(int inWidth, int outWidth) {
			values = new double[inWidth][];
			for (int i = 0; i  < inWidth; i ++) {
				values[i] = new double[outWidth];
			}
		}

		public void setWeight(int input, int output, double value) {
			values[input][output] = value;
		}
		
		public double getWeight(int input, int output) {
			return values[input][output];
		}
	}

	public void scramble(Random rng2) {
		// TODO Auto-generated method stub
		
	}

	public Network cross(Network parent2, Random rng2) {
		// TODO Auto-generated method stub
		return null;
	}

}
