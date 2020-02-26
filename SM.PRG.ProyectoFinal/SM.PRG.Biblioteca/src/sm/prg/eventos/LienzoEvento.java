/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sm.prg.eventos;

import java.awt.Color;
import java.util.EventObject;
import sm.prg.graficos.Figura;
/**
 * Clase que contiene la propiedades de un evento propio para el lienzo.
 * @author Paula Ruiz
 */
public class LienzoEvento extends EventObject{
    private Figura forma; 
    private Color colorRelleno;
    private Color colorBorde;
    
    public Figura getForma(){
        return forma;
    }
    
    public Color getColorRelleno(){
        return colorRelleno;
    }
    
    public Color getColorBorde(){
        return colorBorde;
    }
    
    /**
     * Constructor de la clase LienzoEvento
     * @param source, objecto al que se le asocia el evento.
     * @param forma, figura a la que se le asocia el evento.
     */
    public LienzoEvento(Object source, Figura forma) {
        super(source);
        this.forma = forma;
    }
    
}
