import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;

import java.util.Scanner;
import java.util.HashSet;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.imageio.ImageIO;

public class astar {

  public static final double XCONVERSION = 10.29;
  public static final double YCONVERSION = 7.55;
  public static BufferedImage image;
  

  /*
   * Description:
   * @param x1:
   * @param x2:
   * @param y1:
   * @param y2:
   * @param img:
   * @return: distance (meters) traveled
   */

   //make take in 2 nodes
  public static double drawLine(Node src, Node dest, String fileString) {
    Graphics g = image.getGraphics();
    g.setColor(java.awt.Color.decode("#763fe7"));
    g.drawLine(src.getX(), src.getY(), dest.getX(), dest.getY());
    try {
      File output = new File(fileString);
      ImageIO.write(image, "png", output);
    } catch (IOException e) {e.printStackTrace();}
    return calculateDist(src, dest);
  }


  //h(n)
  public static double calculateHeuristic(Node current, Node goalNode) {
    return calculateDist(current, goalNode);
  }


  public static double calculateDist(Node current, Node dest) {
    //add in conversions
    return Math.sqrt(Math.pow((dest.getX() - current.getX()*XCONVERSION), 2) + Math.pow((dest.getY() - current.getY()*YCONVERSION), 2) + Math.pow(dest.getZ() - current.getZ(), 2));
  }

  //g(n)
  public static double calculateCost(Node current, Node dest) {
    return calculateCost(current, dest) * current.getTer();
  }


  /*
   *  Runs astar search on the graph
   */
  public static ArrayList<Node> aStar(area map, String pathFileName) {
    ArrayList<Node> pathToTop = new ArrayList<>();


    File pathfile = new File(pathFileName);
    ArrayList<Node> goalNodes = new ArrayList<>();
    Scanner reader;
    try {
      reader = new Scanner(pathfile);
      while(reader.hasNextLine()) {
        String[] coords = reader.nextLine().strip().split(" ");
        goalNodes.add(new Node(Integer.parseInt(coords[0]), Integer.parseInt(coords[1])));
      }
    } catch (FileNotFoundException e) {e.printStackTrace();}


    HashMap<Node, Node> hashMap = new HashMap<Node, Node>();
    HashSet<Node> visited = new HashSet<Node>();
    PriorityQueue<Node> frontier = new PriorityQueue<>();
    for(Node goalNode : goalNodes) {
      while(frontier.size() != 0) {
        Node current = frontier.remove();
        visited.add(current);
        if(current.equals(goalNode)) {
          return pathToTop;
        }
        for(Node neighbor : map.getNeighbors(current.getX(), current.getY())) {
          if(!visited.contains(neighbor)) {
            if(calculateHeuristic(neighbor, goalNode) < calculateHeuristic(current, goalNode)) {
              frontier.add(neighbor);
            }
            //neighbor.updateParent(current);
          }
        }
      } 
    }   
    pathToTop.add(new Node(50, 50));
    pathToTop.add(new Node(150, 50));
    pathToTop.add(new Node(50, 150));
    return pathToTop; 
  }

  public static Node[][] createNodes(String elevationFileName) {
    Scanner reader;
    File elevationFile = new File(elevationFileName);
    Node[][] nodes = new Node[395][500];
    try {
      reader = new Scanner(elevationFile);
      int y = 0;
      while(reader.hasNextLine()) {
          String[] line = reader.nextLine().strip().split("\\s+");
          for(int x = 0; x < 395; x++) {
            int pixel = image.getRGB(x, y);
            String color = String.format("#%06X", (0xFFFFFF & pixel));
            Terrain ter;
            switch(color) {
              case "#F89412":
                ter = Terrain.OPENLAND;
                break;
              case "#FFC000":
                ter = Terrain.ROUGHMEADOW;
                break;
              case "#FFFFFF":
                ter = Terrain.EASYMOVEMENTFOREST;
                break;
              case "#02D03C":
                ter = Terrain.SLOWRUNFOREST;
                break;
              case "#028828":
                ter = Terrain.WALKFOREST;
                break;
              case "#054918":
                ter = Terrain.IMPASSIBLEVEGETATION;
                break;
              case "#0000FF":
                ter = Terrain.LAKE;
                break;
              case "#473303":
                ter = Terrain.PAVEDROAD;
                break;
              case "#000000":
                ter = Terrain.FOOTPATH;
                break;
              case "#CD0065":
                ter = Terrain.OOB;
                break;
              default:
                ter = Terrain.OOB;
            }
            nodes[x][y] = new Node(x, y, Double.parseDouble(line[x]), ter);
          }
          y++;
      }
    } catch (FileNotFoundException e) {e.printStackTrace();}
    return nodes;
  }
  
  
  public static void main(String[] args) {

    //process cmd line args
    String imageFileName = args[0];
    String elevationFileName = args[1];
    String pathFileName = args[2]; 
    String outputFileName = args[3];
    double totalDistance = 0.0;
    ArrayList<Node> aStarPath = new ArrayList<>();
    try {
      image = ImageIO.read(new File(imageFileName));
    } catch (IOException e) {e.printStackTrace();}
    area map = new area(createNodes(elevationFileName));
    aStarPath = aStar(map, pathFileName);
    for(int i = 0; i < aStarPath.size() - 1; i ++) {
      totalDistance += drawLine(aStarPath.get(i), aStarPath.get(i+1), outputFileName);
    }
    System.out.println("total distance traveled: " + totalDistance);  
  }
}
