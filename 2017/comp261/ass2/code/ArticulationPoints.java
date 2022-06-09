import java.util.*;

/**
 * Created on 12/04/2017.
 * Name: Christopher Straight
 * Usercode: straigchri
 * ID: 300363269
 */
public class ArticulationPoints {
    private final Graph graph;

    private Stack<Node> nodes = new Stack<>();
    private Stack<Integer> counts = new Stack<>();
    private Stack<Node> fromNodes = new Stack<>();
    private List<Node> notVisited = new ArrayList<>();

    private Collection<Node> articulationPoints;

    public ArticulationPoints(Graph graph){
        this.graph = graph;
        Set<Integer> keys = graph.nodes.keySet();
        for (int key : keys) {
            graph.nodes.get(key).count = (int)Double.POSITIVE_INFINITY;
            notVisited.add(graph.nodes.get(key));
        }
        articulationPoints = new HashSet<>();
        while(!notVisited.isEmpty()) {
            System.out.println("new section");
            Node start = notVisited.get(0);
            notVisited.remove(0);
            start.count = 0;
            int numSubtrees = 0;
            for (Segment s : start.segments) {
                Node neighbour = s.end;
                if (neighbour.equals(start)) {
                    neighbour = s.start;
                }
                if (neighbour.count == (int) Double.POSITIVE_INFINITY) {
                    findArticulationPoints(neighbour, start);
                    numSubtrees++;
                }
            }
            if (numSubtrees > 1) {
                articulationPoints.add(start);
            }
        }
    }



    private void findArticulationPoints(Node start, Node root){
        nodes = new Stack<>();
        counts = new Stack<>();
        fromNodes = new Stack<>();

        nodes.add(start);
        counts.add(1);
        fromNodes.add(root);
        while(!nodes.isEmpty()){
            Node current = nodes.peek();
            int count = counts.peek();
            Node fromNode = fromNodes.peek();

            if(current.count==(int)Double.POSITIVE_INFINITY){
                current.count = count;
                current.reachBack = count;
                current.children = new ArrayDeque<>();
                for (Segment s: current.segments) {
                    Node neighbour = s.end;
                    if(neighbour.equals(current)){
                        neighbour=s.start;
                    }
                    if(neighbour!=fromNode){
                        current.children.add(neighbour);
                    }
                }
            }else if(!current.children.isEmpty()){
                Node child = current.children.poll();
                if(child.count<(int)Double.POSITIVE_INFINITY){
                    current.reachBack = Math.min(current.reachBack, child.count);
                }else{
                    nodes.push(child);
                    counts.push(count+1);
                    fromNodes.push(current);
                }
            }else{
                if(current!=start){
                    if(current.reachBack>= fromNode.count){
                        articulationPoints.add(fromNode);
                    }
                    fromNode.reachBack = Math.min(fromNode.reachBack, current.reachBack);
                }
                notVisited.remove(current);
                nodes.pop();
                counts.pop();
                fromNodes.pop();
            }

        }
        graph.setHighlightedNodes(articulationPoints);
    }

    public Collection<Node> getArticulationPoints(){
        return articulationPoints;
    }

    public int getNumArticulationPoints(){
        return articulationPoints.size();
    }
}

