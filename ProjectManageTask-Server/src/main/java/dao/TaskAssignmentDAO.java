package dao;

import java.util.List;

import jakarta.persistence.EntityManager;
import model.TaskAssignment;

public class TaskAssignmentDAO implements DAOInterface<TaskAssignment> {
 
    private EntityManager em;

    public TaskAssignmentDAO(EntityManager em) {
        this.em = em;
    }

    @Override
    public boolean add(TaskAssignment taskAssignment) {
        try {
            em.persist(taskAssignment);
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(TaskAssignment taskAssignment) {
        try {
            em.merge(taskAssignment);
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(TaskAssignment ta) {
        try {
            em.remove(ta);
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public TaskAssignment findById(String id) {
        return em.find(TaskAssignment.class, id);
    }

    @Override
    public List<TaskAssignment> getAll() {
        String query = "SELECT ta FROM TaskAssignment ta";

        try {
            return em.createQuery(query, TaskAssignment.class).getResultList();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}