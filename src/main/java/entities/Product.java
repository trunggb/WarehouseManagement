package entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@NamedQuery(name = "findAllProduct", query = "SELECT p FROM Product p order by p.id desc")
@Entity
@Table(name = "product")
@Data
@Builder

public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "name", nullable = true)
	private String name;

	@Column(name = "image")
	private String image;

	@Column(name = "weight")
	private double weight;

	@Column(name = "amount")
	private int amount;

	@Column(name = "status")
	private ProductStatus status;

	@Column(name = "in_date")
	private Date inDate;

	@Column(name = "out_date")
	private Date outDate;

	@Column(name = "expiry_date")
	private Date expiryDate;
}
