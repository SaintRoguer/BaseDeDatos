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
    private JTextField txtIngresarpatentes;
    
    private String legajo;
    private int par;
    private String call;
    private int alt;
    
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
    		  	if(model.isEmpty())
    		  		btnLabrarMultas.setEnabled(false);
    		  	
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
     
      scrollPane.setViewportView(list_1);
      
      lblPatentesEnUbicacion = new JLabel("Patentes en ubicacion");
      lblPatentesEnUbicacion.setBounds(263, 1, 105, 14);
      panelInspector.add(lblPatentesEnUbicacion);
      
      btnLabrarMultas = new JButton("Labrar multas");
      btnLabrarMultas.setEnabled(false);
      btnLabrarMultas.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		ejecutarLabrarMultas();
        			
        	}
        });
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
      
      txtIngresarpatentes = new JTextField();
      txtIngresarpatentes.setText("IngresarPatentes");
      txtIngresarpatentes.setEnabled(false);
      txtIngresarpatentes.addActionListener(new ActionListener() {
    	  	public void actionPerformed(ActionEvent arg0) {
    	  		if(txtIngresarpatentes.getText().length()==6) 
    	  			model.addElement(""+txtIngresarpatentes.getText());
    	  		txtIngresarpatentes.setText("");
    	  		if(!model.isEmpty())
    	  			btnLabrarMultas.setEnabled(true);
    	  	}
    	  
      });
      txtIngresarpatentes.setBounds(62, 120, 89, 20);
      panelInspector.add(txtIngresarpatentes);
      txtIngresarpatentes.setColumns(10);
      
      JLabel lblPatente = new JLabel("Patente:");
      lblPatente.setBounds(10, 123, 42, 14);
      panelInspector.add(lblPatente);
      
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
				
				
			}
			else
				btnIngresarCredenciales.setVisible(true);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
   private void ejecutarIngresarLegajo() {
	   IngresarLegajo IngLeg = new IngresarLegajo(tabla);
	   IngLeg.setVisible(true);
	   if(tabla.isValid()){
		   legajo = IngLeg.getLegajo();
		   menuBar.setEnabled(true);
		   txtIngresarpatentes.setEnabled(true);
		   cargarUbicaciones();
	   }
	   	else
	   		btnIngresarLegajo.setVisible(true);
	   IngLeg.dispose();
	   
   }
   
   private void cargarUbicaciones() {

	      PreparedStatement consulta;
			try {
				consulta = tabla.getConnection().prepareStatement("SELECT id_parq,calle,altura FROM parquimetros;", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
				consulta.execute();
				ResultSet resultados = consulta.getResultSet();
				//Creo un item por cada ubicacion.
				while(resultados.next()) {
					String calle = resultados.getString("calle");
					int altura = resultados.getInt("altura");
					int parquimetro = resultados.getInt("id_parq");    
		            
					ubi = new JMenuItem();
					mnSeleccionarUbicacion.add(ubi);
					ubi.setText("N° parquimetro: "+parquimetro+" calle: "+calle+" altura: "+altura);
					ubi.addActionListener(new ActionListener() {
	                   public void actionPerformed(ActionEvent evt) {
	                      menuItemAction(evt,calle,altura,parquimetro);
	                   }
	                });
					
				}
			
			
			
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
   }
   
   private void menuItemAction(ActionEvent evt, String callie, int alturia, int parquimetro) {
	   //Remueve todos los elementos de la lista.
	   model_1.clear();
	   
	   par=parquimetro;
	   call=callie;
	   alt=alturia;
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
        		model_1.addElement(patenteE);
        	}
			
		}
		
		
		
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	   
	   
	   
   }
   
   
   private void ejecutarLabrarMultas() {
	   PreparedStatement consUbic;
	   PreparedStatement consLab;
	   PreparedStatement check;
	   boolean esta = false;
	   String turn="";
	   int id_as = -1;
	   try {
		   check = tabla.getConnection().prepareStatement("SELECT * FROM asociado_con;", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
		   check.execute();
		   ResultSet rescheck = check.getResultSet();
		   //chequeo si el inspector esta asociado con la ubicacion.
		   while(rescheck.next()) {
				int legaM = rescheck.getInt("legajo");
				String calleM = rescheck.getString("calle");
				int alturaM = rescheck.getInt("Altura");
				String turnoM = rescheck.getString("turno");
				int id_asM = rescheck.getInt("id_asoicado_con");
				if(legaM == Integer.parseInt(legajo) && calleM.contentEquals(call) && alturaM==alt) {
					esta=true;
					turn = turnoM;
					id_as = id_asM;
				}
			}
		
		
	   } catch (SQLException e1) {
		   // TODO Auto-generated catch block
		   e1.printStackTrace();
	   }
	   
	 //Hora actual
	   LocalDateTime hor = LocalDateTime.now();
	   DateTimeFormatter horForm = DateTimeFormatter.ofPattern("HH:mm");
	   String formatedHor = hor.format(horForm);
	   //La hora actual es entre las 8 y las 13:59 o entre las 14 y las 20.
	   boolean turno = (turn.equals("M") && formatedHor.compareTo("08;00")>0 && formatedHor.compareTo("13:59")<0) | (turn.equals("T") && formatedHor.compareTo("14:00")>0 && formatedHor.compareTo("20:00")<0);
	   
	   //El inspector no esta asociado con la ubicacion o esta fuera de su turno.
	   if(!esta|!turno) {
		   JOptionPane.showMessageDialog(new JFrame(), "El inspector no esta autorizado para labrar multas en esta ubicacion", "Dialog",
			        JOptionPane.ERROR_MESSAGE);
		   
	   }
	   else {
		   	int cantLista = model.getSize();
	     
		   	for(int i=0;i<cantLista;i++) {
		   		//Decodifico el elemento i de la lista.
		   		String patenteI = (String) model.get(i);
		   		//Fecha actual.
		   		LocalDateTime date = LocalDateTime.now();
		   		DateTimeFormatter dateForm = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		   		String formatedDate = date.format(dateForm);
		   
		   
		   		//Hora actual
		   		LocalDateTime hour = LocalDateTime.now();
		   		DateTimeFormatter hourForm = DateTimeFormatter.ofPattern("HH:mm:ss.SS");
		   		String formatedHour = hour.format(hourForm);
		   		
		   		//chequeo si la patenteI esta estacionada.
		   		boolean stacionado = false;
		   		try {
		   			PreparedStatement estacionado = tabla.getConnection().prepareStatement("SELECT patente FROM estacionados;", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
		   			estacionado.execute();
				   	ResultSet resest = estacionado.getResultSet();
				   	while(resest.next()) {
						String pat = resest.getString("patente");
						if(pat.equals(patenteI))
							stacionado=true;
				   	}
				   	
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		   		//si no esta estacionado labro la multa, insertando una multa a la base de datos.
		   		if(!stacionado) {
		   		
		   		try {
		   			consUbic = tabla.getConnection().prepareStatement("INSERT INTO multa(fecha,hora,patente,id_asociado_con)"+
		   																"VALUES ('"+ formatedDate +"','" + formatedHour + "','" +
		   																patenteI +"','" + id_as + "')"
		   																, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
		   			consUbic.execute();	
		   
		   		} catch (SQLException e) {
		   			// TODO Auto-generated catch block
		   			e.printStackTrace();
		   		}
		   		}
		  
		   
		   
		   
		   	}
	   }
	   
	 
   
   }
}
