class Node {

  private int f;
  private int g;
  private int heuristic;


  /*
   * Constructor to create node
   * 
   * might need to have constructor without heuristic
   */
  public Node (int f, int g, int heuristic) {
    this.f = f;
    this.g = g;
    this.heuristic = heuristic;
  }




  public int hashcode() {
    return 0;
  }


  public boolean equals(Object other) {
    return false;
  }

  //compareTo
}