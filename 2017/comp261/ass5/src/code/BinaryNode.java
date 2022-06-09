package code;

import java.util.HashMap;
import java.util.Map;

/**
 * Created on 3/06/2017.
 * Name: Christopher Straight
 * Usercode: straigchri
 * ID: 300363269
 */
public class BinaryNode implements Node{
    private Node left;
    private Node right;
    private String name;
    private String code;
    private int probability;

    public BinaryNode(String name, int probability, Node left, Node right){
         this.name = name;
         this.probability = probability;
         this.left = left;
         this.right = right;
    }
    public int probability(){
        return probability;
    }

    @Override
    public String name(){
        return name;
    }

    @Override
    public String code(){
        return code;
    }

    @Override
    public void setCode(String code){
        this.code = code;
        left.setCode(code+"0");
        right.setCode(code+"1");
    }

    @Override
    public Map<String, String> getNamesToCodes() {
        Map<String, String> codes = new HashMap<>();
        codes.putAll(left.getNamesToCodes());
        codes.putAll(right.getNamesToCodes());
        return codes;
    }

    @Override
    public Map<String, String> getCodesToNames() {
        Map<String, String> names = new HashMap<>();
        names.putAll(left.getCodesToNames());
        names.putAll(right.getCodesToNames());
        return names;
    }

    public Node getLeft() {
        return left;
    }

    public Node getRight() {
        return right;
    }
}
