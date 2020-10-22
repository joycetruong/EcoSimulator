/**
 * [ Sheep.java ]
 * Initialise the sheep object
 * Created By: Joyce Truong
 * 25 November 2017
 */

class Sheep extends Animals {
  
  // Declare sheep specific variables
  private int gender;
  private int age;
  private boolean moved;
  
  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  
  // Sheep constructor with health, x coordinate, and y coordinate
  // For newborn sheep
  Sheep (int health, int x, int y) { 
    super (health, x, y);
    age = 0;
    gender = 1 + (int) (Math.random() * 2);
    this.moved = false;
  }
  
  // Sheep constructor with health, x coordinate, y coordinate, and age
  // For starting sheep
  Sheep (int health, int x, int y, int age) { 
    super (health, x, y);
    this.age = age;
    gender = 1 + (int) (Math.random() * 2);
    this.moved = false;
  }
  
  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  
  /**
   * healed method
   * This method allows for sheep to heal when they eat plants
   */
  public void healed (int addHealth) {
    this.setHealth (this.getHealth() + addHealth);
  }
  
  /**
   * poisoned method
   * This method allows for sheep to be damaged when they eat poisonous plants
   */
  public void poisoned (int loseHealth) {
    this.setHealth (this.getHealth() - loseHealth);
  }
  
  /**
   * energized method
   * This method allows for sheep to be energized when they eat energizing plants
   */
  public void energized (int energy) {
    this.setEnergy (this.getEnergy() + energy);
  }
  
  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  
  /**
   * getMoved method
   * This method allows sheep to move only once per turn
   * @return boolean, true if can still move, false if cannot move
   */
  public boolean getMoved () {
    return moved;
  }
  
  /**
   * setMove method
   * This method sets whether the sheep have moved or not
   * @param takes in boolean, true if can still move, false if cannot move
   */
  public void setMoved (boolean moved) {
    this.moved = moved;
  }
  
  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  
  /**
   * getAge method
   * This method allows other classes to get the age of sheep
   * @return int, sheep age
   */
  public int getAge () {
    return this.age;
  }
  
  /**
   * gainAge method
   * This method allows for sheep to age each turn
   */
  public void gainAge () {
    this.age += 1;
  }
  
  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  
  /**
   * getGender method
   * This method allows other classes to get the gender of sheep
   * @return int, sheep gender
   */
  public int getGender () {
    return this.gender;
  }
  
  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  
}