package services;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.persistence.EntityNotFoundException;
import javax.persistence.TypedQuery;

import entities.Order;


@Stateless
public class OrderService extends GenericService<Order> {

	public OrderService() {
		super();
	}

	public boolean add(Order product) {
		return this.create(product);
	}

	public boolean updateOrder(Order order) {
		return this.update(order);
	}
	
	public void remove(Order product) {
		Optional<Order> removedProduct = this.find(product.getId());
		if(removedProduct.isPresent()) {
			this.em.remove(removedProduct.get());
		}else {
			throw new EntityNotFoundException("Can not find product with id :" + product.getId());
		}
	}
	
	
	public Optional<Order> find(int id){
		if(id >= 0) {
			Order ret = this.em.find(Order.class, id);
			if(ret != null) {
				return Optional.of(ret);
			}
		}
		return Optional.empty();
	}
	
	public List<Order> findAll() {
		TypedQuery<Order> q = em.createNamedQuery("findAllOrder", Order.class);
		return q.getResultList();
	}
	
	public boolean createOrder(Order order) {
		return this.create(order);
	}
}
