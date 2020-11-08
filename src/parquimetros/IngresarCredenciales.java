package parquimetros;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import quick.dbtable.DBTable;

import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class IngresarCredenciales extends javax.swing.JDialog{

	//private JFrame frmIngresarCredenciales;
	private JTextField textUsuario;
	private JPasswordField passwordField;
	private DBTable tabla;

	/**
	 * Launch the application.
	 *//*
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					IngresarCredenciales window = new IngresarCredenciales();
					window.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}*/

	/**
	 * Create the application.
	 * @param ventanaConsultas 
	 */
	public IngresarCredenciales(DBTable tabla) {
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
		
		JLabel lblUsuario = new JLabel("Usuario:");
		lblUsuario.setBounds(57, 33, 40, 14);
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
    	String usuario = textUsuario.getText();
    	String clave = String.copyValueOf(passwordField.getPassword());
		
		if(usuario.equals("admin") && clave.equals("admin")) {
			
			try {
	            String driver ="com.mysql.cj.jdbc.Driver";
	        	String servidor = "localhost:3306";
	        	String baseDatos = "parquimetros"; 
	            String uriConexion = "jdbc:mysql://" + servidor + "/" + 
	        	                     baseDatos +"?serverTimezone=America/Argentina/Buenos_Aires"; 
	            tabla.connectDatabase(driver, uriConexion, usuario, clave);
	            
	            JOptionPane.showMessageDialog(null, "La conexion fue exitosa.", "Conexion exitosa", JOptionPane.INFORMATION_MESSAGE);
	            
				this.dispose();
				
			} catch (SQLException ex) {
				System.out.println("SQLException: " + ex.getMessage());
				System.out.println("SQLState: " + ex.getSQLState());
				System.out.println("VendorError: " + ex.getErrorCode());
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			
		}
		
		else
			JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(this),
                    "El usuario o contrasenia son incorrectos" + "\n", 
                    "Error de credenciales.",
                    JOptionPane.ERROR_MESSAGE);
		
		
	}
}
