/**
 * [ EnergizingPlant.java ]
 * Initialise the energizing plant object, which gives back energy to sheep
 * Created By: Joyce Truong
 * 25 November 2017
 */

class EnergizingPlant extends Plant {
  
  // Energizing plant constructor with nutrition, x coordinate, y coordinate, and spawn rate
  EnergizingPlant (int nutrition, int x, int y, int spawnRate) { 
    super (nutrition, x, y, spawnRate/3);
  }

}