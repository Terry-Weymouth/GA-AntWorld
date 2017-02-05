package org.weymouth.ants.watchmaker;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.uncommons.watchmaker.framework.EvolutionObserver;
import org.uncommons.watchmaker.swing.evolutionmonitor.EvolutionMonitor;
import org.weymouth.ants.Network;

public class NetworkController {
	
	private EvolutionMonitor<Network> monitor;

	public JPanel getJPanel() {
		JPanel ret = new JPanel();
		monitor = new NetworkEvolutionSwingObserver(this);
		ret.add(monitor.getGUIComponent());
		return ret;
	}

	public EvolutionObserver<? super Network> getEvolutionObserver() {
		return monitor;
	}

	private static void fireItOff(final NetworkController c) {
		
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            	//Create and set up the window.
                JFrame frame = new JFrame("Network GA Learner");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.getContentPane().add(c.getJPanel());

                //Display the window.
                frame.pack();
                frame.setVisible(true);
            }
        });
	}

	public void setupGui() {
		NetworkController.fireItOff(this);
	}

}
