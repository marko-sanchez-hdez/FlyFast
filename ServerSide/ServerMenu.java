package gui.server;

import java.net.ServerSocket;
import java.net.Socket;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.io.IOException;
import java.lang.ClassNotFoundException;
import java.lang.InterruptedException;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;
import java.awt.Dimension;
import utils.server.UserListHandler;
import utils.Message;


public final class ServerMenu extends JFrame {

    //Attributes
    private int userCounter;
    public static ArrayList<ConnectedUser> users;
    private JPanel listOfUsers;
    private JScrollPane scrollpane;


    //Constructor
    public ServerMenu() {
	userCounter = 0;
	users = new ArrayList<ConnectedUser>();

	setTitle("FlyFast - Users");

	listOfUsers = new JPanel();
	listOfUsers.setPreferredSize(new Dimension(232,30));
	listOfUsers.setLayout(null);

	scrollpane = new JScrollPane(listOfUsers);
	scrollpane.setBounds(0,0,250,377);
	scrollpane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

	add(scrollpane,BorderLayout.CENTER);

	setSize(250,400);
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	setResizable(false);
	setLayout(null);
	setVisible(true);
    }


    //Getters
    public ArrayList<ConnectedUser> getUsers() {
	return users;
    }


    //Methods
    public void addUserToMenu(String username, String ip, int number) {
	if(number == 0) {
	    ConnectedUser tmp = new ConnectedUser(username,ip);
	    tmp.setLocation(0,0);
	    users.add(tmp);
	    listOfUsers.add(tmp);
	    listOfUsers.repaint();
	} else {
	    listOfUsers.setPreferredSize(new Dimension(232,((number+1)*30)));
	    ConnectedUser tmp = new ConnectedUser(username,ip);
	    tmp.setLocation(0,number*30);
	    users.add(tmp);
	    listOfUsers.add(tmp);
	    listOfUsers.repaint();
	}
    }

    public boolean isInList(String username) {
	for(ConnectedUser i : users) {
	    if(i.getUsername() == username)
		return true;
	}

	return false;
    }

    public void startServer() {
	try {
	    ServerSocket ss = new ServerSocket(6666);
	    ServerSocket inComingMessages = new ServerSocket(9999);
	Thread connectuser = new Thread(new Runnable()
	{
	    @Override
	    public void run() {
		Socket socket;
		Message userData = new Message();

		try {

		    while(true) {
			socket = ss.accept();

			try {
			    ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
			    userData = (Message)input.readObject();
			} catch(ClassNotFoundException ex) {
			    System.out.println(ex.getMessage());
			}

			    if(isInList(userData.getUsername()))
				continue;
			    else {
				addUserToMenu(userData.getUsername(),userData.getIP(),userCounter);
				userCounter++;

				UserListHandler tmp = new UserListHandler(socket);
				Thread thread = new Thread(tmp);
				thread.start();
			    }

		    }

		} catch(IOException e) {
		    System.out.println(e.getMessage());
		}

	    }
	});

	Thread passMessage = new Thread(new Runnable()
	{
	    @Override
	    public void run() {
		Socket socket1;
		Message message = new Message();

		try {

		    while(true) {
			socket1 = inComingMessages.accept();

			try {
			    ObjectInputStream msgFrom = new ObjectInputStream(socket1.getInputStream());
			    message = (Message)msgFrom.readObject();
			} catch(ClassNotFoundException ex) {
			    System.out.println(ex.getMessage());
			}

			String ip = "";

			for(ConnectedUser j : users) {
			    if(j.getUsername().equals(message.getUsername()))
				ip = j.getIP();
			}

			Socket bridge = new Socket(ip,9989);

			ObjectOutputStream msgTo = new ObjectOutputStream(bridge.getOutputStream());
			msgTo.writeObject(message.getMessage());

			bridge.close();
			socket1.close();

		    }

		} catch(IOException e) {
		    System.out.println(e.getMessage());
		}

	    }
	});


	connectuser.start();
	passMessage.start();

	} catch(IOException exe) {
	    System.out.println(exe.getMessage());
	}
    }

}
