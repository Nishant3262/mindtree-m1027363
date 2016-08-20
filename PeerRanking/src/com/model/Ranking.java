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
	
}
