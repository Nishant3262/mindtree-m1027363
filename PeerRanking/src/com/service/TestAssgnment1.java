package com.service;

import java.util.ArrayList;
import java.util.List;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

import com.dao.PeerStatusDAO;
import com.model.Person;
import com.model.Ranking;
import com.model.Skill;

public class TestAssgnment1 {

	//Default constructor
	public TestAssgnment1()
	{
		setUp();
	}
	
	SessionFactory sessionFactory;
	
	public void setUp()
	{
		Configuration configuration = new Configuration();
		configuration.configure();
		ServiceRegistryBuilder srBuilder = new ServiceRegistryBuilder();
		srBuilder.applySettings(configuration.getProperties());
		ServiceRegistry serviceRegistry = srBuilder.buildServiceRegistry();
		sessionFactory = configuration.buildSessionFactory(serviceRegistry);
	}
	
	//For stuffing data, if "hbm2ddl.auto" is set to create
		public void beforTest(Session session)
		{
			for(int i=0;i<5;i++)
			{
				Skill skill = new Skill();
				skill.setSkillName("SKILL_"+i);
				Person person = new Person();
				person.setName("PERSON_"+i);
				createPersons(person,skill,session);
			}
		}
	
	//Service for creating New Person Record, with one skill.
	public void createPersons(Person person,Skill skill,Session session )
	{
		List<Skill> skills = new ArrayList<Skill>();
		skills.add(skill);
		skill.setPerson(person);
		person.setSkills(skills);
		PeerStatusDAO peerDAO = new PeerStatusDAO();
		//Cascade is implemented, for skill (in person) and ranking (in skill) entity
		peerDAO.addPeerData(person,session);
	}
	
	//Add skill to existing person
	public void addSkillToPerson(String personName,String skillName,Session session)
	{
		PeerStatusDAO peerDAO = new PeerStatusDAO();
		Person person = peerDAO.getPersonByName(personName, session);
		//If we got unique result
		List<Skill> skills = person.getSkills();
		Skill skill=new Skill();
		skill.setSkillName(skillName);
		skill.setPerson(person);
		skills.add(skill);
		peerDAO.updatePerson(person, session);
	}
	
	//Put ranking by observer for first time
	public void giveRanking (String observerName, String subjectName, String skillToBeEvaluated, int rankingValue, Session session )
	{
		PeerStatusDAO peerDAO = new PeerStatusDAO();
		Person person = peerDAO.getPersonByName(subjectName, session);
		//If we got unique result
		for(Skill s:person.getSkills())
		{
			if(s.getSkillName().equals(skillToBeEvaluated))
			{
				Person observerObj = peerDAO.getPersonByName(observerName, session);
				Ranking rank=new Ranking();
				rank.setValue(rankingValue);
				rank.setRankedBy(observerObj);
				rank.setSkill(s);
				List<Ranking> rankings=new ArrayList<Ranking>();
				rankings.add(rank);
				s.setRanking(rankings);
			}
		}
		peerDAO.updatePerson(person, session);
	}
	
	//Update ranking by observer
	public void updateRanking (String observerName, String subjectName, String skillToBeEvaluated, int rankingValue, Session session )
	{
		PeerStatusDAO peerDAO = new PeerStatusDAO();
		List<Ranking> rankings = peerDAO.getRankindForNameAndSkill(subjectName,skillToBeEvaluated,session);
		//If we got unique result.
			Person observerObj = peerDAO.getPersonByName(observerName, session);
			for(Ranking r :rankings)
			{
				if(r.getRankedBy().getId()==observerObj.getId())
				{
					r.setValue(rankingValue);
					peerDAO.updateRankingByObserver(r,session);
				}
			}
	}
	
	//Delete ranking by observer
	public void deleteRankingByObserver(String observerName, String subjectName, String skillToBeEvaluated, Session session)
	{
		PeerStatusDAO peerDAO = new PeerStatusDAO();
		List<Ranking> rankings = peerDAO.getRankindForNameAndSkill(subjectName,skillToBeEvaluated,session);
		//If we got unique result.
			Person observerObj = peerDAO.getPersonByName(observerName, session);
			Ranking toBeRemove = null;
			for(Ranking r :rankings)
			{
				if(r.getRankedBy().getId()==observerObj.getId())
				{
					toBeRemove=r;
					
				}
			}
			Person person = peerDAO.getPersonByName(subjectName, session);
			if(null != toBeRemove)
			{
				rankings.remove(toBeRemove);
				for(Skill s:person.getSkills())
				{
					if(s.getSkillName().equals(skillToBeEvaluated))
					{
						s.setRanking(rankings);
					}
				}
				peerDAO.deleteRanking(toBeRemove,session);
			}
	}
	
	//For getting AVG and displaying on console
	public void getAvgForEachPerson( Session session )
	{
		PeerStatusDAO peerDAO = new PeerStatusDAO();
		
		List<Person> persons = peerDAO.getAllPersons(session);
		for(Person p:persons)
		{
			int skillSize=p.getSkills().size();
			Long total=peerDAO.getTotalRatingForEachPerson(p.getId(), session);
			if(null!=total)
			{
				System.out.println("**********************************");
				System.out.println("Person : "+p.getName()+" Got :: "+total/skillSize);
				System.out.println("**********************************");
			}
			else
			{
				System.out.println("**********************************");
				System.out.println("Person : "+p.getName()+" Got :: No ranking yet");
				System.out.println("**********************************");
			}
		}
		
	}
	
	//Getting Top Ranker on basis of average
	public void getTopRankerOnBasisOfAVG( Session session )
	{
		PeerStatusDAO peerDAO = new PeerStatusDAO();
		
		List<Person> persons = peerDAO.getAllPersons(session);
		
		Person topper = null;
		for(Person p:persons)
		{
			int skillSize=p.getSkills().size();
			Long total=peerDAO.getTotalRatingForEachPerson(p.getId(), session);
			if(null!=total)
			{
				Long avg = total/skillSize;
				if(topper==null)
				{
					topper=p;
					p.setAvgRanking(avg);
				}
				else if(null != topper.getAvgRanking() && topper.getAvgRanking()<avg)
				{
					topper=p;
					p.setAvgRanking(avg);
				}
			}
		}
		System.out.println("**********************************");
		System.out.println("Top Ranking Person : "+topper.getName()+" Got :: "+topper.getAvgRanking());
		System.out.println("**********************************");
	}
	
	//For getting AVG and displaying on console
	public void getAvgForEachPersonForEachSkill( Session session )
	{
		PeerStatusDAO peerDAO = new PeerStatusDAO();
		
		List<Person> persons = peerDAO.getAllPersons(session);
		for(Person p:persons)
		{
			for(Skill s:p.getSkills())
			{
				System.out.println("**********************************");
				System.out.println("Person : "+p.getName() +" :: " );
				System.out.print("Skill : "+s.getSkillName() +" :: ");
				long total = 0;
				if(null!=s.getRanking())
				{
					for(Ranking r : s.getRanking())
					{
						total=total+r.getValue();
					}
					System.out.print(total/s.getRanking().size()+"\n");
				}
				else
				{
					System.out.print("No Rank\n");
				}
			}
		}
		
	}
	
	//function to test service
	public static void main(String[] args)
	{
		TestAssgnment1 test = new TestAssgnment1();
		Session session = test.sessionFactory.openSession();
		//Adding dummy Person data with one skill
		test.beforTest( session );
		
		//Adding Skill to existion Person
		test.addSkillToPerson("PERSON_1", "NEW_SKILL_1", session);
		
		//Giving the Ranking, for particular skill (Say to PERSON_1 by others)
		test.giveRanking("PERSON_0", "PERSON_1", "SKILL_1", 4,session);
		test.giveRanking("PERSON_2", "PERSON_1", "SKILL_1", 5,session);
		test.giveRanking("PERSON_3", "PERSON_1", "SKILL_1", 6,session);
		test.giveRanking("PERSON_4", "PERSON_1", "SKILL_1", 7,session);
		
		//Changing the Ranking by obsever (Say PERSON_3, update its value from 6 to 8
		test.updateRanking("PERSON_3", "PERSON_1", "SKILL_1", 8,session);
		
		//Removing the Ranking by observer (Say by PERSON_4)
		test.deleteRankingByObserver("PERSON_4", "PERSON_1", "SKILL_1", session);
		
		//Getting avg for Each person (Round of)
		test.getAvgForEachPerson(session);
		
		//Top Ranker
		test.getTopRankerOnBasisOfAVG(session);
		
		//Get avg of each user on each skill
		test.getAvgForEachPersonForEachSkill(session);
		
		
		session.close();
	}
}
