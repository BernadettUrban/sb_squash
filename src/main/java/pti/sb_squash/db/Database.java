package pti.sb_squash.db;

import java.util.List;

import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import pti.sb_squash.model.Location;
import pti.sb_squash.model.Match;
import pti.sb_squash.model.User;

public class Database {

	private SessionFactory sessionFactory;

	public Database() {

		StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure() // configures settings from
																							// hibernate.cfg.xml
				.build();

		sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
	}

	public void close() {
		sessionFactory.close();
	}

	public void saveUser(User user) {

		Session session = sessionFactory.openSession();
		Transaction t = session.beginTransaction();

		session.save(user);

		t.commit();
		session.close();
	}

	public void saveLocation(Location location) {

		Session session = sessionFactory.openSession();
		Transaction t = session.beginTransaction();

		session.save(location);

		t.commit();
		session.close();
	}

	public void saveMatch(Match match) {

		Session session = sessionFactory.openSession();
		Transaction t = session.beginTransaction();

		session.save(match);

		t.commit();
		session.close();

	}

	public List<Match> getMatches() {
		List<Match> matches = null;

		Session session = sessionFactory.openSession();
		Transaction tr = session.beginTransaction();

		Query query = session.createQuery("SELECT m FROM Match m", Match.class);
		matches = query.getResultList();

		tr.commit();
		session.close();

		return matches;

	}
	
	public List<User> getUsers(){
		List<User> users = null;
		
		Session session = sessionFactory.openSession();
		Transaction tr = session.beginTransaction();

		Query query = session.createQuery("SELECT u FROM User u", User.class);
		users = query.getResultList();

		tr.commit();
		session.close();

		return users;
	}

	public User getUserById(int id){
		User user = null;
		List<User> users = null;

		Session session = sessionFactory.openSession();
		Transaction tr = session.beginTransaction();

		user = session.get(User.class, id);
		/*Query query = session.createQuery("SELECT u FROM User u WHERE u.id =: id", User.class);
		query.setParameter("id", id);
		users = query.getResultList();
		user = users.get(0);

		 */
		tr.commit();
		session.close();

		return user;
	}

	public List<Location> getLocations(){
		List<Location> locations = null;
		
		Session session = sessionFactory.openSession();
		Transaction tr = session.beginTransaction();

		Query query = session.createQuery("SELECT l FROM Location l", Location.class);
		locations = query.getResultList();

		tr.commit();
		session.close();

		return locations;
	}


	public boolean registeredUser(String name, String pwd) {
		boolean registered = false;
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();

		Query query = session.createQuery("SELECT u FROM User u WHERE u.name = :name AND u.password = :pwd",
				User.class);
		query.setParameter("name", name);
		query.setParameter("pwd", pwd);
		List<User> users = query.getResultList();

		if (users.isEmpty() == false) {
			registered = true;
		}

		transaction.commit();
		session.close();
		return registered;
	}

	public User getUserByName(String name){
		User user = null;
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();

		Query query = session.createQuery("SELECT u FROM User u WHERE u.name = :name",
				User.class);
		query.setParameter("name", name);
		List<User> users = query.getResultList();
		user = users.get(0);
		transaction.commit();
		session.close();
		return user;
	}

	public void updatePwd(String pwd, String name){

		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();

		//"UPDATE users u SET u.name = :uName WHERE u.birthYear >= :startDate AND u.birthYear <= :endDate");
		Query query = session.createQuery("UPDATE users u SET u.password =:pwd WHERE u.name =:name",
				User.class);
		query.executeUpdate();
		transaction.commit();
		session.close();

	}
}
