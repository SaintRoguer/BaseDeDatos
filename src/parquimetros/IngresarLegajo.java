package parquimetros;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import quick.dbtable.DBTable;

import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class IngresarLegajo extends javax.swing.JDialog {
	
	//private JFrame frmIngresarCredenciales;
	private JTextField textUsuario;
	private JPasswordField passwordField;
	private DBTable tabla;
	
	public IngresarLegajo(DBTable tabla) {
		setModal(true);
		initialize();
		this.tabla = tabla;
	}
			
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		//frmIngresarCredenciales = new JFrame();
		this.setTitle("Ingresar Credenciales");
		this.setResizable(false);
		this.setBounds(100, 100, 356, 206);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.getContentPane().setLayout(null);
		
		JLabel lblUsuario = new JLabel("N° Legajo:");
		lblUsuario.setBounds(46, 33, 51, 14);
		this.getContentPane().add(lblUsuario);
		
		JLabel lblContrasea = new JLabel("Contrase\u00F1a:");
		lblContrasea.setBounds(37, 72, 60, 14);
		this.getContentPane().add(lblContrasea);
		
		textUsuario = new JTextField();
		textUsuario.setBounds(107, 30, 196, 20);
		this.getContentPane().add(textUsuario);
		textUsuario.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(107, 69, 196, 20);
		this.getContentPane().add(passwordField);
		
		JButton btnAceptar = new JButton("Aceptar");
		btnAceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				conectar();
			}
		});
		btnAceptar.setBounds(194, 118, 89, 32);
		this.getContentPane().add(btnAceptar);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				cerrarVentana();
			}
		});
		btnCancelar.setBounds(76, 118, 89, 32);
		this.getContentPane().add(btnCancelar);
	}
	

	private void cerrarVentana() {
		this.dispose();	
	}
		
	private void conectar() {
			int num=-1;
			String contra="";
			String user = textUsuario.getText();
			int leg = Integer.parseInt(user);
			char[] pwArray = passwordField.getPassword();
			String pw = String.copyValueOf(pwArray);
			
			
			
			try {
				PreparedStatement consulta = tabla.getConnection().prepareStatement("SELECT legajo, password FROM Inspectores;", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
				consulta.execute();	
				ResultSet resultados = consulta.getResultSet();
				while(resultados.next()) {
					int legajo = resultados.getInt("legajo");
	            	String pass = resultados.getString("password");
	            	if(legajo == leg) {
	            		num=legajo;
	            		contra=pass;
	            	}
					
				}
			
			
			
			
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
			
			
			
			//Si encontro el legajo y el password es correcto, infroma que es exitosa la acreditacion y sale.
			if(num>=0) {
				if(contra.equals(pw)) {
					JOptionPane.showMessageDialog(null, "La acreditacion fue exitosa.", "Acreditacion exitosa", JOptionPane.INFORMATION_MESSAGE);
		            this.dispose();
				}
			}
			//sino muestra un mensaje de error.
			else
				JOptionPane.showMessageDialog(null, "La acreditacion fallo, el N° de legajo o la contrasenia es incorrecta.", "Acreditacion fallida", JOptionPane.INFORMATION_MESSAGE);
			
				
	}
			
			

}
