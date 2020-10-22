/**
 * [DisplayGrid.java]
 * A Small program for Display a 2D String Array graphically
 * Framework author: Mangat
 * Created by: Joyce Truong
 */

// Graphics Imports
import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
import javax.imageio.*;

class DisplayGrid { 

  private JFrame frame;
  private int maxX,maxY, GridToScreenRatio;
  private Species [][] world;
  
  DisplayGrid(Species [][] w) { 
    this.world = w;
    
    maxX = Toolkit.getDefaultToolkit().getScreenSize().width;
    maxY = Toolkit.getDefaultToolkit().getScreenSize().height;
    GridToScreenRatio = maxY / (world.length+1);  //ratio to fit in screen as square map
    
    System.out.println("Map size: "+world.length+" by "+ world[0].length + "\nScreen size: "+ maxX +"x"+maxY+ " Ratio: " + GridToScreenRatio);
    
    this.frame = new JFrame("Joyce's World");
    
    GridAreaPanel worldPanel = new GridAreaPanel();
    
    frame.getContentPane().add(BorderLayout.CENTER, worldPanel);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(Toolkit.getDefaultToolkit().getScreenSize());
    frame.setVisible(true);
  }
  
  
  public void refresh() { 
    frame.repaint();
  }
  
  class GridAreaPanel extends JPanel {
    public void paintComponent(Graphics g) {        
      
      // Import all images
      Image grass = Toolkit.getDefaultToolkit().getImage("Grass.png");
      Image healthPlant = Toolkit.getDefaultToolkit().getImage("HealthyPlant.png");
      Image poisonPlant = Toolkit.getDefaultToolkit().getImage("PoisonousPlant.png");
      Image energyPlant = Toolkit.getDefaultToolkit().getImage("EnergizingPlant.png");
      Image maleSheep = Toolkit.getDefaultToolkit().getImage("MaleSheep.png");
      Image femaleSheep = Toolkit.getDefaultToolkit().getImage("FemaleSheep.png");
      Image babySheep = Toolkit.getDefaultToolkit().getImage("BabySheep.png");
      Image maleWolf = Toolkit.getDefaultToolkit().getImage("MaleWolf.png");
      Image femaleWolf = Toolkit.getDefaultToolkit().getImage("FemaleWolf.png");
      Image babyWolf = Toolkit.getDefaultToolkit().getImage("BabyWolf.png");
      
      setDoubleBuffered(true); 
      
      // Set images for each species
      for (int i = 0; i<world[0].length;i=i+1) { 
        for (int j = 0; j<world.length;j=j+1) { 
          
          if (world[i][j] instanceof Sheep) { 
            if (((Sheep)world[i][j]).getAge () < 5) {
              g.drawImage(babySheep,j*GridToScreenRatio,i*GridToScreenRatio,GridToScreenRatio,GridToScreenRatio,this);
            } else if (((Sheep)world[i][j]).getGender() == 1) {
              g.drawImage(maleSheep,j*GridToScreenRatio,i*GridToScreenRatio,GridToScreenRatio,GridToScreenRatio,this);
            } else {
              g.drawImage(femaleSheep,j*GridToScreenRatio,i*GridToScreenRatio,GridToScreenRatio,GridToScreenRatio,this);
            }
            
          } else if (world[i][j] instanceof Wolf) {
            if (((Wolf)world[i][j]).getAge () < 5) {
              g.drawImage(babyWolf,j*GridToScreenRatio,i*GridToScreenRatio,GridToScreenRatio,GridToScreenRatio,this);
            } else if (((Wolf)world[i][j]).getGender() == 1) {
              g.drawImage(maleWolf,j*GridToScreenRatio,i*GridToScreenRatio,GridToScreenRatio,GridToScreenRatio,this);
            } else {
              g.drawImage(femaleWolf,j*GridToScreenRatio,i*GridToScreenRatio,GridToScreenRatio,GridToScreenRatio,this);
            }
            
          } else if (world[i][j] instanceof HealthyPlant) {
            g.drawImage(healthPlant,j*GridToScreenRatio,i*GridToScreenRatio,GridToScreenRatio,GridToScreenRatio,this);
          } else if (world[i][j] instanceof EnergizingPlant) {
            g.drawImage(energyPlant,j*GridToScreenRatio,i*GridToScreenRatio,GridToScreenRatio,GridToScreenRatio,this);
          } else if (world[i][j] instanceof PoisonousPlant) {
            g.drawImage(poisonPlant,j*GridToScreenRatio,i*GridToScreenRatio,GridToScreenRatio,GridToScreenRatio,this);
            
          } else {
            g.drawImage(grass,j*GridToScreenRatio,i*GridToScreenRatio,GridToScreenRatio,GridToScreenRatio,this);
          }
          
        }
      }
      
      // Display the population for each of the species each turn
      int[] count = EcoSystemSim.population();
      
      // Set new font as helvetica for animal counter displays
      g.setFont(new Font("Helvetica", Font.BOLD, 20));
      int fontX = world[0].length * GridToScreenRatio + 20;

      // Display animal population counters
      g.drawString("Plants: " + count[0], fontX, 20);
      g.drawString("Sheep: " + count[1], fontX, 40);
      g.drawString("Wolves: " + count[2], fontX, 60);
      
    }
  } //end of GridAreaPanel
  
} //end of DisplayGrid

