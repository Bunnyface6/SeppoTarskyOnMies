/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.event.ActionListener;

/**
 *
 * @author Cassu
 */
public class AddCClientView extends javax.swing.JDialog {

    /**
     * Creates new form AddCClientView
     */
    public AddCClientView(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
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

        jLabel1 = new javax.swing.JLabel();
        nameF = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        yIdF = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        cityF = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        postNmbrF = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        adressF = new javax.swing.JTextField();
        backB = new javax.swing.JButton();
        saveB = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setText("Yrityksen nimi");

        jLabel2.setText("Y-tunnus");

        jLabel3.setText("Kaupunki");

        jLabel4.setText("Postinumero");

        jLabel5.setText("Katuosoite");

        backB.setText("Peruuta");
        backB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backBActionPerformed(evt);
            }
        });

        saveB.setText("Tallenna");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(backB)
                        .addGap(18, 18, 18)
                        .addComponent(saveB))
                    .addComponent(nameF)
                    .addComponent(yIdF)
                    .addComponent(cityF)
                    .addComponent(postNmbrF)
                    .addComponent(adressF))
                .addContainerGap(48, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(nameF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(yIdF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cityF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(postNmbrF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(adressF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(backB)
                    .addComponent(saveB))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void backBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backBActionPerformed
        dispose();        // TODO add your handling code here:
    }//GEN-LAST:event_backBActionPerformed

    /**
     * @param args the command line arguments
     */
    public void addListeners(ActionListener x){
        saveB.addActionListener(x);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField adressF;
    private javax.swing.JButton backB;
    private javax.swing.JTextField cityF;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JTextField nameF;
    private javax.swing.JTextField postNmbrF;
    private javax.swing.JButton saveB;
    private javax.swing.JTextField yIdF;
    // End of variables declaration//GEN-END:variables
}
