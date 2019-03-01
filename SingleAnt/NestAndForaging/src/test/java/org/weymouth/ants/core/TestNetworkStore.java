package org.weymouth.ants.core;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Random;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;

import org.weymouth.ants.storage.SqlliteStorage;

public class TestNetworkStore {
	
	int[] net_layers = {3,4,4,2};
	Random zero = new Random(){
		private static final long serialVersionUID = 1L;
		@Override
		public double nextDouble() {
			return 0.0;
		}
	};
	
	private static double ERROR_BAR = 0.01;

	private SqlliteStorage store; 

	@Before
	public void Setup() throws ClassNotFoundException, SQLException {
		store = new SqlliteStorage("testNetwork.db");
	}
	
	@After
	public void Final() throws SQLException {
		store.close();
	}
	
	@Test
	public void testNetworkStore() throws SQLException, IOException{
		Network net = new Network(zero, net_layers);
		int l = 0;
		for (int li = 0; li < net_layers.length - 1; li++) {
			l += (net_layers[li] + 1) * net_layers[li+1];
		}
		double[] internalWeights = net.unwrapWeights();
		assertEquals(l, internalWeights.length);
		for (int i = 0; i < internalWeights.length; i++) {
			assertEquals("(Zero) Unwraped weights failed for index: " + i,-Network.WEIGHT_BAND,internalWeights[i],ERROR_BAR);
		}
		assertEquals("Expect score to be zero", 0.0, net.getScore(), ERROR_BAR);
		NetworkPojo networkPojo = new NetworkPojo(net);
		int id = store.storeNetwork(networkPojo);
		NetworkPojo recovered = store.recoverNetwork(id);
		assertEquals("Original/Recovered worldType", networkPojo.getWorldType(), recovered.getWorldType());
		assertEquals("Original/Recovered score", networkPojo.getScore(), recovered.getScore(), ERROR_BAR);
		assertEquals("Recovered length layers", net_layers.length, recovered.getLayerWidths().length);
		assertEquals("Recovered weights layers", internalWeights.length, recovered.getWeights().length);
		for (int li = 0; li < net_layers.length; li++) {
			assertEquals("layer width " + li, net_layers[li], recovered.getLayerWidths()[li]);
		}
	}
}
