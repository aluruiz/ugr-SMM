/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sm.prg.eventos;

import java.util.EventListener;

/**
 * Listener del LienzoEvento.
 * @author Paula Ruiz
 */
public interface LienzoListener extends EventListener{
    public void aniadirFigura(LienzoEvento evt);
}
