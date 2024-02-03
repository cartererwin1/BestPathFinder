class Node {

  private int x;
  private int y;
  private int heuristic;


  /*
   * Constructor to create node
   * 
   * might need to have constructor without heuristic
   */
  public Node (int x, int y, int heuristic) {
    this.x = x;
    this.y = y;
    this.heuristic = heuristic;
  }

  public static void getNeighbors() {
    return;
  } 


  public int hashcode() {
    return 0;
  }


  public boolean equals(Object other) {
    return false;
  }
}