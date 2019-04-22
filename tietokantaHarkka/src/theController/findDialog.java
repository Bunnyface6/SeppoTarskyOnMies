/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package theController;

/**
 *
 * @author Cassu
 */
public class findDialog extends javax.swing.JDialog {

    /**
     * Creates new form findDialog
     */
    public findDialog(java.awt.Frame parent, boolean modal) {
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

        whatBox = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        howBox = new javax.swing.JComboBox<>();
        jButton1 = new javax.swing.JButton();
        findDialogButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        whatBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Lasku", "Työkohde", "Tarvike", "Yritysasiakas", "Kuluttaja asiakas" }));
        whatBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                whatBoxItemStateChanged(evt);
            }
        });

        jLabel1.setText("Mitä etsitään:");

        jLabel2.setText("Millä perustein:");

        jButton1.setText("Peruuta");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        findDialogButton.setText("Etsi");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel1)
                        .addComponent(whatBox, 0, 150, Short.MAX_VALUE)
                        .addComponent(jLabel2)
                        .addComponent(howBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(findDialogButton)))
                .addContainerGap(240, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(whatBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(howBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 45, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(findDialogButton))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        dispose();        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void whatBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_whatBoxItemStateChanged
        if(whatBox.getSelectedItem().equals("Lasku")){
            howBox.setModel(laskuM);
        }
        else if(whatBox.getSelectedItem().equals("Tarvike")){
            howBox.setModel(tarvikeM);
        }
        else if(whatBox.getSelectedItem().equals("Työkohde")){
            howBox.setModel(tyokohdeM);
        }
        else if(whatBox.getSelectedItem().equals("Yritysasiakas")){
            howBox.setModel(yAsiakasM);
        }
        else if(whatBox.getSelectedItem().equals("Kuluttaja asiakas")){
            howBox.setModel(kAsiakasM);
        }
            
    }//GEN-LAST:event_whatBoxItemStateChanged

    /**
     * @param args the command line arguments
     */

    javax.swing.DefaultComboBoxModel<String> laskuM = new javax.swing.DefaultComboBoxModel<String>(new String[] { "Tunnus", "Päivämäärä", "Työkohdenumero", "Asiakasnumero", "Kaikki maksamattomat" });
    javax.swing.DefaultComboBoxModel<String> tarvikeM = new javax.swing.DefaultComboBoxModel<String>(new String[] { "Tunnus", "Nimi", "Varastotilanne"});
    javax.swing.DefaultComboBoxModel<String> tyokohdeM = new javax.swing.DefaultComboBoxModel<String>(new String[] {"Tunnus", "Asiakas", "Osoite", "Laskuttamaton"});
    javax.swing.DefaultComboBoxModel<String> yAsiakasM = new javax.swing.DefaultComboBoxModel<String>(new String[] { "Tunnus", "Nimi", "Maksamattomia laskuja" });
    javax.swing.DefaultComboBoxModel<String> kAsiakasM = new javax.swing.DefaultComboBoxModel<String>(new String[] { "Tunnus", "Sukunimi", "Maksamattomia laskuja" });

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton findDialogButton;
    private javax.swing.JComboBox<String> howBox;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JComboBox<String> whatBox;
    // End of variables declaration//GEN-END:variables
}
