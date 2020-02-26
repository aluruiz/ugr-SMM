/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sm.prg.graficos;

import java.awt.*;

/**
 * Esta clase abstracta nos permite crear y dar propiedades a Figuras.
 * @author Paula Ruiz
 */

public abstract class Figura{
    /***
     * Nombre de la figura a crear
     */
    String name;
    
    /**
     * Nos devuelve el nombre de la figura.
     * @return String con el nombre de una funcion
     */
    public String getName() {
        return name;
    }
    
    /**
     * Establece el nombre del tipo de figura que estamos dibujando
     * @param name String que contiene el nombre de la figura 
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * Color del borde de la figura
     */
    Color colorTrazo;

    /**
     * Devuelve el valor del color del borde de la figura.
     * @return Color con el color del borde de la figura.
     */
    public Color getColorTrazo() {
        return colorTrazo;
    }
    
    /**
     * Establece el color del borde de la figura
     * @param colorTrazo establece el color que queremos asignarle al borde de la figura
     */
    public void setColorTrazo(Color colorTrazo) {
        this.colorTrazo = colorTrazo;
    }
    
   
    int grosor = 1;
    boolean discontinuo; 
    /**
     * Tipo de borde de la figura
     */
    Stroke trazo = new BasicStroke(grosor);
    
    /**
     * Establece si la figura va a tener discontinuidad o no
     * @param discontinuo es un booleano que si es verdadeor el borde de la figura sera discontinuo y si es falso sera continuo.
     */
    public void setDiscontinuo(boolean discontinuo){
        this.discontinuo = discontinuo;
    }
    
    /**
     * Devuelve si la figura es discontinua o no
     * @return booleano sobre el borde de la figura, discontinua si es verdadero y continua si es falso.
     */
    public boolean getDiscontinuo(){
        return this.discontinuo;
    }
    
    /**
     * Establece el grosor del borde de la figura
     * @param grosor es un entero sobre el tamaño del borde de la figura.
     */
    public void setGrosor (int grosor){
        this.grosor = grosor;
    }
    
    /**
     * Devuelve el valor del grosor del borde de la figura.
     * @return un entero con el tamaño del grosor de la figura.
     */
    public int getGrosor(){
        return grosor;
    }
    
    /**
     * Establece el trazo que contendra la figura
     * @param trazo de tipo Stroke, contiene el trazo de nuestra figura.
     */
    public void setTrazo(Stroke trazo){
        this.trazo = trazo;
    }
    
    /**
     * Tipo de alisado de la figura.
     */
    RenderingHints alisado;
    boolean alisar;
    
    /**
     * Establece un booleano para saber si se quiere alisar la figrua o no.
     * @param alisar de tipo booleano, verdadero si se quiere alisado y falso si no.
     */
    public void setAlisar(boolean alisar){
        this.alisar = alisar;
    }
    
    /**
     * Devuelve el valor del booleano que nos dice si se quiere alisar la figura o no
     * @return booleano verdadero si se busca alisar la figura y falso si no.
     */
    public boolean getAlisar(){
        return alisar;
    }
    
    /**
     * Establece el alisado que contendra la figura
     * @param alisado de tipo RenderingHints, contiene el alisado de nuestra figura.
     */
    public void setAlisado(RenderingHints alisado){
        this.alisado = alisado;
    }
    
    /**
     * Transparencia que tendra nuestra figura
     */
    Composite transparencia;
    float valorTransparencia;
    boolean quieroTransparencia = false;

    /**
     * Asigna el nivel de tranparencia que tendra nuesta figura.
     * @param valorTransparencia, de tipo entero, valor de la transparencia que contendra nuestra figura, oscila entre 0.0f y 1.0f.
     */
    public void setValorTransparencia(float valorTransparencia) {
        this.valorTransparencia = valorTransparencia;
    }
    
    /**
     * Devuelve el valor del nivel de transparencia que tiene nuestra figura.
     * @return Entero con el valor del nivel de transparencia que tiene nuestra figura.
     */
    public float getValorTransparencia(){
        return valorTransparencia;
    }
    
    /**
     * Establece la transparencia que tendra nuestra figura
     * @param transparencia, de tipo Composite, contiene la transparencia de nuestra figura.
     */
    public void setTransparencia(Composite transparencia){
        this.transparencia = transparencia;
    }
    
    /**
     * Rectangulo que contiene a nuestra figura. 
     */
    Rectangle contenedor;
    
    /**
     * Metodo para ir actualizando la figura segun la vamos creando, para que se muestre la posicion aunque el raton no se haya soltado.
     * @param p Punto en el que esta situado el raton.
     */
    public abstract void updateShape(Point p);
    
    /**
     * Metodo para modificar la posicion de la figura. 
     * @param p Punto inicial donde buscamos mover la figura. 
     */
    public abstract void setLocation(Point p); 
    
    /**
     * Metodo para pintar la figura en el lienzo.
     * @param g2d Parametro Graphics2D del lienzo donde se esta pintando.
     */
    public abstract void paint(Graphics2D g2d);
    
    /**
     * Metodo para pintar el rectangulo contenedor de nuestra figura.
     * @param g2d Parametro Graphics2D del lienzo donde se esta pintando.
     */
    public abstract void paintFrame(Graphics2D g2d);
    
    public String toString(){
        return name;
    }
}
