package code;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created on 11/03/2017.
 * Name: Christopher Straight
 * Usercode: straigchri
 * ID: 300363269
 */
public class RoadSegment {
    private final int RoadID;
    private final double Length;
    private final int NodeID1;
    private final int NodeID2;
    private final List<Location> Locations = new ArrayList<>();

    public RoadSegment(String[] parts) {
        RoadID = Integer.parseInt(parts[0]);
        Length = Double.parseDouble(parts[1]);
        NodeID1 = Integer.parseInt(parts[2]);
        NodeID2 = Integer.parseInt(parts[3]);
        int i = 4;
        int n = 6;
        while(parts.length>i+1) {
            Locations.add(Location.newFromLatLon(Double.parseDouble(parts[i]), Double.parseDouble(parts[i+1])));
            i+=2;
        }
    }

    public void draw(Graphics g, Location origin, int scale){
        Location previous = Locations.get(1);
        for(Location location : Locations){
            Point p = previous.asPoint(origin, scale);
            Point l = location.asPoint(origin, scale);
            g.drawLine((int) p.getX(), (int) p.getY(), (int) l.getX(), (int) l.getY());
            previous = location;
        }
    }

    public void drawHighlighted(Graphics g, Location origin, int scale){
        g.setColor(Color.red);
        draw(g,origin,scale);
    }

    public int getRoadID() {
        return RoadID;
    }

    public double getLength() {
        return Length;
    }

    public int getNodeID1() {
        return NodeID1;
    }

    public int getNodeID2() {
        return NodeID2;
    }
}
