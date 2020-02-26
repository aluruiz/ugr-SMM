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
 *  Esta clase es un filtro para imagenes que recorre la imagen componente a componente, como final obtienes la misma imagen pero con una secuencia de iluminacion de mas a menos desde el centro.
 * @author Paula Ruiz
 */
public class ComponenteAComponenteOp extends BufferedImageOpAdapter{

    
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
        
        int anchura = srcRaster.getWidth()/8;
        int altura = srcRaster.getHeight()/8;
        int anchuraTotal = srcRaster.getWidth();
        int alturaTotal = srcRaster.getHeight();
        
        for (int x = 0; x < srcRaster.getWidth(); x++) {
            for (int y = 0; y < srcRaster.getHeight(); y++){
                for (int band = 0; band < srcRaster.getNumBands(); band++) {
                    int sample = srcRaster.getSample(x, y, band);
                    if (((y>(altura*3))&&(y<(alturaTotal-(altura*3))))&&((x>(anchura*3))&&(x<(anchuraTotal-(anchura*3))))){
                        sample = iluminar(200,sample);
                    } else if (((y>(altura*2))&&(y<(alturaTotal-(altura*2))))&&((x>(anchura*2))&&(x<(anchuraTotal-(anchura*2))))){
                        sample = iluminar(125,sample);
                    } else if (((y>(altura))&&(y<(alturaTotal-(altura))))&&((x>(anchura))&&(x<(anchuraTotal-(anchura))))){
                        sample = iluminar(75,sample);
                    } else {
                        sample = iluminar(15,sample);
                    }
                    destRaster.setSample(x, y, band, sample);
                }
            }
            
        }
        return dest;
    }
    
    private int iluminar (int parametro, int sample){
        int sample_final=sample;
        
        if((sample_final + parametro)>255){
            sample_final = 255;
        } else {
            sample_final = sample_final + parametro;
        }
        
        return sample_final;
    }
    
}

    
