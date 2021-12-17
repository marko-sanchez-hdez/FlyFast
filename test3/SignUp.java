package gui.client;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.Font;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import utils.Data;


public final class SignUp extends JFrame implements ActionListener {

		//Attribute
		private JPanel welcomePanel;
		private JPanel warningPanel;
		private JLabel nameLabel;
		private JLabel usernameLabel;
		private JLabel passwordLabel;
		private JLabel confirmPasswordLabel;
		private JLabel welcomeLabel;
		private JLabel warningLabel;
		private JTextField nameTextField;
		private JTextField usernameTextField;
		private JPasswordField passwordfield;
		private JPasswordField confirmpasswordfield;
		private JButton signUpButton;


		//Constructor
		public SignUp() {
				setTitle("FlyFast - Sign Up");

				nameLabel = new JLabel("Nombre");
				nameLabel.setBounds(75,45,55,10);

				nameTextField = new JTextField();
				nameTextField.setBounds(75,56,200,40);

				usernameLabel = new JLabel("Nombre de Usuario");
				usernameLabel.setBounds(75,102,150,10);

				usernameTextField = new JTextField();
				usernameTextField.setBounds(75,113,200,40);

				passwordLabel = new JLabel("Contraseña");
				passwordLabel.setBounds(75,157,150,10);

				confirmPasswordLabel = new JLabel("Confirmar Contraseña");
				confirmPasswordLabel.setBounds(75,212,200,10);

				passwordfield = new JPasswordField();
				passwordfield.setBounds(75,168,200,40);

				confirmpasswordfield = new JPasswordField();
				confirmpasswordfield.setBounds(75,223,200,40);

				warningPanel = new JPanel();
				warningPanel.setBounds(5,270,340,47);

				warningLabel = new JLabel();
				warningLabel.setForeground(Color.RED);

				warningPanel.add(warningLabel,BorderLayout.CENTER);

				signUpButton = new JButton("Registrarse");
				signUpButton.setBounds(75,325,200,40);
				signUpButton.addActionListener(this);

				welcomePanel = new JPanel();
				welcomePanel.setBounds(0,372,350,45);

				welcomeLabel = new JLabel("¡Bienvenido a FlyFast!");
				welcomeLabel.setFont(new Font("Serif", Font.BOLD, 25));
				welcomeLabel.setForeground(new Color(40,170,170));

				welcomePanel.add(welcomeLabel,BorderLayout.CENTER);

				add(nameLabel,BorderLayout.CENTER);
				add(usernameLabel);
				add(nameTextField);
				add(usernameTextField);
				add(passwordLabel);
				add(confirmPasswordLabel);
				add(passwordfield);
				add(confirmpasswordfield);
				add(warningPanel);
				add(signUpButton);
				add(welcomePanel);

				setSize(350,450);
				setResizable(false);
				setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				setLayout(null);
				setVisible(true);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
				if(e.getSource() == signUpButton) {
						String name = nameTextField.getText();
						String username = usernameTextField.getText();
						String password = String.valueOf(passwordfield.getPassword());
						String cpassword = String.valueOf(confirmpasswordfield.getPassword());

						if(name.equals("") || username.equals("") || password.equals("") || cpassword.equals(""))
								warningLabel.setText("Por favor, llena todos los campos requeridos");
						else {
								Data data = new Data(name,username,password);

								if(data.checkName() == false) {
										warningLabel.setText("Nombre: Se ingresó algún carácter inválido");
								} else {
										if(data.checkUsername() == false) {
												warningLabel.setText("Usuario: Se ingresó algún carácter inválido");
										} else {
												if(password.length()<8)
														warningLabel.setText("Contraseña muy corta : min. 8 caracteres");
												else if(password.length()>30)
														warningLabel.setText("Contraseña muy larga : max. 30 caracteres");
												else {
														if(data.checkPassword() == false)
																warningLabel.setText("No se han ingresado los caracteres solicitados");
														else {
																if(!(password.equals(cpassword))) 
																		warningLabel.setText("Las contraseñas no coinciden");
																else{
																		System.out.println("Next");
																		warningLabel.setForeground(Color.BLACK);
																		warningLabel.setText("¡Registro exitoso!");
																		signUpButton.setEnabled(false);
																		nameTextField.setEnabled(false);
																		usernameTextField.setEnabled(false);
																		passwordfield.setEnabled(false);
																		confirmpasswordfield.setEnabled(false);
																		data.registUser();
																}
														}
												}
										}
								}
						}
				}
		}

}
