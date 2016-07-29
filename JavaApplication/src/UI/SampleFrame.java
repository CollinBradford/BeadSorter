
package UI;

import MainFrame.MCU;
import MainFrame.Sampler;
import javax.swing.text.DefaultCaret;

/**
 *
 * @author Collin
 */
public class SampleFrame extends javax.swing.JFrame {

    /**
     * Creates new form SampleFrame
     */
    public SampleFrame() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        textScroll = new javax.swing.JScrollPane();
        sampleTextArea = new javax.swing.JTextArea();
        SampleCount = new javax.swing.JSpinner();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        redHigh = new javax.swing.JRadioButton();
        greenHigh = new javax.swing.JRadioButton();
        blueHigh = new javax.swing.JRadioButton();
        ClearHigh = new javax.swing.JRadioButton();

        sampleTextArea.setColumns(20);
        sampleTextArea.setRows(5);
        textScroll.setViewportView(sampleTextArea);

        SampleCount.setModel(new javax.swing.SpinnerNumberModel(10, 10, null, 10));

        jButton1.setText("Start");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Sample");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        redHigh.setText("Red High");

        greenHigh.setText("Green High");

        blueHigh.setText("Blue High");

        ClearHigh.setText("Clear");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(SampleCount)
                        .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(redHigh, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(greenHigh, javax.swing.GroupLayout.DEFAULT_SIZE, 117, Short.MAX_VALUE)
                        .addComponent(blueHigh, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(ClearHigh))
                .addGap(18, 18, 18)
                .addComponent(textScroll, javax.swing.GroupLayout.DEFAULT_SIZE, 489, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(textScroll)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(SampleCount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton1)
                .addGap(18, 18, 18)
                .addComponent(jButton2)
                .addGap(18, 18, 18)
                .addComponent(redHigh)
                .addGap(18, 18, 18)
                .addComponent(greenHigh)
                .addGap(18, 18, 18)
                .addComponent(blueHigh)
                .addGap(18, 18, 18)
                .addComponent(ClearHigh)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        sampleText = "";
        int sampleCount = (Integer)SampleCount.getValue();
        System.out.println(sampleCount);
        Thread t;
        if(redHigh.isSelected()){
            sampleTextArea.setText("Starting samples with red high.  ");
            t = new Thread(new Sampler(this, micro, sampleCount, 0));
        }else{
            if(greenHigh.isSelected()){
                sampleTextArea.setText("Starting samples with green high.");
                t = new Thread(new Sampler(this, micro, sampleCount, 1));
            }else{
                if(blueHigh.isSelected()){
                    sampleTextArea.setText("Starting samples with blue high.");
                    t = new Thread(new Sampler(this, micro, sampleCount, 2));
                }else{
                    if(ClearHigh.isSelected()){
                        sampleTextArea.setText("Starting samples with clear color selected.  ");
                        t = new Thread(new Sampler(this, micro, sampleCount, 3));
                    }else{
                        sampleTextArea.setText("Starting samples with no color selected.");
                        t = new Thread(new Sampler(this, micro, sampleCount, 5));
                    }
                }
            }
        }
        t.start();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        System.out.println(micro);
        micro.setBead(10);
        int[] color = micro.loadBead();
        printLn("Red: " + color[0]);
        printLn("Green: " + color[1]);
        printLn("Blue: " + color[2]);
        printLn("Clear: " + color[3]);
    }//GEN-LAST:event_jButton2ActionPerformed

    public void printLn(String message){
        sampleText += message + "\n\r";
        sampleTextArea.setText(sampleText);
        DefaultCaret caret = (DefaultCaret)sampleTextArea.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
    }
    
    /**
     * @param args the command line arguments
     */
    public void main() {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(SampleFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SampleFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SampleFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SampleFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SampleFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JRadioButton ClearHigh;
    private javax.swing.JSpinner SampleCount;
    private javax.swing.JRadioButton blueHigh;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JRadioButton greenHigh;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JRadioButton redHigh;
    private javax.swing.JTextArea sampleTextArea;
    private javax.swing.JScrollPane textScroll;
    // End of variables declaration//GEN-END:variables
    private String sampleText = "";
    public static MCU micro;

}