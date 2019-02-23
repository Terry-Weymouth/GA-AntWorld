package org.weymouth.example;

import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PSurface;
import processing.core.PVector;
import processing.event.MouseEvent;

public class ProcessingFlock extends PApplet {

	private static final int MAX_NUMBER_OF_STEPS = 1000;

	private Flock flock;
	
	private PSurface s;
	
	private int numberOfSteps = 0;

	public void settings() {
		size(640, 360);
	}
	
	public void pause() {
		numberOfSteps = MAX_NUMBER_OF_STEPS + 1;
		super.pause();
		if (s != null) {
			s.pauseThread();
			s.setVisible(false);
		}
	}
	
	public void resume() {
		numberOfSteps = 0;
		super.resume();
		if (s != null) {
			s.resumeThread();
			s.setVisible(true);
		}
	}
	
	public int getCurrentCompletionCount() {
		return numberOfSteps;
	}

	public void setup() {
		resetState();
		s = this.getSurface();
		if (s != null) {
			System.out.println(this.getSurface().getClass().getName());
			pause();
		}
		else
			System.out.println("Surface is null");
	}
	
	public void resetState() {
		flock = new Flock();
		// Add an initial set of boids into the system
		for (int i = 0; i < 150; i++) {
			flock.addBoid(new Boid(this, width / 2, height / 2));
		}
	}
	
	public boolean isDone() {
		return (numberOfSteps > MAX_NUMBER_OF_STEPS);
	}

	public void draw() {
		numberOfSteps++;
		background(50);
		flock.run();
	}
	
	public void mousePressed(MouseEvent event) {
		if (event.isShiftDown()) {
			pause();
		} else {
			flock.addBoid(new Boid(this, mouseX, mouseY));
		}
	}

	public static void main(String[] args) {
		PApplet.main("org.weymouth.example.ProcessingFlock",args);
	}

	public String getMessage() {
		return "Number of steps: " + getCurrentCompletionCount() + " out of " + getLengthOfTask();
	}

	public int getLengthOfTask() {
		return MAX_NUMBER_OF_STEPS;
	}

}

// The Flock (a list of Boid objects)

class Flock {
	ArrayList<Boid> boids; // An ArrayList for all the boids

	Flock() {
		boids = new ArrayList<Boid>(); // Initialize the ArrayList
	}

	void run() {
		for (Boid b : boids) {
			b.run(boids); // Passing the entire list of boids to each boid
							// individually
		}
	}

	void addBoid(Boid b) {
		boids.add(b);
	}
	
	void setTarget(PVector target){
		for (Boid b : boids) {
			b.seek(target);
		}
	}

}

// The Boid class

class Boid {

	PVector position;
	PVector velocity;
	PVector acceleration;
	PApplet base;
	float r;
	float maxforce; // Maximum steering force
	float maxspeed; // Maximum speed

	Boid(PApplet base, float x, float y) {
		this.base = base;
		acceleration = new PVector(0, 0);

		// This is a new PVector method not yet implemented in JS
		// velocity = PVector.random2D();

		// Leaving the code temporarily this way so that this example runs in JS
		float angle = base.random(PApplet.TWO_PI);
		velocity = new PVector(PApplet.cos(angle), PApplet.sin(angle));

		position = new PVector(x, y);
		r = 2.0f;
		maxspeed = 2f;
		maxforce = 0.03f;
	}

	void run(ArrayList<Boid> boids) {
		flock(boids);
		update();
		borders();
		render();
	}

	void applyForce(PVector force) {
		// We could add mass here if we want A = F / M
		acceleration.add(force);
	}

	// We accumulate a new acceleration each time based on three rules
	void flock(ArrayList<Boid> boids) {
		PVector sep = separate(boids); // Separation
		PVector ali = align(boids); // Alignment
		PVector coh = cohesion(boids); // Cohesion
		// Arbitrarily weight these forces
		sep.mult(1.5f);
		ali.mult(1.0f);
		coh.mult(1.0f);
		// Add the force vectors to acceleration
		applyForce(sep);
		applyForce(ali);
		applyForce(coh);
	}

	// Method to update position
	void update() {
		// Update velocity
		velocity.add(acceleration);
		// Limit speed
		velocity.limit(maxspeed);
		position.add(velocity);
		// Reset accelertion to 0 each cycle - or coast 
		acceleration.mult(0.5f);
	}

	// A method that calculates and applies a steering force towards a target
	// STEER = DESIRED MINUS VELOCITY
	PVector seek(PVector target) {
		PVector desired = PVector.sub(target, position); // A vector pointing
															// from the position
															// to the target
		// Scale to maximum speed
		desired.normalize();
		desired.mult(maxspeed);

		// Above two lines of code below could be condensed with new PVector
		// setMag() method
		// Not using this method until Processing.js catches up
		// desired.setMag(maxspeed);

		// Steering = Desired minus Velocity
		PVector steer = PVector.sub(desired, velocity);
		steer.limit(maxforce); // Limit to maximum steering force
		return steer;
	}

	void render() {
		// Draw a triangle rotated in the direction of velocity
		@SuppressWarnings("deprecation")
		float theta = velocity.heading2D() + PApplet.radians(90);
		// heading2D() above is now heading() but leaving old syntax until
		// Processing.js catches up

		base.fill(200, 100);
		base.stroke(255);
		base.pushMatrix();
		base.translate(position.x, position.y);
		base.rotate(theta);
		base.beginShape(PApplet.TRIANGLES);
		base.vertex(0, -r * 2);
		base.vertex(-r, r * 2);
		base.vertex(r, r * 2);
		base.endShape();
		base.popMatrix();
	}

	// Wrap around
	void borders() {
		if (position.x < -r)
			position.x = base.width + r;
		if (position.y < -r)
			position.y = base.height + r;
		if (position.x > base.width + r)
			position.x = -r;
		if (position.y > base.height + r)
			position.y = -r;
	}

	// Separation
	// Method checks for nearby boids and steers away
	PVector separate(ArrayList<Boid> boids) {
		float desiredseparation = 25.0f;
		PVector steer = new PVector(0, 0, 0);
		int count = 0;
		// For every boid in the system, check if it's too close
		for (Boid other : boids) {
			float d = PVector.dist(position, other.position);
			// If the distance is greater than 0 and less than an arbitrary
			// amount (0 when you are yourself)
			if ((d > 0) && (d < desiredseparation)) {
				// Calculate vector pointing away from neighbor
				PVector diff = PVector.sub(position, other.position);
				diff.normalize();
				diff.div(d); // Weight by distance
				steer.add(diff);
				count++; // Keep track of how many
			}
		}
		// Average -- divide by how many
		if (count > 0) {
			steer.div((float) count);
		}

		// As long as the vector is greater than 0
		if (steer.mag() > 0) {
			// First two lines of code below could be condensed with new PVector
			// setMag() method
			// Not using this method until Processing.js catches up
			// steer.setMag(maxspeed);

			// Implement Reynolds: Steering = Desired - Velocity
			steer.normalize();
			steer.mult(maxspeed);
			steer.sub(velocity);
			steer.limit(maxforce);
		}
		return steer;
	}

	// Alignment
	// For every nearby boid in the system, calculate the average velocity
	PVector align(ArrayList<Boid> boids) {
		float neighbordist = 50;
		PVector sum = new PVector(0, 0);
		int count = 0;
		for (Boid other : boids) {
			float d = PVector.dist(position, other.position);
			if ((d > 0) && (d < neighbordist)) {
				sum.add(other.velocity);
				count++;
			}
		}
		if (count > 0) {
			sum.div((float) count);
			// First two lines of code below could be condensed with new PVector
			// setMag() method
			// Not using this method until Processing.js catches up
			// sum.setMag(maxspeed);

			// Implement Reynolds: Steering = Desired - Velocity
			sum.normalize();
			sum.mult(maxspeed);
			PVector steer = PVector.sub(sum, velocity);
			steer.limit(maxforce);
			return steer;
		} else {
			return new PVector(0, 0);
		}
	}

	// Cohesion
	// For the average position (i.e. center) of all nearby boids, calculate
	// steering vector towards that position
	PVector cohesion(ArrayList<Boid> boids) {
		float neighbordist = 50;
		PVector sum = new PVector(0, 0); // Start with empty vector to
											// accumulate all positions
		int count = 0;
		for (Boid other : boids) {
			float d = PVector.dist(position, other.position);
			if ((d > 0) && (d < neighbordist)) {
				sum.add(other.position); // Add position
				count++;
			}
		}
		if (count > 0) {
			sum.div(count);
			return seek(sum); // Steer towards the position
		} else {
			return new PVector(0, 0);
		}
	}
}
