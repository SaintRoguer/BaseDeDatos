package parquimetros;

import java.awt.EventQueue;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JList;

public class VentanaListaColumnas extends JDialog{

	private String[] columnas;

	/**
	 * Create the application.
	 */
	public VentanaListaColumnas(String[] arrayColumnas) {
		setResizable(false);
		this.columnas = arrayColumnas;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		this.setBounds(100, 100, 252, 302);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		getContentPane().setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 226, 251);
		getContentPane().add(scrollPane);
		
		JLabel lblColumnas = new JLabel("Atributos");
		scrollPane.setColumnHeaderView(lblColumnas);
		
		JList list = new JList(columnas);
		scrollPane.setViewportView(list);
	}

}
