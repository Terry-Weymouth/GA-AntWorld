package org.weymouth.ants;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class AntStatusPanel extends JPanel implements WorldChangeListener {

	private static final long serialVersionUID = -869395509288543956L;
	
	private final AntWorld theWorld;
	private final List<AntPanel> antPanels = new ArrayList<AntPanel>();

	public AntStatusPanel(AntWorld antWorld) {
		theWorld = antWorld;
		setUpGraphcs(AntWorld.NUMBER_OF_ANTS);
		antWorld.registerChangeListener(this);
	}

	private void setUpGraphcs(int count) {
		this.setLayout(new GridLayout(0,1));
		Ant dummy = new Ant(
				theWorld,AntBrain.starterList().get(0),Util.randomLocation());
		for (int i = 0; i < count; i++) {
			AntPanel p = new AntPanel(dummy);
			this.add(p);
			antPanels.add(p);
		}
	}

	@Override
	public void generationChanged() {
		System.out.println("Generation changed");
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				List<Ant> ants = theWorld.getAnts();
				for (int i = 0; i < ants.size(); i++) {
					Ant ant = ants.get(i);
					AntPanel panel = antPanels.get(i);
					panel.updateAnt(ant);
				}
			}
		});
	}

	@Override
	public void antsChanged() {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				for (int i = 0; i < antPanels.size(); i++) {
					AntPanel panel = antPanels.get(i);
					panel.updateHealth();
				}
			}
		});
	}

	protected class AntPanel extends JPanel{
		private static final long serialVersionUID = -3527203010799721964L;

		private Ant ant;
		JLabel label = new JLabel("Health: ");
		JTextField value = new JTextField(5);
		
		public AntPanel(Ant ant){
			updateAnt(ant);
			setLayout(new FlowLayout());
			setUpGraphics();
		}

		public void updateAnt(Ant ant) {
			this.ant = ant;
			updateHealth();
		}
		
		public void setUpGraphics() {
			this.add(label);
			this.add(value);
			updateHealth();
		}

		public void updateHealth(){
			String text = String.format("%1$,.3f",ant.getHealth());
			value.setText(text);
		}
	}
	
	

}
