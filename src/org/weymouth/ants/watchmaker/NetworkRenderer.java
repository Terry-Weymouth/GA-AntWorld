package org.weymouth.ants.watchmaker;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.uncommons.watchmaker.framework.interactive.Renderer;
import org.weymouth.ants.Network;

public class NetworkRenderer implements Renderer<Network, JComponent> {
		
	private final JComponent itsComponent;
	
	public NetworkRenderer() {
		itsComponent=setup();
	}
	
	@Override
	public JComponent render(Network fn) {
		return itsComponent;
	}
	
	public JPanel setup() {
		JPanel panel = new JPanel();
		JLabel label = new JLabel("Hello World");
		panel.add(label);
		return panel;
	}
}
