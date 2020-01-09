package entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@NamedQuery(name = "findDetailsByOrderId", query = "SELECT o FROM OrderDetail o WHERE o.order.id = :orderId")
@Entity
@Builder
@Data
@Table(name = "order_detail")
public class OrderDetail {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@ManyToOne
	@JoinColumn(name = "order_id")
	Order order;
	
	@ManyToOne
	@JoinColumn(name = "product_id")
	Product product;
	
	
	@Column(name = "amount")
	private int amount;
}
