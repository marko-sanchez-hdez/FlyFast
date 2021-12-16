package gui.client;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.border.Border;
import javax.swing.BorderFactory;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class ConnectedUser extends JPanel implements ActionListener {

		//Attributes
		private String username;
		private JLabel usernameLabel;
		private JButton chatButton;
		private Border blackline;


		//Constructor
		public ConnectedUser(String username) {
				this.username = username;

				usernameLabel = new JLabel(username);
				usernameLabel.setBounds(5,0,((username.length()-1)*12),30);

				chatButton = new JButton("Chat");
				chatButton.setBounds(100,5,70,20);
				chatButton.addActionListener(this);

				blackline = BorderFactory.createLineBorder(Color.black);

				add(usernameLabel);
				add(chatButton);

				setSize(182,30);
				setLayout(null);
				setBorder(blackline);
		}


		//Getters
		public String getUsername() {
				return username;
		}


		//Methods
		@Override
		public void actionPerformed(ActionEvent e) {
		}

}
