/**
 * [ Plant.java ]
 * Initialise the plant object, but is abstract because every plant has a specific type
 * Created By: Joyce Truong
 * 25 November 2017
 */

abstract class Plant extends Species{
  
  // Declare plant specific variables
  private int spawnRate;
  
  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  
  // Plant constructor with nutrition, x coordinate, y coordinate, and spawn rate
  Plant (int nutrition, int x, int y, int spawnRate) { 
    super (nutrition, x, y);
    this.spawnRate = spawnRate;
  }
  
  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  
  /**
   * getSpawnRate method
   * This method allows other classes to get the plant spawn rate
   * @return int, plant spawn rate
   */
  public int getSpawnRate(){
    return this.spawnRate;
  }
  
  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  
}