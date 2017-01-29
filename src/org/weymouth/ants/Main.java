package org.weymouth.ants;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Iterator;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.weymouth.ants.graphics.AnimatedDrawing;
import org.weymouth.ants.graphics.AnimatedDrawingPanel;

public class Main {

	public static void main(String[] args) {
		(new Main()).exec();
	}

	private void exec() {
		
		final AntWorld antWorld = new AntWorld();
		final AntDisplay display = new AntDisplay();
//		final AntStatusPanel statusPanel = new AntStatusPanel(antWorld);
		List<AntBrain> brainList = AntBrain.starterList();
		final Iterator<AntBrain> brains = brainList.iterator();
		
		
		
		AnimatedDrawing brainCycleDrawer = new AnimatedDrawing() {
			
			int brainCount = 0;
			Generation g = null;
			
			@Override
			public void update() {
				boolean running = antWorld.update();
				if (! running) {
					if (brains.hasNext()) {
						if (g != null) {
							System.out.println("Score(" + (brainCount++) + "): " + g.score());
						}
						g = antWorld.setBrain(brains.next());
					} else {
//						System.out.println("Done - kill it");
						if (g != null) {
							System.out.println("Score(" + (brainCount) + "): " + g.score());
						}
						System.exit(0);
					}
				}
			}

			@Override
			public void draw(Graphics g) {
				if (antWorld.isRunning()) {
					display.drawWorld(g,antWorld);
				}
			}
		};
		
		brainCycleDrawer.setSize(new Dimension(AntWorld.WIDTH, AntWorld.HEIGHT));
		
		final AnimatedDrawingPanel drawer = 
				new AnimatedDrawingPanel(brainCycleDrawer);
				
		final JPanel panel = new JPanel(new BorderLayout());
		Dimension d = drawer.getSize();
		Dimension dPlus = new Dimension(d.width+10,d.height+10);
		panel.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
		panel.setSize(dPlus);
		panel.add(drawer,BorderLayout.CENTER);
//		panel.add(statusPanel,BorderLayout.EAST);
				
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				JFrame frame = new JFrame("Ant Generation Display");
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.getContentPane().add(panel);
				Dimension d = panel.getSize();
				Dimension dPlus = new Dimension(d.width+20,d.height+20);
				frame.setSize(dPlus);
				frame.pack();
				frame.setVisible(true);
				drawer.start();
			}
		});

		
	}

}
