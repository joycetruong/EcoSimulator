/**
 * [ EcoSystemSim.java ]
 * Main function that contains an ecosystem imitation with various lifeforms
 * THIS IS THE EDITED VERSION
 * Old version accdientally would move 1 space off screen, (map.length - 1) changed to (map.length - 2)
 * Created By: Joyce Truong
 * 25 November 2017
 */

// Import java utilities for user input 
import javax.swing.*;

class EcoSystemSim {
  
  // Declares map for class methods to use
  private static Species [][] map;
  
  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  
  /**
   * main method
   * This method runs the entire simulation
   * @param takes in string arguments
   */
  public static void main (String [] args) {
    
    // Welcome message
    JOptionPane.showMessageDialog(null, "Welcome to the EcoSystem Simulation!");
    
    // Declare variables for getting user input and for the program to use
    boolean customize = false;
    String answer; // Get user input as a string
    int row, column;
    int plantSpawnRate;
    int plantHealth, sheepHealth, wolfHealth;
    int numSheepStart, numWolfStart, numPlantStart;
    int numTurns; // Keeps track of the number of turns
    int newX, newY; // Updates animal positions
    int [] keepRunning = new int [] {0, 0, 0}; // Keeping track of animal population to determine when the program should end
    
    // Get whether the user wants a custom map or not
    int customizable = JOptionPane.showConfirmDialog(null, "Would you like to customize the map?");
    if (customizable == 0) {
      customize = true;
    }
    
    // Get user inputs for a custom map
    if (customize == true) {
      answer = JOptionPane.showInputDialog("Number of rows and columns (side length, it will be a square): ");
      row = Integer.parseInt(answer);
      column = row;
      answer = JOptionPane.showInputDialog("Plant spawn rate (plants/turn): ");
      plantSpawnRate = Integer.parseInt(answer);
      
      answer = JOptionPane.showInputDialog("Sheep health: ");
      sheepHealth = Integer.parseInt(answer);
      answer = JOptionPane.showInputDialog("Wolf health: ");
      wolfHealth = Integer.parseInt(answer);
      answer = JOptionPane.showInputDialog("Plant nutrition: ");
      plantHealth = Integer.parseInt(answer);
      
      answer = JOptionPane.showInputDialog("Beginning number of sheep: ");
      numSheepStart = Integer.parseInt(answer);
      answer = JOptionPane.showInputDialog("Beginning number of wolves: ");
      numWolfStart = Integer.parseInt(answer);
      answer = JOptionPane.showInputDialog("Beginning number of plants: ");
      numPlantStart = Integer.parseInt(answer);
      
      // Initialise inputs for a non-custom map
    } else {
      row = 25;
      column = 25;
      plantSpawnRate = 3;
      plantHealth = 15;
      sheepHealth = 40;
      wolfHealth = 60;
      numPlantStart = 100;
      numSheepStart = 120;
      numWolfStart = 20;
    }
    
    // Initialise new map with the dimensions given 
    map = new Species [row][column];
    
    // Make the initial grid
    makeGrid(map, numSheepStart, numWolfStart, numPlantStart, sheepHealth, plantHealth, wolfHealth, plantSpawnRate);
    DisplayGrid grid = new DisplayGrid(map);
    grid.refresh();
    
    // Get whether the user is ready or not to load in objects
    int ready = JOptionPane.showConfirmDialog(null, "Are you ready?");
    
    // While user is ready
    if (ready == 0) {
      
      numTurns = 0;
      
      // Run the simulation, updating every second
      do {     
        try{ Thread.sleep(1000); }catch(Exception e) {};
        
        // Update the map each second with all actions
        EcoSystemSim.updateMap(map, plantHealth, sheepHealth, wolfHealth, plantSpawnRate); // Update grid each turn
        
        keepRunning = population(); // Check for the populations
        
        // If board is getting too full, kill off species
        if ((keepRunning[0] + keepRunning[1] + keepRunning[2]) >= ((row * column) - 5)) {
          
          // Count 5 to kill off species
          for (int j = 0; j <= 5; j++) {
            
            // Generate random coordinates
            int y = (int)(Math.random() * map[0].length);
            int x = (int)(Math.random() * map.length);
            
            // Kill species
            map[y][x] = null;
            
          }
        }
        
        numTurns += keepRunning[3]; // Updates number of turns
        
        grid.refresh(); // Refresh each turn
        
      } while ((keepRunning[0] != 0) && (keepRunning[1] != 0) && (keepRunning[2] != 0)); // Check for whether any population is extinct
      
      // Output final populations and number of turns
      JOptionPane.showMessageDialog(null, "The number of plants left are: " + keepRunning[0]);
      JOptionPane.showMessageDialog(null, "The number of sheep left are: " + keepRunning[1]);
      JOptionPane.showMessageDialog(null, "The number of wolves left are: " + keepRunning[2]);
      JOptionPane.showMessageDialog(null, "The number of turns taken is: " + numTurns);
    }

  }
  
  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  
  /**
   * makeGrid method
   * This method runs the entire simulation
   * @param takes in the map, the number of initial animals each, the health set for each animal, and the plant spawn rate
   */
  public static void makeGrid (Species map[][], int numSheepStart, int numWolfStart, int numPlantStart, int sheepHealth, int plantHealth, int wolfHealth, int plantSpawnRate) {
    
    // Declare coordinates to randomly spawn species
    int y = 0;
    int x = 0;
    
    // Initialise plants
    Plant plant;
    
    // Spawn plants
    for (int i = 0; i < numPlantStart; i++) {
      
      // Choose random coordinates to spawn
      y = (int)(Math.random() * map[0].length); 
      x = (int)(Math.random() * map.length);
      
      // Checks for an empty space
      if (map[y][x] == null) { 
        
        // Choose a random plant (50% chance healthy, 25% poisonous or energizing)
        int plantChoice = (int) Math.ceil(Math.random() * 100);
        
        // Create the new plants
        if ((plantChoice <= 100) && (plantChoice >= 50)) {
          plant = new HealthyPlant(plantHealth, x, y, plantSpawnRate);
        } else if ((plantChoice < 50) && (plantChoice >= 25)) {
          plant = new EnergizingPlant(plantHealth, x, y, plantSpawnRate);
        } else {
          plant = new PoisonousPlant(plantHealth, x, y, plantSpawnRate);
        }
        
        // Set plant coordinates
        plant.setX(x);
        plant.setY(y);
        map[y][x] = plant;
        
        // No space
      } else { 
        i -= 1;
      }
      
    }
    
    // Spawn sheep
    for (int i = 0; i < numSheepStart; i++) { 
      
      // Choose random coordinates to spawn
      y = (int)(Math.random() * map[0].length); 
      x = (int)(Math.random() * map.length);
      
      // Checks for an empty space
      if (map[y][x] == null) {
        
        // Create new sheep
        Sheep sheep = new Sheep(sheepHealth, x, y, 5);  
        
        // Set sheep coordinates
        sheep.setX(x);
        sheep.setY(y);
        map[y][x] = sheep;
        
        // No space
      } else { 
        i -= 1; 
      }
      
    }
    
    // Spawn wolves
    for (int i = 0; i < numWolfStart; i++) { 
      
      // Choose random coordinates to spawn
      y = (int)(Math.random() * map[0].length); 
      x = (int)(Math.random() * map.length);
      
      // Checks for an empty space
      if (map[y][x] == null) { 
        
        // Create new wolf
        Wolf wolf = new Wolf(wolfHealth, x, y, 5); 
        
        // Set wolf coordinates
        wolf.setX(x);
        wolf.setY(y);
        map[y][x] = wolf; 
        
        // No space
      } else { 
        i -= 1; 
      }
    }
    
  }
  
  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  
  /**
   * updateMap method
   * This method updates the map each turn
   * @param takes in the map, the health set for each animal, and the plant spawn rate
   */
  public static void updateMap (Species [][] map, int plantHealth, int sheepHealth, int wolfHealth, int plantSpawnRate) {
    
    // Age, reproduce, move, and spawn species
    ageAnimal (map); 
    reproduce (map, sheepHealth, wolfHealth, plantHealth);
    move (map, plantHealth, sheepHealth, wolfHealth);
    spawnPlant(map, plantSpawnRate, plantHealth);
    
    // Update map and reset animal interactions to allow for actions
    for(int y = 0; y < map[0].length; y++){
      for(int x = 0; x < map.length; x++) {
        if (map[y][x] instanceof Sheep) {
          ((Sheep)map[y][x]).setMoved(false);
        } else if (map[y][x] instanceof Wolf) {
          ((Wolf)map[y][x]).setMoved(false);
        }
      }
    }
    
  }
  
  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  
  /**
   * spawnPlant method
   * This method spawns plants each turn
   * @param takes in the map, the plant spawn rate, and the health set for plants
   */
  public static void spawnPlant (Species map[][], int plantSpawnRate, int plantHealth) {
    
    // Declare random coordinates
    int y, x; 
    
    // Declare new plant
    Plant plant;
    
    // Spawn plants randomly until spawn rate is reached per turn
    for (int i = 0; i < plantSpawnRate; i++) {
      
      // Generate random coordinates
      y = (int)(Math.random() * map[0].length);
      x = (int)(Math.random() * map.length);
      
      // Check if space is empty and available to spawn
      if (map[y][x] == null) { 
        
        // Get plant choice
        int plantChoice = (int) Math.ceil(Math.random() * 100);
        
        // Spawn different types of plants
        if ((plantChoice <= 100) && (plantChoice >= 50)) {
          plant = new HealthyPlant(plantHealth, x, y, plantSpawnRate);
        } else if ((plantChoice < 50) && (plantChoice >= 25)) {
          plant = new EnergizingPlant(plantHealth, x, y, plantSpawnRate);
        } else {
          plant = new PoisonousPlant(plantHealth, x, y, plantSpawnRate);
        }
        
        // Set plant location
        plant.setX(x);
        plant.setY(y);
        map[y][x] = plant;
        
      // No space
      } else {
        i -= 1;
      }
    }
    
  }
  
  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  
  /**
   * moveDecision method
   * This method decides what move to make
   * @param takes in the map, the x and y coordinates, and the health set for plants
   * @return int, movement choice
   */
  public static int [] moveDecision (Species [][] map, int x , int y, int plantHealth) {
    
    // Directions: Up = 1, Down = 2, Left = 3, Right = 4
    // Last choice is random movement
    // Animals: sheep = 0, wolves = 1
    int [] directionChoice = new int [] {1 + (int)(Math.random() * 4), 1 + (int)(Math.random() * 4)};
    
    // Find any animals
    if ((map[y][x] != null) && (!(map[y][x] instanceof Plant))) {
      
      // Sheep decisions (sheep cannot decide whether or not to move away from wolves)
      if (map[y][x] instanceof Sheep) {
        
        // First choice is to eat
        if ((y > 0) && (map[y-1][x] instanceof Plant)) {
          directionChoice[0] = 1;
        } else if ((y < map[0].length - 2) && (map[y+1][x] instanceof Plant)) {
          directionChoice[0] = 2;
        } else if ((x > 0) &&(map[y][x-1] instanceof Plant)) {
          directionChoice[0] = 3;
        } else if ((x < map.length - 2) && (map[y][x+1] instanceof Plant)) {
          directionChoice[0] = 4;
          
          // Wolf decisions
        } else if (map[y][x] instanceof Wolf) {
          
          // First choice is to eat
          if ((y > 0) && (map[y-1][x] instanceof Sheep)) {
            directionChoice[1] = 1;
          } else if ((y < map[0].length - 2) && (map[y+1][x] instanceof Sheep)) {
            directionChoice[1] = 2;
          } else if ((x > 0) && (map[y][x-1] instanceof Sheep)) {
            directionChoice[1] = 3;
          } else if ((x < map.length - 2) && (map[y][x+1] instanceof Sheep)) {
            directionChoice[1] = 4;
            
            // Second choice is to fight a weaker wolf
          } else if ((y > 0) && (map[y-1][x] instanceof Wolf)) {
            directionChoice[1] = 1;
          } else if ((y < map[0].length - 2) && (map[y+1][x] instanceof Wolf)) {
            directionChoice[1] = 2;
          } else if ((x > 0) && (map[y][x-1] instanceof Wolf)) {
            directionChoice[1] = 3;
          } else if ((x < map.length - 2) && (map[y][x+1] instanceof Wolf)) {
            directionChoice[1] = 4;
          }
        }
        
      }
    }
    return directionChoice; // Return choice to allow animals to move
  }
  
  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  
  /**
   * moveDecision method
   * This method decides what move to make
   * @param takes in the map, the x and y coordinates, and the health set for plants
   * @return boolean array, true if action should be done, false if action should not be done
   */
  public static boolean [] action (Species [][] map, int x, int y, int plantHealth) {
    
    // Actions: sheep eat = actionChoice[0], wolves eat = actionChoice[1], fight = actionChoice[2]
    boolean [] actionChoice = new boolean [] {false, false, false};
    
    // Find any animals and see if they have enough energy
    if ((map[y][x] != null) && (!(map[y][x] instanceof Plant)) && (((Animals)map[y][x]).getEnergy() > 0) ) {
      
      // Make sure in bounds
      if ((y > 0) && (y < map[0].length - 2) && (x > 0) && (x < map.length - 2)) {
        
        // Sheep decisions (sheep cannot decide whether or not to move away from wolves)
        if (map[y][x] instanceof Sheep) {
          
          // First choice is to eat
          if ((map[y-1][x] instanceof Plant) || (map[y+1][x] instanceof Plant) || (map[y][x-1] instanceof Plant) || (map[y][x+1] instanceof Plant)) {
            actionChoice[0] = true; 
          }
          
          // Wolf decisions
        } else if (map[y][x] instanceof Wolf) {
          
          // First choice is to eat
          if ((map[y-1][x] instanceof Sheep) || (map[y+1][x] instanceof Sheep) || (map[y][x-1] instanceof Sheep) || (map[y][x+1] instanceof Sheep)) {
            actionChoice[1] = true;
            
            // Second choice is to fight a weaker wolf
          } else if ((map[y-1][x] instanceof Wolf) || (map[y+1][x] instanceof Wolf) || (map[y][x-1] instanceof Wolf) || (map[y][x+1] instanceof Wolf)) {
            actionChoice[2] = true;
          }
          
        }
      }
      
    }
    return actionChoice; // Returns what action the animal should do
  }
  
  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  
  /**
   * breedChoice method
   * This method decides which sheep to breed
   * @param takes in the map and the health set for plants
   * @return boolean array, true if action should be done, false if action should not be done
   */
  public static boolean [][] breedChoice (Species [][] map, int x, int y, int plantHealth) {
    // First int: direction
    // Second int: sheep or wolf
    boolean [][] breeding = {
                        {false, false},
                        {false, false},
                        {false, false},
                        {false, false},
                        };
    
    // Check null pointer exceptions
    if (map[y][x] != null) {

      // Breed sheep
      if ((map[y][x] instanceof Sheep) && (y > 0) && (map[y-1][x] instanceof Sheep)) {
        if ((((Sheep)map[y][x]).getGender() != ((Sheep)map[y-1][x]).getGender()) && (map[y][x].getHealth() > 20) && (map[y-1][x].getHealth() > 20) && (((Sheep)map[y][x]).getAge() > 5) && (((Sheep)map[y-1][x]).getAge() > 5)) {
          breeding[0][0] = true;
        }
      } else if ((map[y][x] instanceof Sheep) && (y < map[0].length - 2) && (map[y+1][x] instanceof Sheep)) {
        if ((((Sheep)map[y][x]).getGender() != ((Sheep)map[y+1][x]).getGender()) && (map[y][x].getHealth() > 20) && (map[y+1][x].getHealth() > 20) && (((Sheep)map[y][x]).getAge() > 5) && (((Sheep)map[y+1][x]).getAge() > 5)) {
          breeding[1][0] = true;
        }
      } else if ((map[y][x] instanceof Sheep) && (x > 0) && (map[y][x-1] instanceof Sheep)) {
        if ((((Sheep)map[y][x]).getGender() != ((Sheep)map[y][x-1]).getGender()) && (map[y][x].getHealth() > 20) && (map[y][x-1].getHealth() > 20) && (((Sheep)map[y][x]).getAge() > 5) && (((Sheep)map[y][x-1]).getAge() > 5)) {
          breeding[2][0] = true;
        }
      } else if ((map[y][x] instanceof Sheep) && (x < map.length - 2) && (map[y][x+1] instanceof Sheep)) {
        if ((((Sheep)map[y][x]).getGender() != ((Sheep)map[y][x+1]).getGender()) && (map[y][x].getHealth() > 20) && (map[y][x+1].getHealth() > 20) && (((Sheep)map[y][x]).getAge() > 5) && (((Sheep)map[y][x+1]).getAge() > 5)) {
          breeding[3][0] = true;
        }
     
      // Breed wolves
      } else if ((map[y][x] instanceof Wolf) && (y > 0) && (map[y-1][x] instanceof Wolf)) {
        if ((((Wolf)map[y][x]).getGender() != ((Wolf)map[y-1][x]).getGender()) && (map[y][x].getHealth() > 20) && (map[y-1][x].getHealth() > 20) && (((Wolf)map[y][x]).getAge() > 5) && (((Wolf)map[y-1][x]).getAge() > 5)) {
          breeding[0][1] = true;
        }
      } else if ((map[y][x] instanceof Wolf) && (y < map[0].length - 2) && (map[y+1][x] instanceof Wolf)) {
        if ((((Wolf)map[y][x]).getGender() != ((Wolf)map[y+1][x]).getGender()) && (map[y][x].getHealth() > 20) && (map[y+1][x].getHealth() > 20) && (((Wolf)map[y][x]).getAge() > 5) && (((Wolf)map[y+1][x]).getAge() > 5)) {
          breeding[1][1] = true;
        }
      } else if ((map[y][x] instanceof Wolf) && (x > 0) && (map[y][x-1] instanceof Wolf)) {
        if ((((Wolf)map[y][x]).getGender() != ((Wolf)map[y][x-1]).getGender()) && (map[y][x].getHealth() > 20) && (map[y][x-1].getHealth() > 20) && (((Wolf)map[y][x]).getAge() > 5) && (((Wolf)map[y][x-1]).getAge() > 5)) {
          breeding[2][1] = true;
        }
      } else if ((map[y][x] instanceof Wolf) && (x < map.length - 2) && (map[y][x+1] instanceof Wolf)) {
        if ((((Wolf)map[y][x]).getGender() != ((Wolf)map[y][x+1]).getGender()) && (map[y][x].getHealth() > 20) && (map[y][x+1].getHealth() > 20) && (((Wolf)map[y][x]).getAge() > 5) && (((Wolf)map[y][x+1]).getAge() > 5)) {
          breeding[3][1] = true;
        }
      }
      
    }
    return breeding;
  }
  
  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  
  /**
   * move method
   * This method decides what move to make
   * @param takes in the map and the health set for animals
   */
  public static void move (Species map[][], int plantHealth, int sheepHealth, int wolfHealth) {
    
    // Check map
    for (int y = 0; y < map[0].length; y++) {
      for (int x = 0; x < map.length; x++) {
        
        // Chooses an action to do (anything else does nothing)
        boolean [] actionChoice = action (map, x, y, plantHealth);
    
        // Avoid null pointers
        if (map[y][x] != null) {
          
          // Sheep
          // Check if sheep wants to move
          if ((map[y][x] instanceof Sheep) && (((Sheep)map[y][x]).getMoved() == false)) {
            ((Sheep)map[y][x]).loseHealthPerTurn();
            
            // If sheep run out of health, they die
            if ((map[y][x]).getHealth() < 1) {
              map[y][x] = null; 
              
              // If sheep have sufficient health, they move, stay still, eat, or breed
            } else {
              
              // Choose direction using specific preferences (anything else stays still)
              int [] direction = moveDecision(map, x, y, plantHealth);
              
              // Chose to move up
              if (direction[0] == 1) {
                
                // Check in bounds
                if (y > 0) {
                  
                  // Wants to eat a plant
                  if (actionChoice[0]) {
                    
                    // Depending on plant consumed, the effects vary
                    if (map[y-1][x] instanceof EnergizingPlant) {
                      ((Sheep)map[y][x]).energized(plantHealth);
                    } else if (map[y-1][x] instanceof PoisonousPlant) {
                      ((Sheep)map[y][x]).poisoned(plantHealth);
                    } else {
                      ((Sheep)map[y][x]).healed(plantHealth);
                    }
                    
                    // Update sheep position
                    ((Sheep)map[y][x]).setEnergy(((Sheep)map[y][x]).getEnergy()-1);
                    ((Sheep)map[y][x]).up();
                    ((Sheep)map[y][x]).setMoved(true);
                    map[y-1][x] = map[y][x];
                    map[y][x] = null;
                    
                    // Move to empty space
                  } else if ((y > 0) && (map[y-1][x] == null)) {
                    ((Sheep)map[y][x]).setEnergy(((Sheep)map[y][x]).getEnergy()-1);
                    ((Sheep)map[y][x]).up();
                    ((Sheep)map[y][x]).setMoved(true);
                    map[y-1][x] = map[y][x];
                    map[y][x] = null;
                  }
                }
                
                // Chose to move down
              } else if (direction[0] == 2) {
                
                //Check in bounds
                if (y < map[0].length - 2) {
                  
                  // Wants to eat a plant
                  if (actionChoice[0]) {
                    
                    // Depending on plant consumed, the effects vary
                    if (map[y+1][x] instanceof EnergizingPlant) {
                      ((Sheep)map[y][x]).energized(plantHealth);
                    } else if (map[y+1][x] instanceof PoisonousPlant) {
                      ((Sheep)map[y][x]).poisoned(plantHealth);
                    } else {
                      ((Sheep)map[y][x]).healed(plantHealth);
                    }
                    
                    // Update sheep position
                    ((Sheep)map[y][x]).setEnergy(((Sheep)map[y][x]).getEnergy()-1);
                    ((Sheep)map[y][x]).down();
                    ((Sheep)map[y][x]).setMoved(true);
                    map[y+1][x] = map[y][x];
                    map[y][x] = null;
                    
                    // Move to empty space
                  } else if ((y < (map.length - 2)) && (map[y+1][x] == null)) {
                    ((Sheep)map[y][x]).setEnergy(((Sheep)map[y][x]).getEnergy()-1);
                    ((Sheep)map[y][x]).down();
                    ((Sheep)map[y][x]).setMoved(true);
                    map[y+1][x] = map[y][x];
                    map[y][x] = null;
                  }
                }
                
                // Chose to move left
              } else if (direction[0] == 3) {
                
                // Check in bounds
                if (x > 0) {
                  
                  // Wants to eat a plant
                  if (actionChoice[0]) {
                    
                    // Depending on plant consumed, the effects vary
                    if (map[y][x-1] instanceof EnergizingPlant) {
                      ((Sheep)map[y][x]).energized(plantHealth);
                    } else if (map[y][x-1] instanceof PoisonousPlant) {
                      ((Sheep)map[y][x]).poisoned(plantHealth);
                    } else {
                      ((Sheep)map[y][x]).healed(plantHealth);
                    } 
                    
                    // Update sheep position
                    ((Sheep)map[y][x]).setEnergy(((Sheep)map[y][x]).getEnergy()-1);
                    ((Sheep)map[y][x]).left();
                    ((Sheep)map[y][x]).setMoved(true);
                    map[y][x-1] = map[y][x];
                    map[y][x] = null;
                    
                    // Move to empty space
                  } else if ((x > 0) && (map[y][x-1] == null)) {
                    ((Sheep)map[y][x]).setEnergy(((Sheep)map[y][x]).getEnergy()-1);
                    ((Sheep)map[y][x]).left();
                    ((Sheep)map[y][x]).setMoved(true);
                    map[y][x-1] = map[y][x];
                    map[y][x] = null;
                  }
                }
                
                // Chose to move right
              } else if (direction[0] == 4) {
                
                // Checks in bounds
                if (x < map.length - 2) {
                  
                  // Wants to eat a plant
                  if (actionChoice[0]) {
                    
                    // Depending on plant consumed, the effects vary
                    if (map[y][x+1] instanceof EnergizingPlant) {
                      ((Sheep)map[y][x]).energized(plantHealth);
                    } else if (map[y][x+1] instanceof PoisonousPlant) {
                      ((Sheep)map[y][x]).poisoned(plantHealth);
                    } else {
                      ((Sheep)map[y][x]).healed(plantHealth);
                    }
                    
                    // Update sheep position
                    ((Sheep)map[y][x]).setEnergy(((Sheep)map[y][x]).getEnergy()-1);
                    ((Sheep)map[y][x]).right();
                    ((Sheep)map[y][x]).setMoved(true);
                    map[y][x+1] = map[y][x];
                    map[y][x] = null;
                    
                    // Move to empty space
                  } else if ((x < (map.length - 2)) && (map[y][x+1] == null)) {
                    ((Sheep)map[y][x]).setEnergy(((Sheep)map[y][x]).getEnergy()-1);
                    ((Sheep)map[y][x]).right();
                    ((Sheep)map[y][x]).setMoved(true);
                    map[y][x+1] = map[y][x];
                    map[y][x] = null;
                  }
                  
                }
              }
              
            }
          }
          
          // WOLF
          // Check if wolves wants to move 
          if ((map[y][x] instanceof Wolf) && (((Wolf)map[y][x]).getMoved() == false)) { 
            int run = 0;
            ((Wolf)map[y][x]).loseHealthPerTurn();
            
            // If wolves run out of health, they die
            if (map[y][x].getHealth() < 1) {
              map[y][x] = null;
              
              // If wolves have sufficient health, they move, stay still, eat, breed, or fight
            } else {
              
              // Choose direction using specific preferences (anything else stays still)
              int [] direction = moveDecision (map, x, y, plantHealth);
              
              // Chose to move up
              if (direction[1] == 1) {
                
                // Checks in bounds
                if (y > 0) {
                  
                  // Wants to eat a sheep
                  if ((actionChoice[1]) && (map[y-1][x] instanceof Sheep)) {
                    
                    // Update healths
                    ((Wolf)map[y][x]).healed(((Sheep)map[y-1][x]).getHealth());
                    
                    // Update wolf position
                    ((Wolf)map[y][x]).setEnergy(((Wolf)map[y][x]).getEnergy()-1);
                    ((Wolf)map[y][x]).up();
                    ((Wolf)map[y][x]).setMoved(true);
                    map[y-1][x] = map[y][x];
                    map[y][x] = null;

                    // Wants to fight
                  } else if ((actionChoice[2]) && (map[y-1][x] instanceof Wolf)) {
                    
                    // Weaker wolf loses health; otherwise, nothing happens
                    if (((Wolf)map[y][x]).compareTo((Wolf)map[y-1][x]) > 0) {
                      ((Wolf)map[y-1][x]).damage(10);
                    } else if (((Wolf)map[y][x]).compareTo((Wolf)map[y-1][x]) < 0) {
                      ((Wolf)map[y][x]).damage(10);
                    }
                    
                    // Move to empty space
                  } else if ((x < (map.length - 2)) && (map[y-1][x] == null)) {
                    ((Wolf)map[y][x]).setEnergy(((Wolf)map[y][x]).getEnergy()-1);
                    ((Wolf)map[y][x]).right();
                    ((Wolf)map[y][x]).setMoved(true);
                    map[y-1][x] = map[y][x];
                    map[y][x] = null;
                  }
                }
                
                // Chose to move down
              } else if (direction[1] == 2) {
                
                // Checks in bounds
                if (y < map[0].length - 2) {
                  
                  // Wants to eat a sheep
                  if ((actionChoice[1]) && (map[y+1][x] instanceof Sheep)) {
                    
                    // Update healths
                    ((Wolf)map[y][x]).healed(((Sheep)map[y+1][x]).getHealth());
                    
                    // Update wolf position
                    ((Wolf)map[y][x]).setEnergy(((Wolf)map[y][x]).getEnergy()-1);
                    ((Wolf)map[y][x]).up();
                    ((Wolf)map[y][x]).setMoved(true);
                    map[y+1][x] = map[y][x];
                    map[y][x] = null;
                    
                    // Wants to fight (does not move)
                  } else if ((actionChoice[2]) && (map[y+1][x] instanceof Wolf)) {
                    
                    // Weaker wolf loses health; otherwise, nothing happens
                    if (((Wolf)map[y][x]).compareTo((Wolf)map[y+1][x]) > 0) {
                      ((Wolf)map[y+1][x]).damage(10);
                    } else if (((Wolf)map[y][x]).compareTo((Wolf)map[y+1][x]) < 0) {
                      ((Wolf)map[y][x]).damage(10);
                    }
                    
                    // Move to empty space
                  } else if ((x < (map.length - 2)) && (map[y+1][x] == null)) {
                    ((Wolf)map[y][x]).setEnergy(((Wolf)map[y][x]).getEnergy()-1);
                    ((Wolf)map[y][x]).right();
                    ((Wolf)map[y][x]).setMoved(true);
                    map[y+1][x] = map[y][x];
                    map[y][x] = null;
                  }
                }
                
                // Chose to move left
              } else if (direction[1] == 3) {
                
                // Checks in bounds
                if (x > 0) {
                  
                  // Wants to eat a sheep
                  if ((actionChoice[1]) && (map[y][x-1] instanceof Sheep)) {
                    
                    // Update healths
                    ((Wolf)map[y][x]).healed(((Sheep)map[y][x-1]).getHealth());
                    
                    // Update wolf position
                    ((Wolf)map[y][x]).setEnergy(((Wolf)map[y][x]).getEnergy()-1);
                    ((Wolf)map[y][x]).up();
                    ((Wolf)map[y][x]).setMoved(true);
                    map[y][x-1] = map[y][x];
                    map[y][x] = null;
                    
                    // Wants to fight (does not move)
                  } else if ((actionChoice[2]) && (map[y][x-1] instanceof Wolf)) {
                    
                    // Weaker wolf loses health; otherwise, nothing happens
                    if (((Wolf)map[y][x]).compareTo((Wolf)map[y][x-1]) > 0) {
                      ((Wolf)map[y][x-1]).damage(10);
                    } else if (((Wolf)map[y][x]).compareTo((Wolf)map[y][x-1]) < 0) {
                      ((Wolf)map[y][x]).damage(10);
                    }
                    
                    // Move to empty space
                  } else if ((x < (map.length - 2)) && (map[y][x-1] == null)) {
                    ((Wolf)map[y][x]).setEnergy(((Wolf)map[y][x]).getEnergy()-1);
                    ((Wolf)map[y][x]).right();
                    ((Wolf)map[y][x]).setMoved(true);
                    map[y][x-1] = map[y][x];
                    map[y][x] = null;
                  }
                }
                
                // Chose to move right 
              } else if (direction[1] == 4) {
                
                // Checks in bounds
                if (x < map.length - 2) {
                  
                  // Wants to eat a sheep
                  if ((actionChoice[1]) && (map[y][x+1] instanceof Sheep)) {
                    
                    // Update healths
                    ((Wolf)map[y][x]).healed(((Sheep)map[y][x+1]).getHealth());
                    
                    // Update wolf position
                    ((Wolf)map[y][x]).setEnergy(((Wolf)map[y][x]).getEnergy()-1);
                    ((Wolf)map[y][x]).up();
                    ((Wolf)map[y][x]).setMoved(true);
                    map[y][x+1] = map[y][x];
                    map[y][x] = null;
                    
                    // Wants to fight (does not move)
                  } else if ((actionChoice[2]) && (map[y][x+1] instanceof Wolf)) {
                    
                    // Weaker wolf loses health; otherwise, nothing happens
                    if (((Wolf)map[y][x]).compareTo((Wolf)map[y][x+1]) > 0) {
                      ((Wolf)map[y][x+1]).damage(10);
                    } else if (((Wolf)map[y][x]).compareTo((Wolf)map[y][x+1]) < 0) {
                      ((Wolf)map[y][x]).damage(10);
                    }
                    
                    // Move to empty space
                  } else if ((x < (map.length - 2)) && (map[y][x+1] == null)) {
                    ((Wolf)map[y][x]).setEnergy(((Wolf)map[y][x]).getEnergy()-1);
                    ((Wolf)map[y][x]).right();
                    ((Wolf)map[y][x]).setMoved(true);
                    map[y][x+1] = map[y][x];
                    map[y][x] = null;
                  }
                }
                
              }
            }
          }
          
        }
        
      }
    }
    
  }
  
  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  
  /**
   * reproduce method
   * This method creates baby animals, regardless of any other moves
   * @param takes in the map and the health set for animals, and action choice to validate reproduction
   */
  public static void reproduce (Species [][] map,  int sheepHealth, int wolfHealth, int plantHealth) {
    
    // Place the baby
    int babyY, babyX;
    
    // Check if the baby has been placed
    int spawned = 0;
        
    for (int y = 0; y < map[0].length; y++){
      for (int x = 0; x < map.length; x++){
        
        boolean [][] breeding = breedChoice (map, x, y, plantHealth);
        
        // Sheep upwards to breed with
        if (breeding[0][0]) {
          ((Sheep)map[y][x]).reproduceHealthLost(10);
          ((Sheep)map[y-1][x]).reproduceHealthLost(10);
          
          // Make baby sheep
          Sheep sheep = new Sheep(sheepHealth, x, y);
          
          // Place the baby sheep
          do {
            babyY = (int)(Math.random() * map.length);
            babyX = (int)(Math.random() * map.length);
            if (map[babyY][babyX] == null) {
              map[babyY][babyX] = sheep;
              spawned = 1;
            }
          } while (spawned == 0);
          
        // Sheep downwards to breed with
        } else if (breeding[1][0]) {
          ((Sheep)map[y][x]).reproduceHealthLost(10);
          ((Sheep)map[y+1][x]).reproduceHealthLost(10);
          
          // Make baby sheep
          Sheep sheep = new Sheep(sheepHealth, x, y);
          
          // Place the baby sheep
          do {
            babyY = (int)(Math.random() * map.length);
            babyX = (int)(Math.random() * map.length);
            if (map[babyY][babyX] == null) {
              map[babyY][babyX] = sheep;
              spawned = 1;
            }
          } while (spawned == 0);
          
        // Sheep to the left to breed with
        } else if (breeding[2][0]) {
          ((Sheep)map[y][x]).reproduceHealthLost(10);
          ((Sheep)map[y][x-1]).reproduceHealthLost(10);
        
          // Make baby sheep
          Sheep sheep = new Sheep(sheepHealth, x, y);
          
          // Place the baby sheep
          do {
            babyY = (int)(Math.random() * map.length);
            babyX = (int)(Math.random() * map.length);
            if (map[babyY][babyX] == null) {
              map[babyY][babyX] = sheep;
              spawned = 1;
            }
          } while (spawned == 0);
          
        // Sheep to the right to breed with
        } else if (breeding[3][0]) {
          ((Sheep)map[y][x]).reproduceHealthLost(10);
          ((Sheep)map[y][x+1]).reproduceHealthLost(10);
          
          // Make baby sheep
          Sheep sheep = new Sheep(sheepHealth, x, y);
          
          // Place the baby sheep
          do {
            babyY = (int)(Math.random() * map.length);
            babyX = (int)(Math.random() * map.length);
            if (map[babyY][babyX] == null) {
              map[babyY][babyX] = sheep;
              spawned = 1;
            }
          } while (spawned == 0);
          
        // Wolf upwards to breed with
        } else if (breeding[0][1]) {
          ((Wolf)map[y][x]).reproduceHealthLost(10);
          ((Wolf)map[y-1][x]).reproduceHealthLost(10);
          
          // Make baby wolf
          Wolf wolf = new Wolf(wolfHealth, x, y);
          
          // Place the baby wolf
          do { 
            babyY = (int)(Math.random() * map.length);
            babyX = (int)(Math.random() * map.length);
            if (map[babyY][babyX] == null) {
              map[babyY][babyX] = wolf;
              spawned = 1; 
            }
          } while (spawned == 0);
          
        // Wolf downwards to breed with
        } else if (breeding[1][1]) {
          ((Wolf)map[y][x]).reproduceHealthLost(10);
          ((Wolf)map[y+1][x]).reproduceHealthLost(10);
          
          // Make baby wolf
          Wolf wolf = new Wolf(wolfHealth, x, y);
          
          // Place the baby wolf
          do { 
            babyY = (int)(Math.random() * map.length);
            babyX = (int)(Math.random() * map.length);
            if (map[babyY][babyX] == null) {
              map[babyY][babyX] = wolf;
              spawned = 1; 
            }
          } while (spawned == 0);
          
        // Wolf to the left to breed with
        } else if (breeding[2][1]) {
          ((Wolf)map[y][x]).reproduceHealthLost(10);
          ((Wolf)map[y][x-1]).reproduceHealthLost(10);
        
          // Make baby wolf
          Wolf wolf = new Wolf(wolfHealth, x, y);
          
          // Place the baby wolf
          do { 
            babyY = (int)(Math.random() * map.length);
            babyX = (int)(Math.random() * map.length);
            if (map[babyY][babyX] == null) {
              map[babyY][babyX] = wolf;
              spawned = 1; 
            }
          } while (spawned == 0);
          
        // Wolf to the right to breed with
        } else if (breeding[3][1]) {
          ((Wolf)map[y][x]).reproduceHealthLost(10);
          ((Wolf)map[y][x+1]).reproduceHealthLost(10);
          
          // Make baby wolf
          Wolf wolf = new Wolf(wolfHealth, x, y);
          
          // Place the baby wolf
          do { 
            babyY = (int)(Math.random() * map.length);
            babyX = (int)(Math.random() * map.length);
            if (map[babyY][babyX] == null) {
              map[babyY][babyX] = wolf;
              spawned = 1; 
            }
          } while (spawned == 0);
          
        }
          
      }
    }
     
  }
  
  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  
  /**
   * ageAnimal method
   * This method allows each animal to each each turn
   * @param takes in the map
   */
  public static void ageAnimal (Species [][] map) {
    
    // Check map for animals
    for (int y = 0; y < map[0].length; y++) {
      for (int x = 0; x < map.length; x++) {
        
        // Age any sheep found
        if (map[y][x] instanceof Sheep) {
          ((Sheep)map[y][x]).gainAge();
          
          // Age any wolves found
        } else if (map[y][x] instanceof Wolf) {
          ((Wolf)map[y][x]).gainAge();
        }
        
      }
    }
    
  }
  
  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  
  /**
   * population method
   * This method allows the ecosystem to keep track of the species populations, ending when one becomes extinct
   * @return int array, consisting of plant population, sheep population, and wolf population
   */
  public static int [] population() {
    
    // Declare int array containing plants, sheep, wolves population, and number of turns respectively
    int [] numCounter = new int [] {0, 0, 0, 0};
    
    // Check map for species
    for (int y = 0; y < map[0].length; y++) {
      for (int x = 0; x < map.length; x++) {
        
        // Find the number of plants and add one to the count each time a plant is found
        if (map[y][x] instanceof Plant) {
          numCounter[0] += 1;
          
          // Find the number of sheep and add one to the count each time a sheep is found
        } else if (map[y][x] instanceof Sheep) {
          numCounter[1] += 1;
          
          // Find the number of wolves and add one to the count each time a wolf is found
        } else if (map[y][x] instanceof Wolf) {
          numCounter[2] += 1;
        }
        
      }
    }
    
    // Add a turn
    numCounter[3] += 1;
    
    // Return the int array with population of each species
    return numCounter;
    
  }
  
  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  
} // End Simulation ////////////////////////////////////////////////////////////////////////////////////////////////////
