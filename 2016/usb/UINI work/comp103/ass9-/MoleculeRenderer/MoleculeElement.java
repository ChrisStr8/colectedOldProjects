// This program is copyright VUW.
// You are granted permission to use it to construct your answer to a COMP103 assignment.
// You may not distribute it in any other way without permission.

/* Code for COMP103, Assignment 9
 * Name: Christopher Straight
 * Usercode: straigchri
 * ID: 300363269
 */

import ecs100.*;
import java.awt.Color;
import java.util.*;

/** 
 *  Represents information about an atom in a molecule.
 *  
 *  The information includes
 *   - the 3D position of the atom
 *     (relative to the molecule).
 *     x is measured from the left to the right
 *     y is measured from the top to the bottom
 *     z is measured from the front to the back.
 *   - The color of the atom should be rendered.
 *   - The radius of the atom should be rendered.
 *   
 *  The positions come from the molecule file,
 *  the last two values come from the atom-definitions file.
 */

public class MoleculeElement {

    // coordinates of center of atom, relative to top left front corner
    private double x;       // distance to the right
    private double y;       // distance down
    private double z;       // distance away

    private double xOffset = 200;
    private double yOffset = 200;

    private Color color;   // color of the atom
    private double radius;  // radius

    /** Constructor: requires the position, color, and radius */
    public MoleculeElement (double x, double y, double z, Color color, double radius) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.color = color;
        this.radius = radius;
    }

    public void setXOffset(double offset){xOffset=offset;}

    public void setYOffset(double offset){yOffset=offset;}

    public double getX() {return x;}

    public double getY() {return y;}

    public double getZ() {return z;}

    /** 
     * @param other another atom to check visibility against
     * @param Xangle the viewing Xangle to be used in the calculation
     * @return negative number if this atom is behind "other", when viewed
     *  from the specified position (in degrees). 
     *  0 degrees corresponds to viewing from the front;
     *  90 degrees corresponds to viewing from the right, etc. 
     */
    /*
    public int furtherThan(MoleculeElement other, double Xangle, double Yangle) {
        double Xradian = Xangle * Math.PI / 180;
        double Yradian = Yangle * Math.PI / 180;

        double otherZcordAfterRotation = other.z*Math.cos(Xradian) - other.x*Math.sin(Xradian);
        double thisZcordAfterRotation = z*Math.cos(Xradian) - x*Math.sin(Xradian);

        double otherYcordAfterRotation = other.z*Math.cos(Yradian) - other.y*Math.sin(Yradian);
        double thisYcordAfterRotation = z*Math.cos(Yradian) - y*Math.sin(Yradian);
        return (int)((otherZcordAfterRotation - thisZcordAfterRotation)-(otherYcordAfterRotation - thisYcordAfterRotation));
    }
    */

    public int XfurtherThan(MoleculeElement other, double Xangle) {
        double Xradian = Xangle * Math.PI / 180;
        double otherZcordAfterRotation = other.z*Math.cos(Xradian) - other.x*Math.sin(Xradian);
        double thisZcordAfterRotation = z*Math.cos(Xradian) - x*Math.sin(Xradian);
        return (int)(otherZcordAfterRotation - thisZcordAfterRotation);
    }

    public int YfurtherThan(MoleculeElement other, double Yangle) {
        double Yradian = Yangle * Math.PI / 180;
        double otherYcordAfterRotation = other.z*Math.cos(Yradian) - other.y*Math.sin(Yradian);
        double thisYcordAfterRotation = z*Math.cos(Yradian) - y*Math.sin(Yradian);
        return (int)(otherYcordAfterRotation - thisYcordAfterRotation);
    }

    /** 
     *  Renders the atom on the graphics pane, from the specified angle (degrees)
     *  with the specified size and color.
     *  
     *  The angle is an angle in the horizontal plane, corresponding
     *  to an angle as the user walks around a model of the molecule.
     *     0 degrees corresponds to viewing from the front;
     *   90 degrees corresponds to viewing from the right;
     */
    public void render(double Xangle, double Yangle) {
        double Xradian = Xangle * Math.PI / 180;
        double Yradian = Yangle * Math.PI / 180;
        double XcordAfterRotation = 0;
        double ycordAfterRotation = 0;
        double diam =  radius*2;

        // The vertical coordinate on the graphics pane is the y coordinate of the atom
        ycordAfterRotation = z*Math.sin(Yradian) + y*Math.cos(Yradian) - radius;

        // The horizontal coordinate on the graphics pane is given by x, z,
        // and the angle.
        // angle is 0 if we are looking from the front.
        // horizontal coordinate = x * cos(Xradian) + z * sin(Xradian)
        XcordAfterRotation = z*Math.sin(Xradian) + x*Math.cos(Xradian) - radius;

        UI.setColor(color);
        UI.fillOval(XcordAfterRotation+xOffset, ycordAfterRotation+yOffset, diam, diam);
        UI.setColor(Color.black);
        UI.drawOval(XcordAfterRotation+xOffset, ycordAfterRotation+yOffset, diam, diam);

        // The mathematics of this is
        // fairly straightforward if you draw a diagram.
        // C.f. "Rotation matrix" in linear algebra.
    }
}
