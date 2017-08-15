package br.com.vgusmao.model;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.annotation.TypeAlias;

@TypeAlias("fanSubscriptions")
public class FanSubscription implements Serializable{
	
	private static final long serialVersionUID = 8860980640517255010L;
	
	private String fanEmail;
	private String campaign;
	private Date startDate;
	private Date endDate;
	
	protected FanSubscription(){}

	public FanSubscription(String fanEmail, String campaign, Date startDate, Date endDate) {
		super();
		this.fanEmail = fanEmail;
		this.campaign = campaign;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public String getFanEmail() {
		return fanEmail;
	}

	public void setFan(String fanEmail) {
		this.fanEmail = fanEmail;
	}

	public String getCampaign() {
		return campaign;
	}

	public void setCampaign(String campaign) {
		this.campaign = campaign;
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
