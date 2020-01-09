package beans;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;

import entities.OrderDetail;
import entities.Product;
import lombok.Getter;
import lombok.Setter;
import services.OrderService;
import services.ProductService;

@SuppressWarnings("deprecation")
@ManagedBean(name="orderUpdateBean")
@ViewScoped
public class OrderUpdateBean implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8541930082961765831L;

	/**
	 * 
	 */

	final static Logger logger = Logger.getLogger(OrderService.class);

	@Getter
	@Setter
	private List<OrderDetail> orderDetails;
	
	@Getter
	@Setter
	private List<Product> products;
	
	@Getter
	@Setter
	private String note	;
	
	private static final int NUM_PROD = 5;
	
	@EJB
	ProductService productService;
	
	@PostConstruct
	public void init() {
		this.products = productService.findAll();
		for (int i = 0; i < NUM_PROD; i++) {
			Product product = Product.builder().id(-1).build();
			OrderDetail orderDetail = OrderDetail.builder().product(product).amount(0).build();
			this.orderDetails.add(orderDetail);
		}
	}
	
//	public void update() {
//		if(this.productService.updateProduct(product)) {
//			logger.info("Update product succesfully");
//			PrimeFaces.current().executeScript("parent.showSuccessMessage('Product updated succesfully!')");
//		}else {
//			logger.info("Can not update products");
//			PrimeFaces.current().executeScript("parent.showErrorMessage('Product remove failed!')");
//		}
//		
//		PrimeFaces.current().executeScript("parent.reloadPage();");
//	}
}
