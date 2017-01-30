package org.weymouth.ants;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.weymouth.ants.graphics.AnimatedDrawing;
import org.weymouth.ants.graphics.AnimatedDrawingPanel;

public class AntWorldInterface {

	private final static AntWorldInterface theInterface = new AntWorldInterface();
	private AntWorld theWorld  = new AntWorld();
	private	AntDisplay display = new AntDisplay();
	private AnimatedDrawingPanel drawer = null;
	private AnimatedDrawing brainCycleDrawer = null;

	public static AntWorldInterface getInterface() { return theInterface; }
	
	private AntWorldInterface(){
		initInterface();
	}
	
	public int evaluate(Network net) {
		setNetwork(net);
		runSimulation();
		return getScore();
	}


	private void setNetwork(Network network) {
		theWorld.setBrain(new AntBrain(network));
	}

	private void runSimulation() {
		drawer.start();
	}

	private int getScore() {
		return theWorld.getScore();
	}

	private void initInterface() {
		brainCycleDrawer = new AnimatedDrawing() {
			
			@Override
			public void update() {
				theWorld.update();
			}

			@Override
			public void draw(Graphics g) {
				if (theWorld.isRunning()) {
					display.drawWorld(g,theWorld);
				}
			}
		};
		brainCycleDrawer.setSize(new Dimension(AntWorld.WIDTH, AntWorld.HEIGHT));
		
		 
		drawer = new AnimatedDrawingPanel(brainCycleDrawer);
				
		final JPanel panel = new JPanel(new BorderLayout());
		Dimension d = drawer.getSize();
		Dimension dPlus = new Dimension(d.width+10,d.height+10);
		panel.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
		panel.setSize(dPlus);
		panel.add(drawer,BorderLayout.CENTER);
				
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				JFrame frame = new JFrame("Ant Generation Display");
				frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				frame.getContentPane().add(panel);
				Dimension d = panel.getSize();
				Dimension dPlus = new Dimension(d.width+20,d.height+20);
				frame.setSize(dPlus);
				frame.pack();
				frame.setVisible(true);
			}
		});
	}

}
