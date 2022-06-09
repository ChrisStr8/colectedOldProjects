package code;

import java.util.*;

/**
 * Created on 19/03/2017.
 * Name: Christopher Straight
 * Usercode: straigchri
 * ID: 300363269
 */
public class Trie {
    private TrieNode root;

    public Trie() {
        root = new TrieNode();
    }

    public void clear(){
        root = new TrieNode();
    }

    public void put(String word, int roadID) {
        TrieNode node = root;
        HashMap<Character, TrieNode> children = node.getChildren();
        if(!contains(word)) {
            for (int i = 0; i < word.length(); i++) {
                char c = word.charAt(i);
                if (!children.containsKey(c)) {
                    node.addChild(c, new TrieNode());
                }
                node = children.get(c);
                children = node.getChildren();
            }
            node.setRoadID(roadID);
        }
    }


    public boolean contains(String w) {
        return getNode(w) != null; //returns true if getNode returns a node
    }

    public List<Integer> getAllWith(String prefix) {
        List<Integer> roads = new ArrayList<>();
        TrieNode t = getNode(prefix);
        if(t!=null) {
            t.getAllFromNode(t,roads);
        }
        return roads;
    }

    public int get(String key){
        TrieNode t = getNode(key);
        if(t==null){
            return -1;
        }
        return t .getRoadID();
    }

    private TrieNode getNode(String key) {
        TrieNode t = root;
        Map<Character, TrieNode> children = t.getChildren();
        for (int i = 0; i < key.length(); i++) {
            char c = key.charAt(i);
            if (children.containsKey(c)) {
                t = children.get(c);
                children = t.getChildren();
            } else {
                return null;
            }
        }
        return t;
    }
}