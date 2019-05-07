import java.util.Random; //To generate a random number of coyotes and road-runners
import java.util.Vector; //A Vector is used to store the grid 

/**
 * @brief Grid class of type singleton
 *        
 *        A template for the grid.
 *             
 */
public class grid {
  ///As a default, agent is defined as 0 for this simulation 
  private static final int AGENT = config.AGENT;
  
  ///As a default, road-runner is defined as 1 for this simulation
  private static final int ROADRUNNER = config.ROADRUNNER;
  
  ///As a default, coyote is defined as 2 for this simulation
  private static final int COYOTE = config.COYOTE;
  
  ///The singleton instance for the grid
  private static grid instance; 
  
  ///Grid dimension
  private int n;
  private int m;
  
  ///A vector of agents that signify the current state of the grid. 
  ///NOTE: An agent in the grid, if not later defined as coyote or a road-runner, is  equivalent to a space
  private Vector<agent> state = new Vector<agent>(); 

  /**
   * @brief A private constructor for the grid
   * @param j The n dimension
   * @param k The m dimension
   */
  private grid (int j, int k) {
    n = j;
    m = k;
    initializeGrid();
  }


  /**
   * @brief Instantiates a grid object
   * @param j The n dimension
   * @param k The m dimension
   * @return The singleton grid instance
   */
  public static grid instantiate(int j, int k) {
    if (instance == null) { //If instance has not been created
      instance = new grid(j, k); //Create a new instance
    }
    return instance; //Otherwise, return the static instance
  }

  /**
   * @brief Returns error if dimension is not provided as argument or else returns the singleton grid instance already created.
   * @return The singleton grid instance.
   */
  public static grid instantiate() {
    if (instance == null) { //If instance has not been created
      System.out.println("Error: instantiate function requires dimension parameters."); //Throw an error saying parameters for dimensions required and exit
      System.exit(0);
    }
    return instance; //Otherwise, return the static instance
  }

  /**
   * @brief Initializes the grid with a random number of coyotes and road-runners
   */
  private void initializeGrid() {
	  ///Initialize the grid with agents (space)
	  for(int i = 0; i < n*m; i++) {
		  agent tmp = new agent(i);
		  state.add(tmp);
	  	}
	  
    ///Generate a random number of coyotes and road-runners to be put into the space
    Random random = new Random();
    int nRoadrunner = random.nextInt(n*m); //No. of coyotes to be generated will have to smaller than the dimension space
    int nCoyote = random.nextInt(n*m - nRoadrunner); //No. of road-runners to be generated will have to smaller than (dimension space - number of coyotes)
    
    ///Define agents (the spaces) to be nCoyote coyotes and nRoadrunner road-runners
    ///Put nCoyotes into the space
    for(int i = 0; i < nCoyote; i++) {
      boolean success = false;
      while(success != true) {
    	//Create a temporary coyote and save it's grid index
        int iLoc = random.nextInt(n*m);
        coyote tmp = new coyote(iLoc);
        
        //Only put the coyote in the location if the space is undefined
        if(state.get(iLoc).getDefn() == 0) {
          state.set(iLoc, tmp);
          success = true;
        }
      }
    }

    //Put nRoadrunners into the space
    for(int i = 0; i < nRoadrunner; i++) {
      boolean success = false;
      while(success != true) {
        int iLoc = random.nextInt(n*m);
        roadrunner tmp = new roadrunner(iLoc);
        
        //Only put the road-runner in the location if the space is undefined
        if(state.get(iLoc).getDefn() == 0) {
          state.set(iLoc, tmp);
          success = true;
        }
      }
    }
  }

  /**
   * @brief Displays the grid on-screen
   */
  public void displayGrid() {
	  if(hasGridChanged()) {
	  	for(int i = 0; i < m * n; i += n) { 
	  		for(int j = i; j < n + i && j < m * n; j++) { 
	  			System.out.print(state.get(j).getDefn() + " ");
	  			}  
	  		System.out.print('\n');
	  	}
	  	System.out.println();
	  }
  }

  /**
   * @brief Returns the state at a certain index. Useful when checking what kind of agent occupies the space.
   * @param j The location for which the state is asked for
   * @return The definition of the agent.
   */
  public int getState(int j) {
    return state.get(j).getDefn();
  }
  
  /**
   * @brief Sets the state at a certain index
   * @param j The location for which the state is to be set
   * @param defn The definition of the agent 
   */
  public void setAgent(int j, int defn) {
	  if(defn == AGENT) {
		  state.set(j, new agent()); 
	  }
	  
	  if(defn == COYOTE) {
		  coyote tmp = new coyote(j);
		  state.set(j, tmp);
	  }
	  
	  if(defn == ROADRUNNER) {
	  	  roadrunner tmp = new roadrunner(j);
	  	  state.set(j, tmp);
	  }
  }
 
  /**
   * @brief Returns a reference to the agent at certain index
   * @param j The index for which the agent is to be returned
   * @return The reference to the agent at index j 
   */
  public agent getAgent(int j) {
	  return state.get(j);
  }
    
  /**
   * @brief Whether or not the given index is a left edge in the grid
   * @param j The index for which to check
   * @return A boolean value of true or false
   */
  public boolean isLeftEdge(int j) {
	 ///If the index is 0, it is a left edge
	 if(j == 0) { 
		 return true;
	 }
	 
	  ///If an out-of-range index is given, this function simply returns false
	  if(j >= n * m) {
		  return false;
	  }
	  
	 //Check if index is left edge
	 for(int i = 1; i < m; i++) { 
		 if(j == n * i) {
			 return true;
		 }
	 }
	 return false;
  }
  
  /**
   * @brief Whether or not the given index is a right edge in the grid
   * @param j The index for which to check
   * @return A boolean value of true or false
   */
  public boolean isRightEdge(int j) {
	  //If an out-of-range index is given, this function simply returns false
	  if(j >= n * m) {
		  return false;
	  }
	  
	  //Check if index is right edge
	  for(int i = 1; i <= m; i++) {
		  if(j == n * i - 1) {
			  return true;
		  }
	  }
	  return false;
  }
  
  /**
   * @brief Whether or not the given index is a top edge in the grid
   * @param j The index for which to check
   * @return A boolean value of true or false
   */ 
  public boolean isTopEdge(int j) { 
	  //Check if index is top edge
	  for(int i = 0; i < n; i++) {
		  if(i == j) {
			  return true;
		  }
	  }
	  return false;
  }
  
  /**
   * @brief Whether or not the given index is a bottom edge in the grid
   * @param j The index for which to check
   * @return A boolean value of true or false
   */
  public boolean isBottomEdge(int j) {
	  //Check if index is bottom edge
	  for(int i = n * m - 1; i >= n * m - n; i--) {
		  if(j == i) {
			  return true;
		  }
	  }
	  return false;
  }
  
  /**
   * @brief Gets the m dimension of the grid
   * @return Integer variable representing the dimension
   */
  public int getM() {
	  return m;
  }
  
  /**
   * @brief Get the n dimension of the grid 
   * @return Integer variable representing the dimension
   */
  public int getN() {
	  return n;
  }
  
  /**
   * @brief Updates the grid for new updated indexes of the agents
   */
  public void moveInGrid(int pLoc, int iLoc) {  
	  state.set(iLoc, getAgent(pLoc)); //Get agent from previous location and move it to new location
	  state.set(pLoc, new agent()); //Create a space at the previous location
	  
	  //Debug statement
	  if(config.debug == 1) {
		  System.out.println("Agent at " + iLoc + " has iLoc value of " + getAgent(iLoc).getI() + " and pLoc value of " + pLoc + " = " + getAgent(iLoc).getP());
	  }
  }
  
  

  /**
   * @brief Checks if someone has won the simulation. A win is considered if all coyote or all road-runner remains in the space 
   * @return Boolean of true or false
   */
  public boolean hasWon() {
	  grid g = grid.instantiate();
	  
	  //Debug statements
	  if(config.debug == 1) {
		  System.out.println("The coyote count is " + getCoyoteCount());
		  System.out.println("The road-runner count is " + getRoadrunnerCount());
	  }
	  
	  if(g.getRoadrunnerCount() == 0) { //If no road-runners are left
		  System.out.println("Coyote has won.");
		  return true;
	  }
	  
	  if(g.getCoyoteCount() == 0) { //If no coyotes are left
		  System.out.println("Roadrunner has won.");
		  return true;
	  }
	
	  return false;
  }
  
  /**
   * @brief Take a time step.
   * 		Iterates through the entire grid and invokes agent actions.
   */
  public void timeStep(int speed) {
	  grid g = grid.instantiate();
	  	while(!hasWon()) {
	  		for(int i = 0; i < getM() * getN(); i++) {
	  			g.displayGrid();
	  			g.getAgent(i).move();
	  			g.getAgent(i).breed();
		  
	  			//Go to sleep for 'speed' time
	  			try {
	  				Thread.sleep(speed);
	  			} catch (InterruptedException e) {
	  				e.printStackTrace();
	  			}
	  		}	  
	  
	  	}
  }
  
  /**
   * @brief Get number of road-runners
   * @return Integer of the number of road-runners
   */
  public int getRoadrunnerCount() {
	  int roadrunnerCount = 0;
	  grid g = grid.instantiate();
	  for(int i = 0; i < g.getM() * g.getN(); i++) {
		  if(g.getAgent(i).getDefn() == ROADRUNNER) {
			  roadrunnerCount++;
		  }
	  }
	  return roadrunnerCount;
  }
  
  /**
   * @brief Get the number of coyotes
   * @return The integer of the number of coyotes
   */
  public int getCoyoteCount() {
	  int coyoteCount = 0;
	  grid g = grid.instantiate();
	  for(int i = 0; i < g.getM() * g.getN(); i++) {
		  if(g.getAgent(i).getDefn() == COYOTE) {
			  coyoteCount++;
		  }
	  }
	  return coyoteCount;
  }
  
  private boolean hasGridChanged() {
	  grid g = grid.instantiate();
	  //Check if anything has been updated in the grid by comparing previous locations to current locations 
	  for(int i = 0; i < m * n; i++) {
		  if(i != g.getAgent(i).getI()) {
			  return true;
		  }
	  }
	  return false;
  }
}
