
package Comms;

import arduCom.Connection.Serial.ComputerSerialConnection;
import arduCom.Device.Devices.Microcontroller.Microcontroller;

/**
 *
 * @author Collin
 */
public class Communicator {
    
    public Microcontroller dev;
    
    public Communicator(String comPort){
        System.out.println(System.getProperty("java.library.path"));
        System.out.println("Creating Connection con");
        System.out.println(gnu.io.RXTXVersion.getVersion());
        ComputerSerialConnection con = new ComputerSerialConnection(comPort);
        System.out.println("Created!");
        System.out.println("ComputerSerialConnection");
        dev = new Microcontroller(con);
        System.out.println("Microcontroller One");
    }
}
