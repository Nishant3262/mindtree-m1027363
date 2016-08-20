package com.dao;

import java.util.List;

import org.hibernate.NonUniqueResultException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;


import com.model.Person;
import com.model.Ranking;

public class PeerStatusDAO {
	
	//For adding New Person related data
	public void addPeerData(Person person,Session session)
	{
		Transaction tx=session.beginTransaction();
		
		session.save(person);
		
		tx.commit();
	}
	
	//fetch existing person data by name (Assuming person name to be unique)
	public Person getPersonByName(String personName,Session session ) throws NonUniqueResultException
	{
		Transaction tx=session.beginTransaction();
		
		Query query = session.createQuery("FROM Person p where p.name =:pName");
		query.setParameter("pName", personName);
		Person person = (Person)query.uniqueResult();
		
		tx.commit();
		return person;
	}
	
	//fetch existing person data by name and skill(Assuming person name to be unique)
	public List<Ranking> getRankindForNameAndSkill(String personName,String skillToUpdate, Session session ) throws NonUniqueResultException
	{
		Transaction tx=session.beginTransaction();
		List<Ranking> rankings;
		
		Query query = session.createQuery("SELECT s.ranking from Person p join p.skills s "
				+ "where p.name =:pName and s.skillName=:pSkillName");
		query.setParameter("pName", personName);
		query.setParameter("pSkillName", skillToUpdate);
		rankings=(List<Ranking>)query.list();
		
		tx.commit();
		return rankings;
	}
	
	//update the person entity
	public void updatePerson(Person person,Session session)
	{
		Transaction tx=session.beginTransaction();
		
		session.update(person);
		
		tx.commit();
	}
	
	//Update the ranking provided by observer
	public void updateRankingByObserver (Ranking rank,Session session)
	{
		Transaction tx=session.beginTransaction();
		
		session.persist(rank);
		
		tx.commit();
	}
	
	//Delete RAnking
	public void deleteRanking(Ranking rank,Session session )
	{
		Transaction tx=session.beginTransaction();
		
		session.delete(rank);
		
		tx.commit();
	}
	
	public Long getTotalRatingForEachPerson ( int personId, Session session )
	{
		Transaction tx=session.beginTransaction();
		Long count = 0L;
		Query query = session.createQuery("SELECT SUM(r.value) from Ranking r "
				+ "where r.skill.person.id =:pId");
		query.setParameter("pId", personId);
		
		count=(Long) query.uniqueResult();
		
		tx.commit();
		return count;
	}
	
	public List<Person> getAllPersons( Session session ) throws NonUniqueResultException
	{
		Transaction tx=session.beginTransaction();
		List<Person> persons;
		
		Query query = session.createQuery("FROM Person P");
		persons=(List<Person>)query.list();
		
		tx.commit();
		return persons;
	}
}
