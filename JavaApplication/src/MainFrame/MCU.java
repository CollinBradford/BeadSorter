/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MainFrame;

import arduCom.Device.Devices.Microcontroller.Microcontroller;
import java.io.IOException;
import static java.lang.Math.abs;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Collin
 */
public class MCU {
    public Microcontroller mcu;
    
    private static final int SLEEP_FACTOR = 15;
    
    int chutePlace;
    private byte bins[] = new byte[11];
    
    public MCU(Microcontroller mcu){
        this.mcu = mcu;
        //set up the chute so that it is in the default position.  
        mcu.setChute(8);
        chutePlace = 8;
        Path fileName = Paths.get("binPlaces.dat");
        if(Files.exists(fileName)){
            try {
                bins = Files.readAllBytes(fileName);
            } catch (IOException ex) {
                Logger.getLogger(MCU.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void lockChute(int bin){
        bins[bin] = (byte)chutePlace;
        System.out.println("Bin Number: " + bin + " Bin Location: " + chutePlace);
        Path fileName = Paths.get("binPlaces.dat");
        try {
            Files.write(fileName, bins);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public void setChute(int place){
        chutePlace = place;
        mcu.setChute(place);
    }
    
    public void clk(){
        if(chutePlace-1 > 8){
            chutePlace--;
            mcu.setChute(chutePlace);
        }
    }
    
    public void cclk(){
        if(chutePlace-1 < 150){
            chutePlace++;
            mcu.setChute(chutePlace);
        }
    }
    
    public void toBin(int binNumber){
        try {
            int prePlace = chutePlace;
            setChute(bins[binNumber]);
            
            int difference = abs(prePlace - bins[binNumber]);
            chutePlace = bins[binNumber];
            Thread.sleep(difference * SLEEP_FACTOR);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }
    
    public int getBinPlace(int binNumber){
        return bins[binNumber];
    }
    
    public int[] loadBead(){
        return mcu.getBead();
    }
    
    public void setBead(int bin){
        toBin(bin);
        mcu.placeBead();
    }
}
