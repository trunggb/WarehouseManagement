package services;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public abstract class GenericService <E>{

	@PersistenceContext(name="SmartWarehouse")
	EntityManager em;
	
	public void setEm(EntityManager em) {
		this.em = em;
	}
	
	public EntityManager getEm() {
		return this.em;
	}
	public boolean create(E entity) {
		if(entity != null) {
			em.persist(entity);
			return true;
		}
		return false;
	}
	
	public boolean update (E entity){
		if(entity != null) {
			em.merge(entity);
			return true;
		}
		return false;
	}
	public boolean delete(E entity) {
		if(entity != null) {
			em.remove(entity);
			return true;
		}
		return false;
	}
	
}
