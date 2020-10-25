﻿package parquimetros;
import java.awt.BorderLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyVetoException;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDesktopPane;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;
import javax.swing.LayoutStyle;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;



@SuppressWarnings("serial")
public class VentanaPrincipal extends javax.swing.JFrame 
{

   private VentanaInspector ventanaBarcos;
   private VentanaConsultas ventanaConsultas;
   private JButton btnCerrar;
   private JLabel lblAcercaDe4;
   private JLabel lblAcercaDe3;
   private JLabel lblAcercaDe2;
   private JLabel lblAcercaDe1;
   private JDialog dlgAcercaDe;

   private JDesktopPane jDesktopPane1;
   private JMenuBar jMenuBar1;
   private JMenuItem mniSalir;
   private JSeparator jSeparator1;
   private JMenuItem mniBarcos;
   private JMenuItem mniConsultas;
   private JMenu mnuEjemplos;

   /**
   * Auto-generated main method to display this JFrame
   */
   public static void main(String[] args) 
   {
      SwingUtilities.invokeLater(new Runnable() {
         public void run() {
            VentanaPrincipal inst = new VentanaPrincipal();
            inst.setLocationRelativeTo(null);
            inst.setVisible(true);
         }
      });
   }
   
   public VentanaPrincipal() 
   {
      super();

      initGUI();

      this.ventanaBarcos = new VentanaInspector();
      ventanaBarcos.setLocation(0, -12);
      this.ventanaBarcos.setVisible(false);
      this.jDesktopPane1.add(this.ventanaBarcos);
      
      this.ventanaConsultas = new VentanaConsultas();
      this.ventanaConsultas.setVisible(false);
      this.jDesktopPane1.add(this.ventanaConsultas);

   }
   
   private void initGUI() 
   {
      try 
      {
         javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
      } 
      catch(Exception e) 
      {
         e.printStackTrace();
      }
      
      try {
         {
            this.setTitle("Java y MySQL");
            this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
         }
         {
            jDesktopPane1 = new JDesktopPane();
            getContentPane().add(jDesktopPane1, BorderLayout.CENTER);
            jDesktopPane1.setPreferredSize(new java.awt.Dimension(800, 600));
         }
         
         {
            jMenuBar1 = new JMenuBar();
            setJMenuBar(jMenuBar1);
            {
               mnuEjemplos = new JMenu();
               jMenuBar1.add(mnuEjemplos);
               mnuEjemplos.setText("Ejemplos");
               {
                  mniBarcos = new JMenuItem();
                  mnuEjemplos.add(mniBarcos);
                  mniBarcos.setText("Dispositivo del inspector");
                  mniBarcos.addActionListener(new ActionListener() {
                     public void actionPerformed(ActionEvent evt) {
                        mniBarcosActionPerformed(evt);
                     }
                  });
               }
               {
                  mniConsultas = new JMenuItem();
                  mnuEjemplos.add(mniConsultas);
                  mniConsultas.setText("Consultas");
                  mniConsultas.addActionListener(new ActionListener() {
                     public void actionPerformed(ActionEvent evt) {
                        mniConsultasActionPerformed(evt);
                     }
                  });
               }
               
               {
                  jSeparator1 = new JSeparator();
                  mnuEjemplos.add(jSeparator1);
               }
               {
                  mniSalir = new JMenuItem();
                  mnuEjemplos.add(mniSalir);
                  mniSalir.setText("Salir");
                  mniSalir.addActionListener(new ActionListener() {
                     public void actionPerformed(ActionEvent evt) {
                        mniSalirActionPerformed(evt);
                     }
                  });
               }
            }
         }
         this.setSize(800, 600);
         pack();
      } catch (Exception e) {
         e.printStackTrace();
      }
   }
   
   private void mniSalirActionPerformed(ActionEvent evt) 
   {
      this.dispose();
   }
   
   private void mniBarcosActionPerformed(ActionEvent evt) 
   {
      try
      {
         this.ventanaBarcos.setMaximum(true);
      }
      catch (PropertyVetoException e) {}
      this.ventanaBarcos.setVisible(true);      
   }
   
   private void mniConsultasActionPerformed(ActionEvent evt) 
   {
      try
      {
         this.ventanaConsultas.setMaximum(true);
      }
      catch (PropertyVetoException e) {}
      this.ventanaConsultas.setVisible(true);      
   }

   private void btnCerrarActionPerformed(ActionEvent evt) 
   {
      dlgAcercaDe.setVisible(false);
   }
   
   private void centrarDialogo(javax.swing.JDialog p_dialogo)
   {
     p_dialogo.setLocationRelativeTo(this);
     p_dialogo.setLocationByPlatform(false);
     int desplzX = (int) ((this.getSize().getWidth() / 2.0) - (p_dialogo.getSize().getWidth() / 2.0));
     int desplzY = (int) ((this.getSize().getHeight() / 2.0) - (p_dialogo.getSize().getHeight() / 2.0));
     p_dialogo.setLocation(new Point((int) this.getLocationOnScreen().getX() + desplzX,
                                     (int) this.getLocationOnScreen().getY() + desplzY));

   }   
   

}
