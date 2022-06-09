package code;

import java.util.HashMap;
import java.util.Map;

/**
 * Created on 3/06/2017.
 * Name: Christopher Straight
 * Usercode: straigchri
 * ID: 300363269
 */
public class LeafNode implements Node {
    private String name;
    private String code;
    private int probability;

    public LeafNode(String character, int probability){
        this.name = character;
        this.probability = probability;
    }

    @Override
    public int probability() {
        return probability;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public String code() {
        return code;
    }

    @Override
    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public Map<String, String> getNamesToCodes() {
        Map<String, String> codes = new HashMap<>();
        codes.put(name, code);
        return codes;
    }

    @Override
    public Map<String, String> getCodesToNames() {
        Map<String, String> names = new HashMap<>();
        names.put(code, name);
        return names;
    }
}
