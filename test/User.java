package utils.client;

import gui.client.UserMainPanel;


public final class User {

		//Attributes
		private String username;
		private String ip;
		private UserMainPanel chatPanel;


		//Constructor
		public User(String username, String ip) {
				this.username = username;
				this.ip = ip;

				chatPanel = new UserMainPanel();
				chatPanel.initUser(username);
		}

}
