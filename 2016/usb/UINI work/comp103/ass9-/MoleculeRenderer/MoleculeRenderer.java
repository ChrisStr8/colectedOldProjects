// This program is copyright VUW.
// You are granted permission to use it to construct your answer to a COMP103 assignment.
// You may not distribute it in any other way without permission.

/* Code for COMP103, Assignment 9
 * Name: Christopher Straight
 * Usercode: straigchri
 * ID: 300363269
 */

import ecs100.*;
import java.util.*;
import java.io.*;
import java.lang.*;

/** 
 *  Renders a molecule on the graphics pane from different positions.
 *  
 *  A molecule consists of a collection of molecule elements, i.e., atoms.
 *  Each atom has a type (eg, Carbon, or Hydrogen, or Oxygen, ..),
 *  and a three dimensional position in the molecule (x, y, z).
 *
 *  Each molecule is described in a file by a list of molecule elements (atoms) and their positions.
 *  The molecule is rendered by drawing a colored circle for each atom.
 *  The size and color of each atom is determined by the type of the atom.
 * 
 *  The description of the size and color for rendering the different types of
 *  atoms is stored in the file "atom-definitions.txt" which should be read and
 *  stored in a Map.  When an atom is rendered, the type should be looked up in
 *  the map to find the size and color to pass to the atom's render() method
 * 
 *  A molecule can be rendered from different perspectives, and the program
 *  provides four buttons to control the perspective of the rendering.
 *  
 *  For interpreting the viewing positions indicated below, image the molecule to be in the
 *  centre of a clockface and the viewer rotating around the perimeter of the clockface.
 *   "Front" renders the molecule from the front (viewing position = 6 o'clock (0 degrees))
 *   "Back" renders the molecule from the back (viewing position = 12 o'clock (180 degrees))
 *   "Left" renders the molecule from the left (viewing position = 9 o'clock (-90 degrees))
 *   "Right" renders the molecule from the right (viewing position = 3 o'clock (90 degrees))
 *   "RotateLeft" rotates the viewing position by -50 seconds (-5 degrees),
 *   "RotateRight" rotates the viewing position by 50 seconds (5 degrees).
 *
 *  To make sure that the nearest atoms appear in front of the furthest atoms,
 *  the atoms must be rendered in order from the furthest away to the nearest.
 *  Each viewing position imposes a different ordering on the atoms.
 */

public class MoleculeRenderer {

    // Map containing the size and color of each atom type.
    private Map<String, AtomInfo> atoms; 

    // The collection of the atoms in the current molecule
    private List<MoleculeElement> molecule;  

    private double currentAngleX = 0.0;    //current viewing angle (in degrees)
    private double currentAngleY = 0.0;

    private double rotationStep = 5.0;    // change in ange when rotation around the molecule

    private double spinRotationStep = 5.0;

    private int sleepTime = 60;

    private boolean spining = false;

    // Constructor:
    /** 
     * [CORE] / [COMPLETION]
     * Sets up the Graphical User Interface and reads the file of element data of
     * each possible type of atom into a Map: where the type is the key
     * and an AtomInfo object is the value (containing size and color).
     */
    public MoleculeRenderer() {
        UI.addButton("Read", () -> {
                String filename = UIFileChooser.open();
                readMoleculeFile(filename);
                view(0, 0, new BackToFrontComparator());
            });
        UI.addButton("FromFront", () -> view(0, 0, new BackToFrontComparator()));

        /*# YOUR CODE HERE */
        UI.addButton("FromRight", () -> view(90, 0, new LeftToRightComparator()));
        UI.addButton("FromBack", () -> view(180, 0, new FrontToBackComparator()));
        UI.addButton("FromLeft", () -> view(270, 0, new RightToLeftComparator()));
        UI.addButton("FromTop", () -> view(0, 90, new BottomToTopComparator()));
        UI.addButton("FromBottom", () -> view(0, 270, new TopToBottomComparator()));

        UI.addButton("RotateLeft", () -> view(currentAngleX+rotationStep, 0, new XPerspectiveComparator()));
        UI.addButton("RotateRight", () -> view(currentAngleX-rotationStep, 0, new XPerspectiveComparator()));
        UI.addButton("RotateUp", () -> view(0, currentAngleY+rotationStep, new YPerspectiveComparator()));
        UI.addButton("RotateDown", () -> view(0, currentAngleY-rotationStep, new YPerspectiveComparator()));
        UI.addButton("SpinRight", this::spinRight);
        UI.addButton("SpinLeft", this::spinLeft);
        UI.addButton("SpinUp", this::spinUp);
        UI.addButton("SpinDown", this::spinDown);
        UI.addSlider("SpinSpeed",0.0,10.0, this::setSpeed);
        UI.addSlider("XOffset",0.0,800.0, this::setXOffset);
        UI.addSlider("YOffset",0.0,800.0, this::setYOffset);

        readAtomInfos();    //  Read the atom definitions
    }

    /** 
     *  [CORE]
     *  Reads the molecule data from a file containing one line for each atom in the molecule.
     *  Each line contains an atom type and the 3D coordinates of the atom.
     *  For each atom, the method constructs a MoleculeElement object,
     *  and adds it to the List of molecule elements in the molecule.
     *
     *  [COMPLETION]
     *  To obtain the color and the size of each atom, it has to look up the type
     *  of the respective atom in the Map of atoms.
     */
    public void readMoleculeFile(String fname) {
        molecule = new ArrayList<>();
        try {
            /*# YOUR CODE HERE */
            Scanner sc = new Scanner(new File(fname));
            while(sc.hasNext()){
                AtomInfo atomI = atoms.get(sc.next());
                MoleculeElement moleculeE = new  MoleculeElement(sc.nextDouble(), sc.nextDouble(), sc.nextDouble(),
                        atomI.color, atomI.radius);
                molecule.add(moleculeE);
            }
            sc.close();
        }
        catch(IOException ex) {
            UI.println("Reading molecule file " + fname + " failed.");
        }
    }

    /** 
     *  [COMPLETION]
     *  Reads a file containing radius and color information about each type of
     *  atom and stores the info in a Map, using the atom type as a key.
     */
    private void readAtomInfos() {
        UI.println("Reading the atom definitions...");
        atoms = new HashMap<>();
        try {
            /*# YOUR CODE HERE */
            Scanner sc = new Scanner(new File("atom-definitions.txt"));
            while(sc.hasNext()){
                AtomInfo atom = new AtomInfo(sc);
                atoms.put(atom.name, atom);
            }
            sc.close();
        }
        catch (IOException ex) {
            UI.println("Reading atom definitions FAILED.");
        }
    }

    public void view (double viewingAngleX, double viewingAngleY, Comparator<MoleculeElement> sortingCriterion) {
        currentAngleX = viewingAngleX;
        if(currentAngleX >360){
            currentAngleX = 0+(currentAngleX -360);}
        if(currentAngleX <0){
            currentAngleX = 360+ currentAngleX;}

        currentAngleY = viewingAngleY;
        if(currentAngleY >360){
            currentAngleY = 0+(currentAngleY -360);}
        if(currentAngleY <0){
            currentAngleY = 360+ currentAngleY;}

        if (molecule == null)
            return;
        Collections.sort(molecule, sortingCriterion);

        render(); // render the molecule according to the current ordering
    }

    /**
     *  Renders the molecule, according the the current ordering of Atoms in the List.
     *  The Atom's render() method needs the current perspective angle.
     */
    public void render() {
        UI.clearGraphics();

        for(MoleculeElement moleculeElement : molecule) {
            moleculeElement.render(currentAngleX, currentAngleY);
        }

    }

    public void spinRight(){
        if(!spining) {spining = true;}
        else{spining = false;}
        while(spining){
            view(currentAngleX - spinRotationStep, 0, new XPerspectiveComparator());
            try {
                Thread.sleep(sleepTime);
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    public void spinLeft(){
        if(!spining) {spining = true;}
        else{spining = false;}
        while(spining){
            view(currentAngleX + spinRotationStep, 0, new XPerspectiveComparator());
            try {
                Thread.sleep(sleepTime);
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    public void spinUp(){
        if(!spining) {spining = true;}
        else{spining = false;}
        while(spining){
            view(0, currentAngleY+spinRotationStep, new YPerspectiveComparator());
            try {
                Thread.sleep(sleepTime);
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    public void spinDown(){
        if(!spining) {spining = true;}
        else{spining = false;}
        while(spining){
            view(0, currentAngleY-spinRotationStep, new YPerspectiveComparator());
            try {
                Thread.sleep(sleepTime);
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    public void setSpeed(double speed){
        spinRotationStep = speed;
    }

    public void setXOffset(double offset){
        for(MoleculeElement atom: molecule){atom.setXOffset(offset);}
        render();
    }

    public void setYOffset(double offset){
        for(MoleculeElement atom: molecule){atom.setYOffset(offset);}
        render();
    }

    /**    
     * 
     * Private comparator classes.
     * 
     * You will need a comparator class for each different direction
     *
     * Each comparator class should be a Comparator of MoleculeElement, and will define
     * a compare method that compares two atoms.
     * Each comparator must have a compare method.
     * Most of the comparators do not need an explicit constructor and have no fields.
     * However, the comparator for rotating the viewer by an angle may need a field and a constructor.
     */

    /** 
     * [CORE]
     * Comparator that defines the ordering to be from back to front.
     * 
     * Uses the z coordinates of the two atoms;
     * larger z means towards the back,
     * smaller z means towards the front
     * @return
     *  negative if element1 is more to the back than element2,
     *  0 if they are in the same plane,
     *  positive if element1 is more to the front than element2.
     */
    private class BackToFrontComparator implements Comparator<MoleculeElement> {

        public int compare(MoleculeElement element1, MoleculeElement element2) {
            /*# YOUR CODE HERE */
            return (int)(element2.getZ() - element1.getZ());
        }
    }

    /** 
     * Comparator that defines the ordering to be from front to back.
     * 
     * Uses the z coordinates of the two atoms
     * larger z means towards the back,
     * smaller z means towards the front
     * @return
     *  negative if element1 is more to the front than element2, (
     *  0 if they are in the same plane,
     *  a positive number if element1 is more to the back than element2.
     */
    private class FrontToBackComparator implements Comparator<MoleculeElement> {

        public int compare(MoleculeElement element1, MoleculeElement element2) {
            /*# YOUR CODE HERE */
            return (int)(element1.getZ() - element2.getZ());
        }
    }

    /** 
     * Comparator that defines the ordering to be from left to right 
     * 
     *  uses the x coordinates of the two atom
     *  larger x means more towards the right,
     *  smaller x means more towards the left.
     *  @return
     *   negative if element1 is left of element2, 
     *   positive if atom 1 is right of element2.
     *   0 if they are vertically aligned, 
     */
    private class LeftToRightComparator implements Comparator<MoleculeElement> {

        public int compare(MoleculeElement element1, MoleculeElement element2) {
            /*# YOUR CODE HERE */
            return (int)(element1.getX() - element2.getX());
        }
    }

    /** 
     * Comparator that defines the ordering to be from right to left 
     * 
     * Uses the x coordinates of the two atoms
     * larger x means more towards the right,
     * smaller x means more towards the left.
     * @return
     *  negative if element1 is more to the right than element2,
     *  0 if they are aligned,
     *  positive if atom 1 is more to the left than element2.       
     */
    private class RightToLeftComparator implements Comparator<MoleculeElement> {

        public int compare(MoleculeElement element1, MoleculeElement element2) {
            /*# YOUR CODE HERE */
            return (int)(element2.getX() - element1.getX());
        }
    }

    /**
     * Comparator that defines the ordering to be from back to front.
     *
     * Uses the y coordinates of the two atoms;
     * larger y means towards the bottom,
     * smaller y means towards the top
     * @return
     *  negative if element1 is more to the bottom than element2,
     *  0 if they are in the same plane,
     *  positive if element1 is more to the top than element2.
     */
    private class BottomToTopComparator implements Comparator<MoleculeElement> {

        public int compare(MoleculeElement element1, MoleculeElement element2) {
            /*# YOUR CODE HERE */
            return (int)(element2.getY() - element1.getY());
        }
    }

    /**
     * Comparator that defines the ordering to be from back to front.
     *
     * Uses the y coordinates of the two atoms;
     * larger y means towards the bottom,
     * smaller y means towards the top
     * @return
     *  negative if element1 is more to the top than element2,
     *  0 if they are in the same plane,
     *  positive if element1 is more to the bottom than element2.
     */
    private class TopToBottomComparator implements Comparator<MoleculeElement> {

        public int compare(MoleculeElement element1, MoleculeElement element2) {
            /*# YOUR CODE HERE */
            return (int)(element1.getY() - element2.getY());
        }
    }

    /** Comparator that defines the ordering to be from further to nearer when viewed from
     *  a given perspective.
     *  Needs:
     *  - a constructor that stores the perspective angle,
     *  - a compare method.
     *  
     *  @return
     *  a negative number if element1 is further back than element2 from this angle
     *  0 if they are at the same distance,
     *  a positive number if element1 is nearer than element2 from this angle
     */
    private class XPerspectiveComparator implements Comparator<MoleculeElement> {
        /**
         * You can give this constructor a field to hold the currentAngleX,
         * but it can also access the field of the enclosing class directly.
         */
        private double Xangle;

        public XPerspectiveComparator() {
            Xangle = currentAngleX;
        }
        public int compare(MoleculeElement element1, MoleculeElement element2) {
            /*# YOUR CODE HERE */
            return element1.XfurtherThan(element2, Xangle);
        }
    }

    private class YPerspectiveComparator implements Comparator<MoleculeElement> {
        /**
         * You can give this constructor a field to hold the currentAngleX,
         * but it can also access the field of the enclosing class directly.
         */
        private double Yangle;

        public YPerspectiveComparator() {
            Yangle = currentAngleY;
        }
        public int compare(MoleculeElement element1, MoleculeElement element2) {
            /*# YOUR CODE HERE */
            return element1.YfurtherThan(element2, Yangle);
        }
    }

    public static void main(String args[]) {
        new MoleculeRenderer();
    }
}
