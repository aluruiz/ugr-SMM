/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto;

import sm.prg.iu.Lista;
import sm.prg.iu.ColorRender; 
import java.awt.Color;
import java.awt.Point;
import java.awt.Transparency;
import java.awt.color.ColorSpace;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.ByteLookupTable;
import java.awt.image.ColorModel;
import java.awt.image.ComponentColorModel;
import java.awt.image.ConvolveOp;
import java.awt.image.DataBuffer;
import java.awt.image.Kernel;
import java.awt.image.LookupOp;
import java.awt.image.LookupTable;
import java.awt.image.RescaleOp;
import java.awt.image.WritableRaster;
import java.beans.PropertyVetoException;
import java.io.File;
import java.util.List;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFileChooser;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import sm.image.BlendOp;
import sm.image.EqualizationOp;
import sm.image.KernelProducer;
import sm.image.LookupTableProducer;
import sm.image.SubtractionOp;
import sm.image.TintOp;
import sm.image.color.ColorConvertOp;
import sm.prg.eventos.LienzoAdapter;
import sm.prg.eventos.LienzoEvento;
import sm.prg.graficos.Figura;
import sm.prg.graficos.FiguraRellenable;
import sm.prg.imagen.ComponenteAComponenteOp;
import sm.prg.imagen.PixelAPixelOp;
import sm.prg.imagen.SepiaOp;
import sm.prg.imagen.UmbralizacionOp;
import sm.prg.iu.Herramientas;
import sm.sound.*;
import uk.co.caprica.vlcj.player.MediaPlayer;
import uk.co.caprica.vlcj.player.MediaPlayerEventAdapter;

/**
 *
 * @author Paula Ruiz
 */
public class VentanaPrincipal extends javax.swing.JFrame {
    /**
     * Creates new form ventanaPrincipal
     */
    
    private JFileChooser fileChooser = new JFileChooser(); 
    
    //Variables Graphics
    private Color colores[] = {Color.black, Color.white, Color.blue, Color.green, Color.red, Color.yellow}; 
    int contadorFiguras = 0; 
    
    //Variables Imagen
    private BufferedImage imgSource;
    
    //Variables Audio
    SMPlayer player = null;
    SMRecorder recorder = null;
    boolean grabando = false;
     
    //Variables Video
    
    
    //Clases Manejadoras
    //Graphics
    public class MiManejadorLienzo extends LienzoAdapter{
        public void aniadirFigura(LienzoEvento evt){
            System.out.println("Figura " + evt.getForma() + " añadida");
            actualizarLista();
        }
    }
    
    public void actualizarPropiedades(){
        VentanaInterna vi=(VentanaInterna) escritorio.getSelectedFrame();
        Figura aux = vi.getLienzo().getFiguraEditar();
        
        if(aux.getName() == "Linea"){
            buttonLinea.setSelected(true);
        } else if(aux.getName() == "Rectangulo") {
            buttonRectangulo.setSelected(true);
        } else if(aux.getName() == "Elipse") {
            buttonElipse.setSelected(true);
        }
        
        if((aux.getName() == "Elipse") || (aux.getName() == "Rectangulo")){
            coloresRellenoSelector.setSelectedItem((Color)((FiguraRellenable) aux).getColorRelleno());
            
            if(((FiguraRellenable) aux).isEstaRelleno()){
                comboBoxRelleno.setSelectedItem("Liso");
            } else {
                comboBoxRelleno.setSelectedItem("Sin Relleno");
            }
            
            sliderTransparencia.setValue((int)((FiguraRellenable) aux).getValorTransparencia()*100);
        }
        
        coloresBordeSelector.setSelectedItem((Color)aux.getColorTrazo());
        spinnerGrosor.setValue(aux.getGrosor());
        
        if(aux.getDiscontinuo()){
            comboBoxTipoBorde.setSelectedItem("Discontinua");
        } else {
            comboBoxTipoBorde.setSelectedItem("Continua");
        }
        
        if(aux.getAlisar()){
            buttonAlisar.setSelected(true);
        } else {
            buttonAlisar.setSelected(false);
        }
    }
    
    public void actualizarPropiedadesLienzo(){
        VentanaInterna vi=(VentanaInterna) escritorio.getSelectedFrame();
        
        if(vi.getLienzo().getForma() == Herramientas.linea){
            buttonLinea.setSelected(true);
        } else if(vi.getLienzo().getForma() == Herramientas.rectangulo) {
            buttonRectangulo.setSelected(true);
        } else if(vi.getLienzo().getForma() == Herramientas.elipse) {
            buttonElipse.setSelected(true);
        }
        
        coloresRellenoSelector.setSelectedItem(vi.getLienzo().getColorRelleno());
            
        if(vi.getLienzo().isRelleno()){
            comboBoxRelleno.setSelectedItem("Liso");
        } else {
            comboBoxRelleno.setSelectedItem("Sin Relleno");
        }
            
        sliderTransparencia.setValue((int)vi.getLienzo().getNivel()*100);
        spinnerGrosor.setValue(vi.getLienzo().getGrosor());
        coloresBordeSelector.setSelectedItem(vi.getLienzo().getColorBorde());
        
        if(vi.getLienzo().isDiscontinuo()){
            comboBoxRelleno.setSelectedItem("Liso");
        } else {
            comboBoxRelleno.setSelectedItem("Sin Relleno");
        }
        
        if(vi.getLienzo().isAlisar()){
            buttonAlisar.setSelected(true);
        } else {
            buttonAlisar.setSelected(false);
        }
    }
    
    MiManejadorLienzo manejador = new MiManejadorLienzo();
    
    //Sonido
    class ManejadorAudio implements LineListener {
        @Override
        public void update(LineEvent event){
            if(event.getType() == LineEvent.Type.START){
                buttonReproducir.setEnabled(false);
                buttonGrabar.setEnabled(false);
            }
            
            if(event.getType() == LineEvent.Type.STOP){
                buttonReproducir.setEnabled(true);
                buttonGrabar.setEnabled(true);
            }
            
            if(event.getType() == LineEvent.Type.CLOSE){
                buttonReproducir.setEnabled(true);
                buttonGrabar.setEnabled(true);
            }
        }
    }
    
    //Video
    private class VideoListener extends MediaPlayerEventAdapter {
        public void playing(MediaPlayer mediaPlayer){
            buttonDetener.setEnabled(true);
            buttonReproducir.setEnabled(false);
        }
        
        public void paused(MediaPlayer mediaPlayer){
            buttonDetener.setEnabled(false);
            buttonReproducir.setEnabled(true);
        }
        
        public void finished(MediaPlayer mediaPlayer){
            this.paused(mediaPlayer);
        }
    }
    
    public VentanaPrincipal() {
        initComponents();
        coloresRellenoSelector.setModel(new DefaultComboBoxModel(colores));
        coloresRellenoSelector.setRenderer(new ColorRender());
        coloresBordeSelector.setModel(new DefaultComboBoxModel(colores));
        coloresBordeSelector.setRenderer(new ColorRender());
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        formas = new javax.swing.ButtonGroup();
        panelPropiedades = new javax.swing.JPanel();
        etiqueta = new javax.swing.JLabel();
        panelImagenes = new javax.swing.JPanel();
        herramientasImagenes = new javax.swing.JToolBar();
        panelDuplicar = new javax.swing.JPanel();
        etiquetaDuplicar = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        buttonDuplicar = new javax.swing.JButton();
        panelBrillo = new javax.swing.JPanel();
        panelSliderBrillo = new javax.swing.JPanel();
        sliderBrillo = new javax.swing.JSlider();
        etiquetaBrillo = new javax.swing.JLabel();
        panelFiltro = new javax.swing.JPanel();
        etiquetaFiltros = new javax.swing.JLabel();
        panelComboBoxFiltro = new javax.swing.JPanel();
        comboBoxFiltros = new javax.swing.JComboBox<>();
        panelContraste = new javax.swing.JPanel();
        etiquetaContraste = new javax.swing.JLabel();
        panelBotonNegativo = new javax.swing.JPanel();
        buttonNegativo = new javax.swing.JButton();
        buttonSepia = new javax.swing.JButton();
        buttonTintado = new javax.swing.JButton();
        buttonEcualizacion = new javax.swing.JButton();
        panelBotonesContraste = new javax.swing.JPanel();
        buttonContrasteNormal = new javax.swing.JButton();
        buttonContrasteIluminado = new javax.swing.JButton();
        buttonContrasteOscurecido = new javax.swing.JButton();
        panelBandas = new javax.swing.JPanel();
        etiquetaColor = new javax.swing.JLabel();
        panelOpcionesBandas = new javax.swing.JPanel();
        buttonBandas = new javax.swing.JButton();
        comboBoxEspacioColor = new javax.swing.JComboBox<>();
        panelRotacion = new javax.swing.JPanel();
        etiquetaRotacion = new javax.swing.JLabel();
        panelBotonesRotacion = new javax.swing.JPanel();
        sliderRotacion = new javax.swing.JSlider();
        panelEscala = new javax.swing.JPanel();
        etiquetaEscala = new javax.swing.JLabel();
        panelBotonesEscala = new javax.swing.JPanel();
        buttonAumentar = new javax.swing.JButton();
        buttonReducir = new javax.swing.JButton();
        panelBinarias = new javax.swing.JPanel();
        etiquetaBinarios = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        buttonSuma = new javax.swing.JButton();
        buttonResta = new javax.swing.JButton();
        panelUmbralizacion = new javax.swing.JPanel();
        etiquetaUmbralizacion = new javax.swing.JLabel();
        panelSliderUmbralizacion = new javax.swing.JPanel();
        sliderUmbralizacion = new javax.swing.JSlider();
        panelPropias = new javax.swing.JPanel();
        panelInteriorPropias = new javax.swing.JPanel();
        etiquetaPropias = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        buttonPropiaFuncion = new javax.swing.JButton();
        buttonPropiaPixel = new javax.swing.JButton();
        buttonPropiaComponente = new javax.swing.JButton();
        panelHerramientas = new javax.swing.JPanel();
        herramientasFiguras = new javax.swing.JToolBar();
        panelFormas = new javax.swing.JPanel();
        etiquetaHerramientas = new javax.swing.JLabel();
        panelInteriorFormas = new javax.swing.JPanel();
        buttonLinea = new javax.swing.JToggleButton();
        buttonRectangulo = new javax.swing.JToggleButton();
        buttonElipse = new javax.swing.JToggleButton();
        jSeparator2 = new javax.swing.JToolBar.Separator();
        panelColorRelleno = new javax.swing.JPanel();
        etiquetaColorRelleno = new javax.swing.JLabel();
        panelInteriorRelleno = new javax.swing.JPanel();
        coloresRellenoSelector = new javax.swing.JComboBox<>();
        comboBoxRelleno = new javax.swing.JComboBox<>();
        panelColorBorde = new javax.swing.JPanel();
        etiquetaColorBorde = new javax.swing.JLabel();
        panelInteriorBorde = new javax.swing.JPanel();
        coloresBordeSelector = new javax.swing.JComboBox<>();
        spinnerGrosor = new javax.swing.JSpinner();
        comboBoxTipoBorde = new javax.swing.JComboBox<>();
        jSeparator3 = new javax.swing.JToolBar.Separator();
        panelTransparencia = new javax.swing.JPanel();
        etiquetaTransparencia = new javax.swing.JLabel();
        panelInteriorTransparencia = new javax.swing.JPanel();
        sliderTransparencia = new javax.swing.JSlider();
        panelAlisado = new javax.swing.JPanel();
        etiquetaAlisado = new javax.swing.JLabel();
        buttonAlisar = new javax.swing.JToggleButton();
        jSeparator4 = new javax.swing.JToolBar.Separator();
        panelPosicion = new javax.swing.JPanel();
        etiquetaPosicion = new javax.swing.JLabel();
        panelInteriorPosicion = new javax.swing.JPanel();
        posicionX = new javax.swing.JSpinner();
        posicionY = new javax.swing.JSpinner();
        buttonMover = new javax.swing.JButton();
        herramientasAudioVideo = new javax.swing.JToolBar();
        jPanel5 = new javax.swing.JPanel();
        etiquetaSonido = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        buttonReproducir = new javax.swing.JButton();
        listaReproduccion = new javax.swing.JComboBox<>();
        buttonDetener = new javax.swing.JButton();
        buttonGrabar = new javax.swing.JButton();
        buttonCamara = new javax.swing.JButton();
        buttonCapturar = new javax.swing.JButton();
        scroll = new javax.swing.JScrollPane();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        listaFiguras = new javax.swing.JList<>();
        escritorio = new javax.swing.JDesktopPane();
        barraNavegacion = new javax.swing.JMenuBar();
        menuArchivo = new javax.swing.JMenu();
        opcionNuevo = new javax.swing.JMenuItem();
        opcionAbrir = new javax.swing.JMenuItem();
        opcionGuardar = new javax.swing.JMenuItem();
        menuVer = new javax.swing.JMenu();
        verHerramientasDibujo = new javax.swing.JCheckBoxMenuItem();
        verHerramientasImagen = new javax.swing.JCheckBoxMenuItem();
        verHerramientasSonidoVideo = new javax.swing.JCheckBoxMenuItem();
        menuAyuda = new javax.swing.JMenu();
        opcionAcercaDe = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        panelPropiedades.setLayout(new java.awt.BorderLayout());
        panelPropiedades.add(etiqueta, java.awt.BorderLayout.PAGE_END);

        panelImagenes.setLayout(new javax.swing.BoxLayout(panelImagenes, javax.swing.BoxLayout.LINE_AXIS));

        herramientasImagenes.setRollover(true);

        panelDuplicar.setLayout(new java.awt.BorderLayout());

        etiquetaDuplicar.setText("Duplicar");
        panelDuplicar.add(etiquetaDuplicar, java.awt.BorderLayout.PAGE_START);

        buttonDuplicar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/nuevo.png"))); // NOI18N
        buttonDuplicar.setToolTipText("Boton para duplicar la imagen.");
        buttonDuplicar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonDuplicarActionPerformed(evt);
            }
        });
        jPanel4.add(buttonDuplicar);

        panelDuplicar.add(jPanel4, java.awt.BorderLayout.CENTER);

        herramientasImagenes.add(panelDuplicar);

        panelBrillo.setLayout(new java.awt.BorderLayout());

        panelSliderBrillo.setLayout(new java.awt.BorderLayout());

        sliderBrillo.setToolTipText("Deslizador para  aplicar el brillo.");
        sliderBrillo.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                sliderBrilloStateChanged(evt);
            }
        });
        sliderBrillo.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                sliderBrilloFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                sliderBrilloFocusLost(evt);
            }
        });
        panelSliderBrillo.add(sliderBrillo, java.awt.BorderLayout.CENTER);

        panelBrillo.add(panelSliderBrillo, java.awt.BorderLayout.CENTER);

        etiquetaBrillo.setText("Brillo");
        panelBrillo.add(etiquetaBrillo, java.awt.BorderLayout.PAGE_START);

        herramientasImagenes.add(panelBrillo);

        panelFiltro.setLayout(new java.awt.BorderLayout());

        etiquetaFiltros.setText("Filtros");
        panelFiltro.add(etiquetaFiltros, java.awt.BorderLayout.PAGE_START);

        comboBoxFiltros.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Media", "Binomial", "Enfoque", "Relieve", "Fronteras" }));
        comboBoxFiltros.setToolTipText("Desplegable con filtros.");
        comboBoxFiltros.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboBoxFiltrosActionPerformed(evt);
            }
        });
        panelComboBoxFiltro.add(comboBoxFiltros);

        panelFiltro.add(panelComboBoxFiltro, java.awt.BorderLayout.CENTER);

        herramientasImagenes.add(panelFiltro);

        panelContraste.setLayout(new java.awt.BorderLayout());

        etiquetaContraste.setText("Contraste");
        panelContraste.add(etiquetaContraste, java.awt.BorderLayout.PAGE_START);

        buttonNegativo.setText("Negativo");
        buttonNegativo.setToolTipText("Aplica el filtro negativo.");
        buttonNegativo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonNegativoActionPerformed(evt);
            }
        });
        panelBotonNegativo.add(buttonNegativo);

        buttonSepia.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/sepia.png"))); // NOI18N
        buttonSepia.setToolTipText("Aplica el filtro Sepia.");
        buttonSepia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonSepiaActionPerformed(evt);
            }
        });
        panelBotonNegativo.add(buttonSepia);

        buttonTintado.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/tintar.png"))); // NOI18N
        buttonTintado.setToolTipText("Aplica el filtro de tintado.");
        buttonTintado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonTintadoActionPerformed(evt);
            }
        });
        panelBotonNegativo.add(buttonTintado);

        buttonEcualizacion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/ecualizar.png"))); // NOI18N
        buttonEcualizacion.setToolTipText("Aplica la ecualizacion.");
        buttonEcualizacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonEcualizacionActionPerformed(evt);
            }
        });
        panelBotonNegativo.add(buttonEcualizacion);

        panelContraste.add(panelBotonNegativo, java.awt.BorderLayout.LINE_END);

        buttonContrasteNormal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/contraste.png"))); // NOI18N
        buttonContrasteNormal.setToolTipText("Aplica el constraste normal.");
        buttonContrasteNormal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonContrasteNormalActionPerformed(evt);
            }
        });
        panelBotonesContraste.add(buttonContrasteNormal);

        buttonContrasteIluminado.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/iluminar.png"))); // NOI18N
        buttonContrasteIluminado.setToolTipText("Aplica el contraste para iluminar.");
        buttonContrasteIluminado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonContrasteIluminadoActionPerformed(evt);
            }
        });
        panelBotonesContraste.add(buttonContrasteIluminado);

        buttonContrasteOscurecido.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/oscurecer.png"))); // NOI18N
        buttonContrasteOscurecido.setToolTipText("Aplica el contraste para oscurecer.");
        buttonContrasteOscurecido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonContrasteOscurecidoActionPerformed(evt);
            }
        });
        panelBotonesContraste.add(buttonContrasteOscurecido);

        panelContraste.add(panelBotonesContraste, java.awt.BorderLayout.CENTER);

        herramientasImagenes.add(panelContraste);

        panelBandas.setLayout(new java.awt.BorderLayout());

        etiquetaColor.setText("Color");
        panelBandas.add(etiquetaColor, java.awt.BorderLayout.PAGE_START);

        buttonBandas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/bandas.png"))); // NOI18N
        buttonBandas.setToolTipText("Muestra las bandas.");
        buttonBandas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonBandasActionPerformed(evt);
            }
        });
        panelOpcionesBandas.add(buttonBandas);

        comboBoxEspacioColor.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "RGB", "YCC", "GREY" }));
        comboBoxEspacioColor.setToolTipText("Cambio de Espacio de Color.");
        comboBoxEspacioColor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboBoxEspacioColorActionPerformed(evt);
            }
        });
        panelOpcionesBandas.add(comboBoxEspacioColor);

        panelBandas.add(panelOpcionesBandas, java.awt.BorderLayout.CENTER);

        herramientasImagenes.add(panelBandas);

        panelRotacion.setLayout(new java.awt.BorderLayout());

        etiquetaRotacion.setText("Rotacion");
        panelRotacion.add(etiquetaRotacion, java.awt.BorderLayout.PAGE_START);

        sliderRotacion.setMajorTickSpacing(90);
        sliderRotacion.setMaximum(360);
        sliderRotacion.setPaintTicks(true);
        sliderRotacion.setToolTipText("Rotación de la imagen.");
        sliderRotacion.setValue(0);
        sliderRotacion.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                sliderRotacionStateChanged(evt);
            }
        });
        sliderRotacion.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                sliderRotacionFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                sliderRotacionFocusLost(evt);
            }
        });
        panelBotonesRotacion.add(sliderRotacion);

        panelRotacion.add(panelBotonesRotacion, java.awt.BorderLayout.CENTER);

        herramientasImagenes.add(panelRotacion);

        panelEscala.setLayout(new java.awt.BorderLayout());

        etiquetaEscala.setText("Escala");
        panelEscala.add(etiquetaEscala, java.awt.BorderLayout.PAGE_START);

        buttonAumentar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/aumentar.png"))); // NOI18N
        buttonAumentar.setToolTipText("Aumenta la imagen");
        buttonAumentar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonAumentarActionPerformed(evt);
            }
        });
        panelBotonesEscala.add(buttonAumentar);

        buttonReducir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/disminuir.png"))); // NOI18N
        buttonReducir.setToolTipText("Disminuye la imagen");
        buttonReducir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonReducirActionPerformed(evt);
            }
        });
        panelBotonesEscala.add(buttonReducir);

        panelEscala.add(panelBotonesEscala, java.awt.BorderLayout.CENTER);

        herramientasImagenes.add(panelEscala);

        panelBinarias.setLayout(new java.awt.BorderLayout());

        etiquetaBinarios.setText("Operadores Binarios");
        panelBinarias.add(etiquetaBinarios, java.awt.BorderLayout.PAGE_START);

        buttonSuma.setText("Suma");
        buttonSuma.setToolTipText("Boton para suma de imagenes.");
        buttonSuma.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonSumaActionPerformed(evt);
            }
        });
        jPanel1.add(buttonSuma);

        buttonResta.setText("Resta");
        buttonResta.setToolTipText("Boton para resta de imagenes.");
        buttonResta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonRestaActionPerformed(evt);
            }
        });
        jPanel1.add(buttonResta);

        panelBinarias.add(jPanel1, java.awt.BorderLayout.CENTER);

        herramientasImagenes.add(panelBinarias);

        panelUmbralizacion.setLayout(new java.awt.BorderLayout());

        etiquetaUmbralizacion.setText("Umbralizacion");
        panelUmbralizacion.add(etiquetaUmbralizacion, java.awt.BorderLayout.PAGE_START);

        sliderUmbralizacion.setMaximum(255);
        sliderUmbralizacion.setToolTipText("Deslizador para aplicar umbralizacion.");
        sliderUmbralizacion.setValue(128);
        sliderUmbralizacion.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                sliderUmbralizacionStateChanged(evt);
            }
        });
        sliderUmbralizacion.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                sliderUmbralizacionFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                sliderUmbralizacionFocusLost(evt);
            }
        });
        panelSliderUmbralizacion.add(sliderUmbralizacion);

        panelUmbralizacion.add(panelSliderUmbralizacion, java.awt.BorderLayout.CENTER);

        herramientasImagenes.add(panelUmbralizacion);

        panelPropias.setLayout(new java.awt.BorderLayout());

        panelInteriorPropias.setLayout(new java.awt.BorderLayout());

        etiquetaPropias.setText("Propias");
        panelInteriorPropias.add(etiquetaPropias, java.awt.BorderLayout.PAGE_START);

        buttonPropiaFuncion.setText("Funcion");
        buttonPropiaFuncion.setToolTipText("Aplica el filtro de funcion Lookup..");
        buttonPropiaFuncion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonPropiaFuncionActionPerformed(evt);
            }
        });
        jPanel2.add(buttonPropiaFuncion);

        buttonPropiaPixel.setText("Pixel");
        buttonPropiaPixel.setToolTipText("Aplica el filtro Cambio de color.");
        buttonPropiaPixel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonPropiaPixelActionPerformed(evt);
            }
        });
        jPanel2.add(buttonPropiaPixel);

        buttonPropiaComponente.setText("Componente");
        buttonPropiaComponente.setToolTipText("Aplica el filtro Iluminador por zonas.");
        buttonPropiaComponente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonPropiaComponenteActionPerformed(evt);
            }
        });
        jPanel2.add(buttonPropiaComponente);

        panelInteriorPropias.add(jPanel2, java.awt.BorderLayout.CENTER);

        panelPropias.add(panelInteriorPropias, java.awt.BorderLayout.CENTER);

        herramientasImagenes.add(panelPropias);

        panelImagenes.add(herramientasImagenes);

        panelPropiedades.add(panelImagenes, java.awt.BorderLayout.CENTER);

        getContentPane().add(panelPropiedades, java.awt.BorderLayout.PAGE_END);

        panelHerramientas.setLayout(new javax.swing.BoxLayout(panelHerramientas, javax.swing.BoxLayout.LINE_AXIS));

        herramientasFiguras.setRollover(true);

        panelFormas.setLayout(new java.awt.BorderLayout());

        etiquetaHerramientas.setText("Herramientas");
        panelFormas.add(etiquetaHerramientas, java.awt.BorderLayout.PAGE_START);

        formas.add(buttonLinea);
        buttonLinea.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/linea.png"))); // NOI18N
        buttonLinea.setToolTipText("Herramienta Linea.");
        buttonLinea.setFocusable(false);
        buttonLinea.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        buttonLinea.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        buttonLinea.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonLineaActionPerformed(evt);
            }
        });
        panelInteriorFormas.add(buttonLinea);

        formas.add(buttonRectangulo);
        buttonRectangulo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/rectangulo.png"))); // NOI18N
        buttonRectangulo.setToolTipText("Herramienta Rectangulo.");
        buttonRectangulo.setFocusable(false);
        buttonRectangulo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        buttonRectangulo.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        buttonRectangulo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonRectanguloActionPerformed(evt);
            }
        });
        panelInteriorFormas.add(buttonRectangulo);

        formas.add(buttonElipse);
        buttonElipse.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/elipse.png"))); // NOI18N
        buttonElipse.setToolTipText("Herramienta Elipse.");
        buttonElipse.setFocusable(false);
        buttonElipse.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        buttonElipse.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        buttonElipse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonElipseActionPerformed(evt);
            }
        });
        panelInteriorFormas.add(buttonElipse);

        panelFormas.add(panelInteriorFormas, java.awt.BorderLayout.CENTER);

        herramientasFiguras.add(panelFormas);
        herramientasFiguras.add(jSeparator2);

        panelColorRelleno.setLayout(new java.awt.BorderLayout());

        etiquetaColorRelleno.setText("Relleno / Tintado");
        panelColorRelleno.add(etiquetaColorRelleno, java.awt.BorderLayout.PAGE_START);

        coloresRellenoSelector.setToolTipText("Colores Relleno.");
        coloresRellenoSelector.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                coloresRellenoSelectorActionPerformed(evt);
            }
        });
        panelInteriorRelleno.add(coloresRellenoSelector);

        comboBoxRelleno.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Sin Relleno", "Liso" }));
        comboBoxRelleno.setToolTipText("Tipo de Relleno.");
        comboBoxRelleno.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboBoxRellenoActionPerformed(evt);
            }
        });
        panelInteriorRelleno.add(comboBoxRelleno);

        panelColorRelleno.add(panelInteriorRelleno, java.awt.BorderLayout.CENTER);

        herramientasFiguras.add(panelColorRelleno);

        panelColorBorde.setLayout(new java.awt.BorderLayout());

        etiquetaColorBorde.setText("Borde");
        panelColorBorde.add(etiquetaColorBorde, java.awt.BorderLayout.PAGE_START);

        coloresBordeSelector.setToolTipText("Colores Borde.");
        coloresBordeSelector.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                coloresBordeSelectorActionPerformed(evt);
            }
        });
        panelInteriorBorde.add(coloresBordeSelector);

        spinnerGrosor.setToolTipText("Tamaño del grosor.");
        spinnerGrosor.setPreferredSize(new java.awt.Dimension(40, 25));
        spinnerGrosor.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                spinnerGrosorStateChanged(evt);
            }
        });
        panelInteriorBorde.add(spinnerGrosor);

        comboBoxTipoBorde.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Continua", "Discontinua" }));
        comboBoxTipoBorde.setToolTipText("Tipo de Borde.");
        comboBoxTipoBorde.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboBoxTipoBordeActionPerformed(evt);
            }
        });
        panelInteriorBorde.add(comboBoxTipoBorde);

        panelColorBorde.add(panelInteriorBorde, java.awt.BorderLayout.CENTER);

        herramientasFiguras.add(panelColorBorde);
        herramientasFiguras.add(jSeparator3);

        panelTransparencia.setLayout(new java.awt.BorderLayout());

        etiquetaTransparencia.setText("Transparencia");
        panelTransparencia.add(etiquetaTransparencia, java.awt.BorderLayout.PAGE_START);

        sliderTransparencia.setToolTipText("Nivel de transparencia que tienes.");
        sliderTransparencia.setValue(100);
        sliderTransparencia.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                sliderTransparenciaStateChanged(evt);
            }
        });
        panelInteriorTransparencia.add(sliderTransparencia);

        panelTransparencia.add(panelInteriorTransparencia, java.awt.BorderLayout.LINE_START);

        herramientasFiguras.add(panelTransparencia);

        panelAlisado.setLayout(new java.awt.BorderLayout());

        etiquetaAlisado.setText("Alisado");
        panelAlisado.add(etiquetaAlisado, java.awt.BorderLayout.PAGE_START);

        buttonAlisar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/alisar.png"))); // NOI18N
        buttonAlisar.setToolTipText("Si quieres alisado o no.");
        buttonAlisar.setFocusable(false);
        buttonAlisar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        buttonAlisar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        buttonAlisar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonAlisarActionPerformed(evt);
            }
        });
        panelAlisado.add(buttonAlisar, java.awt.BorderLayout.CENTER);

        herramientasFiguras.add(panelAlisado);
        herramientasFiguras.add(jSeparator4);

        panelPosicion.setLayout(new java.awt.BorderLayout());

        etiquetaPosicion.setText("Mover posicion");
        panelPosicion.add(etiquetaPosicion, java.awt.BorderLayout.PAGE_START);

        posicionX.setToolTipText("Posicion X nueva.");
        posicionX.setMinimumSize(new java.awt.Dimension(40, 20));
        posicionX.setPreferredSize(new java.awt.Dimension(60, 30));
        panelInteriorPosicion.add(posicionX);

        posicionY.setToolTipText("Posicion Y nueva.");
        posicionY.setPreferredSize(new java.awt.Dimension(60, 30));
        panelInteriorPosicion.add(posicionY);

        buttonMover.setText("Mover");
        buttonMover.setToolTipText("Boton para mover a las posiciones asignadas.");
        buttonMover.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonMoverActionPerformed(evt);
            }
        });
        panelInteriorPosicion.add(buttonMover);

        panelPosicion.add(panelInteriorPosicion, java.awt.BorderLayout.CENTER);

        herramientasFiguras.add(panelPosicion);

        panelHerramientas.add(herramientasFiguras);

        herramientasAudioVideo.setRollover(true);

        jPanel5.setLayout(new java.awt.BorderLayout());

        etiquetaSonido.setText("Sonido y Video");
        jPanel5.add(etiquetaSonido, java.awt.BorderLayout.PAGE_START);

        buttonReproducir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/play24x24.png"))); // NOI18N
        buttonReproducir.setToolTipText("Boton para reproducir");
        buttonReproducir.setFocusable(false);
        buttonReproducir.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        buttonReproducir.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        buttonReproducir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonReproducirActionPerformed(evt);
            }
        });
        jPanel6.add(buttonReproducir);

        listaReproduccion.setToolTipText("Lista de reproduccion de audio.");
        listaReproduccion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                listaReproduccionActionPerformed(evt);
            }
        });
        jPanel6.add(listaReproduccion);

        buttonDetener.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/stop24x24.png"))); // NOI18N
        buttonDetener.setToolTipText("Boton de pausa.");
        buttonDetener.setFocusable(false);
        buttonDetener.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        buttonDetener.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        buttonDetener.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonDetenerActionPerformed(evt);
            }
        });
        jPanel6.add(buttonDetener);

        buttonGrabar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/record24x24.png"))); // NOI18N
        buttonGrabar.setToolTipText("Boton de grabar.");
        buttonGrabar.setFocusable(false);
        buttonGrabar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        buttonGrabar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        buttonGrabar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonGrabarActionPerformed(evt);
            }
        });
        jPanel6.add(buttonGrabar);

        buttonCamara.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/Camara.png"))); // NOI18N
        buttonCamara.setToolTipText("Muestra de la camara.");
        buttonCamara.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonCamaraActionPerformed(evt);
            }
        });
        jPanel6.add(buttonCamara);

        buttonCapturar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/Capturar.png"))); // NOI18N
        buttonCapturar.setToolTipText("Sacar capturas de la camara y el video.");
        buttonCapturar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonCapturarActionPerformed(evt);
            }
        });
        jPanel6.add(buttonCapturar);

        jPanel5.add(jPanel6, java.awt.BorderLayout.CENTER);

        herramientasAudioVideo.add(jPanel5);

        panelHerramientas.add(herramientasAudioVideo);

        getContentPane().add(panelHerramientas, java.awt.BorderLayout.PAGE_START);

        jPanel3.setLayout(new java.awt.BorderLayout());

        listaFiguras.setToolTipText("Selecciona la figura a editar de la lista.");
        listaFiguras.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                listaFigurasValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(listaFiguras);

        jPanel3.add(jScrollPane1, java.awt.BorderLayout.LINE_END);
        jPanel3.add(escritorio, java.awt.BorderLayout.CENTER);

        scroll.setViewportView(jPanel3);

        getContentPane().add(scroll, java.awt.BorderLayout.CENTER);

        menuArchivo.setText("Archivo");
        menuArchivo.setToolTipText("Herramientas basicas de la interfaz.");

        opcionNuevo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/nuevo.png"))); // NOI18N
        opcionNuevo.setText("Nuevo");
        opcionNuevo.setToolTipText("Permite crear un nuevo lienzo para dibujar.");
        opcionNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opcionNuevoActionPerformed(evt);
            }
        });
        menuArchivo.add(opcionNuevo);

        opcionAbrir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/abrir.png"))); // NOI18N
        opcionAbrir.setText("Abrir");
        opcionAbrir.setToolTipText("Permite seleccionar un fichero y abrirlo.");
        opcionAbrir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opcionAbrirActionPerformed(evt);
            }
        });
        menuArchivo.add(opcionAbrir);

        opcionGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/guardar.png"))); // NOI18N
        opcionGuardar.setText("Guardar");
        opcionGuardar.setToolTipText("Permite guardar una imagen o dibujo.");
        opcionGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opcionGuardarActionPerformed(evt);
            }
        });
        menuArchivo.add(opcionGuardar);

        barraNavegacion.add(menuArchivo);

        menuVer.setText("Ver");
        menuVer.setToolTipText("Activar o desactivar barras de herramientas.");

        verHerramientasDibujo.setSelected(true);
        verHerramientasDibujo.setText("Ver herramientas dibujo.");
        verHerramientasDibujo.setToolTipText("Activar o desactivar opciones de dibujo.");
        verHerramientasDibujo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                verHerramientasDibujoActionPerformed(evt);
            }
        });
        menuVer.add(verHerramientasDibujo);

        verHerramientasImagen.setSelected(true);
        verHerramientasImagen.setText("Ver herramientas imagen.");
        verHerramientasImagen.setToolTipText("Activar o desactivar opciones de imagen.");
        verHerramientasImagen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                verHerramientasImagenActionPerformed(evt);
            }
        });
        menuVer.add(verHerramientasImagen);

        verHerramientasSonidoVideo.setSelected(true);
        verHerramientasSonidoVideo.setText("Ver herramientas sonido y video.");
        verHerramientasSonidoVideo.setToolTipText("Activar o desactivar opciones de video y sonido.");
        verHerramientasSonidoVideo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                verHerramientasSonidoVideoActionPerformed(evt);
            }
        });
        menuVer.add(verHerramientasSonidoVideo);

        barraNavegacion.add(menuVer);

        menuAyuda.setText("Ayuda");
        menuAyuda.setToolTipText("Opciones de ayuda.");

        opcionAcercaDe.setText("Acerca de");
        opcionAcercaDe.setToolTipText("Muestra la informacion sobre la aplicación.");
        opcionAcercaDe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opcionAcercaDeActionPerformed(evt);
            }
        });
        menuAyuda.add(opcionAcercaDe);

        barraNavegacion.add(menuAyuda);

        setJMenuBar(barraNavegacion);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void opcionNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opcionNuevoActionPerformed
       VentanaInterna vi = new VentanaInterna(this);
       escritorio.add(vi);
       vi.setVisible(true);
       buttonLinea.doClick();
       vi.getLienzo().addLienzoListener(manejador);
    }//GEN-LAST:event_opcionNuevoActionPerformed

    private void opcionAbrirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opcionAbrirActionPerformed
        JFileChooser dlg = new JFileChooser();
        int resp = dlg.showOpenDialog(this);
        if( resp == JFileChooser.APPROVE_OPTION) {
            try{
                File f = dlg.getSelectedFile();
                if (f.toString().contains(".png")||f.toString().contains("jpg")||f.toString().contains("jpeg")){
                    BufferedImage img = ImageIO.read(f);
                    VentanaInterna vi = new VentanaInterna(this);
                    vi.getLienzo().setImage(img);
                    this.escritorio.add(vi);
                    vi.setTitle(f.getName());
                    vi.setVisible(true);
                } else if(f.toString().contains("wav") || f.toString().contains("au") || f.toString().contains("aif")){
                    listaReproduccion.addItem(f);
                } else if(f.toString().contains("avi") || f.toString().contains("mp4") || f.toString().contains("mpg") || f.toString().contains("mp3")){
                    VentanaInternaVLCPlayer viP = VentanaInternaVLCPlayer.getInstance(f);
                    this.escritorio.add(viP);
                    viP.addMediaPlayerEventListener(new VideoListener());
                    viP.setTitle(f.getName());
                    viP.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(this, "Ese tipo de fichero nosotros no lo trabajamos.", "Inane error", JOptionPane.ERROR_MESSAGE);
                }
            }catch(Exception ex){
                ex.printStackTrace();
                System.err.println("Error al leer la imagen");
                
            }
        }
    }//GEN-LAST:event_opcionAbrirActionPerformed

    private void opcionGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opcionGuardarActionPerformed
        VentanaInterna vi=(VentanaInterna) escritorio.getSelectedFrame();
        if (vi != null) {
            JFileChooser dlg = new JFileChooser();
            int resp = dlg.showSaveDialog(this);
                if (resp == JFileChooser.APPROVE_OPTION) {
                try {
                    BufferedImage img = vi.getLienzo().getImage(true);
                    if (img != null) {
                        File f = dlg.getSelectedFile();
                        ImageIO.write(img, "jpg", f);
                        vi.setTitle(f.getName());
                    }
                }catch (Exception ex) {
                    System.err.println("Error al guardar la imagen");
                }
            }
        }
    }//GEN-LAST:event_opcionGuardarActionPerformed

    private void buttonDetenerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonDetenerActionPerformed
        VentanaInternaVLCPlayer viP = (VentanaInternaVLCPlayer) (escritorio.getSelectedFrame());
        if(viP != null){
            viP.stop();
        } else {
            if (grabando){
                recorder.stop();
                grabando = false; 
            }else player.stop();  
        }     
    }//GEN-LAST:event_buttonDetenerActionPerformed

    private void buttonReproducirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonReproducirActionPerformed
        JInternalFrame viP = escritorio.getSelectedFrame();
        if(viP!=null && viP.isSelected()){
            viP = (VentanaInternaVLCPlayer) escritorio.getSelectedFrame();
            ((VentanaInternaVLCPlayer)viP).play();
        } else {
            File f = (File)listaReproduccion.getSelectedItem();
            if(f!=null){
                player = new SMClipPlayer(f);
                if (player != null) {
                    player.play();
                    ((SMClipPlayer) player).addLineListener(new ManejadorAudio());
                }
            }   
        }
    }//GEN-LAST:event_buttonReproducirActionPerformed

    private void buttonGrabarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonGrabarActionPerformed
        JFileChooser dlg = new JFileChooser();
        AudioFileFormat.Type audio = AudioFileFormat.Type.AU;
        FileFilter filter = new FileNameExtensionFilter(audio.getExtension(),audio.getExtension());
        dlg.addChoosableFileFilter(filter);
        int resp = dlg.showSaveDialog(this);
        if (resp == JFileChooser.APPROVE_OPTION) {
            try {
                File f = dlg.getSelectedFile();
                listaReproduccion.addItem(f);
                recorder = new SMSoundRecorder(f);
                recorder.record();
                ((SMSoundRecorder) recorder).addLineListener(new ManejadorAudio());
                grabando = true; 
            }catch (Exception ex) {
                System.err.println("Error al crear el audio");
            }
        }
    }//GEN-LAST:event_buttonGrabarActionPerformed

    private void sliderBrilloFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_sliderBrilloFocusGained
        VentanaInterna vi = (VentanaInterna)(escritorio.getSelectedFrame());
        if(vi!=null){
            ColorModel cm = vi.getLienzo().getImage(true).getColorModel();
            WritableRaster raster = vi.getLienzo().getImage(true).copyData(null);
            boolean alfaPre = vi.getLienzo().getImage(true).isAlphaPremultiplied();
            imgSource = new BufferedImage(cm,raster,alfaPre,null);
        }
    }//GEN-LAST:event_sliderBrilloFocusGained

    private void sliderBrilloFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_sliderBrilloFocusLost
        imgSource = null;
        sliderBrillo.setValue(0);
    }//GEN-LAST:event_sliderBrilloFocusLost

    private void sliderBrilloStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_sliderBrilloStateChanged
        VentanaInterna vi = (VentanaInterna)(escritorio.getSelectedFrame());
        BufferedImage img = vi.getLienzo().getImage();
           
        if(img!=null && imgSource != null){
            try{
                RescaleOp rop;           
                rop = new RescaleOp(1.0F, sliderBrillo.getValue(), null);
                rop.filter(imgSource, img);
                escritorio.repaint();
            } catch(IllegalArgumentException e){
                System.err.println(e.getLocalizedMessage());
            }
        }
    }//GEN-LAST:event_sliderBrilloStateChanged

    private void comboBoxFiltrosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboBoxFiltrosActionPerformed
                VentanaInterna vi = (VentanaInterna)(escritorio.getSelectedFrame());
        String filtro = (String) comboBoxFiltros.getSelectedItem();
            if (vi != null){
                imgSource = vi.getLienzo().getImage();
                try{
                    Kernel k = null; 
                    switch(filtro){
                        case "Media":
                            k = KernelProducer.createKernel(KernelProducer.TYPE_MEDIA_3x3);
                            break;
                        case "Binomial":
                            k = KernelProducer.createKernel(KernelProducer.TYPE_BINOMIAL_3x3);
                            break;
                        case "Enfoque":
                            k = KernelProducer.createKernel(KernelProducer.TYPE_ENFOQUE_3x3);
                            break; 
                        case "Relieve":
                            k = KernelProducer.createKernel(KernelProducer.TYPE_RELIEVE_3x3);
                            break;
                        case "Fronteras":
                            k = KernelProducer.createKernel(KernelProducer.TYPE_LAPLACIANA_3x3);
                            break;
                    }
                    if (k!=null){
                        ConvolveOp cop = new ConvolveOp(k, ConvolveOp.EDGE_NO_OP, null);
                        BufferedImage img = cop.filter(imgSource, null);
                        vi.getLienzo().setImage(img);
                        vi.repaint();
                    }
                } catch(IllegalArgumentException e){
                    System.err.println("Error");
                }
            }
    }//GEN-LAST:event_comboBoxFiltrosActionPerformed

    private void buttonContrasteNormalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonContrasteNormalActionPerformed
        VentanaInterna vi = (VentanaInterna) (escritorio.getSelectedFrame()); 
        if (vi != null) {
            BufferedImage imgSource = vi.getLienzo().getImage();
            try{
                LookupTable lt;
                lt=LookupTableProducer.createLookupTable(LookupTableProducer.TYPE_SFUNCION);
                LookupOp lop = new LookupOp(lt, null);
                lop.filter(imgSource, imgSource);
                vi.getLienzo().setImage(imgSource);
                vi.repaint();
            }catch(IllegalArgumentException e){
                System.err.println(e.getLocalizedMessage());
            }
        }
    }//GEN-LAST:event_buttonContrasteNormalActionPerformed

    private void buttonContrasteIluminadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonContrasteIluminadoActionPerformed
        VentanaInterna vi = (VentanaInterna) (escritorio.getSelectedFrame()); 
        if (vi != null) {
            BufferedImage imgSource = vi.getLienzo().getImage();
            try{
                LookupTable lt;
                lt=LookupTableProducer.createLookupTable(LookupTableProducer.TYPE_ROOT);
                LookupOp lop = new LookupOp(lt, null);
                lop.filter(imgSource, imgSource);
                vi.getLienzo().setImage(imgSource);
                vi.repaint();
            }catch(IllegalArgumentException e){
                System.err.println(e.getLocalizedMessage());
            }
        }
    }//GEN-LAST:event_buttonContrasteIluminadoActionPerformed

    private void buttonContrasteOscurecidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonContrasteOscurecidoActionPerformed
        VentanaInterna vi = (VentanaInterna) (escritorio.getSelectedFrame()); 
        if (vi != null) {
            BufferedImage imgSource = vi.getLienzo().getImage();
            try{
                LookupTable lt;
                lt=LookupTableProducer.createLookupTable(LookupTableProducer.TYPE_POWER);
                LookupOp lop = new LookupOp(lt, null);
                lop.filter(imgSource, imgSource);
                vi.getLienzo().setImage(imgSource);
                vi.repaint();
            }catch(IllegalArgumentException e){
                System.err.println(e.getLocalizedMessage());
            }   
        }
    }//GEN-LAST:event_buttonContrasteOscurecidoActionPerformed

    private void sliderRotacionStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_sliderRotacionStateChanged
        VentanaInterna vi = (VentanaInterna) (escritorio.getSelectedFrame()); 
        if (vi != null) {
            if(imgSource == null){
                imgSource = vi.getLienzo().getImage();
            } 
            try{
                BufferedImage Dest = new BufferedImage((int) Math.sqrt(imgSource.getHeight()*imgSource.getHeight()+imgSource.getWidth()*imgSource.getWidth()), (int) Math.sqrt(imgSource.getHeight()*imgSource.getHeight()+imgSource.getWidth()*imgSource.getWidth()), BufferedImage.TYPE_INT_ARGB);
                double r = Math.toRadians(sliderRotacion.getValue());
                Point p = new Point(imgSource.getWidth()/2, imgSource.getHeight()/2);
                AffineTransform at = AffineTransform.getRotateInstance(r,p.x,p.y);
                AffineTransformOp atop = new AffineTransformOp(at,AffineTransformOp.TYPE_BILINEAR);
                atop.filter(imgSource, Dest);
                vi.getLienzo().setImage(Dest);
                vi.repaint();
            }catch(IllegalArgumentException e){
                System.err.println(e.getLocalizedMessage());
            }   
        }
    }//GEN-LAST:event_sliderRotacionStateChanged

    private void sliderRotacionFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_sliderRotacionFocusGained
        VentanaInterna vi = (VentanaInterna)(escritorio.getSelectedFrame());
        if(vi!=null){
            ColorModel cm = vi.getLienzo().getImage(true).getColorModel();
            WritableRaster raster = vi.getLienzo().getImage(true).copyData(null);
            boolean alfaPre = vi.getLienzo().getImage(true).isAlphaPremultiplied();
            imgSource = new BufferedImage(cm,raster,alfaPre,null);
        }
    }//GEN-LAST:event_sliderRotacionFocusGained

    private void sliderRotacionFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_sliderRotacionFocusLost
        imgSource=null;
        this.sliderRotacion.setValue(0);
    }//GEN-LAST:event_sliderRotacionFocusLost

    private void buttonAumentarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonAumentarActionPerformed
        VentanaInterna vi = (VentanaInterna) (escritorio.getSelectedFrame()); 
        if (vi != null) {
            BufferedImage imgSource = vi.getLienzo().getImage();
            try{
                BufferedImage dest = escalado(imgSource, 1.25);
                vi.getLienzo().setImage(dest);
                vi.repaint();
            }catch(IllegalArgumentException e){
                System.err.println(e.getLocalizedMessage());
            }   
        }
    }//GEN-LAST:event_buttonAumentarActionPerformed

    private void buttonReducirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonReducirActionPerformed
        VentanaInterna vi = (VentanaInterna) (escritorio.getSelectedFrame()); 
        if (vi != null) {
            BufferedImage imgSource = vi.getLienzo().getImage();
            try{
                BufferedImage dest = escalado(imgSource, 0.75);
                vi.getLienzo().setImage(dest);
                vi.repaint();
            }catch(IllegalArgumentException e){
                System.err.println(e.getLocalizedMessage());
            }   
        }
    }//GEN-LAST:event_buttonReducirActionPerformed

    private void buttonBandasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonBandasActionPerformed
        bandas();
    }//GEN-LAST:event_buttonBandasActionPerformed

    private void comboBoxEspacioColorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboBoxEspacioColorActionPerformed
        VentanaInterna vi = (VentanaInterna)(escritorio.getSelectedFrame());
        String espacio = (String) comboBoxEspacioColor.getSelectedItem();
            if (vi != null){
                BufferedImage src = vi.getLienzo().getImage();
                if(src != null){
                    ColorSpace cs;
                    ColorConvertOp cop;
                    BufferedImage imgOut = null; 
                    try{
                        switch(espacio){
                            case "RGB":
                                if (!src.getColorModel().getColorSpace().isCS_sRGB()) {
                                    cs = ColorSpace.getInstance(ColorSpace.CS_sRGB);
                                    cop = new ColorConvertOp(cs, null);
                                    imgOut = cop.filter(src, null);
                                }
                                break;
                            case "YCC":
                                if (src.getColorModel().getColorSpace().getType() != ColorSpace.TYPE_YCbCr) {
                                    cs = ColorSpace.getInstance(ColorSpace.CS_PYCC);
                                    cop = new ColorConvertOp(cs, null);
                                    imgOut = cop.filter(src, null);
                                }
                                break;
                            case "GREY":
                                if (src.getColorModel().getColorSpace().getType() != ColorSpace.CS_GRAY) {
                                    cs = ColorSpace.getInstance(ColorSpace.CS_GRAY);
                                    cop = new ColorConvertOp(cs, null);
                                    imgOut = cop.filter(src, null);
                                }
                                break; 
                        }
                        
                        if(imgOut != null){
                            VentanaInterna vi_interna = new VentanaInterna(this);
                            vi_interna.getLienzo().setImage(imgOut);
                            this.escritorio.add(vi_interna);
                            vi_interna.setTitle(vi.getTitle() + '[' + espacio + ']');
                            vi_interna.setVisible(true);
                        }
                    }catch(IllegalArgumentException e){
                        System.err.println(e.getLocalizedMessage());
                    } 
                }
            }
    }//GEN-LAST:event_comboBoxEspacioColorActionPerformed

    private void buttonNegativoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonNegativoActionPerformed
        VentanaInterna vi = (VentanaInterna) (escritorio.getSelectedFrame()); 
        if (vi != null) {
            BufferedImage imgSource = vi.getLienzo().getImage();
            try{
                LookupTable lt;
                lt=LookupTableProducer.createLookupTable(LookupTableProducer.TYPE_NEGATIVE);
                LookupOp lop = new LookupOp(lt, null);
                lop.filter(imgSource, imgSource);
                vi.getLienzo().setImage(imgSource);
                vi.repaint();
            }catch(IllegalArgumentException e){
                System.err.println(e.getLocalizedMessage());
            }   
        }
    }//GEN-LAST:event_buttonNegativoActionPerformed

    private void buttonTintadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonTintadoActionPerformed
       VentanaInterna vi = (VentanaInterna) (escritorio.getSelectedFrame()); 
        if (vi != null) {
            BufferedImage imgSource = vi.getLienzo().getImage();
            try{
                TintOp tintado = new TintOp((Color) coloresRellenoSelector.getSelectedItem(),0.5f);
                tintado.filter(imgSource, imgSource);
                vi.getLienzo().setImage(imgSource);
                vi.repaint();             
            }catch(IllegalArgumentException e){
                System.err.println(e.getLocalizedMessage());
            }
        }
    }//GEN-LAST:event_buttonTintadoActionPerformed

    private void buttonEcualizacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonEcualizacionActionPerformed
        VentanaInterna vi = (VentanaInterna) (escritorio.getSelectedFrame()); 
        if (vi != null) {
            imgSource = vi.getLienzo().getImage();
            try{
                EqualizationOp ecualizacion = new EqualizationOp();
                ecualizacion.filter(imgSource, imgSource);
                vi.getLienzo().setImage(imgSource);
                vi.repaint();             
            }catch(IllegalArgumentException e){
                System.err.println(e.getLocalizedMessage());
            }
        }
    }//GEN-LAST:event_buttonEcualizacionActionPerformed

    private void buttonSepiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonSepiaActionPerformed
        VentanaInterna vi = (VentanaInterna) (escritorio.getSelectedFrame()); 
        if (vi != null) {
            imgSource = vi.getLienzo().getImage();
            try{
                SepiaOp sepia = new SepiaOp();
                sepia.filter(imgSource, imgSource);
                vi.getLienzo().setImage(imgSource);
                vi.repaint();             
            }catch(IllegalArgumentException e){
                System.err.println(e.getLocalizedMessage());
            }
        }
    }//GEN-LAST:event_buttonSepiaActionPerformed

    private void buttonLineaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonLineaActionPerformed
        VentanaInterna vi = (VentanaInterna) (escritorio.getSelectedFrame());
        if(vi!=null){
            vi.getLienzo().setEditar(false);
            actualizarPropiedadesLienzo();
            vi.getLienzo().setForma(Herramientas.linea);
            etiqueta.setText("Linea.");
        }
    }//GEN-LAST:event_buttonLineaActionPerformed

    private void buttonElipseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonElipseActionPerformed
        VentanaInterna vi = (VentanaInterna) (escritorio.getSelectedFrame());
        if(vi!=null){
            vi.getLienzo().setEditar(false);
            actualizarPropiedadesLienzo();
            vi.getLienzo().setForma(Herramientas.elipse);
            etiqueta.setText("Elipse.");
        }  
    }//GEN-LAST:event_buttonElipseActionPerformed

    private void buttonRectanguloActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonRectanguloActionPerformed
        VentanaInterna vi = (VentanaInterna) (escritorio.getSelectedFrame());
        if(vi!=null){
            vi.getLienzo().setEditar(false);
            actualizarPropiedadesLienzo();
            vi.getLienzo().setForma(Herramientas.rectangulo);
            etiqueta.setText("Rectangulo.");
        }
    }//GEN-LAST:event_buttonRectanguloActionPerformed

    private void coloresRellenoSelectorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_coloresRellenoSelectorActionPerformed
        VentanaInterna vi = (VentanaInterna) (escritorio.getSelectedFrame());
        vi.getLienzo().setColorRelleno((Color) coloresRellenoSelector.getSelectedItem());
    }//GEN-LAST:event_coloresRellenoSelectorActionPerformed

    private void coloresBordeSelectorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_coloresBordeSelectorActionPerformed
        VentanaInterna vi = (VentanaInterna) (escritorio.getSelectedFrame());
        vi.getLienzo().setColorBorde((Color) coloresBordeSelector.getSelectedItem());
    }//GEN-LAST:event_coloresBordeSelectorActionPerformed

    private void comboBoxRellenoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboBoxRellenoActionPerformed
        VentanaInterna vi = (VentanaInterna) (escritorio.getSelectedFrame());
        String tipo = comboBoxRelleno.getSelectedItem().toString();
        
        switch(tipo) {
            case "Sin Relleno":
                vi.getLienzo().setRelleno(false);
                break;
            case "Liso":
                vi.getLienzo().setRelleno(true);
                vi.getLienzo().setColorRelleno((Color) coloresRellenoSelector.getSelectedItem());
                break;      
        }
    }//GEN-LAST:event_comboBoxRellenoActionPerformed

    private void spinnerGrosorStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_spinnerGrosorStateChanged
        VentanaInterna vi = (VentanaInterna) (escritorio.getSelectedFrame());
        if (!vi.getLienzo().getEditar()){
            vi.getLienzo().setGrosor((int)spinnerGrosor.getValue());
        }
        
    }//GEN-LAST:event_spinnerGrosorStateChanged

    private void buttonPropiaFuncionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonPropiaFuncionActionPerformed
        VentanaInterna vi = (VentanaInterna) (escritorio.getSelectedFrame());
        if (vi != null) {
            BufferedImage imgSource = vi.getLienzo().getImage();
            try{
                LookupTable lt;
                lt = funcion(180);
                LookupOp lop = new LookupOp(lt,null);
                lop.filter(imgSource, imgSource);
                vi.getLienzo().setImage(imgSource);
                vi.repaint();
            }catch(IllegalArgumentException e){
                System.err.println(e.getLocalizedMessage());
            }
        }
    }//GEN-LAST:event_buttonPropiaFuncionActionPerformed

    private void buttonPropiaPixelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonPropiaPixelActionPerformed
        VentanaInterna vi = (VentanaInterna) (escritorio.getSelectedFrame()); 
        if (vi != null) {
            imgSource = vi.getLienzo().getImage();
            try{
                PixelAPixelOp pixelAPixel = new PixelAPixelOp();
                pixelAPixel.filter(imgSource, imgSource);
                vi.getLienzo().setImage(imgSource);
                vi.repaint();             
            }catch(IllegalArgumentException e){
                System.err.println(e.getLocalizedMessage());
            }
        }
    }//GEN-LAST:event_buttonPropiaPixelActionPerformed

    private void listaFigurasValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_listaFigurasValueChanged
        VentanaInterna vi = (VentanaInterna) (escritorio.getSelectedFrame());
        if(vi!=null){
            vi.getLienzo().setEditar(true);
            vi.getLienzo().setFiguraEditar(listaFiguras.getSelectedIndex());
            actualizarPropiedades();
        }
    }//GEN-LAST:event_listaFigurasValueChanged

    private void comboBoxTipoBordeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboBoxTipoBordeActionPerformed
        VentanaInterna vi = (VentanaInterna) (escritorio.getSelectedFrame());
        String tipo = comboBoxTipoBorde.getSelectedItem().toString();
        if (!vi.getLienzo().getEditar()){
            switch(tipo) {
            case "Continua":
                vi.getLienzo().setDiscontinuo(false);
                break;
            case "Discontinua":
                vi.getLienzo().setDiscontinuo(true);
                break;     
            }
        }
    }//GEN-LAST:event_comboBoxTipoBordeActionPerformed

    private void buttonAlisarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonAlisarActionPerformed
        VentanaInterna vi = (VentanaInterna) (escritorio.getSelectedFrame());
        if (buttonAlisar.isSelected()){
            vi.getLienzo().setAlisar(true);
        } else {
            vi.getLienzo().setAlisar(false);
        }
        
    }//GEN-LAST:event_buttonAlisarActionPerformed

    private void buttonMoverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonMoverActionPerformed
        VentanaInterna vi = (VentanaInterna) (escritorio.getSelectedFrame());
        if(vi.getLienzo().getEditar()){
            vi.getLienzo().setLocation((int)posicionX.getValue(), (int)posicionY.getValue());
        }
    }//GEN-LAST:event_buttonMoverActionPerformed

    private void buttonDuplicarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonDuplicarActionPerformed
        VentanaInterna vi = (VentanaInterna) (escritorio.getSelectedFrame());
        if (vi != null){
                BufferedImage src = vi.getLienzo().getImage();
                try{
                    if(src != null){
                            VentanaInterna vi_interna = new VentanaInterna(this);
                            vi_interna.getLienzo().setImage(src);
                            this.escritorio.add(vi_interna);
                            vi_interna.setTitle(vi.getTitle() + " Nueva");
                            vi_interna.setVisible(true);
                        }
                    }catch(IllegalArgumentException e){
                        System.err.println(e.getLocalizedMessage());
                    }
        }
    }//GEN-LAST:event_buttonDuplicarActionPerformed

    private void buttonPropiaComponenteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonPropiaComponenteActionPerformed
        VentanaInterna vi = (VentanaInterna) (escritorio.getSelectedFrame()); 
        if (vi != null) {
            imgSource = vi.getLienzo().getImage();
            try{
                ComponenteAComponenteOp cac = new ComponenteAComponenteOp();
                cac.filter(imgSource, imgSource);
                vi.getLienzo().setImage(imgSource);
                vi.repaint();             
            }catch(IllegalArgumentException e){
                System.err.println(e.getLocalizedMessage());
            }
        }
    }//GEN-LAST:event_buttonPropiaComponenteActionPerformed

    private void buttonCamaraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonCamaraActionPerformed
        VentanaInternaCamara vi = VentanaInternaCamara.getInstance();
        escritorio.add(vi);
        vi.setVisible(true);
    }//GEN-LAST:event_buttonCamaraActionPerformed

    private void buttonCapturarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonCapturarActionPerformed
        JInternalFrame vi = escritorio.getSelectedFrame();
        
        VentanaInterna vi2 = new VentanaInterna(this);
        
        if(vi instanceof VentanaInternaVLCPlayer){
            vi = (VentanaInternaVLCPlayer) escritorio.getSelectedFrame();
           vi2.getLienzo().setImage(((VentanaInternaVLCPlayer)vi).getImage()); 
        } else if(vi instanceof VentanaInternaCamara){
            vi = (VentanaInternaCamara) escritorio.getSelectedFrame();
           vi2.getLienzo().setImage(((VentanaInternaCamara)vi).getImage()); 
        }
        
        this.escritorio.add(vi2);
        vi2.setTitle("Captura");
        vi2.setVisible(true);
    }//GEN-LAST:event_buttonCapturarActionPerformed

    private void buttonRestaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonRestaActionPerformed
        VentanaInterna vi = (VentanaInterna) escritorio.selectFrame(true);
        if( vi != null){
            VentanaInterna viNext = (VentanaInterna) escritorio.selectFrame(false);
            if(viNext != null){
                BufferedImage imgRight = vi.getLienzo().getImage();
                BufferedImage imgLeft = viNext.getLienzo().getImage();
                if((imgRight !=null)&&(imgLeft != null)){
                    try{
                        SubtractionOp op = new SubtractionOp(imgLeft);
                        BufferedImage imgdest = op.filter(imgRight, null);
                        vi = new VentanaInterna(this);
                        vi.getLienzo().setImage(imgdest);
                        this.escritorio.add(vi);
                        vi.setVisible(true);
                    } catch (IllegalArgumentException e){
                        System.out.println("Error: "+e.getLocalizedMessage());
                    }
                }
            }
        }
    }//GEN-LAST:event_buttonRestaActionPerformed

    private void buttonSumaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonSumaActionPerformed
        VentanaInterna vi = (VentanaInterna) escritorio.selectFrame(true);
        if( vi != null){
            VentanaInterna viNext = (VentanaInterna) escritorio.selectFrame(false);
            if(viNext != null){
                BufferedImage imgRight = vi.getLienzo().getImage();
                BufferedImage imgLeft = viNext.getLienzo().getImage();
                if((imgRight !=null)&&(imgLeft != null)){
                    try{
                        BlendOp op = new BlendOp(imgLeft);
                        BufferedImage imgdest = op.filter(imgRight, null);
                        vi = new VentanaInterna(this);
                        vi.getLienzo().setImage(imgdest);
                        this.escritorio.add(vi);
                        vi.setVisible(true);
                    } catch (IllegalArgumentException e){
                        System.out.println("Error: "+e.getLocalizedMessage());
                    }
                }
            }
        }
    }//GEN-LAST:event_buttonSumaActionPerformed

    private void sliderUmbralizacionFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_sliderUmbralizacionFocusGained
        VentanaInterna vi = (VentanaInterna)(escritorio.getSelectedFrame());
        if(vi!=null){
            ColorModel cm = vi.getLienzo().getImage(true).getColorModel();
            WritableRaster raster = vi.getLienzo().getImage(true).copyData(null);
            boolean alfaPre = vi.getLienzo().getImage(true).isAlphaPremultiplied();
            imgSource = new BufferedImage(cm,raster,alfaPre,null);
        }
    }//GEN-LAST:event_sliderUmbralizacionFocusGained

    private void sliderUmbralizacionStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_sliderUmbralizacionStateChanged
        VentanaInterna vi = (VentanaInterna) (escritorio.getSelectedFrame()); 
        if (vi != null) {
            try{
                UmbralizacionOp umbralizacion = new UmbralizacionOp(sliderUmbralizacion.getValue());
                BufferedImage imgDest = umbralizacion.filter(imgSource, vi.getLienzo().getImage());
                vi.getLienzo().setImage(imgDest);
                vi.repaint();             
            }catch(IllegalArgumentException e){
                System.err.println(e.getLocalizedMessage());
            }
        }
    }//GEN-LAST:event_sliderUmbralizacionStateChanged

    private void sliderUmbralizacionFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_sliderUmbralizacionFocusLost
        imgSource = null;
        sliderUmbralizacion.setValue(128);
    }//GEN-LAST:event_sliderUmbralizacionFocusLost

    private void sliderTransparenciaStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_sliderTransparenciaStateChanged
        VentanaInterna vi = (VentanaInterna) (escritorio.getSelectedFrame());
        vi.getLienzo().setNivel((sliderTransparencia.getValue()));
    }//GEN-LAST:event_sliderTransparenciaStateChanged

    private void opcionAcercaDeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opcionAcercaDeActionPerformed
        JOptionPane.showMessageDialog(this, "Proyecto Sistemas Multimedia\n" + "Version 1.0\n" + "Por Paula Ruiz García");
    }//GEN-LAST:event_opcionAcercaDeActionPerformed

    private void verHerramientasDibujoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_verHerramientasDibujoActionPerformed
        if(verHerramientasDibujo.isSelected()){
            this.herramientasFiguras.setVisible(true);
        }
        else{
            this.herramientasFiguras.setVisible(false);
        }
    }//GEN-LAST:event_verHerramientasDibujoActionPerformed

    private void verHerramientasImagenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_verHerramientasImagenActionPerformed
        if(verHerramientasImagen.isSelected()){
            this.herramientasImagenes.setVisible(true);
        }
        else{
            this.herramientasImagenes.setVisible(false);
        }
    }//GEN-LAST:event_verHerramientasImagenActionPerformed

    private void verHerramientasSonidoVideoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_verHerramientasSonidoVideoActionPerformed
        if(verHerramientasSonidoVideo.isSelected()){
            this.herramientasAudioVideo.setVisible(true);
        }
        else{
            this.herramientasAudioVideo.setVisible(false);
        }
    }//GEN-LAST:event_verHerramientasSonidoVideoActionPerformed

    private void listaReproduccionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_listaReproduccionActionPerformed
        JInternalFrame ventana = escritorio.getSelectedFrame();
        if (ventana instanceof VentanaInternaVLCPlayer){
            try {
                ventana.setSelected(false);
            } catch (PropertyVetoException ex) {
                Logger.getLogger(VentanaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
            }
            buttonReproducir.setEnabled(true);
        }
    }//GEN-LAST:event_listaReproduccionActionPerformed
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuBar barraNavegacion;
    private javax.swing.JToggleButton buttonAlisar;
    private javax.swing.JButton buttonAumentar;
    private javax.swing.JButton buttonBandas;
    private javax.swing.JButton buttonCamara;
    private javax.swing.JButton buttonCapturar;
    private javax.swing.JButton buttonContrasteIluminado;
    private javax.swing.JButton buttonContrasteNormal;
    private javax.swing.JButton buttonContrasteOscurecido;
    private javax.swing.JButton buttonDetener;
    private javax.swing.JButton buttonDuplicar;
    private javax.swing.JButton buttonEcualizacion;
    private javax.swing.JToggleButton buttonElipse;
    private javax.swing.JButton buttonGrabar;
    private javax.swing.JToggleButton buttonLinea;
    private javax.swing.JButton buttonMover;
    private javax.swing.JButton buttonNegativo;
    private javax.swing.JButton buttonPropiaComponente;
    private javax.swing.JButton buttonPropiaFuncion;
    private javax.swing.JButton buttonPropiaPixel;
    private javax.swing.JToggleButton buttonRectangulo;
    private javax.swing.JButton buttonReducir;
    private javax.swing.JButton buttonReproducir;
    private javax.swing.JButton buttonResta;
    private javax.swing.JButton buttonSepia;
    private javax.swing.JButton buttonSuma;
    private javax.swing.JButton buttonTintado;
    private javax.swing.JComboBox<ColorRender> coloresBordeSelector;
    private javax.swing.JComboBox<ColorRender> coloresRellenoSelector;
    private javax.swing.JComboBox<String> comboBoxEspacioColor;
    private javax.swing.JComboBox<String> comboBoxFiltros;
    private javax.swing.JComboBox<String> comboBoxRelleno;
    private javax.swing.JComboBox<String> comboBoxTipoBorde;
    private javax.swing.JDesktopPane escritorio;
    private javax.swing.JLabel etiqueta;
    private javax.swing.JLabel etiquetaAlisado;
    private javax.swing.JLabel etiquetaBinarios;
    private javax.swing.JLabel etiquetaBrillo;
    private javax.swing.JLabel etiquetaColor;
    private javax.swing.JLabel etiquetaColorBorde;
    private javax.swing.JLabel etiquetaColorRelleno;
    private javax.swing.JLabel etiquetaContraste;
    private javax.swing.JLabel etiquetaDuplicar;
    private javax.swing.JLabel etiquetaEscala;
    private javax.swing.JLabel etiquetaFiltros;
    private javax.swing.JLabel etiquetaHerramientas;
    private javax.swing.JLabel etiquetaPosicion;
    private javax.swing.JLabel etiquetaPropias;
    private javax.swing.JLabel etiquetaRotacion;
    private javax.swing.JLabel etiquetaSonido;
    private javax.swing.JLabel etiquetaTransparencia;
    private javax.swing.JLabel etiquetaUmbralizacion;
    private javax.swing.ButtonGroup formas;
    private javax.swing.JToolBar herramientasAudioVideo;
    private javax.swing.JToolBar herramientasFiguras;
    private javax.swing.JToolBar herramientasImagenes;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JToolBar.Separator jSeparator2;
    private javax.swing.JToolBar.Separator jSeparator3;
    private javax.swing.JToolBar.Separator jSeparator4;
    private javax.swing.JList<Figura> listaFiguras;
    private javax.swing.JComboBox<File> listaReproduccion;
    private javax.swing.JMenu menuArchivo;
    private javax.swing.JMenu menuAyuda;
    private javax.swing.JMenu menuVer;
    private javax.swing.JMenuItem opcionAbrir;
    private javax.swing.JMenuItem opcionAcercaDe;
    private javax.swing.JMenuItem opcionGuardar;
    private javax.swing.JMenuItem opcionNuevo;
    private javax.swing.JPanel panelAlisado;
    private javax.swing.JPanel panelBandas;
    private javax.swing.JPanel panelBinarias;
    private javax.swing.JPanel panelBotonNegativo;
    private javax.swing.JPanel panelBotonesContraste;
    private javax.swing.JPanel panelBotonesEscala;
    private javax.swing.JPanel panelBotonesRotacion;
    private javax.swing.JPanel panelBrillo;
    private javax.swing.JPanel panelColorBorde;
    private javax.swing.JPanel panelColorRelleno;
    private javax.swing.JPanel panelComboBoxFiltro;
    private javax.swing.JPanel panelContraste;
    private javax.swing.JPanel panelDuplicar;
    private javax.swing.JPanel panelEscala;
    private javax.swing.JPanel panelFiltro;
    private javax.swing.JPanel panelFormas;
    private javax.swing.JPanel panelHerramientas;
    private javax.swing.JPanel panelImagenes;
    private javax.swing.JPanel panelInteriorBorde;
    private javax.swing.JPanel panelInteriorFormas;
    private javax.swing.JPanel panelInteriorPosicion;
    private javax.swing.JPanel panelInteriorPropias;
    private javax.swing.JPanel panelInteriorRelleno;
    private javax.swing.JPanel panelInteriorTransparencia;
    private javax.swing.JPanel panelOpcionesBandas;
    private javax.swing.JPanel panelPosicion;
    private javax.swing.JPanel panelPropias;
    private javax.swing.JPanel panelPropiedades;
    private javax.swing.JPanel panelRotacion;
    private javax.swing.JPanel panelSliderBrillo;
    private javax.swing.JPanel panelSliderUmbralizacion;
    private javax.swing.JPanel panelTransparencia;
    private javax.swing.JPanel panelUmbralizacion;
    private javax.swing.JSpinner posicionX;
    private javax.swing.JSpinner posicionY;
    private javax.swing.JScrollPane scroll;
    private javax.swing.JSlider sliderBrillo;
    private javax.swing.JSlider sliderRotacion;
    private javax.swing.JSlider sliderTransparencia;
    private javax.swing.JSlider sliderUmbralizacion;
    private javax.swing.JSpinner spinnerGrosor;
    private javax.swing.JCheckBoxMenuItem verHerramientasDibujo;
    private javax.swing.JCheckBoxMenuItem verHerramientasImagen;
    private javax.swing.JCheckBoxMenuItem verHerramientasSonidoVideo;
    // End of variables declaration//GEN-END:variables

public void setTextoEtiqueta(String texto){
    etiqueta.setText(texto);
}

public void actualizarLista(){
    VentanaInterna vi = (VentanaInterna) (escritorio.getSelectedFrame());
    Lista listaAux = new Lista();
    List<Figura> listalienzo= new ArrayList();
    listalienzo = vi.getLienzo().getVectorFiguras();
    if (vi!=null){
        for(Figura figura: listalienzo){
            listaAux.add(figura);
        }
        listaFiguras.setModel(listaAux);        
    }
}
    
public BufferedImage escalado(BufferedImage imgSource, double factorEscala){
    AffineTransform at = AffineTransform.getScaleInstance(factorEscala, factorEscala);
    AffineTransformOp atop;
    atop = new AffineTransformOp(at,AffineTransformOp.TYPE_BILINEAR);
    return atop.filter(imgSource, null);
}

public LookupTable funcion(int a){
        double K = 255.0/Math.sin(Math.PI/2);
        byte[] lt = new byte[256]; 
        for (int i = 0; i<256; i++){
            lt[i] = (byte)Math.abs(K*Math.tan(Math.sqrt(Math.sin(a*i))));
        }
        ByteLookupTable slt = new ByteLookupTable(0,lt);
        return slt; 
    }

public void bandas(){
    VentanaInterna vi = (VentanaInterna) (escritorio.getSelectedFrame()); 
    
    if( vi != null ){
        BufferedImage src = vi.getLienzo().getImage();
        if (src != null){
            int nroBandas = src.getColorModel().getNumColorComponents();
            
            for (int iBanda = 0; iBanda < nroBandas; iBanda++){
                //Creamos el modelo de color de la nueva imagen basado en un espcio de color GRAY
                ColorSpace cs = ColorSpace.getInstance(ColorSpace.CS_GRAY);
                ComponentColorModel cm = new ComponentColorModel(cs, false, false, Transparency.OPAQUE, DataBuffer.TYPE_BYTE);
                //Creamos el nuevo raster a partir del raster de la imagen original 
                int bandList[] = {iBanda};
                WritableRaster bandRaster = (WritableRaster) src.getRaster().createWritableChild(0, 0, src.getWidth(), src.getHeight(), 0, 0, bandList);
                //Creamos una nueva imagen que contiene como raster el correspondiente a la banda
                BufferedImage imgBanda = new BufferedImage(cm, bandRaster, false, null);
                
                //Creamos la ventana interna nueva con la imagen de fondo de la banda
                VentanaInterna vi_interna = new VentanaInterna(this);

                vi_interna.getLienzo().setImage(imgBanda);
                this.escritorio.add(vi_interna);
                vi_interna.setTitle(vi.getTitle() + '[' + iBanda + ']');
                vi_interna.setVisible(true);
            }
        }   
    }
}
}
