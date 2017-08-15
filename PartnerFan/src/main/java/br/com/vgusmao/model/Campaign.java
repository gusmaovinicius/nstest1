package br.com.vgusmao.model;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.annotation.Id;

public class Campaign implements Serializable{
	
	private static final long serialVersionUID = 8860980640517255420L;
	
	@Id
	private String name;
	private String heartTeam;
	private Date startDate ;
	private Date endDate;
	
	protected Campaign (){}

	public Campaign(String name, String heartTeam, Date startDate, Date endDate) {
		super();
		this.name = name;
		this.heartTeam = heartTeam;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getHeartTeam() {
		return heartTeam;
	}

	public void setHeartTeam(String heartTeam) {
		this.heartTeam = heartTeam;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}	
	
}
