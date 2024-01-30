import java.io.File;
import java.util.HashMap;
import java.util.PriorityQueue;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.image.BufferedImage;

public class astar {
  
  /*
   * Create nodes and graph
   */
  public static void buildGraph() {

  }

  /*
   *  Runs astar search on the graph
   */
  public void aStar(/*graph*/) {

  }
  
  public static void main(String[] args) {
    System.out.println(395 * 10.29);
    System.out.println(500 * 7.55);
    PriorityQueue<Integer> queue = new PriorityQueue<>();
    buildGraph();
    //HashMap<String, Integer> nodes = new HashMap<String, Integer>();

    //print the picture
    JFrame f = new JFrame(); 
    f.add(new JLabel(new ImageIcon("src/terrain.png"))); 
    f.setSize(395, 500); 
    f.setVisible(true); 
  }
}
