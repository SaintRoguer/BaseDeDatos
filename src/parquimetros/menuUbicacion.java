package parquimetros;
import javax.swing.JMenuBar;

import quick.dbtable.DBTable;

public class menuUbicacion extends javax.swing.JDialog{
	
	private DBTable tabla;
	
	private JMenuBar menuBar;
	
	
	public menuUbicacion(DBTable tabla) {
		setModal(true);
		initialize();
		this.tabla = tabla;
		
	}
	private void initialize() {
		
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
	}
	
	

}
