package code;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.*;
import java.io.*;
import java.util.List;

/**
 * Created on 11/03/2017.
 * Name: Christopher Straight
 * Usercode: straigchri
 * ID: 300363269
 */
public class AucklandMaps extends GUI{

    private final Map<Integer,Node> Nodes = new HashMap<>();
    private final Map<Integer,Road> Roads = new HashMap<>();
    private final List<RoadSegment> Segments = new ArrayList<>();
    private final List<Polygon> Polygons = new ArrayList<>();
    private final Trie Names = new Trie();
    private final List<Node> selectedNodes = new ArrayList<>();
    private final List<Road> selectedRoads = new ArrayList<>();

    private Location origin = new Location(0, 0);
    private int scale = 10;
    private final int moveFactor = 100;
    private final Point center = new Point((int)(getDrawingAreaDimension().getHeight()/2),(int)(getDrawingAreaDimension().getWidth()/2));

    public AucklandMaps(){
        getTextOutputArea().setText("select a map to load");
    }

    @Override
    protected void redraw(Graphics g) {
        for(Polygon poly : Polygons){
            poly.draw(g, origin, scale);
        }
        g.setColor(Color.black);
        for(RoadSegment seg : Segments){
            seg.draw(g, origin, scale);
        }
        for(Node n : selectedNodes){
            n.draw(g, origin, scale);
        }
        for(Road r : selectedRoads){
            r.draw(g, origin, scale);
        }
        //getTextOutputArea().setText("scale: "+scale+"\n"+origin.x+" "+origin.y);
    }

    @Override
    protected void onClick(MouseEvent e) {
        if(e.getButton()==1) {
            selectedNodes.clear();
            selectedRoads.clear();
            Location l = Location.newFromPoint(new Point(e.getX(), e.getY()), origin, scale);
            findClose(l);
        }else if(e.getButton()==3){
            int xOffset = (int)(e.getX()-center.getX());
            int yOffset = (int)(e.getY()-center.getY());
            move(xOffset,yOffset);
        }
    }

    @Override
    protected void onSearch() {
        selectedNodes.clear();
        selectedRoads.clear();
        getTextOutputArea().setText("");
        String prefix = getSearchBox().getText();
        if(!prefix.equals("")) {
            List<Integer> IDs = Names.getAllWith(prefix);
            String s = "";
            Road r;
            for (Integer integer : IDs) {
                r = Roads.get(integer);
                if (r.getLabel().equals(prefix)) {
                    selectedRoads.clear();
                    s = r.getLabel();
                    selectedRoads.add(r);
                    break;
                }
                s += (r.getLabel() + "\n");
                selectedRoads.add(r);
            }
            getTextOutputArea().setText(s);
            redraw();
        }
    }

    @Override
    protected void onMove(Move m) {
        if (m==Move.ZOOM_IN && scale<=1000){zoom(center,scale/2);}
        else if (m==Move.ZOOM_OUT && scale>2){zoom(center,-(scale/2));}
        else if (m==Move.NORTH){move(0,-moveFactor);}
        else if (m==Move.SOUTH){move(0,moveFactor);}
        else if (m==Move.EAST){move(moveFactor,0);}
        else if (m==Move.WEST){move(-moveFactor,0);}
    }

    @Override
    protected void onLoad(File nodes, File roads, File segments, File polygons) {
        Nodes.clear();
        Roads.clear();
        Segments.clear();
        Names.clear();
        origin = new Location(0, 0);
        scale = 10;
        try {
            BufferedReader br = new BufferedReader(new FileReader(nodes));
            while(br.ready()) {
                String info = br.readLine();
                String[] parts = info.split("\t");
                int nodeID = Integer.parseInt(parts[0]);
                double latitude = Double.parseDouble(parts[1]);
                double longitude = Double.parseDouble(parts[2]);
                Location Loc = Location.newFromLatLon(latitude, longitude);
                Nodes.put(nodeID, new Node(nodeID, Loc));
            }
            br.close();
        } catch (IOException e1) {e1.printStackTrace();}
       // System.out.println("nodes successfully loaded");
        try {
            BufferedReader br = new BufferedReader(new FileReader(roads));
            br.readLine(); //skips the first line containing the variable titles
            while(br.ready()) {
                String info = br.readLine();
                String[] parts = info.split("\t");
                int roadID = Integer.parseInt(parts[0]);
                int type = Integer.parseInt(parts[1]);
                String label = parts[2];
                String city = parts[3];
                boolean oneWay = parts[4].equals("1");
                int speed = Integer.parseInt(parts[5]);
                int roadClass = Integer.parseInt(parts[6]);
                boolean notForCar = parts[4].equals("1");
                boolean notForPede = parts[4].equals("1");
                boolean notForBicy = parts[4].equals("1");
                Roads.put(roadID, new Road(roadID, type, label, city, oneWay, speed, roadClass, notForCar, notForPede, notForBicy));
                Names.put(label,roadID);
            }
            br.close();
        } catch (IOException e1) {e1.printStackTrace();}
        //System.out.println("roads successfully loaded");
        try {
            BufferedReader br = new BufferedReader(new FileReader(segments));
            br.readLine(); //skips the first line containing the variable titles
            while(br.ready()) {
                String info = br.readLine();
                String[] parts = info.split("\\s+");
                RoadSegment seg = new RoadSegment(parts);
                Segments.add(seg);
                Road r = Roads.get(Integer.parseInt(parts[0]));
                Node n1 = Nodes.get(Integer.parseInt(parts[2]));
                Node n2 = Nodes.get(Integer.parseInt(parts[3]));
                if (r!=null)r.addSegment(seg);
                if (n1!=null)n1.addOutSeg(seg);
                if (n2!=null)n2.addInSeg(seg);
                assert r != null;
                if (!r.isOneWay()){
                    if (n1!=null)n1.addInSeg(seg);
                    if (n2!=null)n2.addOutSeg(seg);
                }
            }
            br.close();
        } catch (IOException e1) {e1.printStackTrace();}

        if(polygons!=null) {
            try {
                BufferedReader br = new BufferedReader(new FileReader(polygons));
                int type;
                int endLevel;
                String label;
                int cityIdx;
                List<Location> locations;

                String info;
                while (br.ready()) {
                    info = br.readLine();
                    if (info.equals("[POLYGON]")) {
                        type = 0;
                        endLevel = 0;
                        label = null;
                        cityIdx = 0;
                        while (true) {
                            info = br.readLine();
                            if (info.equals("[END]")) {
                                break;
                            } else {
                                String[] parts = info.split("=");
                                switch (parts[0]) {
                                    case "Type":
                                        type = Integer.decode(parts[1]);
                                        break;
                                    case "EndLevel":
                                        endLevel = Integer.parseInt(parts[1]);
                                        break;
                                    case "CityIdx":
                                        cityIdx = Integer.parseInt(parts[1]);
                                        break;
                                    case "Label":
                                        label = parts[1];
                                        break;
                                    case "Data0":
                                        locations = polyStringToLocations(parts[1]);
                                        Polygons.add(new Polygon(type, label, endLevel, cityIdx, locations));
                                        break;
                                }
                            }
                        }
                    }
                }
                br.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        getTextOutputArea().setText("Map successfully loaded");
    }

    @Override
    protected void onScroll(MouseWheelEvent e) {
        Point p = new Point(e.getX(), e.getY());
        if (e.getWheelRotation() == 1 && scale>2) {
            zoom(p, -(e.getScrollAmount()));
        } else if(scale<1000) {
            zoom(p, e.getScrollAmount());
        }
        if (scale<=2){
            scale = 2;
        }else if(scale>1000){
            scale = 1000;
        }
    }

    private List<Location> polyStringToLocations(String info){
        List<Location> Locations = new ArrayList<>();
        String[] parts = info.split(",");
        for (int i = 0; i<parts.length;i+=2) {
            Locations.add(Location.newFromLatLon(Double.parseDouble(parts[i].substring(1, parts[i].length())), Double.parseDouble(parts[i + 1].substring(0, parts[i + 1].length() - 1))));
        }
        return Locations;
    }

    private void move(int x, int y){
        Point p = origin.asPoint(origin,scale);
        p.x+=x;
        p.y+=y;
        origin = Location.newFromPoint(p, origin, scale);
    }

    private void zoom(Point p, int s){
        Location l = Location.newFromPoint(p, origin, scale);
        scale += s;
        Point point = l.asPoint(origin, scale);
        int xOffset = (int)(point.getX()-p.getX());
        int yOffset = (int)(point.getY()-p.getY());
        move(xOffset,yOffset);
        redraw();
    }

    private void findClose(Location l){
        for(int i=0;i<Nodes.size();i++){
            System.out.println("check");
            Node n = Nodes.get(Nodes.keySet().toArray()[i]);
            Location nl = n.getLoc();
            if(nl.isClose(l,0.1)){
                System.out.println("found");
                selectedNodes.add(n);
                String s = ("Node ID: "+n.getNodeID()+"\n"+"Roads: ");
                List<Integer> IDs = n.roadIDs();
                Road r;
                for (Integer integer : IDs) {
                    r = Roads.get(integer);
                    s+=(r.getLabel()+"\n");
                    selectedRoads.add(r);
                }
                getTextOutputArea().setText(s);
                redraw();
                break;
            }
        }
        System.out.println("done");
    }

    public static void main(String[] args) {
        new AucklandMaps();
    }
}
