/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplicacionaula;

/**
 *
 * @author Shiri
 */
public class Delegado extends Alumno{
    public char clase;

    public Delegado(String nom, int cur, int na, char clase) {
        super(nom, cur, na);
        this.clase = clase; 
    }
}
