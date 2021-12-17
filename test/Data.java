package utils;

import java.net.Socket;
import java.io.OutputStream;
import java.io.DataOutputStream;
import java.io.DataInputStream;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;


public final class Data {

		//Attributes
		private String name;
		private String username;
		private String password;
		private String data;
		private Socket socket;


		//Constructors
		public Data(String username, String password) {
				this.username = username;
				this.password = password;
		}

		public Data(String name, String username, String password) {
				this.name = name;
				this.username = username;
				this.password = password;
		}


		//Methods
		public boolean checkName() {
				for(int i=0; i<name.length(); i++) {
						if((name.charAt(i)>64 && name.charAt(i)<91) || (name.charAt(i)>96 && name.charAt(i)<123))
								continue;
						else if(name.charAt(i) == 32)
								continue;
						else
								return false;
				}

				return true;
		}

		public boolean checkUsername() {
				for(int i=0; i<username.length(); i++) {
						if((username.charAt(i)>64 && username.charAt(i)<91) || (username.charAt(i)>96 && username.charAt(i)<123))
								continue;
						else if(username.charAt(i)>47 && username.charAt(i)<58)
								continue;
						else if(username.charAt(i) == 95)
								continue;
						else
								return false;
				}

				return true;
		}

		public boolean checkPassword() {
				int capitals = 0;
				int smalls = 0;
				int numbers = 0;
				int special = 0;

				for(int i=0; i<password.length(); i++) {
						//A-Z
						if(password.charAt(i)>64 && password.charAt(i)<91)
								capitals++;
						//a-z
						else if(password.charAt(i)>96 && password.charAt(i)<123)
								smalls++;
						//0-9
						else if(password.charAt(i)>47 && password.charAt(i)<58)
								numbers++;
						//@,?
						else if(password.charAt(i)>62 && password.charAt(i)<65)
								special++;
						//%,#,$
						else if(password.charAt(i)>34 && password.charAt(i)<38)
								special++;
						//[,],\,^
						else if(password.charAt(i)>90 && password.charAt(i)<95)
								special++;
						//(,),'
						else if(password.charAt(i)>38 && password.charAt(i)<42)
								special++;
						//.,/,-,,,+
						else if(password.charAt(i)>42 && password.charAt(i)<48)
								special++;
						//!
						else if(password.charAt(i) == 33)
								special++;
						//:
						else if(password.charAt(i) == 58)
								special++;
						//{
						else if(password.charAt(i) == 123)
								special++;
						//~,}
						else if(password.charAt(i)>124 && password.charAt(i)<127)
								special++;
						else
								return false;
				}

				if(capitals>0 && smalls>0 && numbers>0 && special>0)
						return true;

				return false;
		}

		public void registUser() {
				data = name+","+username+","+password;
				try {
						socket = new Socket("18.235.33.196",6686);
						OutputStream output = socket.getOutputStream();
						DataOutputStream dataoutput = new DataOutputStream(output);
						dataoutput.writeUTF(data);
						dataoutput.flush();
						dataoutput.close();
				} catch(IOException e) {
						e.printStackTrace();
				}
		}

		public String verifyUser() {
				data = username+","+password;
				String result = "";

				try {
						socket = new Socket("18.235.33.196",6898);
						DataOutputStream out = new DataOutputStream(socket.getOutputStream());
						BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

						out.writeUTF(data);
						result = in.readLine();
						out.close();
						in.close();
						socket.close();
				} catch(IOException e) {
						e.printStackTrace();
				}
				return result;
		}

}
