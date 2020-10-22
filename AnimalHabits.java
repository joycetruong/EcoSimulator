/**
 * [ AnimalHabits.java ]
 * This contains all of the actions that any animal do for them to implement, aside from movement
 * Created By: Joyce Truong
 * 25 November 2017
 */

interface AnimalHabits {
  
  /**
   * loseHealthPerTurn method
   * This method allows all animals to lose health each turn
   */
  public void loseHealthPerTurn ();
  
  /**
   * reproduceHealthLost method
   * This method allows all animals to lose health when they reproduce
   */
  public void reproduceHealthLost (int LostHealth);
  
  /**
   * healed method
   * This method allows all animals to gain health when they eat
   */
  public void healed (int addHealth);
  
  /**
   * gainAge method
   * This method allows all animals to gain age each turn
   */
  public void gainAge ();
 
}