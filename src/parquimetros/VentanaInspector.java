package parquimetros;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Types;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
//import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.border.BevelBorder;

import quick.dbtable.*;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTable; 


@SuppressWarnings("serial")
public class VentanaInspector extends javax.swing.JInternalFrame 
{
	
	private JPanel panelInspector;
	private JButton btnIngresarCredenciales;
    private JLabel lblPatentesSinIngresar;
    
    private JScrollPane scrollPane_1;
    private JList list;
    private DefaultListModel model;
    
    private JScrollPane scrollPane;
    private JList list_1;
    private DefaultListModel model_1;
    
    private JLabel lblPatentesEnUbicacion;
    private JButton btnLabrarMultas;
    private DBTable tabla;
    private JButton btnIngresarLegajo;
    
    private JMenuBar menuBar;
    private JMenu mnSeleccionarUbicacion;
    private JMenuItem ubi;
    
   public VentanaInspector() 
   {
      super();
      getContentPane().setLayout(null);
      
      panelInspector = new JPanel();
      panelInspector.setBounds(0, 0, 784, 362);
      getContentPane().add(panelInspector);
      panelInspector.setLayout(null);
      
      btnIngresarCredenciales = new JButton("Ingresar credenciales");
      btnIngresarCredenciales.addActionListener(new ActionListener() {
      	public void actionPerformed(ActionEvent arg0) {
      		ejecutarIngresarCredenciales();
      			
      	}
      });
      btnIngresarCredenciales.setBounds(30, 11, 135, 23);
      panelInspector.add(btnIngresarCredenciales);
      
      lblPatentesSinIngresar = new JLabel("Patentes seleccionadas");
      lblPatentesSinIngresar.setBounds(558, 1, 112, 14);
      panelInspector.add(lblPatentesSinIngresar);
      
      scrollPane_1 = new JScrollPane();
      scrollPane_1.setBounds(453, 45, 307, 315);
      panelInspector.add(scrollPane_1);
      
      model= new DefaultListModel();
      list = new JList(model);
      list.addMouseListener(new MouseListener() {
    	  public void mousePressed(MouseEvent e) {
    		  	model.removeElement(((String) list_1.getSelectedValue()));
			}

			public void mouseClicked(MouseEvent e) {}
			public void mouseEntered(MouseEvent e) {}
			public void mouseExited(MouseEvent e) {}
			public void mouseReleased(MouseEvent e) {}
    	  
      });
      scrollPane_1.setViewportView(list);
      
      scrollPane = new JScrollPane();
      scrollPane.setBounds(194, 46, 258, 313);
      panelInspector.add(scrollPane);
      
      model_1= new DefaultListModel();
      list_1 = new JList(model_1);
      list_1.addMouseListener(new MouseListener() {
    	  public void mousePressed(MouseEvent e) {
    		  	if(!model.contains(((String) list_1.getSelectedValue())))
    		  		model.addElement(((String) list_1.getSelectedValue()));
			}

			public void mouseClicked(MouseEvent e) {}
			public void mouseEntered(MouseEvent e) {}
			public void mouseExited(MouseEvent e) {}
			public void mouseReleased(MouseEvent e) {}
    	  
      });
      scrollPane.setViewportView(list_1);
      
      lblPatentesEnUbicacion = new JLabel("Patentes en ubicacion");
      lblPatentesEnUbicacion.setBounds(263, 1, 105, 14);
      panelInspector.add(lblPatentesEnUbicacion);
      
      btnLabrarMultas = new JButton("Labrar multas");
      btnLabrarMultas.setEnabled(false);
      btnLabrarMultas.setBounds(30, 173, 135, 23);
      panelInspector.add(btnLabrarMultas);
      
      btnIngresarLegajo = new JButton("Ingresar Legajo");
      btnIngresarLegajo.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		ejecutarIngresarLegajo();
        			
        	}
        });
      
      btnIngresarLegajo.setBounds(42, 71, 109, 23);
      btnIngresarLegajo.setVisible(false);
      panelInspector.add(btnIngresarLegajo);
     
      menuBar = new JMenuBar();
      menuBar.setBounds(30, 207, 97, 21);
      panelInspector.add(menuBar);
      
      mnSeleccionarUbicacion = new JMenu("Seleccionar ubicacion");
      menuBar.add(mnSeleccionarUbicacion);
      
      tabla = new DBTable();
      tabla.setEditable(false);
      tabla.setBounds(0, 362, 784, 208);
      getContentPane().add(tabla);
      initGUI();
      
		
   }
 
   private void initGUI() 
   {
      try {
         setPreferredSize(new Dimension(800, 600));
         this.setBounds(0, 0, 800, 600);
         setVisible(true);
         this.setTitle("Unidad personal del inspector");
         this.setClosable(true);
         this.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
         this.setMaximizable(true);
         this.addComponentListener(new ComponentAdapter() {
            public void componentHidden(ComponentEvent evt) {
               /*thisComponentHidden(evt);*/
            }
            /*
            public void componentShown(ComponentEvent evt) {
               thisComponentShown(evt);
            }*/
         });
      } catch (Exception e) {
         e.printStackTrace();
      }
   }
   
   
   private void ejecutarIngresarCredenciales() {
		JDialog IngCred = new IngresarInspector(tabla);
		IngCred.setVisible(true);
		try {
			if(tabla.getConnection().isValid(5)) {
				btnIngresarCredenciales.setVisible(false);
				btnIngresarLegajo.setVisible(true);
				cargarUbicaciones();
				
			}
			else
				btnIngresarCredenciales.setVisible(true);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
   private void ejecutarIngresarLegajo() {
	   JDialog IngLeg = new IngresarLegajo(tabla);
	   IngLeg.setVisible(true);
	   if(tabla.isValid()){
		   menuBar.setEnabled(true);
				
	   }
	   	else
	   		btnIngresarLegajo.setVisible(true);
	   
   }
   
   private void cargarUbicaciones() {

	      PreparedStatement consulta;
			try {
				consulta = tabla.getConnection().prepareStatement("SELECT calle,altura FROM parquimetros;", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
				consulta.execute();
				ResultSet resultados = consulta.getResultSet();
				//Creo un item por cada ubicacion.
				while(resultados.next()) {
					String calle = resultados.getString("calle");
					int altura = resultados.getInt("altura");
					    
		            
					ubi = new JMenuItem();
					mnSeleccionarUbicacion.add(ubi);
					ubi.setText("calle: "+calle+" altura: "+altura);
					ubi.addActionListener(new ActionListener() {
	                   public void actionPerformed(ActionEvent evt) {
	                      menuItemAction(evt,calle,altura);
	                   }
	                });
					
				}
			
			
			
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
   }
   
   private void menuItemAction(ActionEvent evt, String callie, int alturia) {
	   //Remueve todos los elentos de la lista.
	   model.clear();
	   model_1.clear();
	   
	   PreparedStatement consUbic;
	   try {
		consUbic = tabla.getConnection().prepareStatement("SELECT * FROM estacionados;", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
		consUbic.execute();
		ResultSet resUbic = consUbic.getResultSet();
		//Recorre los elementos de la consulta.
		while(resUbic.next()) {
			String calleE = resUbic.getString("calle");
			int alturaE = resUbic.getInt("altura");
			String patenteE = resUbic.getString("patente");
        	if(calleE.equals(callie) && alturaE == alturia) {
        		model_1.addElement("calle: "+calleE+" altura: "+alturaE+" patente: "+patenteE);
        	}
			
		}
		
		
		
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	   
	   
	   
   }
}
