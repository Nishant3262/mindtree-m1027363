package com.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name="MOVIES", uniqueConstraints={@UniqueConstraint(columnNames = { "TITLE" })})
public class Movies {

	@Id
	@Column(name="MOVIES_ID")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int moviesId;
	
	@Column(name="TITLE")
	private String title;
	
	@Column(name="DIRECTOR")
	private String director;
	
	@Lob
	@Column(name="SYNOPSIS")
	private String Synopsis;

	/**
	 * @return the moviesId
	 */
	public int getMoviesId() {
		return moviesId;
	}

	/**
	 * @param moviesId the moviesId to set
	 */
	public void setMoviesId(int moviesId) {
		this.moviesId = moviesId;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the director
	 */
	public String getDirector() {
		return director;
	}

	/**
	 * @param director the director to set
	 */
	public void setDirector(String director) {
		this.director = director;
	}

	/**
	 * @return the synopsis
	 */
	public String getSynopsis() {
		return Synopsis;
	}

	/**
	 * @param synopsis the synopsis to set
	 */
	public void setSynopsis(String synopsis) {
		Synopsis = synopsis;
	}
}
