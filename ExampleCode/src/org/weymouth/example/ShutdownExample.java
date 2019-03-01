package org.weymouth.example;

public class ShutdownExample extends Thread {

	public static void main(String[] args) {
		Runtime.getRuntime().addShutdownHook(new ShutdownExample());
		System.out.println("sleeping");
		try {
			Thread.sleep(60*60*1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		System.out.println("shutdown hook");
	}
}
