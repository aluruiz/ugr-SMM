/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sm.prg.imagen;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import sm.image.BufferedImageOpAdapter;

/**
 *  Filtro para umbralizar una imagen.
 * @author Paula Ruiz
 */
public class UmbralizacionOp extends BufferedImageOpAdapter{
    private int umbral;
    
    public UmbralizacionOp(int umbral){
        this.umbral = umbral; 
    }
    
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
                int[] pixelComp=null;
                pixelComp = srcRaster.getPixel(x,y,pixelComp);
                float intensidad = (pixelComp[0]+pixelComp[1]+pixelComp[2]);
                
                if(intensidad >= umbral){
                    pixelComp[0] = 255;
                    pixelComp[1] = 255;
                    pixelComp[2] = 255;
                    destRaster.setPixel(x, y, pixelComp);
                } else {
                    pixelComp[0] = 0;
                    pixelComp[1] = 0;
                    pixelComp[2] = 0;
                    destRaster.setPixel(x, y, pixelComp);
                }
            }
        }
        return dest;
    }
    
}
