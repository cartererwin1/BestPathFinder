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
  public static double makeImage(int x1, int y1, int x2, int y2, BufferedImage img) {
    double distance = 0;
    Graphics g = img.getGraphics();
    g.setColor(java.awt.Color.decode("#763fe7"));
    g.drawLine(x1, y1, x2, y2);
    try {
      File output = new File("output.png");
      ImageIO.write(img, "png", output);
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
    //Build graph for astar
    //buildGraph needs to return node[]
    double totalDistance = 0.0;
    area map = new area(395, 500);
    //Conduct astar algorithm on graph
    aStar(map);
    //print the picture
    File file = new File("src/path.txt");
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
      try {
        BufferedImage image = ImageIO.read(new File("terrain.png"));
        for(int i = 0; i < line - 1; i++) {
          totalDistance += makeImage(xcoords[i], ycoords[i], xcoords[i+1], ycoords[i+1], image);
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    System.out.println("total distance traveled: " + totalDistance);  
  }
}
