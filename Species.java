/**
 * [ Species.java ]
 * Initialise all species within the ecosystem, but is abstract because every species has a specific type
 * Created By: Joyce Truong
 * 25 November 2017
 */

abstract class Species {
  
  // Declare species variables
  private int health;
  private int x, y;
  
  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  
  // Species constructor with health, x coordinate, and y coordinate
  Species (int health, int x, int y) { 
    this.health = health;
    this.x = x;
    this.y = y;
  }
  
  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  
  /**
   * getHealth method
   * This method allows other classes to get the health of the species
   * @return int, species health
   */
  public int getHealth () {
    return this.health;
  }
  
  /**
   * setHealth method
   * This method for this class to store the species health
   * @param takes in the health of the species
   */
  public void setHealth(int health) {
   this.health = health;
  }
  
  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  
  /**
   * getX method
   * This method allows other classes to get the x coordinate of the species
   * @return int, species x coordinate
   */
  public int getX() {
    return this.x;
  }
  
  /**
   * getY method
   * This method allows other classes to get the y coordinate of the species
   * @return int, species y coordinate
   */
  public int getY() {
    return this.y;
  }
    
  /**
   * setX method
   * This method for this class to store the species x coordinate
   * @param takes in the x coordinate of the species
   */
  public void setX(int x) {
    this.x = x;
  }
  
  /**
   * setY method
   * This method for this class to store the species y coordinate
   * @param takes in the y coordinate of the species
   */
  public void setY(int y) {
   this.y = y;
  } 
  
  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  
}