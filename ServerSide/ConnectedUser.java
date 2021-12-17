package gui.server;

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
    private String ip;
    private JLabel usernameLabel;
    private JButton disconnectButton;
    private Border blackline;


    //Constructor
    public ConnectedUser(String username, String ip) {
	this.username = username;
	this.ip = ip;

	usernameLabel = new JLabel(username);
	usernameLabel.setBounds(5,0,((username.length()-1)*12),30);

	disconnectButton = new JButton("Kill");
	disconnectButton.setBounds(170,5,55,20);

	blackline = BorderFactory.createLineBorder(Color.black);

	add(usernameLabel);
	add(disconnectButton);

	setSize(232,30);
	setLayout(null);
	setBorder(blackline);
    }


    //Getters
    public String getUsername() {
	return username;
    }

    public String getIP() {
	return ip;
    }


    //Methods
    @Override
    public void actionPerformed(ActionEvent e) {
    }

}
