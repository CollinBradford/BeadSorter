/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MainFrame;

import Comms.Communicator;
import UI.MainFrame;
import gnu.io.*;
/**
 *
 * @author Collin B.
 */
public class Main {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.setProperty("gnu.io.rxtx.SerialPorts", "/dev/ttyACM0");
        //String comPort = "COM12";
        String comPort = "/dev/ttyACM0";
        System.out.println("Connecting to: " + comPort);
              
        Communicator Com = new Communicator(comPort);
        System.out.println("Checkpoint one");
        MCU mcu = new MCU(Com.dev);
        System.out.println("mcu made");
        MainFrame.mcu = mcu;
        System.out.println("Transfer");
        MainFrame.main(args);
    }
    
}
