package parquimetros;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.xml.bind.DatatypeConverter;

import quick.dbtable.DBTable;

import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;

public class IngresarLegajo extends javax.swing.JDialog {
	
	//private JFrame frmIngresarCredenciales;
	private JTextField textUsuario;
	private JPasswordField passwordField;
	private DBTable tabla;
	private VentanaInspector inspector;
	
	public IngresarLegajo(DBTable tabla, VentanaInspector insp) {
		setModal(true);
		initialize();
		this.tabla = tabla;
		this.inspector = insp;
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
		
		JLabel lblUsuario = new JLabel("Nro Legajo:");
		lblUsuario.setBounds(26, 33, 71, 14);
		this.getContentPane().add(lblUsuario);
		
		JLabel lblContrasea = new JLabel("Contrasenia:");
		lblContrasea.setBounds(26, 72, 71, 14);
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
		this.setVisible(false);	
	}
		
	private void conectar() {
		boolean success = false;
		String userDB;
		String hash;
		String userIngresado = textUsuario.getText();
		char[] pwArray = passwordField.getPassword();
		String pwIngresado = String.copyValueOf(pwArray);
		
		
		
		try {
			MessageDigest md;
			byte[] digest;
			String myHash;
        	md = MessageDigest.getInstance("MD5");
        	md.update(pwIngresado.getBytes());
        	digest = md.digest();
        	//myHash = DatatypeConverter.printHexBinary(digest).toUpperCase();
        	//myHash = Base64.getEncoder().encodeToString(digest).toUpperCase();
        	BigInteger bigInt = new BigInteger(1,digest);
        	myHash = bigInt.toString(16).toUpperCase();
			
			PreparedStatement consulta = tabla.getConnection().prepareStatement("SELECT legajo, password FROM Inspectores;", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			consulta.execute();	
			ResultSet resultados = consulta.getResultSet();

			
			while(resultados.next() && !success) {
				userDB = resultados.getString("legajo");
            	hash = resultados.getString("password").toUpperCase();
            	success = (myHash.equals(hash)) && (userDB.equals(userIngresado));
			}
			
			if(!success) {
				JOptionPane.showMessageDialog(null, "La acreditacion fallo, el Nro de legajo o la contrasenia es incorrecta.", "Acreditacion fallida", JOptionPane.INFORMATION_MESSAGE);
			}
			else {
				JOptionPane.showMessageDialog(null, "La acreditacion fue exitosa.", "Acreditacion exitosa", JOptionPane.INFORMATION_MESSAGE);
				inspector.legValido(this);
				this.setVisible(false); 
			}
		
			System.out.println();
	
		
		} 
		catch (SQLException e) {e.printStackTrace();}
		catch (NoSuchAlgorithmException e) {e.printStackTrace();}		
	}
		
	public String getLegajo() {
		return textUsuario.getText();
	}

}
