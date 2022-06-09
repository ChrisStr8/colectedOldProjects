package code;

import java.util.Map;

/**
 * Created on 3/06/2017.
 * Name: Christopher Straight
 * Usercode: straigchri
 * ID: 300363269
 */
public interface Node {

    public int probability();

    public String name();

    public String code();

    public void setCode(String code);

    public Map<String, String> getNamesToCodes();

    public Map<String, String> getCodesToNames();
}
