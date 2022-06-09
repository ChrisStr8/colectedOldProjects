// This program is copyright VUW.
// You are granted permission to use it to construct your answer to a COMP102 assignment.
// You may not distribute it in any other way without permission.

/* Code for Assignment 6 COMP102
 * Name:
 * Usercode:
 * ID:
 */

import ecs100.*;
import java.awt.Color;

/** A CameraAperture object represents the aperture of a camera, which can be opened or closed.
 *   It is drawn using a cicle outline of the camera lens, and a solid black circle
 *     representing the aperture. The aperture may be varied (in small steps) between wide open,
 *     (about 80% of the camera lens) and closed (about 20% of the camera lens)
 *   The CameraAperture object needs to remember
 *     - the position of the lens (which doesn't change), and
 *     the width of the aperture (which does change)
 *   The constructor requires two arguments:
 *      the x and y position of the lens
 *   The aperture is initially wide open
 *
 *   The class has two methods:
 *      open(),  which opens the aperture by a further 10% (unless it is fully open)
 *      close(), which closes the aperture by a further 10% (unless it is fully closed)
 *   They don't need to redraw the lens outline
 */
public class CameraAperture{

    public static final double LENS_WIDTH = 100;   // width of the whole lens

    // fields for the position of the center of the lens and for the width of the aperture.
    /*# YOUR CODE HERE */

    //Constructor: passed the center of the lens. Draws the camera
    // (rectangle for camera, circle for lens, black circle for aperature.
    /*# YOUR CODE HERE */

    /** open method (no parameters)
     * If current width less than 80% of LENS_WIDTH, increase it by 10% of LENS_WIDTH
     * redraws the aperture
     */
    /*# YOUR CODE HERE */

    /** close method (no parameters)
     * if current width greather than 20% of LENS_WIDTH, decrease it by 10% of LENS_WIDTH
     * redraws the aperture
     */
    /*# YOUR CODE HERE */
}
