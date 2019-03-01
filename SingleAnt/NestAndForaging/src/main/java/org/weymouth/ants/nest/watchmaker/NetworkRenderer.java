package org.weymouth.ants.nest.watchmaker;

import javax.swing.DefaultListModel;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.uncommons.watchmaker.framework.interactive.Renderer;
import org.weymouth.ants.nest.core.Network;

public class NetworkRenderer implements Renderer<Network, JComponent> {
	
	@SuppressWarnings("unused")
	private final NetworkController controller;
	
	private final JComponent itsComponent;
	
	private DefaultListModel<Double> listModel = new DefaultListModel<Double>();
	
	public NetworkRenderer(NetworkController controller) {
		this.controller = controller;
		itsComponent=setup();
	}

	@Override
	public JComponent render(Network fn) {
		System.out.println("====> " + fn.getScore());
		listModel.add(listModel.getSize(), new Double(fn.getScore()));
		return itsComponent;
	}
	
	public JPanel setup() {
		JPanel panel = new JPanel();
		JLabel label = new JLabel("Top Scores");
		panel.add(label);
		JScrollPane scrollPane = new JScrollPane();
		JList<Double> list = new JList<Double>(listModel);
		scrollPane.setViewportView(list);
		panel.add(scrollPane);
		return panel;
	}

}
