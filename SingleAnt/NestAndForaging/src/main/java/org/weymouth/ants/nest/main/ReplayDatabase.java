package org.weymouth.ants.nest.main;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.uncommons.maths.random.MersenneTwisterRNG;
import org.weymouth.ants.nest.core.AntBrain;
import org.weymouth.ants.nest.core.AntWorld;
import org.weymouth.ants.nest.core.AntWorldView;
import org.weymouth.ants.nest.core.AntWorldViewController;
import org.weymouth.ants.nest.core.NetworkPojo;
import org.weymouth.ants.nest.core.Network;
import org.weymouth.ants.nest.storage.SqlliteStorage;

import processing.core.PApplet;

public class ReplayDatabase {
	
	public void exec() throws ClassNotFoundException, SQLException, IOException {
		System.out.println("Replay of top ten Networks: ");
		ArrayList<NetworkPojo> list = getTopTen();
		Collections.reverse(list);
		System.out.println("Number of networks fetched = " + list.size());
		Random rng = new MersenneTwisterRNG();
		List<NetHolder> nhList = new ArrayList<NetHolder>();
		for (NetworkPojo pojo: list) {
			nhList.add(new NetHolder(new Network(rng, pojo.getLayerWidths(), pojo.getWeights()), pojo));
		}
		AntWorldViewController worldController = AntWorldViewController.getController();
		PApplet.main(AntWorldView.class);
		worldController.initialize();
		AntWorldView antWorldView = worldController.getView();
		for (NetHolder nh: nhList) {
			Network net = nh.network;
			NetworkPojo pojo = nh.pojo;
			System.out.println("Playing next Network...");
			System.out.println("  Previous score = " + pojo.getScore());
			double score = play(net, antWorldView);
			System.out.println("  Resulting score = " + score);
		}
		antWorldView.exit();
		System.out.println("Replay Done.");
	}

	private double play(Network net, AntWorldView antWorldView) {
		AntBrain antBrain = new AntBrain(net);
		AntWorld antWorld = new AntWorld(antBrain);
		while (antWorld.update()){
			antWorldView.update(antWorld.cloneAnts(), antWorld.cloneMeals());
		}
		double score = (double)antWorld.getScore()/10000.0;
		return score;
	}

	private ArrayList<NetworkPojo> getTopTen() throws ClassNotFoundException, SQLException, IOException {
		SqlliteStorage store = new SqlliteStorage("network.db");
		ArrayList<NetworkPojo> ret = store.getTop(10);
		store.close();
		return ret;
	}

	private class NetHolder {
		
		Network network;
		NetworkPojo pojo;
		
		NetHolder(Network n, NetworkPojo np){
			network = n;
			pojo = np;
		}
	}
}
