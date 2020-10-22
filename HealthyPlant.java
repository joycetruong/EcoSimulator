/**
 * [ HealthyPlant.java ]
 * Initialise the healthy plant object, which gives back health to sheep
 * Created By: Joyce Truong
 * 25 November 2017
 */

class HealthyPlant extends Plant {
  
  // Healthy plant constructor with nutrition, x coordinate, y coordinate, and spawn rate
  HealthyPlant (int nutrition, int x, int y, int spawnRate) { 
    super (nutrition, x, y, spawnRate/3);
  }
  
}