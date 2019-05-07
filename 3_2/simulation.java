/** 
 * 	@brief Simulation class. 
 *  A template for the simulation.
 *             
 */
public class simulation {
	/**
	 * @brief Instantiates a grid, taking the dimensions from the configuration class and runs a timeStep at certain speed.
	 */
	public static void run() {
		grid g = grid.instantiate(config.m, config.n);
		g.timeStep(config.SPEED);
	}
	
	public static void main(String[] args) {
		System.out.println("Note: All debugging statements have been turned off by default.");
		System.out.println("Edit configuration to turn debugging statements on.");
		System.out.println("If no coyote or roadrunner are present in the grid, the other side wins by default.");		simulation.run();
	}
}
