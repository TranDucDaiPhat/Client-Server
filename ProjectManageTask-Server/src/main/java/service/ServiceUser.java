package service;

import dao.AccountDAO;
import dao.UserDAO;
import jakarta.persistence.EntityManager;
import model.Account;

public class ServiceUser {

	private UserDAO userDAO;
	private AccountDAO accDao;
	private EntityManager em;
	
	public ServiceUser(EntityManager em) {
		this.em = em;
		this.userDAO = new UserDAO(em);
		this.accDao = new AccountDAO(em);
	}
	
	public Account login(String username, String password) {
		return accDao.findAccountToLogin(username, password);
	}
}
