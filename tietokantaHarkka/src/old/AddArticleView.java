/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package old;

import java.awt.event.ActionListener;

/**
 *
 * @author Cassu
 */
public class AddArticleView extends javax.swing.JDialog {

    /**
     * Creates new form AddArticleView
     */
    public AddArticleView(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }
    public void addListeners(ActionListener x){
       saveB.addActionListener(x);
    }
    public String[] getParam(){
        String[] rtn = new String[5];
        rtn[0] = articleTypeF.getSelectedItem().toString();
        rtn[1] = articleNameF.getText();
        rtn[2] = buyInF.getText();
        rtn[3] = sellF.getText();
        rtn[4] = storageF.getText();
        
        return rtn;
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        articleTypeF = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        articleNameF = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        buyInF = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        sellF = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        storageF = new javax.swing.JTextField();
        backB = new javax.swing.JButton();
        saveB = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setText("Tarvikkeen tyyppi");

        jLabel2.setText("Tarvikkeen nimi");

        jLabel3.setText("Sisäänostohinta");

        jLabel4.setText("Myyntihinta");

        jLabel5.setText("Varastotilanne");

        backB.setText("Peruuta");

        saveB.setText("Tallenna");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel1)
                        .addComponent(jLabel2)
                        .addComponent(jLabel3)
                        .addComponent(jLabel4)
                        .addComponent(jLabel5)
                        .addComponent(articleNameF)
                        .addComponent(buyInF)
                        .addComponent(sellF)
                        .addComponent(storageF)
                        .addComponent(articleTypeF, 0, 160, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(backB)
                        .addGap(18, 18, 18)
                        .addComponent(saveB)))
                .addContainerGap(51, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(articleTypeF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(articleNameF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buyInF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(sellF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(storageF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(backB)
                    .addComponent(saveB))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField articleNameF;
    private javax.swing.JComboBox<String> articleTypeF;
    private javax.swing.JButton backB;
    private javax.swing.JTextField buyInF;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JButton saveB;
    private javax.swing.JTextField sellF;
    private javax.swing.JTextField storageF;
    // End of variables declaration//GEN-END:variables
}
