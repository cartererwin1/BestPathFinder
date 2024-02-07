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


  /*
   * Constructor to create node
   */
  public Node (int x, int y, double z, Terrain ter) {
    this.x = x;
    this.y = y;
    this.z = z;
    this.ter = ter;
  }


  public int getX() {return this.x;}


  public int getY() {return this.y;}


  public double getZ() {return this.z;}

  
  public void setZ(double z) {this.z = z;}


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
        return .8;
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
    return 0;
  }


  public boolean equals(Object other) {
    Node o = (Node) other;
    if(o.getX() != this.getX() || o.getY() != this.getY() || o.getZ() != this.getZ() || o.getTer() != this.getTer()) {
      return false;
    } 
    return true;

  }

  public String toString() {
    return "x=" + this.x + " y =" + this.y + " z=" + this.z;
  }
}