/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practica11_image;

import sm.prg.iu.LienzoImagen2D;

/**
 *
 * @author Shiri
 */
public class VentanaInterna extends javax.swing.JInternalFrame {

    /**
     * Creates new form VentanaInterna
     */
    public VentanaInterna() {
        initComponents();
    }
    
    public LienzoImagen2D getLienzo(){
        return lienzoImagen2D; 
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lienzoImagen2D = new sm.prg.iu.LienzoImagen2D();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);

        javax.swing.GroupLayout lienzoImagen2DLayout = new javax.swing.GroupLayout(lienzoImagen2D);
        lienzoImagen2D.setLayout(lienzoImagen2DLayout);
        lienzoImagen2DLayout.setHorizontalGroup(
            lienzoImagen2DLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 384, Short.MAX_VALUE)
        );
        lienzoImagen2DLayout.setVerticalGroup(
            lienzoImagen2DLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 270, Short.MAX_VALUE)
        );

        getContentPane().add(lienzoImagen2D, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private sm.prg.iu.LienzoImagen2D lienzoImagen2D;
    // End of variables declaration//GEN-END:variables
}
