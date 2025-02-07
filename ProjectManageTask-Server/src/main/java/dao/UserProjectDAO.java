package dao;

import java.util.List;

import jakarta.persistence.EntityManager;
import model.UserProject;

public class UserProjectDAO implements DAOInterface<UserProject>{
	
	private EntityManager em;
	public UserProjectDAO(EntityManager em) {
		this.em = em;
	}
 
	@Override
    public boolean add(UserProject userProject) {
        try {
            em.persist(userProject);
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(UserProject userProject) {
        try {
            em.merge(userProject);
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(UserProject up) {
        try {
            em.remove(up);
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public UserProject findById(String id) {
        return em.find(UserProject.class, id);
    }

    @Override
    public List<UserProject> getAll() {
        String query = "SELECT up FROM UserProject up";

        try {
            return em.createQuery(query, UserProject.class).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}