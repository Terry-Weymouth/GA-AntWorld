package org.weymouth.ants.watchmaker;

import java.sql.SQLException;

import org.uncommons.watchmaker.framework.EvolutionObserver;
import org.uncommons.watchmaker.framework.PopulationData;
import org.weymouth.ants.core.Network;
import org.weymouth.ants.core.NetworkPojo;
import org.weymouth.ants.storage.SqlliteStorage;

import com.fasterxml.jackson.core.JsonProcessingException;

public class StoringTextObserver implements EvolutionObserver<Network> {

	private int lastRecoredId = 0;
	
	@Override
	public void populationUpdate(PopulationData<? extends Network> data) {
		int generation = data.getGenerationNumber();
		Network network = data.getBestCandidate();
		long time = data.getElapsedTime();
		System.out.println("Generation " + generation + "; elapsed time = " + timeString(time) +
				"; average elapsed time = " + timeString(time/(generation + 1)));
		if (networkToStore(network)) {
			System.out.println("Network stored with id = " + lastRecoredId);
		} else {
			System.out.println("-->Network storing failed!");
		}
	}
	
	private String timeString(long elapsedTime) {
		double seconds = elapsedTime/1000.0;
		int minutes = (int)(elapsedTime/60000);
		int hours = minutes/60;
		seconds = seconds - minutes*60;
		minutes = minutes - hours*60;
		seconds = ((int)(seconds*100))/100.0;
		return String.format("%3d:%02d:%02.1f", hours, minutes, seconds);
	}
	
	private boolean networkToStore(Network network){
		boolean sucess = false;
		SqlliteStorage store = null;
		try {
			store = new SqlliteStorage("network.db");
			NetworkPojo networkPojo = new NetworkPojo(network);
			lastRecoredId = store.storeNetwork(networkPojo);
			sucess = true;
		} catch (SQLException | ClassNotFoundException | JsonProcessingException e) {
			e.printStackTrace();
			return false;
		} finally {
			if (store != null)
				try {
					store.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		return sucess;
	}
}
