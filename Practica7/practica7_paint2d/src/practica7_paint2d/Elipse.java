/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practica7_paint2d;

import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

/**
 *
 * @author Shiri
 */
public class Elipse extends Ellipse2D.Double {
    public void setLocation(Point2D pos){
        double Max, Min; 
        if (this.getHeight() > this.getWidth()){
            Max= this.getHeight();
            Min= this.getWidth();
        }else{
            Max= this.getWidth();
            Min= this.getHeight();
        }
        double nuevaX = pos.getX() - (Max / 2);
        double nuevaY = pos.getY() - (Min / 2);
        this.setFrame(nuevaX, nuevaY, this.getWidth(),this.getHeight());
    }
}
