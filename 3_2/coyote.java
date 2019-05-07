import java.util.Random; //To decide a random position for the road-runner

/**
 * @brief Coyote class
 *        
 *        A template for a coyote.
 *        A coyote in this simulation inherits from agent and has a definition of 2.
 *             
 */
public class coyote extends agent {
  ///The default definition for an agent in this simulation in 0
  private static final int AGENT = config.AGENT;
  
  ///The default definition of a road-runner in this simulation is 1 
  private static final int ROADRUNNER = config.ROADRUNNER;
   
  ///The coyote will breed when it has taken BREEDTIME steps 
  private static final int BREEDTIME = config.breedTimeCoyote;
  
  ///Hunger tolerance
  private static final int HUNGER = config.hungerToleranceCoyote;
  
  ///The integer definition of the coyote 
  private static final int COYOTE = config.COYOTE; 
  
  ///The index location of the coyote 
  private int iLoc; 
  
  ///Holds the previous location of the coyote
  private int pLoc = iLoc;
  
  ///Variable to keep track of the steps taken
  private int timeStep = 0;
  
  ///The number of road-runners the coyote has eaten
  private int roadrunnerCount = 0;
  
  /**
   * @brief Coyote constructor with parameters
   * @param iLoc The location of the coyote in the grid.
   */
  public coyote(int iLoc) {
    this.iLoc = iLoc;
  }

  /**
   * @brief Road-runner constructor without parameters. 
   * Reports error if invoked without the location.
   */
  public coyote() {
	  System.out.println("Constructor requires coyote location.");
  }
  
  /**
   * @brief Definition getter
   * @return Integer definition of the coyote
   */
  public int getDefn() {
    return COYOTE;
  }
  
  /**
   * @brief Gets the index for the coyote
   * @return The index of the coyote
   */
  public int getI() {
	  return iLoc;
  }
  
  /**
   * @brief What the coyote sees when it is looking to move around
   * @param i The location from which the vision takes place
   * @return The index of the first road-runner it sees
   */
  private int vision(int i) {
	  grid g = grid.instantiate(); //The singleton grid 
	  
	  if(!g.isLeftEdge(i)) { //Left vision is not required for the left edge
	  	if(g.getState(i - 1) == ROADRUNNER) { //Vision towards left
	  		return i - 1;
	  	}
	  }
	  
	  if(!g.isRightEdge(i)) { //Right vision is not required for the right edge 
	  	if(g.getState(i + 1) == ROADRUNNER) { //Vision towards right 
	  		return i + 1;
	  	}
	  }
	  
	  if(!g.isTopEdge(i)) { //Top vision is not required for the top edge
	  	if(g.getState(i - g.getN()) == ROADRUNNER) { //Up vision
	  		return i - g.getN();
	  	}
	  }
	
	  if(!g.isTopEdge(i) && !g.isRightEdge(i)) { //Up-right vision is not required for the top and right edge
	  	if(g.getState(i - g.getN() + 1) == ROADRUNNER) { //Up-right vision
	  		return i - g.getN() + 1;
	  	}
	  }
	  
	  if(!g.isTopEdge(i) && !g.isLeftEdge(i)) { //Up-left vision is not required for the top and left edge
	  	if(g.getState(i - g.getN() - 1) == ROADRUNNER) { //Up-left vision
	  		return i - g.getN() - 1;
	  	}
	  }
	  
	  if(!g.isBottomEdge(i)) { //Down vision is not required for the bottom edge
	  	if(g.getState(i + g.getN()) == ROADRUNNER) { //Down vision
	  		return i + g.getN();
	  	}
	  }
	  
	  if(!g.isBottomEdge(i) && !g.isRightEdge(i)) { //Down-right vision is not required for the bottom and right edge
	  	if(g.getState(i + g.getN() + 1) == ROADRUNNER) { //Down-right vision
	  		return i + g.getN() + 1;
	  	}
	  }
	  
	  if(!g.isBottomEdge(i) && !g.isLeftEdge(i)) { //Down-left vision is not required for the bottom and left edge
	  	if(g.getState(i + g.getN() - 1) == ROADRUNNER) { //Down-left vision
	  		return i + g.getN() - 1;
	  	}
	  }
	  return -1;
  }
 
  /**
   * @brief Function that helps the coyote decide where to move and move there
   */
  public void move() {
	grid g = grid.instantiate(); //The singleton grid instance
	int bestIndex = iLoc; //The bestIndex at the beginning is it's current position
	
	//If a road-runner is in an adjacent cell then the bestIndex is the index to that cell
	if(vision(iLoc) != -1) { 
		bestIndex = vision(iLoc);
		roadrunnerCount++;
	}
	
	//Else, try and make a random movement
	else {
		Random random = new Random();
		  int direction = random.nextInt(7);
		  
		  switch (direction) {
		  case 0: //Left movement
			  if(!g.isLeftEdge(iLoc)) { 
				  if(g.getState(iLoc - 1) != COYOTE) bestIndex = iLoc - 1; 
			  } 
			  break;
			  
		  case 1: //Right movement
			  if(!g.isRightEdge(iLoc)) { 
				  if(g.getState(iLoc + 1) != COYOTE) bestIndex = iLoc + 1; 
			  } 
			  break;
		  
		  case 2: //Up movement public class simulation extends grid {
			  if(!g.isTopEdge(iLoc)) { 
				  if(g.getState(iLoc - g.getN()) != COYOTE) bestIndex = iLoc - g.getN(); 
			  } 
			  break;
			  
		  case 3: //Up-right movement
			  if(!g.isTopEdge(iLoc) && !g.isRightEdge(iLoc)) { 
				  if(g.getState(iLoc - g.getN() + 1) != COYOTE) bestIndex = iLoc - g.getN() + 1; 
			  } 
			  break;
			
		  case 4: //Up-left movement
			  if(!g.isTopEdge(iLoc) && !g.isLeftEdge(iLoc)) { 
				  if(g.getState(iLoc - g.getN() - 1) != COYOTE) bestIndex = iLoc - g.getN() - 1; 
			  } 
			  break;
		  
		  case 5: //Down movement
			  if(!g.isBottomEdge(iLoc)) { 
				  if(g.getState(iLoc + g.getN()) != COYOTE)bestIndex = iLoc + g.getN(); 
			  } 
			  break;
			  
		  case 6: //Down-right movement
			  if(!g.isBottomEdge(iLoc) && !g.isRightEdge(iLoc)) { 
				  if(g.getState(iLoc + g.getN() + 1) != COYOTE) bestIndex = iLoc + g.getN() + 1; 
			  } 
			  break;
			  
		  case 7: //Down-left movement
			  if(!g.isBottomEdge(iLoc) && !g.isLeftEdge(iLoc)) { 
				  if(g.getState(iLoc + g.getN() - 1) != COYOTE) bestIndex = iLoc + g.getN() - 1; 
			  }
			  break;
		  }
		}
		
		//If a newer index was found 
		if(iLoc != bestIndex) { 
			pLoc = iLoc;
			iLoc = bestIndex; //Update the coyote index
			timeStep++; //Update the timeStep
			g.moveInGrid(pLoc, iLoc); //Update the grid
			
			///Print statement for debugging purposes
			if(config.debug == 1) {
				System.out.println("Coyote moved from " + pLoc + " moved to " + iLoc);
			}
		}
		 ///Print statement for debugging purposes
		if(config.debug == 1) {
			System.out.println("The timeStep for this coyote is now " + timeStep);
		}
  	}
  
  /**
   * @brief Handles the breeding action of the coyote
   */ 
  public void breed() {
	  grid g = grid.instantiate(); //The singleton grid instance
	  
	  //When it is BREEDTIME
	  if(timeStep >= BREEDTIME) {
		  int breedLocation = super.breedVision(iLoc);
		  if(breedLocation != -1) { //If a proper breedLocation was found 
			  g.setAgent(breedLocation, COYOTE);
			  timeStep = 0; //Change the timeStep back to 0
		  }
		  
		  //Debug statement
		  if(config.debug == 1) {
			  System.out.println("Bred at breedLocation: " + breedLocation);
		  }
	  }
  }
  
  /**
   * @brief Coyote dies if it hasn't eaten a road-runner in 4 time-steps
   */
  public void die() {
	  grid g = grid.instantiate(); //The singleton grid instance
	  if(timeStep == 4) { //If the timeStep is 4
		  if(roadrunnerCount == HUNGER) {
			  g.setAgent(iLoc, AGENT); //The coyote dies and a space is created
		  }
	  }
  }
  
  /**
   * @brief Gets the previous location of the road-runner
   */
  public int getP() {
	   return pLoc;
  }
}
