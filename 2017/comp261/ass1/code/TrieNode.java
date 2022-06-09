package code;

import java.util.*;

/**
 * Created on 19/03/2017.
 * Name: Christopher Straight
 * Usercode: straigchri
 * ID: 300363269
 */
class TrieNode {
    public final HashMap<Character, TrieNode> Children = new HashMap<>();
    private int RoadID = -1;

    public TrieNode() {}

    public void setRoadID(int roadID) {
        RoadID = roadID;
    }

    public boolean isRoad() {
        return RoadID!=-1;
    }

    public int getRoadID() {
        return RoadID;
    }

    public HashMap<Character, TrieNode> getChildren() {
        return Children;
    }

    public void addChild(Character c, TrieNode n){
        Children.put(c,n);
    }

    public void getAllFromNode(TrieNode t, List<Integer> roads){
        if(t.isRoad()) {
            roads.add(t.getRoadID());
        }
        Set<Character> keys = Children.keySet();
        TrieNode n;
        for (Character c : keys) {
            n = Children.get(c);
            n.getAllFromNode(n, roads);
        }
    }
}
