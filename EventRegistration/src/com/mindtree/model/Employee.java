package com.mindtree.model;

import java.beans.Transient;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name="EMPLOYEES")
public class Employee {
	 
	@Id
	@Column(name="MID")
	private String mID;
	
	@Column(name="NAME")
	private String name;
	
	@Column(name="JOIN_DATE")
	private Date joinDate;
	
	@Column(name="EMAIL_ID",unique=true)
	private String emailId;
	
	//Only admin user have password, need to insert from back end.
	@Column(name="PASSWORD")
	private String pwd;

	@ManyToMany(cascade=CascadeType.ALL)
	@JoinTable(name="EMPLOYEE_EVENT",
			joinColumns= @JoinColumn (name="MID"),
			inverseJoinColumns=@JoinColumn(name="EVENT_ID"))
	private Set<Event> events = new HashSet<Event>();
	
	/**
	 * @return the mID
	 */
	public String getmID() {
		return mID;
	}

	/**
	 * @param mID the mID to set
	 */
	public void setmID(String mID) {
		this.mID = mID;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the joinDate
	 */
	public Date getJoinDate() {
		return joinDate;
	}

	/**
	 * @param joinDate the joinDate to set
	 */
	public void setJoinDate(Date joinDate) {
		this.joinDate = joinDate;
	}

	/**
	 * @return the emailId
	 */
	public String getEmailId() {
		return emailId;
	}

	/**
	 * @param emailId the emailId to set
	 */
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	/**
	 * @return the events
	 */
	public Set<Event> getEvents() {
		return events;
	}

	/**
	 * @param events the events to set
	 */
	public void setEvents(Set<Event> events) {
		this.events = events;
	}

	/**
	 * @return the pwd
	 */
	public String getPwd() {
		return pwd;
	}

	/**
	 * @param pwd the pwd to set
	 */
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((emailId == null) ? 0 : emailId.hashCode());
		result = prime * result + ((joinDate == null) ? 0 : joinDate.hashCode());
		result = prime * result + ((mID == null) ? 0 : mID.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Employee other = (Employee) obj;
		if (emailId == null) {
			if (other.emailId != null)
				return false;
		} else if (!emailId.equals(other.emailId))
			return false;
		if (joinDate == null) {
			if (other.joinDate != null)
				return false;
		} else if (!joinDate.equals(other.joinDate))
			return false;
		if (mID == null) {
			if (other.mID != null)
				return false;
		} else if (!mID.equals(other.mID))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		String eventStr = getEventDetails( events);
		
		return "Employee [mID=" + mID + ", name=" + name + ", joinDate=" + joinDate + ", emailId=" + emailId
				+ ", events=" + 
				eventStr
				
				+ "]";
	}
	
	@Transient
	private String getEventDetails(Set<Event> events)
	{
		String eventStr = "Events [ ";
		for(Event event : events)
		{
			eventStr=eventStr+event.toString();
		}
		eventStr=eventStr+" ]";
		return eventStr;
	}
}
