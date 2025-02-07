package dao;

import java.util.List;

import jakarta.persistence.EntityManager;
import model.User;

public class UserDAO implements DAOInterface<User> {

	private EntityManager em;

	public UserDAO(EntityManager em) {
		this.em = em;
	}

	@Override
	public boolean add(User user) {
		try {
			em.persist(user);
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean update(User user) {
		try {
			em.merge(user);
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean delete(User user) {
		try {
			em.remove(user);
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return false;
	}

	@Override
	public User findById(String id) {
		return em.find(User.class, id);
	}

	@Override
	public List<User> getAll() {
		String query = "SELECT user FROM User user WHERE user.manager.id is not null";

		try {
			return em.createQuery(query, User.class).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}