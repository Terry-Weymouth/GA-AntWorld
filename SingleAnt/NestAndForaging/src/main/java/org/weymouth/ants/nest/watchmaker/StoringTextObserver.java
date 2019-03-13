package org.weymouth.ants.nest.watchmaker;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.uncommons.watchmaker.framework.EvolutionObserver;
import org.uncommons.watchmaker.framework.PopulationData;
import org.weymouth.ants.nest.core.Network;
import org.weymouth.ants.nest.core.NetworkPojo;
import org.weymouth.ants.nest.storage.SqlliteStorage;

import com.fasterxml.jackson.core.JsonProcessingException;

public class StoringTextObserver implements EvolutionObserver<Network> {

	private int lastRecoredId = 0;
	private List<Double> topScores = new ArrayList<Double>();
	private List<Double> topDBScores = new ArrayList<Double>();
	
	@Override
	public void populationUpdate(PopulationData<? extends Network> data) {
		double topScore = data.getBestCandidateFitness();
		int generation = data.getGenerationNumber();
		Network network = data.getBestCandidate();
		topScores.add(new Double(topScore));
		System.out.println("Generation " + generation);
		System.out.println("  topScore = " + topScore);
		System.out.println("  elapsed time = " + timeString(data.getElapsedTime()));
		if (networkToStore(network)) {
			System.out.println("--> Network stored with id = " + lastRecoredId);
		} else {
			System.out.println("--> Network storing failed!");
		}
		System.out.println("Top Scores:");
		printTopScoreHistory();
		printTopDBScores();
	}
	
	private String timeString(long elapsedTime) {
		double seconds = elapsedTime/1000.0;
		int minutes = (int)(elapsedTime/60000);
		int hours = minutes/60;
		seconds = seconds - minutes*60;
		minutes = minutes - hours*60;
		return String.format("%2d:%2d:%2.2f", hours, minutes, seconds);
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
		} finally {
			if (store != null)
				try {
					store.close();
				} catch (SQLException ignore) {
				}
		}
		return sucess;
	}
	
	private void printTopScoreHistory() {
		System.out.println(String.format("  Top Scores so far (out of %d)", topScores.size()));
		printIndentedScoreList(topScores);
	}

	private void printTopDBScores() {
		SqlliteStorage store = null;
		try {
			store = new SqlliteStorage("network.db");
			List<NetworkPojo> netList = store.getTop(10);
			topDBScores.clear();
			for (NetworkPojo pojo: netList) {
				topDBScores.add(new Double(pojo.getScore()));
			}
		} catch (SQLException | ClassNotFoundException | IOException e) {
			e.printStackTrace();
		} finally {
			if (store != null)
				try {
					store.close();
				} catch (SQLException ignore) {
				}
		}
		System.out.println(String.format("  Top DB Scores (size: %d)", topDBScores.size()));
		printIndentedScoreList(topDBScores);		
	}

	private void printIndentedScoreList(List<Double> scoreList) {
		if (scoreList.isEmpty()) {
			System.out.println("    (empty list)");
		} else {
			for (int i = 0; i < scoreList.size(); i = i+5) {
				System.out.print("    ");
				for (int n = 0; n < 5; n++) {
					if ((i + n) < scoreList.size()) {
						System.out.print(scoreList.get(i + n).doubleValue());
						if ((i+n+1) < scoreList.size()) {
							System.out.print(", ");
						}
					}
				}
				System.out.println();
			}
		}
	}

}
