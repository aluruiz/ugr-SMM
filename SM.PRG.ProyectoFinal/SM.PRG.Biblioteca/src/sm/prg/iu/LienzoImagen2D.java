/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sm.prg.iu;

import java.awt.AlphaComposite;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.RescaleOp;

/**
 *
 * @author Paula Ruiz
 */
public class LienzoImagen2D extends Lienzo2D{
    private BufferedImage img;

    /**
     * Funcion que obtine la imagen asociada al lienzo y la devuelve.
     * @return de tipo BufferedImage, imagen asociada al lienzo.
     */
    public BufferedImage getImage(){
        return img;
    }
    
    /**
     * Funcion que obtine la imagen asociada al lienzo y la devuelve, ademas devuelve si tiene figuras dibujadas o no para fusionarlas con la imagen y crear una sola.
     * @param drawVector, si existe un vector de dibujos o no dentro de la imagen.
     * @return de tipo BufferedImage, imagen asociada al lienzo.
     */
    public BufferedImage getImage(boolean drawVector) {
        if (drawVector) {
            BufferedImage imgOut = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());
            paint(imgOut.createGraphics()); 
            return imgOut; 
        }
        else
            return img;
    }

    /**
     * Establece una imagen dentro de un lienzo para su posible procesamiento.
     * @param img, de tipo BufferedImage, imagen que vamos a procesar.
     */
    public void setImage(BufferedImage img) {
        this.img = img;
        if(img!=null) {
            setPreferredSize(new Dimension(img.getWidth(),img.getHeight()));
        }
    }
    
    /**
     * Creates new form LienzoImagen2D
     */
    public LienzoImagen2D() {
        initComponents();
        this.setSize(new Dimension(400,400));
    }
    
    /**
     * Permite agregar dibujos a nuestra imagen.
     * @param g, graphics asociado al lienzo.
     */
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        if(img!=null) g.drawImage(img,0,0,this);
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
