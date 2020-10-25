package parquimetros;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import quick.dbtable.DBTable;

public class menuUbicacion extends javax.swing.JDialog{
	
	private DBTable tabla;
	
	private JMenuBar menuBar; 
	private JMenu mnNewMenu;
	private JMenuItem ubi;
	
	
	public menuUbicacion(DBTable tabla) {
		setModal(true);
		initialize();
		this.tabla = tabla;
		
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		mnNewMenu = new JMenu("New menu");
		menuBar.add(mnNewMenu);
		
	}
	private void initialize() {
		/*
		JMenu mnuUb = new JMenu();
	    menuBar.add(mnuUb);
	    mnuUb.setText("Ubicaciones");
		*/
		

		
			PreparedStatement consulta;
			try {
				consulta = tabla.getConnection().prepareStatement("SELECT calle,altura FROM ubicaciones;", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
				consulta.execute();
				ResultSet resultados = consulta.getResultSet();
				//Creo un item por cada ubicacion.
				while(resultados.next()) {
					String calle = resultados.getString("calle");
					int altura = resultados.getInt("altura");
					    
		            
		            ubi = new JMenuItem();
	                mnNewMenu.add(ubi);
	                ubi.setText("calle:"+calle+"altura:"+altura);
	                ubi.addActionListener(new ActionListener() {
	                     public void actionPerformed(ActionEvent evt) {
	                        
	                     }
	                  });
					
				}
			
			
			
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				
		
	}
	
	

}
