package utils.server;

import java.util.ArrayList;
import java.net.Socket;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.lang.ClassNotFoundException;
import java.lang.InterruptedException;
import gui.server.ServerMenu;
import gui.server.ConnectedUser;


public class UserListHandler implements Runnable {

    //Attributes
    private Socket socket;


    //Constructor
    public UserListHandler(Socket socket) {
	this.socket = socket;
    }


    //Methods
    @Override
    public void run() {

	    try {
		ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());

		while(!socket.isClosed()) {
		    ArrayList<ConnectedUser> a = ServerMenu.users;
		    synchronized(a){
			a.notifyAll();
			for(ConnectedUser i : a)
			    output.writeObject(i.getUsername());
			a.wait();
		    }
		}
	    } catch(IOException e) {
		System.out.println(e);
	    } catch(InterruptedException e) {
		System.out.println(e);
	    }
    }

}
