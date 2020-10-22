/**
 * [ Animals.java ]
 * Initialise all animals within the ecosystem, but is abstract because every animal has a specific type
 * Created By: Joyce Truong
 * 25 November 2017
 */

abstract class Animals extends Species implements AnimalHabits, Moveable {
  
  // Declare animal specific variables
  private int energy = 15;
  
  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  
  // Animals constructor with health, x coordinate, and y coordinate
  Animals (int health, int x, int y) {
    super (health, x, y);
    this.energy = 15;
  }
    
  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  
  /**
   * getEnergy method
   * This method allows for the animals' energies to be accessed
   * @return boolean, true if can still move, false if cannot move
   */
  public int getEnergy () {
    return energy;
  }
  
  /**
   * setEnergy method
   * This method allows the animals' energies to be set
   * @param takes in boolean, true if can still move, false if cannot move
   */
  public void setEnergy (int Energy) {
    this.energy = energy;
  }
  
  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  
  /**
   * loseHealthPerTurn method
   * This method allows for animals to lose health each turn
   */
  public void loseHealthPerTurn () {
    this.setHealth(this.getHealth() - 1);
  }
  
  /**
   * reproduceHealthLost method
   * This method allows for animals to lose health to reproduce
   */
  public void reproduceHealthLost (int lostHealth) {
    this.setHealth (this.getHealth() - lostHealth);
  }
  
  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  
  /**
   * up method
   * This method allows animals to move upwards
   */
  public void up () {
    this.setY(this.getY() + 1);
  }
  
  /**
   * down method
   * This method allows animals to move downwards
   */
  public void down () {
    this.setY(this.getY() - 1);
  }
  
  /**
   * left method
   * This method allows animals to move to the left
   */
  public void left () {
    this.setX(this.getX() + 1);
  } 
  
  /**
   * right method
   * This method allows animals to move to the right
   */
  public void right () {
    this.setX(this.getX() - 1);
  }
  
  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  
}