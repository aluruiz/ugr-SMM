/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sm.prg.graficos;

/**
 * Esta clase abstracta nos permite dar valores especificos a Figuras Rellenables.
 * @author Paula Ruiz
 */

import java.awt.*;

/**
 * Clase abstracta para la asignacion de propiedades para figuras que contengan un area rellenable.
 * @author Paula Ruiz
 */
public abstract class FiguraRellenable extends Figura{
    
    /**
     * Color del relleno de la figura
     */
    Color colorRelleno;
    boolean estaRelleno = false;
    
    /**
     * Devuelve el valor del color del relleno de la figura.
     * @return Color con el color del relleno de la figura. 
     */
    public Color getColorRelleno() {
        return colorRelleno;
    }
    
    /**
     * Establece el color del relleno de la figura
     * @param colorRelleno establece el color que queremos asignarle al relleno de la figura
     */
    public void setColorRelleno(Color colorRelleno) {
        this.colorRelleno = colorRelleno;
    }

    /**
     * Devuelve si buscamos que la figura este rellena o no. Verdadero si queremos que este rellena, falso si no lo queremos.
     * @return de tipo booleano, si esta rellena la figura o no.
     */
    public boolean isEstaRelleno() {
        return estaRelleno;
    }

    /**
     * Establece si la figura va a ser rellenada o no.
     * @param estaRelleno de tipo booleano que nos dice si la figura va a ser rellenada (verdadero) o no (falso).
     */
    public void setEstaRelleno(boolean estaRelleno) {
        this.estaRelleno = estaRelleno;
    }

}
