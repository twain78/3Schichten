/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.oszimt.DreiSchichten.view;

import de.oszimt.DreiSchichten.controller.ViewController;
import de.oszimt.DreiSchichten.model.Dorf;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Konstantin Görlitz
 */
public class Dorfliste extends javax.swing.JPanel {
    
    private ViewController viewcontroller;

    /**
     * Creates new form Dörfer
     */
    public Dorfliste() {
        initComponents();
    }
    
    public Dorfliste(Dorf[] dorfliste){
        this();
        setDörfer(dorfliste);
    }
    
    public void setDörfer(Dorf[] dorfliste){
        DefaultTableModel model = (DefaultTableModel)jtDorfliste.getModel();
        for(final Dorf dorf : dorfliste){
            JButton button1 = new JButton("B");
            button1.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    öffneDorf(dorf.getId());
                }
            });
            
            JButton button2 = new JButton("P");
            button2.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    öffneEinwohner(dorf.getId());
                }
            });
            
            JButton button3 = new JButton("L");
            button3.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    löscheDorf(dorf.getId());
                }
            });
            
            Object[] row = {dorf.getName(), dorf.getMitgliederIDs().length, dorf.getLagerIDs().length, 
                button1, button2, button3};
            model.addRow(row);
            
        }
    }
    
    public Dorfliste(Dorf[] dorfliste, ViewController vc){
        this(dorfliste);
        this.viewcontroller=vc;
    }
    
    private void öffneDorf(int id){
        viewcontroller.changePanel("Lagerliste", id);
    }
    
    private void öffneEinwohner(int id){
        //openEinwohnerView(id);
    }
    
    private void löscheDorf(int id){
        this.viewcontroller.deleteDorf(id);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jlTitel = new javax.swing.JLabel();
        jbNeuesDorf = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtDorfliste = new javax.swing.JTable();

        jlTitel.setText("Dörfer");

        jbNeuesDorf.setText("Neues Dorf");
        jbNeuesDorf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbNeuesDorfActionPerformed(evt);
            }
        });

        jtDorfliste.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Name", "Bevölkerung", "Lageranzahl", "Bearbeiten", "Einwohner", "Löschen"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jtDorfliste);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jbNeuesDorf))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jlTitel)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 560, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jlTitel)
                .addGap(34, 34, 34)
                .addComponent(jbNeuesDorf)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(37, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jbNeuesDorfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbNeuesDorfActionPerformed
        //openAddDorf();
    }//GEN-LAST:event_jbNeuesDorfActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton jbNeuesDorf;
    private javax.swing.JLabel jlTitel;
    private javax.swing.JTable jtDorfliste;
    // End of variables declaration//GEN-END:variables
}
