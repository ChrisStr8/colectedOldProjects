/**
 * An example of a method - replace this comment with your own
 *
 * @param  y   a sample parameter for a method
 * @return     the sum of x and y
 */

/* Code for Assignment ?? 
 * Name:
 * Usercode:
 * ID:
 */

import ecs100.*;
import java.util.*;
import java.io.*;
import java.awt.*;
import javax.swing.*;

/** <description of class Main>
 */
public class Main{

    private Arm arm;
    private Drawing drawing;
    private ToolPath tool_path;
    // state of the GUI
    private int state; // 0 - nothing
    // 1 - inverse point kinematics - point
    // 2 - enter path. Each click adds point  
    // 3 - enter path pause. Click does not add the point to the path

    /**      */
    public Main(){
        UI.initialise();
        UI.addButton("xy to angles", this::inverse);
        UI.addButton("Enter path XY", this::enter_path_xy);
        UI.addButton("Save path XY", this::save_xy);
        UI.addButton("Load path XY", this::load_xy);
        UI.addButton("Save path Ang", this::save_ang);
        UI.addButton("Load path Ang:Play", this::load_ang);
        UI.addButton("Save PWM", this::save_pwm);
        UI.addButton("Generate circle", this::draw_circle);

        // UI.addButton("Quit", UI::quit);
        UI.setMouseMotionListener(this::doMouse);
        UI.setKeyListener(this::doKeys);

        //ServerSocket serverSocket = new ServerSocket(22); 
        this.arm = new Arm();
        this.drawing = new Drawing();
        this.run();
        arm.draw();
    }

    public void draw_circle(){
        try{
            double theta = 0;  // angle that will be increased each loop
            double h = 300;     // x coordinate of circle center
            double k = 150;      // y coordinate of circle center
            double step = 10.0;  // amount to add to theta each time (degrees)
            double r = 50.0;

            String toSave = UIFileChooser.save();
            File statText = new File(toSave);
            FileOutputStream is = new FileOutputStream(statText);
            OutputStreamWriter osw = new OutputStreamWriter(is);    
            Writer w = new BufferedWriter(osw);
            String str_out;
            int count = 0;
            double x1 = 0;
            double y1 = 0;
            double x2 = 0;
            double y2 = 0;

            while(theta <= 360){
                double x = h + r*Math.cos(theta * Math.PI / 180);
                double y = k + r*Math.sin(theta * Math.PI / 180);
                //                 if(count == 0) {
                //                     str_out = (x+" "+y+" "+ "1\r\n");
                //                     w.write(str_out);
                //                 }
                if(count == 0) {
                    x1 = x;
                    y1 = y;
                    count++;
                }
                if(count == 1) {
                    x2 = x;
                    y2 = y;
                    count++;
                }
                str_out = (x+" "+y+" "+ "2\r\n"); // was pen but now 2000
                UI.println(str_out);
                w.write(str_out);
                //                 if(count == 360) {
                //                     str_out = (x+" "+y+" "+ "1\r\n");
                //                     w.write(str_out);
                //                 }

                theta = theta + step;
            }
            str_out = (x1+" "+y1+" "+ "2\r\n");
            w.write(str_out);
            
            str_out = (x2+" "+y2+" "+ "2\r\n");
            w.write(str_out);
            str_out = (x2+" "+y2+" "+ "1\r\n");
            w.write(str_out);
            
            w.close();
        } catch (IOException e) {
            UI.println("Problem writing to the file statsTest.txt");
        }
    }

    public void doKeys(String action){
        UI.printf("Key :%s \n", action);
        if (action.equals("b")) {
            // break - stop entering the lines
            state = 3;
            //

        }

    }

    public void doMouse(String action, double x, double y) {
        //UI.printf("Mouse Click:%s, state:%d  x:%3.1f  y:%3.1f\n",
        //   action,state,x,y);
        UI.clearGraphics();
        String out_str=String.format("%3.1f %3.1f",x,y);
        UI.drawString(out_str, x+10,y+10);
        // 
        if ((state == 1)&&(action.equals("clicked"))){
            // draw as 

            arm.inverseKinematic(x,y);
            arm.draw();
            return;
        }

        if ( ((state == 2)||(state == 3))&&action.equals("moved") ){
            // draw arm and path
            arm.inverseKinematic(x,y);
            arm.draw();

            // draw segment from last entered point to current mouse position
            if ((state == 2)&&(drawing.get_path_size()>0)){
                PointXY lp = new PointXY();
                lp = drawing.get_path_last_point();
                //if (lp.get_pen()){
                UI.setColor(Color.GRAY);
                UI.drawLine(lp.get_x(),lp.get_y(),x,y);
                // }
            }
            drawing.draw();
        }

        // add point
        if (   (state == 2) &&(action.equals("clicked"))){
            // add point(pen down) and draw
            UI.printf("Adding point x=%f y=%f\n",x,y);
            drawing.add_point_to_path(x,y,true); // add point with pen down

            arm.inverseKinematic(x,y);
            arm.draw();
            drawing.draw();
            drawing.print_path();
        }

        if (   (state == 3) &&(action.equals("clicked"))){
            // add point and draw
            //UI.printf("Adding point x=%f y=%f\n",x,y);
            drawing.add_point_to_path(x,y,false); // add point wit pen up

            arm.inverseKinematic(x,y);
            arm.draw();
            drawing.draw();
            drawing.print_path();
            state = 2;
        }

    }

    public void save_xy(){
        state = 0;
        String fname = UIFileChooser.save();
        drawing.save_path(fname);
    }

    public void enter_path_xy(){
        state = 2;
    }

    public void inverse(){
        state = 1;
        arm.draw();
    }

    public void load_xy(){
        state = 0;
        String fname = UIFileChooser.open();
        drawing.load_path(fname);
        drawing.draw();

        arm.draw();
    }

    // save angles into the file
    public void save_ang(){
        String fname = UIFileChooser.open();
        if(tool_path == null){ UI.println("is null"); }
        tool_path.convert_drawing_to_angles(drawing, arm, fname);
        tool_path.save_angles(fname);
    }

    public void save_pwm(){
        String fname = UIFileChooser.open();
        arm.convert_path_xy_to_pwm(fname);
    }

    public void load_ang(){
    }

    public void run() {
        while(true) {
            arm.draw();
            UI.sleep(20);
        }
    }

    public static void main(String[] args){
        Main obj = new Main();
    }    

}
