import java.lang.Math; //For Math.min function
import java.util.Random; //To decide a random position for the road-runner
  
/** 
 * 	@brief Road-runner class. 
 *  A template for a road-runner.
 *             
 */
public class roadrunner extends agent {
  private static final int AGENT = config.AGENT; ///Definition of an agent for this simulation is 0. 
  
  private static final int COYOTE = config.COYOTE; ///Definition of a coyote in this simulation is 2.
  
  private static final int BREEDTIME = config.breedTimeRoadrunner; ///A definition for the time when it is appropriate to breed.
  
  private static final int ROADRUNNER = config.ROADRUNNER; ///Definition of the road-runner.
  
  private int iLoc; ///Stores the index of the road-runner.
  
  private int pLoc = iLoc; ///Stores the previous index of the road-runner.
  
  private int timeStep = 0; ///Keeps track of the steps a road-runner has taken.
  
 
  /**
   * @brief Road-runner constructor with parameters
   * @param iLoc The location of the road-runner. 
   */
  public roadrunner(int iLoc) {
    this.iLoc = iLoc;
  }
  
  /**
   * @brief Road-runner constructor without parameters.
   * Reports error if invoked without the location.
   */
  public roadrunner() {
   System.out.println("Constructor requires road-runner location");
  }

  /**
   * @brief Definition getter
   * @return Integer definition of the road-runner 
   */
  public int getDefn() {
    return ROADRUNNER;
  }
  
  /**
   * @brief Gets the index of the agent 
   * @return Integer index of the agent
   */
  public int getI() {
	  return iLoc;
  }
  
  /**
   * @brief Coyote vision when it is about to move
   * @param i The index from which it looks around
   * @return The count of the coyotes it sees
   */
  private int moveVision(int i) {
	  grid g = grid.instantiate(); //Access singleton instance of the grid 
	  
	  int coyoteCount = 0; 
	  
	  ///Look around to detect coyotes
	  if(!g.isLeftEdge(i)) { //Left vision is not required for the left edge
	  	if(g.getState(i - 1) == COYOTE) { //Vision towards left
	  		coyoteCount++;
	  	}
	  }
	  
	  if(!g.isRightEdge(i)) { //Right vision is not required for the right edge 
	  	if(g.getState(i + 1) == COYOTE) { //Vision towards right 
	  		coyoteCount++;
	  	}
	  }
	  
	  if(!g.isTopEdge(i)) { //Top vision is not required for the top edge
	  	if(g.getState(i - g.getN()) == COYOTE) { //Up vision
	  		coyoteCount++;
	  	}
	  }
	  
	  if(!g.isTopEdge(i) && !g.isRightEdge(i)) { //Up-right vision is not required for the top and right edge
	  	if(g.getState(i - g.getN() + 1) == COYOTE) { //Up-right vision
	  		coyoteCount++;
	  	}
	  }
	  
	  if(!g.isTopEdge(i) && !g.isLeftEdge(i)) { //Up-left vision is not required for the top and left edge
	  	if(g.getState(i - g.getN() - 1) == COYOTE) { //Up-left vision
	  		coyoteCount++;
	  	}
	  }
	  
	  if(!g.isBottomEdge(i)) { //Down vision is not required for the bottom edge
	  	if(g.getState(i + g.getN()) == COYOTE) { //Down vision
	  		coyoteCount++;
	  	}
	  }
	  
	  if(!g.isBottomEdge(i) && !g.isRightEdge(i)) { //Down-right vision is not required for the bottom and right edge
	  	if(g.getState(i + g.getN() + 1) == COYOTE) { //Down-right vision
	  		coyoteCount++;
	  	}
	  }
	  
	  if(!g.isBottomEdge(i) && !g.isLeftEdge(i)) { //Down-left vision is not required for the bottom and left edge
	  	if(g.getState(i + g.getN() - 1) == COYOTE) { //Down-left vision
	  		coyoteCount++;
	  	}
	  }
	  
	  return coyoteCount; //Having added all the coyotes it sees, the count is returned
  }
  
  /**
   * @brief Function that helps the road-runner make a decision on where to move and move there
   * @return void
   */ 
  public void move() {
	  grid g = grid.instantiate(); //The singleton grid instance
	  int bestIndex = iLoc; //Best index for the road-runner to move, stays in position if no proper index was found  
	  int tmp = Integer.MAX_VALUE; //Maximum possible value for a integer, to compare to, to find minimum
	  int vision = moveVision(iLoc); //Number of coyotes seen from the current position
	  
	  ///Make a random one cell movement if no coyote was seen around
	  if(vision == 0) { 
		  Random random = new Random();
		  int direction = random.nextInt(7);
		  
		  //bestIndex will only be set if the location contains a space (AGENT)
		  switch (direction) {
		  case 0: //Left movement
			  if(!g.isLeftEdge(iLoc)) { 
				  if(g.getState(iLoc - 1) == AGENT) bestIndex = iLoc - 1; 
			  } 
			  break;
			  
		  case 1: //Right movement
			  if(!g.isRightEdge(iLoc)) { 
				  if(g.getState(iLoc + 1) == AGENT) bestIndex = iLoc + 1; 
			  } 
			  break;
		  
		  case 2: //Up movement
			  if(!g.isTopEdge(iLoc)) { 
				  if(g.getState(iLoc - g.getN()) == AGENT) bestIndex = iLoc - g.getN(); 
			  } 
			  break;
			  
		  case 3: //Up-right movement
			  if(!g.isTopEdge(iLoc) && !g.isRightEdge(iLoc)) { 
				  if(g.getState(iLoc - g.getN() + 1) == AGENT) bestIndex = iLoc - g.getN() + 1; 
			  } 
			  break;
			
		  case 4: //Up-left movement
			  if(!g.isTopEdge(iLoc) && !g.isLeftEdge(iLoc)) { 
				  if(g.getState(iLoc - g.getN() - 1) == AGENT) bestIndex = iLoc - g.getN() - 1; 
			  } 
			  break;
		  
		  case 5: //Down movement
			  if(!g.isBottomEdge(iLoc)) { 
				  if(g.getState(iLoc + g.getN()) == AGENT) bestIndex = iLoc + g.getN(); 
			  } 
			  break;
			  
		  case 6: //Down-right movement
			  if(!g.isBottomEdge(iLoc) && !g.isRightEdge(iLoc)) { 
				  if(g.getState(iLoc + g.getN() + 1) == AGENT) bestIndex = iLoc + g.getN() + 1; 
			  } 
			  break;
			  
		  case 7: //Down-left movement
			  if(!g.isBottomEdge(iLoc) && !g.isLeftEdge(iLoc)) { 
				  if(g.getState(iLoc + g.getN() - 1) == AGENT) bestIndex = iLoc + g.getN() - 1; 
			  }
			  break;
		  }
	  }
	  ///If a coyote is seen, decide the best possible location to move to 
	  else {
		  if(!g.isLeftEdge(iLoc)) { ///Left movement is not required for the left edge
			  ///Look left to see if a move there is appropriate
			  vision = moveVision(iLoc - 1); 
			  tmp = Math.min(tmp, vision); 
			  if(tmp == vision) {
				  if(g.getState(iLoc - 1) == AGENT) bestIndex = iLoc - 1; //Set bestIndex only if that index contains a space (AGENT)
			  }
		  }
	  
		  if(!g.isRightEdge(iLoc)) { ///Right movement is not required for the right edge
			  ///Look right to see if a move there is more appropriate
			  vision = moveVision(iLoc + 1);
			  tmp = Math.min(tmp,vision);
			  if(tmp == vision) {
				  if(g.getState(iLoc + 1) == AGENT) bestIndex = iLoc + 1; //Set bestIndex only if that index contains a space (AGENT)
			  }
		  }
	  
		  if(!g.isTopEdge(iLoc)) { ///Up movement is not required for the top edge 
			  ///Look up to see if a move there is more appropriate
			  vision = moveVision(iLoc - g.getN());
			  tmp = Math.min(tmp,vision);
			  if(tmp == vision) {
				  if(g.getState(iLoc - g.getN()) == AGENT) bestIndex = iLoc - g.getN(); //Set bestIndex only if that index contains a space (AGENT) 
			  }
		  }
	  
		  if(!g.isTopEdge(iLoc) && !g.isRightEdge(iLoc)) { ///Up-right movement is not required for the top-right edge
			  ///Look up-right to see if a move there is more appropriate
			  vision = moveVision(iLoc - g.getN() + 1);
			  tmp = Math.min(tmp,vision);
			  if(tmp == vision) {
				  if(g.getState(iLoc - g.getN() + 1) == AGENT) bestIndex = iLoc - g.getN() + 1; //Set bestIndex only if that index contains a space (AGENT) 
			  }
		  }
	  
		  if(!g.isTopEdge(iLoc) && !g.isLeftEdge(iLoc)) { ///Up-left movement is not required for the top-left edge
			  ///Look up-left to see if a move there is more appropriate
			  vision = moveVision(iLoc - g.getN() - 1);
			  tmp = Math.min(tmp,vision);
			  if(tmp == vision) {
				  if(g.getState(iLoc - g.getN() - 1) == AGENT) bestIndex = iLoc - g.getN() - 1; //Set bestIndex only if that index contains a space (AGENT)
			  }
		  }
	  
		  if(!g.isBottomEdge(iLoc)) { ///Down movement is not required for the bottom edge
			  ///Look down to see if a move there is more appropriate
			  vision = moveVision(iLoc + g.getN());
			  tmp = Math.min(tmp,vision);
			  if(tmp == vision) {
				  if(g.getState(iLoc + g.getN()) == AGENT) bestIndex = iLoc + g.getN(); //Set bestIndex only if that index contains a space (AGENT)
			  }
		  }
	  
		  if(!g.isBottomEdge(iLoc) && !g.isRightEdge(iLoc)) { ///Down-right movement is not required for the bottom-right edge
			  ///Look down-right to see if a move there is more appropriate
			  vision = moveVision(iLoc + g.getN() + 1);
			  tmp = Math.min(tmp,vision);
			  if(tmp == vision) {
				  if(g.getState(iLoc + g.getN() + 1) == AGENT) bestIndex = iLoc + g.getN() + 1; //Set bestIndex only if that index contains a space (AGENT) 
			  }
		  }
	  
		  if(!g.isBottomEdge(iLoc) && !g.isLeftEdge(iLoc)) { ///Down-left movement is not required for the bottom-left edge
			  ///Look down-left to see if a move there is more appropriate
			  vision = moveVision(iLoc + g.getN() - 1);
			  tmp = Math.min(tmp,vision);
			  if(tmp == vision) {
				  if(g.getState(iLoc + g.getN() - 1) == AGENT) bestIndex = iLoc + g.getN() - 1; //Set bestIndex only if that index contains a space (AGENT)  
			  }
		  }
	  }
	  
	  ///If an appropriate move is found
	  if(iLoc != bestIndex) {
		  pLoc = iLoc;
		  iLoc = bestIndex; ///Update the road-runner's index
		  timeStep++; ///Update the timeStep for the road-runner
		  g.moveInGrid(pLoc, iLoc); //Update the grid 
		  
		  ///Print statement for debugging purposes
		  if(config.debug == 1) {
		  System.out.println("Road-runner moved from " + pLoc + " moved to " + iLoc);
		  }
	  }
	  
	  //Print statement for debugging purposes
	  if(config.debug == 1) {
		  System.out.println("The timeStep for this road-runner is: " + timeStep);
	  }
  }

  /**
   * @brief Handles the breeding action of the road-runner
   */
  public void breed() {
	  grid g = grid.instantiate(); //The singleton grid
	  
	  //If it is breeding time then breed at the location provided by breedVision
	  if(timeStep >= BREEDTIME) {
		  int breedLocation = super.breedVision(iLoc); //Coyote and road-runner both have the same breedVision
		  if(breedLocation != -1) { //Check if breeding location was found
			  g.setAgent(breedLocation, ROADRUNNER); //Sets a coyote at breedLocation in the grid
			  timeStep = 0; //Reset the timeStep
		  }
	   }
  }
  
  /**
   * @brief Kills a road-runner
   * NOTE: This function is only useful for manual kills.
   */
  public void die() {
	  grid g = grid.instantiate(); //The singleton grid
	  g.setAgent(iLoc, AGENT); //Death means setting the location as a space
  }

   /**
    * @brief Gets the previous location of the road-runner
    */
   public int getP() {
	   return pLoc;
   }
}
