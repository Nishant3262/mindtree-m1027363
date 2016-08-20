package com.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="PERSON")
public class Person {

	@Id
	@GeneratedValue
	private int id;
	
	@Column(name="NAME")
	private String name;

	@OneToMany(mappedBy="person", cascade = CascadeType.ALL)
	private List<Skill> skills;

	@Transient
	private Long avgRanking;
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
	 * @return the skills
	 */
	public List<Skill> getSkills() {
		return skills;
	}

	/**
	 * @param skills the skills to set
	 */
	public void setSkills(List<Skill> skills) {
		this.skills = skills;
	}

	/**
	 * @return the avgRanking
	 */
	public Long getAvgRanking() {
		return avgRanking;
	}

	/**
	 * @param avgRanking the avgRanking to set
	 */
	public void setAvgRanking(Long avgRanking) {
		this.avgRanking = avgRanking;
	}
	
	
}
