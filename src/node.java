/*
 * the enum to type each of the different terrains represented by each
 * color in terrain.png. Different terrain types will affect how easily
 * they are to pass through. A* will weight each node based on its terrain.
 */
enum Terrain {
  OPENLAND,
  ROUGHMEADOW,
  EASYMOVEMENTFOREST,
  SLOWRUNFOREST,
  WALKFOREST,
  IMPASSIBLEVEGETATION,
  LAKE,
  PAVEDROAD,
  FOOTPATH,
  OOB
}
/*
 * The class that represents a single pixel in the terrain.png image. Each pixel is
 * 10.29m wide and 7.55m tall in the scheme of the A* algorithm.
 */
class Node {
  
  private int x;
  private int y;
  private double z;
  private Terrain ter;
  private double f;
  private double g;
  private Node parent;


  /*
   * Description: creates a new Node
   * @param x: the x location of the Node
   * @param y: the y location of the Node
   * @param z: the z location of the Node
   * @param ter: the terrain type of the Node
   */
  public Node (int x, int y, double z, Terrain ter) {
    this.x = x;
    this.y = y;
    this.z = z;
    this.ter = ter;

  }
  /*
   * Description: creates a new Node without specifying z and terrain values
   * @param x: the x location of the Node
   * @param y: the y location of the Node
   */
  public Node(int x, int y) {
    this.x = x;
    this.y = y;
  }


  /*
   * @return: x value of Node
   */
  public int getX() {return this.x;}


  /*
   * @return: y value of Node
   */
  public int getY() {return this.y;}


  /*
   * @return: z value of Node
   */
  public double getZ() {return this.z;}
  

  /*
   * Description: sets the f value of the node. F values are used to compare nodes in A*
   * f(Node) = g(Node) + h(Node)
   */
  public void setF(double f) {this.f = f;}


  /*
   * @return: f value of Node
   */
  public double getF() {return this.f;}


  /*
   * Description: sets the g value of the node. G values are used to represent
   * the "cost" to travel through a Node. Which in this case is the 3D distance
   * from the parent Node to the new node multiplied by the Terrain scalar of the new node
   */
  public void setG(double g) {this.g = g;}


  /*
   * @return: g value of Node
   */
  public double getG() {return this.g;}

  /*
   * @return: the parent Node of Node. 
   */
  public Node getParent() {return this.parent;}

  /*
   * @param node: the Node that will be this Nodes parent.
   * Description: Sets this Nodes parent to node. "Parent" in the scheme of this program is
   * the most recent node that the best path was drawn on.
   */
  public void setParent(Node node) {this.parent = node;}

  /*
   * @return: the Terrain of Node. 
   */
  public double getTer() {
    switch(this.ter) {
      case OPENLAND: 
        return .3;
      case ROUGHMEADOW:
        return .5;
      case EASYMOVEMENTFOREST:
        return .4;
      case SLOWRUNFOREST:
        return .6;
      case WALKFOREST:
        return .7;
      case IMPASSIBLEVEGETATION:
        return .9;
      case LAKE:
        return 1;
      case PAVEDROAD:
        return .1;
      case FOOTPATH:
        return .2;
      case OOB:
        return 1;
      default:
        return 0;
    }
  }

  
  public int hashcode() {
    return this.getX() *100 + this.getY()+1000;
  }

  @Override
  public boolean equals(Object other) {
    Node o = (Node) other;
    if(o.getX() == this.getX() && o.getY() == this.getY()) {
      return true;
    } 
    return false;

  }

  public String toString() {
    return "x=" + this.x + " y =" + this.y + " z=" + this.z + "ter:" + this.ter;
  }


}