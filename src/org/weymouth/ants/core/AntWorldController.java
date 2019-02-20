package org.weymouth.ants.core;

public class AntWorldController {
	
	// poorman's singleton
	public static final AntWorldController controller = new AntWorldController();
	
	public static AntWorldController getController() {
		return controller;
	}

	private AntWorldController() {
		// constructor not available to public
	}

	
	
	AntWorld theWorld = null;
	
	public void initialize() {
		while (theWorld == null || !theWorld.isReady()) {
			// wait for the theWorld to be registered
			try {
				Thread.sleep(100);
			} catch (InterruptedException ignore) {
			}
		}
	}
	
	public void register(AntWorld aw) {
		theWorld = aw;
	}

	public AntWorld getSimulator() {
		return theWorld;
	}

}
