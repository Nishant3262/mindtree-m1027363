package com.service.test;

import org.hibernate.exception.ConstraintViolationException;

import com.model.Movies;
import com.service.MovieDomain;

public class MovieDomainTest {

	
	public static void main(String[] args)
	{
		MovieDomain movie=new MovieDomain();
		
		//Inserting New Movie Details
		movie.insertNewMovie("Top Gun", "Tony Scott", "When Maverick encounters a pair of MiGs…");
		movie.insertNewMovie("Jaws", "Steven Spielberg", "A tale of a white shark!");
		movie.insertNewMovie("toDelete", "toDelete", "toDelelte!");
		//Negative Flow --- Insertion will fail, if title are same
		try
		{
			movie.insertNewMovie("Jaws", "Steven Spielberg", "A tale of a white shark!");
		}
		catch(ConstraintViolationException ce)
		{
			System.out.println("!! Movie with Same title already present !!");
		}
		
		//Updating Movie details
		movie.updateExistingRecord("Top Gun", "Top Gun1", "Tony Scott_1", "When Maverick encounters a pair...");
		
		//Negative flow -- If multiple records found
		movie.insertNewMovie("Jawsa", "Steven Spielberg_NEW", "A tale of a white shark!");
		movie.insertNewMovie("aJawsa", "Steven Spielberg_NEW_1", "A tale of a white shark!");
		
		//Should not update the record -- As multiple records found
		movie.updateExistingRecord("Jaw", "Jaws_NEW", "Steven Spielberg", "A tale of a white shark!");
		
		
		//Deleting existing records
		movie.deleteExistingRecord("toDelete");
		
		//Negative flow --- if multiple records get found
		movie.deleteExistingRecord("jaw");
		
		//Find A RECORD, with exact title
		Movies mov = movie.findRecordWithExactTitle("Jaws");
		System.out.println("---------------Movie Description-----------------");
		System.out.println("Titel : "+mov.getTitle() + "### Director : "+mov.getDirector()+"### Synopsis : "+mov.getSynopsis());
		System.out.println("---------------Movie Description END-----------------");
		
		//Find RECORDS with similar title
		System.out.println("---------------Movies Description-----------------");
		movie.findRecord("Jaws");
		System.out.println("---------------Movies Description-----------------");
	}
}
