package org.weymouth.ants.graphics;

import javax.swing.JFrame;

public class DemoMain {

	public static void main(String[] args) {
		(new DemoMain()).exec(new AnimatedDrawingPanel(new AnimationDemo()));
	}

	private void exec(final AnimatedDrawingPanel panel) {

		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				JFrame frame = new JFrame("DrawingBoardDemo");
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.getContentPane().add(panel);
				System.out.println(panel.getSize().getHeight());
				frame.setSize(panel.getSize());
				frame.setVisible(true);
				panel.start();
			}
		});
	}

}
