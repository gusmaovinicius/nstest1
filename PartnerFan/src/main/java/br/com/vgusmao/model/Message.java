package br.com.vgusmao.model;

import java.io.Serializable;

public class Message implements Serializable{
	
	private static final long serialVersionUID = 8860980640517255100L;
	
	private String text;
	
	protected Message(){}
	
	public Message(String text) {
		super();
		this.text = text;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
}
