import ecs100.*;
import java.util.*;

    /**
     * A FSM controller for an Intersection.
     * The core of the controller is the signal(String sensor) method
     * which is called by the intersection every time a sensor
     * is signalled.
     */
public class LightsController {
    
    /**
     * The field that stores the current state of the FSM
     */
    private String state = "EWgoReady";
    // Note, you may wish to "factor" the state by having additional variables
    // to represent aspects of the state

    
    /**
     * The field containing the Intersection.
     * The signal method will call methods on the intersection to
     * change the lights
     */
    private Intersection intersection;  // the intersection that is being controlled.

    // possible actions that you can perform on the intersection:
   
    // to set the E-W lights
    //   intersection.turnEWred()
    //   intersection.turnEWamber()
    //   intersection.turnEWgreen()

    // to set the N-S lights
    //   intersection.turnNSred()     
    //   intersection.turnNSamber()
    //   intersection.turnNSgreen()
    
    // to set the pedestrian lights
    //   intersection.turnEWpedStop() to set the E-W Pedestrian lights
    //   intersection.turnEWpedGo()
    //   intersection.turnNSpedStop() to set the N-S Pedestrian lights
    //   intersection.turnNSpedGo()

    // to restart the timer, eg for 1 second (1000 milliseconds)
    //   intersection.restartTimer(1000)  


    /**
     * The Constructor is passed the intersection that it is controlling.
     */
    public LightsController(Intersection intersection) {
        this.intersection = intersection;
    }
    

    /**
     * Receives a change in a sensor value that may trigger an action and state change.
     * If there is a transition out of the current state associated with this
     * sensor signal, 
     * - it will perform the appropriate action (if any)
     * - it will transition to the next state
     *   (by changing the state field
     * You can either have one big method, or you can break it up into
     * a separate method for each state 
     *
     * possible sensor values that you should respond to:
     *    "carEW"  "carNS"  "pedEW"  "pedNS"  "timerExpired"
     *
     * The initial version given here is just a two state FSM which switches
     * state (and changes the lights) whenever a car is sensed.
     * It does not respond to the timer at all!
     * This is a bad controller!! (It needs more states, and more actions.)
     */
    public void signal(String sensor){
        UI.println("in state: "+ state + ",  got sensor: "+ sensor);
        // actions and transitions from the EWgo state
        if (state.equals("EWgoReady")){
            if (sensor.equals("carNS")){
                intersection.turnEWamber();
                intersection.restartTimer(3000);
                state = "EWstoping";
            }
            //else if (sensor.equals("....
        }
        // actions and transitions from the NSgo state
        else if (state.equals("EWstoping")){
            if (sensor.equals("carEW")){
                state = "EWstopingCarEW";
            }
            else if (sensor.equals("timer")){
                intersection.turnEWred();
                intersection.turnNSgreen();
                intersection.restartTimer(15000);
            }
        }
        else if (state.equals("EWstopingCarEW")){
            if (sensor.equals("timerExpired")){
                intersection.turnEWred();
                intersection.turnNSgreen();
                intersection.restartTimer(15000);
                state = "NSgoMustChange";
            }
        }
        else if (state.equals("NSgo")){
            if (sensor.equals("carEW")){   ;
                state = "NSgoMustChange";
            }
            else if (sensor.equals("timerExpired")){
                state = "NSgoReady";
            }
        }
        else if (state.equals("NSgoMustChange")){
            if (sensor.equals("timerExpired")){
                intersection.turnNSamber();
                intersection.restartTimer(3000);
                state = "NSstoping";
            }
        }
        
        //NS
        else if (state.equals("NSgoReady")){
            if (sensor.equals("carNS")){
                intersection.turnNSamber();
                intersection.restartTimer(3000);
                state = "NSstoping";
            }
        }
        else if (state.equals("NSstoping")){
            if (sensor.equals("carNS")){
                state = "NSstopingCarNS";
            }
            else if (sensor.equals("timerExpired")){
                intersection.turnNSred();
                intersection.turnEWgreen();
                intersection.restartTimer(15000);
                state = "EWgo";
            }
        }
        else if (state.equals("NSstopingCarNS")){
            if (sensor.equals("timerExpired")){
                intersection.turnNSred();
                intersection.turnEWgreen();
                intersection.restartTimer(15000);
                state = "EWgoMustChange";
            }
        }
        else if (state.equals("EWgo")){
            if (sensor.equals("carNS")){
                state = "EWgoMustChange";
            }
            else if (sensor.equals("timerExpired")){
                state = "EWgoReady";
            }
        }
        else if (state.equals("EWgoMustChange")){
            if (sensor.equals("timerExpired")){
                intersection.turnEWamber();
                intersection.restartTimer(3000);
                state = "EWstoping";
            }
        }
    }
    



    public void reset(){
        state = "EWgoReady";
    }



    public static void main(String[] args){
        LightsSimulation.main(args);
    }
        
}
