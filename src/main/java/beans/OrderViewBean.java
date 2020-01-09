package beans;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import entities.Order;
import entities.OrderDetail;
import lombok.Getter;
import lombok.Setter;
import services.OrderDetailService;
import services.OrderService;
import services.ProductService;

@SuppressWarnings("deprecation")
@ManagedBean(name="orderViewBean")
@ViewScoped
public class OrderViewBean implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4098294323352760786L;
	@Setter
	@Getter
	private Order order;
	
	@Setter
	@Getter
	private List<OrderDetail> orderDetails;
	
	
	
	@EJB
	OrderService orderService;
	
	@EJB
	OrderDetailService orderDetailService;
	
	Logger logger = Logger.getLogger(ProductService.class);
	
	@PostConstruct
	public void init() {
		String orderIdAsString = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("orderId");
		if(StringUtils.isNotBlank(orderIdAsString)) {
			Optional<Order> optionalOrder = orderService.find(Integer.valueOf(orderIdAsString));
			if(optionalOrder.isPresent()) {
				this.order = optionalOrder.get();
				this.orderDetails = orderDetailService.findDetailsByOrderId(order.getId());
				
			}else {
				logger.info("Can not find order with id: " + orderIdAsString);
			}
		}else {
			logger.error("Can not get id of order from list order");
		}
	}
}
