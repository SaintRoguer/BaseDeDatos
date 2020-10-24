package parquimetros;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import java.sql.Types;

//import java.sql.ResultSet;
import java.sql.SQLException;

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
import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import javax.swing.JList; 


@SuppressWarnings("serial")
public class VentanaInspector extends javax.swing.JInternalFrame 
{

   
   
   public VentanaInspector() 
   {
      super();
      getContentPane().setLayout(new MigLayout("", "[][][][][][][][][][][][][][][][][][][][][][][grow]", "[grow][][][][][][]"));
      
      JButton btnNewButton = new JButton("Ingresear credenciales");
      btnNewButton.addActionListener(new ActionListener() {
      	public void actionPerformed(ActionEvent arg0) {
      	}
      });
      
      JButton btnSeleccionarUbicacion = new JButton("Seleccionar Ubicacion");
      getContentPane().add(btnSeleccionarUbicacion, "cell 0 0");
      getContentPane().add(btnNewButton, "cell 8 0,aligny top");
      
      JScrollPane scrollPane = new JScrollPane();
      getContentPane().add(scrollPane, "cell 10 0 13 1,grow");
      
      JLabel lblPatentesSinCargar = new JLabel("Patentes sin cargar");
      scrollPane.setColumnHeaderView(lblPatentesSinCargar);
      
      JList list = new JList();
      scrollPane.setViewportView(list);
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
      } catch (Exception e) {
         e.printStackTrace();
      }
   }

   private void thisComponentHidden(ComponentEvent evt) 
   {
      this.desconectarBD();
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

   
}
