package services;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;

import entities.Location;

@Stateless
public class LocationService extends GenericService<Location>{
	public List<Location> findAll() {
		TypedQuery<Location> q = em.createNamedQuery("findAllLocation", Location.class);
		return q.getResultList();
	}
	
	public List<Location> findAllReceptionPoint(){
		return findAll().stream().filter(loc -> loc.getCol().equals("R")).collect(Collectors.toList());
	}
	public boolean updateLocation(Location location) {
		return this.update(location);
	}
}
