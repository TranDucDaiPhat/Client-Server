package dao;

import java.util.List;

import jakarta.persistence.EntityManager;
import model.Task;

public class TaskDAO implements DAOInterface<Task> {
	private EntityManager em;

	public TaskDAO(EntityManager em) {
		this.em = em;
	}

	@Override
	public boolean add(Task task) {
		try {
			em.persist(task);
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean update(Task task) {
		try {
			em.merge(task);
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean delete(Task task) {
		try {
			em.remove(task);
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return false;
	}

	@Override
	public Task findById(String id) {
		return em.find(Task.class, id);
	}

	@Override
	public List<Task> getAll() {
		String query = "SELECT task FROM Task task WHERE task.manager.id is not null";

		try {
			return em.createQuery(query, Task.class).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}