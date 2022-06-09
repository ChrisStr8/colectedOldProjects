/**
 * Created on 15/04/2017.
 * Name: Christopher Straight
 * Usercode: straigchri
 * ID: 300363269
 */
public class Restriction {
    private int node1;
    private int road1;

    private int node2;
    private int road2;

    public Restriction(int node1, int road1, int node2, int road2){
        this.node1 = node1;
        this.road1 = road1;
        this.node2 = node2;
        this.road2 = road2;
    }

    public int getNode1() {
        return node1;
    }

    public int getRoad1() {
        return road1;
    }

    public int getNode2() {
        return node2;
    }

    public int getRoad2() {
        return road2;
    }
}
