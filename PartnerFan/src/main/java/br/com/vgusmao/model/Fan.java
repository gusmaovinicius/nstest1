package br.com.vgusmao.model;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;

@TypeAlias("fan")
public class Fan implements Serializable{
	
	private static final long serialVersionUID = 8860980640517255120L;
	
	@Id
	private String email;
	private String name;
	private Date birthDate;
	private String heartTeam;
	
	protected Fan(){}
	
	public Fan(String email, String name, Date birthDate, String heartTeam) {
		super();
		this.email = email;
		this.name = name;
		this.birthDate = birthDate;
		this.heartTeam = heartTeam;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public String getHeartTeam() {
		return heartTeam;
	}

	public void setHeartTeam(String heartTeam) {
		this.heartTeam = heartTeam;
	}

	public Fan(String email) {
		super();
		this.email = email;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}	
}
