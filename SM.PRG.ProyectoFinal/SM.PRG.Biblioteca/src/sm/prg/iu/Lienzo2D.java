/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sm.prg.iu;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.util.*;
import sm.prg.eventos.LienzoEvento;
import sm.prg.eventos.LienzoListener;
import sm.prg.graficos.*;

/**
 * Clase Lienzo, crea un panel donde poder pintar.
 * @author Paula Ruiz
 */
public class Lienzo2D extends javax.swing.JPanel {
    
    /**
     * Lista de Eventos asociados a la clase.
     */
    ArrayList<LienzoListener> lienzoEventListeners = new ArrayList();
    
    /**
     * Añadir un evento asociado a la clase.
     * @param listener, listener del evento que añadimos.
     */
    public void addLienzoListener (LienzoListener listener) {
        if(listener != null){
            lienzoEventListeners.add(listener);
        }
    }
    
    /**
     * Notificacion de que se ha añadido una figura a la lista.
     * @param evt, de tipo LienzoEvento, momento en el que se añade un objeto a la lista.
     */
    private void notifyAniadirFiguraEvent(LienzoEvento evt){
        if (!lienzoEventListeners.isEmpty()){
            for(LienzoListener listener : lienzoEventListeners) {
                listener.aniadirFigura(evt);
            }
        }
    }

    private Figura figuraAux;
    /**
     * Vector que contiene las figuras asociadas a un lienzo.
     */
    protected List<Figura> vectorFiguras = new ArrayList();

    /**
     * Devuelve la lista de figuras asociadas a un lienzo.
     * @return List de figuras asociadas al lienzo.
     */
    public List<Figura> getVectorFiguras() {
        return vectorFiguras;
    }
    
    /**
     * Forma que se esta usando para dibujar.
     */
    Herramientas forma = Herramientas.linea;
    
    /**
     * Asigna la forma con la que vamos a trabajar en el lienzo.
     * @param forma, tipo de forma.
     */
    public void setForma(Herramientas forma){
        this.forma = forma; 
    }
    
    /**
     * Devuelve el color de relleno que tiene asociado el lienzo en este momento.
     * @return de tipo Color.
     */
    public Color getColorRelleno() {
        return colorRelleno;
    }

    /**
     * Devuelve el color del borde que tiene asociado el lienzo en este momento.
     * @return de tipo Color.
     */
    public Color getColorBorde() {
        return colorBorde;
    }

    /**
     * Devuelve si esta selecionado o no el valor discontinuo en el lienzo.
     * @return de tipo booleano.
     */
    public boolean isDiscontinuo() {
        return discontinuo;
    }

    /**
     * Devuelve el tamaño del grosor que tiene asociado el lienzo en este momento.
     * @return de tipo entero.
     */
    public int getGrosor() {
        return grosor;
    }

    /**
     * Devuelve si esta selecionado o no el valor relleno en el lienzo.
     * @return de tipo booleano.
     */
    public boolean isRelleno() {
        return relleno;
    }
    
    /**
     * Devuelve si esta selecionado o no el alisado en el lienzo.
     * @return de tipo booleano.
     */
    public boolean isAlisar() {
        return alisar;
    }
    
    /**
     * Devuelve el nivel de transparencia que tiene asociado el lienzo en este momento.
     * @return de tipo entero.
     */
    public float getNivel() {
        return nivel;
    }

    /**
     * Devuelve el punto donde se va a transladar la figura.
     * @return, punto nuevo donde se va a situar la figura.
     */
    public Point getPuntoCambio() {
        return puntoCambio;
    }
    
    /**
     * Devuelve la fomar con la que esta trabajando el lienzo.
     * @return de tipo Herramienta.
     */
    public Herramientas getForma(){
        return forma;
    }
    
    /**
     * Devuelve la figura que se esta creando.
     * @return de tipo Figura.
     */
    public Figura getFigura(){
        return figuraAux;
    }
    
    /**
     * Color de relleno que tiene asociado el lienzo. Por defecto el negro.
     */
    Color colorRelleno = new Color(0,0,0);
    
    /**
     * Color de trazo que tiene asociado el lienzo. Por defecto el negro.
     */
    Color colorBorde = new Color(0,0,0);
    
    
    boolean discontinuo = false;
    int grosor = 1;
    
    /**
     * Tipo de trazo que tiene asociado el lienzo.
     */
    Stroke trazo = new BasicStroke(grosor);
    
    boolean relleno = false;
    
    boolean alisar = false;
    /**
     * Tipo de alisado que tiene asociado el lienzo.
     */
    RenderingHints alisado;
    
    float nivel = 1.0f;
    /**
     * Tipo de transparencia que tiene asociado el lienzo.
     */
    Composite transparencia = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,nivel);
    
    /**
     * Establece el nivel de transparencia que va a tener el lienzo.
     * @param nivel, de tipo entero.
     */
    public void setNivel(float nivel){
        if (editar){
           getSelectedFigura(figuraEditar).setValorTransparencia(nivel/100.0f);
           repaint();
        }
        this.nivel = nivel/100.0f;
        setTransparencia();
    }
    
    /**
     * Asigna un tipo de transparencia al lienzo.
     */
    private void setTransparencia(){
        transparencia = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,nivel);
        if(editar){
            getSelectedFigura(figuraEditar).setTransparencia(transparencia);
            repaint();
        }
    }
    
    /**
     * Establece el color de relleno asociado al lienzo.
     * @param colorRelleno, de tipo Color.
     */
    public void setColorRelleno(Color colorRelleno) {
        if(editar){
            ((FiguraRellenable) getSelectedFigura(figuraEditar)).setColorRelleno(colorRelleno);
            repaint();
        } else {
            this.colorRelleno = colorRelleno;
        }
    }
    
    /**
     * Establece el color de borde asociado al lienzo.
     * @param colorBorde, de tipo Color.
     */
    public void setColorBorde(Color colorBorde) {
        if(editar){
            getSelectedFigura(figuraEditar).setColorTrazo(colorBorde);
            repaint();
        } else {
            this.colorBorde = colorBorde;            
        }
    }
    
    /**
     * Establece el tamaño del grosor del borde asociado al lienzo.
     * @param grosor, de tipo entero.
     */
    public void setGrosor(int grosor) {
        if(editar){
            getSelectedFigura(figuraEditar).setGrosor(grosor);
            repaint();
        }
        this.grosor = grosor;
        setTrazo();
    }
    
    /**
     * Establece si el lienzo tiene asociado el trazo continuo o discontinuo.
     * @param discontinuo, de tipo booleano, verdadero si es discontinuo y falso si es continuo.
     */
    public void setDiscontinuo(boolean discontinuo){
        if(editar){
            getSelectedFigura(figuraEditar).setDiscontinuo(discontinuo);
            repaint();
        }
        
        this.discontinuo = discontinuo;
        setTrazo();
    }
    
    /**
     * Asigna el tipo de trazo al lienzo.
     */
    private void setTrazo(){
        if(discontinuo){
            float patronDiscontinuidad[] = {15.0f, 15.0f};
            trazo = new BasicStroke(grosor, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER, 1.0f, patronDiscontinuidad, 0.0f);
        } else {
            trazo = new BasicStroke(grosor);
        }
        
        if(editar){
            getSelectedFigura(figuraEditar).setTrazo(trazo);
            repaint();
        }
    }
    
    /**
     * Asigna si el lienzo va a tener asociado un relleno o no para la figura a mostrar.
     * @param relleno, de tipo booleano, verdadero si esta rellena la figura y falso si no.
     */
    public void setRelleno(boolean relleno) {
        if(editar){
            if(relleno){
                ((FiguraRellenable) getSelectedFigura(figuraEditar)).setEstaRelleno(true);
            } else {
                ((FiguraRellenable) getSelectedFigura(figuraEditar)).setEstaRelleno(false);
            }
            repaint();
        }
        this.relleno = relleno;
    }
    
    /**
     * Establece si el lienzo va a tener asociado el alisado en las figuras que pinte.
     * @param alisar, de tipo booleano, verdadero si la figura se pinta alisada y falso si no.
     */
    public void setAlisar(boolean alisar){
        if(editar){
            getSelectedFigura(figuraEditar).setAlisar(alisar);
            repaint();
        }
        this.alisar = alisar;
        setAlisado();
    }
    
    /**
     * Establece el alisado asociado al lienzo.
     */
    private void setAlisado(){
        alisado = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        if(editar){
            getSelectedFigura(figuraEditar).setAlisado(alisado);
            repaint();
        }
    }
    
    
    boolean editar = false;
    int figuraEditar;
    Point puntoCambio; 
    
    /**
     * Una vez seleccionada una figura en la lista, se le asocia al lienzo como figura que se va a editar.
     * @param figura, de tipo entero, posicion de la figura dentro del vector figuras.
     */
    public void setFiguraEditar(int figura){
        this.figuraEditar = figura; 
        repaint();
    }
    
    /**
     * Devuelve la figura asociada al lienzo que se este editando.
     * @return de tipo Figura.
     */
    public Figura getFiguraEditar(){
        return  getSelectedFigura(figuraEditar);
    }
    
    /**
     * Nos avisa que a partir de ahora lo que se va a hacer es editar las propiedades de una figura no ha crear nuevas.
     * @param editar, de tipo booleano, verdadero si vamos a editar, falso si no.
     */
    public void setEditar(boolean editar){
        this.editar = editar;
    }
    
    /**
     * Devuelve si el lienzo esta en estado editando figura o no.
     * @return de tipo booleano, verdadero si estamos editando, falso si no.
     */
    public boolean getEditar(){
        return editar;
    }
    
    @Override
    public void paint (Graphics g){
        super.paint(g);
        Graphics2D g2d = (Graphics2D)g; 
        
        if(editar){   
            getSelectedFigura(figuraEditar).paintFrame(g2d);
        }
        
        for(Figura f: vectorFiguras){
            f.paint(g2d); 
        }
    }
    
    /**
     * Creates new form Lienzo2D
     */
    public Lienzo2D() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setBackground(new java.awt.Color(255, 255, 255));
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
        Point posicion = evt.getPoint();
        figuraAux = createShape(posicion);
        vectorFiguras.add(figuraAux);
        notifyAniadirFiguraEvent(new LienzoEvento(this, figuraAux));        
    }//GEN-LAST:event_formMousePressed

    private void formMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseDragged
        Point posicion2 = evt.getPoint();
        figuraAux.updateShape(posicion2);
        repaint();
    }//GEN-LAST:event_formMouseDragged

    private void formMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseReleased
        this.formMouseDragged(evt);
        figuraAux = null; 
    }//GEN-LAST:event_formMouseReleased
    
    /**
     * Creacion de figuras dentro del lienzo, luego seran guardadas en el vector de figuras.
     * @param posicion, donde empezamos a crear nuesta primera figura.
     * @return de tipo Figura, figura que hemos creado y añadido a nuestro vector.
     */
    public Figura createShape(Point posicion) {
        Figura figura = null; 
       
        switch (forma){
            case linea:
                Linea linea = new Linea(posicion, colorBorde, grosor, discontinuo, trazo, alisar, alisado, nivel, transparencia);
                figura = linea;
                break;
            case rectangulo:
                Rectangulo rectangulo = new Rectangulo(posicion, colorBorde, colorRelleno, grosor, discontinuo, trazo, alisar, alisado, relleno, nivel, transparencia);
                figura = rectangulo; 
                break; 
            case elipse:
                Elipse elipse = new Elipse(posicion, colorBorde, colorRelleno, grosor, discontinuo, trazo, alisar, alisado, relleno, nivel, transparencia);
                figura = elipse; 
                break; 
        }
        
        return figura; 
    }

    /**
     * Selecciona una figura dentro del vector figuras segun su posicion en el mismo.
     * @param index, de tipo entero.
     * @return de tipo Figura, figura situada en el vector figuras en la posicion del parametro index.
     */
    public Figura getSelectedFigura(int index){
        return vectorFiguras.get(index);
    }
    
    /**
     * Para modificar posicion dentro del lienzo de una figura.
     * @param x, posicion x nueva dentro del lienzo.
     * @param y, posicion y nueva dentro del lienzo.
     */
    public void setLocation(int x, int y){
        puntoCambio = new Point(x,y);
        getSelectedFigura(figuraEditar).setLocation(puntoCambio);
        repaint();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
