package org.weymouth.ants.main;

import java.io.IOException;
import java.sql.SQLException;

public class Main {

	public static void main(String[] args) {
		String headlessEnv = System.getenv("HEADLESS");
		String replayEnv = System.getenv("REPLAY");
		boolean headless = false;
		boolean replay = false;
		System.out.println("**************************************************");
		if (replayEnv != null) {
			replay = true;
			System.out.println("REPLAY env variable was specified: running replay");
		} else {
			System.out.println("REPLAY env variable was not specified: running main simulation");
		}
		if (!replay) {
			if (headlessEnv != null) {
				System.out.println("HEADELSS env variable was specified: running main simulation headless (without graphics)");
				headless = true;
			} else {
				System.out.println("HEADELSS env variable was not specified: running main simulation with graphics");				
			}
		}
		if (replay) {
			try {
				(new ReplayDatabase()).exec();
			} catch (ClassNotFoundException | SQLException | IOException e) {
				e.printStackTrace();
			}
		} else {
			(new WatchmakerMain(headless)).exec();
		}
	}

}
