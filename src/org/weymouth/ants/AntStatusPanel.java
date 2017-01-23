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
					panel.updateAntValues();
				}
			}
		});
	}

	protected class AntPanel extends JPanel{
		private static final long serialVersionUID = -3527203010799721964L;

		private Ant ant;
		JTextField healthValue = new JTextField(5);
		JTextField netZeroValue = new JTextField(5);
		JTextField netOneValue = new JTextField(5);
		
		public AntPanel(Ant ant){
			updateAnt(ant);
			setLayout(new GridLayout(2,2));
			setUpGraphics();
		}

		public void updateAnt(Ant ant) {
			this.ant = ant;
			updateAntValues();
		}
		
		public void setUpGraphics() {
			this.add(new JLabel("Health: "));
			this.add(healthValue);
			this.add(netZeroValue);
			this.add(netOneValue);
			updateAntValues();
		}

		public void updateAntValues(){
			String text1 = String.format("%1$,.3f",ant.getHealth());
			healthValue.setText(text1);
			Network net = ant.getBrain().getNetwork();
			double[] out = net.output();
			String text2 = String.format("%1$,.3f",out[0]);
			String text3 = String.format("%1$,.3f",out[1]);
			double[] in = net.input();
			String text4 = null;
			for (int i = 0; i < in.length; i++) {
				if (text4 == null) text4 = String.format("%1$,.2f",in[i]);
				else text4 += ", " + String.format("%1$,.2f",in[i]);
			}
//			System.out.println(text4);
			netZeroValue.setText(text2);
			netOneValue.setText(text3);
		}
	}
	
	

}
