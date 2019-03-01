package org.weymouth.ants.nest;

import org.weymouth.ants.core.AntWorldView;

public class AntNestWorldView extends AntWorldView {
	
	static final int MARGIN = 20 + Math.max(AntNestWorld.NUMBER_OF_ANTS, 11)*20;
		
	public void settings(){
		size(AntNestWorld.WIDTH + MARGIN, AntNestWorld.HEIGHT);
		AntNestWorldViewController.getController().register(this);
    }


}
