/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mipaint;

import java.awt.Color;
import java.awt.Point;
import java.io.File;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;


/**
 *
 * @author Shiri
 */
public class VentanaPrincipal extends javax.swing.JFrame {
    /**
     * Creates new form ventanaPrincipal
     */
    public VentanaPrincipal() {
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

        herramientas = new javax.swing.ButtonGroup();
        colores = new javax.swing.ButtonGroup();
        panelColores = new javax.swing.JPanel();
        etiqueta = new javax.swing.JLabel();
        zonaColores = new javax.swing.JPanel();
        primarios = new javax.swing.JPanel();
        negro = new javax.swing.JToggleButton();
        rojo = new javax.swing.JToggleButton();
        azul = new javax.swing.JToggleButton();
        secundarios = new javax.swing.JPanel();
        blanco = new javax.swing.JToggleButton();
        amarillo = new javax.swing.JToggleButton();
        green = new javax.swing.JToggleButton();
        relleno = new javax.swing.JCheckBox();
        barraHerramientas = new javax.swing.JToolBar();
        lapiz = new javax.swing.JToggleButton();
        linea = new javax.swing.JToggleButton();
        rectangulo = new javax.swing.JToggleButton();
        elipse = new javax.swing.JToggleButton();
        lienzo = new mipaint.Lienzo();
        menu = new javax.swing.JMenuBar();
        archivo = new javax.swing.JMenu();
        nuevo = new javax.swing.JMenuItem();
        abrir = new javax.swing.JMenuItem();
        guardar = new javax.swing.JMenuItem();
        edicion = new javax.swing.JMenu();
        barraEstado = new javax.swing.JCheckBoxMenuItem();

        FormListener formListener = new FormListener();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        panelColores.setLayout(new java.awt.BorderLayout());

        etiqueta.setText("Elige una herramienta");
        panelColores.add(etiqueta, java.awt.BorderLayout.PAGE_END);

        zonaColores.setLayout(new java.awt.BorderLayout());

        negro.setBackground(java.awt.Color.black);
        colores.add(negro);
        negro.setPreferredSize(new java.awt.Dimension(20, 20));
        negro.addActionListener(formListener);
        primarios.add(negro);

        rojo.setBackground(java.awt.Color.red);
        colores.add(rojo);
        rojo.setSelected(true);
        rojo.setPreferredSize(new java.awt.Dimension(20, 20));
        rojo.addActionListener(formListener);
        primarios.add(rojo);

        azul.setBackground(java.awt.Color.blue);
        colores.add(azul);
        azul.setToolTipText("");
        azul.setPreferredSize(new java.awt.Dimension(20, 20));
        azul.addActionListener(formListener);
        primarios.add(azul);

        zonaColores.add(primarios, java.awt.BorderLayout.PAGE_START);

        blanco.setBackground(java.awt.Color.white);
        colores.add(blanco);
        blanco.setPreferredSize(new java.awt.Dimension(20, 20));
        blanco.addActionListener(formListener);
        secundarios.add(blanco);

        amarillo.setBackground(java.awt.Color.yellow);
        colores.add(amarillo);
        amarillo.setPreferredSize(new java.awt.Dimension(20, 20));
        amarillo.addActionListener(formListener);
        secundarios.add(amarillo);

        green.setBackground(java.awt.Color.green);
        colores.add(green);
        green.setPreferredSize(new java.awt.Dimension(20, 20));
        green.addActionListener(formListener);
        secundarios.add(green);

        zonaColores.add(secundarios, java.awt.BorderLayout.PAGE_END);

        panelColores.add(zonaColores, java.awt.BorderLayout.LINE_START);

        relleno.setText("Relleno");
        relleno.addActionListener(formListener);
        panelColores.add(relleno, java.awt.BorderLayout.LINE_END);

        getContentPane().add(panelColores, java.awt.BorderLayout.PAGE_END);

        barraHerramientas.setRollover(true);

        herramientas.add(lapiz);
        lapiz.setIcon(new javax.swing.ImageIcon("C:\\Users\\Shiri\\Documents\\Informatica\\TI\\Tercero\\SMM\\Practicas\\Practica4\\iconos\\Lapiz.gif")); // NOI18N
        lapiz.setFocusable(false);
        lapiz.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        lapiz.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        lapiz.addActionListener(formListener);
        barraHerramientas.add(lapiz);

        herramientas.add(linea);
        linea.setIcon(new javax.swing.ImageIcon("C:\\Users\\Shiri\\Documents\\Informatica\\TI\\Tercero\\SMM\\Practicas\\Practica4\\iconos\\Linea.gif")); // NOI18N
        linea.setFocusable(false);
        linea.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        linea.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        linea.addActionListener(formListener);
        barraHerramientas.add(linea);

        herramientas.add(rectangulo);
        rectangulo.setIcon(new javax.swing.ImageIcon("C:\\Users\\Shiri\\Documents\\Informatica\\TI\\Tercero\\SMM\\Practicas\\Practica4\\iconos\\Rectangulo.gif")); // NOI18N
        rectangulo.setFocusable(false);
        rectangulo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        rectangulo.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        rectangulo.addActionListener(formListener);
        barraHerramientas.add(rectangulo);

        herramientas.add(elipse);
        elipse.setIcon(new javax.swing.ImageIcon("C:\\Users\\Shiri\\Documents\\Informatica\\TI\\Tercero\\SMM\\Practicas\\Practica4\\iconos\\Ovalo.gif")); // NOI18N
        elipse.setFocusable(false);
        elipse.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        elipse.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        elipse.addActionListener(formListener);
        barraHerramientas.add(elipse);

        getContentPane().add(barraHerramientas, java.awt.BorderLayout.PAGE_START);

        javax.swing.GroupLayout lienzoLayout = new javax.swing.GroupLayout(lienzo);
        lienzo.setLayout(lienzoLayout);
        lienzoLayout.setHorizontalGroup(
            lienzoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        lienzoLayout.setVerticalGroup(
            lienzoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 174, Short.MAX_VALUE)
        );

        getContentPane().add(lienzo, java.awt.BorderLayout.CENTER);

        archivo.setText("Archivo");

        nuevo.setText("Nuevo");
        nuevo.addActionListener(formListener);
        archivo.add(nuevo);

        abrir.setText("Abrir");
        abrir.addActionListener(formListener);
        archivo.add(abrir);

        guardar.setText("Guardar");
        guardar.addActionListener(formListener);
        archivo.add(guardar);

        menu.add(archivo);

        edicion.setText("Edicion");

        barraEstado.setSelected(true);
        barraEstado.setText("Ver barra de estado");
        barraEstado.addActionListener(formListener);
        edicion.add(barraEstado);

        menu.add(edicion);

        setJMenuBar(menu);

        pack();
    }

    // Code for dispatching events from components to event handlers.

    private class FormListener implements java.awt.event.ActionListener {
        FormListener() {}
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            if (evt.getSource() == relleno) {
                VentanaPrincipal.this.rellenoActionPerformed(evt);
            }
            else if (evt.getSource() == lapiz) {
                VentanaPrincipal.this.lapizActionPerformed(evt);
            }
            else if (evt.getSource() == linea) {
                VentanaPrincipal.this.lineaActionPerformed(evt);
            }
            else if (evt.getSource() == rectangulo) {
                VentanaPrincipal.this.rectanguloActionPerformed(evt);
            }
            else if (evt.getSource() == elipse) {
                VentanaPrincipal.this.elipseActionPerformed(evt);
            }
            else if (evt.getSource() == nuevo) {
                VentanaPrincipal.this.nuevoActionPerformed(evt);
            }
            else if (evt.getSource() == abrir) {
                VentanaPrincipal.this.abrirActionPerformed(evt);
            }
            else if (evt.getSource() == guardar) {
                VentanaPrincipal.this.guardarActionPerformed(evt);
            }
            else if (evt.getSource() == barraEstado) {
                VentanaPrincipal.this.barraEstadoActionPerformed(evt);
            }
            else if (evt.getSource() == negro) {
                VentanaPrincipal.this.negroActionPerformed(evt);
            }
            else if (evt.getSource() == azul) {
                VentanaPrincipal.this.azulActionPerformed(evt);
            }
            else if (evt.getSource() == rojo) {
                VentanaPrincipal.this.rojoActionPerformed(evt);
            }
            else if (evt.getSource() == blanco) {
                VentanaPrincipal.this.blancoActionPerformed(evt);
            }
            else if (evt.getSource() == green) {
                VentanaPrincipal.this.greenActionPerformed(evt);
            }
            else if (evt.getSource() == amarillo) {
                VentanaPrincipal.this.amarilloActionPerformed(evt);
            }
        }
    }// </editor-fold>//GEN-END:initComponents

    private void lapizActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lapizActionPerformed
        this.lienzo.setForma(Herramientas.LAPIZ);
        this.etiqueta.setText("Lapiz");
    }//GEN-LAST:event_lapizActionPerformed

    private void lineaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lineaActionPerformed
        this.lienzo.setForma(Herramientas.LINEA);
        this.etiqueta.setText("Linea");
    }//GEN-LAST:event_lineaActionPerformed

    private void rectanguloActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rectanguloActionPerformed
        this.lienzo.setForma(Herramientas.RECTANGULO);
        this.etiqueta.setText("Rectangulo");
    }//GEN-LAST:event_rectanguloActionPerformed

    private void elipseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_elipseActionPerformed
        this.lienzo.setForma(Herramientas.ELIPSE);
        this.etiqueta.setText("Elipse");
    }//GEN-LAST:event_elipseActionPerformed

    private void rellenoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rellenoActionPerformed
        if(relleno.isSelected()){
            this.lienzo.setRelleno(true);
        }
        else{
            this.lienzo.setRelleno(false);
        }
    }//GEN-LAST:event_rellenoActionPerformed

    private void barraEstadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_barraEstadoActionPerformed
       if(barraEstado.isSelected()){
            this.etiqueta.setVisible(true);
        }
        else{
            this.etiqueta.setVisible(false);
        } 
    }//GEN-LAST:event_barraEstadoActionPerformed

    private void nuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nuevoActionPerformed
        this.herramientas.clearSelection();
        this.lapiz.setSelected(true);
        this.lienzo.setForma(Herramientas.LAPIZ);
        this.etiqueta.setText("Lapiz");
        
        this.colores.clearSelection();
        this.lienzo.setColor(Color.black);
        
        Point posicion = new Point (-10,-10);
        this.lienzo.setPosicion(posicion);
    }//GEN-LAST:event_nuevoActionPerformed

    private void abrirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_abrirActionPerformed
        JFileChooser dlg = new JFileChooser();
        int resp = dlg.showOpenDialog(this);
        if( resp == JFileChooser.APPROVE_OPTION) {
            File f = dlg.getSelectedFile();
            //Código
        }
    }//GEN-LAST:event_abrirActionPerformed

    private void guardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_guardarActionPerformed
        JFileChooser dlg = new JFileChooser();
        int resp = dlg.showSaveDialog(this);
        if( resp == JFileChooser.APPROVE_OPTION) {
            File f = dlg.getSelectedFile();
            //Código
        }
    }//GEN-LAST:event_guardarActionPerformed

    private void negroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_negroActionPerformed
        this.lienzo.setColor(Color.black);
    }//GEN-LAST:event_negroActionPerformed

    private void azulActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_azulActionPerformed
        this.lienzo.setColor(Color.blue);
    }//GEN-LAST:event_azulActionPerformed

    private void rojoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rojoActionPerformed
        this.lienzo.setColor(Color.red);
    }//GEN-LAST:event_rojoActionPerformed

    private void blancoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_blancoActionPerformed
        this.lienzo.setColor(Color.white);
    }//GEN-LAST:event_blancoActionPerformed

    private void greenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_greenActionPerformed
        this.lienzo.setColor(Color.green);
    }//GEN-LAST:event_greenActionPerformed

    private void amarilloActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_amarilloActionPerformed
        this.lienzo.setColor(Color.yellow);
    }//GEN-LAST:event_amarilloActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem abrir;
    private javax.swing.JToggleButton amarillo;
    private javax.swing.JMenu archivo;
    private javax.swing.JToggleButton azul;
    private javax.swing.JCheckBoxMenuItem barraEstado;
    private javax.swing.JToolBar barraHerramientas;
    private javax.swing.JToggleButton blanco;
    private javax.swing.ButtonGroup colores;
    private javax.swing.JMenu edicion;
    private javax.swing.JToggleButton elipse;
    private javax.swing.JLabel etiqueta;
    private javax.swing.JToggleButton green;
    private javax.swing.JMenuItem guardar;
    private javax.swing.ButtonGroup herramientas;
    private javax.swing.JToggleButton lapiz;
    private mipaint.Lienzo lienzo;
    private javax.swing.JToggleButton linea;
    private javax.swing.JMenuBar menu;
    private javax.swing.JToggleButton negro;
    private javax.swing.JMenuItem nuevo;
    private javax.swing.JPanel panelColores;
    private javax.swing.JPanel primarios;
    private javax.swing.JToggleButton rectangulo;
    private javax.swing.JCheckBox relleno;
    private javax.swing.JToggleButton rojo;
    private javax.swing.JPanel secundarios;
    private javax.swing.JPanel zonaColores;
    // End of variables declaration//GEN-END:variables

}