/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MainFrame;

import javax.swing.JLabel;

/**
 *
 * @author Collin
 */
public class Bin {
    Bead bead;
    int binNumber;
    JLabel bcLabel;
    int beadCount = 0;
    
    public Bin(Bead bead, int binNumber){
        this.bead = bead;
        this.binNumber = binNumber;
    }
    
    public void addBead(){
        beadCount++;
        bcLabel.setText(Integer.toString(beadCount));
    }
    
    public void resetBeadCount(){
        beadCount = 0;
        bcLabel.setText(Integer.toString(beadCount));
    }
    
}
