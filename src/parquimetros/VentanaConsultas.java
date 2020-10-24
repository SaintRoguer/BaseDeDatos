package parquimetros;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import java.sql.Types;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
//import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.BorderFactory;
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
import javax.swing.JList;
import javax.swing.JLabel; 


@SuppressWarnings("serial")
public class VentanaConsultas extends javax.swing.JInternalFrame 
{
   private JPanel pnlConsulta;
   private JTextArea txtConsulta;
   private JButton botonBorrar;
   private JButton btnEjecutar;
   private DBTable tabla;    
   private JScrollPane scrConsulta;
   private JButton btnIngresarCredenciales;
   private JList listTablas;
   private JScrollPane scrTablas;

   
   
   public VentanaConsultas() 
   {
      super();
      initGUI();
   }
   
   private void initGUI() 
   {
      try {
         setPreferredSize(new Dimension(800, 600));
         this.setBounds(0, 0, 800, 600);
         setVisible(true);
         this.setTitle("Consultas (Utilizando DBTable)");
         this.setClosable(true);
         this.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
         this.setMaximizable(true);
         this.addComponentListener(new ComponentAdapter() {
            public void componentHidden(ComponentEvent evt) {
               thisComponentHidden(evt);
            }
            /*
            public void componentShown(ComponentEvent evt) {
               thisComponentShown(evt);
            }*/
         });
         getContentPane().setLayout(null);
         {
            pnlConsulta = new JPanel();
            pnlConsulta.setBounds(0, 0, 784, 186);
            getContentPane().add(pnlConsulta);
            pnlConsulta.setLayout(null);
            {
               scrConsulta = new JScrollPane();
               scrConsulta.setBounds(5, 5, 486, 176);
               pnlConsulta.add(scrConsulta);
               {
                  txtConsulta = new JTextArea();
                  txtConsulta.setEnabled(false);
                  scrConsulta.setViewportView(txtConsulta);
                  txtConsulta.setTabSize(3);
                  txtConsulta.setColumns(80);
                  txtConsulta.setBorder(BorderFactory.createEtchedBorder(BevelBorder.LOWERED));
                  txtConsulta.setText("Por favor, ingresar el usuario y contrasenia para realizar consultas");
                  txtConsulta.setFont(new java.awt.Font("Monospaced",0,12));
                  txtConsulta.setRows(10);
               }
            }
            {
            	btnIngresarCredenciales = new JButton("Ingresar Credenciales");
            	btnIngresarCredenciales.setBounds(501, 11, 137, 23);
            	btnIngresarCredenciales.addActionListener(new ActionListener() {
            		public void actionPerformed(ActionEvent e) {
            			ejecutarIngresarCredenciales();
            		}
            	});
            	pnlConsulta.add(btnIngresarCredenciales);
            }
            {
               btnEjecutar = new JButton();
               btnEjecutar.setBounds(501, 45, 73, 23);
               btnEjecutar.setVisible(false);
               pnlConsulta.add(btnEjecutar);
               btnEjecutar.setText("Ejecutar");
               btnEjecutar.addActionListener(new ActionListener() {
                  public void actionPerformed(ActionEvent evt) {
                     btnEjecutarActionPerformed(evt);
                  }
               });
            }
            {
            	botonBorrar = new JButton();
            	botonBorrar.setBounds(584, 45, 63, 23);
            	botonBorrar.setVisible(false);
            	pnlConsulta.add(botonBorrar);
            	botonBorrar.setText("Borrar");            
            	
            	
            }
            
            scrTablas = new JScrollPane();
            scrTablas.setBounds(501, 79, 206, 96);
            pnlConsulta.add(scrTablas);
            
            
            JLabel lblTablas = new JLabel("Tablas");
            scrTablas.setColumnHeaderView(lblTablas);
        	botonBorrar.addActionListener(new ActionListener() {
        		public void actionPerformed(ActionEvent arg0) {
        		  txtConsulta.setText("");            			
        		}
        	});
        
			
         }
         {
        	// crea la tabla  
        	tabla = new DBTable();
        	tabla.setBounds(0, 186, 784, 384);
        	
        	// Agrega la tabla al frame (no necesita JScrollPane como Jtable)
            getContentPane().add(tabla);           
                      
           // setea la tabla para sólo lectura (no se puede editar su contenido)  
           tabla.setEditable(false);       
           
           
           
         }
      } catch (Exception e) {
         e.printStackTrace();
      }
   }
   
   private void thisComponentHidden(ComponentEvent evt) 
   {
      this.desconectarBD();
   }

   private void btnEjecutarActionPerformed(ActionEvent evt) 
   {
      this.refrescarTabla();      
   }
   
   private void desconectarBD()
   {
         try
         {
            tabla.close();            
         }
         catch (SQLException ex)
         {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
         }      
   }

   private void refrescarTabla()
   {
      try
      {    
    	 // seteamos la consulta a partir de la cual se obtendrán los datos para llenar la tabla
    	 tabla.setSelectSql(this.txtConsulta.getText().trim());

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
   

	private void ejecutarIngresarCredenciales() {
		JDialog IngCred = new IngresarCredenciales(tabla);
		IngCred.setVisible(true);
		try {
			if(tabla.getConnection().isValid(5)) {
				btnIngresarCredenciales.setVisible(false);
				btnEjecutar.setVisible(true);
				botonBorrar.setVisible(true);
				txtConsulta.setEnabled(true);
				txtConsulta.setText("");
				listarTablas();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void listarTablas() {
		try {
			PreparedStatement consulta = tabla.getConnection().prepareStatement("show tables;", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			consulta.execute();
			ResultSet resultados = consulta.getResultSet();
			int i=0;
			while(resultados.next())
				i++;
			
			resultados.beforeFirst();
			String[] arrayTablas = new String[i];
			for(int j=0; j<i; j++) {
				resultados.next();
				arrayTablas[j] = resultados.getString(1);
			}
			
        	botonBorrar.addActionListener(new ActionListener() {
        		public void actionPerformed(ActionEvent arg0) {
        		  txtConsulta.setText("");            			
        		}
        	});
        	

            listTablas = new JList(arrayTablas);
            scrTablas.setViewportView(listTablas);
			
			
		} catch (SQLException ex) {
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
}
