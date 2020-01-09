package services;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.persistence.EntityNotFoundException;
import javax.persistence.TypedQuery;

import entities.Product;

@Stateless
public class ProductService extends GenericService<Product> {

	public boolean add(Product product) {
		return this.create(product);
	}

	public boolean updateProduct(Product product) {
		return this.update(product);
	}
	
	public void remove(Product product) {
		Optional<Product> removedProduct = this.find(product.getId());
		if(removedProduct.isPresent()) {
			this.em.remove(removedProduct.get());
		}else {
			throw new EntityNotFoundException("Can not find product with id :" + product.getId());
		}
	}
	
	
	public Optional<Product> find(int id){
		if(id >= 0) {
			Product ret = this.em.find(Product.class, id);
			if(ret != null) {
				return Optional.of(ret);
			}
		}
		return Optional.empty();
	}
	
	public List<Product> findAll() {
		TypedQuery<Product> q = em.createNamedQuery("findAllProduct", Product.class);
		return q.getResultList();
	}
}
