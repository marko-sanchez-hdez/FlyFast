package utils;

import java.io.Serializable;


public final class Message implements Serializable {


    //Attributes
    private String username;
    private String ip;
    private String message;


    //Getters
    public String getUsername() {
	return username;
    }

    public String getIP() {
	return ip;
    }

    public String getMessage() {
	return message;
    }


    //Setters
    public void setUsername(String username) {
	this.username = username;
    }

    public void setIP(String ip) {
	this.ip = ip;
    }

    public void setMessage(String message) {
	this.message = message;
    }

}
