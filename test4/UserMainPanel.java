package gui.client;

import java.net.Socket;
import java.net.ServerSocket;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.io.IOException;
import java.lang.ClassNotFoundException;
import java.lang.InterruptedException;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.Font;
import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import utils.Message;


public class UserMainPanel extends JFrame implements ActionListener {

		//Attributes
		private UserMainPanel reference;
		private JPanel listOfUsers;
		private JPanel chat;
		private JPanel displayUsername;
		private JPanel messagesPanel;
		private JLabel usernameLabel;
		private JScrollPane scrollpane;
		private JScrollPane messagesScroll;
		private JTextArea messages;
		private JTextField writeMessage;
		private JButton sendButton;
		private ArrayList<ConnectedUser> users;


		//Constructor
		public UserMainPanel() {
				reference = this;
				users = new ArrayList<ConnectedUser>();

				setTitle("Online Users");

				listOfUsers = new JPanel();
				listOfUsers.setPreferredSize(new Dimension(182,30));
				listOfUsers.setLayout(null);

				scrollpane = new JScrollPane(listOfUsers);
				scrollpane.setBounds(0,0,200,468);
				scrollpane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

				chat = new JPanel();
				chat.setBounds(200,0,500,468);
				chat.setLayout(null);
				chat.setVisible(false);

				displayUsername = new JPanel();
				displayUsername.setBounds(0,0,500,50);
				displayUsername.setBackground(new Color(50,50,50));
				chat.add(displayUsername);

				messagesPanel = new JPanel();
				messagesPanel.setBounds(0,50,500,360);
				chat.add(messagesPanel);

				messages = new JTextArea(23,43);

				messagesScroll = new JScrollPane(messages);
				messagesPanel.add(messagesScroll,BorderLayout.CENTER);

				usernameLabel = new JLabel();
				usernameLabel.setFont(new Font("Sefir", Font.BOLD,25));
				usernameLabel.setForeground(new Color(255,255,255));
				displayUsername.add(usernameLabel);

				writeMessage = new JTextField();
				writeMessage.setBounds(10,420,410,35);
				chat.add(writeMessage);

				sendButton = new JButton("=>");
				sendButton.setBounds(430,420,60,34);
				sendButton.setVisible(true);
				sendButton.addActionListener(this);
				chat.add(sendButton);

				add(scrollpane);
				add(chat);

				setSize(700,500);
				setResizable(false);
				setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				setLayout(null);
				setVisible(true);

				/*Thread receiveMessageFrom = new Thread(new Runnable()
				{
						@Override
						public void run() {
								String msg;

								try {
										ServerSocket ss = new ServerSocket(9989);
										Socket socket1;

										while(true) {
												socket1 = ss.accept();

												try {
														ObjectInputStream receiveMessage = new ObjectInputStream(socket1.getInputStream());

														while((msg = (String)receiveMessage.readObject()) == null) {
																try {
																		wait();
																} catch(InterruptedException e) {
																		System.out.println(e.getMessage());
																}
														}

														messages.append("\nÉl: "+msg);
												} catch(ClassNotFoundException e) {
														System.out.println(e.getMessage());
												}
										}

								} catch(IOException e) {
										System.out.println(e.getMessage());
								}
						}
				});*/

				//receiveMessageFrom.start();

		}


		//Methods
		public void activeChatArea(String username) {
				setTitle("Chat - "+username);
				usernameLabel.setText(username);
				chat.setVisible(true);

				Thread receiveMessageFrom = new Thread(new Runnable()
				{
						@Override
						public void run() {
								String msg;

								try {
										ServerSocket ss = new ServerSocket(9989);
										Socket socket1;

										while(true) {
												socket1 = ss.accept();

												try {
														ObjectInputStream receiveMessage = new ObjectInputStream(socket1.getInputStream());

														while((msg = (String)receiveMessage.readObject()) == null) {
																try {
																		wait();
																} catch(InterruptedException e) {
																		System.out.println(e.getMessage());
																}
														}

														messages.append("\nÉl: "+msg);
												} catch(ClassNotFoundException e) {
														System.out.println(e.getMessage());
												}

										}

								} catch(IOException e) {
										System.out.println(e.getMessage());
								}
						}
				});

				receiveMessageFrom.start();

		}


		//Methods
		public void addUserToList(String username, int number) {
				if(number == 0) {
						ConnectedUser tmp = new ConnectedUser(username,reference);
						tmp.setLocation(0,0);
						listOfUsers.add(tmp);
						listOfUsers.repaint();
				} else {
						listOfUsers.setPreferredSize(new Dimension(182,((number+1)*30)));
						ConnectedUser tmp = new ConnectedUser(username,reference);
						tmp.setLocation(0,number*30);
						listOfUsers.add(tmp);
						listOfUsers.repaint();
				}
		}

		public boolean isInList(String username) {
				for(ConnectedUser i : users) {
						if(i.getUsername().equals(username))
								return true;
				}

				return false;
		}

		public void initUser(String username, String ip) {
				System.out.println(ip);
				Thread activeusers = new Thread(new Runnable()
				{
						@Override
						public void run() {
								String data;
								Message userData = new Message();

								userData.setUsername(username);
								userData.setIP(ip);

								try {
										Socket socket = new Socket("18.235.33.196",6666);

										ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
										output.writeObject(userData);

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
										}

								} catch(IOException e) {
										System.out.println(e);
								}
						}
										
				});

				activeusers.start();
		}

		@Override
		public void actionPerformed(ActionEvent e) {
				if(e.getSource() == sendButton) {
						messages.append("\nTú: "+writeMessage.getText());

						Message message = new Message();
						message.setUsername(usernameLabel.getText());
						message.setMessage(writeMessage.getText());

						Thread sendMessageTo = new Thread(new Runnable()
						{
								@Override
								public void run() {
										try {
												Socket socket = new Socket("18.235.33.196",9999);

												ObjectOutputStream sendMsg = new ObjectOutputStream(socket.getOutputStream());
												sendMsg.writeObject(message);
												socket.close();
										} catch(IOException e) {
												System.out.println(e.getMessage());
										}
								}
						});

						sendMessageTo.start();
				}
		}

}
