package dao;

import java.util.List;

import jakarta.persistence.EntityManager;
import model.Project;

public class ProjectDAO implements DAOInterface<Project> {

	private EntityManager em;

	public ProjectDAO(EntityManager em) {
		this.em = em;
	}

	@Override
	public boolean add(Project project) {
		try {
			em.persist(project);
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean update(Project project) {
		try {
			em.merge(project);
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean delete(Project project) {
		try {
			em.remove(project);
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}

	@Override
	public Project findById(String id) {
		return em.find(Project.class, id);
	}

	@Override
	public List<Project> getAll() {
		String query = "SELECT project FROM Project project WHERE project.manager.id is not null";

		try {
			return em.createQuery(query, Project.class).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}