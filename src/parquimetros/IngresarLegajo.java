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
			
			String user = textUsuario.getText();
			int leg = Integer.parseInt(user);
			char[] pwArray = passwordField.getPassword();
			String pw = String.copyValueOf(passwordField.getPassword());
			int num=-1;
		    String contra="";
			try
				{
				  	String driver ="com.mysql.cj.jdbc.Driver";
				  	String servidor = "localhost:3306";
		          	String baseDatos = "parquimetros"; 
		          	String usuario = "inspector";
		          	String clave = "inspector";
		          	String uriConexion = "jdbc:mysql://" + servidor + "/" + 
		        	baseDatos +"?serverTimezone=America/Argentina/Buenos_Aires"; 
		            tabla.connectDatabase(driver, uriConexion, usuario, clave);
		            java.sql.Connection cnx;
		            cnx = java.sql.DriverManager.getConnection(uriConexion, usuario, clave);
		            // Se crea una sentencia jdbc para realizar la consulta
		            java.sql.Statement stmt = cnx.createStatement();
					// Se prepara el string SQL de la consulta
		            String sql = "SELECT legajo, password FROM Inspectores";
		            // Se ejecuta la sentencia y se recibe un resultado
		            java.sql.ResultSet rs = stmt.executeQuery(sql);
		            // Se recorre el resultado
		            // Busca si esta el N° legajo en la base de datos
		            while (rs.next()){
		            	int legajo = rs.getInt("legajo");
		            	String pass = rs.getString("password");
		            	if(legajo == leg) {
		            		num=legajo;
		            		contra=pass;
		            	}
		            }
		            
				rs.close();
				stmt.close();
				}
				catch (java.sql.SQLException ex) {}
				catch(ClassNotFoundException ex) {}
			//Si encontro el legajo y el password es correcto, infroma que es exitosa la acreditacion y sale.
			if(num>=0) {
				if(contra.equals(pw)) {
					JOptionPane.showMessageDialog(null, "La acreditacion fue exitosa.", "Acreditacion exitosa", JOptionPane.INFORMATION_MESSAGE);
		            this.dispose();
				}
			}
			else
				JOptionPane.showMessageDialog(null, "La acreditacion fallo, el N° de legajo o la contrasenia es incorrecta.", "Acreditacion exitosa", JOptionPane.INFORMATION_MESSAGE);
			
				
	}
			
			

}
