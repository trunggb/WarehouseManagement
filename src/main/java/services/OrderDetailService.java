package services;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;

import entities.OrderDetail;
import entities.Product;

@Stateless
public class OrderDetailService extends GenericService<Product> {

	
	public List<OrderDetail> findDetailsByOrderId(Integer orderId) {
		
		TypedQuery<OrderDetail> query = this.em.createNamedQuery("findDetailsByOrderId", OrderDetail.class);
		query.setParameter("orderId", orderId);
		return query.getResultList();
	}
}
