package code;

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * Created on 11/03/2017.
 * Name: Christopher Straight
 * Usercode: straigchri
 * ID: 300363269
 */
public class Node {
    private final int NodeID;
    private final Location Loc;
    private final List<RoadSegment> outSegs = new ArrayList<>();
    private final List<RoadSegment> inSegs = new ArrayList<>();

    public Node(int nodeID, Location loc) {
        NodeID = nodeID;
        Loc = loc;
    }

    public void addOutSeg(RoadSegment r){
        outSegs.add(r);
    }

    public void addInSeg(RoadSegment r){
        inSegs.add(r);
    }

    public void draw(Graphics g, Location origin, int scale){
        g.setColor(Color.red);
        Point p = Loc.asPoint(origin, scale);
        g.fillRect((int)(p.getX()-2.5),(int)(p.getY()-2.5),5,5);
    }

    public int getNodeID() {
        return NodeID;
    }

    public Location getLoc() {
        return Loc;
    }

    public List<Integer> roadIDs(){
        List<Integer> IDs = new ArrayList<>();
        int ID;
        for(RoadSegment s : outSegs){
            ID = s.getRoadID();
            if(!IDs.contains(ID)){
                IDs.add(ID);
            }
        }
        for(RoadSegment s : inSegs){
            ID = s.getRoadID();
            if(!IDs.contains(ID)){
                IDs.add(ID);
            }
        }
        return IDs;
    }
}
