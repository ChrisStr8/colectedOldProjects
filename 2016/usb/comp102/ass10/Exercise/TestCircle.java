// This program is copyright VUW.
// You are granted permission to use it to construct your answer to a COMP102 assignment.
// You may not distribute it in any other way without permission.

/* Code for COMP102 Assignment 10
 */

import ecs100.*;

public class TestCircle{
    public static void main(String[] arguments){
        Circle obj = new Circle( 100, 100);
        obj.redraw();
        UI.repaintGraphics();
        UI.sleep(1000);

        UI.println("moving it to 200,300");
        UI.sleep(300);
        obj.setPos(200,300);
        UI.clearGraphics();
        obj.redraw();
        UI.repaintGraphics();
    }

}
