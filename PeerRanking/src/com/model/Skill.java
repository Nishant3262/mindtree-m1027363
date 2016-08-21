package com.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
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
	
	@ManyToMany
	private List<Person> persons; 
	
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

	/**
	 * @return the persons
	 */
	public List<Person> getPersons() {
		return persons;
	}
	/**
	 * @param persons the persons to set
	 */
	public void setPersons(List<Person> persons) {
		this.persons = persons;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + skillId;
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
		Skill other = (Skill) obj;
		if (skillId != other.skillId)
			return false;
		return true;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Skill [skillId=" + skillId + ", skillName=" + skillName + ", ranking=" + ranking + ", persons="
				+ persons + "]";
	}

}
