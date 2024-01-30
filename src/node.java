class node {

  private int x;
  private int y;
  private int heuristic;


  /*
   * Constructor to create node
   * 
   * might need to have constructor without heuristic
   */
  public node (int x, int y, int heuristic) {
    this.x = x;
    this.y = y;
    this.heuristic = heuristic;
  }


  public int hashcode() {
    return 0;
  }


  public boolean equals(Object other) {
    return false;
  }
}