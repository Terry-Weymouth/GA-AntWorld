package org.weymouth.ants.core;

public class AntWorldViewController {
	
	// poorman's singleton
	public static final AntWorldViewController controller = new AntWorldViewController();
	
	public static AntWorldViewController getController() {
		return controller;
	}

	private AntWorldViewController() {
		// constructor not available to public
	}

	
	AntWorldView theWorldView = null;
	
	public void initialize() {
		while (theWorldView == null) {
			// wait for the theWorld to be registered
			try {
				Thread.sleep(100);
			} catch (InterruptedException ignore) {
			}
		}
	}
	
	public void register(AntWorldView aw) {
		theWorldView = aw;
	}
	
	public AntWorldView getView() {
		return theWorldView;
	}

}
