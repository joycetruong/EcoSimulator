/**
 * [ Wolf.java ]
 * Initialise the wolf object
 * Created By: Joyce Truong
 * 25 November 2017
 */

class Wolf extends Animals {
  
  // Declare wolf specific variables
  private int gender;
  private int age;
  private boolean moved;
  
  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  
  // Wolf constructor with health, x coordinate, and y coordinate
  // For newborn wolves
  Wolf (int health, int x, int y) { 
    super (health, x, y);
    age = 0;
    gender = 1 + (int) (Math.random() * 2);
    this.moved = false;
  }
  
  // Wolf constructor with health, x coordinate, y coordinate, and age
  // For starting wolves
  Wolf (int health, int x, int y, int age) { 
    super (health, x, y);
    this.age = age;
    gender = 1 + (int) (Math.random() * 2);
    this.moved = false;
  }
  
  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  
  /**
   * healed method
   * This method allows for wolves to heal when they eat sheep
   */
  public void healed (int addHealth) {
    this.setHealth (this.getHealth() + addHealth);
  }
  
  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  
  /**
   * getMoved method
   * This method allows wolves to move only once per turn
   * @return boolean, true if can still move, false if cannot move
   */
  public boolean getMoved () {
    return moved;
  }
  
  /**
   * setMove method
   * This method sets whether the wolves have moved or not
   * @param takes in boolean, true if can still move, false if cannot move
   */
  public void setMoved (boolean moved) {
    this.moved = moved;
  }
  
  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  
  /**
   * getAge method
   * This method allows other classes to get the age of wolves
   * @return int, wolves age
   */
  public int getAge () {
    return this.age;
  }
  
  /**
   * gainAge method
   * This method allows for wolves to age each turn
   */
  public void gainAge () {
    this.age += 1;
  }
  
  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  
  /**
   * getGender method
   * This method allows other classes to get the gender of wolves
   * @return int, wolves gender
   */
  public int getGender () {
    return this.gender;
  }
  
  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  
  /**
   * damage method
   * This method makes wolves lose health for fighting
   */
  public void damage (int lostHealth) {
    this.setHealth (this.getHealth() - lostHealth);
  }
  
  /**
   * compareTo method
   * This method compares wolves' healths to see which loses health
   */
  public int compareTo (Wolf wolf) {
    if (this.getHealth() > ((Wolf)(wolf)).getHealth()) {
      return 1;
    } else if (this.getHealth() < ((Wolf)(wolf)).getHealth()) {
      return -1;
    } else {
      return 0;
    }
  }
  
  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
 
}