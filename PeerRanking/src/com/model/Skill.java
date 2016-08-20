package com.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name="SKILL")
public class Skill {
	
	@Id
	@GeneratedValue
	private int skillId;
	

	@Column(name="SKILL_NAME")
	private String skillName;
	
	//As for single skill, different person give ranking
	@OneToMany(mappedBy="skill",cascade=CascadeType.ALL)
	private List<Ranking> ranking;
	
	@ManyToOne
	private Person person; 
	
	/**
	 * @return the person
	 */
	public Person getPerson() {
		return person;
	}

	/**
	 * @param person the person to set
	 */
	public void setPerson(Person person) {
		this.person = person;
	}

	/**
	 * @return the skillName
	 */
	public String getSkillName() {
		return skillName;
	}
	/**
	 * @param skillName the skillName to set
	 */
	public void setSkillName(String skillName) {
		this.skillName = skillName;
	}

	/**
	 * @return the ranking
	 */
	public List<Ranking> getRanking() {
		return ranking;
	}

	/**
	 * @param ranking the ranking to set
	 */
	public void setRanking(List<Ranking> ranking) {
		this.ranking = ranking;
	}

	/**
	 * @return the skillId
	 */
	public int getSkillId() {
		return skillId;
	}

	/**
	 * @param skillId the skillId to set
	 */
	public void setSkillId(int skillId) {
		this.skillId = skillId;
	}
	
}
