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
 * Filtro sepia para una imagen.
 * @author Paula Ruiz
 */
public class SepiaOp extends BufferedImageOpAdapter{
    
    public SepiaOp(){
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
        
        for (int x = 0; x < src.getWidth(); x++){
            for (int y = 0; y < src.getHeight(); y++){
               int[] pixelComp=null;
               pixelComp = srcRaster.getPixel(x,y,pixelComp);
               
               int r = pixelComp[0];
               int g = pixelComp[1];
               int b = pixelComp[2];
               
               pixelComp[0] = (int) Math.min(255, 0.393*r + 0.769*g + 0.189*b);
               pixelComp[1]= (int) Math.min(255, 0.349*r + 0.686*g + 0.168*b);
               pixelComp[2]= (int) Math.min(255, 0.272*r + 0.534*g + 0.131*b);
               
               destRaster.setPixel(x, y, pixelComp);
            }
        }
        return dest;
    }
    
}
