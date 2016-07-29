
package MainFrame;

import UI.SampleFrame;

/**
 *
 * @author Collin
 */
public class Sampler implements Runnable {
    SampleFrame sf;
    MCU mcu;
    int sampleCount;
    int colorOver;
    int sampleNumber = 0;
    int[][] samples;
    long[] averages;
    
    public Sampler(SampleFrame sf, MCU mcu, int sampleCount, int colorOver){
        this.sf = sf;
        this.mcu = mcu;
        this.sampleCount = sampleCount;
        this.colorOver = colorOver;
        samples = new int[sampleCount][4];
    }

    @Override
    public void run() {
        
        
        while(sampleNumber < sampleCount){
            int[] newSample = mcu.loadBead();
            mcu.setBead(10);
                if(colorOver != 5 & colorOver != 3){
                    if(newSample[colorOver] > 375){//If the selected color is high or if there was no high color selected.  
                        sf.printLn("Sample Number: " + Integer.toString(sampleNumber + 1) +
                            "Red: " + Integer.toString(newSample[0]) + 
                            " Green: " + Integer.toString(newSample[1]) + 
                            " Blue: " + Integer.toString(newSample[2]) + 
                            " Clear: " + Integer.toString(newSample[3]));

                        samples[sampleNumber] = newSample;
                        sampleNumber++;
                    }
                }else{
                    if(colorOver == 3){
                        if(newSample[colorOver] > 70){
                            sf.printLn("Sample Number: " + Integer.toString(sampleNumber + 1) +
                                    "Red: " + Integer.toString(newSample[0]) + 
                                    " Green: " + Integer.toString(newSample[1]) + 
                                    " Blue: " + Integer.toString(newSample[2]) + 
                                    " Clear: " + Integer.toString(newSample[3]));

                                samples[sampleNumber] = newSample;
                                sampleNumber++;
                        }
                    }else{
                        sf.printLn("Sample Number: " + Integer.toString(sampleNumber + 1) +
                                "Red: " + Integer.toString(newSample[0]) + 
                                " Green: " + Integer.toString(newSample[1]) + 
                                " Blue: " + Integer.toString(newSample[2]) + 
                                " Clear: " + Integer.toString(newSample[3]));

                            samples[sampleNumber] = newSample;
                            sampleNumber++;
                    }
                }
        }//At this point, we have enough samples for the calculations.  Those come next.  
        
        sf.printLn("");
        sf.printLn("Calculating averages.  ");
        
        averages = new long[4];
        
        for(int color = 0; color < 4; color++){
            for(int i = 0; i < sampleCount; i++){
                averages[color] += samples[i][color];
            }
            averages[color] = averages[color] / sampleCount;
        }
        
        
        
        
        sf.printLn("");
        sf.printLn("Red: " + averages[0]);
        sf.printLn("Green: " + averages[1]);
        sf.printLn("Blue: " + averages[2]);
        sf.printLn("Clear: " + averages[3]);
        
        long[] totals = new long[sampleCount];
        long[] totalsClear =  new long[sampleCount];
        
        long meanTotalColor = 0;
        long meanTotalClear = 0;
        
        for(int i = 0; i < sampleCount; i++){//we are finding the 
            totals[i] = (averages[0] - samples[i][0]) * (averages[0] - samples[i][0]);
            totals[i] += (averages[1] - samples[i][1]) * (averages[1] - samples[i][1]);
            totals[i] += (averages[2] - samples[i][2]) * (averages[2] - samples[i][2]);
            totals[i] = (int)Math.sqrt(totals[i]);
            totalsClear[i] = (averages[3] - samples[i][3]) * (averages[3] - samples[i][3]);
            //At this point we have the distance away from the mean for both numbers.  
            meanTotalColor += totals[i] * totals[i];
            meanTotalClear += totalsClear[i];
        }
        
        meanTotalColor = meanTotalColor / sampleCount;
        meanTotalClear = meanTotalClear / sampleCount;
        
        meanTotalColor = (long)Math.sqrt(meanTotalColor);
        meanTotalClear = (long)Math.sqrt(meanTotalClear);
        
        sf.printLn("");
        sf.printLn("Sigma Color: " + meanTotalColor);
        sf.printLn("Sigma Clear: " + meanTotalClear);
    }
}
