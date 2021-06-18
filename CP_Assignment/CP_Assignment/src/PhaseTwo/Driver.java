package PhaseTwo;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.JFrame;
import javax.swing.JPanel;


public class Driver {
    
    public static void main(String[] args) {
        
        //GUI stuff
    	GUI gui = new GUI(); 
        JFrame frame = new JFrame("M&T System GUI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(720, 720);
        JPanel p1 = new JPanel();

        frame.add(p1);
        gui.placeComponents(p1);
        frame.setVisible(true);
        
        gui.setClass(gui);
                
    }
}
