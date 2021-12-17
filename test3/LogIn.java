package gui.client;

import java.net.InetAddress;
import java.net.UnknownHostException;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.Color;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import utils.Data;
import utils.client.User;


public final class LogIn extends JFrame implements ActionListener {

		//Attributes
		private JPanel panel;
		private JLabel label1;
		private JLabel label2;
		private JLabel label3;
		private JLabel label4;
		private JLabel warningLabel;
		private JLabel versionlabel;
		private JTextField textfield1;
		private JPasswordField passwordField;
		private JButton button1;
		private JButton button2;


		//Constructor
		public LogIn() {
				setTitle("FlyFast - Log In or Sign Up");

				panel = new JPanel();
				panel.setBounds(0,0,450,120);

				label1 = new JLabel("FlyFast");
				label1.setFont(new Font("Serif", Font.BOLD, 70));
				label1.setForeground(new Color(40,170,170));

				panel.add(label1,BorderLayout.CENTER);

				label2 = new JLabel("Usuario");
				label2.setBounds(125,134,55,10);

				textfield1 = new JTextField();
				textfield1.setBounds(125,145,200,40);

				label3 = new JLabel("Contraseña");
				label3.setBounds(125,204,85,10);

				passwordField = new JPasswordField();
				passwordField.setBounds(125,215,200,40);

				warningLabel = new JLabel("Usuario/Contraseña incorrecto(s)");
				warningLabel.setBounds(105,260,250,20);
				warningLabel.setForeground(Color.RED);
				warningLabel.setVisible(false);

				button1 = new JButton("Iniciar Sesión");
				button1.setBounds(125,293,200,40);
				button1.addActionListener(this);

				label4 = new JLabel("O");
				label4.setBounds(222,340,15,10);

				button2 = new JButton("Registrarse");
				button2.setBounds(125,357,200,40);
				button2.addActionListener(this);

				versionlabel = new JLabel("Ver. 0.1.0");
				versionlabel.setBounds(370,445,70,10);

				add(panel);
				add(label2);
				add(textfield1);
				add(label3);
				add(passwordField);
				add(button1);
				add(warningLabel);
				add(label4);
				add(button2);
				add(versionlabel);

				setSize(450,500);
				setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				setResizable(false);
				setLayout(null);
				setVisible(true);
		}


		//Methods
		public String getUserIP() {
				String ip = "";

				try {
						InetAddress localhost = InetAddress.getLocalHost();
						ip = localhost.getHostAddress().toString();
				} catch(UnknownHostException e) {
						System.out.println(e.getMessage());
				}

				return ip;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
				if(e.getSource() == button1) {
						String username = textfield1.getText();
						String password = String.valueOf(passwordField.getPassword());

						Data data = new Data(username,password);

						String tmp = data.verifyUser();

						if(tmp.equals("false")) {
								warningLabel.setVisible(true);
								passwordField.setText("");
						}
						else {
								warningLabel.setVisible(false);
								User user = new User(username,getUserIP());
								dispose();
						}

				} else if(e.getSource() == button2) {
						SignUp signup = new SignUp();
				}
		}

}
