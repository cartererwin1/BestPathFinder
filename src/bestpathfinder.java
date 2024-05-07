import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.PriorityQueue;

import java.util.Scanner;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;


public class bestpathfinder {
  public static final double XCONVERSION = 10.29;
  public static final double YCONVERSION = 7.55;
  public static BufferedImage image;
  public static Node currentGoal;
  

  /*
   * Description: draws a line on an image from one node to another
   * @param src: the node the line will be drawn from (if the image is paper this is where a pen is placed)
   * @param dest: the node where the line will finish (if the image is paper this is where the pen would be lifted)
   * @param fileString: the file containing the image to be drawn on
   * @return: the 3D distance between the nodes
   */
  public static double drawLine(Node src, Node dest, String fileString) {
    double distance = 0;
    Graphics g = image.getGraphics();
    g.setColor(java.awt.Color.RED);
    g.drawLine(src.getX(), src.getY(), dest.getX(), dest.getY());
    try {
      File output = new File(fileString);
      ImageIO.write(image, "png", output);
    } catch (IOException e) {
        e.printStackTrace();
    }
    distance = calculateDist(src, dest);
    return distance;
  }


  /*
   * Description: calculates h(node) given a node. This represents the distance from the node to the goal
   * @param current: the node the value is being calculate for
   * @return: the h value of the node
   */
  public static double calculateHeuristic(Node current) {
    return calculateDist(current, currentGoal) * .1;
  }


  /*
   * Description: calculates the 3D distance between two nodes
   * @param current: the starting node
   * @param dest: the ending node
   * @return: the 3D distance between the nodes
   */
  public static double calculateDist(Node current, Node dest) {
    double xdelta = Math.pow((current.getX() - dest.getX()) * XCONVERSION,2);
    double ydelta = Math.pow((current.getY() - dest.getY()) * YCONVERSION,2);
    double zdelta = Math.pow(current.getZ() - dest.getZ(),2);
    return Math.sqrt(xdelta + ydelta + zdelta);
  }


  /*
   * Description: calculates the cost (g(node)) to travel from current to dest
   * @param current: the starting node
   * @param dest: the node to be moved to
   * @return: the g value of the movement which would be the 3D distance multiplied by the terrain value
   */
  public static double calculateCost(Node current, Node dest) {
    return calculateDist(current, dest) * current.getTer() + current.getG();
  }


  /*
   * Description: after a goal is reached recurse backwards to get the path that was taken to get to the goal.
   * @param startNode: the starting node
   * @param map: the map containing all parent data for nodes.
   * @param end: the ending node
   * @return: a list of Nodes that represents the best path calculated by A*
   */
  public static ArrayList<Node> pathToTop(Node startNode, HashMap<Node, Node> map, Node end) {
    Node child = end;
    ArrayList<Node> path = new ArrayList<>();
    while(map.get(child) != null) {
      path.add(child);
      child = map.get(child);
    }
    path.add(child);
    return path;
  }

  


  /*
   *  Runs astar search on the graph
   */
  public static ArrayList<Node> aStar(area map, Node startNode, Node goalNode) {
    //begin astar alg
    HashSet<Node> visited = new HashSet<>();
    HashMap<Node, Node> parentMap = new HashMap<>();
    HashMap<Node, Double> fMap = new HashMap<>();
    Comparator<Node> comparator = new Comparator<Node>() {
      @Override
      public int compare(Node node1, Node node2) {
          if(node1.getF() < node2.getF()) {
            return -1;
          }
          else if(node1.getF() > node2.getF()) {
            return 1;
          }
          else {return 0;}
      }
    };
    PriorityQueue<Node> frontier = new PriorityQueue<>(comparator);
    frontier.add(startNode);

    while(frontier.size() != 0) {
      Node current = frontier.remove();
      if(visited.contains(current)) {
        continue;
      }
      if(current.equals(goalNode)) {
        return pathToTop(startNode, parentMap, current);
      }
      for(Node neighbor : map.getNeighbors(current.getX(), current.getY())) {
        neighbor.setG(calculateCost(current, neighbor));
        neighbor.setF(neighbor.getG() + calculateHeuristic(neighbor));
        //not already visited and neighbor is not OOB
        if(!visited.contains(neighbor) && neighbor.getTer() != 1) {

          if(fMap.get(neighbor) == null) {
            frontier.add(neighbor);
            fMap.put(neighbor, neighbor.getF());
            if(!neighbor.equals(startNode)) {
              parentMap.put(neighbor, current);
              neighbor.setParent(current);
            }
          }
          else {
            if(fMap.get(neighbor) > neighbor.getF()) {
              Node temp = new Node(neighbor.getX(), neighbor.getY());
              fMap.put(temp, neighbor.getF());
              frontier.add(neighbor);
              if(!neighbor.equals(startNode)) {
                parentMap.put(temp, current);
                neighbor.setParent(current);
              }
            }
          }          
        }
      }
    }     
    //no path found
    return null;
  }

  /*
   * Description: created a 2D array of nodes that will be 395x500, each node representing a pixel in the image 
   * @param elevationFileName: the file containing the Z values of all pixels.
   * @return: 2D array of nodes representing the pixels of the image.
  */
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
            //determine color of pixel to set Terrain
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
    try {
      image = ImageIO.read(new File(imageFileName));
    } catch (IOException e) {e.printStackTrace();}
    //create graph for astar
    area map = new area(createNodes(elevationFileName));
    //Conduct astar algorithm on graph
    ArrayList<Node> aStarPath = new ArrayList<Node>();
    File pathFile = new File(pathFileName);
    Scanner pathReader;
    ArrayList<Node> goalNodes = new ArrayList<Node>();
    //create array of places we need to go to
    try {
      pathReader = new Scanner(pathFile);
      while(pathReader.hasNextLine()) {
        String[] coords = pathReader.nextLine().strip().split(" ");
        Node node = map.getNode(Integer.parseInt(coords[0]), Integer.parseInt(coords[1]));
        goalNodes.add(node);
      }
    } catch (FileNotFoundException e ) {e.printStackTrace();}
    for(int n = 0; n < goalNodes.size() - 1; n++) {
      currentGoal = goalNodes.get(n+1);
      ArrayList<Node> currentPath = aStar(map, goalNodes.get(n), goalNodes.get(n+1));
      for(int j = currentPath.size()-1; j >= 0; j--) {
        aStarPath.add(currentPath.get(j));
        map.resetParents();
      }
    }
    //draw the path returned by aStar on the output image
    for(int i = 0; i < aStarPath.size() - 1; i++) {
        totalDistance += drawLine(aStarPath.get(i), aStarPath.get(i+1), outputFileName);
    }
    System.out.println("total distance traveled: " + totalDistance);  
  }
}