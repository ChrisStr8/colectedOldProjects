package code;

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * Created on 14/03/2017.
 * Name: Christopher Straight
 * Usercode: straigchri
 * ID: 300363269
 */
public class Polygon {

    private final int Type;
    private final String Label;
    private final int EndLevel;
    private final int CityIdx;
    private List<Location> Locations = new ArrayList<>();

    public Polygon(int type, String label, int endLevel, int cityIdx, List<Location> locations) {
        Type = type;
        Label = label;
        EndLevel = endLevel;
        CityIdx = cityIdx;
        Locations = locations;
    }

    public void draw(Graphics g, Location origin, int scale){
        int nPoints = Locations.size();
        int[] xPoints = new int[nPoints];
        int[] yPoints = new int[nPoints];
        int i = 0;
        for(Location location : Locations){
            Point p = location.asPoint(origin, scale);
            xPoints[i]=(int)p.getX();
            yPoints[i]=(int)p.getY();
            i++;
        }
        setPolyColour(g);
        g.fillPolygon(xPoints, yPoints, nPoints);
    }

    public int getType() {
        return Type;
    }

    public int getEndLevel() {
        return EndLevel;
    }

    public int getCityIdx() {
        return CityIdx;
    }

    public String getLabel() {
        return Label;
    }

    private void setPolyColour(Graphics g){
        if(Type>=1 && Type<=3){g.setColor(new Color(234, 234, 234));}//city
        else if(Type==4){g.setColor(new Color(222, 199, 199));}//military
        else if(Type==5 || Type==6){g.setColor(new Color(222, 199, 199));}//car park
        else if(Type==7){g.setColor(new Color(200, 219, 222));}//airport
        else if(Type==8){g.setColor(new Color(200, 219, 222));}//shopping center
        else if(Type==10){g.setColor(new Color(200, 219, 222));}//university
        else if(Type==11){g.setColor(new Color(200, 219, 222));}//hospital
        else if(Type==14){g.setColor(new Color(200, 219, 222));}//airport runway
        else if(Type==19){g.setColor(new Color(195, 195, 195));}//man made area
        else if(Type>=20 && Type<=22){g.setColor(new Color(166, 230, 145));}//national park
        else if(Type==23){g.setColor(new Color(203, 230, 163));}//city park
        else if(Type==24){g.setColor(new Color(195, 195, 195));}//golf
        else if(Type==25){g.setColor(new Color(195, 195, 195));}//sport
        else if(Type==26){g.setColor(new Color(118, 118, 118));}//cemetery
        else if(Type==30 || Type==31){g.setColor(new Color(130, 230, 154));}//state park
        else if(Type==40){g.setColor(new Color(135, 175, 255));}//ocean
        else if(Type==59){g.setColor(new Color(0, 0, 255));}//blue-unknown
        else if(Type>=60 && Type<=68){g.setColor(new Color(163, 204, 255));}//lake
        else if(Type==69){g.setColor(new Color(0, 0, 255));}//blue-unknown
        else if(Type>=70 && Type<=73){g.setColor(new Color(148, 184, 255));}//river
        else if(Type==80){g.setColor(new Color(35, 126, 40));}//woods
        else {g.setColor(new Color(255, 0, 0));}//unknown
    }
}
