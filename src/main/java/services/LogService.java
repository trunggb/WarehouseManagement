package services;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;

import entities.Log;

@Stateless
public class LogService extends GenericService<Log>{
	public boolean add(Log log) {
		return this.create(log);
	}
	
	public List<Log> findAll() {
		TypedQuery<Log> q = em.createNamedQuery("findAllLog", Log.class);
		return q.getResultList();
	}
}
