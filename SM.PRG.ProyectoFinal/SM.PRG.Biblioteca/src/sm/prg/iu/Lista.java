/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sm.prg.iu;

import java.util.ArrayList;
import java.util.List;
import javax.swing.AbstractListModel;
import sm.prg.graficos.Figura;

/**
 * Clase que nos permite crear un JList con una lista de figuras que estan asociadas a un Lienzo.
 * @author Paula Ruiz
 */
public class Lista extends AbstractListModel{
    List<Figura> listaFiguras = new ArrayList();
    
    public Lista(){
    }
    
    @Override
    public int getSize() {
       return listaFiguras.size();
    }

    @Override
    public Object getElementAt(int index) {
        return listaFiguras.get(index);
    }
    /**
     * Añade elementos a la lista de figuras de la clase.
     * @param figura, figura a añadir a la lista.
     */
    public void add(Figura figura){
        listaFiguras.add(figura);
    }
}
