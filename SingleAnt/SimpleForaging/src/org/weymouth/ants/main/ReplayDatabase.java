package org.weymouth.ants.main;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.uncommons.maths.random.MersenneTwisterRNG;
import org.weymouth.ants.core.AntBrain;
import org.weymouth.ants.core.AntWorld;
import org.weymouth.ants.core.AntWorldView;
import org.weymouth.ants.core.AntWorldViewController;
import org.weymouth.ants.core.NetworkPojo;
import org.weymouth.ants.core.Network;
import org.weymouth.ants.storage.SqlliteStorage;

public class ReplayDatabase {
	
	public static void main(String[] args) {
		try {
			(new ReplayDatabase()).exec();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void exec() throws ClassNotFoundException, SQLException, IOException {
		System.out.println("Replay of top ten Networks: ");
		NetworkPojo[] list = getTopTen();
		System.out.println("Number of networks fetched = " + list.length);
		Random rng = new MersenneTwisterRNG();
		List<Network> nets = new ArrayList<Network>();
		for (NetworkPojo pojo: list) {
			System.out.println("  score = " + pojo.getScore());
			nets.add(new Network(rng, pojo.getLayerWidths(), pojo.getWeights()));
		}
		AntWorldViewController worldController = AntWorldViewController.getController();
		worldController.initialize();
		for (Network net: nets) {
			play(net, worldController);
		}

	}

	private double play(Network net, AntWorldViewController worldController) {
		System.out.println("Playing next Network...");
		AntBrain antBrain = new AntBrain(net);
		AntWorldView antWorldView = worldController.getView();
		AntWorld antWorld = new AntWorld(antBrain);
		while (antWorld.update()){
			antWorldView.update(antWorld.cloneAnts(), antWorld.cloneMeals());
		}
		double score = (double)antWorld.getScore()/10000.0;
		System.out.println("Resulting score = " + score);
		return score;
	}

	private NetworkPojo[] getTopTen() throws ClassNotFoundException, SQLException, IOException {
		SqlliteStorage store = new SqlliteStorage();
		NetworkPojo[] ret = store.getTop(10);
		store.close();
		return ret;
	}

}
