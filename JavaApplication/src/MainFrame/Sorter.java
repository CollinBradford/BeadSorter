
package MainFrame;

import UI.MainFrame;

/**
 *
 * @author Collin B.
 */
public class Sorter implements Runnable {
    MainFrame mf;
    Thread t;
    boolean startFlag = false;
    float alg = (float)0.7;
    MCU mcu;
    
    public Sorter(MainFrame mf, MCU mcu){
        this.mf = mf;
        this.mcu = mcu;
    }
    
    int[][] colors = {
        // Red Green Blue Clear
        {272, 402, 323, 342}, //Lavender
        {278, 412, 307, 199}, //Plumb
        {357, 361, 279, 232}, //Pink
        {479, 280, 238, 206}, //Magenta
        {280, 219, 498, 433}, //Kiwi Lime
        {207, 301, 488, 218}, //Dark Green
        {196, 291, 510, 255}, //Clear Green
        {142, 396, 459, 348}, //Parrot Green
        {170, 495, 332, 170}, //Dark Blue
        {137, 461, 399, 417}, //Pastel Blue
        {155, 474, 367, 376}, //Light Blue
        {235, 356, 406, 764}, //Toothpaste
        {236, 298, 463, 586}, //Light Green
        {399, 185, 413, 704}, //Yellow
        {339, 212, 447, 393}, //Clear Yellow
        {457, 175, 364, 563}, //Cheddar
        {451, 204, 342, 416}, //Butterscotch
        {376, 257, 364, 525}, //Sand
        {503, 227, 267, 583}, //Peach
        {488, 282, 227, 301}, //Clear Pink
        {465, 301, 232, 374}, //Blush
        {534, 263, 200, 321}, //Hot Pink
        {354, 282, 361, 141}, //Dark Brown
        {410, 235, 352, 238}, //Light Brown
        {286, 328, 383, 84}, //Black
        {289, 332, 375, 286}, //Clear
        {564, 208, 225, 212}, //Red
        {510, 184, 303, 324}, //Clear Orange
        {297, 321, 379, 921}, //White
        {294, 326, 377, 393}, //Grey
    };

    int[][] sigmas = {
        // Colors  Clear
        {16, 88}, //Lavender
        {29, 47}, //Plumb
        {18, 36}, //Pink
        {26, 39}, //Magenta
        {20, 60}, //Kiwi Lime
        {17, 27}, //Dark Green
        {24, 52}, //Clear Green
        {25, 62}, //Parrot Green
        {29, 28}, //Dark Blue
        {16, 91}, //Pastel Blue
        {21, 82}, //Light Blue
        {15, 126}, //Toothpaste
        {16, 112}, //Light Green
        {19, 120}, //Yellow
        {17, 89}, //Clear Yellow
        {24, 145}, //Cheddar
        {24, 115}, //Butterscotch
        {20, 60}, //Sand
        {24, 161}, //Peach
        {21, 65}, //Clear Pink
        {24, 83}, //Blush
        {24, 86}, //Hot Pink
        {22, 24}, //Dark Brown
        {10, 51}, //Light Brown
        {17, 14}, //Black
        {13, 71}, //Clear
        {34, 49}, //Red
        {23, 86}, //Clear Orange
        {14, 144}, //White
        {17, 113}, //Grey
    };
    
    public Sorter(MCU mcu){
        this.mcu = mcu;
    }
    
    @Override
    public void run(){
        startFlag = true;
        while(isRunning()){
            sort();
            System.out.println("Start Flag During Loop: " + startFlag);
        }
    }
    
    private void sort(){
        System.out.println(alg);
        int[] beadColor = mcu.loadBead();
        float[][] compare = new float[colors.length][2];//distance color, distance white,  
        float total = 0;
        float totalWhite = 0;
        for(int i = 0; i < colors.length; i++){//calculate the distance between our point and every other color.  
            for(int color = 0; color < 3; color++ ){
                total += Math.pow(colors[i][color] - beadColor[color], 2);//find the distance between the points and square it.  
            }
            totalWhite = Math.abs(colors[i][3] - beadColor[3]);//do calculation for white length.  
            total = (int)Math.sqrt(total);//finish calculation of color length. 
            compare[i][0] = total;//distance color
            compare[i][1] = totalWhite;//distance white
        }
        
        for(int i = 0; i < colors.length; i++){//calculate the z score and subtract from one so that it inverts the nubmer to represent greater is higher.  
            compare[i][0] = (compare[i][0] / sigmas[i][0]);
            compare[i][1] = (compare[i][1] / sigmas[i][1]);
            //multiply both numbers by the weight factor and add them together.  
            compare[i][0] = (float) ((compare[i][0] * alg) + (compare[i][1] * (1 - alg)));
        }
        
        int index = highest(compare);
        if(index != -1){
            mf.recordBead(index, compare[index][0]);
        }else{
            mf.recordBead(-1, 5);
        }
    }
    
    public void typeSort(){
        
    }
    
    public synchronized void stopThread(){
        startFlag = false;
    }
    
    public synchronized boolean isRunning(){
        return startFlag;
    }
    
    private int highest(float[][] numbers){
        float highest = 1000;
        int index = -1;
        for(int i = 0; i < colors.length; i++){
            if(numbers[i][0] < highest){
                highest = numbers[i][0];
                index = i;
            }
        }
        return index;
    }
    
    public void setAlg (int value){
        this.alg = value / 100;
    }
}
