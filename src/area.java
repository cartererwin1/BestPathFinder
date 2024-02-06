import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.ArrayList;

class area {
    private Set<Node> pixels;
    private Map<Node, Set<Node>> neighbors;
    private Node[][] grid;

    public area(int x, int y) {
        //read in from elevation file
        this.grid = new Node[x][y];
    }
    public Node getNode(int x, int y) {
        return this.grid[x][y];
    }

    public Collection<Node> getNeighbors(int x, int y) {
        ArrayList<Node> neighbors = new ArrayList<>();
        //check if out of bounds??
        neighbors.add(getNode(x+1, y-1));
        neighbors.add(getNode(x+1, y));
        neighbors.add(getNode(x+1, y+1));
        neighbors.add(getNode(x, y-1));
        neighbors.add(getNode(x, y+1));
        neighbors.add(getNode(x-1, y-1));
        neighbors.add(getNode(x-1, y));
        neighbors.add(getNode(x-1, y+1));
        return neighbors;
    }
}