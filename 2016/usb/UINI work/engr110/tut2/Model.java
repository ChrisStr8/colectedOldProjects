import ecs100.*;

/**
 * Write a description of class Model here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Model
{
    // instance variables - replace the example below with your own
    public double plantC = 500;
    public double lineC = 1500;
    public double cost1;
    public double cost2;
    public double losses;
    public double demand;

    /**
     * Constructor for objects of class Model
     */
    public Model(){
        // initialise instance variables
        UI.initialise();
        UI.addButton("Calculate", this::calculate);
        UI.addButton("Quit", UI::quit);
    }
    
    public void calculate(){
        cost1 = UI.askDouble("Plant one cost");
        cost2 = UI.askDouble("Plant two cost");
        demand = UI.askDouble("demand (>500)");
        losses = UI.askDouble("Losses (<0)");
        double output = calcOutput();
        double cost = calcCost(output);
        UI.println("Output: "+output+"MW");
        UI.println("Cost: $"+cost);
    }
    
    public double calcOutput(){
        double output = 0;
        output = demand - plantC;
        if(output>plantC){output = plantC;}
        double loss = output*losses;
        output += loss;
        return output;
    }

    /**
     * calculate the cost
     */
    public double calcCost(double output){
        // put your code here
        double cost = (plantC*24)*cost1;
        cost += (output*24)*cost2;
        return cost;
    } 
}
