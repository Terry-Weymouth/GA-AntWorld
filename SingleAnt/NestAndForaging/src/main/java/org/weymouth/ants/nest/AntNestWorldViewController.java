package org.weymouth.ants.nest;

public class AntNestWorldViewController {

	// poorman's singleton
	public static final AntNestWorldViewController controller = new AntNestWorldViewController();
	
	public static AntNestWorldViewController getController() {
		return controller;
	}

	private AntNestWorldViewController() {
		// constructor not available to public
	}

	
	AntNestWorldView theWorldView = null;
	
	public void initialize() {
		while (theWorldView == null) {
			// wait for the theWorld to be registered
			try {
				Thread.sleep(100);
			} catch (InterruptedException ignore) {
			}
		}
	}
	
	public void register(AntNestWorldView aw) {
		theWorldView = aw;
	}
	
	public AntNestWorldView getView() {
		return theWorldView;
	}

}
