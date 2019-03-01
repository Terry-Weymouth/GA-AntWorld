package org.weymouth.ants.nest.watchmaker;

import org.uncommons.watchmaker.swing.evolutionmonitor.EvolutionMonitor;
import org.weymouth.ants.nest.core.Network;

public class NetworkEvolutionSwingObserver
		extends EvolutionMonitor<Network>{

	public NetworkEvolutionSwingObserver(NetworkController controller){
		super(new NetworkRenderer(controller),false);
	}
	
}
