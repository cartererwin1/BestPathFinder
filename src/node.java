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
class Node {
  
  private int x;
  private int y;
  private double z;
  private Terrain ter;
  private double f;
  private double g;
  private Node parent;


  /*
   * Constructor to create node
   */
  public Node (int x, int y, double z, Terrain ter) {
    this.x = x;
    this.y = y;
    this.z = z;
    this.ter = ter;

  }

  public Node(int x, int y) {
    this.x = x;
    this.y = y;
  }


  public int getX() {return this.x;}

  public int getY() {return this.y;}

  public double getZ() {return this.z;}
  
  public void setF(double f) {this.f = f;}

  public double getF() {return this.f;}

  public void setG(double g) {this.g = g;}

  public double getG() {return this.g;}

  public Node getParent() {return this.parent;}

  public void setParent(Node node) {this.parent = node;}

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