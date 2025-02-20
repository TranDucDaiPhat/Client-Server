package dao;

import java.util.List;

import jakarta.persistence.EntityManager;
import model.Account;

public class AccountDAO implements DAOInterface<Account> {
	
	private EntityManager em;
 
    public AccountDAO(EntityManager em){
        this.em = em;
    }
    
    public boolean add(Account account) {
        try{
            em.persist(account);
            return true;
        }catch (Exception ex){
            return false;
        }
    }
    
    public boolean update(Account account){
        try{
            em.merge(account);
            return true;
        }catch (Exception ex){
            return false;
        }
    }
    
    public boolean delete(Account acc){
        try{
            em.remove(acc);
            return true;
        }catch (Exception ex){
            return false;
        }
    }
    
    public Account findById(String id){
        return em.find(Account.class, id);
    }
    
    public int findByAccountName(String accountName){
    	String query = "SELECT COUNT(acc) FROM Account acc WHERE acc.accountName = :accountName";
    	try {
            return em.createQuery(query, Long.class)
                    .setParameter("accountName", accountName)
                    .getSingleResult().intValue();
        } catch (Exception e) {
        	e.printStackTrace();
            return -1; 
        }
    }
    
    public Account findAccountToLogin(String accountName, String password){
    	String query = "SELECT acc FROM Account acc WHERE acc.accountName = :accountName and acc.password = :password";

        try {
            return em.createQuery(query, Account.class)
                    .setParameter("accountName", accountName)
                    .setParameter("password", password)
                    .getSingleResult();
        } catch (Exception e) {
            return null; 
        }
    }

	@Override
	public List<Account> getAll() {
		String query = "SELECT account FROM Account account WHERE account.manager.id is not null";
		
		try {
			return em.createQuery(query, Account.class).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}