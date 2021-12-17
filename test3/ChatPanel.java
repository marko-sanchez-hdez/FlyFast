package gui.client;

import javax.swing.JPanel;


public class ChatPanel extends JPanel {

		//Attributes
		private String username;


		//Constructor
		public ChatPanel(String username) {
				this.username = username;

				setResizable(false);
				setLayout(null);
				setVisible(true);
		}

}
