/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sm.prg.imagen;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import sm.image.BufferedImageOpAdapter;

/**
 * Clase para el filtro de una imagen que la recorrer pixel a pixel, para obtener sus valores rgb y conseguir su complementario.
 * @author Paula Ruiz
 */
public class PixelAPixelOp extends BufferedImageOpAdapter{
    
    @Override
    public BufferedImage filter(BufferedImage src, BufferedImage dest) {
        if(src == null) {
            throw new NullPointerException("Source imagen is null");
        }
        if(dest == null) {
            dest = createCompatibleDestImage(src, null);
        }
        
        WritableRaster srcRaster = src.getRaster();
        WritableRaster destRaster = dest.getRaster();
        
        for (int x = 0; x < srcRaster.getWidth(); x++) {
            for (int y = 0; y < srcRaster.getHeight(); y++) {
                double[] pixelComp=null;
                pixelComp = srcRaster.getPixel(x,y,pixelComp);
                
                pixelComp = RGBtoHSV(pixelComp[0],pixelComp[1],pixelComp[2]);
                
                pixelComp[0] = (pixelComp[0] + 180)%360;
                
                pixelComp = HSVtoRGB(pixelComp[0], pixelComp[1], pixelComp[2]);
               
                destRaster.setPixel(x, y, pixelComp);
            }
        }
        return dest;
    }
    
    /**
     * Transforma unos valores HSV a RGB
     * @param H, valor del Matiz en el modelo de color HSV.
     * @param S, valor de la Saturacion en el modelo de color HSV.
     * @param V, valor del Valor en el modelo de color HSV.
     * @return array de doubles con los valores R, G y B del pixel de la imagen.
     */
    public static double[] HSVtoRGB(double H, double S, double V) {
        double[] pixelComp= new double[3];
        double R=0, G=0, B=0;
        
        double hi ,f, p, q, t;
        
        //Hi 
        hi = (H/60)%6;
        
        //f
        f = ((H/60)%6)-hi;
        
        //p
        p=V*(1-S);
        
        //q
        q = V*(1-f*S);
        
        //t
        t= V*(1-(1-f)*S);
        
        switch ((int)hi){
            case 0:
                R = V;
                G = t;
                B = p;
                break;
            case 1:
                R = q;
                G = V;
                B = p;
                break;
            case 2:
                R = p;
                G = V;
                B = t;
                break;
            case 3:
                R = p;
                G = q;
                B = V;
                break; 
            case 4: 
                R = t;
                G = p;
                B = V;
                break;
            case 5:
                R = V;
                G = p;
                B = q;
                break;
        }
        
        pixelComp[0]=R;
        pixelComp[1]=G;
        pixelComp[2]=B;
      
        return pixelComp;
    }
    
    /**
     * Transforma unos valores RGB a HSV
     * @param r, valor del Rojo en el modelo de color RGB.
     * @param g, valor del Verde en el modelo de color RGB.
     * @param b, valor del Azul en el modelo de color RGB.
     * @return array de doubles con los valores H, S y V del pixel de la imagen.
     */
    public static double[] RGBtoHSV(double r, double g, double b){
        //
    double h, s, v;

    double min, max, delta;

    min = Math.min(Math.min(r, g), b);
    max = Math.max(Math.max(r, g), b);

    // V
    v = max;

     delta = max - min;

    // S
     if( max != 0 )
        s = delta / max;
     else {
        s = 0;
        h = -1;
        return new double[]{h,s,v};
     }

    // H
     if( r == max )
        h = ( g - b ) / delta;
     else if( g == max )
        h = 2 + ( b - r ) / delta;
     else
        h = 4 + ( r - g ) / delta;

     h *= 60;

    if( h < 0 )
        h += 360;

    return new double[]{h,s,v};
    }
    
    
}
