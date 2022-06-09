/* Code for ENGR110 Assignment
 * Name: Christopher Straight
 * Usercode: straigchri
 * ID: 300363269
 */

import ecs100.*;
import java.util.*;

    /**
     * FSM Controller for a simulated Lift.
     * The core of the controller is the signal(String sensor) method
     * which is called by the lift every time a sensor
     * is signalled.
     *
     * Note, when your controller is started, the lift will be on floor 1 with the doors closed.
     * You should set the initial state of the controller to match this.

     */
public class LiftController {
    
    /**
     * The field that stores the current state of the FSM
     */
    private String state = "f1dc";     // initial state should be at floor 1 with the door closed.
    // Note, you probably want to "factor" the state by having additional variables
    // to represent aspects of the state
    /*# YOUR CODE HERE */
    private boolean r1 = false;
    private boolean r2 = false;
    private boolean r3 = false;
    private boolean on1 = false;

    
    /**
     * The field containing the Lift.
     * The signal method will call methods on the lift to operate it
     */
    private Lift lift;  // the lift that is being controlled.

    // possible actions on the lift that you can perform:
    // lift.moveUp()             to start the lift moving up
    // lift.moveDown()           to start the lift moving down
    // lift.stop()               to stop the lift
    // lift.openDoor()           to start the doors opening
    // lift.closeDoor()          to start the doors closing
    // lift.restartTimer(1000)   to set the time for 1000 milliseconds
    // lift.turnWarningLightOn() to turn the warning light on
    // lift.turnWarningLightOff()to turn the warning light off
    
    //states
    //f1dc      floor 1 doors closed
    //f1do      floor 1 doors open
    //f1ow      floor 1 over weight
    //to1       going to floor 1
    
    //f2dc      floor 2 doors closed
    //f2do      floor 2 doors open
    //f2ow      floor 2 over weight
    //to2       going to floor 2
    
    //f3dc      floor 3 doors closed
    //f3do      floor 3 doors open
    //f3ow      floor 3 over weight
    //to3       going to floor 3

    
    /**
     * The Constructor is passed the lift that it is controlling.
     */
    public LiftController(Lift lift) {
        this.lift = lift;
    }
    
    /**
     * Receives a change in a sensor value that may trigger an action and state change.
     * If there is a transition out of the current state associated with this
     * sensor signal, 
     * - it will perform the appropriate action (if any)
     * - it will transition to the next state
     *   (by calling changeToState with the new state).
     *
     * Possible sensor values that you can respond to:
     * (note, you may not need to respond to all of them)
     *   "request1"   "request2"   "request3"
     *   "atF1"   "atF2"   "atF3"
     *   "startUp"   "startDown"
     *    "doorClosed"   "doorOpened"   "doorMoving"
     *    "timerExpired"
     *    "doorSensor"
     *    "withinCapacity"   "overCapacity"
     * 
     * You can either have one big method, or you can break it up into
     * a separate method for each state 
     */
    public void signal(String sensor){
        UI.printf("In state: %s, got sensor: %s%n", state, sensor);
        /*# YOUR CODE HERE */
    
        if (state.equals("f1dc")){//floor 1 doors closed
            checkRequest(sensor);
            lift.restartTimer(10);
            if (r1){
                // do action
                lift.openDoor();
                lift.restartTimer(2000);
                state = "f1do";
            }
            else if (r2){
                // do action
                lift.moveUp();
                state = "to2";
            }
            else if (r3){
                // do action
                lift.moveUp();
                state = "to3";
            }
        }
        else if (state.equals("f1do")){//floor 1 doors open
            checkRequest(sensor);
            r1 = false;
            on1 = true;
            if (sensor.equals("timerExpired")){
                // do action
                lift.closeDoor();
                state = "f1do";
            }
            else if (sensor.equals("doorSensor")){
                // do action
                lift.restartTimer(1000);
                state = "f1do";
            }
            else if (sensor.equals("doorClosed")){
                // do action
                lift.restartTimer(10);
                state = "f1dc";
            }
            else if (sensor.equals("overCapacity")){
                // do action
                lift.turnWarningLightOn();
                state = "f1ow";
            }
        }
        else if (state.equals("f1ow")){//floor 1 overweight
            checkRequest(sensor);
            if (sensor.equals("withinCapacity")){
                // do action
                lift.closeDoor();
                state = "f1ow";
            }
            else if (sensor.equals("doorClosed")){
                // do action
                lift.restartTimer(10);
                lift.turnWarningLightOff();
                state = "f1dc";
            }
        }
        
        
        else if (state.equals("f2dc")){//floor 2 doors closed
            checkRequest(sensor);
            lift.restartTimer(10);
            if (r2){
                // do action
                lift.openDoor();
                lift.restartTimer(2000);
                state = "f2do";
            }
            else if(!on1){//if it was last on floor 3 floor 1 is checked first
                if (r1){
                    // do action
                    lift.moveDown();
                    state = "to1";
                }
                else if (r3){
                    // do action
                    lift.moveUp();
                    state = "to3";
                }
            }
            else if(on1){//if it was last on floor 1 floor 3 is checked first
                if (r3){
                    // do action
                    lift.moveUp();
                    state = "to3";
                }
                else if (r1){
                    // do action
                    lift.moveDown();
                    state = "to1";
                }
            }
        }
        else if (state.equals("f2do")){//floor 2 doors open
            checkRequest(sensor);
            r2 = false;
            if (sensor.equals("timerExpired")){
                // do action
                lift.closeDoor();
                state = "f2do";
            }
            else if (sensor.equals("doorSensor")){
                // do action
                lift.restartTimer(1000);
                state = "f2do";
            }
            else if (sensor.equals("doorClosed")){
                // do action
                lift.restartTimer(10);
                state = "f2dc";
            }
            else if (sensor.equals("overCapacity")){
                // do action
                lift.turnWarningLightOn();
                state = "f2ow";
            }
        }
        else if (state.equals("f2ow")){//floor 2 overweight
            checkRequest(sensor);
             if (sensor.equals("withinCapacity")){
                // do action
                lift.closeDoor();
                state = "f2ow";
            }
            else if (sensor.equals("doorClosed")){
                // do action
                lift.restartTimer(10);
                lift.turnWarningLightOff();
                state = "f2dc";
            }
        }
        
        
        else if (state.equals("f3dc")){//floor 3 doors closed
            checkRequest(sensor);
            lift.restartTimer(10);
            if (r3){
                // do action
                lift.openDoor();
                lift.restartTimer(2000);
                state = "f3do";
            }
            else if (r2){
                // do action
                lift.moveDown();
                state = "to2";
            }
            else if (r1){
                // do action
                lift.moveDown();
                state = "to1";
            }
        }
        else if (state.equals("f3do")){//floor 3 doors open
            checkRequest(sensor);
            r3 = false;
            on1 = false;
            if (sensor.equals("timerExpired")){
                // do action
                lift.closeDoor();
                state = "f3do";
            }
            else if (sensor.equals("doorSensor")){
                // do action
                lift.restartTimer(1000);
                state = "f3do";
            }
            else if (sensor.equals("doorClosed")){
                // do action
                lift.restartTimer(10);
                state = "f3dc";
            }
            else if (sensor.equals("overCapacity")){
                // do action
                lift.turnWarningLightOn();
                state = "f3ow";
            }
        }
        else if (state.equals("f3ow")){//floor 3 overweight
            checkRequest(sensor);
             if (sensor.equals("withinCapacity")){
                // do action
                
                lift.closeDoor();
                state = "f3ow";
            }
            else if (sensor.equals("doorClosed")){
                // do action
                lift.restartTimer(10);
                lift.turnWarningLightOff();
                state = "f3dc";
            }
        }
        
        else if(state.equals("to1")){
            if (sensor.equals("atF1")){
                // do action
                lift.stop();
                lift.restartTimer(10);
                state = "f1dc";
            }
        }
        else if(state.equals("to2")){
            if (sensor.equals("atF2")){
                // do action
                lift.stop();
                lift.restartTimer(10);
                state = "f2dc";
            }
        }
        else if(state.equals("to3")){
            if (sensor.equals("atF3")){
                // do action
                lift.stop();
                lift.restartTimer(10);
                state = "f3dc";
            }
        }
    }
    
    public void checkRequest(String sensor){
        if (sensor.equals("request1")){
            // do action
            r1 = true;
        }
        else if (sensor.equals("request2")){
            // do action
            r2 = true;
        }
        else if (sensor.equals("request3")){
            // do action
            r3 = true;
        }
    }
}
