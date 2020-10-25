package parquimetros;
import javax.swing.JMenuBar;

import quick.dbtable.DBTable;

public class menuUbicacion extends javax.swing.JDialog{
	
	private DBTable tabla;
	
	public menuUbicacion(DBTable tabla) {
		setModal(true);
		initialize();
		this.tabla = tabla;
	}
	private void initialize() {
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
	}
	
	

}
