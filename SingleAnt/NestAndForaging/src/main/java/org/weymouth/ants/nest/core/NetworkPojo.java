package org.weymouth.ants.nest.core;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.ObjectReader;

public class NetworkPojo {
	
	private String worldType = AntWorld.WORLD_TYPE;
	private int[] layerWidths = new int[0];
	private double[] weights = null;
	private double score = 0;
	
	private ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
	private static ObjectReader or = new ObjectMapper().reader().forType(NetworkPojo.class);
	
	public static NetworkPojo compose(String json) throws IOException {
		return or.readValue(json);
	}
	
	public NetworkPojo() {}
	
	public NetworkPojo(Network network) {
		weights = network.unwrapWeights();
		score = network.getScore();
		layerWidths = network.getLayerWidths();
	}

	public String getWorldType() {
		return worldType;
	}

	public void setWorldType(String worldType) {
		this.worldType = worldType;
	}

	public int[] getLayerWidths() {
		return layerWidths;
	}

	public void setLayerWidths(int[] layerWidths) {
		this.layerWidths = layerWidths;
	}

	public double[] getWeights() {
		return weights;
	}

	public void setWeights(double[] weights) {
		this.weights = weights;
	}

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}
	
	public String toJson() throws JsonProcessingException {
		String json = ow.writeValueAsString(this);
		return json;
	}
	
	public String toString() {
		return "NetworkPojo: \n"
				+ "  worldType: " + getWorldType() + "\n"
				+ "  score: " + getScore() + "\n"
				+ "  layerWidths: " + getLayerWidths() + "\n"
				+ "  weights: " + getWeights();
	}
	
}
