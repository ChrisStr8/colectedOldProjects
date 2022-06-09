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
public class Road {
    private final int RoadID;
    private final int Type;
    private final String Label;
    private final String City;
    private final boolean OneWay;
    private final int Speed;
    private final int RoadClass;
    private final boolean NotForCar;
    private final boolean NotForPede;
    private final boolean NotForBicy;
    private final List<RoadSegment> segments = new ArrayList<>();


    public Road(int roadID, int type, String label, String city, boolean oneWay, int speed, int roadClass, boolean notForCar, boolean notForPede, boolean notForBicy) {
        RoadID = roadID;
        Type = type;
        Label = label;
        City = city;
        OneWay = oneWay;
        Speed = speed;
        RoadClass = roadClass;
        NotForCar = notForCar;
        NotForPede = notForPede;
        NotForBicy = notForBicy;
    }

    public void addSegment(RoadSegment r){
        segments.add(r);
    }

    public boolean isOneWay() {
        return OneWay;
    }

    public int getSpeed() {
        return Speed;
    }

    public int getRoadClass() {
        return RoadClass;
    }

    public boolean isNotForCar() {
        return NotForCar;
    }

    public boolean isNotForPede() {
        return NotForPede;
    }

    public boolean isNotForBicy() {
        return NotForBicy;
    }

    public String getCity() {
        return City;
    }

    public String getLabel() {
        return Label;
    }

    public int getType() {
        return Type;
    }

    public int getRoadID() {
        return RoadID;
    }

    public void draw(Graphics g, Location origin, int scale){
        for(RoadSegment seg : segments){
            seg.drawHighlighted(g, origin, scale);
        }
    }
}
