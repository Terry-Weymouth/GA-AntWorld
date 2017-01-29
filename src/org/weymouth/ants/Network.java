package org.weymouth.ants;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Network {

	private int[] layerWidths;
	private Layer[] layer;
	private Weight[] weight;
	
	public Network(Random rng,int[] layerWidths){
		this.layerWidths = layerWidths;
		initializeLayers();
		setNetToRandom(rng);
	}
	
	private Network(Network clone) {
		layerWidths = clone.layerWidths;
		initializeLayers();
	}
	
	private void initializeLayers() {
		layer = new Layer[layerWidths.length];
		weight = new Weight[layerWidths.length - 1];
		
		for (int i = 0; i < layerWidths.length; i++) {
			layer[i] = new Layer(layerWidths[i]);
		}
		
		for (int layer = 0; layer < (layerWidths.length - 1); layer++) {
			weight[layer] = new Weight(layerWidths[layer]+1,layerWidths[layer+1]);
		}
	}

	public Network cross(Network parent2, Random rng) {
		double[] values1 = unwrapWeights();
		double[] values2 = parent2.unwrapWeights();

		int valueCount = values1.length;
		double[] out = new double[valueCount];
		
		int point = rng.nextInt(valueCount -2) + 1;
		for (int i = 0; i < point; i++) {
			out[i] = values1[i];
		}
		for (int i = point; i < valueCount; i++) {
			out[i] = values2[i];
		}

		Network net = new Network(this);
		net.wrapWeights(out);
		return net;
	}

	public void mutate(Random rng) {
		double[] values = unwrapWeights();
		int valueCount = values.length;
		int point = rng.nextInt(valueCount);
		values[point] = (rng.nextDouble() * 2.0) - 1.0;
		wrapWeights(values);
	}
	
	private double[] unwrapWeights() {
		List<Double> buffer = new ArrayList<Double>();
		for (int layer = 0; layer < (layerWidths.length - 1); layer++) {
			for (int input = 0; input < (layerWidths[layer] + 1); input++) {
				for (int output = 0; output < layerWidths[layer+1]; output++) {
					buffer.add(new Double(getWeight(layer,input,output)));
				}
			}
		}
		double[] out = new double[buffer.size()];
		for (int i = 0; i < out.length; i ++) {
			out[i] = buffer.get(i).doubleValue();
		}
		return out;
	}

	private void wrapWeights(double[] values) {
		int index = 0;
		for (int layer = 0; layer < (layerWidths.length - 1); layer++) {
			for (int input = 0; input < (layerWidths[layer] + 1); input++) {
				for (int output = 0; output < layerWidths[layer+1]; output++) {
					setWeight(layer,input,output, values[index++]);
				}
			}
		}
	}
	
	public void scramble(Random rng) {
		setNetToRandom(rng);
	}

	
	public void setInputs(double[] netInput){
		layer[0].setInputs(netInput);
	}
	
	private void setNetToRandom(Random rng) {
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
	
	private double getWeight(int layer, int input, int output) {
		Weight w = weight[layer];
		return w.getWeight(input, output);
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

}
