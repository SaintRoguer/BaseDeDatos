package parquimetros;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class IngresarCredenciales extends javax.swing.JFrame{

	//private JFrame frmIngresarCredenciales;
	private JTextField textField;
	private JPasswordField passwordField;

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
	 */
	public IngresarCredenciales() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		//frmIngresarCredenciales = new JFrame();
		this.setTitle("Ingresar Credenciales");
		this.setResizable(false);
		this.setBounds(100, 100, 356, 206);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getContentPane().setLayout(null);
		
		JLabel lblUsuario = new JLabel("Usuario:");
		lblUsuario.setBounds(57, 33, 40, 14);
		this.getContentPane().add(lblUsuario);
		
		JLabel lblContrasea = new JLabel("Contrase\u00F1a:");
		lblContrasea.setBounds(37, 72, 60, 14);
		this.getContentPane().add(lblContrasea);
		
		textField = new JTextField();
		textField.setBounds(107, 30, 196, 20);
		this.getContentPane().add(textField);
		textField.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(107, 69, 196, 20);
		this.getContentPane().add(passwordField);
		
		JButton btnAceptar = new JButton("Aceptar");
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
}
