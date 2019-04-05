/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sm.prg.iu;

import sm.prg.graficos.Line;
import sm.prg.graficos.Elipse;
import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.List;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.util.ArrayList;

/**
 *
 * @author Shiri
 */
public class Lienzo2D extends javax.swing.JPanel {
    //Propiedades Iniciales
    protected Color color = new Color(0,0,0);
    protected Stroke stroke = new BasicStroke(1);
    protected Herramientas forma = Herramientas.punto; 
    protected Composite comp;
    protected RenderingHints render;

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
        repaint();
    }

    public Stroke getStroke() {
        return stroke;
    }

    public void setStroke(Stroke stroke) {
        this.stroke = stroke;
        repaint();
    }

    public Herramientas getForma() {
        return forma;
    }

    public void setForma(Herramientas forma) {
        this.forma = forma;
        repaint();
    }
    
    //Vector y Forma Auxiliar
    protected List<Shape> vShape = new ArrayList();
    private Shape sAux; 
    
    //Checkbox
    private boolean relleno;
    private boolean editar; 
    private boolean transparencia; 
    private boolean alisar;
    
    public boolean isRelleno() {
        return relleno;
    }

    public void setRelleno(boolean relleno) {
        this.relleno = relleno;
        repaint();
    }

    public boolean isEditar() {
        return editar;
    }

    public void setEditar(boolean editar) {
        this.editar = editar;
        repaint();
    }

    public boolean isTransparencia() {
        return transparencia;
    }

    public void setTransparencia(boolean transparencia) {
        this.transparencia = transparencia;
        repaint();
    }

    public boolean isAlisar() {
        return alisar;
    }

    public void setAlisar(boolean alisar) {
        this.alisar = alisar;
        repaint();
    }

    //Puntos de posicion
    private Point posicion;
    private Point posicion2;
    
    /**
     * Creates new form Lienzo
     */
    public Lienzo2D() {
        initComponents();
    }
    
    public void paint(Graphics g){
        super.paint(g);
        Graphics2D g2d = (Graphics2D)g;
        //Color
        g2d.setPaint(color);
        
        //Grosor
        g2d.setStroke(stroke);
        
        //Transparencia
        if(transparencia){
            comp = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f);
            g2d.setComposite(comp);
        }
        else{
            comp = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f);
            g2d.setComposite(comp);
        }
        
        //Alisar
        if(alisar){
            render = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setRenderingHints(render);
        }
        else{
            render = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
            g2d.setRenderingHints(render);
        }
        
        //Cambiar las propiedades
        for(Shape s:vShape) {
            if(relleno) g2d.fill(s);
            g2d.draw(s);
        }
    }
    
    public Shape createShape() {
        Shape shape = null; 
        
        //Formas 2D
        switch (forma){
            case punto:
                GeneralPath punto = new GeneralPath();
                punto.moveTo(posicion.x, posicion.y);
                shape = punto; 
                break;
            case linea:
                  Line linea = new Line(); 
                  shape = linea;
                break;
            case rectangulo:
                Rectangle rectangulo = new Rectangle(posicion);
                shape = rectangulo; 
                break; 
            case elipse:
                Elipse elipse = new Elipse();
                shape = elipse; 
                break; 
        }
        
        return shape; 
    }
    
    public void updateShape(Shape shape, Point p) {
            int x, y;
            int ancho, alto;

            if (posicion.x > p.x){
                x = p.x;
                ancho = posicion.x - p.x; 
            }else{
                x = posicion.x;
                ancho = p.x - posicion.x;
            }

            if (posicion.y > p.y){
                y = p.y;
                alto = posicion.y - p.y; 
            }else{
                y = posicion.y;
                alto = p.y - posicion.y;
            }

        switch (forma){
            case punto: 
                ArrayList<Integer> xPoint = new ArrayList();
                ArrayList<Integer> yPoint = new ArrayList(); 
                xPoint.add(p.x);
                yPoint.add(p.y);
                ((GeneralPath) shape).lineTo(xPoint.get(xPoint.size()-1), yPoint.get(yPoint.size()-1));
                break;
            case linea:
                ((Line) shape).setLine(posicion.x, posicion.y, p.x, p.y); 
                break;
            case rectangulo:
                ((Rectangle) shape).setFrameFromDiagonal(posicion, p); 
                break; 
            case elipse:
                ((Elipse) shape).setFrame(x, y, ancho, alto);
                break;
        }
    }
    
    private Shape getSelectedShape(Point2D p){
        for(Shape s:vShape)
        if(s.contains(p)) return s;
        return null;
    }
    
    public void setLocation(Shape s, Point p){
        if(s instanceof Rectangle){
            ((Rectangle) s).setLocation(p);
        }
        else if(s instanceof Elipse){
            ((Elipse) s).setLocation(p);
        }
        else if(s instanceof Line){
            ((Line) s).setLocation(p);
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

    private void formMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMousePressed
        posicion = evt.getPoint();
        if (isEditar()){
            sAux= this.getSelectedShape(posicion);
        }
        else {
            sAux = createShape();
            vShape.add(sAux);
        }
    }//GEN-LAST:event_formMousePressed

    private void formMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseDragged
        posicion2 = evt.getPoint();
        if (isEditar()){
            setLocation(sAux, posicion2);
        }
        else {
            updateShape(vShape.get(vShape.size()-1), posicion2);
        } 
        repaint();
    }//GEN-LAST:event_formMouseDragged

    private void formMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseReleased
        this.formMouseDragged(evt);
    }//GEN-LAST:event_formMouseReleased

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
