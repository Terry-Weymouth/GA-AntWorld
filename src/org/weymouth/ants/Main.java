package org.weymouth.ants;

import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Iterator;
import java.util.List;

import javax.swing.JFrame;

import org.weymouth.ants.graphics.AnimatedDrawing;
import org.weymouth.ants.graphics.AnimatedDrawingPanel;

public class Main {

	public static void main(String[] args) {
		(new Main()).exec();
	}

	private void exec() {
		
		final AntWorld antWorld = new AntWorld();
		final AntDisplay display = new AntDisplay();
		List<AntBrain> brainList = AntBrain.starterList();
		final Iterator<AntBrain> brains = brainList.iterator();
		
		AnimatedDrawing brainCycleDrawer = new AnimatedDrawing() {
			
			@Override
			public void update() {
				boolean running = antWorld.update();
				if (! running) {
					if (brains.hasNext()) {
						antWorld.setBrain(brains.next());
					} else {
						System.out.println("Done - kill it");
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
		
		final AnimatedDrawingPanel panel = 
				new AnimatedDrawingPanel(brainCycleDrawer);
				
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				JFrame frame = new JFrame("Ant Generation Display");
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.getContentPane().add(panel);
				frame.setSize(panel.getSize());
				frame.setVisible(true);
				panel.start();
			}
		});

		
	}

}
