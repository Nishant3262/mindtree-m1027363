package com.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class Ranking {
	@Id
	@GeneratedValue
	private int id;
	
	//0 indicate not ranked
	@Column(name="VALUE")
	private int value=0;
	
	@OneToOne
	@JoinColumn(name="OBSERVER_ID")
	private Person rankedBy;
	
	@OneToOne
	@JoinColumn(name="SUBJECT_ID")
	private Person subject;
	
	@ManyToOne
	private Skill skill;
	/**
	 * @return the value
	 */
	public int getValue() {
		return value;
	}
	/**
	 * @param value the value to set
	 */
	public void setValue(int value) {
		this.value = value;
	}
	/**
	 * @return the rankedBy
	 */
	public Person getRankedBy() {
		return rankedBy;
	}
	/**
	 * @param rankedBy the rankedBy to set
	 */
	public void setRankedBy(Person rankedBy) {
		this.rankedBy = rankedBy;
	}
	
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return the skill
	 */
	public Skill getSkill() {
		return skill;
	}
	/**
	 * @param skill the skill to set
	 */
	public void setSkill(Skill skill) {
		this.skill = skill;
	}
	/**
	 * @return the subject
	 */
	public Person getSubject() {
		return subject;
	}
	/**
	 * @param subject the subject to set
	 */
	public void setSubject(Person subject) {
		this.subject = subject;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((skill == null) ? 0 : skill.hashCode());
		result = prime * result + ((subject == null) ? 0 : subject.hashCode());
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
		Ranking other = (Ranking) obj;
		if (id != other.id)
			return false;
		if (skill == null) {
			if (other.skill != null)
				return false;
		} else if (!skill.equals(other.skill))
			return false;
		if (subject == null) {
			if (other.subject != null)
				return false;
		} else if (!subject.equals(other.subject))
			return false;
		return true;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Ranking [id=" + id + ", value=" + value + ", rankedBy=" + rankedBy + ", subject=" + subject + ", skill="
				+ skill + "]";
	}


	
	
}
