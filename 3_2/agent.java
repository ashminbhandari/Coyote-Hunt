/**
 * @brief Agent class
 *        
 *        A template for agent.
 *        An agent in this simulation has a definition of 0 which is equivalent to a space in the grid.
 *             
 */
public class agent { 
  ///An integer variable that defines the agent gotten from the configuration file 
  private static final int AGENT = config.AGENT; 
  
  ///Holds the location of the agent in the grid 
  private int iLoc;
  
  ///Holds the previous location of the agent in the grid 
  private int pLoc = iLoc;
  
  /**
   * @brief Agent constructor without parameters
   * Agents created this way are considered spaces.
   */
  public agent() {
	  
  }
 
  /**
   * @brief Agent constructor with parameters. The agent will be defined as the parameter provided to this constructor.
   * @param d The agent will be defined as d. 
   * 
   * Note: Use of a custom definition requires manually changing configuration values. Use not advised.
   */
  public agent(int iLoc) {
    this.iLoc = iLoc;
  }

  /**
   * @brief Definition getter
   * @return Integer definition of the agent 
   */
  public int getDefn() {
    return AGENT;
  }

  /**
   * @brief Gets the index of the agent 
   * @return Integer index of the agent
   */
  public int getI() {
	  return iLoc;
  }

  /**
   * @brief Handles the movement of the agent.
   * 		For the agent class this function does nothing because we consider undefined agents to be spaces in this simulation.\
   * 		Function is defined for children.
   */
  public void move () {};
  
  /**
   * @brief Handles the breeding process of the agent
   * 		For the agent class this function does nothing because we consider undefined agents to be spaces in this simulation.
   * 		Function is defined for children.
   */
  public void breed() {}
  
  /**
   * @brief Handles the death of the agent
   * 		For the agent class this function does nothing because we consider undefined agents to be spaces in this simulation.
   * 		Function is defined for children.
   */
  public void die () {}
  
  /**
   * @brief Handles the movement of the agent
   * 		For the agent class this function does nothing because we consider undefined agents to be spaces in this simulation.
   * 		Function is defined for children.
   * @return A space (agent) has no vision so returns -1
   */
  private int vision(int i) { return -1; }
  
  /**
   * @brief Gets the previous location of the road-runner
   */
  public int getP() {
	   return pLoc;
  } 
  
  /**
   * @brief Function that helps the agent make a decision on where to breed 
   * @return void
   */ 
  public int breedVision(int i) {
	  grid g = grid.instantiate(); //The singleton grid 
	  
	  //Look around for a breeding place
	  if(!g.isLeftEdge(i)) { //Left vision is not required for the left edge
	  	if(g.getState(i - 1) == AGENT) { //Vision towards left. Returns index only if that index contains space (AGENT).
	  		return i - 1;
	  	}
	  }
	  
	  if(!g.isRightEdge(i)) { //Right vision is not required for the right edge 
	  	if(g.getState(i + 1) == AGENT) { //Vision towards right. Returns index only if that index contains space (AGENT). 
	  		return i + 1;
	  	}
	  }
	  
	  if(!g.isTopEdge(i)) { //Top vision is not required for the top edge
	  	if(g.getState(i - g.getN()) == AGENT) { //Up vision. Returns index only if that index contains space (AGENT). 
	  		return i - g.getN();
	  	}
	  }
	
	  if(!g.isTopEdge(i) && !g.isRightEdge(i)) { //Up-right vision is not required for the top and right edge
	  	if(g.getState(i - g.getN() + 1) == AGENT) { //Up-right vision. Returns index only if that index contains space (AGENT). 
	  		return i - g.getN() + 1;
	  	}
	  }
	  
	  if(!g.isTopEdge(i) && !g.isLeftEdge(i)) { //Up-left vision is not required for the top and left edge
	  	if(g.getState(i - g.getN() - 1) == AGENT) { //Up-left vision. Returns index only if that index contains space (AGENT). 
	  		return i - g.getN() - 1;
	  	}
	  }
	  
	  if(!g.isBottomEdge(i)) { //Down vision is not required for the bottom edge
	  	if(g.getState(i + g.getN()) == AGENT) { //Down vision. Returns index only if that index contains space (AGENT). 
	  		return i + g.getN();
	  	}
	  }
	  
	  if(!g.isBottomEdge(i) && !g.isRightEdge(i)) { //Down-right vision is not required for the bottom and right edge
	  	if(g.getState(i + g.getN() + 1) == AGENT) { //Down-right vision. Returns index only if that index contains space (AGENT). 
	  		return i + g.getN() + 1;
	  	}
	  }
	  
	  if(!g.isBottomEdge(i) && !g.isLeftEdge(i)) { //Down-left vision is not required for the bottom and left edge
	  	if(g.getState(i + g.getN() - 1) == AGENT) { //Down-left vision
	  		return i + g.getN() - 1;
	  	}
	  }
	  return -1; //Returns -1 if no breeding place was found
  }
}
