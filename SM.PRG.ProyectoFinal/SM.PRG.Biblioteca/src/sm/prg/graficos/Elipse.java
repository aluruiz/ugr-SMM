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
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;

/**
 * Clase que define una Elipse.
 * @author Paula Ruiz
 */
public class Elipse extends FiguraRellenable {
    
    /**
     * Elipse que crea la clase para asignarle atributos propios.
     */
    Ellipse2D elipse;
    
    /**
     * Punto de la situacion inicial para la creacion de la figura.
     */
    Point2D puntoInicial;
    
    /**
     * Valores predeterminados para la asignacion de propiedades de la elipse.
     */
    double x, y, ancho, alto; 
    
    
    /**
     * Constructor para la figura Elipse.
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
    public Elipse(Point2D p1, Color colorT, Color colorR, int grosor, boolean discontinuo, Stroke trazo, boolean alisar, RenderingHints alisado, boolean relleno, float nivel, Composite transparencia){
        this.name="Elipse";
        this.puntoInicial = p1;
        this.colorTrazo = colorT;
        this.colorRelleno = colorR;
        this.trazo = trazo;
        this.alisar = alisar;
        this.alisado = alisado;
        this.estaRelleno = relleno;
        this.valorTransparencia = nivel;
        this.transparencia = transparencia;
        this.elipse = new Ellipse2D.Float();
    }
    
    /**
     * Asigna valores a la propiedades de altura, anchura y puntos x e y, lo utilizamos principalmente para el calculo de ejes y poder crear la figura sin para cualquier lado sin ningun problema.
     * @param p1, de tipo Point2D, punto primero que se busca comparar.
     * @param p2, de tipo Point2D, punto segundo que se busca comparar.
     */
    void calcularAlturaAnchura (Point2D p1, Point2D p2){
            if (p1.getX() > p2.getX()){
                x = p2.getX();
                ancho = (p1.getX() - p2.getX()); 
            }else{
                x = p1.getX();
                ancho = (p2.getX() - p1.getX());
            }

            if (p1.getY() > p2.getY()){
                y = p2.getY();
                alto = (p1.getY() - p2.getY()); 
            }else{
                y = p1.getY();
                alto =  (p2.getY() - p1.getY());
            }
    }
    
    @Override
    public void updateShape(Point p2) {
       calcularAlturaAnchura(puntoInicial, p2);
       elipse.setFrame(x, y, ancho, alto);
    }

    @Override
    public void setLocation(Point p) {
        double Max, Min; 
        if (elipse.getHeight() > elipse.getWidth()){
            Max= elipse.getHeight();
            Min= elipse.getWidth();
        }else{
            Max= elipse.getWidth();
            Min= elipse.getHeight();
        }
        double nuevaX = p.getX() - (Max / 2);
        double nuevaY = p.getY() - (Min / 2);
        
        elipse.setFrame(nuevaX, nuevaY, elipse.getWidth(),elipse.getHeight());
        contenedor.setLocation(p);
        
    }
    
    @Override
    public void paint(Graphics2D g2d) {
        if (estaRelleno){
            g2d.setPaint(colorRelleno);
            g2d.fill(elipse);
        }
        
        g2d.setPaint(colorTrazo);
        
        g2d.setStroke(trazo);
        
        
        g2d.setComposite(transparencia);

        if(alisar){
            g2d.setRenderingHints(alisado);        
        }
        
        g2d.draw(elipse);
        contenedor = elipse.getBounds();
    }

    @Override
    public void paintFrame(Graphics2D g2d){
        Stroke trazo;
        float patronDiscontinuidad[] = {15.0f, 15.0f};
        trazo = new BasicStroke(2.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER, 1.0f, patronDiscontinuidad, 0.0f);
        
        g2d.setStroke(trazo);
        
        g2d.draw(elipse.getFrame());
    }

}
