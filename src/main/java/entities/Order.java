package entities;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@NamedQuery(name = "findAllOrder", query = "SELECT o FROM Order o order by o.id desc")
@Entity
@Builder
@Data
@Table(name = "product_order")
public class Order {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@ManyToOne
	@JoinColumn(name = "create_by")
	private User creator;
	
	@Column(name = "created_date")
	private Date createdDate;
	
	@Column(name = "note")
	private String note;
	
	@Column(name = "status")
	private OrderStatus status;
	
	@ManyToOne
	@JoinColumn(name = "delivery_by")
	private User shipper;
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "order_id")
	private List<OrderDetail> orderDetails;
	
}
