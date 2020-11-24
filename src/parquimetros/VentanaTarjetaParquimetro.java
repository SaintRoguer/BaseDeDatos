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
import java.sql.Statement;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
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
import javax.swing.JTextField; 
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.awt.ScrollPane;
import java.awt.Label;
import java.awt.List;
import java.lang.String;




@SuppressWarnings("serial")
public class VentanaTarjetaParquimetro extends javax.swing.JInternalFrame  {
	private JPanel panelParquimetro;
	
	//Ubicaciones
	private JScrollPane scrollPane;
	private JList list;
	private Label label;
	private DefaultListModel model;
	//Parquimetros
	private JScrollPane scrollPane_1;
	private JList list_1;
	private Label label_1;
	private DefaultListModel model_1;
	//Tarjetas
	private JScrollPane scrollPane_2;
	private JList list_2;
	private Label label_2;
	private DefaultListModel model_2;
	//Boton
	JButton btnAperturaOCierre;
	
	//Base de datos.
	private DBTable tabla;
	
	//Ubicacion actual, parquimetro actual
	private String calleActual;
    private int alturaActual;
    private int id_parqActual;
    private int id_tarjetaActual;
    
	
	public VentanaTarjetaParquimetro(){	
		super();
	    getContentPane().setLayout(null);
	    
	    panelParquimetro = new JPanel();
	    panelParquimetro.setBounds(0, 0, 784, 396);
	    getContentPane().add(panelParquimetro);
	    panelParquimetro.setLayout(null);
	    
	    scrollPane = new JScrollPane();
	    scrollPane.setBounds(10, 42, 256, 300);
	    panelParquimetro.add(scrollPane);
	    
	    model= new DefaultListModel();
	    list = new JList(model);
	    list.setBounds(10, 42, 256, 300);
	    scrollPane.setViewportView(list);
	    list.addMouseListener(new MouseListener(){
	    	 public void mousePressed(MouseEvent e) { 
	    		 String sinParsear =(String) list.getSelectedValue();
	    		 String[] parts = sinParsear.split(",");
	    		 String calle = parsCalle(parts[0]);
	    		 int altura = parsAltura(parts[1]);
	    		 cargarParquimetros(calle,altura);
	    		 model_2.clear();
	    		 btnAperturaOCierre.setEnabled(false);
	    		 
	    		 if(model_2.isEmpty())
	    			 	cargarTarjetas();
	    	 }
	    	 public void mouseClicked(MouseEvent e) {}
			 public void mouseEntered(MouseEvent e) {}
			 public void mouseExited(MouseEvent e) {}
			 public void mouseReleased(MouseEvent e) {}
	    	
	    });
	        
	    scrollPane_1 = new JScrollPane();
	    scrollPane_1.setBounds(272, 42, 240, 300);
	    panelParquimetro.add(scrollPane_1);
	    
	    scrollPane_2 = new JScrollPane();
	    scrollPane_2.setBounds(518, 42, 256, 300);
	    panelParquimetro.add(scrollPane_2);
	    
	    label = new Label("Ubicaciones");
	    label.setBounds(95, 10, 62, 22);
	    panelParquimetro.add(label);
	    
	    label_1 = new Label("Parquimetros en ubicacion");
	    label_1.setBounds(321, 10, 170, 22);
	    panelParquimetro.add(label_1);
	    
	    label_2 = new Label("Tarjetas");
	    label_2.setBounds(627, 10, 62, 22);
	    panelParquimetro.add(label_2);
	    
	    model_1= new DefaultListModel();
	    list_1 = new JList(model_1);
	    list_1.setBounds(272, 42, 240, 300);
	    scrollPane_1.setViewportView(list_1);
	    list_1.addMouseListener(new MouseListener(){
	    	 public void mousePressed(MouseEvent e) { 
	    		 String sinParsear =(String) list.getSelectedValue();
	    		 String[] parts = sinParsear.split(" : ");
	    		 id_parqActual=Integer.parseInt(parts[1]);
	    		 if(model_2.isEmpty())
	    			 	cargarTarjetas();
	    	 }
	    	 public void mouseClicked(MouseEvent e) {}
			 public void mouseEntered(MouseEvent e) {}
			 public void mouseExited(MouseEvent e) {}
			 public void mouseReleased(MouseEvent e) {}
	    	
	    });
	    
	    model_2= new DefaultListModel();
	    list_2 = new JList(model_2);
	    list_2.setBounds(518, 42, 256, 300);
	    scrollPane_2.setViewportView(list_2);
	    list_2.addMouseListener(new MouseListener(){
	    	 public void mousePressed(MouseEvent e) { 
	    		 String sinParsear =(String) list.getSelectedValue();
	    		 String[] parts = sinParsear.split(" : ");
	    		 id_tarjetaActual=Integer.parseInt(parts[1]);
	    		 btnAperturaOCierre.setEnabled(true);
	    	 }
	    	 public void mouseClicked(MouseEvent e) {}
			 public void mouseEntered(MouseEvent e) {}
			 public void mouseExited(MouseEvent e) {}
			 public void mouseReleased(MouseEvent e) {}
	    	
	    });
	    
	    
	    
	    btnAperturaOCierre = new JButton("Apertura o cierre de estacionamiento");
	    btnAperturaOCierre.setBounds(272, 362, 240, 23);
	    panelParquimetro.add(btnAperturaOCierre);
	    btnAperturaOCierre.setEnabled(false);
	    
	    // crea la tabla  
    	tabla = new DBTable();
    	
	    conectarBD();
	    
	    cargarUbicaciones();
	    
	    initGUI();
	}
	
	 private void conectarBD()
	   {
	         try
	         {
	            String driver ="com.mysql.cj.jdbc.Driver";
	        	String servidor = "localhost:3306";
	        	String baseDatos = "parquimetros"; 
	        	String usuario = "parquimetro";
	        	String clave = "parq";
	            String uriConexion = "jdbc:mysql://" + servidor + "/" + 
	        	                     baseDatos +"?serverTimezone=America/Argentina/Buenos_Aires";
	   
	       //establece una conexión con la  B.D. "batallas"  usando directamante una tabla DBTable    
	            tabla.connectDatabase(driver, uriConexion, usuario, clave);
	           
	         }
	         catch (SQLException ex)
	         {
	            JOptionPane.showMessageDialog(this,
	                           "Se produjo un error al intentar conectarse a la base de datos.\n" 
	                            + ex.getMessage(),
	                            "Error",
	                            JOptionPane.ERROR_MESSAGE);
	            System.out.println("SQLException: " + ex.getMessage());
	            System.out.println("SQLState: " + ex.getSQLState());
	            System.out.println("VendorError: " + ex.getErrorCode());
	         }
	         catch (ClassNotFoundException e)
	         {
	            e.printStackTrace();
	         }
	      
	   }
	
	
	
	
	
	private void initGUI() 
	   {
	      try {
	         setPreferredSize(new Dimension(800, 600));
	         this.setBounds(0, 0, 800, 600);
	         setVisible(true);
	         this.setTitle("Tarjeta - Parquimetro");
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


	private void cargarUbicaciones() {

	      PreparedStatement consulta;
			try {
				consulta = tabla.getConnection().prepareStatement("SELECT DISTINCT calle,altura FROM parquimetros;", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
				consulta.execute();
				ResultSet resultados = consulta.getResultSet();
				//Creo un item por cada ubicacion.
				while(resultados.next()) {
					String calle = resultados.getString("calle");
					int altura = resultados.getInt("altura");
						            
					model.addElement("calle : "+calle+", altura : "+altura);
					
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	//Parsea la Altura del string de la lista de ubicaciones.
	private int parsAltura(String sinParsear) {
		
		String[] parts = sinParsear.split(" : ");
		
		
		return Integer.parseInt(parts[1]);
	}
	
	private String parsCalle(String sinParsear) {
		
		String[] parts = sinParsear.split(" : ");
		return parts[1];
	}
	
	private void cargarParquimetros(String c, int a) {
		model_1.clear();
		PreparedStatement consulta;
		try {
			consulta = tabla.getConnection().prepareStatement("SELECT id_parq FROM parquimetros WHERE calle='"+c+"' AND altura='"+a+"' ;", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			consulta.execute();
			ResultSet resultados = consulta.getResultSet();
			//Creo un item por cada ubicacion.
			while(resultados.next()) {
				String id_parq = resultados.getString("id_parq");
						            
				model_1.addElement("ID de parquimetro : "+id_parq);
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	private void cargarTarjetas() {
		PreparedStatement consulta;
		try {
			consulta = tabla.getConnection().prepareStatement("SELECT id_tarjeta FROM tarjetas;", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			consulta.execute();
			ResultSet resultados = consulta.getResultSet();
			//Creo un item por cada ubicacion.
			while(resultados.next()) {
				String id_tarjeta = resultados.getString("id_tarjeta");
						            
				model_2.addElement("Tarjeta con ID : "+id_tarjeta);
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
		
	










}
