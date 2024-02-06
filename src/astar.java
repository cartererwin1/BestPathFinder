import java.util.HashMap;
import java.util.PriorityQueue;

import java.util.Scanner;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class astar {
  public static final double XCONVERSION = 10.29;
  public static final double YCONVERSION = 7.55;
  public static BufferedImage image;
  /*
   * Create nodes and graph
   */
  public static void buildGraph() {

  }


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

  /*
   *  Runs astar search on the graph
   */
  public static void aStar(area map) {
    HashMap<String, Integer> visited = new HashMap<String, Integer>();
    PriorityQueue<Integer> frontier = new PriorityQueue<>();
    /*
    while(frontier.size() != 0) {
      //Node current = frontier.pop();
      //visited.put(current, value???);
      if(isGoal(current)) {
        return pathToTop(current);
      }
      for(neighbor : current.getNeighbors()) {
        if(!visited.containsKey(neighbor)) {
          frontier.addStateIfBetter(neighbor);
          neighbor.updateParent(current);
        }
      }
    } 
    */
  }
  
  
  public static void main(String[] args) {
    String imageFileName = args[0];
    String elevationFileName = args[1];
    String pathFileName = args[2]; 
    String outputFileName = args[3];
    try {
      image = ImageIO.read(new File(imageFileName));
    } catch (IOException e) {
      e.printStackTrace();
    }
    //Build graph for astar
    //buildGraph needs to return node[]
    double totalDistance = 0.0;
    area map = new area(395, 500);
    //Conduct astar algorithm on graph
    aStar(map);
    //print the picture
    File file = new File("src/" + pathFileName);
    Scanner reader;
    try {
      reader = new Scanner(file);
      int line = 0;
      int[] xcoords = new int[4];
      int[] ycoords = new int[4];
      while(reader.hasNextLine()) {
        String[] coords = reader.nextLine().strip().split(" ");
        xcoords[line] = Integer.parseInt(coords[0]);
        ycoords[line] = Integer.parseInt(coords[1]);
        line++;
      }
      for(int i = 0; i < line - 1; i++) {
        totalDistance += drawLine(xcoords[i], ycoords[i], xcoords[i+1], ycoords[i+1], outputFileName);
      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    System.out.println("total distance traveled: " + totalDistance);  
  }
}
