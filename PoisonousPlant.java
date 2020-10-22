/**
 * [ PoisonousPlant.java ]
 * Initialise the poisonous plant object, which gives makes sheep lose health
 * Created By: Joyce Truong
 * 25 November 2017
 */

class PoisonousPlant extends Plant {
  
  // Poisonous plant constructor with nutrition, x coordinate, y coordinate, and spawn rate
  PoisonousPlant (int nutrition, int x, int y, int spawnRate) { 
    super (nutrition, x, y, spawnRate/3);
  }
  
}