import java.util.Collection;
import java.util.Map;
import java.util.Set;
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

    public Collection<Node> getNeighbors(int x, int y) {
        ArrayList<Node> neighbors = new ArrayList<>();
        //check if out of bounds??
        neighbors.add(getNode(x+1, y));
        neighbors.add(getNode(x, y-1));
        neighbors.add(getNode(x, y+1));
        neighbors.add(getNode(x-1, y));
        return neighbors;
    }
}