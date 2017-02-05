package org.weymouth.ants.watchmaker;

import org.uncommons.watchmaker.swing.evolutionmonitor.EvolutionMonitor;
import org.weymouth.ants.Network;

public class NetworkEvolutionSwingObserver
		extends EvolutionMonitor<Network>{

	public NetworkEvolutionSwingObserver(NetworkController controller){
		super(new NetworkRenderer(controller),false);
	}
	
}
