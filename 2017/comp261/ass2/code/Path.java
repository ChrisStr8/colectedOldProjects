/**
 * Created on 10/04/2017.
 * Name: Christopher Straight
 * Usercode: straigchri
 * ID: 300363269
 */
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.*;


public class Path {

    private PriorityQueue<Node> open = new PriorityQueue<>();
    private ArrayList<Node> closed = new ArrayList<>();

    private List<Segment> pathSegments = new ArrayList<>();
    private List<Node> pathNodes = new ArrayList<>();

    private final Node start;
    private final Node end;

    private Graph graph;

    public Path(Node start, Node end, Graph graph){
        this.start = start;
        this.end = end;
        this.graph = graph;
    }

    public boolean findPathAStar(boolean forDistance, boolean driving, boolean walking , boolean biking){
        boolean foundPath = false;
        start.heuristic = calculateHeuristic(start);
        open.offer(start);

        while(open.size()>0){
            Node current = open.poll();
            closed.add(current);

            if(current.equals(end)){
                foundPath = true;
                break;
            }

            for(Segment s : current.segments){
                Node neighbour = s.end;
                if(neighbour.equals(current)){
                    neighbour=s.start;
                }
                if(graph.restrictions.get(current.nodeID)!=null){
                    Restriction r = graph.restrictions.get(current.nodeID);
                    if(current.parent.nodeID==r.getNode1() && neighbour.nodeID==r.getNode2()){
                        continue;
                    }
                }
                if(current==s.end && s.road.oneWay==1){
                    continue;
                }
                if(driving && s.road.notforcar==1){
                    continue;
                }else if(walking && s.road.notforpede==1){
                    continue;
                }else if(biking && s.road.notforbicy==1){
                    continue;
                }

                if (!closed.contains(neighbour)) {
                    if(forDistance || !driving){
                        if (calculateNewGCostLength(current, s) < neighbour.gCost || !open.contains(neighbour)) {
                            neighbour.heuristic = calculateHeuristic(current);
                            neighbour.gCost = calculateNewGCostLength(current, s);
                            neighbour.parent = current;
                            if (!open.contains(neighbour)) {
                                open.offer(neighbour);
                            }
                        }
                    }else{
                        if (calculateNewGCostSpeed(current, s) < neighbour.gCost || !open.contains(neighbour)) {
                            neighbour.heuristic = calculateHeuristic(current);
                            neighbour.gCost = calculateNewGCostSpeed(current, s);
                            neighbour.parent = current;
                            if (!open.contains(neighbour)) {
                                open.offer(neighbour);
                            }
                        }
                    }
                }
            }
        }
        if(foundPath) {
            retracePath();
        }
        return foundPath;
    }

    private void retracePath(){
        Node current = end;
        while(!current.equals(start)) {
            pathNodes.add(current);
            for(Segment s : current.segments){
                if (s.end.equals(current.parent) || s.start.equals(current.parent)) {
                    pathSegments.add(s);
                }
            }
            Node newCurrent = current.parent;
            current.parent = null;
            current = newCurrent;
        }
    }

    private double calculateHeuristic(Node current){
        double a = Math.pow((current.location.x-end.location.x),2);
        double b = Math.pow((current.location.y-end.location.y),2);
        return Math.sqrt(a+b);
    }

    private double calculateNewGCostLength(Node current, Segment s){
        return current.gCost + s.length;
    }

    private double calculateNewGCostSpeed(Node current, Segment s){
        return current.gCost + s.length * s.road.speed * s.road.roadclass;
    }

    private double round(double val){
        BigDecimal num = new BigDecimal(val);
        num = num.setScale(3, RoundingMode.HALF_UP);
        return num.doubleValue();
    }

    public void drawPath(Graph graph){
        graph.setHighlightedNodes(pathNodes);
        graph.setHighlightedSegments(pathSegments);
    }

    /*public String toString(){
        String string = "Path: \n";
        double totalDistance = 0;
        List<String> roads = new ArrayList<>();
        Map<String, Double> route = new HashMap<>();
        for(Segment s : pathSegments) {
            totalDistance += s.length;
            String name = s.road.name;
            double distance = s.length;
            if (route.containsKey(name)) {
                route.replace(name, route.get(name)+distance);
            }else{
                roads.add(name);
                route.put(name, distance);
            }
        }
        StringBuilder stringBuilder = new StringBuilder(string);
        for(int i = roads.size()-1; i>=0; i--){
            String name = roads.get(i);
            double distance = route.get(name);
            stringBuilder.append(name).append(": ").append(round(distance)).append("km\n");
        }
        stringBuilder.append("\nTotal distance = ").append(round(totalDistance)).append("km\n");
        string = stringBuilder.toString();
        return string;
    }*/
    public String toString(){
        String string = "Path: \n";
        double totalDistance = 0;;
        StringBuilder stringBuilder = new StringBuilder(string);
        String name = pathSegments.get(pathSegments.size()-1).road.name;
        double distance = pathSegments.get(pathSegments.size()-1).length;
        for(int i = pathSegments.size()-2; i>=0; i--){
            String newName = pathSegments.get(i).road.name;
            double newDistance =  pathSegments.get(i).length;
            if(newName.equals(name)){
                distance += newDistance;
            }else {
                stringBuilder.append(name).append(": ").append(round(distance)).append("km\n");
                name = newName;
                distance = newDistance;
            }
        }
        stringBuilder.append("\nTotal distance = ").append(round(totalDistance)).append("km\n");
        string = stringBuilder.toString();
        return string;
    }
}