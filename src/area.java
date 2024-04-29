import java.util.ArrayList;

class area {
    private Node[][] grid;

    public area(Node[][] grid) {
        //read in from elevation file
        this.grid = grid;
    }
    public Node getNode(int x, int y) {
        return this.grid[x][y];
    }


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

    public void resetParents() {
        for(int x = 0; x < 395; x++) {
            for(int y = 0; y < 500; y++) {
                getNode(x,y).setParent(null);
            }
        }
    }
}