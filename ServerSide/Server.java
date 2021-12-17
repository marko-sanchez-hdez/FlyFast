package utils.server;

import gui.server.ServerMenu;


public final class Server {

    //Attributes
    private ServerMenu mainmenu;


    //Constructor
    public Server() {
	mainmenu = new ServerMenu();
	mainmenu.startServer();
    }

}
