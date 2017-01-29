package org.weymouth.watchmaker;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.uncommons.watchmaker.framework.interactive.Renderer;
import org.weymouth.ants.Network;

public class NetworkRenderer implements Renderer<Network, JComponent> {
	
	@SuppressWarnings("unused")
	private final NetworkController controller;
	
	private final JComponent itsComponent;
	
	public NetworkRenderer(NetworkController controller) {
		this.controller = controller;
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
