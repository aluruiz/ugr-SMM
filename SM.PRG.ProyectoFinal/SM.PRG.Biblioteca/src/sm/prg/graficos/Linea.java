/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sm.prg.graficos;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

/**
 * Clase que define una Linea.
 * @author Paula Ruiz
 */
public class Linea extends Figura{
    
    /**
     * Linea que crea la clase para asignarle atributos propios.
     */
    Line2D linea;
    
    /**
     * Punto de la situacion inicial para la creacion de la figura.
     */
    Point2D puntoInicial;
    
    /**
     * Constructor para la figura Linea.
     * @param p1, de tipo Point2D, primer punto donde se empieza a dibujar el rectangulo.
     * @param color, de tipo Color, color del trazo.
     * @param grosor, de tipo entero, tamaño del grosor.
     * @param discontinuo, de tipo booleano, si el trazo sera discontinuo o no.
     * @param trazo, de tipo Stroke, trazo de la figura.
     * @param alisar, de tipo booleano, si la figura va a tener alisado o no.
     * @param alisado, de tipo RenderingHints, alisado de la figura.
     * @param nivel, de tipo float, nivel de la transparencia de la figura.
     * @param transparencia, de tipo Composite, transparencia de la figura.
     */
    public Linea(Point2D p1, Color color, int grosor, boolean discontinuo, Stroke trazo, boolean alisar, RenderingHints alisado, float nivel, Composite transparencia){
        this.name="Linea";
        puntoInicial = p1;
        this.colorTrazo = color;
        this.grosor = grosor;
        this.discontinuo = discontinuo;
        this.trazo = trazo;
        this.alisar = alisar;
        this.alisado = alisado;
        this.valorTransparencia = nivel;
        this.transparencia = transparencia;
        this.linea = new Line2D.Float();
    }
    
    @Override
    public void updateShape(Point p) {
        linea.setLine((int)puntoInicial.getX(), (int)puntoInicial.getY(), p.getX(), p.getY());
    }

    @Override
    public void setLocation(Point p) {
        double dx=p.getX()-linea.getX1();
        double dy=p.getY()-linea.getY1();
        Point2D newp2 = new Point2D.Double(linea.getX2()+dx,linea.getY2()+dy);
        linea.setLine(p,newp2);
    }

    @Override
    public void paint(Graphics2D g2d) {
        g2d.setPaint(colorTrazo);
        
        g2d.setStroke(trazo);
        
        g2d.setComposite(transparencia);
        
        if(alisar){
            g2d.setRenderingHints(alisado);
        }
        
        g2d.draw(linea);
    }

    @Override
    public void paintFrame(Graphics2D g2d) {
        Stroke trazo;
        float patronDiscontinuidad[] = {15.0f, 15.0f};
        trazo = new BasicStroke(2.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER, 1.0f, patronDiscontinuidad, 0.0f);
        
        g2d.setStroke(trazo);
        
        g2d.draw(linea.getBounds());
    }
    
}
