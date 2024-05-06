import java.util.ArrayList;
/*
 * This is the class that represents a 2x2 grid of Nodes that serves as the 3 
 * dimensional space that A* will be perfomed on
 */
class area {
    private Node[][] grid;

    public area(Node[][] grid) {
        //read in from elevation file
        this.grid = grid;
    }

    /*
     * Description: getter for a specific node based on x,y coordinate pair.
     * @param x: the x value
     * @param y: the y value
     * @return: the node at that x,y coordinate
     */
    public Node getNode(int x, int y) {
        return this.grid[x][y];
    }


    /*
     * Description: getter for all surrounding nodes (not diagonals)
     * for the node at (x,y)
     * @param x: the x location of the node whose neighbors we are looking for
     * @param y: the y location of the node whose neighbors we are looking for
     */
    public ArrayList<Node> getNeighbors(int x, int y) {
        ArrayList<Node> neighbors = new ArrayList<>();
        if(x != 394) {
            neighbors.add(getNode(x+1, y));
        }
        if(y !=0) {
            neighbors.add(getNode(x, y-1));
        }
        if(y != 499) {
            neighbors.add(getNode(x, y+1));
        }
        if(x != 0) {
            neighbors.add(getNode(x-1, y));
        }
        return neighbors;
    }


    /*
     * Description: loops through all nodes in the 2D grid and erases their
     * parents
     */
    public void resetParents() {
        for(int x = 0; x < 395; x++) {
            for(int y = 0; y < 500; y++) {
                getNode(x,y).setParent(null);
            }
        }
    }
}