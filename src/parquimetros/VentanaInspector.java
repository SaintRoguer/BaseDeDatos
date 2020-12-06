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
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.text.DateFormat;
import java.text.SimpleDateFormat;;

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
    
    
    //Ubicacion actual y legajo de usuario ingresado.
    private String legajo;
    private int par;
    private String calleActual;
    private int alturaActual;
    
    
    
   public VentanaInspector() 
   {
      super();
      getContentPane().setLayout(null);
      
      panelInspector = new JPanel();
      panelInspector.setBounds(0, 0, 784, 362);
      getContentPane().add(panelInspector);
      panelInspector.setLayout(null);
      
      lblPatentesSinIngresar = new JLabel("Patentes ingresadas:");
      lblPatentesSinIngresar.setBounds(559, 15, 112, 14);
      panelInspector.add(lblPatentesSinIngresar);
      
      scrollPane_1 = new JScrollPane();
      scrollPane_1.setBounds(453, 45, 307, 315);
      panelInspector.add(scrollPane_1);
      
      model= new DefaultListModel();
      
      list = new JList(model);
      list.addMouseListener(new MouseListener() {
    	  public void mousePressed(MouseEvent e) {
    		  	model.removeElement(((String) list.getSelectedValue()));
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
      
      lblPatentesEnUbicacion = new JLabel("Patentes en ubicacion:");
      lblPatentesEnUbicacion.setBounds(262, 15, 112, 14);
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
      menuBar.setBounds(30, 219, 135, 23);
      menuBar.setEnabled(false);
      panelInspector.add(menuBar);
      
      mnSeleccionarUbicacion = new JMenu("Seleccionar ubicacion");
      menuBar.add(mnSeleccionarUbicacion);
      
      txtIngresarpatentes = new JTextField();
      txtIngresarpatentes.setText("Ingresar Patente");
      txtIngresarpatentes.setEnabled(false);
      txtIngresarpatentes.addActionListener(new ActionListener() {
    	  	public void actionPerformed(ActionEvent arg0) {
    	  		
    	  		if(patenteValida(txtIngresarpatentes.getText().toUpperCase())&& patenteNoEnLista(txtIngresarpatentes.getText().toUpperCase()))
    	  			model.addElement(""+txtIngresarpatentes.getText().toUpperCase());
    	  		txtIngresarpatentes.setText("");
    	  		if(!model.isEmpty()&& (calleActual != null))
    	  			btnLabrarMultas.setEnabled(true);
    	  	}
    	  
      });
      txtIngresarpatentes.addMouseListener(new MouseListener(){
      public void mousePressed(MouseEvent e) {
		  	txtIngresarpatentes.setText("");
      }
		

		public void mouseClicked(MouseEvent e) {}
		public void mouseEntered(MouseEvent e) {}
		public void mouseExited(MouseEvent e) {}
		public void mouseReleased(MouseEvent e) {};
      });
      txtIngresarpatentes.setBounds(62, 120, 103, 20);
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
      conectarBD();
      
      
		
   }
   //Verifica que la patente ingresada no este en la lista
   private boolean patenteNoEnLista(String patente) {
	   boolean esta= model.contains(patente);
	   
	   if(esta)
		   JOptionPane.showMessageDialog(new JFrame(), "La patente "+patente+" ya se encuentra ingresada.","Patente",
			        JOptionPane.ERROR_MESSAGE);
	   
	   return !esta;
   }
   
   
   //Verifica que la patente ingresada se encuentra en la base de datos.
   protected boolean patenteValida(String patente) {
	   boolean valida = false;
	   if(patente.length() == 6) {
		   try {
			   Statement consulta = tabla.getConnection().createStatement();
			   consulta.execute("SELECT patente FROM automoviles;");
			   ResultSet result = consulta.getResultSet();
			   while(result.next() && !valida)
				   valida = result.getString("patente").equals(patente);
		   } catch (SQLException e) {e.printStackTrace();}
	   }
	   
	   if(!valida)
		   JOptionPane.showMessageDialog(new JFrame(), "La patente "+patente+" no es valida.","Patente",
			        JOptionPane.ERROR_MESSAGE);
	   return valida;
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
   
	private void conectarBD()
	{
	      try
	      {
	         String driver ="com.mysql.cj.jdbc.Driver";
	     	 String servidor = "localhost:3306";
	     	 String baseDatos = "parquimetros"; 
	     	 String usuario = "inspector";
	     	 String clave = "inspector";
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
	      	btnIngresarLegajo.setVisible(true);
			//Crea una tabla temporal con los nuevos registros de multas.
			PreparedStatement tablaTemp;
			try {
				tablaTemp = tabla.getConnection().prepareStatement("CREATE TEMPORARY TABLE MULTILLAS(numero_de_multa INT UNSIGNED NOT NULL,"
						+ " fecha DATE NOT NULL, hora TIME(2) NOT NULL, calle VARCHAR(45) NOT NULL, altura INT UNSIGNED NOT NULL, patente_del_auto VARCHAR(6) NOT NULL,"
						+ " legajo_del_inspector INT UNSIGNED NOT NULL);", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
				tablaTemp.execute();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//Se conecta la dbTable a la tabla temporal.
			tabla.setSelectSql("select * from MULTILLAS");
	   
	}
   

   
   private void ejecutarIngresarLegajo() {
	   IngresarLegajo IngLeg = new IngresarLegajo(tabla,this);
	   IngLeg.setVisible(true);
	   IngLeg.dispose();
	   
   }
   
   public void legValido(IngresarLegajo IngLeg) {
	   legajo = IngLeg.getLegajo();
	   menuBar.setEnabled(true);
	   btnIngresarLegajo.setVisible(false);
	   cargarUbicaciones();
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
	   //Remueve todos los elementos de la lista que muestra las patentes estacionadas.
	   model_1.clear();
	   //Remueve las patentes cargadas al cambiar de ubicacion
	   model.clear();
	   
	   txtIngresarpatentes.setEnabled(true);
	   txtIngresarpatentes.setText("Ingresar Patente");
	   par=parquimetro;
	   calleActual=callie;
	   alturaActual=alturia;
	   mnSeleccionarUbicacion.setText("           "+callie+" al "+alturia+"             ");
	   PreparedStatement consUbic;
	   PreparedStatement consAccede;
	   
		 //Fecha actual.
	  		LocalDateTime date = LocalDateTime.now();
	  		DateTimeFormatter dateForm = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	  		String formatedDate = date.format(dateForm);
	  
	  
	  		//Hora actual
	  		LocalDateTime hour = LocalDateTime.now();
	  		DateTimeFormatter hourForm = DateTimeFormatter.ofPattern("HH:mm:ss.SS");
	  		String formatedHour = hour.format(hourForm);
	  		
		   //Registra el acceso a parquimetro.
		    try {
				consAccede = tabla.getConnection().prepareStatement("INSERT INTO ACCEDE(legajo,id_parq,fecha,hora) "+
						"VALUES ('"+ legajo + "','" +
						par +"','"+
						formatedDate +"','" + formatedHour + "')"
						, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
				
				consAccede.execute();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
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
   
   private void refrescarTabla()
   {
      try
      {    
    	 // seteamos la consulta a partir de la cual se obtendran los datos para llenar la tabla
    	  tabla.setSelectSql("select * from MULTILLAS");

    	  // obtenemos el modelo de la tabla a partir de la consulta para 
    	  // modificar la forma en que se muestran de algunas columnas  
    	  tabla.createColumnModelFromQuery();    	    
    	  for (int i = 0; i < tabla.getColumnCount(); i++)
    	  { // para que muestre correctamente los valores de tipo TIME (hora)  		   		  
    		 if	 (tabla.getColumn(i).getType()==Types.TIME)  
    		 {    		 
    		    tabla.getColumn(i).setType(Types.CHAR);  
  	       	 }
    		 // cambiar el formato en que se muestran los valores de tipo DATE
    		 if	 (tabla.getColumn(i).getType()==Types.DATE)
    		 {
    		    tabla.getColumn(i).setDateFormat("dd/MM/YYYY");
    		 }
          }  
    	  // actualizamos el contenido de la tabla.   	
    	  JTable table = tabla.getTable();
	      table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
	       
    	  tabla.refresh();
    	  // No es necesario establecer  una conexión, crear una sentencia y recuperar el 
    	  // resultado en un resultSet, esto lo hace automáticamente la tabla (DBTable) a 
    	  // patir de la conexión y la consulta seteadas con connectDatabase() y setSelectSql() respectivamente.
          
    	  
    	  
       }
      catch (SQLException ex)
      {
         // en caso de error, se muestra la causa en la consola
         System.out.println("SQLException: " + ex.getMessage());
         System.out.println("SQLState: " + ex.getSQLState());
         System.out.println("VendorError: " + ex.getErrorCode());
         JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(this),
                                       ex.getMessage() + "\n", 
                                       "Error al ejecutar la consulta.",
                                       JOptionPane.ERROR_MESSAGE);
         
      }
      


      
   }
   
   //Metodo que retorna el dia si el dia es igual al dia recibido, sino retorna "".
   private String diaActual(String dia) {
	   String actual = "";
	   int diaNum = getDayNumberOld(new Date());
	   switch (diaNum)
	   {
	   	case 1:
	   		actual = "do";
	   		break;
	   	case 2:
	   		actual = "lu";
	   		break;
	   	case 3:
	   		actual = "ma";
	   		break;
	   	case 4:
	   		actual = "mi";
	   		break;
	   	case 5:
	   		actual = "ju";
	   		break;
	   	case 6:
	   		actual = "vi";
	   		break;
	   	case 7:
	   		actual = "sa";
	   		break;
	   }
	   if(!actual.equals(dia))
		   actual = "";
		   
	   return actual;
   }
   
   private static int getDayNumberOld(Date date) {
	    Calendar cal = Calendar.getInstance();
	    cal.setTime(date);
	    return cal.get(Calendar.DAY_OF_WEEK);
	}
   
   public static String getDayStringOld(Date date, Locale locale) {
	    DateFormat formatter = new SimpleDateFormat("EEEE", locale);
	    return formatter.format(date);
	}
   
   private void ejecutarLabrarMultas() {
	   PreparedStatement multas;
	   PreparedStatement consUbic;
	   PreparedStatement insert;
	   PreparedStatement consLab;
	   PreparedStatement check;
	   int nMulta=-1;
	   boolean esta = false;
	   String turn="";
	   int id_as = -1;
	   String dia="";
	   
	   try {
		   check = tabla.getConnection().prepareStatement("SELECT * FROM asociado_con;", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
		   check.execute();
		   ResultSet rescheck = check.getResultSet();
		   //chequeo si el inspector esta asociado con la ubicacion.
		   while(rescheck.next()) {
				int legaM = rescheck.getInt("legajo");
				String calleM = rescheck.getString("calle");
				int alturaM = rescheck.getInt("altura");
				String turnoM = rescheck.getString("turno");
				String diaM = rescheck.getString("dia");
				int id_asM = rescheck.getInt("id_asociado_con");
				if(legaM == Integer.parseInt(legajo) && calleM.contentEquals(calleActual) && alturaM==alturaActual) {
					esta=true;
					
					id_as = id_asM;
					//Verifico si alguna tupla tiene el dia actual como dia en el que se le asigno el turno.
					String actual = diaActual(diaM);
					if(!actual.equals("")) {
						dia = diaM;
						//Tiene un solo turno o es la primera tupla.
						if(turn.equals(""))
							turn = turnoM;
						else //Tiene 2 turnos, mañana y tarde.
							turn = "mt";
					}
				}
			}
		   
	   } catch (SQLException e1) {
		   // TODO Auto-generated catch block
		   e1.printStackTrace();
	   }
	   System.out.println("dia : "+dia);
	   
	   //El inspector no esta asociado con la ubicacion.
	   if(!esta) {
		   JOptionPane.showMessageDialog(new JFrame(), "El inspector no esta autorizado para labrar multas en esta ubicacion", "Ubicacion",
			        JOptionPane.ERROR_MESSAGE);
		   
	   }
	   else { 
		   //Hora actual
		   LocalDateTime hor = LocalDateTime.now();
		   DateTimeFormatter horForm = DateTimeFormatter.ofPattern("HH:mm");
		   String formatedHor = hor.format(horForm);
		   
		   //Dia actual en numero, 1 Domingo 7 sabado. 
		   Date dateI = new Date();
		  
		   
		   //El dia actual es el dia en el que trabaja el inspector.
		   boolean turnoD = (dia.equals("")) ? false : true; 
		   //La hora actual es entre las 8 y las 13:59 o entre las 14 y las 20.
		   boolean turno = turnoD && (((turn.equals("m") || turn.equals("mt")) && formatedHor.compareTo("08:00")>0 && formatedHor.compareTo("13:59")<0) || ((turn.equals("t") || turn.equals("mt")) && formatedHor.compareTo("14:00")>0 && formatedHor.compareTo("20:00")<0));
		   
		    //El inspector esta fuera de turno.
		   if(!turno) {
			   JOptionPane.showMessageDialog(new JFrame(), "El inspector esta fuera de turno, el horario actual es : "+formatedHor+" y el dia es : "+getDayStringOld(dateI, new Locale("es")), "Turno",
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
				   		boolean estacionado = false;
				   		boolean multar = false;
				   		String pateneteEst, alturaEstS, calleEst;
				   		int alturaEst;
				   		try {
				   			PreparedStatement consulta = tabla.getConnection().prepareStatement("SELECT * FROM estacionados;", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
				   			consulta.execute();
						   	ResultSet results = consulta.getResultSet();
						   	while(results.next()) {
						   		pateneteEst = results.getString("patente");
						   		alturaEstS = results.getString("altura");
						   		alturaEst = Integer.parseInt(alturaEstS);
						   		calleEst = results.getString("calle");
								if(pateneteEst.equals(patenteI) && alturaEst == alturaActual && calleEst.equals(calleActual))
									estacionado=true;
								else if(pateneteEst.equals(patenteI) && alturaEst != alturaActual && !(calleEst.equals(calleActual)))
									multar=true;
						   	}
						   	
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
				   		//si no esta estacionado labro la multa, insertando una multa a la base de datos.
				   		if(!estacionado || multar) {
				   		
				   		try {
				   			consUbic = tabla.getConnection().prepareStatement("INSERT INTO multa(fecha,hora,patente,id_asociado_con)"+
				   																"VALUES ('"+ formatedDate +"','" + formatedHour + "','" +
				   																patenteI +"','" + id_as + "')"
				   																, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
				   			consUbic.execute();	
				   			
				   			//Recupero el numero de multa luego de la insercion de dicha multa.
				   			multas = tabla.getConnection().prepareStatement("SELECT * FROM multa;", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
				   			multas.execute();
						   	ResultSet resmul = multas.getResultSet();
						   	while(resmul.next()) {
						   		int numMul = resmul.getInt("numero");
						   		String feMul = resmul.getString("fecha");
						   		String hoMul = resmul.getString("hora");
						   		String pat = resmul.getString("patente");
						   		int idMul = resmul.getInt("id_asociado_con");
						   		if((feMul.equals(formatedDate)) && (hoMul.equals(formatedHour)) && (pat.equals(patenteI)) && (idMul == id_as)){
						   			nMulta = numMul;
						   		}
						   				
								
						   	}
				   			
				   			
				   			
				   			
				   			//Inserto en la tabla temporal de multas las multa que labro
				   			insert = tabla.getConnection().prepareStatement("INSERT INTO MULTILLAS(numero_de_multa,fecha,hora,calle,altura,patente_del_auto,legajo_del_inspector) "+
										"VALUES ('"+ nMulta + "','" +
										formatedDate +"','" + formatedHour + "','" +
										calleActual + "','" + alturaActual + "','" +
										patenteI +"','" + legajo + "')"
										, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
				   			insert.execute();	
				   			
				   			
				   		} catch (SQLException e) {
				   			// TODO Auto-generated catch block
				   			e.printStackTrace();
				   		}
				   		}
				  
				   
				   
				   
				   	}
			   }
	   }
	   
	 refrescarTabla();
	 btnLabrarMultas.setEnabled(false);
	 model.clear();
   }
}
