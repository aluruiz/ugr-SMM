/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mipaint;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

/**
 *
 * @author Shiri
 */
public class Lienzo extends javax.swing.JPanel {
    private Color color = new Color(0,0,0); 
    private boolean relleno = false;
    private Herramientas forma = Herramientas.LAPIZ;
    private Point posicion = new Point(-10,-10);
    private Point posicion2 = new Point(-10,-10);
    /**
     * Creates new form Lienzo
     */
    public Lienzo() {
        initComponents();
    }
    
    //Sets y Gets para las variables
    public Color getColor() {
        return color;
    }
     public void setColor(Color color) {
        this.color = color;
    }

    public boolean isRelleno() {
        return relleno;
    }
    public void setRelleno(boolean relleno) {
        this.relleno = relleno;
    }

    public Herramientas getForma() {
        return forma;
    }
    public void setForma(Herramientas forma) {
        this.forma = forma;
    }
    
    public void setPosicion(Point posicion) {
        this.posicion = posicion;
    }
    
    //Paint
    public void paint(Graphics g){
        super.paint(g);
        g.setColor(this.getColor());
        
        int x, y;
        int ancho, alto;
        
        if (posicion.x > posicion2.x){
            x = posicion2.x;
            ancho = posicion.x - posicion2.x; 
        }else{
            x = posicion.x;
            ancho = posicion2.x - posicion.x;
        }
        
        if (posicion.y > posicion2.y){
            y = posicion2.y;
            alto = posicion.y - posicion2.y; 
        }else{
            y = posicion.y;
            alto = posicion2.y - posicion.y;
        }
        
        switch (forma){
            case LAPIZ:
                g.fillOval(x, y, 7, 7);
                break; 
            case LINEA:
                g.drawLine(posicion2.x, posicion2.y, posicion.x, posicion.y);
                break;
            case RECTANGULO:
                if (this.isRelleno()){
                    g.fillRect(x, y, ancho, alto);
                }else{
                    g.drawRect(x, y, ancho, alto);
                }
                break; 
            case ELIPSE:
                if (this.isRelleno()){
                    g.fillOval(x, y, ancho, alto);
                }else{
                    g.drawOval(x, y, ancho, alto);
                }
                break; 
        }
        
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setBackground(java.awt.Color.white);
        addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                formMouseDragged(evt);
            }
        });
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                formMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                formMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                formMouseReleased(evt);
            }
        });

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

    private void formMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseDragged
        posicion2 = evt.getPoint(); 
        repaint(); 
    }//GEN-LAST:event_formMouseDragged

    private void formMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMousePressed
        posicion = evt.getPoint();
    }//GEN-LAST:event_formMousePressed

    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked
        posicion = evt.getPoint();
        repaint();
    }//GEN-LAST:event_formMouseClicked

    private void formMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseReleased
        posicion2 = evt.getPoint();
    }//GEN-LAST:event_formMouseReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
