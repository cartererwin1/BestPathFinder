import java.util.HashMap;
import java.util.PriorityQueue;

import java.util.Scanner;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class astar {

  public static final double XCONVERSION = 10.29;
  public static final double YCONVERSION = 7.55;
  public static BufferedImage image;
  public static Node goalNode;
  

  /*
   * Description:
   * @param x1:
   * @param x2:
   * @param y1:
   * @param y2:
   * @param img:
   * @return: distance (meters) traveled
   */
  public static double drawLine(int x1, int y1, int x2, int y2, String fileString) {
    double distance = 0;
    Graphics g = image.getGraphics();
    g.setColor(java.awt.Color.decode("#763fe7"));
    g.drawLine(x1, y1, x2, y2);
    try {
      File output = new File(fileString);
      ImageIO.write(image, "png", output);
    } catch (IOException e) {
        e.printStackTrace();
    }
    distance = (x2-x1) * XCONVERSION + (y2-y1) * YCONVERSION;
    return distance;
  }


  //h(n)
  public double calculateHeuristic(Node current) {
    return calculateDist(current, goalNode);
  }


  public double calculateDist(Node current, Node dest) {
    //add in conversions
    return Math.sqrt(Math.pow(dest.getX() - current.getX(), 2) + Math.pow(dest.getY() - current.getY(), 2) + Math.pow(dest.getZ() - current.getZ(), 2));
  }

  //g(n)
  public double calculateCost(Node current, Node dest) {
    return calculateCost(current, dest) * current.getTer();
  }


  /*
   *  Runs astar search on the graph
   */
  public static void aStar(area map) {
    HashMap<String, Integer> visited = new HashMap<String, Integer>();
    PriorityQueue<Node> frontier = new PriorityQueue<>();
    /*
    while(frontier.size() != 0) {
      
      Node current = frontier.remove();
      visited.put(current, );
      if(current.equals(goalNode)) {
        return pathToTop(current);
      }
      for(neighbor : map.getNeighbors(current)) {
        if(!visited.containsKey(neighbor)) {
          frontier.addStateIfBetter(neighbor);
          neighbor.updateParent(current);
        }
      }

    } 
    */
  }

  public static Node[][] createNodes(String elevationFileName) {
    Scanner reader;
    File elevationFile = new File(elevationFileName);
    Node[][] nodes = new Node[395][500];
    try {
      reader = new Scanner(elevationFile);
      int y = 0;
      while(reader.hasNextLine()) {
          String[] line = reader.nextLine().strip().split("\t");
          for(int x = 0; x < 395; x++) {
            //nodes[x][y].setZ(Double.parseDouble(line[0]));
            //findTerrain method to get what color the img pixel is
            nodes[x][y] = new Node(x, y, 0.0, Terrain.FOOTPATH);
          }
          y++;
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return nodes;
  }
  
  
  public static void main(String[] args) {

    //process cmd line args
    String imageFileName = args[0];
    String elevationFileName = args[1];
    String pathFileName = args[2]; 
    String outputFileName = args[3];

    double totalDistance = 0.0;

                //initialize 1st goal node after opening path file

                //update goal node after reaching it

    try {
      image = ImageIO.read(new File(imageFileName));
    } catch (IOException e) {
      e.printStackTrace();
    }



    
    //create graph for astar
    area map = new area(createNodes(elevationFileName));
                  //change area to a hashmap??? key: x,y tuple value: Node
    //Conduct astar algorithm on graph
    aStar(map);




    //this will be removed once astar works
    File pathfile = new File(pathFileName);
    Scanner reader;
    try {
      reader = new Scanner(pathfile);
      int line = 0;
      int[] xcoords = new int[4];
      int[] ycoords = new int[4];
      while(reader.hasNextLine()) {
        String[] coords = reader.nextLine().strip().split(" ");
        xcoords[line] = Integer.parseInt(coords[0]);
        ycoords[line] = Integer.parseInt(coords[1]);
        line++;
      }



    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    System.out.println("total distance traveled: " + totalDistance);  
  }
}
