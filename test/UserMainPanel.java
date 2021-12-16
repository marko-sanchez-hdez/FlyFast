package gui.client;

import java.net.Socket;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.io.IOException;
import java.lang.ClassNotFoundException;
import java.lang.InterruptedException;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import java.awt.Dimension;


public class UserMainPanel extends JFrame {

		//Attributes
		private JPanel listOfUsers;
		private JScrollPane scrollpane;
		private ArrayList<ConnectedUser> users;


		//Constructor
		public UserMainPanel() {
				users = new ArrayList<ConnectedUser>();

				setTitle("Chat");

				listOfUsers = new JPanel();
				listOfUsers.setPreferredSize(new Dimension(182,30));
				listOfUsers.setLayout(null);

				scrollpane = new JScrollPane(listOfUsers);
				scrollpane.setBounds(0,0,200,484);
				scrollpane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

				add(scrollpane);

				setSize(700,500);
				setResizable(false);
				setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				setLayout(null);
				setVisible(true);
		}


		//Methods
		public void addUserToList(String username, int number) {
				if(number == 0) {
						ConnectedUser tmp = new ConnectedUser(username);
						tmp.setLocation(0,0);
						listOfUsers.add(tmp);
				} else {
						listOfUsers.setPreferredSize(new Dimension(182,((number+1)*30)));
						ConnectedUser tmp = new ConnectedUser(username);
						tmp.setLocation(0,number*30);
						listOfUsers.add(tmp);
				}
		}

		public boolean isInList(String username) {
				for(ConnectedUser i : users) {
						if(i.getUsername().equals(username))
								return true;
				}

				return false;
		}

		public void initUser(String username) {
				Thread activeusers = new Thread(new Runnable()
				{
						@Override
						public void run() {
								String data;

								try {
										Socket socket = new Socket("18.235.33.196",6666);

										ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
										output.writeObject(username);

										while(true) {
												try {
														ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
														
														int i = 0;

														while((data = (String)input.readObject()) != null) {
																if(users.size() == 0) {
																		if(data.equals(username))
																				continue;
																		else
																				addUserToList(data,i);
																} else {
																		i = users.size();
																		if(isInList(data))
																				continue;
																		else
																				addUserToList(data,i);
																}
														}

												} catch(ClassNotFoundException ex) {
														System.out.println(ex);
												}
												//Thread.sleep(10000);
										}

								} catch(IOException e) {
										System.out.println(e);
								} /*catch(InterruptedException e) {
										System.out.println(e.getMessage());
								}*/
						}
										
				});

				activeusers.start();
		}

}
