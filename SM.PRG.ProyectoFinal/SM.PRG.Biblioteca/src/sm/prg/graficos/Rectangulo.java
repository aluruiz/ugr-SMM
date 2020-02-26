/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sm.prg.graficos;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.geom.Point2D;

/**
 * Clase que define un Rectangulo.
 * @author Paula Ruiz
 */

public class Rectangulo extends FiguraRellenable {
    
    /**
     * Rectangulo que crea la clase para asignarle atributos propios.
     */
    Rectangle rectangulo;
    
    /**
     * Punto de la situacion inicial para la creacion de la figura.
     */
    Point2D puntoInicial;
    
    /**
     * Constructor para la figura Rectangulo.
     * @param p1, de tipo Point2D, primer punto donde se empieza a dibujar el rectangulo.
     * @param colorT, de tipo Color, color del trazo.
     * @param colorR, de tipo Color, color del relleno.
     * @param grosor, de tipo entero, tamaño del grosor.
     * @param discontinuo, de tipo booleano, si el trazo sera discontinuo o no.
     * @param trazo, de tipo Stroke, trazo de la figura.
     * @param alisar, de tipo booleano, si la figura va a tener alisado o no.
     * @param alisado, de tipo RenderingHints, alisado de la figura.
     * @param relleno, de tipo booleano, si la figura va a ser rellenada o no. 
     * @param nivel, de tipo float, nivel de la transparencia de la figura.
     * @param transparencia, de tipo Composite, transparencia de la figura.
     */
    public Rectangulo(Point2D p1, Color colorT, Color colorR, int grosor, boolean discontinuo, Stroke trazo, boolean alisar, RenderingHints alisado, boolean relleno, float nivel, Composite transparencia){
        this.name="Rectangulo";
        this.puntoInicial = p1;
        this.colorTrazo = colorT;
        this.colorRelleno = colorR;
        this.trazo = trazo;
        this.alisar = alisar;
        this.alisado = alisado;
        this.estaRelleno = relleno;
        this.valorTransparencia = nivel;
        this.transparencia = transparencia;
        this.rectangulo = new Rectangle((Point)p1);
    }
    
    @Override
    public void updateShape(Point p) {
        rectangulo.setFrameFromDiagonal(puntoInicial, p);
    }

    @Override
    public void setLocation(Point p) {
        rectangulo.setLocation(p);
        contenedor.setLocation(p);
    }
    
    @Override
    public void paint(Graphics2D g2d) {
        if (estaRelleno){
            g2d.setPaint(colorRelleno);
            g2d.fill(rectangulo);
        }

        g2d.setPaint(colorTrazo);
        g2d.setStroke(trazo);
        
    
        g2d.setComposite(transparencia);

        if(alisar){
            g2d.setRenderingHints(alisado);        
        }
             
        g2d.draw(rectangulo);
        
        Rectangle aux = rectangulo.getBounds();
        
        contenedor = rectangulo.getBounds();
        contenedor.setLocation((int)aux.getX()-5, (int)aux.getY()-5);
        contenedor.setSize((int)aux.getWidth()+10, (int)aux.getHeight()+10);
    }

    @Override
    public void paintFrame(Graphics2D g2d) {
        Stroke trazo;
        float patronDiscontinuidad[] = {15.0f, 15.0f};
        trazo = new BasicStroke(2.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER, 1.0f, patronDiscontinuidad, 0.0f);
        
        g2d.setStroke(trazo);
        
        g2d.draw(contenedor);
    }
}
