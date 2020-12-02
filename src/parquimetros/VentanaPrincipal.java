package parquimetros;
import java.awt.BorderLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;


import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;

import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;



@SuppressWarnings("serial")
public class VentanaPrincipal extends javax.swing.JFrame 
{

   private VentanaInspector ventanaInspector;
   private VentanaConsultas ventanaConsultas;
   private VentanaTarjetaParquimetro ventanaTarjetaParquimetro;

   private JDesktopPane jDesktopPane1;
   private JMenuBar jMenuBar1;
   private JMenuItem mniSalir;
   private JSeparator jSeparator1;
   private JMenuItem mniInspector;
   private JMenuItem mniConsultas;
   private JMenu mnuModos;
   private JMenuItem mntmTarjetaParquimetro;

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

            
      this.ventanaConsultas = new VentanaConsultas();
      this.ventanaConsultas.setVisible(false);
      this.jDesktopPane1.add(this.ventanaConsultas);
      
      this.ventanaTarjetaParquimetro = new VentanaTarjetaParquimetro();
      ventanaTarjetaParquimetro.setVisible(false);
      this.jDesktopPane1.add(this.ventanaTarjetaParquimetro);
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
               mnuModos = new JMenu();
               jMenuBar1.add(mnuModos);
               mnuModos.setText("Modos");
               {
               	mntmTarjetaParquimetro = new JMenuItem("Parquimetro");
               	mnuModos.add(mntmTarjetaParquimetro);
               	mntmTarjetaParquimetro.addActionListener(new ActionListener() {
               		public void actionPerformed(ActionEvent evt) {
               			mniTarjetaParquimetroActionPerformed(evt);
               		}
               	});
               }
               {
                  mniInspector = new JMenuItem();
                  mnuModos.add(mniInspector);
                  mniInspector.setText("Dispositivo del inspector");
                  mniInspector.addActionListener(new ActionListener() {
                     public void actionPerformed(ActionEvent evt) {
                        mniInspectorActionPerformed(evt);
                     }
                  });
               }
               {
                  mniConsultas = new JMenuItem();
                  mnuModos.add(mniConsultas);
                  mniConsultas.setText("Consultas");
                  mniConsultas.addActionListener(new ActionListener() {
                     public void actionPerformed(ActionEvent evt) {
                        mniConsultasActionPerformed(evt);
                     }
                  });
               }
               
               {
                  jSeparator1 = new JSeparator();
                  mnuModos.add(jSeparator1);
               }
               {
                  mniSalir = new JMenuItem();
                  mnuModos.add(mniSalir);
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
   
   private void mniInspectorActionPerformed(ActionEvent evt) 
   {
      try
      {
    	 this.ventanaInspector = new VentanaInspector();
         ventanaInspector.setLocation(0, 0);
         this.ventanaInspector.setVisible(false);
         this.jDesktopPane1.add(this.ventanaInspector);
         ventanaInspector.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
         this.ventanaInspector.setMaximum(true);
      }
      catch (PropertyVetoException e) {}
      this.ventanaInspector.setVisible(true);      
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
   
   private void mniTarjetaParquimetroActionPerformed(ActionEvent evt)
   {
	   	try
	   	{
	   			this.ventanaTarjetaParquimetro.setMaximum(true);
	   	}
	   	catch(PropertyVetoException e) {}
	   	this.ventanaTarjetaParquimetro.setVisible(true);
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
