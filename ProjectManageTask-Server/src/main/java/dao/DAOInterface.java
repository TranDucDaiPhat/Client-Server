package dao;

import java.util.List;

public interface DAOInterface<T> {
	
	public boolean add(T t);
	
	public boolean update(T t);
	
	public boolean delete(T t);
	
	public T findById(String id);
	
	public List<T> getAll();
}